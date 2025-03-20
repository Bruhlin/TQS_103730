package TQS.lab5.ex3;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("TQS/lab5/ex3")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "TQS/lab5/ex3")
public class BookSearchTest {

}
