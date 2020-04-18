import Classes.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SimulatedAnnealing implements Solver{
    public static void main(String[] args) throws IOException {
        simulatedAnnealing();

    }
    public void solve() throws IOException {
        simulatedAnnealing();
    }

    //method for performing Simulated Annealing

    public static void simulatedAnnealing() throws IOException {
        int check = 0;
        List<Solution> solutions = GenerateSolution.genSolution(new ArrayList<>());
        //analysing the solution before the Simulated Annealing has been performed
        ScoringFunctions.main(solutions);
        //temperature starts at the size of the list of solutions multiplied by 1.7
        double temperature = solutions.size() * 1.7;
        while(temperature > 0){
            double score = ScoringFunctions.scoreSolution(solutions);
            List<Solution> changedSolutions = HillClimbing.change(solutions);
            solutions = acceptance(solutions, changedSolutions, temperature, score);
            //standard decrease of 3
            temperature -= 3;

            if(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score) == 1.0){
                //if no change has been made, then the temperature cooling is increased
                temperature -= solutions.size() * 0.12;
                check++;
                if(check > 3){
                    //if no change has been made for 3 times in a row, then the cooling is increased
                    temperature -= solutions.size() * 0.06;
                }
            }else{
                check = 0;
            }
        }
        //analysing the solution after the Simulated Annealing has been performed
        ScoringFunctions.main(solutions);
    }

    //method for checking if the changed solution is to be accepted or not, takes Boltzmann probability into account
    private static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, double temperature, double score){
        if(score > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            double boltzmann = boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score);
            if(new Random().nextDouble() < (1-boltzmann) && boltzmann < 1.0){
                return changedSolutions;
            }
            return solutions;
        }
    }

    //method for calculating Boltzmann probability
    private static double boltzmann(double temp, double energyOne, double energyTwo){
        double energy = (energyOne - energyTwo) * 100;
        return 1/(Math.pow(Math.exp(1), (energy/ temp)));
    }

//----------------------------------------------------------------------------------------------------------------------------------//
    //TEST METHODS

    //method to test boltzmann method
    static String testBoltzmann(){
        //did the boltzmann formula on my calculator and got the answer that the method is checking against
        //checked it against an online calculator as well just to be safe
        if(Math.abs(boltzmann(100, 6, 5) - 0.904837418) < 1e-4){
            return "boltzmann method is working";
        }else{
            return "error in method boltzmann";
        }
    }
}
