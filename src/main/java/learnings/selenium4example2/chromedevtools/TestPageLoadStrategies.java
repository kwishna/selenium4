package learnings.selenium4example2.chromedevtools;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestPageLoadStrategies {

	public static void main(String[] args) {

		ChromeOptions opt = new ChromeOptions();
		//opt.setPageLoadStrategy(PageLoadStrategy.NORMAL);//6132
		//opt.setPageLoadStrategy(PageLoadStrategy.EAGER);//5859
		opt.setPageLoadStrategy(PageLoadStrategy.NONE);//6175
		
		long start = System.currentTimeMillis();
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver(opt);
		driver.get("http://flipkart.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("/html/body/div[2]/div/div/button")).click();
		driver.quit();
		long stop = System.currentTimeMillis();
		System.out.println(stop-start);
		
	}

}
