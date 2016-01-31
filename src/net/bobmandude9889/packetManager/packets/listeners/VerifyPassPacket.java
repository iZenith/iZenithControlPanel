package net.bobmandude9889.packetManager.packets.listeners;

import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Util.Config;
import net.bobmandude9889.bukkit.Util.Encrypt;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class VerifyPassPacket implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		String user = (String) packet.getValue("user");
		String pass = (String) packet.getValue("pass");
		FileConfiguration users = Config.getUsers();
		boolean verified = false;
		if(users.contains(user.toLowerCase())){
			ConfigurationSection userS = users.getConfigurationSection(user);
			String salt = userS.getString("salt");
			String confPass = userS.getString("pass");
			Boolean confirmed = userS.getBoolean("conf");
			String encPass = Encrypt.encrypt2(pass, salt);
			if(confPass.equals(encPass))
				verified = true;
		}
		
		System.out.println("Connection from " + conn + " username: " + user + " verified: " + verified);
		
		VerifiedPacket verPacket = (VerifiedPacket) IPacket.CLIENT.VERIFIED.getListener();
		verPacket.sendPacket(verified, conn);
		
		PlayerListUpdatePacket listPacket = (PlayerListUpdatePacket) IPacket.CLIENT.PLAYER_LIST_UPDATE.getListener();
		listPacket.sendPacket();
		
		if(!verified){
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendPacket(String username, String password) {
		String encPass = Encrypt.md5(password);
		Packet packet = new Packet(IPacket.SERVER.VERIFY_PASS.name());
		packet.put("user", username);
		packet.put("pass", encPass);
		ConnectionManager.serverConn.sendPacket(packet);
	}

}
