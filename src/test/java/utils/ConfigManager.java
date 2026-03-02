package utils;

public class ConfigManager {

    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }

    public static String getScreenType() {
        return System.getProperty("screenType", "desktop");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", "true")
        );
    }

    public static String getOS() {
        return System.getProperty("os", System.getProperty("os.name"));
    }

}
