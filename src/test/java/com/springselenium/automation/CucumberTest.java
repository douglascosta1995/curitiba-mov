package com.springselenium.automation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/com/springselenium/automation/cucumber/features"},
        glue = {"com.springselenium.automation.cucumber", "com.springselenium.automation.config"},
        tags = "@bookVolleyball",
        plugin = {"pretty"}
)
public class CucumberTest {
}


