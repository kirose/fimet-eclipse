package com.fimet.commons;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.fimet.commons.exception.FimetException;

public final class Version implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 173839934905L;
	private static final String VERSION_OBJECT = "\u0072\u004f\u0030\u0041\u0042\u0058\u004e\u0079\u0041\u0042\u006c\u006a\u0062\u0032\u0030\u0075\u005a\u006d\u006c\u0074\u005a\u0058\u0051\u0075\u0059\u0032\u0039\u0074\u0062\u0057\u0039\u0075\u0063\u0079\u0035\u0057\u005a\u0058\u004a\u007a\u0061\u0057\u0039\u0075\u0041\u0041\u0041\u0041\u004b\u0048\u006d\u0071\u0035\u0062\u006b\u0043\u0041\u0041\u004a\u004d\u0041\u0041\u004e\u0072\u005a\u0058\u006c\u0030\u0041\u0042\u004a\u004d\u0061\u006d\u0046\u0032\u0059\u0053\u0039\u0073\u0059\u0057\u0035\u006e\u004c\u0031\u004e\u0030\u0063\u006d\u006c\u0075\u005a\u007a\u0074\u004d\u0041\u0041\u0064\u0032\u005a\u0058\u004a\u007a\u0061\u0057\u0039\u0075\u0063\u0051\u0042\u002b\u0041\u0041\u0046\u0034\u0063\u0048\u0051\u0041\u0048\u006d\u0045\u0078\u0051\u0033\u0031\u0053\u004c\u0053\u0041\u0034\u0050\u0056\u0052\u0053\u004c\u006b\u0063\u0075\u0054\u0045\u004e\u0054\u004c\u0047\u0063\u006f\u0057\u0057\u005a\u006c\u0049\u0031\u0034\u0032\u0049\u0048\u0055\u0034\u004e\u0048\u0051\u0041\u0043\u0030\u005a\u0070\u0062\u0057\u0056\u0030\u0049\u0044\u0049\u0075\u004d\u0043\u0034\u0032";
	private static Version instance;
	public static Version getInstance() {
		if (instance == null) {
			instance = fromString(VERSION_OBJECT);
		}
		return instance;
	}
	private String version;
	private String key;
	public Version(String version, String key){
		this.version = version;
		this.key = key;
	}
	public final String getVersion() {
		return version;
	}
	private static Version fromString(String s) {
		byte [] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o  = ois.readObject();
			ois.close();
			return (Version)o;
		} catch (Exception e) {
			Activator.getInstance().error("Invalid Version "+s,e);
			throw new FimetException("Invalid version");
		}
	}
	public final String decrypt(String encrypted) {
		try {
			byte[] decrypted = decrypt_v4(encrypted.getBytes(), key.getBytes());
			return new String(decrypted);
		} catch (Exception e) {
			Activator.getInstance().warning("Version.decrypt: "+e.getMessage(), e);
			return null;
		}
	}
	public final String encrypt(String decrypted) {
		try {
			byte[] encrypted = encrypt_v4(decrypted.getBytes(), key.getBytes());
			return new String(encrypted);
		} catch (Exception e) {
			Activator.getInstance().warning("Version.encrypt: "+e.getMessage(), e);
			return null;
		}
	}
	private static byte[] des_cbc_decrypt(byte[] encrypted_password, byte[] decryption_key, byte[] iv)
	throws GeneralSecurityException {
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
}