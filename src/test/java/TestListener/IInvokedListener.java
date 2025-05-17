package TestListener;

import Utilities.utilityClass;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import testBase.BaseTest;

public class IInvokedListener implements TestLifecycleListener{
    BaseTest driver = new BaseTest();

    public void beforeTestStop(TestResult testResult) {


        // Get test case name
        System.out.println("test");
        String testName = testResult.getName();
        utilityClass.captureScreenshot(driver.getDriver(), testName);
    }
}
