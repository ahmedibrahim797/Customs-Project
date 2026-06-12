package Portal.listeners;

import Portal.FileUtils;
import Portal.drivers.UITest;
import Portal.drivers.WebDriverProvider;
import Portal.media.ScreenRecordManager;
import Portal.media.ScreenshotsManager;
import Portal.utils.dataReader.PropertyReader;
import Portal.utils.logs.LogsManager;
import Portal.utils.report.AllureAttachmentManager;
import Portal.utils.report.AllureConstants;
import Portal.utils.report.AllureEnvironmentManager;
import Portal.utils.report.AllureReportGenerator;
import Portal.validation.Validation;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {
    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Automation Exercise");
    }

    public void onExecutionStart() {
        LogsManager.info("Test Execution started");
        cleanTestOutputDirectories();
        LogsManager.info("Directories cleaned");
        createTestOutputDirectories();
        LogsManager.info("Directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment set");
    }

    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        LogsManager.info("Test Execution Finished");
    }


    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (isUITest(testResult)) {
                ScreenRecordManager.startRecording();
            }
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = null;
        if (method.isTestMethod()) {
            if (isUITest(testResult)) {
                ScreenRecordManager.stopRecording(testResult.getName());
                if (testResult.getInstance() instanceof WebDriverProvider provider)
                    driver = provider.getWebDriver(); //initialize driver from WebDriverProvider
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                    case ITestResult.FAILURE ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                    case ITestResult.SKIP ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
                }
                AllureAttachmentManager.attachRecords(testResult.getName());
            }

            Validation.assertAll(testResult);

            AllureAttachmentManager.attachLogs();

        }
    }


    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " passed");
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " failed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " skipped");
    }


    // cleaning and creating dirs (logs, screenshots, recordings,allure-results)
    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));
        FileUtils.forceDelete(new File(LogsManager.LOGS_PATH + "logs.log"));
    }

    private void createTestOutputDirectories() {
        // Implement logic to create test output directories
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.createDirectory("src/test/resources/downloads/");

    }

    /**
     * Checks if the test class or test method is annotated with @UITest.
     * Cannot use instanceof because UITest is an annotation, not an interface.
     */
    private boolean isUITest(ITestResult testResult) {
        // Check class-level annotation
        if (testResult.getInstance().getClass().isAnnotationPresent(UITest.class)) {
            return true;
        }
        // Check method-level annotation
        return testResult.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(UITest.class);
    }
}