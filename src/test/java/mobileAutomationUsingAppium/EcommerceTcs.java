package mobileAutomationUsingAppium;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

public class EcommerceTcs extends BaseTest {

    Scanner scan=new Scanner(System.in);
    public WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
    List<String> productList=new ArrayList<>();

    @Test
    public void fillForm() throws InterruptedException {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("SavitaRK");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
//        driver.pressKey(new KeyEvent(AndroidKey.BACK));
//        driver.hideKeyboard();
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        scrollToText("Austria");
        driver.findElement(By.xpath("//android.widget.TextView[@text=\"Austria\"]")).click();
        driver.findElement(By.className("android.widget.Button")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text=\"Products\"]")).isDisplayed());
        Thread.sleep(3000);
    }

    @Test
    public void toastMessages() {
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.className("android.widget.Button")).click();
        //android.widget.Toast :tag used for error coding has name attribute
        // more than one than get indexes

        String ToastMsg = driver.findElement(By.xpath("//android.widget.Toast")).getText();
        Assert.assertEquals(ToastMsg, "Please enter your name");

    }

    @Test
    public void addToCart() {
        driver.findElement(By.id("com.androidsample.generalstore:id/nameField")).sendKeys("SavitaRK");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.className("android.widget.Button")).click();
        scrollToText("PG 3");
        WebElement addToCartBtn = driver.findElement(By.xpath("//android.widget.TextView[@text='PG 3']/following-sibling::android.widget.LinearLayout/android.widget.TextView[2]"));
        addToCartBtn.click();
        Assert.assertEquals(addToCartBtn.getText(), "ADDED TO CART");
    }

    @Test
    public void getProductList() {
        driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'Enter name')]")).sendKeys("SavitaRK");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.className("android.widget.Button")).click();

        List<WebElement> productEle = driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView//android.widget.ImageView/following-sibling::android.widget.TextView"));

        productList = storeUniqueElements(productEle, productList);
        int previousSize = -1;
        while (true) {
            // Perform scroll
            ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of("left", 0, "top", 0, "width", 900, "height", 1000, "direction", "down", "percent", 0.8));

            // Collect products AFTER each scroll
            productEle = driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView//android.widget.ImageView/following-sibling::android.widget.TextView"));
            productList = storeUniqueElements(productEle, productList);

            // If no new products found, stop scrolling
            if (productList.size() == previousSize) {
                break;
            }
            previousSize = productList.size();
        }

        for (String i : productList) {
            System.out.println(i);
        }

    }

    @Test
    public void addMultipleProductsToCart() {
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'Enter name')]"))));
        driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'Enter name')]")).sendKeys("SavitaRK");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.className("android.widget.Button")).click();

        //products to add to cart
        List<String> productNames = Arrays.asList("PG 3", "Nike SFB Jungle");
        List<String> productsAddedToCart = new ArrayList<>();
        //check the products added to cart
        while (productsAddedToCart.size() < productNames.size()) {
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView//android.widget.ImageView/following-sibling::android.widget.TextView"))));
            List<WebElement> productEle = driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView//android.widget.ImageView/following-sibling::android.widget.TextView"));
            for (WebElement ele : productEle) {
                if (productNames.contains(ele.getText()) && !productsAddedToCart.contains(ele.getText())) {
                    productsAddedToCart.add(ele.getText());
                    driver.findElement(By.xpath("//android.widget.TextView[@text='"+ele.getText()+"']/..//android.widget.TextView[@text='ADD TO CART']")).click();
                }
            }
            if (productsAddedToCart.size() == productNames.size()) {
                break;
            }
            ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of("left", 0, "top", 0, "width", 900, "height", 1000, "direction", "down", "percent", 0.8));
        }
    }

    public List<String> storeUniqueElements(List<WebElement> productEle, List<String> productList) {
        for (WebElement i : productEle) {
            if (!productList.contains(i.getText())) {
                productList.add(i.getText());
            }
        }
        return productList;
    }


    @Test
    public void checkoutProcessAndNavigatingToGoogle() throws InterruptedException {
        //wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'Enter name')]"))));
        driver.findElement(By.xpath("//android.widget.EditText[contains(@text,'Enter name')]")).sendKeys("SavitaRK");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.className("android.widget.Button")).click();

        //products to add to cart
        List<String> productNames = Arrays.asList("PG 3", "Nike SFB Jungle");
        List<String> productsAddedToCart = new ArrayList<>();
        //check the products added to cart
        while (productsAddedToCart.size() < productNames.size()) {
            wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView//android.widget.ImageView/following-sibling::android.widget.TextView"))));
            List<WebElement> productEle = driver.findElements(By.xpath("//android.support.v7.widget.RecyclerView//android.widget.ImageView/following-sibling::android.widget.TextView"));
            for (WebElement ele : productEle) {
                if (productNames.contains(ele.getText()) && !productsAddedToCart.contains(ele.getText())) {
                    productsAddedToCart.add(ele.getText());
                    driver.findElement(By.xpath("//android.widget.TextView[@text='"+ele.getText()+"']/..//android.widget.TextView[@text='ADD TO CART']")).click();
                }
            }
            if (productsAddedToCart.size() == productNames.size()) {
                break;
            }
            ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of("left", 0, "top", 0, "width", 900, "height", 1000, "direction", "down", "percent", 0.8));
        }
        //click on cart icon and navigate to checkout page
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        //long press and open terms
        WebElement terms=driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        longPressGesture(terms);
        Assert.assertEquals(driver.findElement(By.id("com.androidsample.generalstore:id/alertTitle")).getText(),"Terms Of Conditions");
        //close terms
        driver.findElement(By.id("android:id/button1")).click();
        //checkbox
        driver.findElement(By.className("android.widget.CheckBox")).click();
        //navigate to google chrome(Hybrid app)
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(10000);
        Set<String> contextNames=driver.getContextHandles();
        for(String contextName:contextNames){
//NATIVE_APP and WEBVIEW_com.androidsample.generalstore are the context names
            System.out.println(contextName);
        }

        //set options. set chromedriver executable(chrome driver path);
        driver.context("WEBVIEW_com.androidsample.generalstore");
        driver.findElement(By.name("q")).sendKeys("Rahul Shetty Academy");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));


    }

}
