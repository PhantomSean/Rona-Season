import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class SimulatedAnnealing implements Solver{

    private  static HashMap<String, Project> projects;
    private static List<Student> students;


    public List<Solution> solve(int fileSize, boolean custom, int GPAInput) throws IOException {
        fillData(fileSize, custom);

        List<Solution> answer = simulatedAnnealing(custom, GPAInput);

        projects.clear();
        students.clear();

        return answer;
    }


    public List<Solution> solve(int popNumber, double matePercentage, double cullPercentage, int numGenerations, int fileSize, boolean custom, int GPAInput){
        return null;
    }

    //method for performing Simulated Annealing

    private static List<Solution> simulatedAnnealing(boolean custom, int GPAInput) {
        int check = 0;
        List<Solution> solutions = GenerateSolution.genSolution(projects, students, new ArrayList<>(), false, custom);
        ScoringFunctions.scoreSolution(solutions, GPAInput);
        //temperature starts at the size of the list of solutions multiplied by 1.7
        double temperature = solutions.size() * 1.7;
        while(temperature > 0){
            double score = ScoringFunctions.scoreSolution(solutions, GPAInput);
            List<Solution> changedSolutions = HillClimbing.change(solutions);
            solutions = acceptance(solutions, changedSolutions, temperature, score,GPAInput);
            //standard decrease of 3
            temperature -= 3;

            if(boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions, GPAInput), score) == 1.0){
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
            Solve.ui.displayInfoString("-------------------------------------------------------------------------------\nEnergy: " + ScoringFunctions.scoreSolution(solutions, GPAInput) + "\nFitness: -" + ScoringFunctions.scoreSolution(solutions,GPAInput) + "\nTemperature: "+temperature);

        }

        StringBuilder output = new StringBuilder();
        for(Student student : students){
            Project proj = HillClimbing.findProjectByStudent(solutions, student.getName());
            if(student.getPrefGotten() == 0){
                output.append("-------------------------------------------------------------------------------\nName: ").append(student.getName()).append("\tStudent No.: ").append(student.getStudentId()).append("\tGPA: ").append(student.getGPA()).append("\nProject: ").append(proj.getTitle()).append("\nPreference: ").append("None").append("\n");
            }else{
                output.append("-------------------------------------------------------------------------------\nName: ").append(student.getName()).append("\tStudent No.: ").append(student.getStudentId()).append("\tGPA: ").append(student.getGPA()).append("\nProject: ").append(proj.getTitle()).append("\nPreference: ").append(student.getPrefGotten()).append("\n");
            }
        }
        Solve.ui.overwriteStudentString(output.toString());
        //analysing the solution after the Simulated Annealing has been performed
        ScoringFunctions.scoreSolution(solutions,GPAInput);

        return solutions;
    }


    private void fillData(int fileSize, boolean custom){
        try {
            if(custom){
                students = PopulateClasses.populateCustomStudentClass(Solve.ui.getFileName());
            }else {
                students = PopulateClasses.populateStudentClass("Students&Preferences(" + fileSize + ").xlsx");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(custom){
                projects = PopulateClasses.populateCustomProjectClass(Solve.ui.getFileName());
            }else {
                projects = PopulateClasses.populateProjectClass("Staff&Projects(" + fileSize + ").xlsx");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //method for checking if the changed solution is to be accepted or not, takes Boltzmann probability into account
    private static List<Solution> acceptance(List<Solution> solutions, List<Solution> changedSolutions, double temperature, double score, int GPAInput){
        if(score > ScoringFunctions.scoreSolution(changedSolutions, GPAInput)){
            return changedSolutions;
        }else{
            double boltzmann = boltzmann(temperature, ScoringFunctions.scoreSolution(changedSolutions, GPAInput), score);
            // The chance of accepting a worse result is boltzmann score
            // Have to check that boltzmann is less than 1 as because of our scoring system
            if(new Random().nextDouble() <= boltzmann && boltzmann < 1.0){
                return changedSolutions;
            }
            return solutions;
        }
    }

    //method for calculating Boltzmann probability
    static double boltzmann(double temp, double energyOne, double energyTwo){
        double energy = (energyOne - energyTwo) * 100;
        return 1/(Math.pow(Math.exp(1), (energy/ temp)));
    }
}