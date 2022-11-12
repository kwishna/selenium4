package org.example.capabilities;

import org.example.enums.Browsers;
import org.example.utilities.PropertyReader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CapabilitiesManager {
    private static final boolean HEADLESS = Boolean.parseBoolean(PropertyReader.getConfig("HEADLESS"));
    private static final boolean MOBILE_EMULATION = Boolean.parseBoolean(PropertyReader.getConfig("MOBILE_EMULATION"));
    private static final boolean EXE_BINARY_ENABLED = Boolean.parseBoolean(PropertyReader.getConfig("EXE_BINARY_ENABLED"));
    private static final boolean ENABLE_BROWSERSTACK = Boolean.parseBoolean(PropertyReader.getConfig("ENABLE_BROWSERSTACK"));
    private static final boolean ENABLE_SAUCELABS = Boolean.parseBoolean(PropertyReader.getConfig("ENABLE_SAUCELABS"));
    private static final String DOWNLOAD_DIR = PropertyReader.getConfig("DOWNLOAD_DIR");
    private static final String EXE_BINARY_PATH = PropertyReader.getConfig("EXE_BINARY_PATH");
    private static final int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertyReader.getConfig("PAGE_LOAD_TIMEOUT"));
    private static final int IMPLICIT_WAIT_TIME = Integer.parseInt(PropertyReader.getConfig("IMPLICIT_WAIT_TIME"));
    private static final double BUILD_NAME = Double.parseDouble(PropertyReader.getConfig("BUILD_NAME"));
    private static final String DRIVER_ARGUMENTS = PropertyReader.getConfig("DRIVER_ARGUMENTS");
    private static final String PROJECT_NAME = PropertyReader.getConfig("PROJECT_NAME");

    static public MutableCapabilities getBrowserCapabilities(Browsers browser) {
        return switch (browser) {
            case CHROME ->
                    CommonCapabilities.addCommonCapabilities(ChromiumCapabilities.addChromiumCapabilities(ChromeCapabilities.getOptions()));
            case FIREFOX, MOZILLA, GECKO -> CommonCapabilities.addCommonCapabilities(FirefoxCapabilities.getOptions());
            case EDGE ->
                    CommonCapabilities.addCommonCapabilities(ChromiumCapabilities.addChromiumCapabilities(EdgeCapabilities.getOptions()));
            case IE -> CommonCapabilities.addCommonCapabilities(IECapabilities.getOptions());
            case SAFARI -> CommonCapabilities.addCommonCapabilities(SafariCapabilities.getOptions());
            default -> throw new RuntimeException("No Capabilities Found For Provided Browser : " + browser);
        };
    }

    private static class CommonCapabilities {
        static MutableCapabilities addCommonCapabilities(AbstractDriverOptions<? extends MutableCapabilities> options) {
            options.setAcceptInsecureCerts(true);
            options.setPageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
            options.setScriptTimeout(Duration.ofSeconds(60));
            options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
            options.setImplicitWaitTimeout(Duration.ofSeconds(IMPLICIT_WAIT_TIME));

            BrowserStackCapabilities.browserstackCapabilities(options);
            SauceLabsCapabilities.sauceLabsCapabilities(options);

            return options;
        }
    }

    private static class BrowserStackCapabilities {
        static AbstractDriverOptions<? extends MutableCapabilities> browserstackCapabilities(AbstractDriverOptions<? extends MutableCapabilities> options) {
            if (ENABLE_BROWSERSTACK) {
                PropertyReader bStackProps = new PropertyReader("browserstack.properties");

                options.setCapability("browserName", bStackProps.getValue("BROWSERSTACK_BROWSER_NAME"));
                options.setCapability("browserVersion", bStackProps.getValue("BROWSERSTACK_BROWSER_VERSION"));

                HashMap<String, Object> bstackOptions = new HashMap<>();
                bstackOptions.put("os", bStackProps.getValue("BROWSERSTACK_OS"));
                bstackOptions.put("osVersion", bStackProps.getValue("BROWSERSTACK_OS_VERSION"));
                bstackOptions.put("projectName", PROJECT_NAME);
                bstackOptions.put("debug", "true");
                bstackOptions.put("video", "true");
                bstackOptions.put("consoleLogs", "info");
                bstackOptions.put("buildName", BUILD_NAME);
                bstackOptions.put("networkLogs", "true");
                bstackOptions.put("seleniumLogs", "true");
                bstackOptions.put("resolution", bStackProps.getValue("BROWSERSTACK_RESOLUTION"));
                options.setCapability("bstack:options", bstackOptions);
            }
            return options;
        }
    }

    private static class SauceLabsCapabilities {
        static AbstractDriverOptions<? extends MutableCapabilities> sauceLabsCapabilities(AbstractDriverOptions<? extends MutableCapabilities> options) {
            if (ENABLE_SAUCELABS) {

                PropertyReader sLabsProps = new PropertyReader("saucelabs.properties");

                options.setCapability("browserName", sLabsProps.getValue("SAUCELABS_BROWSER_NAME"));
                options.setCapability("browserVersion", sLabsProps.getValue("SAUCELABS_BROWSER_VERSION"));
                options.setCapability("platformName", sLabsProps.getValue("SAUCELABS_PLATFORM"));
                options.setCapability("platformName", sLabsProps.getValue("SAUCELABS_PLATFORM"));

                Map<String, Object> sauceOptions = new HashMap<>();
                sauceOptions.put("username", sLabsProps.getValue("SAUCELABS_USERNAME"));
                sauceOptions.put("accessKey", sLabsProps.getValue("SAUCELABS_ACCESS_KEY"));
                sauceOptions.put("name", PROJECT_NAME);
                sauceOptions.put("build", BUILD_NAME);

                options.setCapability("sauce:options", sauceOptions);
            }
            return options;
        }
    }

    private static class ChromiumCapabilities {
        /*
                // SET CAPABILITY
                const capabilities = {
                  'browserName' : 'chrome',
                  'browserVersion' : 'latest',
                  'os' : 'Windows',
                  'osVersion' : '10',
                  'browserstack.user' : 'YOUR_USERNAME',
                  'browserstack.key' : 'YOUR_ACCESS_KEY',
                  'build': 'Location pop-ups in Browsers ',
                  'name': 'location popups disabled in Chrome',

                  // SET Chrome options
                  'goog:chromeOptions': {
                    prefs: {
                      // 0 - Default, 1 - Allow, 2 - Block
                      'profile.managed_default_content_settings.geolocation' : 1
                    }
                  }

                   // SET Firefox options
                   'moz:firefoxOptions': {
                     prefs: {
                       'dom.disable_beforeunload': true,
                       'dom.webnotifications.enabled': false,
                       'geo.enabled': true,
                       'geo.provider.use_corelocation': false,
                       'geo.prompt.testing': true,
                       'geo.prompt.testing.allow': true,
                       'devtools.debugger.remote-enabled': true,
                       'devtools.debugger.prompt-connection': false,
                       'devtools.chrome.enabled': true
                     }
                   }
                   // SET Edge options
                   'ms:edgeOptions': {
                     prefs: {
                       // 0 - Default, 1 - Allow, 2 - Block
                       'profile.managed_default_content_settings.geolocation' : 1
                     }
                   }
                }
https://chromedriver.chromium.org/capabilities
*/
        static AbstractDriverOptions<? extends MutableCapabilities> addChromiumCapabilities(ChromiumOptions<? extends ChromiumOptions<?>> options) {

            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", DOWNLOAD_DIR);
            chromePrefs.put("plugins.always_open_pdf_externally", true);
            chromePrefs.put("credentials_enable_service", false);
            options.setExperimentalOption("prefs", chromePrefs);

//            Map<String, Object> perfLogPrefs = new HashMap<>();
//            perfLogPrefs.put("traceCategories", "browser,devtools.timeline,devtools");
//            perfLogPrefs.put("enableNetwork", true);
//            options.setExperimentalOption("perfLoggingPrefs", perfLogPrefs);

            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//            options.setExperimentalOption("useAutomationExtension", false); // Deprecated!
            options.addArguments("disable-infobars");

            if (EXE_BINARY_ENABLED) {
                if (Files.isExecutable(Paths.get(EXE_BINARY_PATH))) {
                    options.setBinary(EXE_BINARY_PATH);
                } else {
                    throw new RuntimeException("Error: Binary File Path " + EXE_BINARY_PATH);
                }
            }

            if (MOBILE_EMULATION) {
                Map<String, Object> deviceMetrics = new HashMap<>();
                deviceMetrics.put("width", Integer.parseInt("SCREEN_WIDTH"));
                deviceMetrics.put("height", Integer.parseInt("SCREEN_HEIGHT"));
                deviceMetrics.put("pixelRatio", 3.0);

                Map<String, Object> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceMetrics", deviceMetrics);
                mobileEmulation.put("userAgent", "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53");

                options.setExperimentalOption("mobileEmulation", mobileEmulation);

//                Map<String, Object> chromeOptions = new HashMap<>();
//                chromeOptions.put("mobileEmulation", mobileEmulation);
//                options.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            }

            options.addArguments(DRIVER_ARGUMENTS.split(";"));
            return options;
        }
    }

    private static class ChromeCapabilities {
        static ChromeOptions getOptions() {
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(HEADLESS);
//            options.setLogLevel(ChromeDriverLogLevel.ALL);
            options.setLogLevel(ChromeDriverLogLevel.WARNING);
            options.setPlatformName(Platform.WINDOWS.name());

            return options;
        }
    }

    private static class EdgeCapabilities {
        static EdgeOptions getOptions() {
            EdgeOptions options = new EdgeOptions();
            options.setHeadless(HEADLESS);
            options.setPlatformName(Platform.WINDOWS.name());
            return options;
        }
    }

    private static class FirefoxCapabilities {
        static FirefoxOptions getOptions() {

            FirefoxOptions options = new FirefoxOptions();
            options.setHeadless(HEADLESS);
            options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
            options.setPlatformName(Platform.WINDOWS.name());

            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.download.dir", DOWNLOAD_DIR);
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain,application/octet-stream,application/pdf,application/x-pdf,application/vnd.pdf,application/zip,application/x-rar-compressed,application/x-gzip,application/msword");
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            profile.setPreference("browser.helperApps.alwaysAsk.force", false);

            if (EXE_BINARY_ENABLED) {
                if (Files.isExecutable(Paths.get(EXE_BINARY_PATH))) {
                    options.setBinary(EXE_BINARY_PATH);
                } else {
                    throw new RuntimeException("Error: Binary File Path " + EXE_BINARY_PATH);
                }
            }

            if (MOBILE_EMULATION) {
                profile.setPreference("general.useragent.override", "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53");
            }
            options.setProfile(profile);
            options.addArguments(DRIVER_ARGUMENTS.split(";"));

            return options;
        }
    }

    private static class IECapabilities {
        static InternetExplorerOptions getOptions() {
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.setPlatformName(Platform.WINDOWS.name());
            options.setCapability("requireWindowFocus", true);
            return options;
        }
    }

    private static class SafariCapabilities {
        static SafariOptions getOptions() {
            SafariOptions options = new SafariOptions();
            options.setUseTechnologyPreview(true);
            options.setPlatformName(Platform.MAC.name());
            return options;
        }
    }
}
