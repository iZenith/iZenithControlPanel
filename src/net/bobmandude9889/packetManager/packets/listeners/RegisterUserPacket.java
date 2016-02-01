package net.bobmandude9889.packetManager.packets.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Util.Config;
import net.bobmandude9889.bukkit.Util.Encrypt;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.md_5.bungee.api.ChatColor;

public class RegisterUserPacket implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		String pass = (String) packet.getValue("pass");
		String name = (String) packet.getValue("user");
		FileConfiguration users = Config.getUsers();
		if (!users.contains(name) || !users.getBoolean(name + ".conf")) {
			Player player = Bukkit.getPlayer(name);
			if (player == null) {
				UserRegisterErrorPacket errPacket = (UserRegisterErrorPacket) IPacket.CLIENT.USER_REGISTER_ERROR.getListener();
				errPacket.sendPacket("Player does not exist or is not online!", conn);
				if(Config.isDebug())
					System.out.println("Player does not exist!");
				return;
			}

			if (player.hasPermission("remote.access")) {
				String verCode = Encrypt.genVerCode();
				player.sendMessage(ChatColor.BLUE + "If you are trying to register with the remote panel, enter this code: " + ChatColor.GREEN + verCode);

				String encVerCode = Encrypt.md5(verCode);

				users.createSection(name);

				ConfigurationSection userSect = users.getConfigurationSection(name);

				String salt = Encrypt.genSalt();
				userSect.set("salt", salt);
				userSect.set("pass", Encrypt.encrypt2(pass, salt));
				userSect.set("conf", false);
				userSect.set("code", encVerCode);
				
				VerificationRequestPacket verifReq = (VerificationRequestPacket) IPacket.CLIENT.VERIFICATION_REQUEST.getListener();
				verifReq.sendPacket(conn);
			} else {
				UserRegisterErrorPacket errPacket = (UserRegisterErrorPacket) IPacket.CLIENT.USER_REGISTER_ERROR.getListener();
				errPacket.sendPacket("You do not have permission to register an account!", conn);
				if(Config.isDebug())
					System.out.println("Not enough permissions to register!");
				return;
			}
		} else {
			UserRegisterErrorPacket errPacket = (UserRegisterErrorPacket) IPacket.CLIENT.USER_REGISTER_ERROR.getListener();
			errPacket.sendPacket("A user with that name already exists!", conn);
			if(Config.isDebug())
				System.out.println("User already exists!");
			return;
		}
	}

	public void sendPacket(String name, String pass) {
		String passEnc = Encrypt.md5(pass);
		Packet packet = new Packet(IPacket.SERVER.REGISTER_USER.name());
		packet.put("user", name.toLowerCase());
		packet.put("pass", passEnc);
		ConnectionManager.serverConn.sendPacket(packet);
	}

}
