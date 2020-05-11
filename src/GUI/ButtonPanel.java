package GUI;

import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

class ButtonPanel extends JPanel  {

    private static final long serialVersionUID = 1L;
    private static int fileSize = 0;

    private final LinkedList<String> commandBuffer;

    ButtonPanel() {
        commandBuffer = new LinkedList<>();
        JButton quitButton = new JButton("Quit");
        JButton sixtyButton = new JButton("       60 Person File       ");
        JButton oneTwentyButton = new JButton("      120 Person File      ");
        JButton twoFortyButton = new JButton("      240 Person File      ");
        JButton fiveHButton = new JButton("      500 Person File      ");
        JButton customButton = new JButton("       Custom File       ");

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    System.exit(0);
                }
            }});
        sixtyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    setVisible(false);
                    fileSize=60;
                }
            }});
        oneTwentyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    setVisible(false);
                    fileSize=120;
                }
            }});
        twoFortyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    setVisible(false);
                    fileSize=240;
                }
            }});
        fiveHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    setVisible(false);
                    fileSize=500;
                }
            }});
        customButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (commandBuffer) {
                    setVisible(false);
                    fileSize=1;
                }
            }});

        setLayout(new BorderLayout());
        JPanel panelOne = new JPanel();
        JPanel panelTwo = new JPanel();
        panelOne.add(quitButton);
        panelTwo.add(sixtyButton);
        panelTwo.add(oneTwentyButton);
        panelTwo.add(twoFortyButton);
        panelTwo.add(fiveHButton);
        panelTwo.add(customButton);
        add(panelOne, BorderLayout.LINE_END);
        add(panelTwo, BorderLayout.CENTER);
    }

    static int getFileSize(){
        return fileSize;
    }
}
