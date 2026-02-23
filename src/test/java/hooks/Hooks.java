package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverManager;

public class Hooks {

    @Before("@ui")
    public void setUp() {
        DriverManager.initDriver();
    }

    @After("@ui")
    public void tearDown() {
        DriverManager.quitDriver();
    }

}
