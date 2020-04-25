public class Solve {
	public static void main(String[] args){
		SimulatedAnnealing SA = new SimulatedAnnealing();
		GeneticAlgorithm GA = new GeneticAlgorithm();

		try {
			SA.solve();
		}
		catch (Exception e){
			e.printStackTrace();
		}

		try{
			GA.solve(500, 15, 10, 500);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

}
