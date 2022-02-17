/**
 * 
 */
package speedtrace.design;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sparx.Collection;
import org.sparx.Diagram;
import org.sparx.Package;
import org.sparx.Repository;

import speedtrace.readers.IDesignReader;

/**
 * Enterprise Architect reader.
 * 
 * @author jonathanvijayakumar
 *
 *         File: EADesignReader.java Created: 17 Feb 2022 03:04:37
 */
public class EADesignReader implements IDesignReader {

	private File designFile;
	private Repository repo;
	private List<String> validationRegex;

	/**
	 * @param regexList
	 * 
	 */
	public EADesignReader(List<String> regexList) {
		designFile = null;
		repo = null;
		validationRegex = regexList;
	}

	@Override
	public void createReader() {
		repo = new Repository();
		repo.OpenFile(designFile.getAbsolutePath());
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(String filePath) {
		File file = new File(filePath);
		if (!file.exists() || file.isDirectory()) {
			return;
		}

		designFile = file;
	}

	@Override
	public Set<String> readRequirements() {

		if (repo == null) {
			return Collections.emptySet();
		}

		Set<String> requirementsSet = new HashSet<String>();

		Collection<Package> models = repo.GetModels();
		for (Package model : models) {
			Collection<Package> packages = model.GetPackages();

			for (Package pkg : packages) {
				Collection<Diagram> diagrams = pkg.GetDiagrams();

				for (Diagram diagram : diagrams) {
					requirementsSet.addAll(validateAndExtract(diagram.GetNotes()));
				}
			}
		}
		return requirementsSet;
	}

	/**
	 * 
	 * @param text
	 * @return
	 */
	public List<String> validateAndExtract(String text) {

		List<String> matchesList = new ArrayList<String>();

		for (String requirementPattern : validationRegex) {

			Pattern pattern = Pattern.compile(requirementPattern);
			Matcher matcher = pattern.matcher(text);
			while (matcher.find()) {
				matchesList.add(matcher.group());
			}
		}

		return matchesList;
	}

}
