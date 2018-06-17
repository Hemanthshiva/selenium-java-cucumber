package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:cucumber",
        glue = "steps",
        tags = {"not @wip", "not @ignore", "not @merged"},
        monochrome = true,
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json"
//                "rerun:target/cucumber-reports/rerun.txt"
        })
public class TestRunner {
}