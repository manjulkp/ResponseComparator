package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadConfiguration {

	private static final Properties properties = new Properties();

	public static void loadUserConfigProp(String propertyFileName) throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/properties/" + propertyFileName);
		properties.load(fis);
		System.out.println("The user configuration is loaded ");
	}

	public static Map<String, String> getConfiguration() {
		Map<String, String> propertyMap = new HashMap();
		Enumeration keys = properties.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			propertyMap.put(key, properties.getProperty(key));
		}
		return propertyMap;
	}

	public static String getConfigurationValue(String key) {
		return properties.getProperty(key);
	}

}