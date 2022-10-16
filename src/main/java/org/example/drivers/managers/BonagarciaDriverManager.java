package org.example.drivers.managers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.enums.Browsers;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class BonagarciaDriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = ThreadLocal.withInitial(() -> null);
    private static final ThreadLocal<WebDriverManager> WD_MANAGER = ThreadLocal.withInitial(() -> null);

    public static WebDriver startBonagarciaDriver(Browsers browser) {
        if (DRIVER.get() == null) {
            WebDriver driver = switch (browser) {
                case CHROME -> startDriver(getChromeDriver());
                case FIREFOX, MOZILLA, GECKO -> startDriver(getFirefoxDriver());
                case EDGE -> startDriver(getEdgeDriver());
                case IE -> startDriver(getIEDriver());
                case SAFARI -> startDriver(getSafariDriver());
                default -> throw new RuntimeException("No Match Found For The Provided Browser : " + browser);
            };
            DRIVER.set(driver);
        }
        return DRIVER.get();
    }

    public static void setUpBonagarciaDriver(Browsers browser) {
        if (WD_MANAGER.get() == null) {
            WebDriverManager wdManager = switch (browser) {
                case CHROME -> setUpBonagarciaBrowser(getChromeDriver());
                case FIREFOX, MOZILLA, GECKO -> setUpBonagarciaBrowser(getFirefoxDriver());
                case EDGE -> setUpBonagarciaBrowser(getEdgeDriver());
                case IE -> setUpBonagarciaBrowser(getIEDriver());
                case SAFARI -> setUpBonagarciaBrowser(getSafariDriver());
                default -> throw new RuntimeException("No Match Found For The Provided Browser : " + browser);
            };
            WD_MANAGER.set(wdManager);
        }
    }

    private static WebDriver startDriver(WebDriverManager manager) {
        WebDriver driver;
        try {
            driver = setUpBonagarciaBrowser(manager).create();
        } catch (Exception e) {
            throw new RuntimeException("Error Creating Bonagarcia Driver! " + Arrays.toString(e.getStackTrace()));
        }
        return driver;
    }

    private static WebDriverManager setUpBonagarciaBrowser(WebDriverManager manager) {
        manager.timeout(60);
        manager.arch64();
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
