package TQS.hw1.ui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

class FunctionalUITest {

    private static WebDriver driver;
    private static String token;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    @Order(1)
    void testBookMealAndGetToken() {
        driver.get("http://localhost:8080/index/index.html");

        WebElement dropdown = driver.findElement(By.id("restaurant"));
        dropdown.sendKeys(("Cantina Castro"));

        WebElement checkButton = driver.findElement(By.xpath("//button[contains(text(), 'Check Meals')]"));
        checkButton.click();

        WebElement reservationButton = driver.findElement(By.xpath("//button[contains(text(), 'Make a reservation')]"));
        reservationButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();

        assertTrue(alertText.contains("Token : "));
        token = alertText.substring(alertText.indexOf("Token : ") + 8).trim();

        assertNotNull(token);
        assertFalse(token.isEmpty());
        System.out.println("Token : " + token);
    }

    @Test
    @Order(2)
    void testCheckinPageWithToken() {
        driver.get("http://localhost:8080/checkin/checkin.html");

        WebElement input = driver.findElement(By.id("token"));
        input.sendKeys(token);

        WebElement verifyButton = driver.findElement(By.xpath("//button[contains(text(), 'Verify')]"));
        verifyButton.click();

        WebElement result = driver.findElement(By.id("resultado"));
        assertTrue(result.getText().contains("CantinaCastro"));
        assertTrue(result.getText().contains("Used? No"));
        
        WebElement markButton = driver.findElement(By.xpath("//button[contains(text(), 'Mark as used')]"));
        assertNotNull(markButton);
        markButton.click();

        WebElement greenText = driver.findElement(By.xpath("//*[contains(text(), 'Reservation marked as used')]"));

        assertNotNull(greenText);

        assertTrue(result.getText().contains("Used? Yes"));
    }

    @Test
    @Order(3)
    void testCancelReservation() {
        driver.get("http://localhost:8080/index/index.html");

        WebElement tokenInput = driver.findElement(By.id("cancelToken"));
        tokenInput.sendKeys(token);

        WebElement cancelButton = driver.findElement(By.xpath("//button[contains(text(), 'Cancel Reservation')]"));
        cancelButton.click();

        WebElement cancelResult = driver.findElement(By.id("cancelResult"));

        assertTrue(cancelResult.getText().contains("Reservation cancelled successfully"));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
