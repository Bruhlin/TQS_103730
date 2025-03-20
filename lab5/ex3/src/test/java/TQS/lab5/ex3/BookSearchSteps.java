package TQS.lab5.ex3;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookSearchSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Given("I am on the bookstore homepage")
    public void i_am_on_the_book_search_page() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("I search for {string}")
    public void i_search_for(String string) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='book-search-input']")));
        searchBox.sendKeys(string);

        WebElement searchButton = driver.findElement(By.cssSelector((".Navbar_searchBtnIcon__25k0u")));
        searchButton.click();
    }

    @Then("I should see a book with title {string}")
    public void i_should_see_a_book_with_title(String string) {
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));

        WebElement bookTitle = searchResult.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a"));
        assertThat(bookTitle.getText()).isEqualTo(string);
    }

    @And("the author should be {string}")
    public void the_author_should_be(String string) {
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));

        WebElement bookAuthor = searchResult.findElement(By.cssSelector(".SearchList_bookAuthor__3giPc"));
        assertThat(bookAuthor.getText()).isEqualTo(string);
    }

    @Then("I should see a message indicating no books are found")
    public void verifyNoBooksFoundMessage() {
        WebElement image = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".SearchList_emptySearchImage__3R6v8")));
        assertThat(image.isDisplayed()).isTrue();
    }

}
