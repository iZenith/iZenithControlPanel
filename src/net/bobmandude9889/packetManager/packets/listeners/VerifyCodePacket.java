package net.bobmandude9889.packetManager.packets.listeners;

import org.bukkit.configuration.ConfigurationSection;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.Packet;
import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.bukkit.Users.UserManager;
import net.bobmandude9889.bukkit.Util.Config;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;

public class VerifyCodePacket implements PacketListener {

	@Override
	public void packetReceived(Packet packet, Connection conn) {
		String name = (String) packet.getValue("user");
		String code = (String) packet.getValue("code");
		ConfigurationSection user = Config.getUsers().getConfigurationSection(name);
		if (user.getString("code").equals(code)) {
			user.set("code", null);
			user.set("conf", true);
			Config.saveUsers();
			
			UserManager.addUser(conn.getTCPSocket().getInetAddress(), name);
			
			VerifiedPacket verified = (VerifiedPacket) IPacket.CLIENT.VERIFIED.getListener();
			verified.sendPacket(true, conn);
			
			PlayerListUpdatePacket listPacket = (PlayerListUpdatePacket) IPacket.CLIENT.PLAYER_LIST_UPDATE.getListener();
			listPacket.sendPacket();
		} else {
			UserRegisterErrorPacket errPacket = (UserRegisterErrorPacket) IPacket.CLIENT.USER_REGISTER_ERROR.getListener();
			errPacket.sendPacket("Incorrect verification code!", conn);
		}
	}

	public void sendPacket(String encCode, String name) {
		Packet packet = new Packet(IPacket.SERVER.VERIFY_CODE.name());
		packet.put("code", encCode);
		packet.put("user", name);
		ConnectionManager.serverConn.sendPacket(packet);
	}

}
