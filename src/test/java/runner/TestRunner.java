package runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(strict = true, monochrome = true, glue = "steps", features = "src/test/resources/feature", tags = {
		"@Compare" }, plugin = { "pretty", "html:target/cucumber-reports",
				"json:target/cucumber-reports/cucumber.json" })

public class TestRunner {

	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		testNGCucumberRunner.finish();
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleEventWrapper pickleEvent, CucumberFeatureWrapper cucumberFeature) throws Throwable {
		testNGCucumberRunner.runScenario(pickleEvent.getPickleEvent());
	}

	@DataProvider
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterSuite
	public void generateReport() {
		File reportOutputDirectory = new File("target"); //
		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add("target/cucumber-reports/cucumber.json");
		String projectName = "Automation";
		String buildNumber = "1.0";
		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		configuration.setBuildNumber(buildNumber);
		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();
	}

}
