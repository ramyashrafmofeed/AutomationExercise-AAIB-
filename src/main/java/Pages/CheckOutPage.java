package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage {
    WebDriver driver;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By proceedToCheckout = By.xpath("//a[@class='btn btn-default check_out']");
    By nameOnCard = By.name("name_on_card");
    By cardNumber = By.name("card_number");
    By CVC = By.name("cvc");
    By Expiration_Month = By.name("expiry_month");
    By Expiration_Year = By.name("expiry_year");

    By placeOrderButton = By.xpath("//a[@href='/payment' and @class='btn btn-default check_out']");
    By PayAndConfirm = By.xpath("//button[@id='submit' and @data-qa='pay-button']");

    public void proceedToCheckout() {
        driver.findElement(proceedToCheckout).click();
    }

    public void fillDetails(String name, String number, String cvc, String month, String year) {
        driver.findElement(nameOnCard).sendKeys(name);
        driver.findElement(cardNumber).sendKeys(number);
        driver.findElement(CVC).sendKeys(cvc);
        driver.findElement(Expiration_Month).sendKeys(month);
        driver.findElement(Expiration_Year).sendKeys(year);
    }

    public void placeOrder() {
        driver.findElement(placeOrderButton).click();
    }

    public void confirm() {
        driver.findElement(PayAndConfirm).click();
    }
}