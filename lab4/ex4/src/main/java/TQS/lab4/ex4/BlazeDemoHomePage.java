package TQS.lab4.ex4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BlazeDemoHomePage {
    private WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromDropdown;

    @FindBy(name = "toPort")
    private WebElement toDropdown;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsButton;

    public BlazeDemoHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectDepartureCity(String city) {
        new Select(fromDropdown).selectByVisibleText(city);
    }

    public void selectDestinationCity(String city) {
        new Select(toDropdown).selectByVisibleText(city);
    }

    public void searchFlights() {
        findFlightsButton.click();
    }
}
