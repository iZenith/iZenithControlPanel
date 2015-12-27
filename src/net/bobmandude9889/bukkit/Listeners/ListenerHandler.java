package net.bobmandude9889.bukkit.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import net.bobmandude9889.bukkit.Main;

public class ListenerHandler {

	public static List<Listener> listeners;
	
	public static void init(){
		listeners = new ArrayList<Listener>();
		listeners.add(new PlayerListener());
		
		for(Listener lis : listeners){
			Bukkit.getPluginManager().registerEvents(lis, Main.instance);
		}
	}
	
}
