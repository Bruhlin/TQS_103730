package TQS.lab4.ex3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BookTest {
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
    public void bookSearchTest() {
        driver.get("https://cover-bookstore.onrender.com/");

        WebElement searchBox = driver.findElement(By.xpath("//input[@data-testid='book-search-input']"));
        searchBox.sendKeys("Harry Potter");

        WebElement searchButton = driver.findElement(By.cssSelector(".Navbar_searchBtnIcon__25k0u"));
        searchButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement bookTitle = driver.findElement(By.xpath("//span[contains(text(), \"Harry Potter and the Sorcerer's Stone\")]"));
        WebElement bookAuthor = driver.findElement(By.xpath("//span[contains(text(), 'J.K. Rowling')]"));

        assertTrue(bookTitle.isDisplayed());
        assertTrue(bookAuthor.isDisplayed());
    }
}
