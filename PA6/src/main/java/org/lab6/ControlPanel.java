package org.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private final MainFrame frame;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new FlowLayout());

        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        JButton exitBtn = new JButton("Exit");
        JButton exportBtn = new JButton("Export to PNG");

        saveBtn.addActionListener(this::saveGame);
        loadBtn.addActionListener(this::loadGame);
        exitBtn.addActionListener(e -> System.exit(0));
        exportBtn.addActionListener(this::exportToPNG);

        add(saveBtn);
        add(loadBtn);
        add(exportBtn);
        add(exitBtn);
    }

    private void saveGame(ActionEvent e) {
        frame.drawingPanel.saveGame();
    }

    private void loadGame(ActionEvent e) {
        frame.drawingPanel.loadGame();
    }

    private void exportToPNG(ActionEvent e) {
        frame.drawingPanel.exportToPNG();
    }
}
