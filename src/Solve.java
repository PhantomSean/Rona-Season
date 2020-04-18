public class Solve {
	public static void main(String[] args){
		SimulatedAnnealing SA = new SimulatedAnnealing();

		try {
			SA.solve();
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}
}
