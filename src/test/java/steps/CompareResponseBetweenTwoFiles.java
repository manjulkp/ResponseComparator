package steps;

import java.io.IOException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import utility.ReadContentFromFile;

public class CompareResponseBetweenTwoFiles {
	ReadContentFromFile fileHandler = new ReadContentFromFile();

	@Given("I load the two files that has the request")
	public void i_load_the_two_files_that_has_the_request() {
		try {
			fileHandler.loadFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("compare tool prints the two response has matched or not")
	public void compare_tool_prints_the_two_response_has_matched_or_not() throws IOException {
		fileHandler.compareEachReponse(fileHandler.getReader1(), fileHandler.getReader2());
	}

}