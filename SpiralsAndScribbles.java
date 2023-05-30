// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.GroupLayout.Group;

/**
 * This program draws a "Spiral of Lines". When the mouse is pressed
 * then dragged, a series of lines are drawn from the press point to
 * the current location.
 *
 * This will be enhanced as part of a lab exercise with various other
 * functionality.
 *
 * @author Jim Teresco (initial Spiral drawing)
 * @author Ira Goldstein (revised swing components)
 * @author Shaswar Mohammed, Feven Mengistu
 * @version Spring 2023
 */

public class SpiralsAndScribbles
  extends MouseAdapter
  implements Runnable, ActionListener {

  // a list of pairs of points to keep track of the start and end coordinates
  // where we need to draw lines
  private ArrayList<Point[]> lines = new ArrayList<>();

  // press point for the current spiral
  private Point pressPoint;

  private JPanel panel;

  // should we clear on exit?
  private JCheckBox clearOnExit;

  // what should we draw?
  private JComboBox<String> drawWhat;

  ArrayList<Groups> l = new ArrayList<Groups>();

  // color mode
  // private JComboBox colorMode;

  private ArrayList<LineSegment> lineSegments = new ArrayList<>();

  // the drop down box that lets you select a color mode
  JComboBox<String> colorModeBox;

  // the current color mode
  String colorMode;

  // the current color line segments will be drawn in
  Color currentColor;

  // this method is called by the paintComponent method of
  // the anonymous extension of JPanel, to keep that method
  // from getting too long
  protected void redraw(Graphics g) {
    // draw all of the lines in the list
    for (Point[] p : lines) {
      g.drawLine(p[0].x, p[0].y, p[1].x, p[1].y);
    }
    for (LineSegment line : lineSegments) {
      line.paint(g);
    }
  }

  /**
   * The run method to set up the graphical user interface
   */
  @Override
  public void run() {
    // set up the GUI "look and feel" which should match
    // the OS on which we are running
    JFrame.setDefaultLookAndFeelDecorated(false);

    // create a JFrame in which we will build our very
    // tiny GUI, and give the window a name
    JFrame frame = new JFrame("SpiralsAndScribbles");
    frame.setMinimumSize(new Dimension(600, 600));

    // tell the JFrame that when someone closes the
    // window, the application should terminate
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel outerPanel = new JPanel(new BorderLayout());
    frame.add(outerPanel);

    // JPanel with a paintComponent method
    panel =
      new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
          // first, we should call the paintComponent method
          // we are overriding in JPanel
          super.paintComponent(g);

          // redraw our graphics items
          redraw(g);
        }
      };

    outerPanel.add(panel, BorderLayout.CENTER);
    panel.addMouseListener(this);
    panel.addMouseMotionListener(
      new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
          SpiralsAndScribbles.this.mouseDragged(e);
        }
      }
    );
    panel.addMouseListener(
      new MouseAdapter() {
        @Override
        public void mouseExited(MouseEvent e) {
          SpiralsAndScribbles.this.mouseExited(e);
        }
      }
    );

    JPanel controlPanel = new JPanel(new GridLayout(1, 3));
    controlPanel = new JPanel(new GridLayout(1, 3));
    clearOnExit = new JCheckBox("Clear on exit?", false);
    controlPanel.add(clearOnExit);

    //What types of lines should be drawn
    String[] drawWhatOptions = { "Spirals", "Scribbles" };
    drawWhat = new JComboBox<String>(drawWhatOptions);
    drawWhat.setSelectedIndex(0);
    drawWhat.addActionListener(this);
    controlPanel.add(drawWhat);

    //The types of colors that can be selected from the drop down
    String[] colorModeOptions = {
      "Default",
      "Colorful",
      "More Colorful",
      "Crazy Colorful",
    };
    colorModeBox = new JComboBox<String>(colorModeOptions);
    colorModeBox.setSelectedIndex(0);
    colorModeBox.addActionListener(this);
    controlPanel.add(colorModeBox);

    outerPanel.add(controlPanel, BorderLayout.SOUTH);

    // display the window we've created
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (colorMode == "Colorful") {
      currentColor = this.generateRandomColor();
      if (drawWhat.getSelectedItem() == "Scribbles") {
        // checks if click is 25 or closer to start point by
        // checking the array list of lines (created by the
        // individual points in the lines class) then creates
        // a line segment out of tha line and runs a loop
        // through the points in that segment and basically will
        // recolor them
        for (int i = 0; i < l.size(); i++) {
          //Checks distance of 25
          if ((int) e.getPoint().distance(l.get(i).pts.get(0)) < 25) {
            for (int j = 0; j < l.get(i).pts.size() - 1; j++) {
              lineSegments.add(
                new LineSegment(
                  l.get(i).getN(j),
                  l.get(i).getN(j + 1),
                  currentColor
                )
              );
              panel.repaint();
            }
          }
        }
      }
    } else if (colorMode == "More Colorful") {
      if (drawWhat.getSelectedItem() == "Scribbles") {
        // same idea as colorful mode but a new color is generated everytime a
        // new line segment is created, so every line should be a different color.
        for (int i = 0; i < l.size(); i++) {
          if ((int) e.getPoint().distance(l.get(i).pts.get(0)) < 25) {
            for (int j = 0; j < l.get(i).pts.size() - 1; j++) {
              lineSegments.add(
                new LineSegment(
                  l.get(i).getN(j),
                  l.get(i).getN(j + 1),
                  this.generateRandomColor()
                )
              );
              panel.repaint();
            }
          }
        }
      }
    }
    pressPoint = e.getPoint();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    if (drawWhat.getSelectedItem() == "Scribbles") {
      //Create a 2 point array
      Point p[] = new Point[2];
      //The point at which the mouse was pressed
      p[0] = e.getPoint();
      //The point end point of the line tht was created upon mouse press
      p[1] = e.getPoint();
      //we add this to the lines <POINT> arrayList
      lines.add(p);
      //We repaint
      panel.repaint();
    } else if (drawWhat.getSelectedItem() == "Spirals") {
      Point p[] = new Point[2];
      p[0] = pressPoint;
      p[1] = e.getPoint();
      lines.add(p);
      panel.repaint();
    }

    // get the correct color to draw the line in
    Color lineColor = Color.BLACK;
    if (colorMode == "Colorful") {
      lineColor = currentColor;
    } else if (colorMode == "More Colorful" || colorMode == "Crazy Colorful") {
      lineColor = this.generateRandomColor();
    }

    LineSegment newLine = new LineSegment(pressPoint, e.getPoint(), lineColor);

    lineSegments.add(newLine);
    panel.repaint();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (clearOnExit.isSelected()) {
      lines.clear();
      lineSegments.clear();
      panel.repaint();
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    if (colorMode == "Crazy Colorful") {
      for (LineSegment segment : lineSegments) {
        segment.setColor(generateRandomColor());
      }
      panel.repaint();
    }
  }

  /**
   * This method generate a random color using the Random object.
   */
  private Color generateRandomColor() {
    Random r = new Random();
    int red = r.nextInt(256);
    int green = r.nextInt(256);
    int blue = r.nextInt(256);
    return new Color(red, green, blue);
  }

  /**
   * Action listener for JComboBox selections
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == drawWhat) {
      lines.clear();
      lineSegments.clear();
      if (drawWhat.getSelectedItem() == "Scribbles") {
        l.clear();
      }
      panel.repaint();
    } else if (e.getSource() == colorModeBox) {
      colorMode = (String) colorModeBox.getSelectedItem();
      panel.repaint();
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new SpiralsAndScribbles());
  }
}

/**
 * LineSegment objects make up the spirals and scribbles the user draws
 */
class LineSegment {

  // the start point of the line segment
  protected Point start;

  // the end point of the line segment
  protected Point end;

  // the color of the line segment
  protected Color color;

  public LineSegment(Point start, Point end, Color color) {
    this.start = start;
    this.end = end;
    this.color = color;
  }

  public void paint(Graphics g) {
    g.setColor(this.color);
    g.drawLine(
      (int) this.start.getX(),
      (int) this.start.getY(),
      (int) this.end.getX(),
      (int) this.end.getY()
    );
  }

  protected void setColor(Color newColor) {
    this.color = newColor;
  }
}
