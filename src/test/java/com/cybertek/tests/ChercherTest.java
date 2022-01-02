package com.cybertek.tests;

import com.cybertek.utilities.WebDriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ChercherTest {

    /*
    Task1:
    1. Go to https://chercher.tech/practice/explicit-wait-sample-selenium-webdriver
    2. Click on "Click me, to Open an alert after 5 seconds"
    3. Explicitly wait until alert is present
    4. Then handle the Javascript alert

     Task2:
    1. Go to https://chercher.tech/practice/explicit-wait-sample-selenium-webdriver
    2. Click on "Enable button after 10 seconds"
    3. Explicitly wait until the button is enabled
    4. Then verify the button is enabled
    Task3:
    1. Go to:  http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx
    2. Login with username: Tester, password: test
    3. Click  Order button
    4. Verify under Product Information, selected option is “MyMoney”
    5. Then select FamilyAlbum, make quantity 2, and click Calculate,
    6. Then verify Total is equal to Quantity*PricePerUnit

     */

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp(){
        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();

        // This implicitly wait is going to be applied to all test cases and elements
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);



    }
    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

    @Test
    public void task1(){

        driver.get("https://chercher.tech/practice/explicit-wait-sample-selenium-webdriver");

        WebElement button = driver.findElement(By.id("alert"));
        button.click();


        wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }


    @Test
    public void task2(){
        driver.get("https://chercher.tech/practice/explicit-wait-sample-selenium-webdriver");
        WebElement enableButton = driver.findElement(By.id("enable-button"));
        enableButton.click();
        WebElement disabledButton = driver.findElement(By.id("disable"));
        wait = new WebDriverWait(driver,12);
        wait.until(ExpectedConditions.elementToBeClickable(disabledButton));
        Assert.assertTrue(disabledButton.isEnabled());
    }


    @Test
    public void test3(){
        driver.get("http://secure.smartbearsoftware.com/samples/testcomplete12/WebOrders/login.aspx");

        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
        driver.findElement(By.id("ctl00_MainContent_login_button")).click();

        driver.findElement(By.linkText("Order")).click();

        WebElement productDropdown = driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct"));

        Select pDropdown = new Select(productDropdown);

        Assert.assertEquals(pDropdown.getFirstSelectedOption().getText(), "MyMoney", "Selected Product failed");

        pDropdown.selectByIndex(1);
        WebElement quantityInput = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity"));
        WebElement priceInput = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtUnitPrice"));
        WebElement totalInput = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal"));


        quantityInput.clear();
        quantityInput.sendKeys("2");
        driver.findElement(By.cssSelector("[value='Calculate']")).click();

        int total = Integer.parseInt(quantityInput.getAttribute("value")) * Integer.parseInt(priceInput.getAttribute("value"));
        Assert.assertEquals(totalInput.getAttribute("value"), Integer.toString(total), "Calculation failed");
        System.out.println("totalInput.getAttribute(\"value\") = " + totalInput.getAttribute("value"));


    }

}
