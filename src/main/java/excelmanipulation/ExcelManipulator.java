package excelmanipulation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ExcelManipulator {

    public ArrayList<String> getTestDataFromExcelByTestCaseName(String testCaseName, String sheetName, String testCasesColumnName, String path) {

        try {
            // Access the file
            FileInputStream fileInputStream = new FileInputStream(path);
            // Create a workbook object to access the workbook
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            // Create a sheet object to access the sheet
            XSSFSheet workingSheet = workbook.getSheet(sheetName);
            // Identify Test Cases column by scanning the 1st row
            Iterator<Row> rowIterator = workingSheet.iterator();
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            Cell testCasesCell = null;
            while (cellIterator.hasNext()) {
                testCasesCell = cellIterator.next();
                if (testCasesCell.getStringCellValue().equalsIgnoreCase(testCasesColumnName)) {
                    break;
                }
            }

            assert testCasesCell != null;
            Iterator<Row> rowIterator1 = workingSheet.iterator();
            Row testCasesRow = null;
            boolean isTestCaseDataPresent = false;
            while (rowIterator1.hasNext()) {
                testCasesRow = rowIterator1.next();
                if (testCasesRow.getCell(testCasesCell.getColumnIndex()).getStringCellValue()
                        .equalsIgnoreCase(testCaseName)) {
                    isTestCaseDataPresent = true;
                    break;
                }
            }

            if (isTestCaseDataPresent) {
                Iterator<Cell> testCaseCellsIterator = testCasesRow.iterator();
                Cell testCaseCell = null;
                ArrayList<String> cellsList = new ArrayList<String>();
                while (testCaseCellsIterator.hasNext()) {
                    testCaseCell = testCaseCellsIterator.next();
                    if (testCaseCell.getCellType().equals(CellType.STRING)) {
                        cellsList.add(testCaseCell.getStringCellValue());
                    } else {
                        cellsList.add(NumberToTextConverter.toText(testCaseCell.getNumericCellValue()));
                    }
                }
                return cellsList;
            }

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}