package net.bobmandude9889.UI.server;

import java.util.Scanner;

import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerCommandPacket;

public class UserInputThread implements Runnable {

	public Scanner in;

	@Override
	public void run() {
		in = new Scanner(System.in);
		while (true) {
			if (in.hasNextLine()) {
				String command = in.nextLine();
				ServerCommandPacket packet = (ServerCommandPacket) IPacket.SERVER.SERVER_COMMAND.getListener();
				packet.sendPacket(command);
			}
		}
	}

}
