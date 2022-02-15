/**
 * 
 */
package speedtrace;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jonathanvijayakumar
 *
 *         File: SpeedTraceHome.java Created: 15 Feb 2022 00:58:07
 */
public class SpeedTraceHome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		RequirementsReader reader = createAndConfigReader();

		// Rerun the following two functions every time configuration is changed
		reader.createReader();
		List<String> requirementsList = reader.readRequirements();

		for (String requirements : requirementsList) {
			System.out.println(requirements);
		}
	}

	/**
	 * Configures the data for the reader and gets it ready for a read operation
	 * 
	 * @return
	 */
	private static RequirementsReader createAndConfigReader() {

		RequirementsReader reader = new RequirementsReader();
		List<String> input = new ArrayList<String>();

		input.add(
				"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\test.xlsx");
		input.add(
				"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\test_sheet_two.xlsx");

		reader.addFiles(input);

		reader.addColumnData(
				"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\test.xlsx",
				"headLamp", 1, 1);

		reader.addColumnData(
				"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\test.xlsx",
				"tailLamp", 1, 1);

		reader.addColumnData(
				"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\test_sheet_two.xlsx",
				"engineCooling", 1, 1);

		reader.addColumnData(
				"C:\\Personal Files\\Jonathan\\Work\\github\\traceability\\speedtrace\\speedtrace\\test-data\\test_sheet_two.xlsx",
				"batterySystem", 1, 1);

		List<String> regexList = new ArrayList<String>();

		regexList.add("ID\\_\\d+");
		regexList.add("ID\\_BS\\_\\d+");
		regexList.add("ID\\_EC\\_\\d+");
		reader.setRegexsForValidation(regexList);

		return reader;
	}

}
