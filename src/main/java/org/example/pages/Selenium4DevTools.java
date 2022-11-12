package org.example.pages;

import com.google.common.net.MediaType;
import org.example.drivers.WebDriversManager;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v106.emulation.Emulation;
import org.openqa.selenium.devtools.v106.log.Log;
import org.openqa.selenium.devtools.v106.performance.Performance;
import org.openqa.selenium.devtools.v106.performance.model.Metric;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.openqa.selenium.remote.http.Contents.utf8String;

public class Selenium4DevTools extends BasePage {

    public void setGeolocationOverride() {
        WebDriver augment = new Augmenter().augment(WebDriversManager.getDriver());
        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setGeolocationOverride(Optional.of(52.5043), Optional.of(13.4501), Optional.of(1)));
    }

    public void setDeviceMetricsOverride() {
        WebDriver augment = new Augmenter().augment(WebDriversManager.getDriver());
        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();
// iPhone 11 Pro dimensions
        devTools.send(Emulation.setDeviceMetricsOverride(375, 812, 50, true, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
        augment.get("https://selenium.dev/");
        augment.quit();
    }

    public void performanceMetricsExample() {
        WebDriver augment = new Augmenter().augment(WebDriversManager.getDriver());
        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());

        augment.get("https://google.com");
        augment.quit();

        for (Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }
    }

    public void jsExceptionsExample() {
        WebDriver driver = WebDriversManager.getDriver();
        WebDriver augment = new Augmenter().augment(WebDriversManager.getDriver());
        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();

        List<JavascriptException> jsExceptionsList = new ArrayList<>();
        Consumer<JavascriptException> addEntry = jsExceptionsList::add;
        devTools.getDomains().events().addJavascriptExceptionListener(addEntry);

        driver.get("<your site url>");

        WebElement link2click = driver.findElement(By.linkText("<your link text>"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", link2click, "onclick", "throw new Error('Hello, world!')");
        link2click.click();

        for (JavascriptException jsException : jsExceptionsList) {
            System.out.println("JS exception message: " + jsException.getMessage());
            System.out.println("JS exception system information: " + jsException.getSystemInformation());
            jsException.printStackTrace();
        }
    }

    public void virtualAuthenticationExample() {
//        VirtualAuthenticatorOptions options = new VirtualAuthenticatorOptions()
//                .setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2)
//                .setHasResidentKey(true)
//                .setHasUserVerification(true)
//                .setIsUserVerified(true);
//        VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options);
//
//        byte[] credentialId = {1, 2, 3, 4};
//        byte[] userHandle = {1};
//        Credential residentCredential = Credential.createResidentCredential(
//                credentialId, "localhost", rsaPrivateKey, userHandle, /*signCount=*/0);
//
//        authenticator.addCredential(residentCredential);
//
//        List<org.openqa.selenium.virtualauthenticator.Credential> credentialList = authenticator.getCredentials();
//
//        VirtualAuthenticator authenticator =
//                ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(new VirtualAuthenticatorOptions());
//
//        byte[] credentialId = {1, 2, 3, 4};
//        Credential residentCredential = Credential.createNonResidentCredential(
//                credentialId, "localhost", rsaPrivateKey, /*signCount=*/0);
//
//        authenticator.addCredential(residentCredential);
//
//        authenticator.removeAllCredentials();
    }

    public void networkInterceptor() {
        NetworkInterceptor interceptor = new NetworkInterceptor(WebDriversManager.getDriver(), Route.matching(req -> true).to(() -> req -> new HttpResponse().setStatus(200).addHeader("Content-Type", MediaType.HTML_UTF_8.toString()).setContent(utf8String("Creamy, delicious cheese!"))));
        WebDriversManager.getDriver().get("https://example-sausages-site.com");
        interceptor.close();
    }

    public void setUpNetwork() {
        WebDriver augmented = new Augmenter().augment(WebDriversManager.getDriver());
        NetworkConnection networkConnectionDriver = ((NetworkConnection) augmented);

        NetworkConnection.ConnectionType current = networkConnectionDriver.getNetworkConnection();
        NetworkConnection.ConnectionType modified;
        if (current.isAirplaneMode()) {
            modified = networkConnectionDriver.setNetworkConnection(NetworkConnection.ConnectionType.ALL);
        } else {
            modified = networkConnectionDriver.setNetworkConnection(NetworkConnection.ConnectionType.AIRPLANE_MODE);
        }
        assertThat(modified, equalTo(current.isAirplaneMode()));
    }

    public void listenConsoleLogEvents() {
        WebDriver augment = new Augmenter().augment(WebDriversManager.getDriver());
        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("log: " + logEntry.getText());
            System.out.println("level: " + logEntry.getLevel());
        });
        augment.get("http://the-internet.herokuapp.com/broken_images");
    }

    public void listenJsException() {
        WebDriver augment = new Augmenter().augment(WebDriversManager.getDriver());
        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();

        List<JavascriptException> jsExceptionsList = new ArrayList<>();
        Consumer<JavascriptException> addEntry = jsExceptionsList::add;
        devTools.getDomains().events().addJavascriptExceptionListener(addEntry);

        augment.get("<your site url>");

        WebElement link2click = augment.findElement(By.linkText("<your link text>"));
        ((JavascriptExecutor) augment).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", link2click, "onclick", "throw new Error('Hello, world!')");
        link2click.click();

        for (JavascriptException jsException : jsExceptionsList) {
            System.out.println("JS exception message: " + jsException.getMessage());
            System.out.println("JS exception system information: " + jsException.getSystemInformation());
            jsException.printStackTrace();
        }
    }

    public void basicAuth() {
        AtomicReference<DevTools> devToolsAtomicReference = new AtomicReference<>();

        WebDriver driver = WebDriversManager.getDriver();

        WebDriver augment = new Augmenter()
                .addDriverAugmentation("chrome",
                        HasAuthentication.class,
                        (caps, exec) -> (whenThisMatches, useTheseCredentials) -> {
                            devToolsAtomicReference.get()
                                    .createSessionIfThereIsNotOne();
                            devToolsAtomicReference.get().getDomains()
                                    .network()
                                    .addAuthHandler(whenThisMatches,
                                            useTheseCredentials);
                        }).augment(driver);

        DevTools devTools = ((HasDevTools) augment).getDevTools();
        devTools.createSession();
        devToolsAtomicReference.set(devTools);
        ((HasAuthentication) augment).
                register(UsernameAndPassword.of("admin", "admin"));
    }
}
