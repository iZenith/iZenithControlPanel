package net.bobmandude9889.packetManager.packets;

import net.bobmandude9889.api.PacketListener;
import net.bobmandude9889.packetManager.packets.listeners.ConsoleUpdatePacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerListUpdatePacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerMsgPacket;
import net.bobmandude9889.packetManager.packets.listeners.RegisterUserPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerClosingPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerCommandPacket;
import net.bobmandude9889.packetManager.packets.listeners.UserRegisterErrorPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifiedPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerifyPassPacket;
import net.bobmandude9889.packetManager.packets.listeners.VerificationRequestPacket;

public class IPacket {
	
	// Named based on who receives the packet. 
	public enum SERVER {
		
		SERVER_COMMAND(new ServerCommandPacket()),
		PLAYER_MSG(new PlayerMsgPacket()),
		VERIFY_PASS(new VerifyPassPacket()),
		REGISTER_USER(new RegisterUserPacket());
		
		PacketListener listener;
		
		private SERVER(PacketListener listener){
			this.listener = listener;
		}

		public PacketListener getListener() {
			return listener;
		}
		
	}
	
	public enum CLIENT {
		
		SERVER_CLOSING(new ServerClosingPacket()),
		PLAYER_LIST_UPDATE(new PlayerListUpdatePacket()),
		VERIFIED(new VerifiedPacket()),
		CONSOLE_UPDATE(new ConsoleUpdatePacket()),
		USER_REGISTER_ERROR(new UserRegisterErrorPacket()),
		VERIFICATION_REQUEST(new VerificationRequestPacket());
		
		PacketListener listener;
		
		private CLIENT(PacketListener listener){
			this.listener = listener;
		}

		public PacketListener getListener() {
			return listener;
		}
		
	}
	
}
