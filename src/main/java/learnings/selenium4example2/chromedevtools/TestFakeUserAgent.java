package learnings.selenium4example2.chromedevtools;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestFakeUserAgent {

	public static void main(String[] args) {


		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		
		String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36";
		
		devTools.send(Network.setUserAgentOverride(userAgent, Optional.empty(), Optional.empty(), Optional.empty()));
		
		driver.get("https://www.whatismybrowser.com/detect/what-is-my-user-agent");
		

	}

}
