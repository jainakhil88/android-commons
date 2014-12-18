package com.aj.android.commons.java;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {
	/**
	 * Calculate the MD5 of a given String
	 * 
	 * @param text -{@link String} input.
	 * @return {@link String} -MD5 of the given input.
	 */
	public static String calculateMD5(String text) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}
		StringBuilder hex = new StringBuilder(hash.length * 2);

		for (byte b : hash) {
			int i = (b & 0xFF);
			if (i < 0x10)
				hex.append('0');
			hex.append(Integer.toHexString(i));
		}

		return hex.toString();
	}

	/**
	 * Calculate the SHA-1 of a given String
	 * 
	 * @param text -{@link String} input.
	 * @return {@link String} -SHA-1 of the given input.
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	public static String calculateSHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		try {
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
		} catch (UnsupportedEncodingException e) {
			throw e;

		}
		byte[] sha1hash = md.digest();
		return StringUtils.convertToHex(sha1hash);
	}
}
