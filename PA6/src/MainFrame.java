
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ConfigPanel configPanel;
    private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;

    public MainFrame() {
        super("Graphical Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel(this);
        drawingPanel = new DrawingPanel();
        controlPanel = new ControlPanel(this);

        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }
}
