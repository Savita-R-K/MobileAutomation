import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class AppiumBasics {
    @Test
    public void AppiumTest() throws MalformedURLException {

        //appium server start set up
        AppiumDriverLocalService service= new AppiumServiceBuilder().withAppiumJS(new File("C://Users//savita//AppData//Roaming//npm//node_modules//appium//build//lib//main.js")).withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();

        //set device options
        UiAutomator2Options options= new UiAutomator2Options();
        options.setDeviceName("emulator-5554");//emulator
        options.setApp("C://Users//savita//Downloads//APKFiles//resources//ApiDemos-debug.apk");

        //driver set up
        //URL class is used for java version less than 20
        AndroidDriver driver=new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        //java version 20 and above //driver set up
        //AndroidDriver driver1=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(),options);

        //Automation
        //we can use selenium By. but we can't access accessibiltyId type of locators
        //driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Preference')]")).click();
        //locators supported by appium -> AppiumBy.====>xpath,id,accessibilityId, Classname, androidUIAutomator
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();

        //set wifi name


        driver.quit();

        service.stop();
    }
}
