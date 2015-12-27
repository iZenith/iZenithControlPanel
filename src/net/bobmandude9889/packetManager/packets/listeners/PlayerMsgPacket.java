package net.bobmandude9889.packetManager.packets.listeners;

import org.bukkit.Bukkit;

import net.bobmandude9889.UI.player.Player;
import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class PlayerMsgPacket implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		String msg = (String) packet.getValue("text");
		boolean showName = (boolean) packet.getValue("show_name");
		String player = (String) packet.getValue("player");
		String from = (String) packet.getValue("from");
		org.bukkit.entity.Player bPlayer = Bukkit.getPlayer(player);
		String sender = showName ? from : "console";
		bPlayer.sendMessage(sender + " says: " + msg);
	}

	public void sendPacket(String msg, boolean showName, Player player, String from) {
		Packet packet = new Packet(IPacket.SERVER.PLAYER_MSG.name());
		packet.put("text", msg);
		packet.put("show_name", showName);
		packet.put("player", player.getName());
		packet.put("from", from);
		ConnectionManager.serverConn.sendPacket(packet);
	}

}
