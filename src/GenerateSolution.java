import Classes.Project;
import Classes.Solution;
import Classes.Staff;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateSolution {
    private static List<Solution> solutions = new ArrayList<>();

    public static void  main(String[] args) throws IOException {
        genSolution();
    }

    private static void genSolution() throws IOException {
        HashMap<String, Project> projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        List<Student> students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        List<Staff> staff = PopulateClasses.populateStaff("Staff&Projects(60).xlsx");
    }


}
