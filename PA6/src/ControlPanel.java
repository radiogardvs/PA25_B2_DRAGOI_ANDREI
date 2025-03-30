import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private final MainFrame frame;
    private JButton exitButton;

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new FlowLayout());

        exitButton = new JButton("Exit");
        add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
