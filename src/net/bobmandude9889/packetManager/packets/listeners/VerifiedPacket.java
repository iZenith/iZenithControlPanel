package net.bobmandude9889.packetManager.packets.listeners;

import net.bobmandude9889.UI.display.Frame;
import net.bobmandude9889.UI.display.InfoInputWindow;
import net.bobmandude9889.UI.display.RegisterUserWindow;
import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Users.UserManager;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class VerifiedPacket implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		boolean verified = (boolean) packet.getValue("verified");
		if (!verified) {
			InfoInputWindow.setStatus("Could not verify info.");
			ConnectionManager.closeConnections();
		} else {
			InfoInputWindow.setStatus("Verified");
			Frame.instance.info.openControlWindow();
			RegisterUserWindow.instance.setVisible(false);
		}
	}

	public void sendPacket(boolean verified, Connection conn) {
		Packet packet = new Packet(IPacket.CLIENT.VERIFIED.name());
		packet.put("verified", verified);
		ConnectionManager.verificationFilter.sendPacket(packet, conn);
	}

}
