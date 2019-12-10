package com.fimet.core.entity.sqlite.pojo;

public class TransactionParams {

	private String pan;
	private String timestampLE;
	private String timestampGE;
	private String iapAcquirer;
	private String iapIssuer;
	private String merchantType;
	private String mti;
	private String amountLE;
	private String amountGE;
	private String limit;
	
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getTimestampLE() {
		return timestampLE;
	}
	public void setTimestampLE(String timestampLE) {
		this.timestampLE = timestampLE;
	}
	public String getTimestampGE() {
		return timestampGE;
	}
	public void setTimestampGE(String timestampGE) {
		this.timestampGE = timestampGE;
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
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getAmountLE() {
		return amountLE;
	}
	public void setAmountLE(String amountLE) {
		this.amountLE = amountLE;
	}
	public String getAmountGE() {
		return amountGE;
	}
	public void setAmountGE(String amountGE) {
		this.amountGE = amountGE;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
}