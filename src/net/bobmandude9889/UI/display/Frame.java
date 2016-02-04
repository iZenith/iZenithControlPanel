package net.bobmandude9889.UI.display;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import net.bobmandude9889.UI.server.ServerInfo;
import net.bobmandude9889.UI.server.UserInputThread;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerCommandPacket;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel mainPage;
	public static PlayerScrollPanel playerPanel;
	public static JScrollPane scrollPane;
	public static JLabel lblPlayersOnline;
	private JTextField textPlayerSearch;
	public JCheckBox chckbxDebug;
	public static Frame instance;
	public static InfoInputWindow info;
	public static ConsoleWindow console;
	public static UserInputThread inputThreadRun;
	public static Thread inputThread = null;

	public static void main(String[] args) {
		new RegisterUserWindow();
	}

	private void setupCustomConsole(){
		if (inputThread == null) {
			inputThreadRun = new UserInputThread();
			inputThread = new Thread(inputThreadRun);
			inputThread.start();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					info = new InfoInputWindow();
					console = new ConsoleWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@Deprecated
	private void setupSystemConsole(String[] args){
		if (args == null || (args.length > 0 && args[0].equals("console"))) {
			if (inputThread == null) {
				inputThreadRun = new UserInputThread();
				inputThread = new Thread(inputThreadRun);
				inputThread.start();
			}

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
							e.printStackTrace();
						}
						info = new InfoInputWindow();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {

			String cn = Frame.class.getName();
			String rn = cn.replace('.', '/') + ".class";
			String path = Frame.class.getClassLoader().getResource(rn).getPath().replace("file:", "");
			int ix = path.indexOf("!");
			if (ix >= 0) {
				path = path.substring(0, ix);
			}
			path = path.replace("%20", " ");
			System.out.println(path);
			File file = new File(path);

			String os = System.getProperty("os.name").toLowerCase();
			try {
				if (os.indexOf("win") >= 0) {
					File console = new File(file.getParentFile(), "Console.bat");
					PrintWriter fileOut = new PrintWriter(console);

					fileOut.println("@echo off\n" + "cd \\\n" + "cd \"" + file.getParentFile().getPath() + "\"\n" + "java -jar \"" + file.getName() + "\" console\n" + "exit");
					fileOut.close();
					Runtime rt = Runtime.getRuntime();
					rt.exec("cmd.exe /c start cmd.exe /k \"Console.bat\" & exit");
				} else if (os.indexOf("mac") >= 0) {
					File console = new File(file.getParentFile(), "Console.command");
					PrintWriter fileOut = new PrintWriter(console);

					fileOut.println("#!/bin/bash\n" + "cd \"$(dirname \"$0\")\"\n" + "java -jar \"" + file.getName().replace(" ", "\\ ") + "\" console\n" + "exit");
					fileOut.close();
					Runtime rt = Runtime.getRuntime();
					rt.exec("/usr/bin/open -a Terminal Console.command & exit");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public Frame() {
		setResizable(false);
		setTitle("Server Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 400);
		mainPage = new JPanel();
		mainPage.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPage);
		mainPage.setLayout(null);

		playerPanel = new PlayerScrollPanel(this);

		scrollPane = new JScrollPane(playerPanel);
		scrollPane.setBounds(10, 25, 200, 342);
		mainPage.add(scrollPane);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 394, 21);
		mainPage.add(menuBar);

		lblPlayersOnline = DefaultComponentFactory.getInstance().createLabel("Players Online: " + ServerInfo.getOnlinePlayers());
		lblPlayersOnline.setForeground(Color.RED);
		lblPlayersOnline.setBounds(220, 183, 92, 14);
		mainPage.add(lblPlayersOnline);

		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ServerCommandPacket packet = (ServerCommandPacket) IPacket.SERVER.SERVER_COMMAND.getListener();
				packet.sendPacket("stop");
			}
		});
		btnStop.setBounds(220, 25, 92, 23);
		mainPage.add(btnStop);

		JButton btnReload = new JButton("Reload");
		btnReload.setBounds(220, 59, 92, 23);
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ServerInfo.setOnlinePlayers(new Random().nextInt(100));
				ServerCommandPacket packet = (ServerCommandPacket) IPacket.SERVER.SERVER_COMMAND.getListener();
				packet.sendPacket("reload");
			}
		});
		mainPage.add(btnReload);

		textPlayerSearch = new JTextField();
		textPlayerSearch.setBounds(220, 152, 92, 20);
		textPlayerSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				playerPanel.setParam(textPlayerSearch.getText());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				playerPanel.setParam(textPlayerSearch.getText());
			}

			@Override
			public void keyTyped(KeyEvent e) {
				playerPanel.setParam(textPlayerSearch.getText());
			}
		});
		mainPage.add(textPlayerSearch);
		textPlayerSearch.setColumns(10);

		JLabel lblSearchPlayers = DefaultComponentFactory.getInstance().createLabel("Search players:");
		lblSearchPlayers.setBounds(220, 127, 92, 14);
		mainPage.add(lblSearchPlayers);

		JButton btnBroadcast = new JButton("Broadcast");
		btnBroadcast.setBounds(220, 93, 92, 23);
		btnBroadcast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ServerCommandPacket packet = (ServerCommandPacket) IPacket.SERVER.SERVER_COMMAND.getListener();
				String message = JOptionPane.showInputDialog("Enter a message to broadcast:");
				if (message != null) {
					packet.sendPacket("say " + message);
				}
			}

		});
		mainPage.add(btnBroadcast);
		
		chckbxDebug = new JCheckBox("Debug");
		chckbxDebug.setBounds(216, 341, 97, 23);
		mainPage.add(chckbxDebug);
	}
}
