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
            solutions = acceptance(solutions, HillClimbing.change(solutions), temperature, score);
            temperature -= 5;
        }
        ScoringFunctions.main(solutions);
    }

    static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, int temperature, double score){
        System.out.println(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score));
        if(score > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{/*
            if(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), ScoringFunctions.scoreSolution(solutions)) > 0.5){
                return changedSolutions;
            }*/
            return solutions;
        }
    }

    private static double boltzmann(int temp, double energyOne, double energyTwo){
        double energy = (energyOne - energyTwo) * 10;
        System.out.println(energyOne);
        System.out.println(energyTwo);
        System.out.println("------------------");
        System.out.println(energy);
        System.out.println("\n");
        return 1/(Math.pow(Math.exp(1), (energy/ (double) temp)));
    }

}
