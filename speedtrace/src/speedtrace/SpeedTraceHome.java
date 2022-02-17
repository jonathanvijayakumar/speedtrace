package speedtrace;

import java.util.Set;

import speedtrace.design.EADesignReader;
import speedtrace.requirements.RequirementsReader;

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

		ConfigurationManager.readConfigFile(args[0]);

		/*
		 * The requirements document section
		 */
		Set<String> requirementsFromSpec = readRequirementsFromSpec();

		/*
		 * The requirements from design
		 */
		Set<String> requirementsFromDesign = readRequirementsFromDesign();

		/*
		 * Traceability operation
		 */
		requirementsFromSpec.removeAll(requirementsFromDesign);

		System.out.println("Requirements not covered in the design:");

		for (String req : requirementsFromSpec) {
			System.out.println(req);
		}
	}

	/**
	 * @return
	 */
	public static Set<String> readRequirementsFromDesign() {
		EADesignReader designReader = new EADesignReader(ConfigurationManager.readRegex());
		designReader.open(ConfigurationManager.readDesignPath());

		designReader.createReader();
		Set<String> requirementsFromDesign = designReader.readRequirements();
		return requirementsFromDesign;
	}

	/**
	 * @return
	 */
	public static Set<String> readRequirementsFromSpec() {

		RequirementsReader reader = new RequirementsReader();
		loadConfiguration(reader);

		reader.createReader(); // Rerun on change
		Set<String> requirementsFromSpec = reader.readRequirements(); // Rerun on change
		return requirementsFromSpec;
	}

	/**
	 * Loads the configuration from a JSON file
	 * 
	 * @param reader
	 */
	public static void loadConfiguration(RequirementsReader reader) {
		ConfigurationManager.readColumnData(reader);
		reader.setRegexsForValidation(ConfigurationManager.readRegex());
	}
}
