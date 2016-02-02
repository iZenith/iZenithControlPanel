package net.bobmandude9889.packetManager.packets;

import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.packets.listeners.ConsoleUpdatePacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerListUpdatePacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerMsgPacket;
import net.bobmandude9889.packetManager.packets.listeners.RegisterUserPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerClosingPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerCommandPacket;
import net.bobmandude9889.packetManager.packets.listeners.UserRegisterErrorPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerificationRequestPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifiedPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifyCodePacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifyPassPacket;

public class IPacket {
	
	// Named based on who receives the packet. 
	public enum SERVER {
		
		SERVER_COMMAND(new ServerCommandPacket(), true),
		PLAYER_MSG(new PlayerMsgPacket(), true),
		VERIFY_PASS(new VerifyPassPacket(), false),
		VERIFY_CODE(new VerifyCodePacket(), false),
		REGISTER_USER(new RegisterUserPacket(), false);
		
		PacketListener listener;
		Boolean reqVerify;
		
		private SERVER(PacketListener listener, boolean reqVerify){
			this.listener = listener;
			this.reqVerify = reqVerify;
		}

		public PacketListener getListener() {
			return listener;
		}
		
		public Boolean requiresVerification(){
			return reqVerify;
		}
		
	}
	
	public enum CLIENT {
		
		SERVER_CLOSING(new ServerClosingPacket(), false),
		PLAYER_LIST_UPDATE(new PlayerListUpdatePacket(), true),
		VERIFIED(new VerifiedPacket(), false),
		CONSOLE_UPDATE(new ConsoleUpdatePacket(), true),
		USER_REGISTER_ERROR(new UserRegisterErrorPacket(), false),
		VERIFICATION_REQUEST(new VerificationRequestPacket(), false);
		
		PacketListener listener;
		Boolean reqVerify;
		
		private CLIENT(PacketListener listener, boolean reqVerify){
			this.listener = listener;
			this.reqVerify = reqVerify;
		}

		public PacketListener getListener() {
			return listener;
		}
		
		public Boolean requiresVerification(){
			return reqVerify;
		}
		
	}
	
}
