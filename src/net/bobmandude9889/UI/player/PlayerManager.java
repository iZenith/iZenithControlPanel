package net.bobmandude9889.UI.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

	private static List<Player> players = new ArrayList<Player>();
	
	public static Player getPlayer(String name){
		Player player = null;
		for(Player p : players){
			if(p.getName().equalsIgnoreCase(name)){
				player = p;
				break;
			}
		}
		
		if(player == null){
			player = new Player(name);
			players.add(player);
		}
		return player;
	}
	
}