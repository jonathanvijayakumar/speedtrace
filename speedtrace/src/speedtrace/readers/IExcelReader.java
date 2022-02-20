/**
 * 
 */
package speedtrace.readers;

import java.util.List;
import java.util.Set;

/**
 * @author jonathanvijayakumar
 *
 *         File: IExcelReader.java Created: 15 Feb 2022 01:04:01
 */
public interface IExcelReader extends IFileReader {

//	/**
//	 * To add files to be read, files where one may find requirements
//	 * 
//	 * @param files
//	 */
//	public void addFiles(List<String> files);
//	
//	public void setFiles(List<String> files);

	/**
	 * To add a column where requirements are found, no file existence check done
	 * here
	 * 
	 * @param sheetName
	 * @param rowNum
	 * @param colNum
	 */
	public void addColumnData(String filePath, String sheetName, int rowNum, int colNum);

	/**
	 * If this function is called prior to calling createReader(), the program will
	 * misbehave.
	 */
	public void clearColumnData();

	/**
	 * A single invocation will read all files loaded for requirements. Must be
	 * called each time the regex or column data is changed
	 * 
	 * @return
	 */
	public Set<String> readRequirements();

	/**
	 * 
	 * @param regexStrings
	 */
	void setRegexsForValidation(List<String> regexStrings);
}
