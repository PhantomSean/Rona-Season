
import java.io.IOException;

public class TestSuite {
    public static void main(String[] args) throws IOException {
        testSuite();
    }
    //method for calling all the test methods
    private static void testSuite() throws IOException {
        System.out.println("\n");
        System.out.println("Test Results from class ScoringFunctions:");
        System.out.println(TestScoringFunctions.testCheckForPref());
        System.out.println(TestScoringFunctions.testGetPrefNumber());
        System.out.println(TestScoringFunctions.testReturnNumber());
        System.out.println(TestScoringFunctions.testCheckStream());
        System.out.println(TestScoringFunctions.testCheckForDuplicates());
        System.out.println("\n");
        System.out.println("Test Results from class GenerateSolution:");
        System.out.println(TestGenerateSolution.testGenGPA());
        System.out.println("\n");
        System.out.println("Test Results from class HillClimbing:");
        System.out.println(TestHillClimbing.testFindProjectByTitle());
        System.out.println(TestHillClimbing.testFindSolNumberByStudent());
        System.out.println(TestHillClimbing.testFindStudentByProject());
        System.out.println(TestHillClimbing.testAcceptance());
        System.out.println("\n");
        System.out.println("Test Results from class SimulatedAnnealing:");
        System.out.println(TestSimulatedAnnealing.testBoltzmann());
        System.out.println("\n");
        System.out.println("Test Results from class GeneticAlgorithm:");
        System.out.println(TestGeneticAlgorithms.testGetSolutionScore());
        System.out.println(TestGeneticAlgorithms.testMutate());
        System.out.println("\n");
    }
}
