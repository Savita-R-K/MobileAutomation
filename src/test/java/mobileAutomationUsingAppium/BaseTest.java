package mobileAutomationUsingAppium;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    public AppiumDriverLocalService service;
    public AndroidDriver driver;



    @BeforeClass
//    @BeforeTest
    public void configureAppium() throws MalformedURLException {

        //appium server start set up
        service = new AppiumServiceBuilder().withAppiumJS(new File("C://Users//savita//AppData//Roaming//npm//node_modules//appium//build//lib//main.js")).withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();

        //set device options
        UiAutomator2Options options = new UiAutomator2Options();

        options.setDeviceName("emulator-5554");//emulator

        //options.setApp("C:\Users\savita\IdeaProjects\Mobile Automation\src\test\resources\ApiDemos-debug.apk");
        options.setApp("C:\\Users\\savita\\IdeaProjects\\Mobile Automation\\src\\test\\resources\\General-Store.apk");

        //use for hybrid or web applications
        options.setChromedriverExecutable("C:\\Users\\savita\\Downloads\\chromedriver\\chromedriver-win64\\chromedriver.exe");

        //use for only mobile chrome driver automation use set device name,setChromedriverExecutable and setCapability and remove setApp options
        //options.setCapability("browserNames","Chrome");

        //driver set up
        //URL class is used for java version less than 20
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //java version 20 and above //driver set up
        //AndroidDriver driver1=new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(),options);
    }

    @AfterClass
//    @AfterTest
    public void tearDown(){
        driver.quit();
        service.stop();
    }

    public void longPressGesture(WebElement ele) {
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of("elementId",((RemoteWebElement)ele).getId(),"Duration",2000));
    }

    public  void scrollToText(String visibleText){
        String uiAutomatorText="new UiScrollable(new UiSelector()).scrollIntoView(text(\""+visibleText+"\"));";
        driver.findElement(AppiumBy.androidUIAutomator(uiAutomatorText));
       // driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"));"));

    }
    public void scrollToEnd(){
        boolean canWeScroll;
        do {
            canWeScroll=(Boolean)((JavascriptExecutor)driver)
                    .executeScript("mobile: scrollGesture", ImmutableMap.of(
                            "left", 100,
                            "top", 100,
                            "width", 200,
                            "height", 200,
                            "direction", "down",
                            "percent", 3.0));
        }while (canWeScroll);
    }

    public void swipeGesture(WebElement ele){
        ((JavascriptExecutor)driver).executeScript("mobile: swipeGesture",ImmutableMap.of(
                "elementId",((RemoteWebElement)ele).getId(),
                "direction","left",
                "percent",0.75));
    }

    public void dragGesture(WebElement ele){
        ((JavascriptExecutor)driver).executeScript("mobile: dragGesture",ImmutableMap.of(
                "elementId",((RemoteWebElement)ele).getId(),
                "endX",620,
                "endY",560));
    }
}
