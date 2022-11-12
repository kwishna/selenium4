package learnings.selenium4example2.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestWebElements {

	public static void main(String[] args) throws InterruptedException {


		
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver();
		driver.get("http://gmail.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		 Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			       .withTimeout(Duration.ofSeconds(30))
			       .pollingEvery(Duration.ofSeconds(5))
			       .withMessage("Time out as the condition is not met")
			       .ignoring(NoSuchElementException.class);
		//driver.manage().window().minimize();
		WebElement username = driver.findElement(By.id("identifierId"));
		username.sendKeys("trainer@way2automation.com");
		//driver.manage().window().fullscreen();
		driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/span")).click();
		//Thread.sleep(10000);							
		//driver.findElement(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input")).sendKeys("asdfsf");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"password\"]/div[1]/div/div[1]/input"))).sendKeys("sadfdf");
		driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button")).click();
		
		System.out.println(driver.findElement(By.xpath("//*[@id=\"view_container\"]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div[1]/div[2]/div[2]/span")).getText());

	}

}
