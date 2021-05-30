package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePOM {
    AppiumDriver appiumDriver;

    public void setDriver(WebDriver driver) {this.appiumDriver = appiumDriver;}

    public BasePOM() {}

    public BasePOM(  AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(appiumDriver, this);
    }
}
