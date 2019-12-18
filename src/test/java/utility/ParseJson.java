package utility;

import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * For a given json object all the xpath are extract with key and value stored and returned as Map
 * Nested are taken care
 *
 */
public class ParseJson {
	
	/**
	 * Get all the Xpath and returned as Map
	 * @param json
	 * @param keyValueStore
	 * @param keyPath
	 * @return
	 */
	public static Map<String, String> getAllXpathAndValueFromJsonObject(JSONObject json,
			Map<String, String> keyValueStore, Stack<String> keyPath) {
		Set<String> jsonKeys = json.keySet();
		for (Object keyO : jsonKeys) {
			String key = (String) keyO;
			keyPath.push(key);
			Object object = json.get(key);
			if (object instanceof JSONObject) {
				getAllXpathAndValueFromJsonObject((JSONObject) object, keyValueStore, keyPath);
			}
			if (object instanceof JSONArray) {
				doJsonArray((JSONArray) object, keyPath, keyValueStore, json, key);
			}
			if (object instanceof String || object instanceof Boolean || object.equals(null)
					|| object instanceof Integer) {
				String keyStr = "";
				for (String keySub : keyPath) {
					keyStr += keySub + ".";
				}
				keyStr = keyStr.substring(0, keyStr.length() - 1);
				keyPath.pop();
				keyValueStore.put(keyStr, json.get(key).toString());
			}
		}
		if (keyPath.size() > 0) {
			keyPath.pop();
		}
		return keyValueStore;
	}

	/**
	 * For JsonArray ,we are find the paths
	 * @param object
	 * @param keyPath
	 * @param keyValueStore
	 * @param json
	 * @param key
	 */
	public static void doJsonArray(JSONArray object, Stack<String> keyPath, Map<String, String> keyValueStore,
			JSONObject json, String key) {
		JSONArray arr = (JSONArray) object;
		for (int i = 0; i < arr.length(); i++) {
			keyPath.push(Integer.toString(i));
			Object obj = arr.get(i);
			if (obj instanceof JSONObject) {
				getAllXpathAndValueFromJsonObject((JSONObject) obj, keyValueStore, keyPath);
			}
			if (obj instanceof JSONArray) {
				doJsonArray((JSONArray) obj, keyPath, keyValueStore, json, key);
			}
			if (obj instanceof String || obj instanceof Boolean || obj.equals(null) || obj instanceof Integer) {
				String keyStr = "";
				for (String keySub : keyPath) {
					keyStr += keySub + ".";
				}
				keyStr = keyStr.substring(0, keyStr.length() - 1);
				keyPath.pop();
				keyValueStore.put(keyStr, json.get(key).toString());
			}
		}
		if (keyPath.size() > 0) {
			keyPath.pop();
		}
	}

}
