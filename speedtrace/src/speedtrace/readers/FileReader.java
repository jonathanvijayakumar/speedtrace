/**
 * 
 */
package speedtrace.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * @author jonathanvijayakumar
 *
 *         File: FileReader.java Created: 15 Feb 2022 22:45:48
 */
public class FileReader implements IFileReader {

	String filePath;
	FileInputStream stream;

	/**
	 * 
	 */
	public FileReader(String path) {
		filePath = path;
		stream = null;
	}

	@Override
	public void createReader() {

		File file = new File(filePath);
		if (!file.exists() || file.isDirectory()) {
			return;
		}

		stream = open(file);
	}

	public String read() {
		String fileText = "";
		try {
			fileText = IOUtils.toString(stream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileText;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(String file) {
		// TODO Auto-generated method stub
		
	}

}
