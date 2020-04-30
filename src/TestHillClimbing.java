import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.util.ArrayList;
import java.util.List;

class TestHillClimbing {

    //method for checking if acceptance is working
    static String testAcceptance(){
        List<Solution> testSolutionsOne = new ArrayList<>();
        List<Solution> testSolutionsTwo = new ArrayList<>();
        List<String> prefs = new ArrayList<>();
        Project testProject = new Project("test", "t", "t", true);
        testSolutionsOne.add(new Solution( new Student("Sam", "t", 1, prefs, true, 1, 4.2), testProject, 0));
        testSolutionsTwo.add(new Solution( new Student("Sean", "t", 1, prefs, true, 1, 1.2), testProject, 20));

        List<Solution> test = HillClimbing.acceptance(testSolutionsOne, testSolutionsTwo);

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

        Student testStudent = HillClimbing.findStudentByProject(testSolutions, "test");

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

        int test = HillClimbing.findSolNumberByStudent(testSolutions, testStudent);

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

        Project test = HillClimbing.findProjectByTitle(testSolutions, project);

        if(test.getTitle().equals(project)){
            return "method findProjectbyTitle is working";
        }else{
            return "error in method findProjectbyTitle";
        }
    }
}
