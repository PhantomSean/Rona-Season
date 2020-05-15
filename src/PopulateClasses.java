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
            List<String> projects = new ArrayList<>();
            for(j=i; j <= numStaff; j++){
                if (readCellData(readFile1, i, 0).equals(name)) {
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

    //method for populating the projects hashmap for already loaded files
    static HashMap<String, Project> populateProjectClass(String readFile) throws IOException {
        int numProjects = getNumRows(readFile);
        for(int i = 1; i < numProjects; i++){
            Project project = new Project(readCellData(readFile, i, 1), readCellData(readFile, i, 2), readCellData(readFile, i, 0), false);
            projects.put(readCellData(readFile, i, 1), project);

        }
        projects.put("Self Specified Project", new Project("Self Specified Project", "CS + DS", "Student", false));
        return projects;
    }
    //method for populating the students List for already loaded files
    static List<Student> populateStudentClass(String readFile) throws IOException{
        int numStudents = getNumRows(readFile);
        for(int i = 1; i < numStudents; i++){
            List<String> preferences = new ArrayList<>();
            for (int j = 0; j <= 9; j++){
                int pos = j+3;
                preferences.add(readCellData(readFile,i,pos));
            }

            Student student = new Student(readCellData(readFile, i, 0),readCellData(readFile, i, 2), Integer.parseInt(readCellData(readFile, i, 1)), preferences, false, 0, genGPA());

            students.add(student);
        }
        return students;
    }

    //method for populating the project HashMap for custom files
    static HashMap<String, Project> populateCustomProjectClass(String readFile) throws IOException {
        for(int i = 1; i < getNumRows(readFile); i++){
            if(readCellData(readFile, i, 1).equals(""))
                break;
            for(int j = 4; j < 21; j++){
                if(readCellData(readFile, i, j).equals(""))
                    break;
                if(checkProject(projects, (readCellData(readFile, i, j)))){
                    Project project = new Project(readCellData(readFile, i, j), "CS + DS", (readCellData(readFile, i, 3)),false);
                    projects.put(project.getTitle(), project);
                }
            }
        }
        return projects;
    }

    //method which checks if a project has or has not been already added to the HashMap
    private static boolean checkProject(HashMap<String, Project> projects, String project){
        for (Map.Entry<String, Project> entry : projects.entrySet()) {
            if(entry.getKey().equals(project)){
                return false;
            }
        }
        return true;
    }

    //method for populating the students List for a custom file
    static List<Student> populateCustomStudentClass(String readFile) throws IOException{
        int numStudents = getNumRows(readFile);
        for(int i = 1; i < numStudents; i++){
            if(readCellData(readFile, i, 1).equals(""))
                break;
            List<String> preferences = new ArrayList<>();
            for (int j = 0; j < 20; j++){
                int pos = j+4;
                if(readCellData(readFile, i, pos).equals("")) {
                    preferences.add("none");
                }else {
                    preferences.add(readCellData(readFile, i, pos));
                }
            }
            Student student = new Student(readCellData(readFile, i, 0), "CS", Integer.parseInt(readCellData(readFile, i, 1)), preferences, false, 0, Double.parseDouble(readCellData(readFile, i, 2)));
            students.add(student);
        }
        return students;
    }

    //method which analyzes the inputted file
    static String analyzeFile(String readFile, boolean custom) throws IOException {
        boolean checkForEmpty = true;
        boolean checkStudentNumber = true;
        boolean checkStudentGPA = true;
        boolean emptyGPA=true;
        boolean emptyStudentNumber=true;
        boolean emptyStudentName=true;
        boolean checkDupeStudentNumber=false;
        String result = "\n";
        if(custom) {
            for (int i = 1; i < getNumRows(readFile); i++) {
                if (readCellData(readFile, i, 1).equals("") && readCellData(readFile, i, 2).equals("") && readCellData(readFile, i, 3).equals(""))
                    break;
                //ensuring that all student numbers are numerical
                try {
                    Double.parseDouble(readCellData(readFile, i, 1));
                } catch (NumberFormatException e) {
                    checkStudentNumber = false;
                }
                for(int j = i+1; j < getNumRows(readFile);j++){
                    if(readCellData(readFile, j, 1).equals(""))
                        break;
                    if(Double.parseDouble(readCellData(readFile, i, 1))==Double.parseDouble(readCellData(readFile, j, 1))){
                        checkDupeStudentNumber=true;
                    }
                }
                //ensuring that all student GPAs are numerical
                try {
                    Double.parseDouble(readCellData(readFile, i, 2));
                } catch (NumberFormatException e) {
                    checkStudentGPA = false;
                }
                //checking if any cells are empty
                if (readCellData(readFile, i, 0).equals("") || readCellData(readFile, i, 1).equals("") || readCellData(readFile, i, 2).equals(""))
                    checkForEmpty = false;
                //checking for empty columns
                if (!readCellData(readFile, i, 0).equals(""))
                    emptyStudentName = false;
                if (!readCellData(readFile, i, 1).equals(""))
                    emptyStudentNumber = false;
                if (!readCellData(readFile, i, 2).equals(""))
                    emptyGPA = false;
                //updating the progress bar
                double dI = i+1;
                double dNumGen = getNumRows(readFile);
                double progress = (dI/dNumGen) * 100;
                int val = (int) progress;
                Solve.ui.setProgress(val);
            }
        }else{
            for (int i = 1; i < getNumRows(readFile); i++) {
                if (readCellData(readFile, i, 1).equals("") && readCellData(readFile, i, 2).equals("") && readCellData(readFile, i, 3).equals(""))
                    break;
                //ensuring that all student numbers are numerical
                try {
                    Double.parseDouble(readCellData(readFile, i, 1));
                } catch (NumberFormatException e) {
                    checkStudentNumber = false;
                }
                for(int j = i+1; j < getNumRows(readFile);j++){
                    if(readCellData(readFile, j, 1).equals(""))
                        break;
                    if(Double.parseDouble(readCellData(readFile, i, 1))==Double.parseDouble(readCellData(readFile, j, 1)))
                        checkDupeStudentNumber=true;

                }
                //checking if any cells are empty
                if (readCellData(readFile, i, 0).equals("") || readCellData(readFile, i, 1).equals("") || readCellData(readFile, i, 2).equals(""))
                    checkForEmpty = false;
                //checking for empty columns
                if (!readCellData(readFile, i, 0).equals(""))
                    emptyStudentName = false;
                if (!readCellData(readFile, i, 1).equals(""))
                    emptyStudentNumber = false;
                //updating the progress bar
                double dI = i+1;
                double dNumGen = getNumRows(readFile);
                double progress = (dI/dNumGen) * 100;
                int val = (int) progress;
                Solve.ui.setProgress(val);
            }
            emptyGPA=false;
        }
        //the result is compiled and then returned
        if(!checkForEmpty)
            result+=("Warning: The file loaded may not have all rows loaded correctly as empty values were found\n");
        if(!checkStudentNumber)
            result+=("Warning: The file loaded may contain non-numerical values for type 'Student Number'\n");
        if(!checkStudentGPA)
            result+=("Warning: The file loaded may contain non-numerical values for type 'Student GPA'\n");
        if(emptyStudentName)
            result+=("Warning: Unable to locate column for type 'Student Name'\n");
        if(emptyStudentNumber)
            result+=("Warning: Unable to locate column for type 'Student Number'\n");
        if(emptyGPA)
            result+=("Warning: Unable to locate column for type 'Student GPA'\n");
        if(checkDupeStudentNumber)
            result+=("Warning: The file loaded may contain duplicate values for type 'Student Number'\n");
        result+=("File has ran all tests successfully\n");
        if(checkForEmpty && checkStudentNumber && checkStudentGPA && !emptyStudentName && !emptyStudentNumber && !emptyGPA)
            result+=("File has passed all tests successfully\n");
        return result;
    }

    private static String readCellData(String file, int vRow, int vColumn) throws IOException {
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
            double num = cell.getNumericCellValue();
            if (Math.floor(num) == num) {
                value = String.valueOf((int) num);
            }
            else
                value = String.valueOf(num);
            readBook.close();
            return value;
        }

        value = cell.getStringCellValue();
        readBook.close();
        return value;

    }

    private static int getNumRows(String file) {
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

    //method for generating students GPA
    static double genGPA(){
        double GPA = 0;
        Random r = new Random();
        //selects a random number from 1 to 10
        int random = r.nextInt(10) + 1;
        //approx. one tenth of students will have a GPA of 1.0-2.4
        if(random == 1){
            //1.0-2.4
            GPA = 1.0 + r.nextDouble()*(2.4-1.0);
        }
        //approx. three tenths of students will have a GPA of 2.5-3.1
        if(random >= 2 && random <= 4){
            //2.5-3.1
            GPA = 2.5 + r.nextDouble()*(3.1-2.5);
        }
        //approx. two fifths of students will have a GPA of 3.2-3.7
        if(random >= 5 && random <= 8){
            //3.2-3.7
            GPA = 3.2 + r.nextDouble()*(3.7-3.2);
        }
        //approx. one fifth of students will have a GPA of 3.8-4.2
        if(random == 9 || random == 10){
            //3.8-4.2
            GPA = 3.8 + r.nextDouble()*(4.2-3.8);
        }
        //returns the number rounded to two decimal points
        return Math.round(GPA * 100.0) / 100.0;
    }

}