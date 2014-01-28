package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class NumericalInfoPanel extends JPanel {
	private JLabel valueLabel;

	public NumericalInfoPanel(String label) {
		setForeground(Color.WHITE);
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		
		JLabel panelLabel = new JLabel(label);
		panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelLabel.setFont(new Font("Lucida Console", Font.BOLD, 14));
		panelLabel.setForeground(Color.WHITE);
		add(panelLabel, BorderLayout.NORTH);
		
		valueLabel = new JLabel("00000000");
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		valueLabel.setFont(new Font("Lucida Console", Font.BOLD, 18));
		valueLabel.setForeground(Color.WHITE);
		add(valueLabel, BorderLayout.CENTER);
	}
	
	protected void updateValue(String val) {
		valueLabel.setText(val);
	}
}
