package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelFileReader {
    /**
     * Reads an Excel XLSX file SHEET located at PATH
     * NOTE: The program assumes the sheet has a first row with column headers
     *
     * @param filePath  Path to the file
     * @param sheetName Sheet name inside the workbook
     * @throws IOException
     */
    public static ArrayList<ArrayList<String>> readFile(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        FileInputStream fs = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sheet = wb.getSheet(sheetName);

        ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
        DataFormatter formatter = new DataFormatter();

        for (Row row : sheet) {
            if (row.getRowNum() == 0)
                continue; // SKIP the first row that has column names

            values.add(new ArrayList<String>());
            for (Cell cell : row) {
                values.get(row.getRowNum() - 1).add(cell.getColumnIndex(), formatter.formatCellValue(cell));
            }
        }

        return values;
    }

    public static ArrayList<String> readFileRow(String filePath, String sheetName, int index) throws IOException {
        File file = new File(filePath);
        FileInputStream fs = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fs);
        XSSFSheet sheet = wb.getSheet(sheetName);

        ArrayList<String> values = new ArrayList<String>();
        DataFormatter formatter = new DataFormatter();

        if(index > sheet.getLastRowNum())
            throw new IndexOutOfBoundsException("The provided row number is outside the sheet data range");

        for (Row row : sheet) {
            if (row.getRowNum() + 1 != index)
                continue; // SKIP until the number of row matches
            for (Cell cell : row) {
                values.add(formatter.formatCellValue(cell));
            }
        }

        return values;
    }
}
