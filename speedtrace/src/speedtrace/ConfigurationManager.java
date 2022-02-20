/**
 * 
 */
package speedtrace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import speedtrace.readers.FileReader;
import speedtrace.requirements.RequirementsReader;

/**
 * Class that helps instantiate a requirements reader and apply the
 * configuration based on a JSON file.
 * 
 * @author jonathanvijayakumar
 *
 *         File: ConfigurationManager.java Created: 15 Feb 2022 23:51:15
 */
public class ConfigurationManager {

	private static JSONObject jsonData = null;

	/**
	 * Function loads configuration as soon as the application is loaded in memory
	 */
	public static void readConfigFile(String filePath) {
		try {
			FileReader fileReader = new FileReader(filePath);
			fileReader.createReader();

			jsonData = new JSONObject(fileReader.read());
		} catch (Exception e) {
			System.out.println("Error reading config file" + e.getMessage());
		}
	}

	static String readDesignPath() {
		return getAttrAsString("design");
	}

	/**
	 * Reads the paths specified for source code
	 * 
	 * @return
	 */
	public static List<String> readSourcePaths() {
		return readArray("source");
	}

	/**
	 * Reads the different comments support regular expressions.
	 * 
	 * @return
	 */
	public static Map<String, String> readCommentsRegex() {
		return readMap("comments_regex");
	}

	/**
	 * Configures the data for the reader and gets it ready for a read operation
	 * 
	 * @return
	 */
	static void readColumnData(RequirementsReader reader) {

		JSONArray jsonArray = jsonData.getJSONArray("files");

		for (int iter = 0; iter < jsonArray.length(); iter++) {
			JSONObject requirementData = (JSONObject) jsonArray.get(iter);
			String filePath = (String) requirementData.get("path");

			JSONArray columnData = (JSONArray) requirementData.get("data");
			readColumnData(reader, filePath, columnData);
		}
	}

	/**
	 * 
	 * @param reader
	 * @param filePath
	 * @param columnData
	 */
	private static void readColumnData(RequirementsReader reader, String filePath, JSONArray columnData) {
		for (int colIter = 0; colIter < columnData.length(); colIter++) {

			JSONObject sheet = (JSONObject) columnData.get(colIter);

			String sheetName = getAttrAsString("sheet_name", sheet);
			int rowNum = getAttrAsInt("row_num", sheet);
			int colNum = getAttrAsInt("col_num", sheet);

			reader.addColumnData(filePath, sheetName, rowNum, colNum);
		}
	}

	/**
	 * 
	 * @return
	 */
	public static List<String> readRegex() {

		return readArray("regex");
	}

	/**
	 * @param key
	 * @return
	 */
	public static List<String> readArray(String key) {
		List<String> regexList = new ArrayList<String>();
		JSONArray regexJSONData = jsonData.getJSONArray(key);

		for (int regexIter = 0; regexIter < regexJSONData.length(); regexIter++) {
			String regex = (String) regexJSONData.get(regexIter);

			regexList.add(regex);
		}

		return regexList;
	}

	/**
	 * @param key
	 * @return
	 */
	public static Map<String, String> readMap(String key) {
		Map<String, String> mapObject = new HashMap<String, String>();
		JSONArray regexJSONData = jsonData.getJSONArray(key);

		for (int regexIter = 0; regexIter < regexJSONData.length(); regexIter++) {
			JSONObject obj = (JSONObject) regexJSONData.get(regexIter);
			String keyStr = obj.keys().next();

			String valueObj = obj.getString(keyStr);
			mapObject.put(keyStr, valueObj);
		}

		return mapObject;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private static String getAttrAsString(String key) {
		return jsonData.getString(key);
	}

	/**
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	private static String getAttrAsString(String key, JSONObject object) {
		return object.getString(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private static int getAttrAsInt(String key) {
		return jsonData.getInt(key);
	}

	/**
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	private static int getAttrAsInt(String key, JSONObject object) {
		return object.getInt(key);
	}

}
