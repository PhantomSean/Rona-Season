import Classes.Project;
import Classes.Solution;
import Classes.Student;
import GUI.UI;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class GeneticAlgorithm implements Solver{

    private static List<ArrayList<Solution>> population = new ArrayList<>();
    private  static HashMap<String, Project> projects;
    private static List<Student> students;
    private static List<Solution> temp;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();            //starting the timer
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");             //populating projects HashMap and students List
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        //calling the geneticAlgorithm method with the population number, number of generations and percentages for culling and mating declared
        geneticAlgorithm(1000, 15, 10, 1000);
        sortPopulation();             //sorting the finalized list of solutions
		ScoringFunctions.main(population.get(0));           //Analysing the most optimal solution found

        long endTime = System.currentTimeMillis();
        //Stating the time took to complete
        System.out.println("Execution time : " + (endTime-startTime)/60000 + " minutes");
//        System.out.println("\n");
        createSolutionFile(population.get(0), "Sample Solutions("+population.get(0).size()+").xlsx");
    }

    public void solve(int popNumber, double matePercentage, double cullPercentage, int numGenerations) throws IOException{
        long startTime = System.currentTimeMillis();            //starting the timer
        projects = PopulateClasses.populateProjectClass("Staff&Projects(60).xlsx");             //populating projects HashMap and students List
        students = PopulateClasses.populateStudentClass("Students&Preferences(60).xlsx");
        //calling the geneticAlgorithm method with the population number, number of generations and percentages for culling and mating declared
        geneticAlgorithm(popNumber, matePercentage, cullPercentage, numGenerations);
        sortPopulation();             //sorting the finalized list of solutions
        ScoringFunctions.main(population.get(0));//Analysing the most optimal solution found



        long endTime = System.currentTimeMillis();
        //Stating the time took to complete
        System.out.println("Execution time : " + (endTime-startTime)/60000 + " minutes");
    }

    public void solve(){}

    //method for performing the genetic algorithm
    private static void geneticAlgorithm(int popNumber, double matePercentage, double cullPercentage, int numGenerations) {
        int check = 0;
        genPopulation(popNumber);           //generating and sorting the population
        sortPopulation();
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < numGenerations; i++){//Each generation is sorted and then culled
        	String output = "";
        	for(Student student : students){
        		Project proj = HillClimbing.findProjectByStudent(population.get(0) , student.getName());
                output += ("---------------------------------------------------------------------------------\nName: " + student.getName() + "\nProject: " +proj.getTitle()) + "\nPreference: "+ student.getPrefGotten()+ "\n";
	        }
        	Solve.ui.overwriteStudentString(output);

            System.out.println("Population size before culling: " + population.size());
            cullPopulation(cullPercentage);
            System.out.println("Population size after culling: " + population.size());
            System.out.println("Number to add back to the population: " + (int) (popNumber*cullPercentage*0.01));
            for (int j = 0; j < (int) (popNumber*cullPercentage*0.01); j++) {               //mating is performed with the amount of new solutions produced during mating
                insertToPopulation((ArrayList<Solution>) mate(popNumber, matePercentage));      //matching the amount that was culled
            }
            System.out.println("\nBEST SCORE OF GENERATION " + (i+1)+ ": "+ScoringFunctions.scoreSolution(population.get(0))+"\nSize of population: "+population.size() +"\n---------------------------------------------------------------");         //printing the best score of the generation
            if((ScoringFunctions.scoreSolution(population.get(0)) < (0.2 * students.size())) && (ScoringFunctions.scoreSolution(population.get(0)) == ScoringFunctions.scoreSolution(temp))){        //if the score is underneath 25 and the best score
                check++;                                                                                                                                                        //is the same as the last generation, then check is incremented
            }else{                                                                                                                                                              //otherwise check is reset
                check = 0;
            }
            if(check == 5){                         //if check reaches 5 then the current population is judged as the best and is returned
                return;                             //to ensure that the runtime is not longer than it needs to be
            }
            temp = population.get(0);
        }
    }

    //method for generating the population
    private static void genPopulation(int popNumber) {
        for(int i = 0; i < popNumber; i++){
            List<Solution> solutions;
            solutions = GenerateSolution.genSolution(projects, students, new ArrayList<>());
            population.add((ArrayList<Solution>) solutions);
        }
    }

    //method for sorting the population
    private static void sortPopulation() {
        int n = population.size();                                              //We decided to use bubbleSort
        for(int i = 0; i < n-1; i++){
            for(int j = 0; j < n-i-1; j++){
                if(ScoringFunctions.scoreSolution(population.get(j)) > ScoringFunctions.scoreSolution(population.get(j+1))){
                    Collections.swap(population, j, j+1);                       //swapping the solution sets if the score of solution j is greater than the score of solution j+1
                }
            }
        }
    }

    private static void insertToPopulation(ArrayList<Solution> child) {
        for (int i = 0; i < population.size(); i++) {
            if (ScoringFunctions.scoreSolution(population.get(i)) > ScoringFunctions.scoreSolution(child)) {
                population.add(i, child);
                return;
            }
        }
        population.add(child);
    }

    //method for culling the population
    private static void cullPopulation(double percentage){
        int number = (int) (0.01*percentage*population.size());         //takes in the percentage to be culled and the sorted population
        int popSize = population.size();
        System.out.println("Cull number: " + number);
        for(int i = (popSize-1); i >= (popSize-number); i--){             //removes the bottom x% of the population
            population.remove(i);
        }
    }

    //method for randomly choosing a parent to mate
    private static List<Solution> getParent(int popNumber, double matingPercentage) {
        double poolSample = new Random().nextDouble();

        int matingPool = (int) (popNumber*matingPercentage*0.01);
        int parentId;

        if (poolSample < 0.9)                                                                  //there is a 90% chance that the parent chosen will be part of the percentage chosen to mate
            parentId = new Random().nextInt(matingPool);                                       //otherwise a completely random parent is chosen
        else
            parentId = new Random().nextInt(population.size()-matingPool) + matingPool;

        return population.get(parentId);
    }

	//method for mating two parents
	private static List<Solution> mate(int popNumber, double matingPercentage) {
		List<Solution> parentOne = getParent(popNumber, matingPercentage);
		List<Solution> parentTwo = getParent(popNumber, matingPercentage);

		List<Solution> child = new ArrayList<>();
		for (Solution solution: parentOne) {
			double inherit = new Random().nextDouble();
			if(inherit < 0.4875) {
				child.add(solution);
			}
			else if(inherit >= 0.4875 && inherit < 0.975) {
				for (Solution solution1 : parentTwo) {
					if (solution.getStudent().getName().equals(solution1.getStudent().getName())) {
						child.add(solution1);
					}
				}
			}
			else if(inherit >= 0.975) {
				Solution sol = new Solution(solution.getStudent(), mutate(parentOne, parentTwo), 0);
				sol.setScore(getSolutionScore(sol));
				child.add(sol);
			}
		}
		ScoringFunctions.scoreSolution(child);
		return child;
	}

    //method that calculates a solutions score in case of mutation
    private static double getSolutionScore(Solution solution){
        int j = 10;
        for(int i = 0; i < solution.getStudent().getPreferences().size(); i++){                        //Goes through the students preferences and if one of them equals the project given
            if(solution.getProject().getTitle().equals(solution.getStudent().getPreference(i))){       //then sets the students prefGotten accordingly
                j = i;
                solution.getStudent().setPrefGotten(i);
            }
        }
        double score_mult = 0.75;
        return Math.pow(score_mult, 10 - j);            //calculating the solutions score
    }

	//method for assigning a project to mutation
	private static Project mutate(List<Solution> parent1, List<Solution> parent2){
		Collection<Project> projectsCollection = projects.values();
		ArrayList<Project> projects = new ArrayList<>(projectsCollection);
		List<Project> unassignedProjects = new ArrayList<>(projects);

		for(int i=0; i<parent1.size(); i++){
			if (projects.contains(parent1.get(i).getProject()))
				unassignedProjects.remove(parent1.get(i).getProject());
			if (projects.contains(parent2.get(i).getProject()))
				unassignedProjects.remove(parent2.get(i).getProject());
		}
		Random rand = new Random();
		return unassignedProjects.get(rand.nextInt(unassignedProjects.size()));
	}

	//method that creates an excel file that stores the solution generated
	private static void createSolutionFile(List<Solution> solutions, String writeFile) throws IOException {
        Workbook writeBook = new XSSFWorkbook();
        Sheet writeSheet = writeBook.createSheet("Classes.Solution("+solutions.size()+")");

        XSSFCellStyle style = (XSSFCellStyle) writeBook.createCellStyle();
        XSSFFont font = (XSSFFont) writeBook.createFont();
        font.setBold(true);
        style.setFont(font);

        Row row = writeSheet.createRow(0);                      //adding headers to the file
        row.createCell(0).setCellValue("Student Number");
        row.getCell(0).setCellStyle(style);
        row.createCell(1).setCellValue("Student Name");
        row.getCell(1).setCellStyle(style);
        row.createCell(2).setCellValue("GPA");
        row.getCell(2).setCellStyle(style);
        row.createCell(3).setCellValue("Stream");
        row.getCell(3).setCellStyle(style);
        row.createCell(4).setCellValue("Project");
        row.getCell(4).setCellStyle(style);
        row.createCell(5).setCellValue("Preference Gotten");
        row.getCell(5).setCellStyle(style);
        font.setBold(false);
        style.setFont(font);
        for(int i = 0; i < solutions.size(); i++){
            row = writeSheet.createRow(i+1);            //adding student and project data to the file

            row.createCell(0).setCellValue(solutions.get(i).getStudentNumber());
            row.getCell(0).setCellStyle(style);
            row.createCell(1).setCellValue(solutions.get(i).getStudentName());
            row.getCell(1).setCellStyle(style);
            row.createCell(2).setCellValue(solutions.get(i).getStudent().getGPA());
            row.getCell(2).setCellStyle(style);
            row.createCell(3).setCellValue(solutions.get(i).getStudent().getStream());
            row.getCell(3).setCellStyle(style);
            row.createCell(4).setCellValue(solutions.get(i).getProjectTitle());
            row.getCell(4).setCellStyle(style);
            if(solutions.get(i).getStudent().getPrefGotten() != 0){
                row.createCell(5).setCellValue(solutions.get(i).getStudent().getPrefGotten());
            }else{
                row.createCell(5).setCellValue("None");
            }
            row.getCell(5).setCellStyle(style);
        }

        writeBook.write(new FileOutputStream(writeFile));
        writeBook.close();

    }

