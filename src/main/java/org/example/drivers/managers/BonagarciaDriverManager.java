package org.example.drivers.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.capabilities.CapabilitiesManager;
import org.example.enums.Browsers;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class BonagarciaDriverManager {
//    private static final ThreadLocal<WebDriver> DRIVER = ThreadLocal.withInitial(() -> null);
//    private static final ThreadLocal<WebDriverManager> WD_MANAGER = ThreadLocal.withInitial(() -> null);

    public static WebDriver getDriver(Browsers browser) {
//        if (DRIVER.get() == null) {
        System.out.println("Using BonagarciaDriverManager!");
        WebDriver driver = switch (browser) {
            case CHROME -> startDriver(getChromeDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case FIREFOX, MOZILLA, GECKO ->
                    startDriver(getFirefoxDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case EDGE -> startDriver(getEdgeDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case IE -> startDriver(getIEDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case SAFARI -> startDriver(getSafariDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            default -> throw new RuntimeException("No Match Found For The Provided Browser : " + browser);
        };
//            DRIVER.set(driver);
//        }
//        return DRIVER.get();
        return driver;
    }

    public static void setUpDriver(Browsers browser) {
//        if (WD_MANAGER.get() == null) {
        WebDriverManager wdManager = switch (browser) {
            case CHROME ->
                    setUpBonagarciaBrowser(getChromeDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case FIREFOX, MOZILLA, GECKO ->
                    setUpBonagarciaBrowser(getFirefoxDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case EDGE -> setUpBonagarciaBrowser(getEdgeDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case IE -> setUpBonagarciaBrowser(getIEDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            case SAFARI ->
                    setUpBonagarciaBrowser(getSafariDriver(), CapabilitiesManager.getBrowserCapabilities(browser));
            default -> throw new RuntimeException("No Match Found For The Provided Browser : " + browser);
        };
//            WD_MANAGER.set(wdManager);
//        }
    }

    private static WebDriver startDriver(WebDriverManager manager, MutableCapabilities capabilities) {
        WebDriver driver;
        try {
            driver = setUpBonagarciaBrowser(manager, capabilities).create();
        } catch (Exception e) {
            throw new RuntimeException("Error Creating Bonagarcia Driver! " + Arrays.toString(e.getStackTrace()));
        }
        return driver;
    }

    private static WebDriverManager setUpBonagarciaBrowser(WebDriverManager manager, MutableCapabilities capabilities) {
        manager.timeout(60);
        manager.arch64();
        manager.capabilities(capabilities);
        manager.setup();
        return manager;
    }

    private static WebDriverManager getChromeDriver() {
        return WebDriverManager.chromedriver();
    }

    private static WebDriverManager getFirefoxDriver() {
        return WebDriverManager.firefoxdriver();
    }

    private static WebDriverManager getEdgeDriver() {
        return WebDriverManager.edgedriver();
    }

    private static WebDriverManager getIEDriver() {
        return WebDriverManager.iedriver();
    }

    private static WebDriverManager getSafariDriver() {
        return WebDriverManager.safaridriver();
    }

}
