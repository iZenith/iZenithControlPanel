package net.bobmandude9889.bukkit;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.bobmandude9889.bukkit.Listeners.PlayerListener;
import net.bobmandude9889.bukkit.console.ConsoleLogAppender;
import net.bobmandude9889.packetManager.connections.ConnectionHandlerType;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerClosingPacket;

public class Main extends JavaPlugin {

	private static FileConfiguration config;
	public static Main instance;
	public static File conBuff;

	@Override
	public void onEnable() {
		ConsoleLogAppender appender = new ConsoleLogAppender();
		Logger logger = (Logger) LogManager.getRootLogger();
		logger.addAppender(appender);
		
		/*conBuff = new File(getDataFolder(), "cmdbuff");
		try {
			if (!conBuff.exists())
				conBuff.createNewFile();
			OutputStream out = System.out;
			System.setOut(new ConsoleOutputStream(out, conBuff));
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		if (!getFile().exists()) {
			getLogger().info("Generating config");
		}
		saveDefaultConfig();
		instance = this;
		config = getConfig();
		if (!ConnectionManager.isConnected()) {
			getLogger().info("Initialized connection server...");
			if (ConnectionManager.init(ConnectionHandlerType.SERVER)) {
				getLogger().info("Connection server succesfully initialized.");
			} else {
				getLogger().info("There was a problem while initializing the connection server");
			}
		}
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public void onDisable() {
		getLogger().info("Disabling connection server");
		ServerClosingPacket packet = (ServerClosingPacket) IPacket.CLIENT.SERVER_CLOSING.getListener();
		packet.sendPacket();
		ConnectionManager.closeConnections();
	}

	public static FileConfiguration getConf() {
		return config;
	}

}
