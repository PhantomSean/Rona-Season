package GUI;

import java.awt.*;
import javax.swing.*;

public class UI {

    private static final int FRAME_WIDTH = 885;
    private static final int FRAME_HEIGHT = 600;

    private final InfoPanel infoPanel;
    private final InfoPanel studentPanel;
    private final CommandPanel commandPanel;

    public UI() {
        infoPanel = new InfoPanel();
        studentPanel = new InfoPanel();
        commandPanel = new CommandPanel();
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Solver");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(studentPanel, BorderLayout.LINE_END);
        frame.add(infoPanel, BorderLayout.LINE_START);
        frame.add(commandPanel,BorderLayout.PAGE_END);
        frame.setResizable(false);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
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

