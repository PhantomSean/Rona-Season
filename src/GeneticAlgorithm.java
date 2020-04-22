import Classes.Project;
import Classes.Solution;
import Classes.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm implements Solver{

	private static List<ArrayList<Solution>> population = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		population = genPopulation(5);
		for(int i = 0; i < population.size(); i++) {
			ScoringFunctions.main(population.get(i));
		}
		cullPopulation(10, sortPopulation(population));
		System.out.println("\n");
		System.out.println("CULLED");
		System.out.println("\n");
		for(int i = 0; i < population.size(); i++) {
			ScoringFunctions.main(population.get(i));
		}
	}
	public void solve() throws IOException {

	}
	private static List<ArrayList<Solution>> genPopulation(int popNumber) throws IOException {
		List<ArrayList<Solution>> population = new ArrayList<>();
		for(int i = 0; i < popNumber; i++){
			List<Solution> solutions;
			solutions = GenerateSolution.genSolution(new ArrayList<>());
			population.add((ArrayList<Solution>) solutions);
		}
		return population;
	}

	private static List<ArrayList<Solution>> sortPopulation(List<ArrayList<Solution>> population) throws IOException {
		int n = population.size();
		for(int i = 0; i < n-1; i++){
			for(int j = 0; j < n-i-1; j++){
				if(ScoringFunctions.scoreSolution(population.get(j)) > ScoringFunctions.scoreSolution(population.get(j+1))){
					Collections.swap(population, j, j+1);
				}
			}
		}
		return population;
	}

	private static List<ArrayList<Solution>> cullPopulation(double percentage, List<ArrayList<Solution>> population){
		int number = (int) Math.round((0.01 * percentage) * population.size());
		for(int i = population.size() - number; i < population.size(); i++){
			population.remove(i);
		}
		return population;
	}


	private static Project mutate(List<Solution> parent1, List<Solution> parent2, List<Project> projects){
		List<Project> unassignedProjects = new ArrayList<>(projects);

		for(int i=0; i<parent1.size(); i++){

			if (projects.contains(parent1.get(i).getProject()))
				unassignedProjects.remove(parent1.get(i).getProject());
			if (projects.contains(parent2.get(i).getProject()))
				unassignedProjects.remove(parent2.get(i).getProject());

		}
		Random rand = new Random();

		projects.get(rand.nextInt(unassignedProjects.size()));

		return unassignedProjects.get(rand.nextInt(unassignedProjects.size()));
	}

//----------------------------------------------------------------------------------------------------------------------------------//
	//TEST METHODS

	//method to test genPopulation
	static String testGenPopulation() throws IOException {
		int popNumber = 3;
		List<ArrayList<Solution>> testPopulation = genPopulation(3);
		if(testPopulation.size() == popNumber){
			return "genPopulation method is working";
		}else{
			return "error in method genPopulation";
		}
	}

	//method to test sortPopulation
	static String testSortPopulation() throws IOException {
		int check = 0;
		List<ArrayList<Solution>> testPopulation = sortPopulation(genPopulation(3));
		for(int i = 0; i < testPopulation.size() - 1; i++){
			if(ScoringFunctions.scoreSolution(testPopulation.get(i)) > ScoringFunctions.scoreSolution(testPopulation.get(i + 1))){
				check = 1;
			}
		}
		if(check == 0){
			return "sortPopulation method is working";
		}else{
			return "error in method sortPopulation";
		}
	}

	//method to test cullPopulation
	static String testCullPopulation() throws IOException {
		int check = 0;
		List<ArrayList<Solution>> testPopulation = sortPopulation(genPopulation(3));
		double temp = ScoringFunctions.scoreSolution(testPopulation.get(2));
		testPopulation = cullPopulation(33, testPopulation);
		for (ArrayList<Solution> solutions : testPopulation) {
			if (ScoringFunctions.scoreSolution(solutions) == temp) {
				check = 1;
			}
		}
		if(check == 0){
			return "cullPopulation method is working";
		}else{
			return "error in method cullPopulation";
		}
	}

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

		List<Project> projects = new ArrayList<Project>();

		projects.add(one);
		projects.add(two);
		projects.add(three);
		projects.add(four);

		Project test = mutate(parent1, parent2, projects);

		if (test.getTitle().equals("Three") || test.getTitle().equals("Four"))
			return "Mutate method working";
		else
			return "Mutate method not working";
	}
}
