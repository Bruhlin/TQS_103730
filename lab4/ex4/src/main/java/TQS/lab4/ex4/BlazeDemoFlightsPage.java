package TQS.lab4.ex4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeDemoFlightsPage {
    private WebDriver driver;

    @FindBy(css = "tr:nth-child(3) .btn")
    private WebElement selectFlightButton;

    public BlazeDemoFlightsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void chooseFlight() {
        selectFlightButton.click();
    }
}
