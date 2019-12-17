package utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.json.JSONObject;

import io.restassured.response.Response;

public class ReadContentFromFile {

	private BufferedReader reader1;
	
	private BufferedReader reader2;
	
	
	public BufferedReader getReader1() {
		return reader1;
	}

	public void setReader1(BufferedReader reader1) {
		this.reader1 = reader1;
	}

	public BufferedReader getReader2() {
		return reader2;
	}

	public void setReader2(BufferedReader reader2) {
		this.reader2 = reader2;
	}

	

	/**
	 * 
	 * @throws IOException
	 */
	public void loadFile() throws IOException {
		reader1 = new BufferedReader(
				new FileReader(System.getProperty("user.dir") + "/src/test/resources/properties/Config1.txt"));
		reader2 = new BufferedReader(
				new FileReader(System.getProperty("user.dir") + "/src/test/resources/properties/Config2.txt"));
	}

	/**
	 * 
	 * @param reader1
	 * @param reader2
	 * @return 
	 * @throws IOException
	 */
	public boolean compareEachReponse(BufferedReader reader1, BufferedReader reader2) throws IOException {
		String line1 = reader1.readLine();
		String line2 = reader2.readLine();
		boolean areEqual = false;
		boolean finalStatus = true;
		int lineNum = 1;
		while (line1 != null || line2 != null) {
			if (line1 == null || line1.trim().isEmpty() || line2 == null || line2.trim().isEmpty()) {
				areEqual = false;
				
			} else {
				Response res1 = null;
				Response res2 = null;
				try {
					res1 = new RestAssuredHelper().getResponse(line1);
					res2 = new RestAssuredHelper().getResponse(line2);
					String jsonOfFile1 = new RestAssuredHelper().getResponseAsString(res1);
					String jsonOfFile2 = new RestAssuredHelper().getResponseAsString(res2);
					areEqual = getData(jsonOfFile1, jsonOfFile2);
				} catch (IncorrectHttpProtocolException e) {
					e.printStackTrace();
				}
			}
			getStatusPrint(areEqual, line1, line2, lineNum);
			if(!areEqual)
			{
				finalStatus = false;
			}
			areEqual=false;
			line1 = reader1.readLine();
			line2 = reader2.readLine();
			lineNum++;
		}
		reader1.close();
		reader2.close();
		return finalStatus;
		
	}

	/**
	 * 
	 * @param status
	 * @param line1
	 * @param line2
	 * @param lineNum
	 */
	private void getStatusPrint(boolean status, String line1, String line2, int lineNum) {
		System.out.println(status ? line1 + "response is equal to response of " + line2
				: line1 + "response is not equal to response of " + line2 + " at line number" + lineNum);
		System.out.println("\n");
	}

	/**
	 * 
	 * @param json1
	 * @param json2
	 * @return
	 */
	private boolean getData(String json1, String json2) {
		Map<String, String> uniquePath1 = publishAllXpathInResponse(json1);
		Map<String, String> uniquePath2 = publishAllXpathInResponse(json2);
		return compareMap(uniquePath1, uniquePath2);
	}

	/**
	 * 
	 * @param map1
	 * @param map2
	 * @return
	 */
	private boolean compareMap(Map<String, String> map1, Map<String, String> map2) {
		if (map1 == null || map2 == null)
			return false;
		for (String ch1 : map1.keySet()) {
			if (!map1.get(ch1).equalsIgnoreCase(map2.get(ch1)))
				return false;
		}
		for (String ch2 : map2.keySet()) {
			if (!map2.get(ch2).equalsIgnoreCase(map1.get(ch2)))
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	private static Map<String, String> publishAllXpathInResponse(String s) {
		Map<String, String> keyValueStore = new HashMap<String, String>();
		Stack<String> keyPath = new Stack<String>();
		JSONObject json = new JSONObject(s);
		keyValueStore = ParseJson.getAllXpathAndValueFromJsonObject(json, keyValueStore, keyPath);
		for (Map.Entry<String, String> map : keyValueStore.entrySet()) {
			 System.out.println("The Key is " + map.getKey() + "-------------the value is" + map.getValue());
		}
		return keyValueStore;
	}

}
