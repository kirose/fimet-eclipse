package com.fimet.cfg.enums;


public enum FieldFormatGroup {
	VISA(0, "Visa"),
	MASTERCARD(1, "MasterCard"),
	NACIONAL(2, "Nacional"),
	TPVS(3, "TPVs"),
	NACIONAL_POS(4, "Nacional POS"),
	NACIONAL_ATM(5, "Nacional ATM"),
	NACIONAL_CEL(6, "Nacional CEL"),
	NACIONAL_COR(7, "Nacional COR"),
	VISA_ATM(8, "Visa ATM"),
	VISA_POS(9, "Visa POS"),
	MASTERCARD_ATM(10, "MasterCard ATM"),
	MASTERCARD_POS(11,"MasterCard POS"),
	NACIONAL_POS_STRATUS(12, "Nacional POS Stratus"),
	DISCOVER_POS(13, "Discover POS"),
	
	
	EXTRACT_BASE_POS (50, "Extract Base POS"),
	EXTRACT_ADD001_POS(51, "Extract Add 01 POS Puntos Bancomer"),
	EXTRACT_ADD002_POS(52, "Extract Add 02 POS Multipagos"),
	EXTRACT_ADD003_POS(53, "Extract Add 03 POS EMV FULL"),
	EXTRACT_ADD004_POS(54, "Extract Add 04 POS Pagos y Transf. Corres."),
	EXTRACT_ADD005_POS(55, "Extract Add 05 POS Multipagos de Serv. Corres."),
	EXTRACT_ADD006_POS(56, "Extract Add 06 POS Amex & Discover & MC & Visa"),
	EXTRACT_ADD007_POS(57, "Extract Add 07 POS Campañas"),
	EXTRACT_ADD008_POS(58, "Extract Add 08 POS Ecommerce"),
	EXTRACT_ADD009_POS(59, "Extract Add 09 POS DCC Bancomer"),
	EXTRACT_ADD010_POS(60, "Extract Add 10 POS Garanti Bancomer"),
	EXTRACT_ADD011_POS(61, "Extract Add 11 POS Lealtad Banamex"),
	EXTRACT_ADD012_POS(62, "Extract Add 12 POS HCE Adquirente"),
	EXTRACT_ADD013_POS(63, "Extract Add 13 POS HCE Emisor"),
	EXTRACT_ADD014_POS(64, "Extract Add 14 POS Contactless"),
	EXTRACT_ADD015_POS(65, "Extract Add 15 POS Agregadores"),
	EXTRACT_ADD016_POS(66, "Extract Add 16 POS Adq/Iss MasterCard"),
	EXTRACT_ADD017_POS(67, "Extract Add 17 POS Adq Visa"),
	EXTRACT_ADD018_POS(68, "Extract Add 18 POS Versionado TPV Bancomer"),
	EXTRACT_ADD019_POS(69, "Extract Add 19 POS"),
	EXTRACT_ADD020_POS(70, "Extract Add 20 POS");
	private final int id;
	private final String name;
	FieldFormatGroup(int id, String name){
		this.id =  id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
