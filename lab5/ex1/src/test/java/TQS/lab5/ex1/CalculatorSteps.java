package TQS.lab5.ex1;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;
    private Exception capturedException;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I subtract {int} from {int}")
    public void subtract(int arg1, int arg2) {
        log.debug("Subtracting {} from {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }
    @When("I multiply {int} and {int}")
    public void multiply(int arg1, int arg2) {
        log.debug("Multiplying {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int arg1, int arg2) {
        log.debug("Dividing {} by {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("/");
    }

    @When("I try to perform an invalid operation {string}")
    public void invalid(String op) {
        log.debug("Trying to perform an invalid operation: {}", op);
        try {
            calc.push(op);
            fail("Expected an exception for invalid operation: " + op);
        } catch (Exception e) {
            capturedException = e;
            log.debug("Captured exception: {}", e.getMessage());

        }
    }

    @Then("the result is {int}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

    @Then("an error should be thrown")
    public void an_error_should_be_thrown() {
        assertNotNull(capturedException ,"Expected an exception to be captured");
    }
}
