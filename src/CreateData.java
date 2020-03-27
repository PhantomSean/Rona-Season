import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class CreateData {
    public static void main(String[] args) throws IOException {
        staffProject("Miskatonic Classes.Staff Members.xlsx", "Classes.Staff&Projects(60).xlsx", 60);
        studentPreference("Top Boys Names 1999. Source CSO Ireland.xlsx", "surnames.xlsx", "Classes.Staff&Projects(60).xlsx", "Students&Preferences(60).xlsx",60);
        staffProject("Miskatonic Classes.Staff Members.xlsx", "Classes.Staff&Projects(120).xlsx", 120);
        studentPreference("Top Boys Names 1999. Source CSO Ireland.xlsx", "surnames.xlsx", "Classes.Staff&Projects(120).xlsx", "Students&Preferences(120).xlsx",120);
        staffProject("Miskatonic Classes.Staff Members.xlsx", "Classes.Staff&Projects(240).xlsx", 240);
        studentPreference("Top Boys Names 1999. Source CSO Ireland.xlsx", "surnames.xlsx", "Classes.Staff&Projects(240).xlsx", "Students&Preferences(240).xlsx",240);
        staffProject("Miskatonic Classes.Staff Members.xlsx", "Classes.Staff&Projects(500).xlsx", 500);
        studentPreference("Top Boys Names 1999. Source CSO Ireland.xlsx", "surnames.xlsx", "Classes.Staff&Projects(500).xlsx", "Students&Preferences(500).xlsx",500);
    }

    public static void staffProject(String readFile, String writeFile, int num) throws IOException {
        Workbook writeBook = new XSSFWorkbook();
        Sheet writeSheet = writeBook.createSheet("Classes.Staff & Projects("+num+")");

        // Style used for header
        XSSFCellStyle style = (XSSFCellStyle) writeBook.createCellStyle();
        XSSFFont font = (XSSFFont) writeBook.createFont();
        font.setBold(true);
        style.setFont(font);

        // Header Values
        Row row = writeSheet.createRow(0);
        row.createCell(0).setCellValue("Supervisor");
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue("Classes.Project");
        row.getCell(1).setCellStyle(style);
        row.createCell(2).setCellValue("Target Audience");
        row.getCell(2).setCellStyle(style);

        // Insertion of relevant supervisor data from random rows
        int[] randNums = genNums(readFile, (num/2), false);
        int j = 1;
        int extraProjects = (int) (num*0.366);
        for (int randNum : randNums) {
            String[] projects = readCellData(readFile, randNum - 1, 1).split(", ");

            for (String a : projects) {
                j = newRow(readFile, writeSheet, j, randNum, a);
            }
            if (projects.length < 3 && extraProjects > 0) {
                j = newRow(readFile, writeSheet, j, randNum, "Abstract views on " + projects[0]);
            }
        }

        for (int i = 0; i < 3; i++) {
            writeSheet.autoSizeColumn(i);
        }

        writeBook.write(new FileOutputStream(writeFile));
        writeBook.close();
    }

    private static int newRow(String readFile, Sheet writeSheet, int j, int randNum, String a) throws IOException {
        Row row;
        row = writeSheet.createRow(j);
        row.createCell(0).setCellValue(readCellData(readFile, randNum - 1, 0));
        row.createCell(1).setCellValue(a);
        row.createCell(2).setCellValue(readCellData(readFile, randNum - 1, 3));
        j++;
        return j;
    }

    public static void studentPreference(String firstNameFile, String surnameFile, String projectsFile, String writeFile, int num) throws IOException {
        Workbook writeBook = new XSSFWorkbook();
        Sheet writeSheet = writeBook.createSheet("Classes.Student & Preferences("+num+")");

        // Style used for header
        XSSFCellStyle style = (XSSFCellStyle) writeBook.createCellStyle();
        XSSFFont font = (XSSFFont) writeBook.createFont();
        font.setBold(true);
        style.setFont(font);

        // Header Values
        Row row = writeSheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue("Classes.Student Number");
        row.getCell(1).setCellStyle(style);
        row.createCell(2).setCellValue("Stream");
        row.getCell(2).setCellStyle(style);
        for (int i = 3; i < 13; i++) {
            row.createCell(i).setCellValue("Preference " + (i-2));
            row.getCell(i).setCellStyle(style);
        }

        // Insertion of relevant supervisor data from random rows
        int[] randFirstNames = genNums(firstNameFile, num, true);
        int[] randSurnames = genNums(surnameFile, num, true);
        int[] studentNums = genStudentNums(num);
        int[] preferenceProbs = genPrefProbs(num, projectsFile);
        int j = 1;
        for (int i = 0; i < num; i++) {
            String stream = genStream(i, num);
            String name = readCellData(firstNameFile, randFirstNames[i], 0) + " " + readCellData(surnameFile, randSurnames[i], 1);
            String[] preferences = genPrefs(preferenceProbs, num, projectsFile, stream);
            row = writeSheet.createRow(j);
            row.createCell(0).setCellValue(name);
            row.createCell(1).setCellValue(studentNums[i]);
            row.createCell(2).setCellValue(stream);
            for (int x = 0; x < preferences.length; x++) {
                row.createCell(x+3).setCellValue(preferences[x]);
            }
            j++;
        }

        for (int i = 0; i < 13; i++) {
            writeSheet.autoSizeColumn(i);
        }

        writeBook.write(new FileOutputStream(writeFile));
        writeBook.close();
    }

    // Assigns students to Stream based on a 60:40 split
    private static String genStream(double i, double num) {
        double split = (i/num);
        if (split < 0.6)
            return "CS";
        return "DS";
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

        value = cell.getStringCellValue();
        readBook.close();
        return value;
    }

    // Random number generator
    public static int[] genNums(String file, int total, boolean duplicates) {
        int totalRows = getNumRows(file);
        int[] randNums = new int[total];
        int r, i, j;
        if (duplicates) {
            for (i = 0; i < randNums.length; i++) {
                randNums[i] = new Random().nextInt(totalRows - 1) + 1;
            }
        }
        else {
            randNums[0] = new Random().nextInt(totalRows - 1) + 1;
            for (i = 1; i < randNums.length; i++) {
                r = new Random().nextInt(totalRows - 1) + 1;
                for (j = 0; j < i; j++) {
                    if (randNums[j] == r) {
                        i--;
                        break;
                    }
                    randNums[i] = r;
                }
            }
        }
        return randNums;
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

    public static int[] genStudentNums(int total) {
        int[] studentNums = new int[total];
        int num, i , j;
        studentNums[0] = genStudentNum();
        for (i = 1; i < studentNums.length; i++) {
            num = genStudentNum();
            for (j = 0; j < i; j++) {
                if (studentNums[j] == num) {
                    i--;
                    break;
                }
                studentNums[i] = num;
            }
        }
        return studentNums;
    }

    public static int genStudentNum() {
        int studentNum = 0;
        for(int i = 0; i < 6; i++) {
            studentNum = studentNum*10 + new Random().nextInt(9)+1;
        }
        return studentNum;
    }

    public static int[] genPrefProbs(int students, String file) {
        int projects = getNumRows(file)-1;
        int[] projectsSelect = new int[students];
        Random r = new Random();
        projectsSelect[0] = (int) (Math.round(projects/2) + r.nextGaussian() * (projects * 0.13));
        for (int i = 0; i < students; i++) {

            double randomValue = (projects/2) + r.nextGaussian() * (projects * 0.13);
            for (int j = 0; j < i; j++) {
                if (projectsSelect[j] == randomValue) {
                    i--;
                    break;
                }
                projectsSelect[i] = (int) Math.round(randomValue);
            }
        }

        int[] projectProbabilities = new int[projects];
        for (int i = 0; i < students; i++) {
            projectProbabilities[projectsSelect[i]] += 1;
        }
        return projectProbabilities;
    }

    public static String[] genPrefs(int[] probs, int students, String projectFile, String stream) throws IOException {
        String[] choice = new String[10];
        int[] row = new int[10];
        Random rand = new Random();
        int index = rand.nextInt(students);
        int sum = 0;
        int i = 0;
        while (sum <= index) {
            sum = sum + probs[i++];
        }
        while(!checkStream(projectFile, stream, i)){
            i++;
        }
        row[0] = i;
        choice[0] = readCellData(projectFile, i, 1);
        for (int x = 1; x < 10; x++) {
            rand = new Random();
            index = rand.nextInt(students);
            sum = 0;
            i = 0;
            while (sum <= index) {
                sum = sum + probs[i++];
            }
            for (int j = 0; j < x; j++) {
                if (row[j] == i) {
                    x--;
                    break;
                }
                if(readCellData(projectFile, i, 2).equals(stream) || readCellData(projectFile, i, 2).length() > 2) {
                    row[x] = i;
                    choice[x] = readCellData(projectFile, i, 1);
                }else{
                    x--;
                    break;
                }
            }
        }
        return choice;
    }
    private static boolean checkStream(String projectFile, String stream, int i) throws IOException{
        return readCellData(projectFile, i, 2).equals(stream) || readCellData(projectFile, i, 2).length() > 2;
    }
}

