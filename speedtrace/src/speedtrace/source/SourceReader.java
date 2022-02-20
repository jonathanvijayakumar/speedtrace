/**
 * 
 */
package speedtrace.source;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import speedtrace.readers.FileReader;
import speedtrace.readers.IFileReader;
import speedtrace.readers.IRegexHelper;
import speedtrace.readers.ISourceReader;
import speedtrace.readers.RegexHelper;

/**
 * @author jonathanvijayakumar
 *
 *         File: SourceReader.java Created: 18 Feb 2022 00:51:17
 */
public class SourceReader implements ISourceReader {

	IFileReader fileReader;
	IRegexHelper regexHelper;

	/**
	 * 
	 */
	public SourceReader() {
		fileReader = new FileReader();
		regexHelper = new RegexHelper();
	}

	@Override
	public void addFiles(List<String> fileList) {
		files.addAll(fileList);
	}

	@Override
	public Set<String> readRequirements() {

		Set<String> requirements = new HashSet<String>();
		List<String> tempRegexList = new ArrayList<String>();

		Set<Path> recursivelyCollectedFiles = new HashSet<Path>();
		for (String file : files) {
			recursivelyCollectedFiles.addAll(FileReader.getFilesFromDir(file));
		}

		for (Path file : recursivelyCollectedFiles) {
			fileReader.open(file.toAbsolutePath().toString());

			String fileText = fileReader.read();
			String fileType = getFileType(file.toAbsolutePath().toString().toUpperCase());

			tempRegexList.clear();
			tempRegexList.add(ISourceReader.languageComments.get(fileType));
			
			List<String> comments = regexHelper.getMatches(fileText, tempRegexList);

			for (String comment : comments) {
				List<String> ids = regexHelper.getMatches(comment, ISourceReader.regexForRequirements);
				requirements.addAll(ids);
			}
		}
		return requirements;
	}

	public String getFileType(String file) {
		return FilenameUtils.getExtension(file);
	}

	@Override
	public void setRegexForLanguageSupport(Map<String, String> languageComments) {
		ISourceReader.languageComments.clear();
		ISourceReader.languageComments.putAll(languageComments);
	}

	@Override
	public void setRegexForRequirements(List<String> regexList) {
		ISourceReader.regexForRequirements.clear();
		ISourceReader.regexForRequirements.addAll(regexList);

	}

}
