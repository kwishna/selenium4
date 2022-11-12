package org.example.pages;

import com.google.common.net.MediaType;
import org.example.drivers.WebDriversManager;
import org.example.enums.DriverType;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.net.DefaultNetworkInterfaceProvider;
import org.openqa.selenium.net.NetworkInterfaceProvider;
import org.openqa.selenium.net.NetworkUtils;
import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class Selenium4Page extends BasePage {

    public void openUrl(String url) {
        open(url);
    }

    public void clickSlowCalculator() {
        click(By.cssSelector("a[href='slow-calculator.html']"));
    }

    public void userQuitsTheBrowser() {
        WebDriversManager.quitDriver();
    }

    public void userTakesScreenshotOfCalculatorElement() {
        takeElementScreenshot(By.cssSelector("#calculator"));
        fullPageScreenshot();
    }

    public void userOpensWindowOnTheBrowser(String url) {
        WebDriversManager.getDriver().switchTo().newWindow(WindowType.WINDOW);
        navigateTo(url);
    }

    public void userVerifyTotalTabsOpenedAre(int num) {
        assertThat(allWindows(), hasSize(num));
    }

    public void userOpensTabOnTheBrowser(String url) {
        WebDriversManager.getDriver().switchTo().newWindow(WindowType.TAB);
        navigateTo(url);
    }

    public void userPrintTheLocationOfTheObject() {
        WebElement logo = getElement(By.cssSelector("a img[src='img/hands-on-icon.png']"));
        System.out.println("┌───────────────────┐");
        System.out.println("│ Height: " + logo.getRect().getDimension().getHeight());
        System.out.println("│ Height: " + logo.getRect().getDimension().getWidth());
        System.out.println("│ X-Location: " + logo.getRect().getX());
        System.out.println("│ Y-Location: " + logo.getRect().getY());
        System.out.println("└───────────────────┘");
    }

    public void loginUsingRelativeLocators() {

        LambdaPage.enterUserName("userName@user.com");
        LambdaPage.enterPassword("Password@321");
        WebElement submit = getElement(By.cssSelector("#login-button"));

        sleep(15000);

        submit.click();
    }


    public void networkUtils() {
        NetworkUtils networkUtils = new NetworkUtils();
        System.out.println(networkUtils.obtainLoopbackIp4Address());
        System.out.println(networkUtils.getPrivateLocalAddress());
        System.out.println(networkUtils.getIp4NonLoopbackAddressOfThisMachine());
        System.out.println(networkUtils.getNonLoopbackAddressOfThisMachine());
    }

    public void pointerPen() {
        WebElement pointerArea = WebDriversManager.getDriver().findElement(By.id("pointer"));
        new Actions(WebDriversManager.getDriver()).setActivePointer(PointerInput.Kind.PEN, "default pen").moveToElement(pointerArea).clickAndHold().moveByOffset(2, 2).release().perform();

        // ------------------------

        WebElement pointer = WebDriversManager.getDriver().findElement(By.id("pointer"));
        PointerInput pen = new PointerInput(PointerInput.Kind.PEN, "default pen");
        PointerInput.PointerEventProperties eventProperties = PointerInput.eventProperties().setTiltX(-72).setTiltY(9).setTwist(86);
        PointerInput.Origin origin = PointerInput.Origin.fromElement(pointer);

        Sequence actionListPen = new Sequence(pen, 0).addAction(pen.createPointerMove(Duration.ZERO, origin, 0, 0)).addAction(pen.createPointerDown(0)).addAction(pen.createPointerMove(Duration.ZERO, origin, 2, 2, eventProperties)).addAction(pen.createPointerUp(0));

        ((RemoteWebDriver) WebDriversManager.getDriver()).perform(Collections.singletonList(actionListPen));
    }


    public void scrollByAmount() {
        //www.selenium.dev/documentation/webdriver/actions_api/wheel/
        // -------- Scroll by given amount
        WebElement footer = WebDriversManager.getDriver().findElement(By.tagName("footer"));
        int deltaY = footer.getRect().y;
        new Actions(WebDriversManager.getDriver()).scrollByAmount(0, deltaY).perform();

        // ---------- Scroll from an element by a given amount
        WebElement iframe = WebDriversManager.getDriver().findElement(By.tagName("iframe"));
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(iframe);
        new Actions(WebDriversManager.getDriver()).scrollFromOrigin(scrollOrigin, 0, 200).perform();

        // ----------- Scroll from an element with an offset
        WebElement footer1 = WebDriversManager.getDriver().findElement(By.tagName("footer"));
        WheelInput.ScrollOrigin scrollOrigin1 = WheelInput.ScrollOrigin.fromElement(footer1, 0, -50);
        new Actions(WebDriversManager.getDriver()).scrollFromOrigin(scrollOrigin1, 0, 200).perform();

        // ----------- Scroll from a offset of origin (element) by given amount
        WheelInput.ScrollOrigin scrollOrigin2 = WheelInput.ScrollOrigin.fromViewport(10, 10);
        new Actions(WebDriversManager.getDriver()).scrollFromOrigin(scrollOrigin2, 0, 200).perform();
    }


}
