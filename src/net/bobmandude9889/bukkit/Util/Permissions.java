package net.bobmandude9889.bukkit.Util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;

public class Permissions {

	private static Permission perms = null;
	
	public static Permission getPermHandler(){
		if(perms == null){
			setupPerms();
		}
		return perms;
	}
	
	public static void setupPerms(){
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            perms = permissionProvider.getProvider();
        }
	}
	
}
