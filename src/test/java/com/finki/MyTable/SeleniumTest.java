package com.finki.MyTable;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SeleniumTest {
    private WebDriver driver;
    private String orderTimeErrorMsg;
    private boolean successfulOrder;
    private boolean orderedItemsExist;

    @BeforeClass
    public void init() throws InterruptedException {
        ChromeDriverManager.getInstance().version("2.40").setup();
        driver = new ChromeDriver();

        driver.get("localhost:3000");

        Thread.sleep(1000);


        WebElement signInCustomerBtn = driver.findElement(By.id("sign_in_customer"));
        signInCustomerBtn.click();

        Thread.sleep(1000);

        WebElement customerEmailInput = driver.findElement(By.id("customer_email_input"));
        WebElement customerPasswordInput = driver.findElement(By.id("customer_password_input"));

        customerEmailInput.sendKeys("dbarisic@gmail.com");
        customerPasswordInput.sendKeys("password");

        WebElement proceedSignInCustomerBtn = driver.findElement(By.id("proceed_sign_in_customer"));
        proceedSignInCustomerBtn.click();

        Thread.sleep(1000);


        WebElement selectRestaurant = driver.findElement(By.id("restaurant_5"));
        selectRestaurant.click();

        Thread.sleep(1000);

        WebElement increaseFirstItem = driver.findElement(By.id("increase_16"));
        increaseFirstItem.click();
        Thread.sleep(200);
        increaseFirstItem.click();
        Thread.sleep(200);
        WebElement addFirstToCart = driver.findElement(By.id("add_to_cart_16"));
        addFirstToCart.click();

        Thread.sleep(1000);

        WebElement increaseSecondItem = driver.findElement(By.id("increase_17"));
        increaseSecondItem.click();
        Thread.sleep(200);
        WebElement addSecondToCart = driver.findElement(By.id("add_to_cart_17"));
        addSecondToCart.click();

        Thread.sleep(1000);

        WebElement checkoutCartBtn = driver.findElement(By.id("checkout_cart"));
        checkoutCartBtn.click();

        Thread.sleep(1000);

        WebElement submitCurrentOrder = driver.findElement(By.id("submit_current_order"));
        submitCurrentOrder.click();

        WebElement numberOfSeatsInput = driver.findElement(By.name("numberOfSeatsInput"));
        numberOfSeatsInput.sendKeys("3");

        WebElement dayInput = driver.findElement(By.name("dayInput"));
        dayInput.sendKeys("10");

        WebElement monthInput = driver.findElement(By.name("monthInput"));
        monthInput.sendKeys("10");

        WebElement yearInput = driver.findElement(By.name("yearInput"));
        yearInput.sendKeys("2020");

        WebElement hourInput = driver.findElement(By.name("hourInput"));
        hourInput.sendKeys("20");

        WebElement minuteInput = driver.findElement(By.name("minuteInput"));
        minuteInput.sendKeys("30");

        WebElement creditCardInput = driver.findElement(By.name("creditCardNumber"));
        creditCardInput.sendKeys("123123123");

        WebElement cvvInput = driver.findElement(By.name("CVV"));
        cvvInput.sendKeys("812");

        WebElement finalConfirmOrder = driver.findElement(By.id("final_confirm_order"));
        finalConfirmOrder.click();

        Thread.sleep(2000);

        WebElement errorMsg = driver.findElement(By.id("order_error_msg"));
        orderTimeErrorMsg = errorMsg.getText();


        yearInput.clear();
        yearInput.sendKeys("2019");
        finalConfirmOrder.click();

        Thread.sleep(1000);



        try {
            driver.findElement(By.className("my_orders_customer"));
            successfulOrder = true;
        } catch (NoSuchElementException e) {
            successfulOrder = false;
        }



        WebElement dropdownMenuBtn = driver.findElement(By.id("dropdownMenuButton"));
        dropdownMenuBtn.click();

        Thread.sleep(200);

        WebElement signOutCustomer = driver.findElement(By.id("sign_out_customer"));
        signOutCustomer.click();

        Thread.sleep(1000);


        WebElement signInRestaurant = driver.findElement(By.id("sign_in_restaurant"));
        signInRestaurant.click();

        Thread.sleep(1000);

        WebElement restaurantEmailInput = driver.findElement(By.id("restaurant_email_input"));
        restaurantEmailInput.sendKeys("anastasovski.h@gmail.com");

        WebElement restaurantPasswordInput = driver.findElement(By.id("restaurant_password_input"));
        restaurantPasswordInput.sendKeys("password");

        WebElement restaurantSignInBtn = driver.findElement(By.id("restaurant_sign_in"));
        restaurantSignInBtn.click();

        Thread.sleep(1000);

        WebElement restaurantOrdersLink = driver.findElement(By.id("restaurant_orders_link"));
        restaurantOrdersLink.click();

        Thread.sleep(1000);

        try {
            driver.findElement(By.className("ordered_items_restaurant"));
            orderedItemsExist = true;
        } catch (NoSuchElementException e) {
            orderedItemsExist = false;
        }

    }


   @Test
    public void checkOrderTimeErrorMsg(){
       Assert.assertEquals(orderTimeErrorMsg,"Your order can be in a maximum of 1 month in the future");
   }

   @Test
    public void orderCompleted(){
       Assert.assertTrue(successfulOrder);
   }

    @Test
    public void orderReceivedByRestaurant(){
        Assert.assertTrue(orderedItemsExist);
    }



}

