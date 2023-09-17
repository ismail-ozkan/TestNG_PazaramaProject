package com.pazarama.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;
import java.util.logging.Level;

public class Driver {
    //Creating a private constructor, so we are closing access to the object of this class from outside the class

    private Driver() {
    }

    // We make WebDriver private, because we want to close access from outside the class.
    // We make it static because we will use it in a static method.
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    //Create a re-usable utility method which will return same driver instance when we call it
    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            String browserType = null;
            // We put following statement for specifying driver type with maven command line if we need. Otherwise we
            // already have determined driver type in configuration.properties file
            if (System.getProperty("BROWSER") == null) {
                browserType = ConfigurationReader.getProperty("browser");
            } else {
                browserType = System.getProperty("BROWSER");
            }
            /*
                Depending on the browserType that will be return from configuration.properties file
                switch statement will determine the case, and open the matching browser
            */
            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    LoggingPreferences logPrefs = new LoggingPreferences();
                    logPrefs.enable(LogType.DRIVER, Level.ALL);
                    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                    options.setCapability("goog:loggingPrefs", logPrefs);
                    driverPool.set(new ChromeDriver(options));
                    break;
                case "chrome-headless":
                    ChromeOptions options2 = new ChromeOptions();
                    options2.setHeadless(true);
                    driverPool.set(new ChromeDriver(options2));
                    break;
                case "firefox":
                    driverPool.set(new FirefoxDriver());
                    break;
                case "firefox-headless":
                    driverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    break;
                case "ie":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    driverPool.set(new InternetExplorerDriver());
                    break;
                case "edge":
                    if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    driverPool.set(new EdgeDriver());
                    break;
                case "safari":
                    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                        throw new WebDriverException("Your operating system does not support the requested browser");
                    }
                    driverPool.set(new SafariDriver());
                    break;
                case "remote-chrome":
                    try {
                        String gridAddress = "localhost";
                        URL url = new URL("http://" + gridAddress + ":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("chrome");
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "remote-firefox":
                    try {
                        String gridAddress = "localhost";
                        URL url = new URL("http://" + gridAddress + ":4444/wd/hub");
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName("firefox");
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
        return driverPool.get();
    }

    // This method will make sure our driver value is always null after using quit() method
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit(); // this line will terminate the existing session. value will not even be null
            driverPool.remove();
        }
    }

}