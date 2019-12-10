package com.fimet.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fimet.cfg.enums.Parser;
import com.fimet.commons.Version;
import com.fimet.commons.utils.ReflectUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.parser.util.ParserUtils;

public class Main {
	
	private static final Pattern PATTERN_TOKEN = Pattern.compile("(01|Q1|Q2|Q3|Q6|04|C0|C4|C5|ER|ES|ET|EW|EX|EY|EZ|R1|R2|R3|R7|R8|C6|CE|S3|17|20|QS|B2|B3|B4|R0|QF|R4|QR|QO|QP|CZ)[0-9]{5}([0-9]{3})");
	
	public static void main(String[] args) throws Exception {
		//testOrclSYSTEM();
		//testOrclSIINTEGRA();
		//testOrclbcmr_pos_owner();
		//testOrclEG();
		//testFindIap();
		//testOracle();
		//testParsers()
		//testTokens();
		//testLG();
		//testByte();
		testEncrypt();
	}

	private static void testEncrypt() {
		String encrypt = Version.getInstance().encrypt("Bcmrp0s0wn3r21");
		String decrypt = Version.getInstance().decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decrypt);
	}

	private static void testByte() {
		System.out.println(Integer.toHexString(6));
		
		byte[] b = {5,83,81,-124};
		String ascii = new String(b);
		String hex = "";
		for (int i = 0; i < b.length; i++) {System.out.print(b[i]);System.out.print(',');}System.out.println();
		for (int i = 0; i < b.length; i++) {System.out.print((int)(char)b[i]);System.out.print(',');}System.out.println();
		for (int i = 0; i < b.length; i++) {System.out.print(ascii.getBytes()[i]);System.out.print(',');}System.out.println();
		for (int i = 0; i < b.length; i++) {System.out.print((int)(char)ascii.getBytes()[i]);System.out.print(',');}System.out.println();
		
		for (int i = 0; i < b.length; i++) {
			//hex += StringUtils.leftPad(Integer.toHexString(ascii.getBytes()[i]),2,'0');
			hex += String.format("%02X", ascii.getBytes()[i]);
		}
		System.out.println("ASCII:"+ascii);
		System.out.println("HEX:"+hex);
		byte[] bytesAscii = new byte[hex.length()/2];
		for (int i = 0; i < b.length; i++) {
			bytesAscii[i] = (byte)Integer.parseInt(hex.substring(2*i,2*(i+1)),16);	
		}
		System.out.println("ASCII:"+new String(bytesAscii));
		
	}

	private static void testLG() {
		
		//Msg msg = ParserUtils.parseMsgSmart("013549534F30323430303030373730313130333233413834383032453831383030323030303030303030303030303130303030303032303731343033323430373033323030383033323330323037303230373032303730313037393231353538373635303030333733353631323D3232313231393033383030353639363330323831363130304148455245433033202020202020202030323734303736313039202020202020202020202020303030313030303134383431323426203030303036303031323421205131303030303220392021205132303030303220303421204334303030313220303030313130343033303830212030343030303230202020202020202020202020202020202020202020212043303030303236203232313220303031202020202020202020203020203020302020",Parser.DEFAULT.toString());
		//0220
		//Msg msg = ParserUtils.parseMsgSmart("013249534F3032333430303037303032323033323338383430313245383138303032303630303030303030303030313630353030303230373134303434323037303434303038303434313032303730323037303130303231323231353538373635303030333733353631323D32323132313930333530393336323834303539323035303041484552454330332020202020202020303237343037363130392020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203520212051323030303032203034212030343030303230202020202020202020202020202020202020202020212043303030303236203232313220303031202020202020202020203020203020302020212043343030303132203030303131303030333038300A",Parser.DEFAULT.toString());
		//0110
		//Msg msg = ParserUtils.parseMsgSmart("013449534F30323430303030373730313130333233413834383032453831383030323030303030303030303030303031303530303032303731343034323730373034323530383034323730323037303230373032303730313037393231353538373635303030333733353631323D32323132313930333830303536393932303637393736303041484552454330332020202020202020303237343037363130392020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203920212051323030303032203034212043343030303132203030303131303430333038302120303430303032302020202020202020202020202020202020202020202120433030303032362032323132203030312020202020202020202030202030203020200A",Parser.DEFAULT.toString());
		//0110
		//Msg msg = ParserUtils.parseMsgSmart("013449534F30323430303030373730313130333233413834383032453831383030323030303030303030303030303036313231303032303731353531323930393534313630393531323830323037303230373032303730313037393231343535353133333030313130323938373D32323132313930333830303632303536353330383033303050413252454330322020202020202020303237343134303337342020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203030212051323030303032203034212043343030303132203030303131303430333038302120303430303032302020202020202020202020202020202020202020202120433030303032362032323132203030312020202020202020202030202030203020200A",Parser.DEFAULT.toString());
		//0220
		//Msg msg = ParserUtils.parseMsgSmart("013249534F3032333430303037303032323033323338383430313245383138303032303630303030303030303030323930303230303230373135353134313039353432383039353134313032303730323037303130303231323231343535353133333030313130323938373D32323132313930333730303434303731383234313430303050413252454330322020202020202020303237343134303337342020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203520212051323030303032203034212030343030303230202020202020202020202020202020202020202020212043303030303236203232313220303031202020202020202020203020203020302020212043343030303132203030303131303030333038300a",Parser.DEFAULT.toString());
		//0110
		//Msg msg = ParserUtils.parseMsgSmart("013449534F30323430303030373730313130333233413834383032453831383030323030303030303030303030303130303030303032303731343033323430373033323030383033323330323037303230373032303730313037393231353538373635303030333733353631323D32323132313930333830303536393633303238313631303041484552454330332020202020202020303237343037363130392020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203920212051323030303032203034212043343030303132203030303131303430333038302120303430303032302020202020202020202020202020202020202020202120433030303032362032323132203030312020202020202020202030202030203020200A",Parser.DEFAULT.toString());
		//Msg msg = ParserUtils.parseMsgSmart("00A949534F3032343030303037373031313033323341383430303045383138323032303030303030303030303032333939363030303230373134313833343038313831383038313833333032303730323037303230373035313139303338303035373631303534353734343030544F4752454330322020202020202020303237343432363335C2A63F54C2A641C2A620202020202020303030313030303134383430333672C2A6C2A6C2A60A",Parser.INT_BR.toString());
		//Msg msg = ParserUtils.parseMsgSmart("013449534F30323430303030373730313130333233413834383032453831383030323030303030303030303030303032313238353032303731333536303930373536303730373536303830323037303230373032303730313037393231353437373336393030303030363436323D32323132313930333830303536373036303833303031303053414C52454330332020202020202020303237343432353836342020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203020212051323030303032203034212043343030303132203030303131303430333038302120303430303032302020202020202020202020202020202020202020202120433030303032362032323132203030312020202020202020202030202030203020200A",Parser.DEFAULT.toString());
		//Msg msg = ParserUtils.parseMsgSmart("013249534F3032333430303037303032323033323338383430313245383138303032303630303030303030303030333036313831303230373133353633333037353633313037353633333032303730323037303130303231323231353437373336393030303030363436323D2A2A2A2A313930333630393538383531303534343234303053414C52454330332020202020202020303237343432353836342020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203520212051323030303032203034212030343030303230202020202020202020202020202020202020202020212043303030303236202A2A2A2A20303031202020202020202020203020203020302020212043343030303132203030303131303030333038300A",Parser.DEFAULT.toString());
		//Msg msg = ParserUtils.parseMsgSmart("013249534F3032333430303037303032323033323338383430313245383138303032303630303030303030303030333036313831303230373133353633333037353633313037353633333032303730323037303130303231323231353437373336393030303030363436323D32323132313930333630393538383531303534343234303053414C52454330332020202020202020303237343432353836342020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203520212051323030303032203034212030343030303230202020202020202020202020202020202020202020212043303030303236203232313220303031202020202020202020203020203020302020212043343030303132203030303131303030333038300A",Parser.INT_BR.toString());
		//Msg msg = ParserUtils.parseMsgSmart("00B649534F3032343030303037373032333033323341383430303245383138303130303030303030303030303030333036313831303230373133353633333037353633313037353633333032303730323037303230373031303231353437373336393030303030363436323D32323132313930333630393538383531303534343234303053414C524543303320202020202020203032373434323538363420202020202020202020202030303031303030313438343030300A",Parser.INT_BR.toString());
		//Msg msg = ParserUtils.parseMsgSmart("00B649534F3032343030303037373032333033323341383430303245383138303130303030303030303030303030313630353030303230373134303434323037303434303038303434313032303730323037303230373031303231353538373635303030333733353631323D323231323139303335303933363238343035393230353030414845524543303320202020202020203032373430373631303920202020202020202020202030303031303030313438343030300A",Parser.INT_BR.toString());
		//Msg msg = ParserUtils.parseMsgSmart("00B649534F3032343030303037373032333033323341383430303245383138303130303030303030303030303030323930303230303230373135353134313039353432383039353134313032303730323037303230373031303231343535353133333030313130323938373D323231323139303337303034343037313832343134303030504132524543303220202020202020203032373431343033373420202020202020202020202030343732333230313438343030300A",Parser.INT_BR.toString());
		// 0110 Reautorizacion
		//Msg msg = ParserUtils.parseMsgSmart("012C49534F30323334303030373030313030333233383834383132383831383030323030303030303030303030303032313238353032303731333536303930373536303730373536303830323037303230373031303739303231323231353437373336393030303030363436323D3232313231393033383030353637303653414C52454330332020202020202020303237343432353836342020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203920212051323030303032203034212030343030303230202020202020202020202020202020202020202020212043303030303236203232313220303031202020202020202020203020203020302020212043343030303132203030303131303430333038300A",Parser.INT_BR.toString());
		//Msg msg = ParserUtils.parseMsgSmart("00AE49534F303233343030303730303130303332333838363031303838313832303230303030303030303030303233393936303030323037313431383334303831383138303831383333303230373032303730353130303330323132313930333830303537363130544F47524543303220202020202020203032373434323633353920202020202020202020202030303031303030313438343133365F2AC2A6C2A63CC2A6C2A6C2A6C2A6C2A6C2A6C2A6C2A639C2A6C2A6C2A620C2A6C2A60A",Parser.INT_BR.toString());
		//Msg msg = ParserUtils.parseMsgSmart("012C49534F30323334303030373030313030333233383834383132383831383030323030303030303030303030303130303030303032303731343033323430373033323030383033323330323037303230373031303739303231323231353538373635303030333733353631323D3232313231393033383030353639363341484552454330332020202020202020303237343037363130392020202020202020202020203030303130303031343834313234262030303030363030313234212051313030303032203920212051323030303032203034212030343030303230202020202020202020202020202020202020202020212043303030303236203232313220303031202020202020202020203020203020302020212043343030303132203030303131303430333038300A",Parser.INT_BR.toString());
		Message msg;
		msg = (Message)ParserUtils.parseMessage("[T: 07:56:32.983][D: 11186][C:    5477369000006462][Iap: iap_SMART_BT_M1     ][Lp: 0:I19][Rw: R][L:  306]ISO0234000700220323884012E8180020600000000003061810207135633075631075633020702070100212215477369000006462=221219036095885105442400SALREC03        0274425864            00010001484124& 0000600124! Q100002 5 ! Q200002 04! 0400020                     ! C000026 2212 001          0  0 0  ! C400012 000110003080",Parser.POS_INT_BR.toString());
		System.out.println(msg.toJson());
		msg = (Message)ParserUtils.parseMessage("[T: 07:56:32.983][D: 11186][C:    5477369000006462][Iap: iap_SMART_BT_M1     ][Lp: 0:I19][Rw: R][L:  306]ISO0234000700220323884012E8180020600000000003061810207135633075631075633020702070100212215477369000006462=221219036095885105442400SALREC03        0274425864            00010001484124& 0000600124! Q100002 5 ! Q200002 04! 0400020                     ! C000026 2212 001          0  0 0  ! C400012 000110003080",Parser.POS_INT_BR.toString());
		System.out.println(msg.toJson());
	}
	/*public static void testTokens() {
		TransactionLog trn = new TransactionLog();
		//trn.setC63("Q1010000029  Q21200000204 Q601000006000000 C010000026     001          0  9 0   0410000020                     C412000012000000101135 C500000000 C600000000 CE00000000 R100000000 R400000000 ER00000000 ET00000000 EX00000000 ES10000060BNMX00301OQG0012A3  000VXV810323067801  50000000000000000000 EY100001720076A37FDB0D5407C9335588C51416A848EE024815295D95EE2125F15BAF55FB349B3DC28D0B68353074560A7F6A224B956FA859C054AD8759886DAB81738E48CBA00C9AF3EB03019BBE8076044E87B59B0FB59137DA EZ10000098001010000004A10000160000021001053700002635E950540C4F8706C4B4BDA73639B8C74A231D79BADDCE3804E2749DBB EW00000000 B100000000 0100000000 Q800000000 Q900000000 R700000000 R800000000 BM00000000 CH00000000 B000000000 CZ000000400001000000000000000000000000000000000000 QC00000000 B400000000 B600000000 S300000000 QF00000000 QK00000000 QI00000000 QJ00000000 ");
		trn.setC63("Q1010000029  Q21200000209 Q612000006000000 C0100000260000 001     0230050 1 01  0400000000 C412000012102510003663 C500000000 C610000080eb0aa8ce680c82a41619a0a9af28490ac0494a510000010404440549499684464744050000000000 CE00000000 R100000000 R400000000 ER00000000 ET00000000 EX00000000 ES00000000 EY00000000 EZ00000000 EW00000000 B100000000 0100000000 Q800000000 Q900000000 R700000000 R800000000 BM00000000 CH00000000 B000000000 CZ00000000 QC00000000 B400000000 B600000000 S300000000 QF00000000 QK00000000 QI00000000 QJ00000000 ");
		parseTokens(trn);  
	}
	private static void parseTokens(TransactionLog trn) {
		String c63 = trn.getC63();
		if (c63 == null || c63.trim().length() == 0) {
			return;
		}
		Matcher m = PATTERN_TOKEN.matcher(c63);
		int start = 0;
		while (m.find(start)) {
			start = m.end() + Integer.parseInt(m.group(2));
			String value = c63.substring(m.end(), start);
			ReflectUtils.invokeSetter(trn, ReflectUtils.createSetterString("token"+m.group(1)), String.class, value);
		}
		
	}*/
	public static void testFindIap() {
		//List<InterchangeAccessPoint> iaps = InterchangeAccessPointDAO.getInstance().findIAP("172.29.4.157");
		//for (InterchangeAccessPoint iap : iaps) {
			//System.out.println(iap.getName());
		//}
	}
	public static void testOracle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@172.29.40.8:1521:DESA","bcmr_pos_owner","bcmrp0s0wn3r20");
		if (connection != null)
		System.out.println("Successful connection");
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("select * from dual");
		while(rs.next()) {
			String dummy = rs.getString("DUMMY");
			System.out.println("dummy: "+dummy);
		}
		connection.close();
	}
	public static void testParsers() throws IOException {
		
		//TextEditorActionContributor
		//BasicTextEditorActionContributor
		//StatusLineContributionItem
		//sorg.eclipse.core.internal.filesystem.Activator
		//RegistryStrategyOSGI
		//LocalFileSystem
		//FileDocumentProvider
		//TextFileDocumentProvider
		//VirtualFileSystem
		//org.eclipse.ui.navigator.CommonNavigator
		//org.eclipse.ui.navigator.resources.ProjectExplorer
		//FileInputAdapterFactory
		//FileEditorInputAdapterFactory
		//ILocationProvider
		//FileEditorInputFactory
		//FileStoreEditorInputFactory
		//org.eclipse.core.filesystem.EFS. 
		//org.eclipse.ui.ide.IDE.
		//org.eclipse.core.filesystem.FileSystemCore.getFileSystem(
		
		//ResourceTextFileBuffer
		//FileInputStream fio;
		//fio = new FileInputStream(new File("C:\\ws\\wsPlugins\\com.eg.test\\META-INF/format2.exc.IntBr.sim_queue"));
		//System.out.println(new IntBrMsgIsoParser().parse(fio,true).toJson());
		//fio.close();
//		fio = new FileInputStream(new File("C:\\ws\\wsPlugins\\com.eg.test\\META-INF\\TPV_MOVI_BX_01_M2.tpvbr.sim_queue"));
//		System.out.println(new TpvBrMsgParser(true).parse(fio).toJson());
//		fio.close();
		//fio = new FileInputStream(new File("C:/Users/Marco Antonio/Documents/eglobal/tramas/intBR_BR_15_MOTO2.IntBr.sim_queue"));
		//FileInputStream fr = new FileInputStream(new File("C:\\ws\\wsPlugins\\com.eg.test\\META-INF/format2.exc.IntBr.sim_queue"));
		/*byte[] bytes = new byte[1024];
		StringBuilder sb = new StringBuilder();
		int nom = 0;
		while ((nom = fr.read(bytes)) > 0) {
			for (int i = 0; i < nom; i++) {
				sb.append((char)bytes[i]);	
			}			
		}*/
		FileInputStream fr = new FileInputStream(new File("C:\\Users\\Marco Antonio\\Documents\\eglobal\\tramas\\intBR_BR_15_MOTO2.IntBr.sim_queue"));
		Message msg;
		//msg = ParserUtils.parseMsgFromIso(fr, Parser.INT_BR, true);
		//System.out.println(msg.toJson());
		
		
		fr.close();
		
		
		//String json = "{\"parser\":\"IntBr\",\"exclusive\":true,\"iso\":\"ISO023400070\",\"mti\":\"0421\",\"fields\":{\"3\":\"000000\",\"4\":\"000000017500\",\"7\":\"1218131924\",\"11\":\"612451\",\"12\":\"131924\",\"13\":\"1218\",\"15\":\"1218\",\"17\":\"1218\",\"18\":\"8999\",\"22\":\"051\",\"23\":\"001\",\"32\":\"12\",\"35\":\"4101773000653800=22102210000064201001\",\"37\":\"000001692093\",\"38\":\"177484\",\"39\":\"00\",\"41\":\"00031196        \",\"43\":\"OPENPAY*DESARROLMOTO15Hermosillo   SONMX\",\"48\":\"4623948            00000000\",\"49\":\"484\",\"55\":{\"55.5F2A\":\"0484\",\"55.82\":\"1C00\",\"55.84\":\"A0000000032010\",\"55.95\":\"8080008800\",\"55.9A\":\"180514\",\"55.9C\":\"00\",\"55.9F02\":\"000000017500\",\"55.9F03\":\"000000000000\",\"55.9F09\":\"0084\",\"55.9F10\":\"06010A03A4A800\",\"55.9F1A\":\"0484\",\"55.9F1E\":\"3330303630353337\",\"55.9F26\":\"44BC25A108E173EC\",\"55.9F27\":\"80\",\"55.9F33\":\"E0B0C8\",\"55.9F34\":\"010302\",\"55.9F35\":\"22\",\"55.9F36\":\"0032\",\"55.9F37\":\"9D8B7CBF\",\"55.9F41\":\"00000067\",\"55.9F53\":\"52\"},\"62\":\"83100     \",\"63\":{\"63.Q1\":\"9 \",\"63.Q2\":\"08\",\"63.C4\":\"203300001082\",\"63.QF\":\"0000001234503PSM            \",\"63.04\":\"                   2\",\"63.C0\":\"     001          1  0 0 0\"},\"90\":\"02500000016920930531163609000608          \"}}";
		//String json = "{\"parser\":\"IntBr\",\"exclusive\":true,\"iso\":\"ISO023400070\",\"mti\":\"0421\",\"fields\":{\"3\":\"000000\",\"4\":\"000000017500\",\"7\":\"1218131924\",\"11\":\"612451\",\"12\":\"131924\",\"13\":\"1218\",\"15\":\"1218\",\"17\":\"1218\",\"18\":\"8999\",\"22\":\"051\",\"23\":\"001\",\"32\":\"12\",\"35\":\"4101773000653800=22102210000064201001\",\"37\":\"000001692093\",\"38\":\"177484\",\"39\":\"00\",\"41\":\"00031196        \",\"43\":\"OPENPAY*DESARROLMOTO15Hermosillo   SONMX\",\"48\":\"4623948            00000000\",\"49\":\"484\",\"55\":{\"55.5F2A\":\"0484\",\"55.82\":\"1C00\",\"55.84\":\"A0000000032010\",\"55.95\":\"8080008800\",\"55.9A\":\"180514\",\"55.9C\":\"00\",\"55.9F02\":\"000000017500\",\"55.9F03\":\"000000000000\",\"55.9F09\":\"0084\",\"55.9F10\":\"06010A03A4A800\",\"55.9F1A\":\"0484\",\"55.9F1E\":\"3330303630353337\",\"55.9F26\":\"44BC25A108E173EC\",\"55.9F27\":\"80\",\"55.9F33\":\"E0B0C8\",\"55.9F34\":\"010302\",\"55.9F35\":\"22\",\"55.9F36\":\"0032\",\"55.9F37\":\"3F8B7CBF\",\"55.9F41\":\"00000067\",\"55.9F53\":\"52\"},\"62\":\"83100     \",\"63\":{\"63.Q1\":\"9 \",\"63.Q2\":\"08\",\"63.C4\":\"203300001082\",\"63.QF\":\"0000001234503PSM            \",\"63.04\":\"                   2\",\"63.C0\":\"     001          1  0 0 0\"},\"90\":\"02500000016920930531163609000608          \"}}";
		//String json = "{\"parser\":\"IntBr\",\"exclusive\":true,\"iso\":\"ISO023400070\",\"mti\":\"0421\",\"fields\":{\"3\":\"000000\",\"4\":\"000000017500\",\"7\":\"1218131924\",\"11\":\"612451\",\"12\":\"131924\",\"13\":\"1218\",\"15\":\"1218\",\"17\":\"1218\",\"18\":\"8999\",\"22\":\"051\",\"23\":\"001\",\"32\":\"12\",\"35\":\"4101773000653800=22102210000064201001\",\"37\":\"000001692093\",\"38\":\"177484\",\"39\":\"00\",\"41\":\"00031196        \",\"43\":\"BRMNPAY*DESARROLMOTO15Hermosillo   SONMX\",\"48\":\"4623948            00000000\",\"49\":\"484\",\"55\":{\"55.5F2A\":\"0484\",\"55.82\":\"1C00\",\"55.84\":\"A0000000032010\",\"55.95\":\"8080008800\",\"55.9A\":\"180514\",\"55.9C\":\"00\",\"55.9F02\":\"000000017500\",\"55.9F03\":\"000000000000\",\"55.9F09\":\"0084\",\"55.9F10\":\"06010A03A4A800\",\"55.9F1A\":\"0484\",\"55.9F1E\":\"3330303630353337\",\"55.9F26\":\"44BC25A108E173EC\",\"55.9F27\":\"80\",\"55.9F33\":\"E0B0C8\",\"55.9F34\":\"010302\",\"55.9F35\":\"22\",\"55.9F36\":\"0032\",\"55.9F37\":\"9D8B7CBF\",\"55.9F41\":\"00000067\",\"55.9F53\":\"52\"},\"62\":\"83100     \",\"63\":{\"63.Q1\":\"9 \",\"63.Q2\":\"08\",\"63.C4\":\"203300001082\",\"63.QF\":\"0000001234503PSM            \",\"63.04\":\"                   2\",\"63.C0\":\"     001          1  0 0 0\"},\"90\":\"02500000016920930531163609000608          \"}}";
		/*String json = "{\n	\"parser\":\"IntBr\",\n	\"exclusive\":true,\n	\"iso\":\"ISO023400070\",\n	\"mti\":\"0420\",\n	\"fields\":{\n		\"3\":\"000000\",\n		\"4\":\"000000017500\",\n		\"7\":\"1218131924\",\n		\"11\":\"612451\",\n		\"12\":\"131924\",\n		\"13\":\"1218\",\n		\"15\":\"1218\",\n		\"17\":\"1218\",\n		\"18\":\"8999\",\n		\"22\":\"051\",\n		\"23\":\"001\",\n		\"32\":\"12\",\n		\"35\":\"4101773000653800=22102210000064201001\",\n		\"37\":\"000001692093\",\n		\"38\":\"177484\",\n		\"39\":\"00\",\n		\"41\":\"00031196        \",\n		\"43\":\"BRMNPAY*DESARROLMOTO15Hermosillo   SONMX\",\n		\"48\":\"4623948            00000000\",\n		\"49\":\"484\",\n		\"55\":{\n			\"55.5F2A\":\"0484\",\n			\"55.82\":\"1C00\",\n			\"55.84\":\"A0000000032010\",\n			\"55.95\":\"8080008800\",\n			\"55.9A\":\"180514\",\n			\"55.9C\":\"00\",\n			\"55.9F02\":\"000000017500\",\n			\"55.9F03\":\"000000000000\",\n			\"55.9F09\":\"0084\",\n			\"55.9F10\":\"06010A03A4A800\",\n			\"55.9F1A\":\"0484\",\n			\"55.9F1E\":\"3330303630353337\",\n			\"55.9F26\":\"44BC25A108E173EC\",\n			\"55.9F27\":\"80\",\n			\"55.9F33\":\"E0B0C8\",\n			\"55.9F34\":\"010302\",\n			\"55.9F35\":\"22\",\n			\"55.9F36\":\"0032\",\n			\"55.9F37\":\"3F8B7CBF\",\n			\"55.9F41\":\"00000067\",\n			\"55.9F53\":\"52\"\n		},\n		\"62\":\"83100     \",\n		\"63\":{\n			\"63.Q1\":\"9 \",\n			\"63.Q2\":\"08\",\n			\"63.C4\":\"203300001082\",\n			\"63.QF\":\"0000001234503PSM            \",\n			\"63.04\":\"                   2\",\n			\"63.C0\":\"     001          1  0 0 0\"\n		},\n		\"90\":\"02500000016920930531163609000608          \"\n	}\n}";
		Msg msg = new MsgJsonParser().parse(json);
		System.out.println(msg);
		System.out.println(msg.toJson());
		IntBrMsgIsoFormater formater = new IntBrMsgIsoFormater();

		String format = formater.format(msg);
		String msgFile = sb.toString();
		
		String formatHex = ParserUtils.byteArrayAsHexString(format.getBytes());
		String msgFileHex = ParserUtils.byteArrayAsHexString(msgFile.getBytes());
		System.out.println("Hola\n");

		System.out.println(formatHex);
		System.out.println(msgFileHex);
		System.out.println(format);
		System.out.println(msgFile);*/
		
		//fio.close();
	}
	public static void testOrclSYSTEM() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","SYSTEM","Fokeuler27182818$");
		if (connection != null)
		System.out.println("Successful connection");
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("select * from dual");
		while(rs.next()) {
			String dummy = rs.getString("DUMMY");
			System.out.println("dummy: "+dummy);
		}
		connection.close();
		System.out.println("closed");
	}
	public static void testOrclSIINTEGRA() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","SIINTEGRA","0AfX2#DD%X!");
		if (connection != null)
		System.out.println("Successful connection");
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("select * from dual");
		while(rs.next()) {
			String dummy = rs.getString("DUMMY");
			System.out.println("dummy: "+dummy);
		}
		connection.close();
		System.out.println("closed");
	}
	public static void testOrclEG() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","EGLOBAL","0AfX2#DD%X!");
		if (connection != null)
		System.out.println("Successful connection");
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery("select * from dual");
		while(rs.next()) {
			String dummy = rs.getString("DUMMY");
			System.out.println("dummy: "+dummy);
		}
		connection.close();
		System.out.println("closed");
	}
	public static void testOrclbcmr_pos_owner() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.7:1521:orcl","bcmr_pos_owner","bcmrp0s0wn3r20");
			if (connection != null)
			System.out.println("Successful connection");
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery("select * from TRANSACTION_LOG");
			while(rs.next()) {
				String timeStamp = rs.getString("TRL_SYSTEM_TIMESTAMP");
				System.out.println("timeStamp: "+timeStamp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection.close();
			System.out.println("closed");
		}
	}
}
