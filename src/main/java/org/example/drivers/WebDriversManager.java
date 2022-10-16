package org.example.drivers;

import org.openqa.selenium.WebDriver;

public class WebDriversManager {
    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    public static WebDriver getDriver() {
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }
}
