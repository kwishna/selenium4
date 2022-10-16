package org.example.drivers.managers;

import org.example.capabilities.CapabilitiesManager;
import org.example.enums.Browsers;
import org.example.drivers.service.ServiceManager;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ServiceDriverManager {

    private static final ThreadLocal<RemoteWebDriver> DRIVER = ThreadLocal.withInitial(() -> null);

    private static RemoteWebDriver getServiceDriver(Browsers browser) {
        if (DRIVER.get() == null) {
            DRIVER.set(new RemoteWebDriver(ServiceManager.startBrowserService(browser), CapabilitiesManager.getBrowserCapabilities(browser)));
        }
        return DRIVER.get();
    }
}
