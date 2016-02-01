package net.bobmandude9889.packetManager.packets.listeners;

import javax.swing.JOptionPane;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.packets.IPacket;

public class UserRegisterErrorPacket implements PacketListener{

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		JOptionPane.showMessageDialog(null, packet.getValue("msg"), "Error", 0);
	}

	public void sendPacket(String message, Connection conn){
		Packet packet = new Packet(IPacket.CLIENT.USER_REGISTER_ERROR.name());
		packet.put("msg", message);
		conn.sendPacket(packet);
	}
	
}