//----------------------------------------------------------------------------------------------------------------------------------//
    //TEST METHODS

    //method to test the mutate method
    static String testMutate(){
        List<Solution> parent1 = new ArrayList<>();
        List<Solution> parent2 = new ArrayList<>();

        Project one = new Project("One", "CS", "ME", false);
        Project two = new Project("Two", "CS", "ME", false);
        Project three = new Project("Three", "CS", "ME", false);
        Project four = new Project("Four", "CS", "ME", false);
        List<String> empty = new ArrayList<>();

        Student me = new Student("ME", "CS", 0, empty, true, 0, 4.0);

        parent1.add(new Solution(me, one, 0));
        parent2.add(new Solution(me, two, 0));

        List<Project> projects = new ArrayList<>();

        projects.add(one);
        projects.add(two);
        projects.add(three);
        projects.add(four);

		Project test = mutate(parent1, parent2);

        if (test.getTitle().equals("Three") || test.getTitle().equals("Four"))
            return "Mutate method working";
        else
            return "Mutate method not working";
    }

    //method to test the getSolutionScore method
    static String testGetSolutionScore(){
        List<String> empty = new ArrayList<>();
        empty.add("testProject");
        Solution sol = new Solution(new Student("test", "CS", 1, empty, true, 1, 4.2), new Project("testProject", "CS","x", true), 0);
        if(Math.abs(getSolutionScore(sol) - 0.056313514) < 1e-4){
            return "getSolutionScore method is working";
        }else{
            return "error in method getSolutionScore";
        }
    }
}