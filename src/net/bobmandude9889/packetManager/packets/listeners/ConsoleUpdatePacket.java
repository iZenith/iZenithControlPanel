package net.bobmandude9889.packetManager.packets.listeners;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class ConsoleUpdatePacket implements PacketListener {

	@SuppressWarnings("deprecation")
	@Override
	public void packetReceived(Packet packet, Connection conn) {
		String message = (String) packet.getValue("value");
		Date time = Calendar.getInstance().getTime();
		String formatted = "[" + time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds() + " INFO]: " + message;
		System.out.println(formatted + "\r");
	}

	public void sendPacket(String message) throws IOException {
		if (ConnectionManager.isConnected()) {
			Packet packet = new Packet(IPacket.CLIENT.CONSOLE_UPDATE.name());
			packet.put("value", message);
			ConnectionManager.verificationFilter.broadcastPacket(packet);
		}
	}

}
