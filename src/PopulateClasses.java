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

    public static HashMap<String, Project> populateProjectClass(String readFile) throws IOException {
        int numProjects = getNumRows(readFile);
        for(int i = 1; i < numProjects; i++){
            Project project = new Project(readCellData(readFile, i, 1), readCellData(readFile, i, 2), readCellData(readFile, i, 0), false);
            projects.put(readCellData(readFile, i, 1), project);

        }
        return projects;
    }

    public static List<Student> populateStudentClass(String readFile) throws IOException{
        int numStudents = getNumRows(readFile);
        for(int i = 1; i < numStudents; i++){
            List<String> preferences = new ArrayList<String>();
            for (int j = 0; j <= 9; j++){
                int pos = j+3;
                preferences.add(readCellData(readFile,i,pos));
            }

            Student student = new Student(readCellData(readFile, i, 0),readCellData(readFile, i, 2), Integer.valueOf(readCellData(readFile, i, 1)), preferences, false);

            students.add(student);
            //System.out.println("Added Student " + i);
        }
        return students;
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