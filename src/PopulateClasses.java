import Classes.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class PopulateClasses {
    private static HashMap<String, Project> projects = new HashMap<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();
    private static DecimalFormat df = new DecimalFormat("0.00");

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

            Student student = new Student(readCellData(readFile, i, 0),readCellData(readFile, i, 2), Integer.valueOf(readCellData(readFile, i, 1)), preferences, false, 0, genGPA());

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

    public static double genGPA(){
        double GPA = 0;
        Random r = new Random();
        int random = r.nextInt(10) + 1;
        if(random == 1){
            //1.0-2.4
            GPA = 1.0 + r.nextDouble()*(2.4-1.0);
        }
        if(random >= 2 && random <= 4){
            //2.5-3.1
            GPA = 2.5 + r.nextDouble()*(3.1-2.5);
        }
        if(random >= 5 && random <= 8){
            //3.2-3.7
            GPA = 3.2 + r.nextDouble()*(3.7-3.2);
        }
        if(random == 9 || random == 10){
            //3.8-4.2
            GPA = 3.8 + r.nextDouble()*(4.2-3.8);
        }
        return Math.round(GPA * 100.0) / 100.0;
    }

}