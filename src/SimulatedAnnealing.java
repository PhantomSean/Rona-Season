import Classes.Project;
import Classes.Solution;
import Classes.Student;
import GUI.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class SimulatedAnnealing implements Solver{
    private  static HashMap<String, Project> projects;
    private static List<Student> students;

    public void solve() throws IOException {
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        simulatedAnnealing();
    }
    public void solve(int popNumber, double matePercentage, double cullPercentage, int numGenerations){}

    //method for performing Simulated Annealing

    private static void simulatedAnnealing() throws IOException {
        int check = 0;
        List<Solution> solutions = GenerateSolution.genSolution(projects, students, new ArrayList<>());
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
                //resetting check if there is a change made
                check = 0;
            }
        }

        String output = "";
        for(Student student : students){
            Project proj = HillClimbing.findProjectByStudent(solutions, student.getName());
            output += ("---------------------------------------------------------------------------------\nName: " + student.getName() + "\nProject: " +proj.getTitle()) + "\nPreference: "+ student.getPrefGotten()+ "\n";
        }
        Solve.ui.overwriteStudentString(output);
        //analysing the solution after the Simulated Annealing has been performed
        ScoringFunctions.main(solutions);
    }

    //method for checking if the changed solution is to be accepted or not, takes Boltzmann probability into account
    private static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, double temperature, double score){
        if(score > ScoringFunctions.scoreSolution(changedSolutions)){
            return changedSolutions;
        }else{
            double boltzmann = boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions), score);
            // The chance of accepting a worse result is boltzmann score
            // Have to check that boltzmann is less than 1 as because of our scoring system
            if(new Random().nextDouble() <= boltzmann && boltzmann < 1.0){
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
        if(Math.abs(boltzmann(100, 6, 5) - 0.367879441) < 1e-4){
            return "boltzmann method is working";
        }else{
            return "error in method boltzmann";
        }
    }
}