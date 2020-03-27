import Classes.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PopulateClasses {
    private static HashMap<String, Project> projects = new HashMap<>();
    private static String temp;

    public static void main(String[] args) throws IOException {
        //populateProjectClass("Staff&Projects(60).xlsx");
        populateStaff("Staff&Projects(60).xlsx", "Miskatonic Staff Members.xlsx");

    }

    public static HashMap<String, Project> populateProjectClass(String readFile) throws IOException {
        for(int i = 1; i < getNumRows(readFile); i++){
            Project project = new Project(readCellData(readFile, i, 1), readCellData(readFile, i, 2), readCellData(readFile, i, 0));
            projects.put(readCellData(readFile, i, 1), project);
        }
        return projects;
    }

    public static String readCellData(String file, int vRow, int vColumn) throws IOException {
        String value;
        Workbook readBook = null;
        try {
            readBook = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert readBook != null;
        Sheet sheet = readBook.getSheetAt(0);
        Row row = sheet.getRow(vRow);
        Cell cell = row.getCell(vColumn);

        value = cell.getStringCellValue();
        readBook.close();
        return value;

    }

    public static int getNumRows(String file) {
        Workbook readBook = null;
        try {
            readBook = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert readBook != null;
        Sheet sheet = readBook.getSheetAt(0);
        return sheet.getPhysicalNumberOfRows();
    }

}
