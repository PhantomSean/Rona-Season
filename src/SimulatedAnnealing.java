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
            double score = ScoringFunctions.scoreSolution(solutions);
            List<Solution> changedSolutions = HillClimbing.change(solutions);
            solutions = acceptance(solutions, changedSolutions, temperature, score);
            temperature -= 5;

            if(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score) == 1.0){
                temperature -= 10;
            }
        }
        ScoringFunctions.main(solutions);
    }

    static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, int temperature, double score){
        System.out.println(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score));
        System.out.println("\n");
        if(score > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            if(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score) < 0.97){
                return changedSolutions;
            }
            return solutions;
        }
    }

    private static double boltzmann(int temp, double energyOne, double energyTwo){
        double energy = (energyOne - energyTwo) * 10;
        System.out.println(energy);
        return 1/(Math.pow(Math.exp(1), (energy/ (double) temp)));
    }

}
