import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbing {

    public static void main(String[] args) throws IOException {
        List<Solution> solutions = GenerateSolution.genSolution(new ArrayList<>());
        ScoringFunctions.main(solutions);

        for(int i = 0; i < 100; i++) {
            solutions = acceptance(solutions, change(solutions));
        }
        ScoringFunctions.main(solutions);


    }

    // Decide whether or not to accept the new solution
    static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions){

        if(ScoringFunctions.scoreSolution(solutions) > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            return solutions;
        }
    }


    // Makes a change to the current solution by swapping the preference of a person that got a randomly assigned project
    static List<Solution> change(List<Solution> solutions) {
        //GPA Importance to do after
        double GPAImportance = 3.3;

        // Get a student who didn't get their preference whose GPA is the same as GPAImportance or higher
        List<Solution> tempSols = new ArrayList<>();
        if (GPAImportance > 0) {
            int num = 0;
            for (Solution solution : solutions) {
                if (solution.getPrefGotten() == 0 && solution.getStudent().getGPA() >= GPAImportance && num < 1) {
                    tempSols.add(solution);
                    num++;
                }
            }
        } else {
            Random rand = new Random();
            int x = rand.nextInt(solutions.size());
            int y = rand.nextInt(solutions.size());
            while (y == x) {
                y = rand.nextInt(60);
            }
            System.out.println("Getting 2 Random Students");

            tempSols.add(solutions.get(x));
            tempSols.add(solutions.get(y));

        }
        if (tempSols.size() != 0){
            for (int j = 0; j < 10; j++) {
                if (tempSols.get(0).getStudent().getGPA() > findStudentByProject(solutions, tempSols.get(0).getStudent().getPreference(j)).getGPA()) {
                    int tmpOne = findSolNumberByStudent(solutions, tempSols.get(0).getStudent());
                    int tmpTwo = findSolNumberByStudent(solutions, findStudentByProject(solutions, tempSols.get(0).getStudent().getPreference(j)));
                    Project tmpProject = findProjectByTitle(solutions, tempSols.get(0).getStudent().getPreference(j));
                    solutions.get(tmpOne).getProject().setTaken(false);                                       //swapping the projects
                    solutions.get(tmpOne).setProject(tmpProject);
                    solutions.get(tmpTwo).setProject(GenerateSolution.giveRandomProject(solutions.get(tmpTwo).getStudent().getStream()));
                    double score_mult = 0.75;
                    solutions.get(tmpOne).setScore(Math.pow(score_mult, 10 - j));                             //assigning new scores
                    solutions.get(tmpTwo).setScore(Math.pow(score_mult, 0));
                    solutions.get(tmpOne).getStudent().setPrefGotten(j);                                      //assigning the new preferences gotten
                    solutions.get(tmpTwo).getStudent().setPrefGotten(0);
                    break;
                }
            }
         }
        tempSols.clear();
        return solutions;
    }

    private static Student findStudentByProject(List<Solution> solutions, String project){
        for (Solution solution : solutions) {
            if (project.equals(solution.getProjectTitle())) {
                return solution.getStudent();
            }
        }
        return solutions.get(0).getStudent();
    }

    private static int findSolNumberByStudent(List<Solution> solutions, Student student){
        for(int i = 0; i < solutions.size(); i++){
            if(student.getName().equals(solutions.get(i).getStudent().getName())){
                return i;
            }
        }
        return 0;
    }

    private static Project findProjectByTitle(List<Solution> solutions, String project){
        for (Solution solution : solutions) {
            if (project.equals(solution.getProjectTitle())) {
                return solution.getProject();
            }
        }
        return solutions.get(0).getProject();
    }

//-----------------------------------------------------------------------------------------------------------------------------------------//
    //TEST METHODS

    //method for checking if acceptance is working
    static String testAcceptance(){
        List<Solution> testSolutionsOne = new ArrayList<>();
        List<Solution> testSolutionsTwo = new ArrayList<>();
        List<String> prefs = new ArrayList<>();
        Project testProject = new Project("test", "t", "t", true);
        testSolutionsOne.add(new Solution( new Student("Sam", "t", 1, prefs, true, 1, 4.2), testProject, 0));
        testSolutionsTwo.add(new Solution( new Student("Sean", "t", 1, prefs, true, 1, 1.2), testProject, 20));

        List<Solution> test = acceptance(testSolutionsOne, testSolutionsTwo);

        if(test.get(0).getStudent().getName().equals("Sam")){
            return "method acceptance is working";
        }else{
            return "error in method acceptance";
        }
    }

    //method for checking if findStudentByProject is working
    static String testFindStudentByProject(){
        List<Solution> testSolutions = new ArrayList<>();
        List<String> prefs = new ArrayList<>();
        Project testProject = new Project("test", "t", "t", true);
        testSolutions.add(new Solution( new Student("Sean", "t", 1, prefs, true, 1, 4.2), testProject, 0));

        Student testStudent = findStudentByProject(testSolutions, "test");

        if(testStudent.getName().equals("Sean")){
            return "method findStudentByProject is working";
        }else{
            return "error in method findStudentByProject";
        }
    }

    //method for checking if findSolNumberByStudent is working
    static String testFindSolNumberByStudent(){
        List<Solution> testSolutions = new ArrayList<>();
        List<String> prefs = new ArrayList<>();
        Student testStudent = new Student("t", "t", 1, prefs, true, 1, 4.2);
        testSolutions.add(new Solution( testStudent, new Project("test", "t", "t", true), 0));

        int test = findSolNumberByStudent(testSolutions, testStudent);

        if(test == 0){
            return "method findSolNumberByStudent is working";
        }else{
            return "error in method findSolNumberByStudent";
        }
    }

    //method for checking if findProjectByTitle is working
    static String testFindProjectByTitle(){
        String project = "test";
        List<Solution> testSolutions = new ArrayList<>();
        List<String> prefs = new ArrayList<>();
        testSolutions.add(new Solution(new Student("t", "t", 1, prefs, true, 1, 4.2), new Project("test", "t", "t", true), 0));

        Project test = findProjectByTitle(testSolutions, project);

        if(test.getTitle().equals(project)){
            return "method findProjectbyTitle is working";
        }else{
            return "error in method findProjectbyTitle";
        }
    }
}
