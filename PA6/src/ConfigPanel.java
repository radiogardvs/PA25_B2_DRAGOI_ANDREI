import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPanel extends JPanel {
    private final MainFrame frame;
    private JLabel label;
    private JSpinner dotsSpinner;
    private JButton createButton;

    public ConfigPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new FlowLayout());

        label = new JLabel("Number of dots:");
        dotsSpinner = new JSpinner(new SpinnerNumberModel(5, 2, 20, 1));
        createButton = new JButton("Create");

        add(label);
        add(dotsSpinner);
        add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfDots = (int) dotsSpinner.getValue();
                frame.getDrawingPanel().generateDots(numberOfDots);
            }
        });
    }
}
