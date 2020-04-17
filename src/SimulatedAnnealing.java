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
            solutions = acceptance(solutions, HillClimbing.change(solutions));
            temperature -= 5;
        }
        ScoringFunctions.main(solutions);
    }

    // Decide whether or not to accept the new solution
    private static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions){

        if(ScoringFunctions.scoreSolution(solutions) > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            return solutions;
        }
    }
}
