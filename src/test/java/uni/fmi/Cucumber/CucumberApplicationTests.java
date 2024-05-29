package uni.fmi.Cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/WebMenu.html"}, features = {"classpath:features"}, glue = {"io.cucumber.spring","uni.fmi.Cucumber.steps"})
class CucumberApplicationTests {
}
