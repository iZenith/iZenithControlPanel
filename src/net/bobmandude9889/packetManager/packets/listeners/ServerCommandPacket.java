package net.bobmandude9889.packetManager.packets.listeners;

import org.bukkit.Bukkit;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Main;
import net.bobmandude9889.packetManager.connections.ConnectionManager;

public class ServerCommandPacket implements PacketListener{
	
	@Override
	public void packetReceived(Packet packet, Connection conn) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){

			@Override
			public void run() {
				Main.instance.getServer().dispatchCommand(Bukkit.getConsoleSender(), (String) packet.getValue("value"));	
			}
			
		});
	}

	public void sendPacket(String command){
		Packet packet = new Packet("SERVER_COMMAND");
		packet.put("value", command);
		if(ConnectionManager.serverConn != null)
			ConnectionManager.serverConn.sendPacket(packet);
		else
			System.out.println("Please connect to a server first");
	}
	
}
