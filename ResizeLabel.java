import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Class to resize label using JComboBox Component
 * 
 * @author Feven Mengistu
 * @version Spring 2023
 */
public class ResizeLabel implements Runnable, ActionListener {

    private static JComboBox resizeBox;
    private static JLabel label;

    public void run() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        JFrame frame = new JFrame("Resize Box");
        frame.setPreferredSize(new Dimension(300, 300));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);
        // create a JLabel showing the text to be resized
        label = new JLabel("Resize text");
        panel.add(label, BorderLayout.NORTH);

        resizeBox = new JComboBox();
        resizeBox.addItem("Small");
        resizeBox.addItem("Medium");
        resizeBox.addItem("Large");
        resizeBox.addActionListener(this);
        panel.add(resizeBox, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Font currentFont = label.getFont();

        if (resizeBox.getSelectedItem().equals("Small")) {
            Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 15);
            label.setFont(newFont);
        } else if (resizeBox.getSelectedItem().equals("Medium")) {
            Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 30);
            label.setFont(newFont);
        } else if (resizeBox.getSelectedItem().equals("Large")) {
            Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 60);
            label.setFont(newFont);
        }
    }

    public static void main(String args[]) {

        // The main method 
        javax.swing.SwingUtilities.invokeLater(new ResizeLabel());
    }
}
