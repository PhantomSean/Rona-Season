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
    private static HashMap<String, Project> projects = new HashMap<>();
    private static List<Student> students = new ArrayList<>();
    private static List<Staff> staff = new ArrayList<>();


    public static void  main(String[] args) throws IOException {
        genSolution();
    }

    private static void genSolution() throws IOException {
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        staff = PopulateClasses.populateStaff("Staff&Projects(60).xlsx");
        randomlyAssign(1);
        for(int i = 0; i < solutions.size(); i++){
            System.out.println(solutions.get(i).getProjectTitle());
            System.out.println(solutions.get(i).getStudentName());
            System.out.println("\n");
        }
    }

    private static void randomlyAssign(int preference) {
        int tmp = 0;
        List<Student> temp = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if (!students.get(i).hasProject() && !projects.get(students.get(i).getPreference(1)).isAllocated() && checkForOthers(students, 1, i + 1, students.get(i).getPreference(1))) {
                temp.add(students.get(i));
                //System.out.println(students.get(i).getPreference(1));
                //System.out.println(students.get(i).getName());
                tmp = i + 1;

                for (int j = tmp; j < students.size(); j++) {
                    if (temp.get(0).getPreference(1).equals(students.get(j).getPreference(1))) {
                        temp.add(students.get(j));
                        //System.out.println(students.get(j).getName());
                    }
                }
                if(temp.size() != 0) {
                    //System.out.println(temp.size());
                    Random r = new Random();
                    int random = r.nextInt(temp.size() - 1);
                    Solution s = new Solution(temp.get(random), projects.get(temp.get(random).getPreference(preference)));
                    temp.get(random).setHasProject(true);
                    projects.get(temp.get(random).getPreference(preference)).setAllocated(true);
                    //System.out.println("\n");
                    //System.out.println(temp.get(random).getName());
                    solutions.add(s);
                    temp.clear();
                    //System.out.println("\n");
                }
            }
        }
    }

    private static boolean checkForOthers(List<Student> students, int pref, int n, String p){
        for(int i = n; i < students.size(); i++){
            if(students.get(i).getPreference(pref).equals(p)){
                return true;
            }
        }
        return false;
    }


}
