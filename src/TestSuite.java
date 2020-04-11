import java.io.IOException;

public class TestSuite {
    public static void main(String[] args) {
        testSuite();
    }
    //method for calling all the test methods
    private static void testSuite(){
        System.out.println("\n");
        System.out.println("Test Results from class ScoringFunctions:");
        System.out.println(ScoringFunctions.testCheckForPref());
        System.out.println(ScoringFunctions.testGetPrefNumber());
        System.out.println(ScoringFunctions.testReturnNumber());
        System.out.println(ScoringFunctions.testCheckStream());
        System.out.println(ScoringFunctions.testCheckForDuplicates());
        System.out.println("\n");
        System.out.println("Test Results from class GenerateSolution:");
        System.out.println(GenerateSolution.testGenGPA());
        System.out.println(GenerateSolution.testCheckForOthers());
        System.out.println(GenerateSolution.testAssignSelfSpecified());
        System.out.println("\n");
    }
}
