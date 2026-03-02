package driver;

import org.openqa.selenium.WebDriver;

public interface IBrowserCreator {

    WebDriver createDriver(int width, int height, boolean headless);

}
