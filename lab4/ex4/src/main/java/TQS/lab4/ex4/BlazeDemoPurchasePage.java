package TQS.lab4.ex4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlazeDemoPurchasePage {
    private WebDriver driver;

    @FindBy(id = "inputName")
    private WebElement nameField;

    @FindBy(id = "address")
    private WebElement addressField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "state")
    private WebElement stateField;

    @FindBy(id = "zipCode")
    private WebElement zipCodeField;

    @FindBy(id = "cardType")
    private WebElement cardTypeDropdown;

    @FindBy(id = "creditCardNumber")
    private WebElement cardNumberField;

    @FindBy(id = "creditCardMonth")
    private WebElement cardMonthField;

    @FindBy(id = "creditCardYear")
    private WebElement cardYearField;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardField;

    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseButton;

    public BlazeDemoPurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterPassengerDetails(String name, String address, String city, String state, String zip) {
        nameField.sendKeys(name);
        addressField.sendKeys(address);
        cityField.sendKeys(city);
        stateField.sendKeys(state);
        zipCodeField.sendKeys(zip);
    }

    public void enterPaymentDetails(String cardNumber, String month, String year, String cardHolderName) {
        cardNumberField.sendKeys(cardNumber);
        cardMonthField.sendKeys(month);
        cardYearField.sendKeys(year);
        nameOnCardField.sendKeys(cardHolderName);
    }

    public void confirmPurchase() {
        rememberMeCheckbox.click();
        purchaseButton.click();
    }
}
