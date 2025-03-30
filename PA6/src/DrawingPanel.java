import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private final int DOT_SIZE = 10;
    private List<Point> dots = new ArrayList<>();
    private Random rand = new Random();

    public DrawingPanel() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
    }

    public void generateDots(int numberOfDots) {
        dots.clear();
        for (int i = 0; i < numberOfDots; i++) {
            int x = rand.nextInt(getWidth() - DOT_SIZE);
            int y = rand.nextInt(getHeight() - DOT_SIZE);
            dots.add(new Point(x, y));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Point dot : dots) {
            g.fillOval(dot.x, dot.y, DOT_SIZE, DOT_SIZE);
        }
    }
}
