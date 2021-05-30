package tests;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import pages.ResultPage;
import pages.RootPage;
import util.ExcelUtils;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseClass {
    AppiumDriver appiumDriver;

    @BeforeTest
    public void setup() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_4_API_30");
            capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
            capabilities.setCapability("appium:chromeOptions", ImmutableMap.of("w3c", false));
            URL url = new URL("http://127.0.0.1:4723/wd/hub");
            appiumDriver = new AndroidDriver(url, capabilities);
            appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1)
    public void openRootPage() {
        appiumDriver.get("https://www.geizhals.at");
        RootPage rootPage = new RootPage(appiumDriver);
        rootPage.checkForPopup();
        System.out.println(rootPage.getTitle());
        assert rootPage.getTitle().equals("Geizhals Österreich");
    }

    @Test(priority = 2)
    public void search() {
        RootPage rootPage = new RootPage(appiumDriver);
        rootPage.enterSearchString("Garmin Venu 2 black/slate");
        rootPage.pressSearch();
        assert rootPage.getTitle().equals("Garmin Venu 2 black/slate" + " Geizhals Österreich");
    }

    @Test(priority = 3)
    public void checkResults() {
        ResultPage resultPagePage = new ResultPage(appiumDriver, "Garmin Venu 2 black/slate");
        resultPagePage.sortByCheapest();
        Float price = resultPagePage.getCheapestPrice();
        assert price < Float.parseFloat("400");
    }

    @DataProvider
    public Object[][] SearchItems() throws Exception {
        Object[][] testObjArray = ExcelUtils.getTableArry("items.xlsx", "Tabelle 1", 2);
        return testObjArray;
    }

    @AfterTest
    public void teardown() {
        appiumDriver.close();
    }
}
