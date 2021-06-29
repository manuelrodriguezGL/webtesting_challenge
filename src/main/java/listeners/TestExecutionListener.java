package listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import utils.ScreenshotUtil;

public class TestExecutionListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult testResult) {
        super.onTestStart(testResult);
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        ScreenshotUtil.takeScreenshot(testResult.getTestClass() + "_" + testResult.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {}
}
