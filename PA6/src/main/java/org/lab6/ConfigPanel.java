package org.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConfigPanel extends JPanel {
    private final MainFrame frame;
    JLabel dotsLabel;
    JSpinner dotsSpinner;
    JButton newGameButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new FlowLayout());

        dotsLabel = new JLabel("Number of dots:");
        dotsSpinner = new JSpinner(new SpinnerNumberModel(6, 3, 100, 1));
        newGameButton = new JButton("New Game");

        newGameButton.addActionListener(this::createNewGame);

        add(dotsLabel);
        add(dotsSpinner);
        add(newGameButton);
    }

    private void createNewGame(ActionEvent e) {
        int n = (int) dotsSpinner.getValue();
        frame.drawingPanel.createRandomDots(n);
        frame.drawingPanel.repaint();
    }
}
