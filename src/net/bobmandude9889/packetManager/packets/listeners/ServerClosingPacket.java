package net.bobmandude9889.packetManager.packets.listeners;

import javax.swing.JOptionPane;

import net.bobmandude9889.UI.display.Frame;
import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class ServerClosingPacket implements PacketListener{

	@Override
	public void packetReceived(Packet packet, Connection connection) {
		JOptionPane.showMessageDialog(null, "You have been disconnected because the server is shutting down or reloading.","Server closing",1);
		ConnectionManager.closeConnections();
		Frame.instance.dispose();
		Frame.main(null);
	}

	public Packet sendPacket(){
		Packet packet = new Packet(IPacket.CLIENT.SERVER_CLOSING.name());
		ConnectionManager.server.broadcast(packet);
		return packet;
	}
	
}
