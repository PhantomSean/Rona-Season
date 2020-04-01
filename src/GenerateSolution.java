import Classes.Project;
import Classes.Solution;
import Classes.Staff;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GenerateSolution {
    private static List<Solution> solutions = new ArrayList<>();
    private static HashMap<String, Project> projects;
    private static List<Student> students;
    private static List<Staff> staff;

    public static void main(String[] args) throws IOException {
        genSolution();
        System.out.println(giveRandomProject().getTitle());
    }

    private static void genSolution() throws IOException {
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        staff = PopulateClasses.populateStaff("Staff&Projects(60).xlsx");
    }

    private static Project giveRandomProject() {
        Project project = genProject();
        if (project.isTaken())
            giveRandomProject();
        project.setTaken(true);
        return project;
    }

    private static Project genProject() {
        Random generator = new Random();
        Object[] values = projects.values().toArray();
        Project randomProject = (Project) values[generator.nextInt(values.length)];
        return randomProject;
    }

}
