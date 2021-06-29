package utils;

import java.text.MessageFormat;

/**
 * Class with common utilities
 */
public class CommonUtils {

    public static String formatLocator(String pattern, String text) {
        return MessageFormat.format(pattern, text);
    }
}
