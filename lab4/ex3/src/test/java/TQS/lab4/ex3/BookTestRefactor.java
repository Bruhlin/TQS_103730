package TQS.lab4.ex3;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BookTestRefactor {
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
    void testBookSearch() {
        driver.get("https://cover-bookstore.onrender.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='book-search-input']")));
        searchBox.sendKeys("Harry Potter");

        WebElement searchButton = driver.findElement(By.cssSelector((".Navebar_searchBtnIcon__25k0u")));
        searchButton.click();

        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));

        WebElement bookTitle = searchResult.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a"));
        WebElement bookAuthor = searchResult.findElement(By.cssSelector(".SearchList_bookAuthor__3giPc"));

        assertThat(bookTitle.getText()).isEqualTo("Harry Potter and the Sorcerer's Stone");
        assertThat(bookAuthor.getText()).isEqualTo("J.K. Rowling");
    }
}
