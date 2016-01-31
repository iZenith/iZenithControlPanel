package net.bobmandude9889.UI.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.bobmandude9889.UI.player.Player;

@SuppressWarnings("serial")
public class PlayerWindow extends JFrame {

	private JPanel contentPane;
	
	public PlayerWindow(final Player player) {
		setTitle(player.getName());
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				g.setColor(Color.BLACK);
				
				g.fillRect(0, 0, 120, 220);
				g.drawImage(player.getSkin(), 10, 10, 100, 200, null);
			}
		};
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.LIGHT_GRAY);
		setContentPane(contentPane);
		setResizable(false);
		setVisible(true);
	}
	
}
