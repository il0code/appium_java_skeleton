package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class ResultPage extends pages.BasePOM {

    String searchtext;

    @FindBy(className = "listview__item")
    List<MobileElement> listview_items;

    @FindBy(className = "results__control__sort-by-select")
    MobileElement sortDropdown;

    By namefieldBy = By.className("listview__name-link");
    By pricetextBy = By.className("price");

    public ResultPage(AppiumDriver appiumDriver, String searchstring) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver, Duration.ofSeconds(20L)), this);
        this.searchtext = searchstring;
    }

    public void sortByCheapest() {
        Select dropdown = new Select(sortDropdown);
        dropdown.selectByVisibleText("Preis aufsteigend");
    }

    public Float getCheapestPrice() {
        Float cheapestpricetext = null;
        for (MobileElement mobileElement : listview_items) {
            if (mobileElement.findElement(namefieldBy).getText().contains(searchtext)) {
                String pricestring = mobileElement.findElement(pricetextBy).getText();
                Float price = Float.parseFloat(pricestring.replace(',', '.').split(" ")[1]);
                if (cheapestpricetext == null || cheapestpricetext > price) {
                    cheapestpricetext = price;
                }
            }
        }
        return cheapestpricetext;
    }
}
