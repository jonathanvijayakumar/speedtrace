/**
 * 
 */
package speedtrace.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;

/**
 * @author jonathanvijayakumar
 *
 *         File: FileReader.java Created: 15 Feb 2022 22:45:48
 */
public class FileReader implements IFileReader {

	String filePath;
	FileInputStream stream;
	Boolean docTypeUndefined = false;

	/**
	 * 
	 */
	public FileReader() {
		stream = null;
	}

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
		if (!file.exists()) {
			if (!docTypeUndefined && file.isDirectory())
				return;
		}

		if (stream != null) {
			close();
		}

		stream = open(file);
	}

	@Override
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
		try {
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void open(String file) {
		filePath = file;
		createReader();
	}

	@Override
	public void openFileOrDir(String file) {
		docTypeUndefined = true;
		filePath = file;
		createReader();

	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static Set<Path> getFilesFromDir(String path) {
		try {
			return Files.walk(Paths.get(path)).filter(Files::isRegularFile).collect(Collectors.toSet());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Collections.EMPTY_SET;
	}
}
