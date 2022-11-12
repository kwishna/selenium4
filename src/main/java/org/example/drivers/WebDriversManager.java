package org.example.drivers;

import org.example.drivers.managers.BasicDriverManager;
import org.example.drivers.managers.BonagarciaDriverManager;
import org.example.drivers.managers.RemoteDriverManager;
import org.example.drivers.managers.ServiceDriverManager;
import org.example.enums.Browsers;
import org.example.enums.DriverType;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class WebDriversManager {
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final String BROWSER_NAME = PropertyReader.getConfig("BROWSER_NAME");
    private static final String DRIVER_TYPE = PropertyReader.getConfig("DRIVER_TYPE");

//    private static final List<WebDriver> wdList = new ArrayList<>();
//    static {
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            wdList.stream().filter(Objects::nonNull).forEach(WebDriver::quit);
//        }));
//    }

    private WebDriversManager() {
    }

    private static Browsers getBrowser() {
        return Arrays.stream(Browsers.values()).filter(browsr -> browsr.name().equalsIgnoreCase(BROWSER_NAME)).findFirst().orElse(Browsers.CHROME);
    }

    private static DriverType getDriverType() {
        return Arrays.stream(DriverType.values()).filter(type -> type.name().contains(DRIVER_TYPE.toUpperCase())).findFirst().orElse(DriverType.BASIC);
    }

    public static WebDriver getDriver() {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            WebDriver driver = switch (getDriverType()) {
                case BASIC -> BasicDriverManager.getDriver(getBrowser());
                case SERVICE -> ServiceDriverManager.getDriver(getBrowser());
                case REMOTE, GRID -> RemoteDriverManager.getDriver(getBrowser());
                case BONAGARCIA -> BonagarciaDriverManager.getDriver(getBrowser());
            };
            setDriver(driver);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            setDriver(null);
        }
    }
}
