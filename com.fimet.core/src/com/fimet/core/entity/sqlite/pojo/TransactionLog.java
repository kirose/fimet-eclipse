package com.fimet.core.entity.sqlite.pojo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionLog {

	private static final Pattern PATTERN_TOKEN = Pattern.compile("(01|Q1|Q2|Q3|Q4|Q5|Q6|04|C0|C4|C5|ER|ES|ET|EW|EX|EY|EZ|R1|R2|R3|R7|R8|C6|CE|S3|17|20|QS|B0|B1|B2|B3|B4|R0|QF|R4|QR|QO|QP|CZ)[0-9]{5}([0-9]{3})");
	
	private String id;
	private String timestamp;
	private String resultCode;
	private String resultCodeComment;
	private String actionCode;
	private String on2;
	private String mti;
	private String iapAcquirer;
	private String iapIssuer;
	private String proccesingCode;// Field 3
	private String amount;// Field 4
	private String merchantType;//Field 18 Giro
	private String pan;
	private String pem;
	private String approbalCode;
	private String authorizationCode;
	private String affiliation;// Field
	private String c63;// Field 53
	private Map<String, String> tokens;
	private Map<String, String> tags;
	private String pemIss;
	private String errorIss;

	public TransactionLog() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultCodeComment() {
		return resultCodeComment;
	}
	public void setResultCodeComment(String resultCodeComment) {
		this.resultCodeComment = resultCodeComment;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOn2() {
		return on2;
	}
	public void setOn2(String on2) {
		this.on2 = on2;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getIapAcquirer() {
		return iapAcquirer;
	}
	public void setIapAcquirer(String iapAcquirer) {
		this.iapAcquirer = iapAcquirer;
	}
	public String getIapIssuer() {
		return iapIssuer;
	}
	public void setIapIssuer(String iapIssuer) {
		this.iapIssuer = iapIssuer;
	}
	public String getProccesingCode() {
		return proccesingCode;
	}
	public void setProccesingCode(String proccesingCode) {
		this.proccesingCode = proccesingCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getPem() {
		return pem;
	}
	public void setPem(String pem) {
		this.pem = pem;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getApprobalCode() {
		return approbalCode;
	}
	public void setApprobalCode(String approbalCode) {
		this.approbalCode = approbalCode;
	}
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	public String getC63() {
		return c63;
	}
	public void setC63(String c63) {
		this.c63 = c63;
	}
	public String getPemIss() {
		return pemIss;
	}
	public void setPemIss(String pemIss) {
		this.pemIss = pemIss;
	}
	public String getErrorIss() {
		return errorIss;
	}
	public void setErrorIss(String errorIss) {
		this.errorIss = errorIss;
	}
	public Map<String, String> getTokens() {
		return tokens;
	}
	public Map<String, String> getTags() {
		return tags;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public void parseTokens() {
		if (c63 == null || c63.trim().length() == 0 || tokens != null) {
			return;
		}
		tokens = new LinkedHashMap<>();
		Matcher m = PATTERN_TOKEN.matcher(c63);
		int start = 0;
		String value;
		int ln = c63.length();
		while (start < ln && m.find(start)) {
			start = m.end() + Integer.parseInt(m.group(2));
			if (start > m.end()) {
				value = c63.substring(m.end(), start >= ln ? ln -1 : start);
				tokens.put(m.group(1), value);
			}
		}
	}
	public void parseTAGS() {
		if (c63 == null || c63.trim().length() == 0 || tags != null) {
			return;
		}
		tags = new LinkedHashMap<>();
	}
	@Override
	public String toString() {
		return "timestamp:" + timestamp +
				",\nresultCode:" + resultCode +
				",\nresultCodeComment:" + resultCodeComment +
				",\nactionCode:" + actionCode +
				",\non2:" + on2 +
				",\nmti:" + mti +
				",\niapAcquirer:" + iapAcquirer +
				",\niapIssuer:" + iapIssuer +
				",\nproccesingCode:" + proccesingCode +
				",\namount:" + amount +
				",\nmerchantType:" + merchantType +
				",\npan:" + pan +
				",\npem:" + pem +
				",\napprobalCode:" + approbalCode +
				",\naffiliation:" + affiliation +
				",\nc63:" + c63 +
				",\npemIss:" + pemIss +
				",\nerrorIss:" + errorIss;
	}
	
	
}
