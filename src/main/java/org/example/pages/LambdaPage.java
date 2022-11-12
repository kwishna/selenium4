package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

public class LambdaPage extends BasePage {
    public static void enterUserName(String userEmail) {
        By sso = By.cssSelector("[href='/sso']");
        WebElement userName = getElement(RelativeLocator.with(By.tagName("input")).below(sso));
        userName.sendKeys(userEmail);
    }

    public static void enterPassword(String passWord) {
        By sso = By.cssSelector("[href='/sso']");
        By userName = RelativeLocator.with(By.tagName("input")).below(sso);
        WebElement password = getElement(RelativeLocator.with(By.tagName("input")).below(userName));
        password.sendKeys(passWord);
    }
}