import Classes.Project;
import Classes.Solution;
import Classes.Student;
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
    private  static HashMap<String, Project> projects;             //populating projects HashMap and students List
	private static List<Student> students;


    private static List<Solution> temp = new ArrayList<>();



    private void fillData(int fileSize, boolean custom){
	    try {
	        if(custom){
                students = PopulateClasses.populateCustomStudentClass("Student Data.xlsx");
            }else {
                students = PopulateClasses.populateStudentClass("Students&Preferences(" + fileSize + ").xlsx");
            }
	    } catch (IOException e) {
		    e.printStackTrace();
	    }

	    try {
            if(custom){
                projects = PopulateClasses.populateCustomProjectClass("Student Data.xlsx");
            }else {
                projects = PopulateClasses.populateProjectClass("Staff&Projects(" + fileSize + ").xlsx");
            }
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
    }

    public List<Solution> solve(int popNumber, double matePercentage, double cullPercentage, int numGenerations, int fileSize, boolean custom, int GPAInput){
	    List<Solution> fittestSolution;
        fillData(fileSize, custom);
    	long startTime = System.currentTimeMillis();            //starting the timer
        //calling the geneticAlgorithm method with the population number, number of generations and percentages for culling and mating declared
        geneticAlgorithm(popNumber, matePercentage, cullPercentage, numGenerations, custom,GPAInput);
        sortPopulation(GPAInput);             //sorting the finalized list of solutions
        ScoringFunctions.scoreSolution(population.get(0),GPAInput);//Analysing the most optimal solution found



        long endTime = System.currentTimeMillis();
        //Stating the time took to complete
        System.out.println("Execution time : " + (endTime-startTime)/60000 + " minutes");
        Solve.ui.displayInfoString("Execution time : " + (endTime-startTime)/60000 + " minutes");

        fittestSolution = population.get(0);

        population.clear();
        projects.clear();
        students.clear();

        return fittestSolution;
    }

    public List<Solution> solve(int fileSize, boolean custom, int GPAInput){
        return null;
    }


    //method for performing the genetic algorithm
    private static void geneticAlgorithm(int popNumber, double matePercentage, double cullPercentage, int numGenerations, boolean custom, int GPAInput) {
        int check = 0;
        population = new ArrayList<>();

        genPopulation(popNumber, custom);           //generating and sorting the population
        System.out.println("---------------------------------------------------------------");
        for(int i = 0; i < numGenerations; i++){//Each generation is sorted and then culled
            sortPopulation(GPAInput);
        	StringBuilder output = new StringBuilder();
        	for(Student student : students){
        		Project proj = HillClimbing.findProjectByStudent(population.get(0) , student.getName());
        		if(student.getPrefGotten() == 0){
                    output.append("-------------------------------------------------------------------------------\nName: ").append(student.getName()).append("\tStudent No.: ").append(student.getStudentId()).append("\tGPA: ").append(student.getGPA()).append("\nProject: ").append(proj.getTitle()).append("\nPreference: ").append("None").append("\n");
                }else{
                    output.append("-------------------------------------------------------------------------------\nName: ").append(student.getName()).append("\tStudent No.: ").append(student.getStudentId()).append("\tGPA: ").append(student.getGPA()).append("\nProject: ").append(proj.getTitle()).append("\nPreference: ").append(student.getPrefGotten()).append("\n");
                }
	        }
        	Solve.ui.overwriteStudentString(output.toString());

            System.out.println("Population size before culling: " + population.size());
            cullPopulation(cullPercentage);
            System.out.println("Population size after culling: " + population.size());
            System.out.println("Number to add back to the population: " + (int) (popNumber*cullPercentage*0.01));
            for (int j = 0; j < (int) (popNumber*cullPercentage*0.01); j++) {               //mating is performed with the amount of new solutions produced during mating
                insertToPopulation((ArrayList<Solution>) mate(popNumber, matePercentage,GPAInput, custom), GPAInput);      //matching the amount that was culled
            }
            output = new StringBuilder();
            output.append("-------------------------------------------------------------------------------\nBEST SCORE OF GENERATION ").append(i + 1).append(": ").append(ScoringFunctions.scoreSolution(population.get(0),GPAInput));
            Solve.ui.displayInfoString(output.toString());
            System.out.println("\nBEST SCORE OF GENERATION " + (i+1)+ ": "+ScoringFunctions.scoreSolution(population.get(0),GPAInput)+"\nSize of population: "+population.size() +"\n---------------------------------------------------------------");         //printing the best score of the generation
            if((ScoringFunctions.scoreSolution(population.get(0),GPAInput) < (0.075 * students.size())) && (ScoringFunctions.scoreSolution(population.get(0),GPAInput) == ScoringFunctions.scoreSolution(temp,GPAInput))){        //if the score is underneath 25 and the best score
                check++;                                                                                                                                                        //is the same as the last generation, then check is incremented
            }else{                                                                                                                                                              //otherwise check is reset
                check = 0;
            }
            if(check == 5){                         //if check reaches 5 then the current population is judged as the best and is returned
                return;                             //to ensure that the runtime is not longer than it needs to be
            }

	        double dI = i+1;
            double progress = (dI/ (double) numGenerations) * 50;
	        int val = (int) progress + 50;
	        Solve.ui.setProgress(val);

            temp = population.get(0);
            ScoringFunctions.analyse(temp);
        }
    }

    //method for generating the population
    private static void genPopulation(int popNumber, boolean custom) {
        for(int i = 0; i < popNumber; i++){
            List<Solution> solutions;
            solutions = GenerateSolution.genSolution(projects, students, new ArrayList<>(), true, custom);
            population.add((ArrayList<Solution>) solutions);
        }
    }

    //method for sorting the population
    private static void sortPopulation(int GPAInput) {
        int n = population.size();                                              //We decided to use bubbleSort
        for(int i = 0; i < n-1; i++){
            for(int j = 0; j < n-i-1; j++){
                if(ScoringFunctions.scoreSolution(population.get(j),GPAInput) > ScoringFunctions.scoreSolution(population.get(j+1),GPAInput)){
                    Collections.swap(population, j, j+1);                       //swapping the solution sets if the score of solution j is greater than the score of solution j+1
                }
            }
        }
    }

    private static void insertToPopulation(ArrayList<Solution> child, int GPAInput) {
        for (int i = 0; i < population.size(); i++) {
            if (ScoringFunctions.scoreSolution(population.get(i),GPAInput) > ScoringFunctions.scoreSolution(child,GPAInput)) {
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
        //removes the bottom x% of the population
        if (popSize > (popSize - number)) {
            population.subList((popSize - number), popSize).clear();
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
	private static List<Solution> mate(int popNumber, double matingPercentage,int GPAInput, boolean custom) {
		List<Solution> parentOne = List.copyOf(getParent(popNumber, matingPercentage));
		List<Solution> parentTwo = List.copyOf(getParent(popNumber, matingPercentage));

		List<Solution> child = GenerateSolution.genSolution(projects, students, new ArrayList<>(), true, custom);
		for (int i = 0; i < parentOne.size(); i++) {
			double inherit = new Random().nextDouble();
			if(inherit < 0.4875) {
				child.get(i).setProject(parentOne.get(i).getProject());
				child.get(i).getStudent().setPrefGotten(parentOne.get(i).getStudent().getPrefGotten());
				child.get(i).setScore(parentOne.get(i).getScore());
			}
			else if(inherit >= 0.4875 && inherit < 0.975) {
				for (int j = 0; j < parentOne.size(); j++) {
					if (parentOne.get(i).getStudent().getName().equals(parentTwo.get(j).getStudent().getName())) {
                        child.get(i).setProject(parentTwo.get(i).getProject());
                        child.get(i).getStudent().setPrefGotten(parentTwo.get(i).getStudent().getPrefGotten());
                        child.get(i).setScore(parentTwo.get(i).getScore());
					}
				}
			}
			else if(inherit >= 0.975) {
				Solution sol = new Solution(parentOne.get(i).getStudent(), mutate(parentOne, parentTwo, projects), 0);
				sol.setScore(getSolutionScore(sol));
				child.set(i, sol);
			}
		}
		ScoringFunctions.scoreSolution(child,GPAInput);
		return child;
	}

    //method that calculates a solutions score in case of mutation
    static double getSolutionScore(Solution solution){
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
	static Project mutate(List<Solution> parent1, List<Solution> parent2, HashMap<String, Project> p){
		Collection<Project> projectsCollection = p.values();
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
	static void createSolutionFile(List<Solution> solutions, String writeFile) throws IOException {
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
}