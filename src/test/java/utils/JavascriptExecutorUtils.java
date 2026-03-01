package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class JavascriptExecutorUtils {

    // <editor-fold desc="Class Fields / Constants">
    private final JavascriptExecutor js;

    public JavascriptExecutorUtils(WebDriver driver) {
        this.js = (JavascriptExecutor) driver;
    }
    // </editor-fold>

    // <editor-fold desc="Public Methods">
    public String getLocalStorageItem(String key) {
        return (String) this.js.executeScript(
                String.format("return window.localStorage.getItem('%s');", key)
        );
    }

    public void setLocalStorageItem(String key, String value) {
        this.js.executeScript(
                String.format("return window.localStorage.setItem('%s', '%s');", key, value)
        );
    }
    // </editor-fold>

}
