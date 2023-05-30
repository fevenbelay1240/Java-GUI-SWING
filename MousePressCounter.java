import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Lab 5 demo of mouse events.
 * 
 * @author Jim Teresco , Feven Mengistu
 * @version Spring 2022/2023
 */
public class MousePressCounter extends MouseAdapter implements Runnable, ActionListener {
	String toDisplay;
	JPanel panel;
	JButton resetButton;
	JPanel buttonPanel;
	private int count = 0;

	@Override
	public void run() {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("MouseDemo");
		frame.setPreferredSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// construct an anonymous class that extends JPanel,
		// for which we override the paintComponent method
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				super.paintComponent(g);

				FontMetrics fm = g.getFontMetrics();

				toDisplay = "Mouse press count: " + count;
				int stringWidth = fm.stringWidth(toDisplay);
				int stringAscent = fm.getAscent();

				int xStart = getWidth() / 2 - stringWidth / 2;
				int yStart = getHeight() / 2 + stringAscent / 2;

				g.drawString(toDisplay, xStart, yStart);

			}
		};
		resetButton = new JButton("Reset Count");
		resetButton.addActionListener(this);

		buttonPanel = new JPanel();
		buttonPanel.add(resetButton);
		frame.add(panel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		panel.addMouseWheelListener(this);

		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		count++;
		toDisplay = "Mouse press count " + count;
		panel.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == resetButton) {
			count = 0;
			toDisplay = "Mouse press counts " + count;
			panel.repaint();
		}
	}

	public static void main(String args[]) {
		javax.swing.SwingUtilities.invokeLater(new MousePressCounter());

	}
}
