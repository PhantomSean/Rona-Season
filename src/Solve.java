import Classes.Solution;
import GUI.UI;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Solve {
	private static JFrame frame = new JFrame();
	static UI ui = null;

	static {
		try {
			ui = new UI(frame);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int solutionsGenerated = 0;
	private int GPAImportance;
	private static boolean custom = false;

	public static void main(String[] args) throws IOException {
		Solve s = new Solve();
		s.solver();
	}

	private void solver() throws IOException {
		boolean validCommand = false;
		int fileSize=0;

		ui.displaySliderText();

		ui.displayFileInput();

		do{
			//Keep asking for input until user selects a valid file size
			String command = ui.getCommand();
			if(command.equals("60") || command.equals("120") || command.equals("240") || command.equals("500")){
				fileSize = Integer.parseInt(command);
				validCommand = true;
			}else if(command.toLowerCase().equals("custom")){
				ui.importFile(frame);
				custom=true;
				fileSize = 1;
				validCommand = true;
			}else
				ui.displayInfoString("Please enter a valid file size");
		}while(!validCommand);

		GPAImportance = ui.getSliderInput();

		validCommand = false;



		if(fileSize==1)
			custom=true;
		//Keep asking for input until user inputs valid mode, SA, GA or quit
		if(custom) {
			ui.removeImportPanel();
			ui.displayInfoString("You have chosen the file: "+ui.getFileName());
			ui.displayInfoString(PopulateClasses.analyzeFile(ui.getFileName(), custom));
		}else {
			ui.clearInfoPanel();
			ui.displayInfoString("You have chosen file size: " + fileSize + "\n");
			ui.displayInfoString(PopulateClasses.analyzeFile("Students&Preferences(" + fileSize + ").xlsx", custom));
		}
		ui.displayStart();
		ui.displayInfoString("GPA Importance for this is " +GPAImportance);
		do {

			//convert to lower to allow for upper and lower inputs
			String command = ui.getCommand().toLowerCase();
			if (command.equals("sa")) {
				validCommand = true;
				simulatedAnnealing(fileSize, GPAImportance);
				ui.displayFinish();
			}
			if (command.equals("ga")) {
				validCommand = true;
				geneticAlgorithm(fileSize, GPAImportance);
				ui.displayFinish();
			}
			if (command.equals("quit")) {
				ui.quit();
			}
			if (command.equals("restart")) {
				ui.clearInfoPanel();
				solver();
			}
			if(!validCommand){
				ui.displayInfoString("\n\nINVALID INPUT:\nPlease enter either 'GA' or 'SA'");
			}
		}while(!validCommand);
		String command = ui.getCommand().toLowerCase();
		if (command.equals("quit")) {
			ui.quit();
		}
		if(command.equals("restart")){
			ui.clearInfoPanel();
			solver();
		}
	}

	private void geneticAlgorithm(int fileSize, int GPAInput){
		ui.displayGAInfo();
		try {
			GeneticAlgorithm GA = new GeneticAlgorithm();
			int popNumber=0;
			double matePercentage=0;
			double cullPercentage=0;
			int numGenerations=0;
			boolean isIntOne;
			boolean isIntTwo;
			boolean isDoubleOne;
			boolean isDoubleTwo;

			//keep asking for input until all 4 parameters are correct for GA
			ui.displayInfoString("\nPlease Enter Population Size");
			do{
				String command = ui.getCommand();
				isIntOne = checkInt(command);
				if(isIntOne)
					popNumber = Integer.parseInt(command);
				ui.displayInfoString("Population Size: "+command);
				ui.displayInfoString("\nPlease Enter Mate Percentage");
				command = ui.getCommand();
				isDoubleOne = checkDouble(command);
				if(isDoubleOne)
					matePercentage = Double.parseDouble(command);
				ui.displayInfoString("Mate Percentage: "+command+"%");
				ui.displayInfoString("\nPlease Enter Cull Percentage");
				command = ui.getCommand();
				isDoubleTwo = checkDouble(command);
				if(isDoubleTwo)
					cullPercentage = Double.parseDouble(command);
				ui.displayInfoString("Cull Percentage: "+command+"%");
				ui.displayInfoString("\nPlease Number of Generations");
				command = ui.getCommand();
				isIntTwo = checkInt(command);
				if(isIntTwo)
					numGenerations = Integer.parseInt(command);
				ui.displayInfoString("Number of Generations: "+command);
				if(!isIntOne || !isIntTwo || !isDoubleOne || !isDoubleTwo){
					ui.displayInfoString("\n\nWARNING: INVALID INPUT\nPlease re-enter your values\n\n");
				}
			}while(!isIntOne || !isIntTwo || !isDoubleOne || !isDoubleTwo);
			ui.displayInfoString("Please wait as this will take a few moments\n");
			List<Solution> sol = GA.solve(popNumber, matePercentage, cullPercentage, numGenerations, fileSize,custom,GPAInput);
			solutionsGenerated++;
			GeneticAlgorithm.createSolutionFile(sol, "Solutions("+solutionsGenerated+").xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void simulatedAnnealing(int fileSize, int GPAInput){
		ui.displaySAInfo();
		try {
			SimulatedAnnealing SA = new SimulatedAnnealing();
			List<Solution> sol = SA.solve(fileSize, custom,GPAInput);
			solutionsGenerated++;
			GeneticAlgorithm.createSolutionFile(sol, "Solutions("+solutionsGenerated+").xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkInt(String string){
		try {
			Integer.parseInt(string);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}

	private boolean checkDouble(String string){
		try {
			Double.parseDouble(string);
			return true;
		} catch(NumberFormatException e){
			return false;
		}
	}



}
