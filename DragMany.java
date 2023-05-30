import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


/** This program has an abstract foundation for the DraggableShape class.
 * @author Feven Mengistu, Shaswar Mohammed
 * @version 03/23/2023
 */
public class DragMany extends MouseAdapter implements Runnable {

    public static final int MIN_SIZE = 25;
    public static final int MAX_SIZE = 100;
    public static final int PANEL_SIZE = 600;

    private java.util.List<DraggableShape> shapes;
    private Point lastMouse;
    private DraggableShape dragging;
    private int count;
    private JPanel panel;

    public DragMany(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("DragMany");
        frame.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (DraggableShape s : shapes) {
                    s.paint(g);
                }
            }
        };
        frame.add(panel);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        shapes = new ArrayList<DraggableShape>(count);
        Random r = new Random();

        for (int i = 0; i < count; i++) {
            int size = MIN_SIZE + r.nextInt(MAX_SIZE - MIN_SIZE);
            Point point = new Point(r.nextInt(PANEL_SIZE - size), r.nextInt(PANEL_SIZE - size));
            Color color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));

            int shapeType = r.nextInt(4);
            switch (shapeType) {
                case 0:
                    shapes.add(new FilledCircle(size, point, color));
                    break;
                case 1:
                    shapes.add(new FramedCircle(size, point, color));
                    break;
                case 2:
                    shapes.add(new FilledSquare(size, point, color));
                    break;
                case 3:
                    shapes.add(new FramedSquare(size, point, color));
                    break;
            }
        }

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastMouse = null;
        Point p = e.getPoint();
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).contains(p)) {
                dragging = shapes.get(i);
                lastMouse = p;
                shapes.remove(i);
                shapes.add(dragging);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (lastMouse != null) {
            int dx = e.getPoint().x - lastMouse.x;
            int dy = e.getPoint().y - lastMouse.y;
            dragging.translate(dx, dy);
            panel.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastMouse != null) {
            int dx = e.getPoint().x - lastMouse.x;
            int dy = e.getPoint().y - lastMouse.y;
            dragging.translate(dx, dy);
            lastMouse = e.getPoint();
            panel.repaint();
        }
    }
	public static void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Usage: java DragMany count");
            System.exit(1);
        }

        int count = 0;
        try {
            count = Integer.parseInt(args[0]);
        }
		catch (NumberFormatException e) {
            System.err.println("Could not parse " + args[0] + " as integer.");
            System.exit(1);
        }

        javax.swing.SwingUtilities.invokeLater(new DragMany2(count));
    }
}

abstract class DraggableShape {
    protected int size;
    protected Point upperLeft;
    protected Color color;

    public DraggableShape(int size, Point upperLeft, Color color) {
        this.size = size;
        this.upperLeft = new Point(upperLeft);
        this.color = color;
    }

    public abstract void paint(Graphics g);
    public abstract boolean contains(Point p);

    public void translate(int dx, int dy) {
        upperLeft.translate(dx, dy);
    }
}

class FilledCircle extends DraggableShape {

    public FilledCircle(int size, Point upperLeft, Color color) {
        super(size, upperLeft, color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(upperLeft.x, upperLeft.y, size, size);
    }

    @Override
    public boolean contains(Point p) {
        Point circleCenter = new Point(upperLeft.x + size / 2, upperLeft.y + size / 2);
        return circleCenter.distance(p) <= size / 2;
    }
}

class FramedCircle extends DraggableShape {

    public FramedCircle(int size, Point upperLeft, Color color) {
        super(size, upperLeft, color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.drawOval(upperLeft.x, upperLeft.y, size, size);
    }

    @Override
    public boolean contains(Point p) {
        Point circleCenter = new Point(upperLeft.x + size / 2, upperLeft.y + size / 2);
        return circleCenter.distance(p) <= size / 2;
    }
}

class FilledSquare extends DraggableShape {

    public FilledSquare(int size, Point upperLeft, Color color) {
        super(size, upperLeft, color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(upperLeft.x, upperLeft.y, size, size);
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= upperLeft.x && p.x <= upperLeft.x + size &&
                p.y >= upperLeft.y && p.y <= upperLeft.y + size;
    }
}

class FramedSquare extends DraggableShape {

    public FramedSquare(int size, Point upperLeft, Color color) {
        super(size, upperLeft, color);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.drawRect(upperLeft.x, upperLeft.y, size, size);
    }

    @Override
    public boolean contains(Point p) {
        return p.x >= upperLeft.x && p.x <= upperLeft.x + size &&
                p.y >= upperLeft.y && p.y <= upperLeft.y + size;
    }
}


