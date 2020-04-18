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
        List<Solution> solutions = GenerateSolution.genSolution(new ArrayList<>());
        ScoringFunctions.main(solutions);
        double temperature = solutions.size() * 1.7;
        while(temperature > 0){
            double score = ScoringFunctions.scoreSolution(solutions);
            List<Solution> changedSolutions = HillClimbing.change(solutions);
            solutions = acceptance(solutions, changedSolutions, temperature, score);
            temperature -= 3;

            if(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score) == 1.0){
                temperature -= solutions.size() * 0.12;
            }
        }
        ScoringFunctions.main(solutions);
    }

    private static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, double temperature, double score){
        System.out.println("Boltzmann " + boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score));
        System.out.println("\n");
        if(score > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            double boltzmann = boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score);
            if(new Random().nextDouble() < (1-boltzmann) && boltzmann < 1.0){
                System.out.println("ACCEPTED");
                return changedSolutions;
            }
            return solutions;
        }
    }

    private static double boltzmann(double temp, double energyOne, double energyTwo){
        double energy = (energyOne - energyTwo) * 100;
        System.out.println("Energy " + energy);
        return 1/(Math.pow(Math.exp(1), (energy/ temp)));
    }

}
