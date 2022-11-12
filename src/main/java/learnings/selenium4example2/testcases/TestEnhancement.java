package learnings.selenium4example2.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestEnhancement {

	public static void main(String[] args) {


		
		WebDriverManager.firefoxdriver().setup();
		FirefoxDriver driver = new FirefoxDriver();
		driver.get("http://google.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement link = driver.findElement(By.linkText("Gmail"));
		
		int x = link.getRect().getX();
		int y = link.getRect().getY();
		
		
		Actions action = new Actions(driver);
		action.moveByOffset(x, y).click().perform();
		

	}

}
