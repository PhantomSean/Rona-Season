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
    private static int x = 0;

    public static void main(String[] args) throws IOException {
        genSolution();
        //System.out.println(giveRandomProject().getTitle());

    }

    private static void genSolution() throws IOException {
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        staff = PopulateClasses.populateStaff("Staff&Projects(60).xlsx");

/*

        for(int i = 0; i < students.size(); i++){
            if(!students.get(i).hasProject()){
                Solution sol = new Solution(students.get(i), giveRandomProject());
                solutions.add(sol);
            }
        }
        System.out.println(solutions.size());
*/
        for(int i = 0; i < 10; i++){
            assignUnique(students, projects, i);
            //randomlyAssign(students, i);
        }
        System.out.println(solutions.size());


    }

    private static void randomlyAssign(List<Student> students, int preference) {
        int tmp = 0;
        List<Student> temp = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if (!students.get(i).hasProject() && !projects.get(students.get(i).getPreference(preference)).isTaken() && checkForOthers(students, preference, i + 1, students.get(i).getPreference(preference))) {
                temp.add(students.get(i));
                //System.out.println(students.get(i).getName());
                tmp = i + 1;

                for (int j = tmp; j < students.size(); j++) {
                    if (temp.get(0).getPreference(preference).equals(students.get(j).getPreference(preference))) {
                        temp.add(students.get(j));
                        //System.out.println(students.get(j).getName());
                    }
                }
                if(temp.size() != 0) {
                    //System.out.println(temp.size());
                    Random r = new Random();
                    int random = r.nextInt(temp.size() - 1);
                    //System.out.println("\n");
                    //System.out.println(temp.get(random).getName());
                    Solution s = new Solution(temp.get(random), projects.get(temp.get(random).getPreference(preference)));
                    temp.get(random).setHasProject(true);
                    //System.out.println("\n");
                    projects.get(temp.get(random).getPreference(preference)).setTaken(true);
                    solutions.add(s);
                    temp.clear();
                }
            }
        }
    }

    private static boolean checkForOthers(List<Student> students, int pref, int n, String project){
        n++;
        for(int i = n; i < students.size(); i++){
            if(students.get(i).getPreference(pref).equals(project)){
                return true;
            }
        }
        return false;
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

    private static void assignUnique(List<Student> students, HashMap<String, Project> projects, int preference) {
        int check;
        for (int i = 0; i < students.size(); i++) {
            check = 0;
            for(int j = 0; j < students.size(); j++){
                if(students.get(i).getPreference(preference).equals(students.get(j).getPreference(preference))){
                    check++;
                }
            }
            if(check == 1 && !students.get(i).hasProject() && !projects.get(students.get(i).getPreference(preference)).isTaken()) {
                System.out.println(students.get(i).getName());
                System.out.println(students.get(i).getPreference(preference));
                Solution solution = new Solution(students.get(i), projects.get(students.get(i).getPreference(preference)));
                solutions.add(solution);
                students.get(i).setHasProject(true);
                projects.get(students.get(i).getPreference(preference)).setTaken(true);
            }
        }

    }

//    private static List<Student> assignUnique(List<Student> students, int rank){
//        List<Student> not_unique = new ArrayList<>();
//        List<Student> studentsCopy = new ArrayList<>();
//
//        for (int i=0;i<students.size(); i++)
//        {
//            studentsCopy.add(students.get(i));
//        }
//        // traverse the list removing duplicates and adding them to not_unique
//        for (int i=0; i<studentsCopy.size(); i++){
//            boolean isASolution = true;
//            String reference = studentsCopy.get(i).getPreference(rank);
//
//            for (int j = i+1; j<studentsCopy.size(); j++) {
//                String compare = studentsCopy.get(j).getPreference(rank);
//
//
//                if (reference.equals(compare)) {
//                    not_unique.add(studentsCopy.remove(j));
//                    j--;
//                }
//            }
//
//            // Check to see if the reference needs to be removed
//            for (int j=0; j<not_unique.size(); j++) {
//
//	            if(not_unique.get(j).getPreference(rank).equals(reference)) {
//                    not_unique.add(studentsCopy.remove(i));
//                    i--;
//                    isASolution = false;
//                    break;
//                }
//            }
//            if(isASolution){
//                Solution newSolution = new Solution(studentsCopy.get(i), projects.get(studentsCopy.get(i).getPreference(rank)));
//                solutions.add(newSolution);
//
//            }
//        }
//        randomlyAssign(not_unique, rank);
//        return null;
//    }

}
