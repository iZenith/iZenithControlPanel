package net.bobmandude9889.packetManager.connections;

import java.io.IOException;

import net.bobmandude9889.api.Connection;
import net.bobmandude9889.api.ConnectionServer;
import net.bobmandude9889.api.NetworkAPI;
import net.bobmandude9889.api.PacketHandler;
import net.bobmandude9889.packetManager.packets.IPacket;

public class ConnectionManager {

	public static ConnectionHandlerType type;
	public static PacketHandler packetHandler;
	public static ConnectionServer server;
	public static Connection serverConn;

	private static boolean connected = false;

	public static boolean init(ConnectionHandlerType connType) {
		if (!connected) {
			type = connType;

			packetHandler = new PacketHandler();

			if (connType.equals(ConnectionHandlerType.SERVER)) {
				for (IPacket.SERVER packet : IPacket.SERVER.values()) {
					packetHandler.addListener(packet.name(), packet.getListener());
				}
			} else {
				for (IPacket.CLIENT packet : IPacket.CLIENT.values()) {
					packetHandler.addListener(packet.name(), packet.getListener());
				}
			}

			connType.init();
		}
		return connected;
	}

	public static void closeConnections(){
		try {
			packetHandler.close();
			NetworkAPI.getConnectionHandler().close();
			if(server != null)
				server.close();
			if(serverConn != null)
				serverConn.close();
			setConnected(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setConnected(boolean value) {
		connected = value;
	}
	
	public static boolean isConnected(){
		return connected;
	}

	public static Connection getConnection(){
		return serverConn;
	}
	
}
