package com.hha.utilities;

import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
	
	public  String path = "";
	public  FileInputStream fis = null;
	//public  FileOutputStream fileOut = null;
	//private OPCPackage fs = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	
	public ExcelReader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// returns the row count in a sheet
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			return sheet.getLastRowNum() + 1;
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum){
		try {
			int index = workbook.getSheetIndex(sheetName);
			if(rowNum <= 0 || colNum <= 0 || index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			cell = row.getCell(colNum-1);

			if((row == null) || (cell == null))
				return "";

			if(cell.getCellTypeEnum() == CellType.STRING)
				return cell.getStringCellValue();
			else if(cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA){
				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					   // format in form of M/D/YY
					  double d = cell.getNumericCellValue();
					  Calendar cal = Calendar.getInstance();
					  cal.setTime(HSSFDateUtil.getJavaDate(d));
					  cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					  cellText = cal.get(Calendar.MONTH)+ 1 + "/" +
								  cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
			  	return cellText;
			} else if (cell.getStringCellValue() == "") {
				return "";
			} else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch (Exception e){
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in .xlsx file";
		}
	}

    // find whether sheets exists
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			//index = workbook.getSheetIndex(sheetName.toUpperCase());
			return false;
		else
			return true;
	}
	
	// returns number of columns in a sheet	
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
		 return -1;
		
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		
		if(row == null)
			return -1;
		return row.getLastCellNum();
	}
    /*
	// to run this on stand alone
	public static void main(String arg[]) throws IOException{
		String excelPath = System.getProperty("user.dir") + "//src//test//resources//excel//testdata.xlsx";
		System.out.println("excelPath: " + excelPath);
		ExcelReader dataTable = new ExcelReader(excelPath);
		for(int colCount = 0 ; colCount < dataTable.getColumnCount("AddCustomerTest"); colCount++)
			System.out.println(dataTable.getCellData("AddCustomerTest", colCount, 1));
	}
	*/
}