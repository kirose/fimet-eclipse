package com.fimet.core.impl.swt.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fimet.commons.Version;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.Activator;
import com.fimet.core.entity.sqlite.DataBase;

public class ImporterSqlDeveloper implements IDataBaseImporter {

	@Override
	public List<DataBase> parse(File file, String key) {
		try {
			return parse(new FileInputStream(file), key);
		} catch (IOException e) {
			if (Activator.getInstance() != null) {
				Activator.getInstance().warning("Cannot found file: "+file, e);
			} else {
				e.printStackTrace();
			}
		} catch (GeneralSecurityException e) {
			if (Activator.getInstance() != null) {
				Activator.getInstance().warning("Invalid Key: "+key+" for file "+file, e);
			} else {
				e.printStackTrace();
			}
		} catch (Exception e) {
			if (Activator.getInstance() != null) {
				Activator.getInstance().warning("Malformed file: "+file, e);
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}
	public List<DataBase> parse(String xml, String key) throws ParserConfigurationException, SAXException, IOException, GeneralSecurityException {
		return parse(new java.io.ByteArrayInputStream(xml.getBytes()), key);
	}
	public List<DataBase> parse(InputStream stream, String key) throws ParserConfigurationException, SAXException, IOException, GeneralSecurityException {
		List<DataBase> dataBases = new ArrayList<DataBase>();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(stream);
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		if (!"References".equals(root.getNodeName())) {
			throw new ParserException("Invalid xml connections, expected node References insted found: "+root.getNodeName());
		}
		NodeList children = root.getChildNodes();
		int ln = children.getLength();
		DataBase dataBase;
		for (int i = 0;	i < ln; i++) {
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE && "Reference".equals(node.getNodeName()) && (dataBase = parseDataBase(node, key))!=null) {
				dataBases.add(dataBase);
			}
		}
		return dataBases;
	}
	private DataBase parseDataBase(Node node, String key) throws GeneralSecurityException {
		
		if (!node.hasChildNodes()) return null;
		Node refAddresses = findNode(node.getChildNodes(), "RefAddresses");
		if (refAddresses == null) return null;
		if (!refAddresses.hasChildNodes()) return null;
		
		NodeList children = refAddresses.getChildNodes();
		
		String name = getContents(findNode(children, "StringRefAddr", "addrType", "ConnName"));
		if (name == null) return null;
		String sid = getContents(findNode(children, "StringRefAddr", "addrType", "sid"));
		if (sid == null) return null;
		String hostname = getContents(findNode(children, "StringRefAddr", "addrType", "hostname"));
		if (hostname == null) return null;
		String password = getContents(findNode(children, "StringRefAddr", "addrType", "password"));
		if (password == null) return null;
		String port = getContents(findNode(children, "StringRefAddr", "addrType", "port"));
		if (port == null) return null;
		String user = getContents(findNode(children, "StringRefAddr", "addrType", "user"));
		if (user == null) return null;

		DataBase dataBase = new DataBase();
		dataBase.setName(name);
		dataBase.setAddress(hostname);
		dataBase.setSid(sid);
		dataBase.setPort(port);
		dataBase.setUser(user);
		String decrypt = decrypt(password,key);
		dataBase.setPassword(decrypt != null ? Version.getInstance().encrypt(decrypt) : null);
		dataBase.setIsValid(Boolean.FALSE);
		return dataBase;
	}
	private Node findNode(NodeList list, String name) {
		int ln = list.getLength();
		int i = 0;
		Node node;
		while (i < ln) {
			if ((node = list.item(i++)).getNodeType() == Node.ELEMENT_NODE && name.equals(node.getNodeName())) {
				return node;
			}
		}
		return null;
	}
	private Node findNode(NodeList list, String name, String attribute, String attributeValue) {
		int ln = list.getLength();
		int i = 0;
		Node node;
		while (i < ln) {
			if ((node = list.item(i++)).getNodeType() == Node.ELEMENT_NODE &&
					name.equals(node.getNodeName()) &&
					attributeValue.equals(findAttribute(node,attribute))) {
				return node;
			}
		}
		return null;
	}
	private String findAttribute(Node node, String name) {
		if (!node.hasAttributes()) return null;
		Node attribute = node.getAttributes().getNamedItem(name);
		if (attribute == null) return null;
		return attribute.getNodeValue();
	}
	public String getContents(Node node) {
		if (node == null) return null;
		if (!node.hasChildNodes()) return null;
		Node contents = findNode(node.getChildNodes(), "Contents");
		if (contents == null) return null;
		return getContent(contents);
	}
	private String getContent(Node node) {
		if (!node.hasChildNodes()) return null;
		return node.getChildNodes().item(0).getNodeValue();
	}
	private byte[] des_cbc_decrypt(byte[] encrypted_password, byte[] decryption_key, byte[] iv) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryption_key, "DES"), new IvParameterSpec(iv));
		return cipher.doFinal(encrypted_password);
	}
	private byte[] doKey(byte[] db_system_id) throws NoSuchAlgorithmException {
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
	private byte[] decrypt_v4(byte[] encrypted, byte[] db_system_id) throws GeneralSecurityException {

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
	public String decrypt(String encrypted, String key) throws GeneralSecurityException {
		byte[] decrypted = decrypt_v4(encrypted.getBytes(), key.getBytes());
		return new String(decrypted);
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, GeneralSecurityException {
		System.out.println(new ImporterSqlDeveloper().parse("","123qwe!\"#QWE"));
		System.out.println(new ImporterSqlDeveloper().parse(new File("C:\\eclipse\\wsplugins\\connections.xml"),"123qwe!\"#QWE"));
	}
	static String xml = "<?xml version = '1.0' encoding = 'UTF-8'?>\n"+
			"<References xmlns=\"http://xmlns.oracle.com/adf/jndi\">"+
			   "<Reference name=\"LOCAL_BR\" className=\"oracle.jdeveloper.db.adapter.DatabaseProvider\" xmlns=\"\">"+
			      "<Factory className=\"oracle.jdevimpl.db.adapter.DatabaseProviderFactory1212\"/>"+
			      "<RefAddresses>"+
			         "<StringRefAddr addrType=\"role\">"+
			            "<Contents/>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"SavePassword\">"+
			            "<Contents>true</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"OracleConnectionType\">"+
			            "<Contents>BASIC</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"RaptorConnectionType\">"+
			            "<Contents>Oracle</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"Connection-Color-For-Editors\">"+
			            "<Contents>-44462</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"sid\">"+
			            "<Contents>DESA</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"ExportPasswordMode\">"+
			            "<Contents>Key</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"customUrl\">"+
			            "<Contents>jdbc:oracle:thin:@172.29.40.8:1521:DESA</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"oraDriverType\">"+
			            "<Contents>thin</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"NoPasswordConnection\">"+
			            "<Contents>TRUE</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"password\">"+
			            "<Contents>rs6Q2YeZ/ECOjoYXtMGXtA==</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"hostname\">"+
			            "<Contents>172.29.40.8</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"driver\">"+
			            "<Contents>oracle.jdbc.OracleDriver</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"port\">"+
			            "<Contents>1521</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"subtype\">"+
			            "<Contents>oraJDBC</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"OS_AUTHENTICATION\">"+
			            "<Contents>false</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"ConnName\">"+
			            "<Contents>LOCAL_BR</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"KERBEROS_AUTHENTICATION\">"+
			            "<Contents>false</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"user\">"+
			            "<Contents>bcmr_pos_owner</Contents>"+
			         "</StringRefAddr>"+
			         "<StringRefAddr addrType=\"ExportKeyChecksum\">"+
			            "<Contents>FkeuzZu8t7inuImOtE8cFw==</Contents>"+
			         "</StringRefAddr>"+
			      "</RefAddresses>"+
			   "</Reference>"+
			"</References>";
}
