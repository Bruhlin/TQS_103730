package TQS.lab4.ex4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class BlazeDemoTest {
    private WebDriver driver;
    private BlazeDemoHomePage homePage;
    private BlazeDemoFlightsPage flightsPage;
    private BlazeDemoPurchasePage purchasePage;

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        homePage = new BlazeDemoHomePage(driver);
        flightsPage = new BlazeDemoFlightsPage(driver);
        purchasePage = new BlazeDemoPurchasePage(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testBookingFlight() {
        driver.get("https://blazedemo.com/");

        homePage.selectDepartureCity("San Diego");
        homePage.selectDestinationCity("London");
        homePage.searchFlights();

        flightsPage.chooseFlight();

        purchasePage.enterPassengerDetails("John Doe", "123 Main St", "Los Angeles", "CA", "90001");

        purchasePage.enterPaymentDetails("4111111111111111", "12", "2025", "John Doe");

        purchasePage.confirmPurchase();

        assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }
}
