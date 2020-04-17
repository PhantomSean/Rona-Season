import Classes.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealing {
    public static void main(String[] args) throws IOException {
        simulatedAnnealing();

    }

    private static void simulatedAnnealing() throws IOException {
        int temperature = 100;
        List<Solution> solutions = GenerateSolution.genSolution(new ArrayList<>());
        ScoringFunctions.main(solutions);
        while(temperature > 0){
            solutions = HillClimbing.acceptance(solutions, HillClimbing.change(solutions));
            temperature -= 5;
        }
        ScoringFunctions.main(solutions);
    }
}
