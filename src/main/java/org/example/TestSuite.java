package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestSuite {
    protected static WebDriver driver;
    public static void clickOnElement(By by){

        driver.findElement(by).click();
    }

    public static void typeText(By by, String text){
        driver.findElement(by).sendKeys(text);

    }

    public static long timestamp(){
        Timestamp timestamp =new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    public static String getTextFromElement(By by){
        return driver.findElement(by).getText();
    }

    static String expectedRegistrationCompleteMsg ="Congratulations Registration is successful";
    static String expectedCommunityPollVoteErrorMsg = "Registered users should vote";
    static String expectedEmail_a_friendErrorMsg = "Email a friend is allowed only for registered user";
    static String expectedEmailConfirmationMsg = "Email has been sent";
    static String expectedProductName = "Leica T Digital Camera (Mirrorless)";
    static String expectedVoteConfirmationMsg = "Thanks for voting" ;
    static String expectedProductNameAfterSendingEmail = "Build your own computer with Peripherals";
    @BeforeMethod
    public static void openBrowser(){
        //open Chrome browser
        driver = new ChromeDriver();
        //open demonopcommerce.com
        driver.get("https://demo.nopcommerce.com/");
        //maximize window
        driver.manage().window().maximize();
        //add implicit wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @AfterMethod
    public static void closeBrowser(){
        //close the browser
        driver.close();
    }


    @Test
    //To verify user should be able to register successfully
    public static void verifyUserShouldBeAbleToRegisterSuccessfully(){

        //click on Register button
        clickOnElement(By.className("ico-register"));

        //type firstname
        typeText(By.id("FirstName"),"TestFirstName");

        //type lastname
        typeText(By.id("LastName"),"TestLastName");

        //type email address
        typeText(By.id("Email"),"testJava12"+timestamp()+"@gmail.com");

        //type password
        typeText(By.id("Password"),"abc567");

        //type confirm password
        typeText(By.id("ConfirmPassword"),"abc567");

        //click on submit button
        clickOnElement(By.id("register-button"));

        //print the message "Your registration is completed"
        String actualRegistrationCompletionMesssage = getTextFromElement(By.className("result"));
        System.out.println("Actual message : "+actualRegistrationCompletionMesssage);

        //compare expected msg with actual msg
        Assert.assertEquals(actualRegistrationCompletionMesssage,expectedRegistrationCompleteMsg,"Your registration is not completed");
    }
    @Test

    //To verify only registered user should be able to vote
    public static void verifyUserShouldBeAbleToVote(){

        //click on Good
        clickOnElement(By.id("pollanswers-2"));

        //click on vote
        clickOnElement(By.id("vote-poll-1"));

        //apply explicit wait of 20 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("block-poll-vote-error-1")));

        //print message
        String actualVoteEligibilityErrorMsg = getTextFromElement(By.id("block-poll-vote-error-1"));
        System.out.println("Actual Poll vote error message : "+actualVoteEligibilityErrorMsg);

        //compare
        Assert.assertEquals(actualVoteEligibilityErrorMsg,expectedCommunityPollVoteErrorMsg,"You are not registered user to vote");
    }
    @Test

    //To verify only registered user should be able to email a friend
    public static void verifyUserShouldBeAbleToEmail_A_Friend(){
        //click on Add To Cart Button of Apple Mac
        clickOnElement(By.xpath("//div[@class='product-grid home-page-product-grid']/div[2]/div[2]/div[1]/div[2]/div[3]/div[2]/button[1]"));

        //click on Email a friend button
        clickOnElement(By.xpath("//div[@class='email-a-friend']/button"));

        //type friend's email id
        typeText(By.id("FriendEmail"),"testFriend@gmail.com");

        //type your email id
        typeText(By.id("YourEmailAddress"),"testUnique@gmail.com");

        //type message
        typeText(By.id("PersonalMessage"),"Apple Mac Product is very useful");

        //Name of the product being referred to the friend before send email
        String productNameBeforeSendEmail = getTextFromElement(By.xpath("//div[@class='page-body']/div[1]/h2/a"));
        System.out.println("Product name before sending email to friend : "+productNameBeforeSendEmail);

        //click send email button
        clickOnElement(By.name("send-email"));

        //capture error message
        String actualErrorMessage = getTextFromElement(By.xpath("//div[@class='message-error validation-summary-errors']"));
        System.out.println("Actual message:"+actualErrorMessage);
        Assert.assertEquals(actualErrorMessage,expectedEmail_a_friendErrorMsg,"Please Register yourself.");

    }

    @Test

    //To verify user should be able to add two products in compare list and then clear list
    public static void verifyUserShouldBeAbleToAdd_2_Products_inCompareListAndClear()  {
        String expectedmsg = "Items not present in the compare list";

        //click on add to compare button of HTC One
        clickOnElement(By.xpath("//div[@class='product-grid home-page-product-grid']/div[2]/div[3]/div[1]/div[2]/div[3]/div[2]/button[2]"));

        WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//p[@class='content']")));

        //click on add to compare button of $25 Virtual Gift Card
        clickOnElement(By.xpath("//div[@class='product-grid home-page-product-grid']/div[2]/div[4]/div[1]/div[2]/div[3]/div[2]/button[2]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("product comparison")));

        //click on product comparison link
        clickOnElement(By.linkText("product comparison"));

        //User should be able to see two products in compare list
        String actualVirtual£25ProductName = getTextFromElement(By.xpath("//table[@class='compare-products-table']/tbody/tr[3]/td[2]/a"));
        String actualHTCProductName = getTextFromElement(By.xpath("//table[@class='compare-products-table']/tbody/tr[3]/td[3]/a"));
        System.out.println("Virtual product: "+actualVirtual£25ProductName);
        System.out.println("HTC product: "+actualHTCProductName);

        //click on clear list
        clickOnElement(By.xpath("//div[@class='page-body']/a"));

        //print msg to compare
        String actualMessageWhenNoProductsInCompareList = getTextFromElement(By.className("no-data"));
        System.out.println("Actual message after clear products from compare list= "+actualMessageWhenNoProductsInCompareList);
        Assert.assertEquals(actualMessageWhenNoProductsInCompareList,expectedmsg,"Message displayed is incorrect");

    }

    @Test

    //To verify user should be able to see same products in shopping cart after adding them
    public static void verifyUserShouldBeAbleToSeeSameProductAddedInShoppingCart()
    {
        //click on electronic page
        clickOnElement(By.xpath("(//a[@title='Show products in category Electronics'])[1]"));

        //click on camera & photo
        clickOnElement(By.xpath("//img[@title='Show products in category Camera & photo']"));

        //print to verify product name before add to cart
        String name= getTextFromElement(By.linkText("Leica T Mirrorless Digital Camera"));
        System.out.println("Product name before adding to cart :"+name);

        //click on add to cart
        clickOnElement(By.xpath("(//button[@class='button-2 product-box-add-to-cart-button'])[2]"));

        //click on shopping cart
        clickOnElement(By.linkText("shopping cart"));

        //print product name in shopping cart after adding to compare
        String actualProduct_nameInShoppingCart= getTextFromElement(By.linkText("Leica T Mirrorless Digital Camera"));
        System.out.println("Product name after adding in the shopping cart: "+actualProduct_nameInShoppingCart);
        Assert.assertEquals(actualProduct_nameInShoppingCart,expectedProductName,"Product name in Shopping cart is incorrect.");

    }

    @Test

    //To verify that registered user should be able to refer a product to friend
    public static void verifyRegisteredUserShouldBeAbleToReferProductToFriend()  {
        //click on Register button
        clickOnElement(By.className("ico-register"));

        //type firstname
        typeText(By.id("FirstName"),"TestFirstName");

        //type lastname
        typeText(By.id("LastName"),"TestLastName");

        //type email address
        typeText(By.id("Email"),"testing12345@gmail.com");

        //type password
        typeText(By.xpath("//input[@name='Password']"),"xyz%100");

        //type confirm password
        typeText(By.id("ConfirmPassword"),"xyz%100");

        //click on submit button
        clickOnElement(By.id("register-button"));

        //click on login
        clickOnElement(By.xpath("//a[@href=\"/login?returnUrl=%2F\"]"));

        //enter email id
        typeText(By.id("Email"),"testing12345@gmail.com");

        //enter password
        typeText(By.id("Password"),"xyz%100");

        //click on LOGIN button
        clickOnElement(By.xpath("(//div[@class='buttons']/button)[2]"));

        //click on add to cart button of Build your own computer
        clickOnElement(By.xpath("//div[@class='product-grid home-page-product-grid']/div[2]/div[1]/div[1]/div[2]/div[3]/div[2]/button[1]"));

        //click on email a friend
        clickOnElement(By.xpath("//div[@class='email-a-friend']/button"));

        //enter email id of friend
        typeText(By.id("FriendEmail"),"testFriend@gmail.com");

        //enter your email id
        typeText(By.id("YourEmailAddress")," ");

        //enter personal message
        typeText(By.id("PersonalMessage"),"This Product is too good.Have a look.");

        //click on send email
        clickOnElement(By.name("send-email"));

        //Name of the product referred to the friend after email has been sent
        String productNameAfterEmail = getTextFromElement(By.xpath("//div[@class='page-body']/div/h2/a"));
        System.out.println("Product name shown after sending email (Confirmation Message): "+productNameAfterEmail);
        Assert.assertEquals(productNameAfterEmail,expectedProductNameAfterSendingEmail,"Product name referred is incorrect");

    }

    @Test

    //To verify that registered user should be able to vote successfully
    public static void verifyRegisteredUserShouldAbleToVoteSuccesfully()  {

        //click on Register button
        clickOnElement(By.className("ico-register"));

        //type firstname
        typeText(By.id("FirstName"),"TestFirstName");

        //type lastname
        typeText(By.id("LastName"),"TestLastName");

        //type email address
        typeText(By.id("Email"),"javatest2@gmail.com");

        //type password
        typeText(By.xpath("//input[@name='Password']"),"test123");

        //type confirm password
        typeText(By.id("ConfirmPassword"),"test123");

        //click on submit button
        clickOnElement(By.id("register-button"));

        //click on login
        clickOnElement(By.xpath("//a[@href=\"/login?returnUrl=%2F\"]"));

        //enter email id
        typeText(By.id("Email"),"javatest2@gmail.com");

        //enter password
        typeText(By.id("Password"),"test123");

        //click on LOGIN button
        clickOnElement(By.xpath("(//div[@class='buttons']/button)[2]"));

        //click on Good
        clickOnElement(By.xpath("//div[@class='poll']/ul/li[2]/input"));

        //click on vote
        clickOnElement(By.id("vote-poll-1"));

        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='poll-total-votes']")));

        //Print and verify the expected with actual vote confirmation message when Good is entered
        String actualVoteFeedbackMsg = getTextFromElement(By.xpath("//span[@class='poll-total-votes']"));
        System.out.println("Actual confirmation message : "+actualVoteFeedbackMsg);
        Assert.assertEquals(actualVoteFeedbackMsg,expectedVoteConfirmationMsg,"Vote Confirmation Message is not correct");

    }
}
