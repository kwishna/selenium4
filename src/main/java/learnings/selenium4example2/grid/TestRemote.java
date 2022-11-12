package learnings.selenium4example2.grid;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestRemote {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {


		ChromeOptions opt = new ChromeOptions();
		
		WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444"),opt);
		driver.get("http://google.com");
		driver.findElement(By.name("q")).sendKeys("Hello Grid !!!");
		
		driver.quit();
		

	}

}
