package net.bobmandude9889.UI.display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import net.bobmandude9889.bukkit.Util.Encrypt;
import net.bobmandude9889.packetManager.connections.ConnectionHandlerType;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifyPassPacket;

@SuppressWarnings("serial")
public class InfoInputWindow extends JFrame implements ActionListener {

	private JPanel contentPane;
	public static JTextField txtIP;
	public static JTextField txtPort;
	public static JTextField txtUser;
	public static JTextField txtPassword;
	public static JLabel status;
	private String connect = "connect";

	public InfoInputWindow() {
		setTitle("Info");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 290, 190);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIpAddress = DefaultComponentFactory.getInstance().createLabel("IP Address:");
		lblIpAddress.setBounds(10, 11, 92, 14);
		contentPane.add(lblIpAddress);

		txtIP = new JTextField();
		txtIP.setBounds(10, 30, 150, 20);
		txtIP.setText("127.0.0.1");
		contentPane.add(txtIP);
		txtIP.setColumns(10);

		JLabel lblPort = DefaultComponentFactory.getInstance().createLabel("Port:");
		lblPort.setBounds(188, 11, 92, 14);
		contentPane.add(lblPort);

		txtPort = new JTextField();
		txtPort.setBounds(188, 30, 86, 20);
		txtPort.setText("25566");
		contentPane.add(txtPort);
		txtPort.setColumns(10);

		JLabel lblUsername = DefaultComponentFactory.getInstance().createLabel("Username:");
		lblUsername.setBounds(10, 61, 92, 14);
		contentPane.add(lblUsername);

		txtUser = new JTextField();
		txtUser.setBounds(10, 80, 125, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);

		JLabel lblPassword = DefaultComponentFactory.getInstance().createLabel("Password:");
		lblPassword.setBounds(149, 61, 92, 14);
		lblPassword.setLabelFor(txtPassword);
		contentPane.add(lblPassword);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 111, 264, 23);
		btnConnect.setActionCommand(connect);
		btnConnect.addActionListener(this);
		contentPane.add(btnConnect);

		txtPassword = new JPasswordField();
		txtPassword.setActionCommand(connect);
		txtPassword.setBounds(149, 80, 125, 20);
		txtPassword.addActionListener(this);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);

		status = new JLabel("Ready to connect");
		status.setBounds(10,140,264,14);
		status.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(status);
		
		setVisible(true);
	}

	public static void setStatus(String message){
		status.setText(message);
	}
	
	public void openControlWindow(){
		Frame frame = new Frame();
		frame.setLocation(this.getLocation());
		this.dispose();
		frame.setVisible(true);
		Frame.instance = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(connect)) {
			setStatus("Attempting to connect.");
			if (ConnectionManager.init(ConnectionHandlerType.CLIENT)) {
				VerifyPassPacket packet = (VerifyPassPacket) IPacket.SERVER.VERIFY_PASS.getListener();
				String user = txtUser.getText();
				String pass = txtPassword.getText();
				System.out.println(pass);
				System.out.println(pass);
				packet.sendPacket(user, pass);
			} else {
				setStatus("Could not connect.");
			}
		}
	}

}
