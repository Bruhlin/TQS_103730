package TQS.lab4.ex1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class BuyingticketTest {
  private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void buyingTicket() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().maximize();

        // Select departure city
        WebElement departure = driver.findElement(By.name("fromPort"));
        departure.sendKeys("San Diego");
        
        // Select destination city
        WebElement destination = driver.findElement(By.name("toPort"));
        destination.sendKeys("London");

        // Submit flight search
        driver.findElement(By.cssSelector(".btn-primary")).click();

        // Select a flight
        driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();

        // Fill in passenger details
        driver.findElement(By.id("inputName")).sendKeys("Person");
        assertThat(driver.findElement(By.id("inputName")).getDomProperty("value")).isEqualTo("Person");

        driver.findElement(By.id("address")).sendKeys("123 Rua");
        assertThat(driver.findElement(By.id("address")).getDomProperty("value")).isEqualTo("123 Rua");

        driver.findElement(By.id("city")).sendKeys("Cidade");
        assertThat(driver.findElement(By.id("city")).getDomProperty("value")).isEqualTo("Cidade");

        driver.findElement(By.id("state")).sendKeys("Estado");
        assertThat(driver.findElement(By.id("state")).getDomProperty("value")).isEqualTo("Estado");

        driver.findElement(By.id("zipCode")).sendKeys("12345");
        
        // Select card type
        WebElement cardType = driver.findElement(By.id("cardType"));
        cardType.sendKeys("Diner's Club");
        assertThat(cardType.getDomProperty("value")).isEqualTo("dinersclub");

        driver.findElement(By.id("creditCardNumber")).sendKeys("38921739120381");
        assertThat(driver.findElement(By.id("creditCardNumber")).getDomProperty("value")).isEqualTo("38921739120381");

        driver.findElement(By.id("creditCardMonth")).clear();
        driver.findElement(By.id("creditCardMonth")).sendKeys("9");
        assertThat(driver.findElement(By.id("creditCardMonth")).getDomProperty("value")).isEqualTo("9");

        driver.findElement(By.id("creditCardYear")).clear();
        driver.findElement(By.id("creditCardYear")).sendKeys("2019");
        assertThat(driver.findElement(By.id("creditCardYear")).getDomProperty("value")).isEqualTo("2019");

        driver.findElement(By.id("nameOnCard")).sendKeys("Person");
        assertThat(driver.findElement(By.id("nameOnCard")).getDomProperty("value")).isEqualTo("Person");

        driver.findElement(By.id("rememberMe")).click();

        // Submit purchase
        driver.findElement(By.cssSelector(".btn-primary")).click();

        // Assert confirmation page title
        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
  }
}
