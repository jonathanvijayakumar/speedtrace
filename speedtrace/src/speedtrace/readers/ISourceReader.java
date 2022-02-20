/**
 * 
 */
package speedtrace.readers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jonathanvijayakumar
 *
 *         File: ISourceReader.java Created: 18 Feb 2022 00:46:39
 */
public interface ISourceReader {

	public static final Map<String, String> languageComments = new HashMap<String, String>();
	public static final Set<String> files = new HashSet<String>();
	public static final List<String> regexForRequirements = new ArrayList<String>();

	public void addFiles(List<String> files);

	public Set<String> readRequirements();

	public void setRegexForLanguageSupport(Map<String, String> languageComments);

	public void setRegexForRequirements(List<String> regexList);
}
