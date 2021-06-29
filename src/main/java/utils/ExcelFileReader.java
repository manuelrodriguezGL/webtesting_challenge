package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelFileReader {
    /**
     * Reads an Excel XLSX file SHEET located at PATH
     * NOTE: The program assumes the sheet has a first row with column headers
     *
     * @param filePath  Path to the file
     * @param sheetName Sheet name inside the workbook
     * @throws IOException
     */
    public static String[][] readFile(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        FileInputStream fs = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sheet = wb.getSheet(sheetName);

        String[][] values = new String[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            if (row.getRowNum() == 0)
                continue; // SKIP the first row that has column names
            for (Cell cell : row) {
                values[row.getRowNum() - 1][cell.getColumnIndex()] = formatter.formatCellValue(cell);
            }
        }

        return values;
    }

    /**
     * Return only one roe of data
     *
     * @param filePath  Path to the file
     * @param sheetName Sheet name inside the workbook
     * @param index     The row number (starting at 1)
     * @return One-dimensioned ArrayList of Strings
     * @throws IOException
     */
    public static String[][] readFileRow(String filePath, String sheetName, int index) throws IOException {
        File file = new File(filePath);
        FileInputStream fs = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sheet = wb.getSheet(sheetName);

        String[][] values = new String[1][sheet.getRow(0).getLastCellNum()];
        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            if (row.getRowNum() + 1 != index)
                continue; // SKIP until the row number matches
            for (Cell cell : row) {
                values[0][cell.getColumnIndex()] = formatter.formatCellValue(cell);
            }
            break;
        }

        return values;
    }
}
