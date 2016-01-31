package net.bobmandude9889.packetManager.packets.listeners;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.packets.IPacket;

public class VerificationRequestPacket implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		// TODO
	}

	public void sendPacket(Connection conn){
		Packet packet = new Packet(IPacket.CLIENT.VERIFICATION_REQUEST.name());
		conn.sendPacket(packet);
	}
	
}
