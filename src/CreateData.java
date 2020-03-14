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
import java.util.Random;

public class CreateData {
    public static void main(String[] args) throws IOException {
        staffProject("Miskatonic Staff Members.xlsx", "Staff&Projects(60).xlsx", 60);
        studentPreference("Top Boys Names 1999. Source CSO Ireland.xlsx", "surnames.xlsx", "Students&Preferences(60).xlsx",60);
    }

    public static void staffProject(String readFile, String writeFile, int num) throws IOException {
        Workbook writeBook = new XSSFWorkbook();
        Sheet writeSheet = writeBook.createSheet("Staff & Projects("+num+")");

        // Style used for header
        XSSFCellStyle style = (XSSFCellStyle) writeBook.createCellStyle();
        XSSFFont font = (XSSFFont) writeBook.createFont();
        font.setBold(true);
        style.setFont(font);

        // Header Values
        Row row = writeSheet.createRow(0);
        row.createCell(0).setCellValue("Supervisor");
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue("Project");
        row.getCell(1).setCellStyle(style);
        row.createCell(2).setCellValue("Target Audience");
        row.getCell(2).setCellStyle(style);

        // Insertion of relevant supervisor data from random rows
        int[] randNums = genNums(readFile, (num/2), false);
//        System.out.println(Arrays.toString(randNums));
        int j = 1;
        int extraProjects = (int) (num*0.366);
        for (int randNum : randNums) {
            String[] projects = readCellData(readFile, randNum - 1, 1).split(", ");

            for (String a : projects) {
                row = writeSheet.createRow(j);
                row.createCell(0).setCellValue(readCellData(readFile, randNum - 1, 0));
                row.createCell(1).setCellValue(a);
                row.createCell(2).setCellValue(readCellData(readFile, randNum - 1, 3));
                j++;
            }
            if (projects.length < 3 && extraProjects > 0) {
                row = writeSheet.createRow(j);
                row.createCell(0).setCellValue(readCellData(readFile, randNum - 1, 0));
                row.createCell(1).setCellValue("Abstract views on " + projects[0]);
                row.createCell(2).setCellValue(readCellData(readFile, randNum - 1, 3));
                j++;
            }
        }

        for (int i = 0; i < 3; i++) {
            writeSheet.autoSizeColumn(i);
        }

        writeBook.write(new FileOutputStream(writeFile));
        writeBook.close();
    }

    public static void studentPreference(String firstNameFile, String surnameFile, String writeFile, int num) throws IOException {
        Workbook writeBook = new XSSFWorkbook();
        Sheet writeSheet = writeBook.createSheet("Student & Preferences("+num+")");

        // Style used for header
        XSSFCellStyle style = (XSSFCellStyle) writeBook.createCellStyle();
        XSSFFont font = (XSSFFont) writeBook.createFont();
        font.setBold(true);
        style.setFont(font);

        // Header Values
        Row row = writeSheet.createRow(0);
        row.createCell(0).setCellValue("Name");
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue("Student Number");
        row.getCell(1).setCellStyle(style);
        row.createCell(2).setCellValue("Stream");
        row.getCell(2).setCellStyle(style);
        row.createCell(3).setCellValue("Preferences");
        row.getCell(3).setCellStyle(style);

        // Insertion of relevant supervisor data from random rows
        int[] randFirstNames = genNums(firstNameFile, num, true);
        int[] randSurnames = genNums(surnameFile, num, true);
        int j = 1;
        for (int i = 0; i < num; i++) {
            String name = readCellData(firstNameFile, randFirstNames[i], 0) + " " + readCellData(surnameFile, randSurnames[i], 1);
            row = writeSheet.createRow(j);
            row.createCell(0).setCellValue(name);
            row.createCell(1).setCellValue(genStudentNum());
            row.createCell(2).setCellValue(genStream(i, num));
            j++;
        }

        for (int i = 0; i < 4; i++) {
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
        Workbook readBook = null;
        try {
            readBook = new XSSFWorkbook(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert readBook != null;
        Sheet sheet = readBook.getSheetAt(0);
        int totalRows = sheet.getPhysicalNumberOfRows();
        int[] randNums = new int[total];
        int r;
        if (duplicates) {
            for (int i = 0; i < randNums.length; i++) {
                randNums[i] = new Random().nextInt(totalRows - 1) + 1;
            }
        }
        else {
            randNums[0] = new Random().nextInt(totalRows - 1) + 1;
            for (int i = 1; i < randNums.length; i++) {
                r = new Random().nextInt(totalRows - 1) + 1;
                for (int j = 0; j < i; j++) {
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

    public static int genStudentNum() {
        int studentNum = 0;
        for(int i = 0; i < 6; i++) {
            studentNum = studentNum*10 + new Random().nextInt(9)+1;
        }
        return studentNum;
    }
}
