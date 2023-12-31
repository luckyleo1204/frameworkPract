package com.murali.ecomercesite;

import com.murali.ecomercesite.pageObjects.LandingPage;
import com.murali.ecomercesite.pageObjects.ProductCatalog;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static java.util.spi.ToolProvider.findFirst;

public class E2ETest {

    public static void main(String args[]) throws InterruptedException {

        String productName = "ZARA COAT 3";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client");
        LandingPage lp=new LandingPage(driver);
        lp.loginApp("cp.murali@gmail.com","Harshi@123");
        ProductCatalog pc=new ProductCatalog(driver);
        pc.addProductToCart(productName);

        driver.findElement(By.cssSelector("[routerlink*=cart]")).click();

        List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = 	cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);
      //  wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()='Checkout']"))));
        JavascriptExecutor js =(JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Checkout']")));

        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
       // driver.findElement(By.cssSelector(".action__submit")).click();
        js.executeScript("arguments[0].click();", driver.findElement(By.cssSelector(".action__submit")));

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Thread.sleep(5000L);
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();



    }
}
