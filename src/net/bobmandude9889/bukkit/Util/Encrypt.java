package net.bobmandude9889.bukkit.Util;

import java.math.BigInteger;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class Encrypt {

	public static void main(String[] args) {
		String pass = "password";
		String salt = "asdgksaj";
		pass = md5(pass);

		pass += salt;
		pass = md5(pass);
	}

	public static String encrypt2(String encPass, String salt) {
		return md5(encPass + salt);
	}

	public static String encrypt(String pass, String salt) {
		String out = md5(pass);
		return encrypt2(pass, salt);
	}

	public static String md5(String message) {
		return DigestUtils.md5Hex(message.getBytes());
	}

	public static String genVerCode() {
		return genString(60);
	}

	public static String genSalt() {
		return genString(240);
	}
	
	private static String genString(int bitLength){
		return new BigInteger(bitLength,new Random()).toString(32);
	}
	
}