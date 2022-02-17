/**
 * 
 */
package speedtrace.requirements;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import speedtrace.readers.IExcelReader;

/**
 * @author jonathanvijayakumar
 *
 *         File: RequirementsReader.java Created: 15 Feb 2022 01:20:59
 */
public class RequirementsReader implements IExcelReader {

	/**
	 * This map holds data for the sheet to be read and the exact row col data for
	 * where the requirements lie
	 */
	private HashMap<String, List<ExcelSheetData>> excelColumnData;
	private List<String> validationRegex;
	private List<Requirement> requirementsList;
	private List<Cell> listOfCells;

	/**
	 * 
	 */
	public RequirementsReader() {
		excelColumnData = new HashMap<String, List<ExcelSheetData>>();
		validationRegex = new ArrayList<String>();
		requirementsList = new ArrayList<Requirement>();
		listOfCells = new ArrayList<Cell>();
	}

	@Override
	public void createReader() {

		try {
			listOfCells.clear();

			// Looping through all the files collected from the user, rerun this if either
			// one of these are changed: files or column data
			for (File file : files) {

				FileInputStream fileStream = open(file);

				XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
				List<ExcelSheetData> excelData = excelColumnData.get(file.getAbsolutePath());

				loadSheetData(workbook, excelData);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * To load sheet data, this function in turn loads cell data.
	 * 
	 * @param workbook
	 * @param excelData
	 */
	private void loadSheetData(XSSFWorkbook workbook, List<ExcelSheetData> excelData) {
		for (ExcelSheetData excelSheetData : excelData) {
			Sheet sheet = workbook.getSheet(excelSheetData.getSheetName());

			loadCellData(excelSheetData, sheet);
		}
	}

	/**
	 * Function loads cell data, where the requirements data is actually present
	 * 
	 * @param excelSheetData
	 * @param sheet
	 */
	private void loadCellData(ExcelSheetData excelSheetData, Sheet sheet) {
		for (int rowNum = excelSheetData.getRowNum(); rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			if (row == null)
				continue;

			Cell cell = row.getCell(excelSheetData.getColNum());
			if (cell == null)
				continue;

			listOfCells.add(cell);
		}
	}

	@Override
	public Set<String> readRequirements() {

		Set<String> listAsString = new HashSet<String>();
		requirementsList.clear();

		for (Cell cell : listOfCells) {
			String cellValue = cell.getStringCellValue();

			String validatedString = validateAndExtract(cellValue);

			if (!validatedString.isEmpty()) {
				requirementsList.add(new Requirement(validatedString));
			}
		}

		for (Requirement requirement : requirementsList) {
			listAsString.add(requirement.getID());
		}

		return listAsString;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

//	@Override
//	public void addFiles(List<String> filesAsStrings) {
//
//		for (String file : filesAsStrings) {
//			File fileObject = new File(file);
//			if (fileObject.exists() && !fileObject.isDirectory()) {
//				files.add(fileObject);
//			}
//		}
//	}

//	@Override
//	public void setFiles(List<String> filesAsStrings) {
//
//		files.clear();
//
//		for (String file : filesAsStrings) {
//			File fileObject = new File(file);
//			if (fileObject.exists() && !fileObject.isDirectory()) {
//				files.add(fileObject);
//			}
//		}
//	}

	@Override
	public void clearColumnData() {
		excelColumnData.clear();
	}

	@Override
	public void addColumnData(String filePath, String sheetName, int rowName, int colName) {
		if (!excelColumnData.containsKey(filePath)) {
			excelColumnData.put(filePath, new ArrayList<ExcelSheetData>());
		}

		List<ExcelSheetData> excelDataList = excelColumnData.get(filePath);
		excelDataList.add(new ExcelSheetData(sheetName, rowName, colName));

		files.add(new File(filePath));
	}

	@Override
	public void setRegexsForValidation(List<String> regexStrings) {
		validationRegex.clear();
		validationRegex.addAll(regexStrings);
	}

	@Override
	public String validateAndExtract(String cellValue) {
		for (String requirementPattern : validationRegex) {
			if (cellValue.matches(requirementPattern)) {
				Pattern pattern = Pattern.compile(requirementPattern);
				Matcher matcher = pattern.matcher(cellValue);
				if (matcher.find()) {
					return matcher.group(0);
				}
			}
		}

		return "";
	}

	@Override
	public void open(String file) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the validationRegex
	 */
	public List<String> getValidationRegex() {
		return validationRegex;
	}

	/**
	 * This class need not be accessed outside this module
	 * 
	 * @author jonathanvijayakumar
	 *
	 *         File: RequirementsReader.java Created: 15 Feb 2022 02:22:10
	 */
	class ExcelSheetData {
		String sheetName;
		int rowNum;
		int colNum;

		/**
		 * Constructor to initialize all the fields
		 * 
		 * @param path
		 * @param sheetName
		 * @param rowNum
		 * @param colNum
		 */
		public ExcelSheetData(String sheetName, int rowNum, int colNum) {
			super();
			this.sheetName = sheetName;
			this.rowNum = rowNum;
			this.colNum = colNum;
		}

		/**
		 * @return the sheetName
		 */
		String getSheetName() {
			return sheetName;
		}

		/**
		 * @return the rowNum
		 */
		int getRowNum() {
			return rowNum;
		}

		/**
		 * @return the colNum
		 */
		int getColNum() {
			return colNum;
		}
	}

	/**
	 * Requirements object
	 * 
	 * @author jonathanvijayakumar
	 *
	 *         File: RequirementsReader.java Created: 15 Feb 2022 02:58:58
	 */
	class Requirement {
		String ID;

		/**
		 * 
		 * @param iD
		 */
		public Requirement(String iD) {
			super();
			ID = iD;
		}

		/**
		 * @return the iD
		 */
		String getID() {
			return ID;
		}

	}

}
