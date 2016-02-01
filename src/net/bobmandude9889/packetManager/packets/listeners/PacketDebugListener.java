package net.bobmandude9889.packetManager.packets.listeners;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Util.Config;

public class PacketDebugListener implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		if (Config.isDebug()) {
			System.out.println("Recieved packet: " + packet.serialize().toString() + " from: " + conn.getTCPSocket().getInetAddress());
		}
	}

}
