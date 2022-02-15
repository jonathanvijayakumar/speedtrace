/**
 * 
 */
package speedtrace.readers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jonathanvijayakumar
 *
 *         File: IFileReader.java Created: 15 Feb 2022 00:58:26
 */
public interface IFileReader {

	List<File> files = new ArrayList<File>();

	/**
	 * Creates a file reader, FileInputStream to be used as an input stream for
	 * every implementation
	 * 
	 * @return
	 */
	public String createReader();

	/**
	 * closes the file reading handle
	 */
	public void close();

	/**
	 * Returns a handle to the file opened
	 * 
	 * @param file
	 * @return
	 */
	FileInputStream open(File file);

}
