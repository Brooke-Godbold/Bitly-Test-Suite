package core;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber-report/cucumber.json"},
        features = "src/test/resources/feature",
        glue = "steps"
)
public class RunCuumberTest {
}
