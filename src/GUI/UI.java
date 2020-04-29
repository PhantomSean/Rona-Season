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

    public void displayInfoString(String string) {
        infoPanel.addText(string);
    }

    public void displayStudentString(String string) {
        studentPanel.addText(string);
    }

    public void overwriteStudentString(String string){
        studentPanel.overwriteText(string);
    }


    public void displayStart() {
        infoPanel.clear();
        studentPanel.clear();
        displayInfoString("Welcome to Solver\n");
        displayInfoString("This panel will be used to display information while a solution set is generated\n");
        displayInfoString("There is a text box below which can be used for inputting commands\n");
        displayInfoString("Please state below if you would like to use Simulated Annealing or Genetic Algorithms to generate a solution set");
        displayInfoString("\nIf you would like to quit, please enter 'quit'\n");
        displayInfoString("Input either 'SA' or 'GA':");
        displayStudentString("Welcome to Solver\n");
        displayStudentString("This panel will be used to display the students names and also the projects allocated to them\n");
    }

    public void clearInfoPanel(){
        infoPanel.clear();
    }

    public void clearStudentPanel(){
        studentPanel.clear();
    }

    public void displayGAInfo(){
        displayInfoString("Please enter the following parameters in the text box:");
        displayInfoString("-Population size");
        displayInfoString("-Percentage to mate");
        displayInfoString("-Percentage to cull");
        displayInfoString("-Number of Generations\n");
        displayInfoString("Please enter the parameters one at a time in correct order\n");
    }

}

