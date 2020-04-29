package GUI;

import java.awt.BorderLayout;
import javax.swing.*;

public class UI {

    private static final int FRAME_WIDTH = 885;
    private static final int FRAME_HEIGHT = 600;

    private final InfoPanel infoPanel;
    private final InfoPanel studentPanel;
    private final CommandPanel commandPanel;

    public static void main(String[] args) {
        UI ui = new UI();
    }
    UI() {
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
    }

    public String getCommand() {
        return commandPanel.getCommand();
    }

    public void displayString(String string) {
        infoPanel.addText(string);
    }
}

