package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By viewCartLink = By.xpath("//a[@href='/view_cart']");

    public void addToCart() {
        driver.findElement(viewCartLink).click();
    }
}