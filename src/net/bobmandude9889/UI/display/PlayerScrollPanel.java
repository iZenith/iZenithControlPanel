package net.bobmandude9889.UI.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.bobmandude9889.UI.player.Player;
import net.bobmandude9889.packetManager.connections.ConnectionManager;
import net.bobmandude9889.packetManager.packets.IPacket;
import net.bobmandude9889.packetManager.packets.listeners.PlayerMsgPacket;
import net.bobmandude9889.packetManager.packets.listeners.ServerCommandPacket;

@SuppressWarnings("serial")
public class PlayerScrollPanel extends JPanel {

	private List<Player> players;

	private static int sectionHeight = 63;
	@SuppressWarnings("unused")
	private Frame frame;
	private String param = "";

	public PlayerScrollPanel(Frame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(180, 5));
		setLayout(null);
		players = new CopyOnWriteArrayList<Player>();
	}

	public void updatePlayerList() {
		removeAll();
		int height = 5 + sectionHeight * players.size();
		height = height < 235 ? 235 : height;
		setPreferredSize(new Dimension(180, height));

		List<Player> accept = new ArrayList<Player>();
		for (Player p : players) {
			if (p.getName().contains(this.param)) {
				accept.add(p);
			}
		}

		for (final Player p : accept) {
			if (p.getName().contains(param)) {
				Color back = Color.WHITE;
				if (accept.indexOf(p) % 2 == 1) {
					back = Color.LIGHT_GRAY;
				}

				JButton kickBtn = new JButton("Kick");
				kickBtn.setBackground(back);
				kickBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ServerCommandPacket packet = (ServerCommandPacket) IPacket.SERVER.SERVER_COMMAND.getListener();
						String reason = JOptionPane.showInputDialog(null, "Enter a reason");
						if(reason != null){
							packet.sendPacket("kick " + p.getName() + " " + reason);
						}
					}
				});
				kickBtn.setBounds(60, 15 + sectionHeight * accept.indexOf(p), 57, 23);

				JButton banBtn = new JButton("Ban");
				banBtn.setBackground(back);
				banBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ServerCommandPacket packet = (ServerCommandPacket) IPacket.SERVER.SERVER_COMMAND.getListener();
						String reason = JOptionPane.showInputDialog(null, "Enter a reason");
						packet.sendPacket("ban " + p.getName() + " " + reason);
					}
				});
				banBtn.setBounds(120, 15 + sectionHeight * accept.indexOf(p), 57, 23);

				//Msg button
				JButton messageBtn = new JButton("Msg");
				messageBtn.setBackground(back);
				messageBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						new MessageWindow(p).setVisible(true);
					}
				});
				messageBtn.setBounds(60, 37 + sectionHeight * accept.indexOf(p), 57, 23);

				JButton moreBtn = new JButton("More");
				moreBtn.setBackground(back);
				moreBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						new PlayerWindow(p);
					}

				});
				moreBtn.setBounds(120, 37 + sectionHeight * accept.indexOf(p), 57, 23);

				add(kickBtn);
				add(banBtn);
				add(messageBtn);
				add(moreBtn);
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
				revalidate();
			}
		});
	}

	public void setParam(String arg) {
		this.param = arg;
		updatePlayerList();
		Frame.scrollPane.getVerticalScrollBar().setValue(0);
	}

	public void clear() {
		for (Player player : players) {
			remove(player,false);
		}
		updatePlayerList();
	}

	public void add(Player player, boolean update) {
		if (!players.contains(player)) {
			players.add(player);
		}
		if(update)
			updatePlayerList();
	}

	public void remove(Player player, boolean update) {
		players.remove(player);
		if(update)
			updatePlayerList();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < getHeight() / 37; i++) {
			g.setColor(i % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
			g.fillRect(0, i * sectionHeight, 200, sectionHeight);
		}

		List<Player> accept = new ArrayList<Player>();
		for (Player p : players) {
			if (p.getName().contains(this.param)) {
				accept.add(p);
			}
		}

		for (Player p : accept) {
			BufferedImage head = p.getHead();
			g.drawImage(head, 5, 5 + sectionHeight * accept.indexOf(p), 50, 50, null);
			g.setColor(Color.BLACK);
			g.drawString(p.getName(), 65, 12 + sectionHeight * accept.indexOf(p));
		}
	}

}
