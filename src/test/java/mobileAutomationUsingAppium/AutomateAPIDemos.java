package mobileAutomationUsingAppium;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.List;

public class AutomateAPIDemos extends BaseTest{
    @Test
    public void wifiSettingsName() throws MalformedURLException {
        //may be 'preference' ele is not reachable some times
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Preference']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@content-desc,'Preference dependencies')]")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();
        driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.EditText")).sendKeys("auto wifi");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }
    @Test
    public void longPressUsingExecuteScript(){
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text,'Custom Adapter')]")).click();
        WebElement ele=driver.findElement(AppiumBy.xpath("//android.widget.ExpandableListView//android.widget.TextView[1]"));
        longPressGesture(ele);
        WebElement menu= driver.findElement(AppiumBy.className("android.widget.ListView"));
        Assert.assertTrue(menu.isDisplayed());
        List<WebElement> menuItems=driver.findElements(AppiumBy.xpath("//android.widget.ListView//android.widget.TextView"));
        for(WebElement item:menuItems){
            System.out.println(item.getText());
        }
    }

    @Test
    public void scrollUsingAndroidUIAutomator(){
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        String visibleText="WebView";
        scrollToText(visibleText);
    }
    @Test
    public void scrollUsingExecuteScript(){
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        scrollToEnd();
    }

    @Test
    public void swipeUsingExecuteScript(){
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@content-desc,'Photos')]")).click();
        WebElement image1=driver.findElement(By.xpath("(//android.widget.ImageView)[1]"));
        Assert.assertEquals(image1.getAttribute("focusable"),"true");
        swipeGesture(image1);
    }

    @Test
    public void dragAndDropUsingExecuteScript(){
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
        WebElement circle1= driver.findElement(By.id("io.appium.android.apis:id/drag_dot_1"));
        dragGesture(circle1);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Dropped')]")).isDisplayed());
    }

    @Test
    public void landscapeMode() {
        //may be 'preference' ele is not reachable some times
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Preference']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@content-desc,'Preference dependencies')]")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();
        driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();
        //rotate device to 90 degree
        DeviceRotation landscape=new DeviceRotation(0,0,90);
        driver.rotate(landscape);

        driver.findElement(AppiumBy.xpath("//android.widget.EditText")).sendKeys("auto wifi");
        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }

    @Test
    public void storeAndPasteValueFromClipboard() {
        //may be 'preference' ele is not reachable some times
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Preference']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@content-desc,'Preference dependencies')]")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();
        driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();
        //copy paste
        //store in clipboard
        driver.setClipboardText("Free Wifi");
        //paste from clipboard
        driver.findElement(AppiumBy.xpath("//android.widget.EditText")).sendKeys( driver.getClipboardText());
        driver.findElement(AppiumBy.id("android:id/button1")).click();

        //go back
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        //go to home
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    @Test
    public void keyEvents() throws MalformedURLException {
        //may be 'preference' ele is not reachable some times
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@content-desc='Preference']")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@content-desc,'Preference dependencies')]")).click();
        driver.findElement(AppiumBy.id("android:id/checkbox")).click();
        driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();
        //copy paste

        //store in clipboard
        driver.setClipboardText("Free Wifi");
        //paste from clipboard
        driver.findElement(AppiumBy.xpath("//android.widget.EditText")).sendKeys( driver.getClipboardText());

        driver.findElement(AppiumBy.id("android:id/button1")).click();
    }

    @Test
    public void jumpToRequiredPageDirectlyUsingAppPackageAndActivity()  {
        
        //previous methods-depricated
        //Activity activity=new Activity("io.appium.android.apis","io.appium.android.apis.text.LogTextBox1}");
        //driver.startActivity(activity);

        //current method
        ((JavascriptExecutor)driver).executeScript("mobile: startActivity",ImmutableMap.of("intent","io.appium.android.apis/io.appium.android.apis.text.LogTextBox1}"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
