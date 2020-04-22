import Classes.Solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneticAlgorithm implements Solver{

	private static List<ArrayList<Solution>> population = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		population = genPopulation(5);
		population = sortPopulation(population);
		for(int i = 0; i < population.size(); i++) {
			ScoringFunctions.main(population.get(i));
		}
	}
	public void solve() throws IOException {

	}
	public static List<ArrayList<Solution>> genPopulation(int popNumber) throws IOException {
		List<ArrayList<Solution>> population = new ArrayList<>();
		for(int i = 0; i < popNumber; i++){
			List<Solution> solutions;
			solutions = GenerateSolution.genSolution(new ArrayList<>());
			population.add((ArrayList<Solution>) solutions);
		}
		return population;
	}

	public static List<ArrayList<Solution>> sortPopulation(List<ArrayList<Solution>> population) throws IOException {
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
}
