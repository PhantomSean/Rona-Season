

import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.util.ArrayList;
import java.util.List;

class TestScoringFunctions {

    //TEST METHODS

    //method which tests the checkForPref method
    static String testCheckForPref(){
        List<String> preferences = new ArrayList<>();
        preferences.add("test");
        preferences.add("z");
        preferences.add("x");
        preferences.add("y");

        Student student = new Student("check", "CS", 0, preferences, false, 0, 4.0);

        if(ScoringFunctions.checkForPref("test", student)){
            return "checkForPref method is working";
        }else{
            return "error in method checkForPref";
        }
    }

    //method which tests the getPrefNumber method
    static String testGetPrefNumber(){
        List<String> preferences = new ArrayList<>();
        preferences.add("z");
        preferences.add("test");
        preferences.add("x");
        preferences.add("y");

        Student student = new Student("check", "CS", 0, preferences, false, 0, 4.0);

        if(ScoringFunctions.getPrefNumber("test", student) == 1){
            return "getPrefNumber method is working";
        }else{
            return "error in method getPrefNumber";
        }
    }

    //method which tests the returnNumber method
    static String testReturnNumber(){
        List<Solution> testSolutions = new ArrayList<>();
        List<String> preferences = new ArrayList<>();

        Student studentOne = new Student("check", "CS", 0, preferences, false, 0, 4.0);
        Student studentTwo = new Student("chuck", "DS", 1, preferences, false, 0, 1.0);
        Student studentThree = new Student("cheek", "CS", 2, preferences, false, 0, 2.0);
        Student studentFour = new Student("cheese", "DS", 3, preferences, false, 0, 3.0);

        Project project = new Project("x", "y", "z", false);

        Solution two = new Solution(studentTwo, project, 0);
        testSolutions.add(two);
        testSolutions.add(new Solution(studentOne, project, 1));
        testSolutions.add(new Solution(studentThree, project, 1));
        testSolutions.add(new Solution(studentFour, project, 1));

        if(ScoringFunctions.returnNumber(testSolutions, two) == 0){
            return "returnNumber method is working";
        }else{
            return "error in method returnNumber";
        }
    }

    //method which tests the checkStream method
    static String testCheckStream(){
        List<Solution> testSolutions = new ArrayList<>();
        List<String> preferences = new ArrayList<>();
        testSolutions.add(new Solution(new Student("check", "CS", 0, preferences, false, 0, 4.0), new Project("x", "y", "z", false), 0));

        ScoringFunctions.checkStream(testSolutions);

        if(testSolutions.get(0).getScore() == 100){
            return "checkStream method is working";
        }else{
            return "error in method checkStream";
        }
    }

    //method which tests the checkForDuplicates method
    static String testCheckForDuplicates(){
        List<Solution> testSolutions = new ArrayList<>();
        List<String> preferences = new ArrayList<>();

        testSolutions.add(new Solution(new Student("check", "CS", 0, preferences, false, 0, 4.0), new Project("x", "y", "z", false), 0));
        testSolutions.add(new Solution(new Student("check", "CS", 0, preferences, false, 0, 4.0), new Project("x", "y", "z", false), 0));

        ScoringFunctions.checkForDuplicates(testSolutions);

        if(testSolutions.get(0).getScore() == 200){
            return "checkForDuplicates method is working";
        }else{
            return "error in method checkForDuplicates";
        }
    }
}
