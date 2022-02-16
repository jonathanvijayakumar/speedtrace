package speedtrace;

import java.util.List;

/**
 * Class for reading Excel format requirements and processing them!
 * 
 * @author jonathanvijayakumar
 *
 *         File: SpeedTraceHome.java Created: 15 Feb 2022 00:58:07
 */
public class SpeedTraceHome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		RequirementsReader reader = RequirementsHelper.getRequirementsReader();

		// Rerun the following two functions every time configuration is changed
		reader.createReader();
		List<String> requirementsList = reader.readRequirements();

		for (String requirements : requirementsList) {
			System.out.println(requirements);
		}
	}

}
