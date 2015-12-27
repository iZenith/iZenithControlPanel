package net.bobmandude9889.packetManager.connections;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import net.bobmandude9889.UI.display.InfoInputWindow;
import net.bobmandude9889.api.ConnectionHandler;
import net.bobmandude9889.api.ConnectionServer;
import net.bobmandude9889.api.NetworkAPI;
import net.bobmandude9889.bukkit.Util.Config;

public enum ConnectionHandlerType {

	SERVER(new Runnable() {

		@Override
		public void run() {
			try {
				ConnectionServer server = new ConnectionServer(Config.getPort(), ConnectionManager.packetHandler);
				ConnectionManager.server = server;
				ConnectionManager.setConnected(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}),

	CLIENT(new Runnable() {

		@Override
		public void run() {
			ConnectionHandler handler = NetworkAPI.getConnectionHandler();
			try {
				int connection = handler.openConnection(InfoInputWindow.txtIP.getText(),
						Integer.parseInt(InfoInputWindow.txtPort.getText()), ConnectionManager.packetHandler);
				ConnectionManager.setConnected(true);
				ConnectionManager.serverConn = handler.getConnection(connection);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Please enter an integer port.", "Invalid port", 0);
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "There was a problem finding the server. \nMake sure the config is setup correctly", "Unknown host", 0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "There was a problem while opening a connection to the server.", "Exception", 0);
			}
		}

	});

	private Runnable init;

	private ConnectionHandlerType(Runnable init) {
		this.init = init;
	}

	public void init() {
		init.run();
	}

}
