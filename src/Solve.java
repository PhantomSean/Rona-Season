import GUI.UI;

public class Solve {
	private final UI ui = new UI();

	public static void main(String[] args){
		Solve s = new Solve();
		s.solver();

	}
	public void solver(){
		boolean validCommand = false;
		ui.displayStart();
		do {
			String command = ui.getCommand();
			if (command.equals("SA")) {
				validCommand = true;
				ui.clearInfoPanel();
				ui.displayInfoString("You have chosen Simulated Annealing");
				ui.displayInfoString("Please wait as this will take a few moments\n");
				try {
					SimulatedAnnealing SA = new SimulatedAnnealing();
					SA.solve();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ui.displayInfoString("Process finished\n");
			}
			if (command.equals("GA")) {
				validCommand = true;
				ui.clearInfoPanel();
				ui.displayInfoString("You have chosen Genetic Algorithms\n");
				ui.displayGAInfo();
				try {
					GeneticAlgorithm GA = new GeneticAlgorithm();
					int popNumber = Integer.parseInt(ui.getCommand());
					double matePercentage = Double.parseDouble(ui.getCommand());
					double cullPercentage = Double.parseDouble(ui.getCommand());
					int numGenerations = Integer.parseInt(ui.getCommand());
					ui.displayInfoString("Please wait as this will take a few moments\n");
					GA.solve(popNumber, matePercentage, cullPercentage, numGenerations);
					validCommand = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				ui.displayInfoString("Process finished\n");
			}
			if(!validCommand){
				ui.displayInfoString("\n\nINVALID INPUT:\nPlease enter either 'GA' or 'SA'");
			}
		}while(!validCommand);
	}


}
