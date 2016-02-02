package net.bobmandude9889.UI.display;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import net.bobmandude9889.bukkit.Util.Encrypt;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.RegisterUserPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifyCodePacket;

public class RegisterUserWindow extends JFrame implements ActionListener {

	public static RegisterUserWindow instance;
	private JTextField name;
	private JPasswordField pass;
	private JPasswordField passConf;

	private JTextField code;

	int mode = 0;

	private String register = "reg";
	private String verify = "ver";

	public RegisterUserWindow() {
		instance = this;
		registerMode();
	}

	public void registerMode() {
		mode = 0;
		getContentPane().setBackground(Color.WHITE);
		setTitle("Register new user");
		setResizable(false);
		getContentPane().removeAll();
		getContentPane().setLayout(null);
		setBounds(100, 100, 226, 160);

		JLabel lblUsername = DefaultComponentFactory.getInstance().createLabel("Username:");
		lblUsername.setBounds(10, 11, 76, 14);
		getContentPane().add(lblUsername);

		name = new JTextField();
		name.setBounds(124, 8, 86, 20);
		getContentPane().add(name);
		name.setColumns(10);

		JLabel lblPassword = DefaultComponentFactory.getInstance().createLabel("Password:");
		lblPassword.setBounds(10, 42, 76, 14);
		getContentPane().add(lblPassword);

		JLabel lblConfirmPassword = DefaultComponentFactory.getInstance().createLabel("Confirm Password:");
		lblConfirmPassword.setBounds(10, 73, 92, 14);
		getContentPane().add(lblConfirmPassword);

		pass = new JPasswordField();
		pass.setBounds(124, 39, 86, 20);
		getContentPane().add(pass);

		passConf = new JPasswordField();
		passConf.setBounds(124, 70, 86, 20);
		getContentPane().add(passConf);

		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(10, 98, 200, 23);
		btnRegister.setActionCommand(register);
		btnRegister.addActionListener(this);
		getContentPane().add(btnRegister);
	}

	public void verifyMode() {
		mode = 1;
		getContentPane().setBackground(Color.WHITE);
		setTitle("Verify user");
		setResizable(false);
		getContentPane().removeAll();
		getContentPane().setLayout(null);
		setBounds(100, 100, 226, 125);

		code = new JTextField();
		code.setBounds(10, 36, 200, 20);
		getContentPane().add(code);
		code.setColumns(10);

		JLabel lblCode = DefaultComponentFactory.getInstance().createLabel("Verification Code:");
		lblCode.setBounds(10, 11, 92, 14);
		getContentPane().add(lblCode);

		JButton btnVerify = new JButton("Verify");
		btnVerify.setBounds(10, 67, 200, 23);
		btnVerify.setActionCommand(verify);
		btnVerify.addActionListener(this);
		getContentPane().add(btnVerify);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals(register)) {
			if (name.getText().length() > 0 && pass.getText().length() > 0 && passConf.getText().length() > 0) {
				if (pass.getText().equals(passConf.getText())) {
					RegisterUserPacket regPack = (RegisterUserPacket) IPacket.SERVER.REGISTER_USER.getListener();
					regPack.sendPacket(name.getText(), pass.getText());
				} else {
					JOptionPane.showMessageDialog(null,"Passwords do not match!","Error",0);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Missing info!", "Error", 0);
			}
		} else if (cmd.equals(verify)) {
			if(code.getText().length() > 0){
				VerifyCodePacket verPack = (VerifyCodePacket) IPacket.SERVER.VERIFY_CODE.getListener();
				verPack.sendPacket(Encrypt.md5(code.getText()), name.getText());
			} else {
				JOptionPane.showMessageDialog(null, "Please enter the code!", "Error", 0);
			}
		}
	}

}
