import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Class to resize label using JRadioButton Component
 * 
 * @author Feven Mengistu
 * @version Spring 2023
 */
public class ResizeRadio implements Runnable, ActionListener {
    private static JRadioButton resizeSmall;
    private static JRadioButton resizeMedium;
    private static JRadioButton resizeLarge;
    private static JLabel label;

    @Override
    public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        // create a JFrame main frame
        JFrame frame = new JFrame("Resize Box");
        frame.setPreferredSize(new Dimension(300, 300));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel.add(panel2, BorderLayout.SOUTH);
        // create a JLabel showing the text to be resized
        label = new JLabel("Resize text");
        panel.add(label, BorderLayout.NORTH);

        resizeSmall = new JRadioButton("Small");
        resizeSmall.setSelected(true);
        resizeMedium = new JRadioButton("Medium");
        resizeLarge = new JRadioButton("Large");
        ButtonGroup buttons = new ButtonGroup();
        buttons.add(resizeSmall);
        buttons.add(resizeMedium);
        buttons.add(resizeLarge);
        resizeSmall.addActionListener(this);
        resizeMedium.addActionListener(this);
        resizeLarge.addActionListener(this);
        panel2.add(resizeSmall);
        panel2.add(resizeMedium);
        panel2.add(resizeLarge);

        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Font currentFont = label.getFont();
  
        if(resizeSmall.isSelected())
        {
            Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 15);	
            label.setFont(newFont);
        }
        else if(resizeMedium.isSelected())
        {
            Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 30);	
            label.setFont(newFont);
        }
        else if(resizeLarge.isSelected())
        {
            Font newFont = new Font(currentFont.getFontName(), currentFont.getStyle(), 60);	
            label.setFont(newFont);
        }
    }

    public static void main(String args[]) {

        // The main method
        javax.swing.SwingUtilities.invokeLater(new ResizeRadio());
    }
}
