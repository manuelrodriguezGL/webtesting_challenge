package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import utils.ScreenshotUtil;

public class TestExecutionListener extends TestListenerAdapter {
    ;

    @Override
    public void onTestStart(ITestResult testResult) {
        super.onTestStart(testResult);
        Reporter.log("Executing the following test: " + testResult.getName() + "\n");

    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        WebDriver driver = (WebDriver) testResult.getTestContext().getAttribute("WebDriver");
        ScreenshotUtil screenshotUtil = new ScreenshotUtil();
        Reporter.log("The following test has failed: " + testResult.getName() + "\n");
        if (screenshotUtil.takeScreenshot(driver, testResult.getTestClass() + "_" + testResult.getName())) {
            Reporter.log("Screenshot has been created successfully \n");
        }
        Reporter.log("Cause of test failure: " + testResult.getThrowable().getMessage());
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        Reporter.log("The following test has passed: " + testResult.getName() + "\n");
    }
}
