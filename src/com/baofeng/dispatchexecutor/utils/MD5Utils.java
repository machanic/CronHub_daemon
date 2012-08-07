package com.baofeng.dispatchexecutor.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Utils {
	private static MessageDigest digest = null;
	public synchronized static final String MD5Hash(String data,String charcode) throws UnsupportedEncodingException {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. "
						+ "We will be unable to function normally.");
				nsae.printStackTrace();
			}
		}
		// Now, compute hash.
		digest.update(data.getBytes(charcode));
		return encodeHex(digest.digest());
	}
		public synchronized static final String MD5Hash(String data) throws UnsupportedEncodingException {
			return MD5Hash(data,"UTF-8");
		}
		public static final String encodeHex(byte[] bytes) {
			StringBuffer buf = new StringBuffer(bytes.length * 2);
			int i;

			for (i = 0; i < bytes.length; i++) {
				if (((int) bytes[i] & 0xff) < 0x10) {
					buf.append("0");
				}
				buf.append(Long.toString((int) bytes[i] & 0xff, 16));
			}
			return buf.toString().toUpperCase();
		}
}
