/**
 * 
 */
package speedtrace.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathanvijayakumar
 *
 *         File: IFileReader.java Created: 15 Feb 2022 00:58:26
 */
public interface IFileReader {

	Set<File> files = new HashSet<File>();

	/**
	 * Creates a file reader, FileInputStream to be used as an input stream for
	 * every implementation
	 * 
	 * @return
	 */
	public void createReader();

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
	default FileInputStream open(File file) {
		FileInputStream stream = null;

		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stream;
	}

	public void open(String file);

}
