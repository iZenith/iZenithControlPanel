package net.bobmandude9889.bukkit.Users;

import java.net.InetAddress;
import java.util.HashMap;

public class UserManager {

	private static HashMap<InetAddress, String> users;

	public static void init(){
		users = new HashMap<InetAddress, String>();
	}
	
	public static void addUser(InetAddress address, String name) {
		users.put(address, name);
	}

	public static void removeUser(InetAddress address){
		users.remove(address);
	}
	
	public static boolean isVerified(InetAddress address) {
		return users.containsKey(address);
	}

	public static String getName(InetAddress address) {
		return users.get(address);
	}

}
