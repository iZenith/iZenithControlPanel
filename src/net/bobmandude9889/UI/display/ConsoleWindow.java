package net.bobmandude9889.UI.display;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;

public class ConsoleWindow extends JFrame{
	
	private JTextField inputField;
	
	public static void main(String[] args) {
		new ConsoleWindow();
	}
	
	public ConsoleWindow() {
		setSize(800,600);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		inputField = new JTextField();
		inputField.setToolTipText("Enter a command here");
		getContentPane().add(inputField, BorderLayout.SOUTH);
		inputField.setColumns(10);
		
		JTextPane output = new JTextPane();
		output.setEditable(false);
		output.setForeground(Color.WHITE);
		output.setBackground(Color.BLACK);
		getContentPane().add(output, BorderLayout.CENTER);
		setVisible(true);
	}

}