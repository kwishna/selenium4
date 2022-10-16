package org.example.drivers.managers;

import org.example.capabilities.CapabilitiesManager;
import org.example.enums.Browsers;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class BasicDriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = ThreadLocal.withInitial(() -> null);

    public static WebDriver getBasicDriver(Browsers browser) {
        if (DRIVER.get() == null) {
            WebDriver driver = switch (browser) {
                case CHROME -> getChromeDriver();
                case FIREFOX, MOZILLA, GECKO -> getFirefoxDriver();
                case EDGE -> getEdgeDriver();
                case IE -> getIEDriver();
                case SAFARI -> getSafariDriver();
                default -> throw new RuntimeException("No Match Found For The Provided Browser : " + browser);
            };
            DRIVER.set(driver);
        }
        return DRIVER.get();
    }

    private static ChromeDriver getChromeDriver() {
        System.setProperty("webdriver.chrome.driver", PropertyReader.getConfig("CHROMEDRIVER_PATH"));
        return new ChromeDriver((ChromeOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.CHROME));
    }

    private static FirefoxDriver getFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", PropertyReader.getConfig("FIREFOXDRIVER_PATH"));
        return new FirefoxDriver((FirefoxOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.FIREFOX));
    }

    private static EdgeDriver getEdgeDriver() {
        System.setProperty("webdriver.edge.driver", PropertyReader.getConfig("EDGEDRIVER_PATH"));
        return new EdgeDriver((EdgeOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.EDGE));
    }

    private static InternetExplorerDriver getIEDriver() {
        System.setProperty("webdriver.ie.driver", PropertyReader.getConfig("IEDRIVER_PATH"));
        return new InternetExplorerDriver((InternetExplorerOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.EDGE));
    }

    private static SafariDriver getSafariDriver() {
        System.setProperty("webdriver.ie.driver", PropertyReader.getConfig("IEDRIVER_PATH"));
        return new SafariDriver((SafariOptions) CapabilitiesManager.getBrowserCapabilities(Browsers.SAFARI));
    }
}
