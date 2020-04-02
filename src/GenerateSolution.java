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
    private static HashMap<String, Project> projects;
    private static List<Student> students;
    private static List<Staff> staff;


    public static void  main(String[] args) throws IOException {
        genSolution();
    }

    private static void genSolution() throws IOException {
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        staff = PopulateClasses.populateStaff("Staff&Projects(60).xlsx");
        unique(students, 0);
        for(int i=0; i<students.size(); i++){
            System.out.println(students.get(i).getPreference(0));
        }

    }

    public static void unique(List<Student> students, int rank){
        List<Student> not_unique = new ArrayList<Student>();

        for (int i=0; i<students.size(); i++){
            String reference = students.get(i).getPreference(rank);
            for (int j = i+1; j<students.size(); j++) {
                String compare = students.get(j).getPreference(j);

                if (reference.equals(compare)) {
                    not_unique.add(students.remove(j));
                    j--;
                }

            }
            for (int j=0; j<not_unique.size(); j++) {
                if(not_unique.get(j).equals(reference)) {
                    not_unique.add(students.remove(i));
                    i--;
                    break;
                }
            }
        }
    }
}
