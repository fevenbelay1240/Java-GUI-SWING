import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.*;

/**
 * Class to recolor label using JSpinner Component
 * 
 * @author Feven Mengistu
 * @version Spring 2023
 */
public class RecolorSpinner implements Runnable, ChangeListener {

    private static JLabel label;

    private static JSpinner spin;
    private static JSpinner spinRed;
    private static JSpinner spinGreen;
    private static JSpinner spinBlue;

    public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Resize Box");
        frame.setPreferredSize(new Dimension(800, 800));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel.add(panel2, BorderLayout.SOUTH);

        label = new JLabel("Recolor Spinner");
        panel.add(label, BorderLayout.NORTH);

        spinRed = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        spinGreen = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
        spinBlue = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));

        panel2.add(spinRed);
        panel2.add(spinGreen);
        panel2.add(spinBlue);

        spinRed.addChangeListener(this);
        spinGreen.addChangeListener(this);
        spinBlue.addChangeListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public void stateChanged(ChangeEvent e) {
        int redSpin = (int) spinRed.getValue();
        int greenSpin = (int) spinGreen.getValue();
        int blueSpin = (int) spinBlue.getValue();

        label.setForeground(new Color(redSpin, blueSpin, greenSpin));
        label.setText("Red: " + redSpin + " Green: " + greenSpin + " Blue: " + blueSpin);

    }

    public static void main(String args[]) {

        javax.swing.SwingUtilities.invokeLater(new RecolorSpinner());
    }
}
