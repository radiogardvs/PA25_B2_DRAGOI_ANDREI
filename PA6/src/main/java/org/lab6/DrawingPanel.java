package org.lab6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    private final int DOT_RADIUS = 5;
    private List<Point> dots = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private Point selectedDot = null;

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                selectDot(evt.getPoint());
            }
        });
    }

    public void createRandomDots(int n) {
        dots.clear();
        Random rand = new Random();
        int width = getWidth(), height = getHeight();
        for (int i = 0; i < n; i++) {
            int x = rand.nextInt(width - 40) + 20;
            int y = rand.nextInt(height - 40) + 20;
            dots.add(new Point(x, y));
        }
    }

    public void selectDot(Point point) {
        for (Point p : dots) {
            if (p.distance(point) <= DOT_RADIUS) {
                if (selectedDot == null) {
                    selectedDot = p;
                } else {
                    addLine(selectedDot, p);
                    selectedDot = null;
                }
                repaint();
                break;
            }
        }
    }

    private void addLine(Point p1, Point p2) {
        lines.add(new Line(p1, p2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        for (Line line : lines) {
            g.drawLine(line.p1.x, line.p1.y, line.p2.x, line.p2.y);
        }

        g.setColor(Color.BLACK);
        for (Point p : dots) {
            g.fillOval(p.x - DOT_RADIUS, p.y - DOT_RADIUS, 2 * DOT_RADIUS, 2 * DOT_RADIUS);
        }
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.ser"))) {
            out.writeObject(dots);
            out.writeObject(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game.ser"))) {
            dots = (List<Point>) in.readObject();
            lines = (List<Line>) in.readObject();
            repaint();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportToPNG() {
        try {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            paint(g2d);
            ImageIO.write(image, "PNG", new File("game.png"));
            g2d.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Line implements Serializable {
        private static final long serialVersionUID = 1L;
        Point p1, p2;

        Line(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }
}
