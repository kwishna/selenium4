package org.example.drivers.service;

import org.example.enums.Browsers;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.net.URL;

public class ServiceManager {

    private static final ThreadLocal<DriverService> DRIVER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);

    private static DriverService getDriverService(Browsers browser) {
        if (DRIVER_THREAD_LOCAL.get() == null) {
            DriverService.Builder builder = BrowserService.getBrowserService(browser);
            DriverService service = builder.build();
            DRIVER_THREAD_LOCAL.set(service);
        }
        return DRIVER_THREAD_LOCAL.get();
    }

    public static URL startBrowserService(Browsers browser) {
        try {
            DriverService service = getDriverService(browser);
            if (!service.isRunning()) {
                service.start();
            }
            return service.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopBrowserService() {
        if (DRIVER_THREAD_LOCAL.get() != null) {
            DriverService service = DRIVER_THREAD_LOCAL.get();
            if (service.isRunning()) {
                service.stop();
            }
        }
    }
}
