package com.fimet.cfg.enums;


/**
 * The default Parsers
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public enum Parser {

	POS_BANCOMER(0,"POS BBVA"),
	POS_INT_BR(1,"POS BBVA Interredes"),
	POS_INT_BX(2,"POS BMX Interredes"),
	POS_TPV_BR(3,"POS BBVA TPV"),
	POS_TPV_BX(4,"POS BMX TPV"),
	POS_AMEX(5,"POS AMEX"),
	POS_VISA(6,"POS VISA"),
	POS_MASTERCARD(7,"POS MasterCard"),
	POS_NACIONAL(8,"POS Nacional"),
	ATM_BANCOMER(9,"ATM BBVA"),
	ATM_NACIONAL(10,"ATM Nacional"),
	ATM_VISA(11,"ATM VISA"),
	ATM_MASTERCARD(12,"ATM MasterCard"),
	CEL_BBVA(13,"CEL BBVA Adquirente"),
	CEL_ADQUIRA(14,"CEL Adquira Emisor"),
	POS_INT_BR_STRATUS(15,"POS BBVA STRATUS"),
	COR_NACIONAL(16,"COR NACIONAL"),
	POS_DISCOVER(17,"POS DISCOVER"),
	
	POS_EXTRACT_BASE(50,"POS Extract Base"),
	POS_EXTRACT_ADD001(51, "POS Extract Add 1 Puntos Bancomer"),
	POS_EXTRACT_ADD002(52, "POS Extract Add 2 Multipagos"),
	POS_EXTRACT_ADD003(53, "POS Extract Add 3 EMV FULL"),
	POS_EXTRACT_ADD004(54, "POS Extract Add 4 Pagos y Transf. Corres."),
	POS_EXTRACT_ADD005(55, "POS Extract Add 5 Multipagos de Serv. Corres."),
	POS_EXTRACT_ADD006(56, "POS Extract Add 6 Amex & Discover & MC & Visa"),
	POS_EXTRACT_ADD007(57, "POS Extract Add 7 Campañas"),
	POS_EXTRACT_ADD008(58, "POS Extract Add 8 Ecommerce"),
	POS_EXTRACT_ADD009(59, "POS Extract Add 9 DCC Bancomer"),
	POS_EXTRACT_ADD010(60, "POS Extract Add 10 Garanti Bancomer"),
	POS_EXTRACT_ADD011(61, "POS Extract Add 11 Lealtad Banamex"),
	POS_EXTRACT_ADD012(62, "POS Extract Add 12 HCE Adquirente"),
	POS_EXTRACT_ADD013(63, "POS Extract Add 13 HCE Emisor"),
	POS_EXTRACT_ADD014(64, "POS Extract Add 14 Contactless"),
	POS_EXTRACT_ADD015(65, "POS Extract Add 15 Agregadores"),
	POS_EXTRACT_ADD016(66, "POS Extract Add 16 Adq/Iss MasterCard"),
	POS_EXTRACT_ADD017(67, "POS Extract Add 17 Adq Visa"),
	POS_EXTRACT_ADD018(68, "POS Extract Add 18 Versionado TPV Bancomer"),
	POS_EXTRACT_ADD019(69, "POS Extract Add 19"),
	POS_EXTRACT_ADD020(70, "POS Extract Add 20");
	
	
	private int id;
	private String name;
	Parser(int idParser, String parser) {
		this.id = idParser;
	    this.name = parser;
	}
	@Override
	public String toString() {
	    return name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
