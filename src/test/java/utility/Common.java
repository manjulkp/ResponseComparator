package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;

public class Common {

	public static RequestSpecification Request;
	public static Map<String, String> file1Content;
	public static Map<String, String> file2Content;

	public void getRequestURLFromFile(String file1, String file2) throws IOException {
		ReadConfiguration.loadUserConfigProp(file1);

		file1Content = ReadConfiguration.getConfiguration();

		ReadConfiguration.loadUserConfigProp(file2);

		file2Content = ReadConfiguration.getConfiguration();

	}

	public void compareEachReponse(Map<String, String> file1Content, Map<String, String> file2Content) {
		boolean status = false;

		if (file1Content.size() == file2Content.size()) {

			for (Map.Entry<String, String> entry1 : file1Content.entrySet()) {
				String key = entry1.getKey();
				String value1 = entry1.getValue();
				String value2 = null;
				// do whatever with value1 and value2

				if (value1 != null && !value1.trim().isEmpty()
						&& (value1.startsWith("http") || value1.startsWith("https"))) {

					value2 = file2Content.get(key);

					if (value2 != null && !value2.trim().isEmpty()
							&& (value2.startsWith("http") || value2.startsWith("https"))) {

//						String jsonOfFile1 = new RestAssuredHelper().getResponse(value1);
//						String jsonOfFile2 = new RestAssuredHelper().getResponse(value1);
						//boolean match = getData(jsonOfFile1, jsonOfFile2);

						//status = match;

					}

				}

				getStatusPrint(status, value1, value2);
				status = false;

			}

		}
	}

	private void getStatusPrint(boolean status, String value1, String value2) {

		System.out.println(status ? value1 + "response is equal to response of " + value2
				: value1 + "response is not equal to response of " + value2);
	}

	private boolean getData(String json1, String json2) {
		Map<String, String> uniquePath1 = publishAllXpathInResponse(json1);
		Map<String, String> uniquePath2 = publishAllXpathInResponse(json2);
		return compareMap(uniquePath1, uniquePath2);
	}

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

	private static Map<String, String> publishAllXpathInResponse(String s) {
		Map<String, String> keyValueStore = new HashMap<String, String>();
		Stack<String> keyPath = new Stack<String>();
		JSONObject json = new JSONObject(s);
		keyValueStore = ParseJson.getAllXpathAndValueFromJsonObject(json, keyValueStore, keyPath);
		for (Map.Entry<String, String> map : keyValueStore.entrySet()) {
			// System.out.println("The Key is " + map.getKey() + "-------------the value is
			// " + map.getValue());
		}
		return keyValueStore;
	}

}
