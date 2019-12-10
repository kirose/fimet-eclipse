package com.fimet.commons;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.xml.bind.DatatypeConverter;

//     Requires Java 8:
import java.util.Base64;


public class SQLDeveloperDecrypt {

	private static byte[] des_cbc_decrypt(byte[] encrypted_password, byte[] decryption_key, byte[] iv) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryption_key, "DES"), new IvParameterSpec(iv));
		return cipher.doFinal(encrypted_password);
	}
	private static byte[] doKey(byte[] db_system_id) throws NoSuchAlgorithmException {
		byte[] salt = DatatypeConverter.parseHexBinary("051399429372e8ad");
		// key = db_system_id + salt
		byte[] key = new byte[db_system_id.length + salt.length];
		System.arraycopy(db_system_id, 0, key, 0, db_system_id.length);
		System.arraycopy(salt, 0, key, db_system_id.length, salt.length);

		java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		for (int i=0; i<42; i++) {
			key = md.digest(key);
		}
		return key;
	}
	private static byte[] decrypt_v4(byte[] encrypted, byte[] db_system_id) throws GeneralSecurityException {

		byte[] encrypted_password = Base64.getDecoder().decode(encrypted);

		// key = db_system_id + salt
		byte[] key = doKey(db_system_id);

		// secret_key = key [0..7]
		byte[] secret_key = new byte[8];
		System.arraycopy(key, 0, secret_key, 0, 8);

		// iv = key [8..]
		byte[] iv = new byte[key.length - 8];
		System.arraycopy(key, 8, iv, 0, key.length - 8);

		return des_cbc_decrypt(encrypted_password, secret_key, iv);

	}
	public static String decrypt(String encrypted, String key) {
		try {
			byte[] decrypted = decrypt_v4(encrypted.getBytes(), key.getBytes());
			return new String(decrypted);
		} catch (Exception e) {
			return null;
		}
	}
	private static byte[] des_cbc_encrypt(byte[] decrypted_password, byte[] encryption_key, byte[] iv) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryption_key, "DES"), new IvParameterSpec(iv));
		return cipher.doFinal(decrypted_password);
	}
	private static byte[] encrypt_v4(byte[] decrypted_password, byte[] db_system_id) throws GeneralSecurityException {

		// key = db_system_id + salt
		byte[] key = doKey(db_system_id);

		// secret_key = key [0..7]
		byte[] secret_key = new byte[8];
		System.arraycopy(key, 0, secret_key, 0, 8);

		// iv = key [8..]
		byte[] iv = new byte[key.length - 8];
		System.arraycopy(key, 8, iv, 0, key.length - 8);

		byte[] encrypted = des_cbc_encrypt(decrypted_password, secret_key, iv);

		return Base64.getEncoder().encode(encrypted);

	}
	public static String encrypt(String decrypted, String key) {
		try {
			byte[] encrypted = encrypt_v4(decrypted.getBytes(), key.getBytes());
			return new String(encrypted);
		} catch (Exception e) {
			return null;
		}
	}
	public static void main(String[] argv) {
		System.out.println(decrypt("b+Y9OUGqNdqVjMhB29Idnw==", "9a30b58f-50de-4555-93a5-b8cdcb646534"));
		System.out.println(decrypt("rs6Q2YeZ/ECOjoYXtMGXtA==", "123qwe!\"#QWE"));
		System.out.println(encrypt("bcmrp0s0wn3r20", "9a30b58f-50de-4555-93a5-b8cdcb646534"));
		System.out.println(encrypt("bcmrp0s0wn3r20", "123qwe!\"#QWE"));
	}
}