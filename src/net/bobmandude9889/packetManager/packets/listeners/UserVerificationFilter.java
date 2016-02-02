package net.bobmandude9889.packetManager.packets.listeners;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.ConnectionHandler;
import net.bobmandude9889.api.NetworkAPI;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Users.UserManager;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class UserVerificationFilter implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		ConnectionManager.debugListener.packetReceived(packet, conn);
		IPacket.SERVER iPacket = IPacket.SERVER.valueOf(packet.getName());
		if (iPacket != null) {
			if (UserManager.isVerified(conn.getTCPSocket().getInetAddress()) || !iPacket.requiresVerification()) {
				PacketListener listener = iPacket.getListener();
				listener.packetReceived(packet, conn);
			}
		}
	}

	public void sendPacket(Packet packet, Connection conn) {
		IPacket.CLIENT iPacket = IPacket.CLIENT.valueOf(packet.getName());
		if (iPacket != null) {
			if (UserManager.isVerified(conn.getTCPSocket().getInetAddress()) || !iPacket.requiresVerification()) {
				conn.sendPacket(packet);
			}
		}
	}

	public void broadcastPacket(Packet packet) {
		IPacket.CLIENT iPacket = IPacket.CLIENT.valueOf(packet.getName());
		if (iPacket != null) {
			int i = 0;
			ConnectionHandler handler = NetworkAPI.getConnectionHandler();
			while (i >= 0) {
				try {
					Connection conn = handler.getConnection(i);
					if (UserManager.isVerified(conn.getTCPSocket().getInetAddress()) || !iPacket.requiresVerification()) {
						conn.sendPacket(packet);
					}
					i++;
				} catch (Exception e) {
					i = -1;
					break;
				}
			}
		}
	}

}
