package listeners;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import utils.ScreenshotUtil;

public class TestExecutionListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult testResult) {
        super.onTestStart(testResult);
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        //Reporter
        ScreenshotUtil.takeScreenshot(testResult.getTestClass() + "_" + testResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {}
}
