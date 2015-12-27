package net.bobmandude9889.bukkit.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.bobmandude9889.bukkit.Main;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerListUpdatePacket;

public class PlayerListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		sendPlayerListUpdate();
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable(){
			public void run(){
				sendPlayerListUpdate();
			}
		},20);
	}
	
	public void sendPlayerListUpdate(){
		((PlayerListUpdatePacket) IPacket.CLIENT.PLAYER_LIST_UPDATE.getListener()).sendPacket();
	}
	
}
