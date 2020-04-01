import Classes.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PopulateClasses {
    private static HashMap<String, Project> projects = new HashMap<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        populateStaff("Staff&Projects(60).xlsx");
//        populateProjectClass("Staff&Projects(60).xlsx");
        populateStudentClass("Students&Preferences(60).xlsx");
    }

    public static List<Staff> populateStaff(String readFile1) throws IOException {

        int numStaff = getNumRows(readFile1);
        int i = 1, j;

        while (i < numStaff) {
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
            assert staff != null;
            staff.add(staff_member);
//            System.out.println("Added staff member" + i);
            i++;
        }

        return staff;
    }

    public static void populateProjectClass(String readFile) throws IOException {
        int numProjects = getNumRows(readFile);
        for(int i = 1; i < numProjects; i++){
            Project project = new Project(readCellData(readFile, i, 1), readCellData(readFile, i, 2), readCellData(readFile, i, 0));
            projects.put(readCellData(readFile, i, 1), project);

        }
    }

    public static void populateStudentClass(String readFile) throws IOException{
        int numStudents = getNumRows(readFile);
        List<String> preferences = new ArrayList<String>();

        for(int i = 1; i < numStudents; i++){
            for (int j = 0; j <= 9; j++){
                int pos = j+3;
                preferences.add(readCellData(readFile,i,pos));
            }

            Student student = new Student(readCellData(readFile, i, 0),readCellData(readFile, i, 3), Integer.valueOf(readCellData(readFile, i, 1)), preferences);

            students.add(student);
            System.out.println("Added Student " + i);
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
            int num = (int) cell.getNumericCellValue();
            value = String.valueOf(num  );
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