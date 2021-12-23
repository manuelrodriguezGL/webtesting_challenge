package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotUtil {

    private final CommonUtils commonUtils = new CommonUtils();

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

    public boolean takeScreenshot(WebDriver driver, String failedTestName) {
        boolean result = false;
        try {
            String screenshotPath = commonUtils.getPropertyValue("screenshot_folder_name") +
                    File.separator + System.currentTimeMillis() + "_" + failedTestName + ".png";
            File screenshot = new File(screenshotPath);
            if (createFile(screenshot)) {
                try {
                    writeScreenshotToFile(driver, screenshot);
                    result = true;
                } catch (ClassCastException augmentationException) {
                    writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
                    result = true;
                }
                System.out.println("Screenshot written to: " + screenshotPath);
            } else {
                System.out.println("Unable to create screenshot! - " + screenshotPath);
            }
        } catch (Exception e) {
            System.out.println("Unable to capture screenshot!");
            e.printStackTrace();
        }

        return result;
    }
}
