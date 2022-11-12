package learnings.selenium4example2.chromedevtools;

import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v96.network.Network;
import org.openqa.selenium.devtools.v96.network.model.ConnectionType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestNetworkSpeed {

	public static void main(String[] args) {


		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
	
		
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(false, 100, 20000, 10000, Optional.of(ConnectionType.CELLULAR4G)));
		
		driver.get("http://way2automation.com");

	}

}
