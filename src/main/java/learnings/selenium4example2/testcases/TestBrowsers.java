package learnings.selenium4example2.testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBrowsers {

	public static String browser = "firefox";
	public static WebDriver driver;

	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver();
	

		if (browser.equals("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().driverVersion("0.30.0").setup();
			 driver = new FirefoxDriver();

		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.get("http://way2automation.com");
		System.out.println(driver.getTitle());
		driver.quit();

	}

}
