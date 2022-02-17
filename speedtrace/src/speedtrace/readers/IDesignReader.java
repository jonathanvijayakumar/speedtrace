/**
 * 
 */
package speedtrace.readers;

import java.util.List;
import java.util.Set;

/**
 * @author jonathanvijayakumar
 *
 *         File: IDesignReader.java Created: 17 Feb 2022 03:02:23
 */
public interface IDesignReader extends IFileReader {

	/**
	 * Simple read requirements function. Uses regex list for validation and
	 * extraction.
	 * 
	 * @return
	 */
	public Set<String> readRequirements();
}
