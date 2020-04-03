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
    private static int[] prefs = new int[10];

    public static void main(String[] args) throws IOException {
        genSolution();

    }

    private static void genSolution() throws IOException {

        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        staff = PopulateClasses.populateStaff("Staff&Projects(60).xlsx");


        for(int i = 0; i < 10; i++){
            prefs[i] = 0;
        }
        for(int i = 0; i < 10; i++){
            assignUnique(students, projects, i);
            randomlyAssign(students, i);
        }
        System.out.println(prefs[0] + " students got their first preference");
        System.out.println(prefs[1] + " students got their second preference");
        System.out.println(prefs[2] + " students got their third preference");
        System.out.println(prefs[3] + " students got their fourth preference");
        System.out.println(prefs[4] + " students got their fifth preference");

        for (Student student : students) {
            if (!student.hasProject()) {
                Solution sol = new Solution(student, giveRandomProject());
                solutions.add(sol);
            }
        }
        System.out.println(solutions.size());
    }

    private static void randomlyAssign(List<Student> students, int preference) {
        int tmp;
        List<Student> temp = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if (!students.get(i).hasProject() && !projects.get(students.get(i).getPreference(preference)).isTaken() && checkForOthers(students, preference, i, students.get(i).getPreference(preference))) {
                temp.add(students.get(i));
                tmp = i + 1;

                for (int j = tmp; j < students.size(); j++) {
                    if (temp.get(0).getPreference(preference).equals(students.get(j).getPreference(preference)) && !students.get(j).hasProject()) {
                        temp.add(students.get(j));
                    }
                }
                if(temp.size() > 1) {
                    Random r = new Random();
                    int random = r.nextInt(temp.size() - 1);
                    Solution s = new Solution(temp.get(random), projects.get(temp.get(random).getPreference(preference)));
                    temp.get(random).setHasProject(true);
                    temp.get(random).setPrefGotten(preference + 1);
                    projects.get(temp.get(random).getPreference(preference)).setTaken(true);
                    solutions.add(s);
                    temp.clear();
                    prefs[preference]++;
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
        return (Project) values[generator.nextInt(values.length)];
	}

    private static void assignUnique(List<Student> students, HashMap<String, Project> projects, int preference) {
        int check;
        for (int i = 0; i < students.size(); i++) {
            check = 0;
            for (Student student : students) {
                if (students.get(i).getPreference(preference).equals(student.getPreference(preference))) {
                    check++;
                }
            }
            if(check == 1 && !students.get(i).hasProject() && !projects.get(students.get(i).getPreference(preference)).isTaken()) {
                Solution solution = new Solution(students.get(i), projects.get(students.get(i).getPreference(preference)));
                solutions.add(solution);
                students.get(i).setHasProject(true);
                projects.get(students.get(i).getPreference(preference)).setTaken(true);
                students.get(i).setPrefGotten(preference + 1);
                prefs[preference]++;
            }
        }

    }

}
