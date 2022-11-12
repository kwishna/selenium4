package rough;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v106.browser.Browser;
import org.openqa.selenium.devtools.v106.emulation.Emulation;
import org.openqa.selenium.devtools.v106.log.Log;
import org.openqa.selenium.net.NetworkUtils;

import java.util.Map;
import java.util.Optional;

public class Rough {
    public static void main(String[] args) {
        ChromeDriverManager.chromedriver().setup();

        ChromeOptions ops = new ChromeOptions();
//        ops.addArguments("start-maximized", "--no-sandbox", "--headless");
        ops.addArguments("start-maximized", "--no-sandbox");

        ChromeDriver driver = new ChromeDriver(ops);
        DevTools devTool = driver.getDevTools();
        devTool.createSession();

        devTool.send(Log.enable());
        devTool.addListener(Log.entryAdded(), logEntry -> {
            // More Listeners At org.openqa.selenium.devtools.v106 - Check For Classes Which Has Method Which Returns 'Event' Object.
            System.out.println("Log Text => " + logEntry.getText());
            System.out.println("Log Level => " + logEntry.getLevel());
            System.out.println("Log Source => " + logEntry.getSource());
            System.out.println("Url => " + logEntry.getUrl().orElse(null));
        });

        devTool.send(Emulation.setDeviceMetricsOverride(
                500,
                600,
                50,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));

        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", Map.of(
                "width", 500,
                "height", 600,
                "deviceScaleFactor", 50,
                "mobile", true
        ));

        driver.get("http://the-internet.herokuapp.com/");

        Browser.GetVersionResponse browser = devTool.send(Browser.getVersion());
        System.out.println("Browser Version => " + browser.getProduct());
        System.out.println("User Agent => " + browser.getUserAgent());

//        NetworkUtils networkUtils = new NetworkUtils();
//        System.out.println(networkUtils.obtainLoopbackIp4Address());
//        System.out.println(networkUtils.getPrivateLocalAddress());
//        System.out.println(networkUtils.getIp4NonLoopbackAddressOfThisMachine());
//        System.out.println(networkUtils.getNonLoopbackAddressOfThisMachine());
        driver.quit();
    }
}
