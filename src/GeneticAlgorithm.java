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

	public static List<Solution> mate(List<Solution> parentOne, List<Solution> parentTwo) {
		List<Solution> child = new ArrayList<>();
		int count = 0;
		for (Solution solution: parentOne) {
			double inherit = new Random().nextDouble();
			if(inherit < 0.4875) {
				child.add(solution);
				count++;
			}
			else if(inherit >= 0.4875 && inherit < 0.975) {
				for (Solution solution1 : parentTwo) {
					if (solution.getStudent().getName().equals(solution1.getStudent().getName())) {
						child.add(solution1);
						count++;
					}
				}
			}
			else if(inherit >= 0.975) {
//				child.add(new Solution(solution.getStudent(), mutate(parentOne, parentTwo), null);
				System.out.println("Call to mutate");
				count++;
			}
		}
		System.out.println("Count = " + count);
		return child;
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

	static String testMate() throws IOException {
		List<Solution> parentOne = GenerateSolution.genSolution(new ArrayList<>());
		List<Solution> parentTwo = GenerateSolution.genSolution(new ArrayList<>());
		List<Solution> child = mate(parentOne, parentTwo);
//		for (Solution solution : child) {
//			System.out.println(solution.getStudentName() + " " + solution.getProjectTitle());
//		}
//		System.out.println(child.size());
		if (child.size() == parentOne.size())
			return "testMate method is working";
		else
			return "error in method mate";
	}
}
