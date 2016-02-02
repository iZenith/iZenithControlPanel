package net.bobmandude9889.bukkit.Util;

import java.math.BigInteger;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class Encrypt {
	
	public static String encrypt2(String encPass, String salt) {
		return md5(encPass + salt);
	}

	public static String encrypt(String pass, String salt) {
		return md5(md5(pass) + salt);
	}

	public static String md5(String message) {
		return DigestUtils.md5Hex(message.getBytes());
	}

	public static String genVerCode() {
		return genString(60);
	}

	public static String genSalt() {
		return genString(30);
	}
	
	private static String genString(int bitLength){
		return new BigInteger(bitLength,new Random()).toString(32);
	}
	
}