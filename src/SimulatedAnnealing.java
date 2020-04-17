import Classes.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
            solutions = acceptance(solutions, HillClimbing.change(solutions), temperature, score);
            temperature -= 5;
        }
        ScoringFunctions.main(solutions);
    }

    static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, int temperature, double score){
        System.out.println(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score));
        System.out.println("\n");
        double changedScore = ScoringFunctions.scoreSolution(changedSolutions);
        double originalScore = ScoringFunctions.scoreSolution(solutions);
        if(score > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            if(1 - boltzmann(temperature, changedScore, originalScore) > 0){
                if(new Random().nextDouble() <= (1 - boltzmann(temperature, changedScore, originalScore)))
                    System.out.println("We chose a worse solution");
                    return changedSolutions;
            }
            return solutions;
        }
    }

    private static double boltzmann(int temp, double energyOne, double energyTwo){
        double energy = (energyOne - energyTwo) * 100;
        System.out.println(energy);
        return 1/(Math.pow(Math.exp(1), (energy/ (double) temp)));
    }

}
