package org.example.utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    Workbook work = null;
    private final String excelFilePath;
    private FileInputStream fin = null;

    public ExcelReader(String fileName) {

        excelFilePath = System.getProperty("user.dir") + "/Resources/testdata/";

        if (!fileName.endsWith(".xlsx")) {

            fileName = fileName + ".xlsx";
        }


        String file = excelFilePath + fileName;

        try {

            fin = new FileInputStream(file);

            work = new XSSFWorkbook(fin);

            fin.close();
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    public String readCell(String sheetName, int row, int col) {

        DataFormatter formatter = new DataFormatter();

        Sheet sheet = work.getSheet(sheetName);

        Row roww = sheet.getRow(row);
        Cell cell = roww.getCell(col);

        return formatter.formatCellValue(cell);

    }

    public String readCell(int sheetIndex, int row, int col) {

        DataFormatter formatter = new DataFormatter();

        Sheet sheet = work.getSheetAt(sheetIndex);
        Row roww = sheet.getRow(row);
        Cell cell = roww.getCell(col);

        return formatter.formatCellValue(cell);

    }

}
