/**
 * 
 */
package speedtrace.readers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex processing functions
 * 
 * @author jonathanvijayakumar
 *
 *         File: RegexHelper.java Created: 17 Feb 2022 21:32:30
 */
public class RegexHelper implements IRegexHelper {

	/**
	 * 
	 */
	public RegexHelper() {
		validationRegex.clear();
	}

	/**
	 * @param regexList
	 * 
	 */
	public RegexHelper(List<String> regexList) {
		validationRegex.clear();
		validationRegex.addAll(regexList);
	}

	@Override
	public void setRegexsForValidation(List<String> regexList) {
		validationRegex.clear();
		validationRegex.addAll(regexList);
	}

	@Override
	public boolean isMatch(String text) {
		for (String regex : validationRegex) {
			if (regex.matches(regex)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getMatches(String text) {
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

	@Override
	public String getMatch(String text) {
		for (String patternRgx : validationRegex) {
			if (text.matches(patternRgx)) {
				Pattern pattern = Pattern.compile(patternRgx);
				Matcher matcher = pattern.matcher(text);
				if (matcher.find()) {
					return matcher.group();
				}
			}
		}

		return "";
	}

	@Override
	public List<String> getMatches(String text, List<String> validationRegex) {
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

	public List<String> getRegexList() {
		return validationRegex;
	}

}
