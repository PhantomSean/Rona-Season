import java.io.IOException;

public class TestSuite {
    public static void main(String[] args) throws IOException {
        testSuite();
    }
    //method for calling all the test methods
    private static void testSuite() throws IOException {
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
        System.out.println("\n");
        System.out.println("Test Results from class HillClimbing:");
        System.out.println(HillClimbing.testFindProjectByTitle());
        System.out.println(HillClimbing.testFindSolNumberByStudent());
        System.out.println(HillClimbing.testFindStudentByProject());
        System.out.println(HillClimbing.testAcceptance());
        System.out.println("\n");
        System.out.println("Test Results from class SimulatedAnnealing:");
        System.out.println(SimulatedAnnealing.testBoltzmann());
        System.out.println("\n");
        System.out.println("Test Results from class GeneticAlgorithm:");
//        System.out.println(GeneticAlgorithm.testGenPopulation());
//        System.out.println(GeneticAlgorithm.testSortPopulation());
//        System.out.println(GeneticAlgorithm.testCullPopulation());
//        System.out.println(GeneticAlgorithm.testMate());
        System.out.println(GeneticAlgorithm.testMutate());
        System.out.println(GeneticAlgorithm.testGetSolutionScore());
//        System.out.println(GeneticAlgorithm.testGetParent());
        System.out.println("\n");

    }
}
