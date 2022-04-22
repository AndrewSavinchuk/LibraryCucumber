package com.library.runner;




import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions( features="src/test/resources/features" ,
                  glue="com/library/step_definitions",
                   publish = true,
                  plugin = {"html:target/cucumber.html" ,
                          "rerun:target/rerun.txt"
                          // store the failed scenario into rerun.txt

                  },


           dryRun = false,
          tags="@wip"

)
public class TestRunner {
}
