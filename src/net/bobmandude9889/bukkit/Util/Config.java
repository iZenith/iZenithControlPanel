package net.bobmandude9889.bukkit.Util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.bobmandude9889.bukkit.Main;

public class Config {

	static File file;
	static FileConfiguration userFile;

	public static int getPort() {
		return Main.getConf().getInt("port");
	}

	public static FileConfiguration getUsers() {
		if (file == null) {
			file = new File(Main.instance.getDataFolder(), "users.yml");
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			userFile = YamlConfiguration.loadConfiguration(file);
		}
		return userFile;
	}

}
