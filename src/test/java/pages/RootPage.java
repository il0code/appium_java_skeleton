package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RootPage extends pages.BasePOM {
    @FindBy(id = "fs")
    MobileElement search_field;

    @FindBy(id = "gh_suchen_bt_i")
    MobileElement search_button;

    public RootPage(AppiumDriver appiumDriver) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver, Duration.ofSeconds(20L)), this);
    }

    public void enterSearchString(String searchitem) {
        search_field.sendKeys(searchitem);
    }

    public void pressSearch() {
        search_button.click();
    }

    public String getTitle() {
        return appiumDriver.getTitle();
    }

    public void checkForPopup() {
        By acceptBtn = By.id("onetrust-accept-btn-handler");
        WebDriverWait wait = new WebDriverWait(appiumDriver, 20L);
        wait.until(ExpectedConditions.elementToBeClickable(acceptBtn));
        appiumDriver.findElement(acceptBtn).click();
    }
}
