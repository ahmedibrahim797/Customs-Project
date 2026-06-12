package Portal.utils.report;


import Portal.media.ScreenRecordManager;
import Portal.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static Portal.utils.dataReader.PropertyReader.getProperty;

public class AllureAttachmentManager {
    // attachScreenshot, attachLogs, attachRecords methods would go here
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

    public static void attachLogs() {
        try {
            LogManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching logs", e.getMessage());
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
