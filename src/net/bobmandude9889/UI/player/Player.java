package net.bobmandude9889.UI.player;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Player {

	private String name;
	private BufferedImage rawSkin;
	private BufferedImage head;
	private BufferedImage skin;
	private String uuid;
	private BufferedImage unknown;

	protected Player(String name) {
		this.name = name;
		Image unknownImg = new ImageIcon(Player.class.getResource("/res/unknown.png")).getImage();
		unknown = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		unknown.getGraphics().drawImage(unknownImg, 0, 0, 32, 32, null);
	}

	private boolean loadRawSkin() {
		try {
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + getUUID());
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(in.readLine());
			String value = (String) ((JSONObject) ((JSONArray) obj.get("properties")).get(0)).get("value");
			String decoded = new String(Base64.getDecoder().decode(value));
			obj = (JSONObject) parser.parse(decoded);
			URL imgURL = new URL((String) ((JSONObject) (((JSONObject) obj.get("textures")).get("SKIN"))).get("url"));
			rawSkin = ImageIO.read(imgURL);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getUUID() {
		if (uuid == null) {
			try {
				URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(in.readLine());
				uuid = (String) obj.get("id");
			} catch (IOException | ParseException e) {
				return null;
			}
		}
		return uuid;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getHead() {
		if (head == null) {
			if (rawSkin == null) {
				if (!loadRawSkin()) {
					System.err.println("Could not retrieve skin for " + this);
					return unknown;
				}
			}
			head = rawSkin.getSubimage(8, 8, 8, 8);
		}
		return head;
	}

	public BufferedImage getSkin() {
		if (skin == null) {
			if (rawSkin == null) {
				if (!loadRawSkin()) {
					return unknown;
				}
			}
			skin = new BufferedImage(16, 32, BufferedImage.TYPE_INT_ARGB);
			Graphics g = skin.getGraphics();
			g.drawImage(rawSkin.getSubimage(8, 8, 8, 8), 4, 0, null);
			g.drawImage(rawSkin.getSubimage(44, 20, 4, 12), 0, 8, null);
			if (rawSkin.getHeight() == 32) {
				g.drawImage(rawSkin.getSubimage(44, 20, 4, 12), 12, 8, null);
				g.drawImage(rawSkin.getSubimage(20, 20, 8, 12), 4, 8, null);
				g.drawImage(rawSkin.getSubimage(4, 20, 4, 12), 4, 20, null);
				g.drawImage(rawSkin.getSubimage(4, 20, 4, 12), 8, 20, null);
			} else {
				g.drawImage(rawSkin.getSubimage(36, 52, 4, 12), 12, 8, null);
				g.drawImage(rawSkin.getSubimage(44, 36, 4, 4), 0, 8, null);
				g.drawImage(rawSkin.getSubimage(52, 52, 4, 4), 12, 8, null);
				g.drawImage(rawSkin.getSubimage(20, 20, 8, 12), 4, 8, null);
				g.drawImage(rawSkin.getSubimage(20, 36, 8, 12), 4, 8, null);
				g.drawImage(rawSkin.getSubimage(4, 20, 4, 12), 4, 20, null);
				g.drawImage(rawSkin.getSubimage(4, 36, 4, 12), 4, 20, null);
				g.drawImage(rawSkin.getSubimage(20, 52, 4, 12), 8, 20, null);
				g.drawImage(rawSkin.getSubimage(4, 52, 4, 12), 8, 20, null);
			}
		}
		return skin;
	}

	@Override
	public String toString(){
		return getName();
	}
	
}
