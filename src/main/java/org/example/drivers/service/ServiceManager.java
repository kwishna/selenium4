package org.example.drivers.service;

import org.example.enums.Browsers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ServiceManager {

    private static DriverService service;

    private static DriverService getDriverService(Browsers browser) {
        return BrowserService.getBrowserService(browser).build();
    }

    public static URL startBrowserService(Browsers browser) {
        try {
            service = getDriverService(browser);
            if (!service.isRunning()) {
                service.start();
            }
            return service.getUrl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopBrowserService() {
        if (service != null && service.isRunning()) {
            service.close();
        }
    }
}
