package TestListener;
import Utilities.logUnit;
import org.testng.ITestContext;
import testBase.BaseTest;
import Utilities.utilityClass;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {

    public TestListener() {
    }

    public void onTestStart(ITestResult result) {
        logUnit.info("Test Case : "+result.getName()+" Started");
    }

    public void onTestSuccess(ITestResult result) {
        logUnit.info("Test Case : "+result.getName()+" Passed");
    }
    public void onTestSkipped(ITestResult result) {
        logUnit.info("Test Case : "+result.getName()+" Skipped");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        logUnit.info("Test Case : "+result.getName()+" Failed");
         //Capture screenshot on test failure
        BaseTest testInstance = (BaseTest) result.getInstance();
        WebDriver driver = testInstance.getDriver();
        // Get test case name
        String testName = result.getName();
        utilityClass.captureScreenshot(driver, testName);
    }
}
