package TQS.lab4.ex1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class HelloSeleniumJupiterTest {

    @Test
    void test(FirefoxDriver driver) {
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();

        // Verify the main page title
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");

        // Locate and click on "Slow calculator" link
        WebElement slowCalculatorLink = driver.findElement(By.linkText("Slow calculator"));
        slowCalculatorLink.click();

        // Wait for navigation
        try {
            Thread.sleep(2000); // Consider using WebDriverWait instead
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert correct page navigation
        String expectedUrl = "https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html";
        assertThat(driver.getCurrentUrl()).isEqualTo(expectedUrl);
    }
}