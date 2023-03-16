package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features="src//test//resources//features//DemoBlazeRun.feature",
		glue= {"stepDefs"},
		monochrome=true,
		dryRun=false,
		plugin= {"pretty",
				"html:target//Reports//HTMLReport.html"}
		)

public class DemoBlazeRunner extends AbstractTestNGCucumberTests{
  
}
