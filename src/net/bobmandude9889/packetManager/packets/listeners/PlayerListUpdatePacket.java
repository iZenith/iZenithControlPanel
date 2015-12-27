package net.bobmandude9889.packetManager.packets.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;

import net.bobmandude9889.UI.display.Frame;
import net.bobmandude9889.UI.player.PlayerManager;
import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class PlayerListUpdatePacket implements PacketListener{

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		JSONArray playerNames = (JSONArray) packet.getValue("players");
		Frame.playerPanel.clear();
		for(int i = 0; i < playerNames.size(); i++){
			Frame.playerPanel.add(PlayerManager.getPlayer((String) playerNames.get(i)),true);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void sendPacket() {
		Packet packet = new Packet(IPacket.CLIENT.PLAYER_LIST_UPDATE.name());
		JSONArray playerNames = new JSONArray();
		for(Player p : Bukkit.getOnlinePlayers()){
			playerNames.add(p.getName());
		}
		packet.put("players", playerNames);
		System.out.println(playerNames);
		ConnectionManager.server.broadcast(packet);
	}
	
}
