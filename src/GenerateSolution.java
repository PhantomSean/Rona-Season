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
    private static HashMap<String, Project> projects = new HashMap<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();
    private static PopulateClasses pop = new PopulateClasses();


    public static void  main(String[] args) {
        //genSolution();
    }

    public static void genSolution() throws IOException {
        projects = pop.populateProjectClass("Staff&Projects(60).xlsx");
        students = pop.populateStudentClass("Students&Preferences(60).xlsx");
        staff = pop.populateStaff("Staff&Projects(60).xlsx");
    }


}
