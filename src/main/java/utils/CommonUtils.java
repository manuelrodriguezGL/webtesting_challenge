package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class with common utilities
 */
public class CommonUtils {

    private static final String PROPERTIES_FILE_NAME = "application.properties";

    /**
     * Formats dynamically generated locators
     *
     * @param pattern the locator pattern, with a placeholder for string interpolation
     * @param text    the value to be inserted into the string
     * @return A string value with the parameters interpolated
     */
    public static String formatLocator(String pattern, String text) {
        return MessageFormat.format(pattern, text);
    }

    /**
     * Reads thru the properties file, and gets the specified property
     * Credits: https://crunchify.com/java-properties-file-how-to-read-config-properties-values-in-java/
     *
     * @param property Name of the property that is needed
     * @return A string representation of the property value
     * @throws IOException
     */
    public static String getPropertyValue(String property) throws IOException {

        InputStream inputStream = null;

        try {
            Properties properties = new Properties();

            inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
            }

            return properties.getProperty(property);

        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
        }
    }

    /**
     * Gets all the properties needed at once, in the form of an array,
     * to avoid opening and closing the file multiple times
     *
     * @param propertiesArray An array of strings representing the properties names needed
     * @return An array with all the properties requested
     * @throws IOException
     */
    public static ArrayList<String> getPropertiesArray(ArrayList<String> propertiesArray) throws IOException {

        InputStream inputStream = null;
        ArrayList<String> result = new ArrayList<>();

        try {
            Properties properties = new Properties();
            inputStream = CommonUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
            }

            for (String s : propertiesArray) {
                result.add(properties.getProperty(s));
            }
            return result;

        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
        }
    }

}
