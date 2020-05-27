package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import selenium.SeleniumBase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotListener extends TestListenerAdapter {

    private static final String SCREENSHOT_FOLDER_NAME = "screenshots";

    private boolean createFile(File screenshot) throws IOException {
        boolean fileCreated = false;
        if (screenshot.exists()) {
            fileCreated = true;
        } else {
            File directory = new File(screenshot.getParent());
            if (directory.exists() || directory.mkdirs()) {
                fileCreated = screenshot.createNewFile();
            }
        }
        return fileCreated;
    }

    private void writeScreenshotToFile(WebDriver driver, File screenshot) throws IOException {
        FileOutputStream stream = new FileOutputStream(screenshot);
        stream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        stream.close();
    }

    @Override
    public void onTestFailure(ITestResult failingTest) {
        try {
            WebDriver driver = SeleniumBase.getWebDriverInstance();
            String screenshotPath = SCREENSHOT_FOLDER_NAME + File.separator +
                    System.currentTimeMillis() + "_" + failingTest.getName() + ".png";
            File screenshot = new File(screenshotPath);
            if (createFile(screenshot)) {
                try {
                    writeScreenshotToFile(driver, screenshot);
                } catch (ClassCastException augmentationException) {
                    writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                }
                System.out.println("Screenshot written to: " + screenshotPath);
            } else {
                System.out.println("Unable to create screenshot! - " + screenshotPath);
            }
        } catch (Exception e) {
            System.out.println("Unable to capture screenshot!");
            e.printStackTrace();
        }
    }

}
