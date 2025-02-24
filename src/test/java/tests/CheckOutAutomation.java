package tests;

import Pages.CartPage;
import Pages.CheckOutPage;
import Pages.HomePage;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

import org.apache.poi.ss.usermodel.*;

    public class CheckOutAutomation {
        WebDriver driver;
        WebDriverWait wait;
        HomePage homePage;

        CartPage cartPage;
        CheckOutPage checkoutPage;

        @BeforeClass
        public void setUp() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://automationexercise.com/");
            homePage = new HomePage(driver);
            cartPage = new CartPage(driver);
            checkoutPage = new CheckOutPage(driver);
        }

        @Test(dataProvider = "checkoutData")
        public void testCheckout(String nameOnCard, String cardNumber, String CVC, String Expiration_Month, String Expiration_Year) {
            homePage.goToProducts();
            cartPage.addToCart();
            checkoutPage.proceedToCheckout();
            checkoutPage.fillDetails(nameOnCard, cardNumber, CVC, Expiration_Month, Expiration_Year);
            checkoutPage.placeOrder();
            checkoutPage.confirm();
        }

        @DataProvider(name = "checkoutData")
        public Object[][] getCheckoutData() throws IOException {
            InputStream file = getClass().getClassLoader().getResourceAsStream("testdata.xlsx");

            if (file == null) {
                throw new RuntimeException("testdata.xlsx not found in resources folder!");
            }

            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                workbook.close();
                throw new RuntimeException("Excel sheet is empty or not found!");
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                workbook.close();
                throw new RuntimeException("Excel sheet has no data rows!");
            }

            int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
            Object[][] data = new Object[rowCount - 1][columnCount];

            for (int i = 1; i < rowCount; i++) { // Skip header row
                Row row = sheet.getRow(i);
                if (row == null) continue; // Skip empty rows

                for (int j = 0; j < columnCount; j++) {
                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    data[i - 1][j] = cell.toString().trim();
                }
            }
            workbook.close();
            return data;
        }



        @AfterClass
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }
