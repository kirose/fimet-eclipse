package com.fimet.cfg;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fimet.commons.Activator;
import com.fimet.commons.Version;
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.io.FileUtils;

public class Versioner {
	public static final Pattern P_SERIAL_VERSION = Pattern.compile("private static final long serialVersionUID = ([0-9]+)L;");
	public static final Pattern P_VERSION_OBJECT = Pattern.compile("private static final String VERSION_OBJECT = \"(.+)\";");
	private static Random random = new Random();
	private static final File FILE_VERSION = new File("C:\\eclipse\\wsplugins\\com.fimet.commons\\src\\com\\fimet\\commons\\Version.java");
	public static void main(String[] args) {
		createNewVersion();
		//a1C}R- 8=TR.G.LCS,g(Yfe#^6 u84
	}
	private static void createNewVersion() {
		String versionFileContents = FileUtils.readContents(FILE_VERSION);
		/*Matcher mSerialVersion = P_SERIAL_VERSION.matcher(versionFileContents);
		long versionLong = 0;
		String serialVersion = null;
		if (mSerialVersion.find()) {
			serialVersion = mSerialVersion.group(0);
			versionLong = Long.parseLong(mSerialVersion.group(1));
		}*/
		Matcher mVersionObject = P_VERSION_OBJECT.matcher(versionFileContents);
		String versionObject = null;
		if (mVersionObject.find()) {
			versionObject = mVersionObject.group(1);
		}
		
		String key = "a1C}R- 8=TR.G.LCS,g(Yfe#^6 u84";//randomString(30);
		Version version = new Version("Fimet 2.0.6", key);
		String versionObjectNew = toString(version); 
		version = fromString(versionObjectNew);
		System.out.println("VERSION:"+version.getVersion());
		System.out.println("KEY:"+key);
		versionObjectNew = toUnicode(versionObjectNew);
		//versionLong++;
		versionFileContents = versionFileContents.replace(versionObject, versionObjectNew);
		//versionFileContents = versionFileContents.replace(serialVersion, "private static final long serialVersionUID = "+versionLong+"L;");

		FileUtils.writeContents(FILE_VERSION, versionFileContents);
	}
	private static Version fromString(String s) {
		try {
			byte [] data = Base64.getDecoder().decode(new String(s.getBytes("UTF-8")));
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o  = ois.readObject();
			ois.close();
			return (Version)o;
		} catch (Exception e) {
			e.printStackTrace();
			//Activator.getInstance().error("Invalid Version "+s);
			throw new FimetException("Invalid version");
		}
	}
	private static String formatVersion(Long version) {
		if (version == 0L) {
			return "0.0.0";
		}
		String sv = Long.toString(version);
		String v = "";
		for (int i = 0; i < sv.length(); i++) {
			v += sv.charAt(i)+".";
		}
		return v.substring(0,v.length()-1);
	}
	private static String randomString(int length) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < length; i++) {
			s.append(randomAscii());
		}
		return s.toString();
	}
	private static String toUnicode(String src) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			s.append(toUnicode(src.charAt(i)));
		}
		return s.toString();		
	}
	private static String toUnicode(char c) {
		return "\\u" + Integer.toHexString(c | 0x10000).substring(1);
	}
	private static char randomAscii() {
		return (char) (random.nextInt(126-32) + 32);
	}
    private static String toString(Serializable o) {
    	try {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			oos.close();
			return Base64.getEncoder().encodeToString(baos.toByteArray()); 
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
}
