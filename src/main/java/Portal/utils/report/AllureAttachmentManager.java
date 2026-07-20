package Portal.utils.report;


import Portal.media.ScreenRecordManager;
import Portal.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static Portal.utils.dataReader.PropertyReader.getProperty;

public class AllureAttachmentManager {

    /**
     * Holds the line index (0-based) in logs.log at which the current test case started.
     * Set by markLogsStart() in beforeInvocation; consumed by attachLogs() in afterInvocation.
     */
    private static int testStartLineIndex = 0;

    /**
     * Call this at the START of every test method (in beforeInvocation).
     * Records how many lines are already in logs.log so that attachLogs()
     * can later attach ONLY the lines written during this test.
     */
    public static void markLogsStart() {
        try {
            // Flush pending log events before counting lines
            LogManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();

            if (logFile.exists()) {
                List<String> lines = Files.readAllLines(logFile.toPath());
                testStartLineIndex = lines.size();
            } else {
                testStartLineIndex = 0;
            }
        } catch (Exception e) {
            testStartLineIndex = 0;
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Attaches ONLY the log lines produced during the current test case to Allure.
     * Must be called after markLogsStart() was called in beforeInvocation.
     */
    public static void attachLogs() {
        try {
            // Flush pending log events so all lines are flushed to disk
            LogManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();

            if (logFile.exists()) {
                List<String> allLines = Files.readAllLines(logFile.toPath());

                // Slice: only lines written AFTER markLogsStart() was called
                List<String> testLines = allLines.subList(
                        Math.min(testStartLineIndex, allLines.size()),
                        allLines.size()
                );

                String logsContent = String.join(System.lineSeparator(), testLines);
                Allure.attachment("logs.log", logsContent.isEmpty() ? "(no logs)" : logsContent);
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching logs", e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────

    public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
            } else {
                LogsManager.error("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching screenshot", e.getMessage());
        }
    }

    public static void attachRecords(String testMethodName) {
        if (getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                File recordingsDir = new File(ScreenRecordManager.RECORDINGS_PATH);
                File[] matchingFiles = recordingsDir.listFiles(
                        (dir, name) -> name.startsWith(testMethodName) && name.endsWith(".mp4")
                );
                if (matchingFiles != null && matchingFiles.length > 0) {
                    File record = matchingFiles[matchingFiles.length - 1]; // latest file
                    Allure.addAttachment(testMethodName, "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
                } else {
                    LogsManager.error("Recording file not found for test: " + testMethodName);
                }
            } catch (Exception e) {
                LogsManager.error("Error attaching records", e.getMessage());
            }
        }
    }
}
