/**
 * 
 */
package speedtrace.readers;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jonathanvijayakumar
 *
 *         File: IRegexHelper.java Created: 17 Feb 2022 21:28:56
 */
public interface IRegexHelper {

	public List<String> validationRegex = new ArrayList<String>();

	public void setRegexsForValidation(List<String> regexList);

	public boolean isMatch(String text);

	public List<String> getMatches(String text);

	public String getMatch(String text);

	public List<String> getMatches(String text, List<String> validationRegex);

}
