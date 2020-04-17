import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GenerateSolution {
    private static List<Solution> solutions = new ArrayList<>();
    private static HashMap<String, Project> projects;
    private static List<Student> students = new ArrayList<>();

    private static double score_mult = 0.75;

    public static void main(String[] args) {
//        genSolution();
    }

    static List<Solution> genSolution(List<Student> changes) throws IOException {
        // NB! change value within rounded brackets to test the other data sets
        solutions = new ArrayList<>();
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");

        // For giving students their first preference when coming from the hillClimbing class
        for (Student change : changes) {
            for(Student student : students) {
                if (student.getStudentId() == change.getStudentId() && !student.hasProject() && !projects.get(student.getPreference(0)).isTaken()) {
                    projects.get(student.getPreference(0)).setTaken(true);
                    student.setHasProject(true);
                    student.setPrefGotten(1);
                    solutions.add(new Solution(student, projects.get(student.getPreference(0)), Math.pow(score_mult, 10)));
                }
            }
        }
        for (Solution solution : solutions)
            System.out.println("This change has been added to solutions " + solution.getStudent().getName() + "\t" + solution.getProject().getTitle());


        for(int i = 0; i < 10; i++){
            if(i == 0)
                assignSelfSpecified(students);
            assignUnique(students, i);
            assignByGPA(students, i);
        }

        for (Student student : students) {
            if (!student.hasProject()) {
                Solution sol = new Solution(student, giveRandomProject(student.getStream()), Math.pow(score_mult, 0));
                student.setPrefGotten(0);
                student.setHasProject(true);
                solutions.add(sol);
            }

        }

        return solutions;

    }

    //method which assigns preferences based on students GPA
    private static void assignByGPA(List<Student> students, int preference) {
        int tmp;
        List<Student> temp = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            //goes through all students and if multiple students have the same project at the same preference adds them to the List temp
            if (!students.get(i).hasProject() && !projects.get(students.get(i).getPreference(preference)).isTaken() && checkForOthers(students, preference, i, students.get(i).getPreference(preference))) {
                temp.add(students.get(i));
                tmp = i + 1;

                for (int j = tmp; j < students.size(); j++) {
                    if (temp.get(0).getPreference(preference).equals(students.get(j).getPreference(preference)) && !students.get(j).hasProject()) {
                        temp.add(students.get(j));
                    }
                }
                if(temp.size() > 1) {
                    while(temp.size() != 1){
                        if(temp.get(0).getGPA() > temp.get(1).getGPA()){
                            temp.remove(1);
                        }else{
                            temp.remove(0);
                        }
                    }
                        //creates a new solution, calculates the score and stores the solution
                        Solution s = new Solution(temp.get(0), projects.get(temp.get(0).getPreference(preference)), Math.pow(score_mult, 10-preference));
                        temp.get(0).setHasProject(true);
                        temp.get(0).setPrefGotten(preference + 1);
                        projects.get(temp.get(0).getPreference(preference)).setTaken(true);
                        solutions.add(s);
                        temp.clear();
                }
            }
        }
    }


    //method which checks if there are other students with the same project as same preference, returns a boolean
    private static boolean checkForOthers(List<Student> students, int pref, int n, String project){
        n++;
        for(int i = n; i < students.size(); i++){
            if(students.get(i).getPreference(pref).equals(project)){
                return true;
            }
        }
        return false;
    }

    //gives random project to student while taking students stream into account
    static Project giveRandomProject(String studentStream) {
        Project project = genProject(studentStream);
        while(project.isTaken()){
            project = genProject(studentStream);
        }
        project.setTaken(true);
        return project;
    }
    //generates a random project
	private static Project genProject(String studentStream) {
        Random generator = new Random();
        Object[] values = projects.values().toArray();
        Project project = (Project) values[generator.nextInt(values.length)];
        while (!project.getStream().equals(studentStream) && !project.getStream().equals("CS + DS")) {
            values = projects.values().toArray();
            project = (Project) values[generator.nextInt(values.length)];
        }
		return project;
	}

	//method that if a student has a unique project choice as a preference, assigns the project to the student
    private static void assignUnique(List<Student> students, int preference) {
        int check;
        for (int i = 0; i < students.size(); i++) {
            check = 0;
            for (Student student : students) {
                if (students.get(i).getPreference(preference).equals(student.getPreference(preference))) {
                    check++;
                }
            }
            if(check == 1 && !students.get(i).hasProject() && !projects.get(students.get(i).getPreference(preference)).isTaken()) {
                Solution solution = new Solution(students.get(i), projects.get(students.get(i).getPreference(preference)), Math.pow(score_mult, 10-preference));
                solutions.add(solution);
                students.get(i).setHasProject(true);
                projects.get(students.get(i).getPreference(preference)).setTaken(true);
                students.get(i).setPrefGotten(preference + 1);
            }
        }

    }

    //method that if a student has a self specified project, the project is assigned
    private static void assignSelfSpecified(List<Student> students) {
        String title = "Self Specified Project";
        for (Student student : students) {
            if (!student.hasProject() && student.getPreference(0).equals(title)) {
                Project project = new Project("Self Specified", student.getStream(), student.getName(), true);
                Solution solution = new Solution(student, project, Math.pow(score_mult, 10));
                solutions.add(solution);
                student.setHasProject(true);
                student.setPrefGotten(0);
                student.setPrefGotten(1);
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------//
    //TEST METHODS

    //method that tests the genGPA method
    static String testGenGPA(){
        List<String> preferences = new ArrayList<>();
        Student student = new Student("check", "CS", 0, preferences, false, 0, PopulateClasses.genGPA());
        if(student.getGPA() > 4.2 && student.getGPA() < 1){
            return "error in method genGPA";
        }else{
            return "genGPA method is working";
        }
    }

    //method that tests the checkForOthers method
    static String testCheckForOthers(){
        List<String> testPreferences = new ArrayList<>();
        List<Student> testStudents = new ArrayList<>();
        testPreferences.add("test");
        testPreferences.add("z");
        testPreferences.add("x");
        testPreferences.add("y");

        testStudents.add(new Student("check", "CS", 0, testPreferences, false, 0, 4.0));
        testStudents.add(new Student("chuck", "DS", 1, testPreferences, false, 0, 1.0));

        if(checkForOthers(testStudents, 0, 0, "test")){
            return "checkForOthers method is working";
        }else{
            return "error in method checkForOthers";
        }
    }
}
