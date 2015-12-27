package net.bobmandude9889.UI.server;

import net.bobmandude9889.UI.display.Frame;

public class ServerInfo {

	private static int onlinePlayers = 0;

	public static int getOnlinePlayers() {
		return onlinePlayers;
	}

	public static void setOnlinePlayers(int onlinePlayers) {
		ServerInfo.onlinePlayers = onlinePlayers;
		Frame.lblPlayersOnline.setText("Players Online: " + onlinePlayers);
	}
	
}
