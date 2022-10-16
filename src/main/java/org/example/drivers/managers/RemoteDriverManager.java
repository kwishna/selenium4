package org.example.drivers.managers;

import org.example.capabilities.CapabilitiesManager;
import org.example.enums.Browsers;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteDriverManager {
    private static final ThreadLocal<RemoteWebDriver> DRIVER = ThreadLocal.withInitial(() -> null);
    private static final String REMOTE_URL = PropertyReader.getConfig("GRID_URL");
    private static final boolean ENABLE_BROWSERSTACK = Boolean.parseBoolean(PropertyReader.getConfig("ENABLE_BROWSERSTACK"));
    private static final boolean ENABLE_SAUCELABS = Boolean.parseBoolean(PropertyReader.getConfig("ENABLE_SAUCELABS"));
    private static final boolean ENABLE_GRID = Boolean.parseBoolean(PropertyReader.getConfig("ENABLE_GRID"));

    public static RemoteWebDriver getRemoteDriver(Browsers browser) {
        if (DRIVER.get() == null) {
            RemoteWebDriver driver = switch (browser) {
                case CHROME, FIREFOX, MOZILLA, GECKO, EDGE, IE, SAFARI -> getGridDriver(browser);
//                case FIREFOX, MOZILLA, GECKO -> getFirefoxDriver();
//                case EDGE -> getEdgeDriver();
//                case IE -> getIEDriver();
//                case SAFARI -> getSafariDriver();
                case BROWSERSTACK -> getBrowserStackDriver(browser);
                case SAUCELABS -> getSauceLabsDriver(browser);
                default -> throw new RuntimeException("No Match Found For The Provided Browser : " + browser);
            };
            DRIVER.set(driver);
        }
        return DRIVER.get();
    }

    private static RemoteWebDriver getBrowserStackDriver(Browsers browser) {
        if (ENABLE_BROWSERSTACK) {
            PropertyReader bStackProps = new PropertyReader("browserstack.properties");
            RemoteWebDriver driver;
            try {
                String BROWSERSTACK_USERNAME = bStackProps.getValue("BROWSERSTACK_USERNAME");
                String BROWSERSTACK_ACCESS_KEY = bStackProps.getValue("BROWSERSTACK_ACCESS_KEY");
                driver = new RemoteWebDriver(
                        new URL("https://" + BROWSERSTACK_USERNAME + ":" + BROWSERSTACK_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub"),
                        CapabilitiesManager.getBrowserCapabilities(browser), true);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return driver;
        } else {
            throw new RuntimeException("Cannot Create Browserstack Driver. Browserstack Is Not Enabled In Configuration File!");
        }
    }

    private static RemoteWebDriver getSauceLabsDriver(Browsers browser) {
        if (ENABLE_SAUCELABS) {
            PropertyReader bLabsProps = new PropertyReader("saucelabs.properties");
            RemoteWebDriver driver;
            try {
                driver = new RemoteWebDriver(
                        new URL(bLabsProps.getValue("SAUCELABS_URL")),
                        CapabilitiesManager.getBrowserCapabilities(browser), true);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return driver;
        } else {
            throw new RuntimeException("Cannot Create SauceLabs Driver. SauceLabs Is Not Enabled In Configuration File!");
        }
    }

    private static RemoteWebDriver getGridDriver(Browsers browser) {
        if (ENABLE_GRID) {
            RemoteWebDriver driver;
            try {
                driver = new RemoteWebDriver(new URL(REMOTE_URL), CapabilitiesManager.getBrowserCapabilities(browser), true);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return driver;
        } else {
            throw new RuntimeException("Cannot Create Selenium Grid Driver. Selenium Grid Is Not Enabled In Configuration File!");
        }

    }

//    private static RemoteWebDriver getChromeDriver() {
//        RemoteWebDriver driver;
//        try {
//            driver = new RemoteWebDriver(new URL(REMOTE_URL), CapabilitiesManager.getBrowserCapabilities(Browsers.CHROME), true);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        return driver;
//    }
//
//    private static RemoteWebDriver getFirefoxDriver() {
//        RemoteWebDriver driver;
//        try {
//            driver = new RemoteWebDriver(new URL(REMOTE_URL), CapabilitiesManager.getBrowserCapabilities(Browsers.FIREFOX), true);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        return driver;
//    }
//
//    private static RemoteWebDriver getEdgeDriver() {
//        RemoteWebDriver driver;
//        try {
//            driver = new RemoteWebDriver(new URL(REMOTE_URL), CapabilitiesManager.getBrowserCapabilities(Browsers.EDGE), true);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        return driver;
//    }
//
//    private static RemoteWebDriver getIEDriver() {
//        RemoteWebDriver driver;
//        try {
//            driver = new RemoteWebDriver(new URL(REMOTE_URL), CapabilitiesManager.getBrowserCapabilities(Browsers.IE), true);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        return driver;
//    }
//
//    private static RemoteWebDriver getSafariDriver() {
//        RemoteWebDriver driver;
//        try {
//            driver = new RemoteWebDriver(new URL(REMOTE_URL), CapabilitiesManager.getBrowserCapabilities(Browsers.SAFARI), true);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        }
//        return driver;
//    }
}
