package org.example.drivers.service;

import org.example.enums.Browsers;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriverService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import static org.example.utilities.Utilities.timeStamp;

class BrowserService {
    private static final boolean EXE_BINARY_ENABLED = Boolean.parseBoolean(PropertyReader.getConfig("EXE_BINARY_ENABLED"));
    private static final String EXE_BINARY_PATH = PropertyReader.getConfig("EXE_BINARY_PATH");
    private static final String CHROMEDRIVER_PATH = PropertyReader.getConfig("CHROMEDRIVER_PATH");
    private static final String FIREFOXDRIVER_PATH = PropertyReader.getConfig("FIREFOXDRIVER_PATH");
    private static final String EDGEDRIVER_PATH = PropertyReader.getConfig("EDGEDRIVER_PATH");
    private static final String IEDRIVER_PATH = PropertyReader.getConfig("IEDRIVER_PATH");

    public static DriverService.Builder getBrowserService(Browsers browser) {
        return switch (browser) {
            case CHROME -> CommonDriverService.addCommonProperties(BrowserService.ChromeBrowserService.getService());
            case FIREFOX, MOZILLA, GECKO ->
                    CommonDriverService.addCommonProperties(BrowserService.FirefoxBrowserService.getService());
            case EDGE -> CommonDriverService.addCommonProperties(BrowserService.EdgeBrowserService.getService());
            case IE -> CommonDriverService.addCommonProperties(BrowserService.IEBrowserService.getService());
            case SAFARI -> CommonDriverService.addCommonProperties(BrowserService.SafariBrowserService.getService());
            default -> throw new RuntimeException("No Capabilities Found For Provided Browser : " + browser);
        };
    }

    private static class CommonDriverService {
        static DriverService.Builder addCommonProperties(DriverService.Builder service) {
            service.usingAnyFreePort();
            service.withLogFile(Paths.get(System.getProperty("user.dir"), "/log/", timeStamp() + ".log").toFile());
            service.withTimeout(Duration.ofSeconds(60));
            return service;
        }
    }

    private static class ChromeBrowserService {
        static ChromeDriverService.Builder getService() {
            ChromeDriverService.Builder service = new ChromeDriverService.Builder();
            service.usingDriverExecutable(Paths.get(CHROMEDRIVER_PATH).toFile());
            service.withLogLevel(ChromeDriverLogLevel.ALL);
            service.withAppendLog(false);
            service.withSilent(false);
            service.withVerbose(true);
            return service;
        }
    }

    private static class EdgeBrowserService {
        static EdgeDriverService.Builder getService() {
            EdgeDriverService.Builder service = new EdgeDriverService.Builder();
            service.usingDriverExecutable(Paths.get(EDGEDRIVER_PATH).toFile());
            service.withSilent(false);
            service.withVerbose(true);
            return service;
        }
    }

    private static class FirefoxBrowserService {
        static GeckoDriverService.Builder getService() {
            GeckoDriverService.Builder service = new GeckoDriverService.Builder();
            service.usingDriverExecutable(Paths.get(FIREFOXDRIVER_PATH).toFile());
            if (EXE_BINARY_ENABLED) {
                final Path path = Paths.get(EXE_BINARY_PATH);
                if (Files.isExecutable(path)) {
                    service.usingFirefoxBinary(new FirefoxBinary(path.toFile()));
                } else {
                    throw new RuntimeException("Error: Binary File Path " + EXE_BINARY_PATH);
                }
            }
            return service;
        }
    }

    private static class IEBrowserService {
        static InternetExplorerDriverService.Builder getService() {
            InternetExplorerDriverService.Builder service = new InternetExplorerDriverService.Builder();
            service.usingDriverExecutable(Paths.get(IEDRIVER_PATH).toFile());
            service.withLogLevel(InternetExplorerDriverLogLevel.DEBUG);
            service.withSilent(false);
            return service;
        }
    }

    private static class SafariBrowserService {
        static SafariDriverService.Builder getService() {
            SafariDriverService.Builder service = new SafariDriverService.Builder();
            service.usingDriverExecutable(Paths.get(IEDRIVER_PATH).toFile());
            return service;
        }
    }
}
