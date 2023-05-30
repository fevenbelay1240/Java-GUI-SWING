import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.*;

/**
 * Class to recolor label using JSlider Component
 * 
 * @author Feven Mengistu
 * @version Spring 2023
 */
public class RecolorLabel implements Runnable, ChangeListener {

    private static JLabel label;
    private static JSlider red;
    private static JSlider green;  
    private static JSlider blue;
    
        public  void run()
     {
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

                    label = new JLabel("Recolor text");
                    panel.add(label, BorderLayout.NORTH);
                    red = new JSlider(0, 255);
                    green = new JSlider(0, 255);
                    blue = new JSlider(0, 255);
    
                   panel2.add(red);
                   panel2.add(green);
                   panel2.add(blue);
    
                   red.addChangeListener(this);
                   green.addChangeListener(this);
                   blue.addChangeListener(this);
    
                    frame.pack();
                    frame.setVisible(true);
                }
    
        
        public void stateChanged(ChangeEvent e) { 
            int redValue = red.getValue();
            int blueValue = blue.getValue();
            int greenValue = green.getValue();
    
    
           label.setForeground(new Color(redValue, blueValue, greenValue));
           label.setText("Red: " + redValue + " Green: " + greenValue + " Blue: " + blueValue);
        }
        public static void main(String args[]) {
    
            javax.swing.SwingUtilities.invokeLater(new RecolorLabel());
            }
    }
