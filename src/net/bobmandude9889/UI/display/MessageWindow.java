package net.bobmandude9889.UI.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import net.bobmandude9889.UI.player.Player;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerMsgPacket;

public class MessageWindow extends JFrame {

	private JPanel contentPane;
	public JTextField textField;
	public JCheckBox showName;
	private JButton btnSend;

	/**
	 * Create the frame.
	 */
	public MessageWindow(final Player p) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent e) {
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				dispose();
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
		setBounds(100, 100, 215, 135);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMsg = DefaultComponentFactory.getInstance().createLabel("Enter the message you want to send:");
		lblMsg.setBounds(10, 11, 261, 14);
		contentPane.add(lblMsg);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 180, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		showName = new JCheckBox("Show name");
		showName.setBounds(10, 63, 89, 23);
		contentPane.add(showName);
		
		btnSend = new JButton("Send");
		btnSend.setBounds(105, 63, 89, 23);
		contentPane.add(btnSend);
		btnSend.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = textField.getText();
				boolean show = showName.isSelected();
				PlayerMsgPacket packet = (PlayerMsgPacket) IPacket.SERVER.PLAYER_MSG.getListener();
				packet.sendPacket(message,show,p);
				setVisible(false);
				dispose();
			}
			
		});
	}
}
