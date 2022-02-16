/**
 * 
 */
package speedtrace;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class that helps instantiate a requirements reader and apply the
 * configuration based on a JSON file.
 * 
 * @author jonathanvijayakumar
 *
 *         File: RequirementsHelper.java Created: 15 Feb 2022 23:51:15
 */
public class RequirementsHelper {

	/**
	 * 
	 */
	public RequirementsHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Configures the data for the reader and gets it ready for a read operation
	 * 
	 * @return
	 */
	static RequirementsReader getRequirementsReader() {

		RequirementsReader reader = new RequirementsReader();

		List<String> input = new ArrayList<String>();
		List<String> regexList = new ArrayList<String>();

		try {
			FileReader fileReader = new FileReader(
					"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\data.json");
			fileReader.createReader();

			JSONObject jsonData = new JSONObject(fileReader.read());
			readFileData(reader, input, jsonData);

			reader.addFiles(input);
			JSONArray regexJSONData = jsonData.getJSONArray("regex");

			for (int regexIter = 0; regexIter < regexJSONData.length(); regexIter++) {
				String regex = (String) regexJSONData.get(regexIter);

				regexList.add(regex);
			}

			reader.setRegexsForValidation(regexList);

		} catch (Exception e) {
			System.out.println("Error reading config file" + e.getMessage());
		}

		return reader;
	}

	/**
	 * 
	 * @param reader
	 * @param input
	 * @param jsonData
	 */
	private static void readFileData(RequirementsReader reader, List<String> input, JSONObject jsonData) {

		JSONArray jsonArray = jsonData.getJSONArray("files");

		for (int iter = 0; iter < jsonArray.length(); iter++) {
			JSONObject requirementData = (JSONObject) jsonArray.get(iter);

			String filePath = (String) requirementData.get("path");
			input.add(filePath);

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
			String sheetName = (String) sheet.get("sheet_name");

			int rowNum = (int) sheet.get("row_num");
			int colNum = (int) sheet.get("col_num");

			reader.addColumnData(filePath, sheetName, rowNum, colNum);
		}
	}

}
