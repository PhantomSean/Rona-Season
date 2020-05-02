package GUI;

import java.awt.*;
import javax.swing.*;


public class UI {

    private static final int FRAME_WIDTH = 1195;
    private static final int FRAME_HEIGHT = 600;

    private final InfoPanel infoPanel;
    private final InfoPanel studentPanel;
    private final CommandPanel commandPanel;
    private final JFrame frame;

    public UI(JFrame frame) {
        this.frame = frame;
        infoPanel = new InfoPanel();
        studentPanel = new InfoPanel();
        commandPanel = new CommandPanel();
        this.frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.frame.setTitle("Solver");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.add(studentPanel, BorderLayout.LINE_END);
        this.frame.add(infoPanel, BorderLayout.LINE_START);
        this.frame.add(commandPanel,BorderLayout.PAGE_END);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation(dim.width/2- this.frame.getSize().width/2, dim.height/2- this.frame.getSize().height/2);
    }
    public void quit(){
        frame.setVisible(false);
        System.exit(0);
    }

    public String getCommand() {
        return commandPanel.getCommand();
    }

    //appends text to info panel
    public void displayInfoString(String string) {
        infoPanel.addText(string);
    }

    //appends text to student panel
    private void displayStudentString(String string) {
        studentPanel.addText(string);
    }

    //Overwrites text in student panel
    public void overwriteStudentString(String string){
        studentPanel.overwriteText(string);
    }


    public void displayStart() {
        infoPanel.clear();
        studentPanel.clear();
        displayInfoString("Welcome to Solver\n");
        displayInfoString("This panel will be used to display information while a solution set is generated\n");
        displayInfoString("There is a text box below which can be used for inputting commands\n");
        displayInfoString("Please state below if you would like to use Simulated Annealing by entering 'SA' or Genetic Algorithms by entering 'GA' to generate a solution set");
        displayInfoString("\nIf you would like to quit, please enter 'quit'\n");
        displayStudentString("Welcome to Solver\n");
        displayStudentString("This panel will be used to display the students names and also the projects allocated to them\n");
    }

    public void displayGAInfo(){
        infoPanel.clear();
        displayInfoString("You have chosen Genetic Algorithms\n");
        displayInfoString("Please enter the following parameters in the text box:");
        displayInfoString("-Population size");
        displayInfoString("-Percentage to mate");
        displayInfoString("-Percentage to cull");
        displayInfoString("-Number of Generations\n");
        displayInfoString("Please enter the parameters one at a time in correct order\n");
    }
    public void displaySAInfo() {
        infoPanel.clear();
        displayInfoString("You have chosen Simulated Annealing");
        displayInfoString("Please wait as this will take a few moments\n");
    }

    public void displayFinish(){
        displayInfoString("Process finished\n");
        displayInfoString("If you would like to generate another solution please enter 'restart'");
        displayInfoString("If you would like to end the program please enter 'quit'");
    }

    public void displayFileInput(){
        displayInfoString("Enter the file size to use 60, 120, 240 or 500");
    }
}

