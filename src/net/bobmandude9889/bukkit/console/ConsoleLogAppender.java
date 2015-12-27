package net.bobmandude9889.bukkit.console;

import java.io.IOException;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.ConsoleUpdatePacket;

public class ConsoleLogAppender extends AbstractAppender {

	public ConsoleLogAppender() {
		super("ControlPanel", null, null);
		start();
	}

	@Override
	public void append(LogEvent e) {
		ConsoleUpdatePacket packet = (ConsoleUpdatePacket) IPacket.CLIENT.CONSOLE_UPDATE.getListener();
		try {
			packet.sendPacket(e.getMessage().getFormattedMessage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}