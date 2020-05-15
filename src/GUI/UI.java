package GUI;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;


public class UI {

    private static final int FRAME_WIDTH = 1195;
    private static final int FRAME_HEIGHT = 600;

    private final InputPanel infoPanel;
    private final InfoPanel studentPanel;
    private CommandPanel commandPanel;
    private ImportFilePanel importFilePanel;
    private JPanel progressPanel;
    private JProgressBar progressBar;
    private final JFrame frame;

    public UI(JFrame frame) throws IOException {
        this.frame = frame;
        infoPanel = new InputPanel();
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

    public void importFile(JFrame frame) throws IOException {
        importFilePanel = new ImportFilePanel();
        studentPanel.setVisible(false);
        frame.add(importFilePanel, BorderLayout.LINE_END);
    }

    public void progress(JFrame frame) {
        progressPanel = new JPanel();
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);
        frame.add(progressPanel, BorderLayout.PAGE_START);
        frame.setSize(250, 100);
        frame.setVisible(true);
    }

    public void setProgress(int progress) {
        if (progress > 100)
            progressBar.setValue(100);
        else
            progressBar.setValue(progress);
    }

    public void removeProgress() {
        progressPanel.setVisible(false);
    }

    public String getCommand() {
        return commandPanel.getCommand();
    }

    public int getSliderInput(){ return infoPanel.getSliderInput(); }

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

    public void removeImportPanel(){
        importFilePanel.setVisible(false);
        infoPanel.clear();
        displayInfoString("Please allow time for the file to be analyzed\n\n");
    }

    public void clearInfoPanel(){
        infoPanel.clear();
    }

    public void displayStart() {
        studentPanel.setVisible(true);
        studentPanel.clear();
        displayInfoString("Please state below if you would like to use Simulated Annealing by entering 'SA' or Genetic Algorithms by entering 'GA' to generate a solution set");
        displayInfoString("\nIf you would like to restart, please enter 'RESTART\n");
        displayInfoString("\nIf you would like to quit, please enter 'quit'\n");
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
        displayInfoString("Enter the file size to use 60, 120, 240 or 500\n");
        displayInfoString("If you would like to upload your own file please enter 'CUSTOM'\n");
    }

    public String getFileName(){ return importFilePanel.getFileName(); }

    public void displaySliderText(){
        displayInfoString("Welcome to Solver\n");
        displayInfoString("This panel will be used to display information while a solution set is generated\n");
        displayInfoString("There is a text box below which can be used for inputting commands\n\n");
        displayInfoString("Use the slider below to enter the GPA importance for the solving");
        displayStudentString("Welcome to Solver\n");
        displayStudentString("This panel will be used to display the students names and also the projects allocated to them\n");
    }
}

