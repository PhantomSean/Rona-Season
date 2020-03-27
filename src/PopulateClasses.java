import Classes.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PopulateClasses {
    private static HashMap<String, Project> projects = new HashMap<>();
    private static String temp;

    public static void main(String[] args) throws IOException {
        populateProjectClass("Staff&Projects(60).xlsx", 60);
        System.out.println(projects.get(readCellData("Staff&Projects(60).xlsx", 5, 1)).getTitle());
        System.out.println(projects.get(readCellData("Staff&Projects(60).xlsx", 10, 1)).getProposed_by());
        System.out.println(projects.get(readCellData("Staff&Projects(60).xlsx", 20, 1)).getTitle());
        System.out.println(projects.get(readCellData("Staff&Projects(60).xlsx", 30, 1)).getStream());
        System.out.println(projects.get(readCellData("Staff&Projects(60).xlsx", 50, 1)).getStream());
        System.out.println(projects.get(readCellData("Staff&Projects(60).xlsx", 59, 1)).getProposed_by());

    }

    public static List<Staff> populateStaff(String readFile1) throws IOException {
        List<Staff> staff = null;
        int numStaff = getNumRows(readFile1);
        int i = 1, j;

        while (i <= numStaff) {
            String name = readCellData(readFile1, i, 0);
            String stream = readCellData(readFile1, i, 2);
            List<String> projects = new ArrayList<String>();
            for(j=i; j <= numStaff; j++){
                if (readCellData(readFile1, i, 0) == name) {
                    projects.add(readCellData(readFile1, i, 1));
                    i =j;
                } else
                    break;
            }
            Staff staff_member = new Staff(name, stream, projects);
            staff.add(staff_member);
        }

        return null;
    }

    public static void populateProjectClass(String readFile, int num) throws IOException {
        for(int i = 1; i < num; i++){
            Project project = new Project(readCellData(readFile, i, 1), readCellData(readFile, i, 2), readCellData(readFile, i, 0));
            projects.put(readCellData(readFile, i, 1), project);

        }
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

        // Target Audience value assigned based on supervisor special focus
        if(vColumn == 3) {
            if(cell == null) {
                // Randomised value if no focus
                int r = new Random().nextInt(2);
                if(r == 0)
                    value = "CS";
                else{
                    value = "CS + DS";
                }
            }
            else
                value = "DS";
            readBook.close();
            return value;
        }

        if(cell.getCellType() == CellType.NUMERIC ) {
            value = String.valueOf(cell.getNumericCellValue());
            readBook.close();
            return value;
        }

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
