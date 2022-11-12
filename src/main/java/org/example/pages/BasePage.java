package org.example.pages;

import org.example.drivers.WebDriversManager;
import org.example.utilities.Utilities;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class BasePage {
    private static final long TIMEOUT = 5L;

    protected static <WebDriver, V> V fluentWait(org.openqa.selenium.WebDriver input, long timeOutInSeconds, String errMessage, Function<? super org.openqa.selenium.WebDriver, V> isTrue) {
        return new FluentWait<>(input).ignoreAll(Collections.singleton(WebDriverException.class)).pollingEvery(Duration.of(1, ChronoUnit.SECONDS)).withTimeout(Duration.ofSeconds(timeOutInSeconds)).withMessage(errMessage).until(isTrue);
    }

    protected static <WebDriver, R> R explicitWait(org.openqa.selenium.WebDriver driver, long timeOutInSeconds, String message, Function<org.openqa.selenium.WebDriver, R> fun) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds)).withMessage(message).until(fun);
        } catch (Exception e) {
            return null;
        }
    }

    protected static <WebDriver, R> R explicitWait(long timeOutInSeconds, String message, Function<org.openqa.selenium.WebDriver, R> fun) {
        try {
            return new WebDriverWait(WebDriversManager.getDriver(), Duration.ofSeconds(timeOutInSeconds)).withMessage(message).until(fun);
        } catch (Exception e) {
            return null;
        }
    }

    protected static <WebDriver, R> R explicitWait(String message, Function<org.openqa.selenium.WebDriver, R> fun) {
        try {
            return new WebDriverWait(WebDriversManager.getDriver(), Duration.ofSeconds(TIMEOUT)).withMessage(message).until(fun);
        } catch (Exception e) {
            return null;
        }
    }

    protected static <T, V> V fluentWaitGeneric(T input, Function<? super T, V> isTrue, String errMessage) {
        return new FluentWait<>(input).ignoreAll(Collections.singleton(Exception.class)).pollingEvery(Duration.of(2, ChronoUnit.SECONDS)).withTimeout(Duration.ofSeconds(10)).withMessage(errMessage).until(isTrue);
    }

    protected static WebElement waitForPresence(By byObj, long timeout) {
        return explicitWait(timeout, "Element Not Present : " + byObj.toString(), ExpectedConditions.presenceOfElementLocated(byObj));
    }

    protected static WebElement waitForVisible(By byObj, long timeout) {
        return explicitWait(timeout, "Element Not Visible : " + byObj.toString(), ExpectedConditions.visibilityOfElementLocated(byObj));
    }

    protected static WebElement waitForVisible(WebElement element, long timeout) {
        return explicitWait(timeout, "Element Not Visible : " + element, ExpectedConditions.visibilityOf(element));
    }

    protected static boolean waitForInvisible(By byObj, long timeout) {
        return Boolean.TRUE.equals(explicitWait(timeout, "Element Not Invisible : " + byObj.toString(), ExpectedConditions.invisibilityOfElementLocated(byObj)));
    }

    protected static boolean waitForInvisible(WebElement element, long timeout) {
        return Boolean.TRUE.equals(explicitWait(timeout, "Element Not Invisible : " + element, ExpectedConditions.stalenessOf(element)));
    }

    protected static WebElement waitForClickable(By byObj, long timeout) {
        return explicitWait(timeout, "Element Not Clickable : " + byObj.toString(), ExpectedConditions.elementToBeClickable(byObj));
    }

    protected static WebElement waitForClickable(WebElement element, long timeout) {
        return explicitWait(timeout, "Element Not Clickable : " + element, ExpectedConditions.elementToBeClickable(element));
    }

    protected static boolean isVisible(By byObj) {
        WebElement element = waitForVisible(byObj, TIMEOUT);
        return element != null && element.isDisplayed();
    }

    protected static boolean isVisible(WebElement element) {
        WebElement el = waitForVisible(element, TIMEOUT);
        return el != null && el.isDisplayed();
    }

    protected static boolean isEnabled(By byObj) {
        WebElement element = waitForVisible(byObj, TIMEOUT);
        return element != null && element.isEnabled();
    }

    protected static boolean isEnabled(WebElement element) {
        WebElement el = waitForVisible(element, TIMEOUT);
        return el != null && el.isEnabled();
    }

    protected static boolean isSelected(By byObj) {
        WebElement element = waitForVisible(byObj, TIMEOUT);
        return element != null && element.isSelected();
    }

    protected static boolean isSelected(WebElement element) {
        WebElement el = waitForVisible(element, TIMEOUT);
        return el != null && element.isSelected();
    }

    protected static boolean isNotVisible(By byObj) {
        return waitForInvisible(byObj, TIMEOUT);
    }

    protected static boolean isNotVisible(WebElement element) {
        return waitForInvisible(element, TIMEOUT);
    }

    protected static void sendKeys(By byObj, CharSequence... string) {
        WebElement element = waitForVisible(byObj, TIMEOUT);
        element.sendKeys(string);
    }

    protected static void sendKeys(WebElement element, CharSequence... string) {
        WebElement el = waitForVisible(element, TIMEOUT);
        el.sendKeys(string);
    }

    protected static void clear(By byObj) {
        WebElement element = getElement(byObj);
        element.clear();
    }

    protected static void clear(WebElement element) {
        WebElement el = waitForVisible(element, TIMEOUT);
        el.clear();
    }

    protected static void click(By byObj) {
        WebElement el = getElement(byObj);
        el.click();
    }

    protected static void click(WebElement element) {
        WebElement el = waitForClickable(element, TIMEOUT);
        el.click();
    }

    protected static void submit(By byObj) {
        WebElement el = getElement(byObj);
        el.submit();
    }

    protected static void submit(WebElement element) {
        WebElement el = waitForClickable(element, TIMEOUT);
        el.submit();
    }

    protected static WebElement getElement(By byObj) {
        return waitForClickable(byObj, TIMEOUT);
    }

    protected static List<WebElement> getElements(By byObj) {

        return WebDriversManager.getDriver().findElements(byObj);
    }

    protected static WebElement getElement(SearchContext ctx, By byObj) {
        return ctx.findElement(byObj);
    }

    protected static List<WebElement> getElements(SearchContext ctx, By byObj) {
        return ctx.findElements(byObj);
    }

    protected static String text(By byObj) {
        WebElement el = waitForClickable(byObj, TIMEOUT);
        return el.getText();
    }

    protected static String text(WebElement element) {
        WebElement el = waitForClickable(element, TIMEOUT);
        return el.getText();
    }

    protected static String texts(By byObj) {
        WebElement el = waitForPresence(byObj, TIMEOUT);
        return el.getText();
    }

    protected static List<String> texts(List<WebElement> element) {
        return element.stream().map(WebElement::getText).toList();
    }

    protected static String cssValue(By byObj, String cssKey) {
        WebElement el = waitForVisible(byObj, TIMEOUT);
        return el.getCssValue(cssKey);
    }

    protected static String cssValue(WebElement element, String cssKey) {
        WebElement el = waitForVisible(element, TIMEOUT);
        return el.getCssValue(cssKey);
    }

    protected static String attribute(By byObj, String attr) {
        WebElement el = waitForVisible(byObj, TIMEOUT);
        return el.getAttribute(attr);
    }

    protected static String attribute(WebElement element, String attr) {
        WebElement el = waitForVisible(element, TIMEOUT);
        return el.getAttribute(attr);
    }

    protected static String className(By byObj) {
        WebElement el = waitForVisible(byObj, TIMEOUT);
        return el.getAttribute("class");
    }

    protected static String className(WebElement element) {
        WebElement el = waitForVisible(element, TIMEOUT);
        return el.getAttribute("class");
    }

    protected static String title() {
        return WebDriversManager.getDriver().getTitle();
    }

    protected static String url() {
        return WebDriversManager.getDriver().getCurrentUrl();
    }

    protected static Set<String> allWindows() {
        return WebDriversManager.getDriver().getWindowHandles();
    }

    protected static String currentWindow() {
        return WebDriversManager.getDriver().getWindowHandle();
    }

    protected static void navigateTo(String url) {
        WebDriversManager.getDriver().navigate().to(url);
    }

    protected static void reload() {
        WebDriversManager.getDriver().navigate().refresh();
    }

    protected static void forward() {
        WebDriversManager.getDriver().navigate().forward();
    }

    protected static void back() {
        WebDriversManager.getDriver().navigate().back();
    }

    protected static void open(String url) {
        WebDriversManager.getDriver().get(url);
    }

    protected static void clearCookies() {
        WebDriversManager.getDriver().manage().deleteAllCookies();
    }

    protected static void addCookies(Cookie cookie) {
        WebDriversManager.getDriver().manage().addCookie(cookie);
    }

    protected static Set<Cookie> cookies() {
        return WebDriversManager.getDriver().manage().getCookies();
    }

    protected static void implicitlyWait(Duration timeout) {
        WebDriversManager.getDriver().manage().timeouts().implicitlyWait(timeout);
    }

    protected static void pageLoadTimeout(Duration timeout) {
        WebDriversManager.getDriver().manage().timeouts().pageLoadTimeout(timeout);
    }

    protected static void scriptTimeout(Duration timeout) {
        WebDriversManager.getDriver().manage().timeouts().scriptTimeout(timeout);
    }

    protected static void takeElementScreenshot(By byObj) {
        byte[] file = WebDriversManager.getDriver().findElement(byObj).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(System.getProperty("user.dir"), "media", Utilities.timeStamp() + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void takeElementScreenshot(By byObj, String fileName) {
        byte[] file = WebDriversManager.getDriver().findElement(byObj).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(System.getProperty("user.dir"), "media", fileName + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void fullPageScreenshot(String fileName) {
        byte[] file = ((TakesScreenshot) WebDriversManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(System.getProperty("user.dir"), "media", fileName + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void fullPageScreenshot() {
        byte[] file = ((TakesScreenshot) WebDriversManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        try {
            Files.write(Paths.get(System.getProperty("user.dir"), "media", Utilities.timeStamp() + ".PNG"), file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void closeWindow() {
        WebDriversManager.getDriver().close();
    }

    protected void switchToNewTab() {
        WebDriversManager.getDriver().switchTo().newWindow(WindowType.TAB);
    }

    protected void switchToNewWindow() {
        WebDriversManager.getDriver().switchTo().newWindow(WindowType.WINDOW);
    }

    protected Alert switchToAlert() {
        return WebDriversManager.getDriver().switchTo().alert();
    }

    protected void sleep(long timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
