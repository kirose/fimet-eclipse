package com.fimet.core.entity.sqlite.pojo;

public class MessageIsoParameters {
	private Integer idTypeEnviroment;
	private Integer idParser;
	private Short type;
	private String mti;
	private String processingCode;
	private String merchant;
	private String name;
	private Boolean asc;
	public Integer getIdTypeEnviroment() {
		return idTypeEnviroment;
	}
	public void setIdTypeEnviroment(Integer idEnviroment) {
		this.idTypeEnviroment = idEnviroment;
	}
	public Integer getIdParser() {
		return idParser;
	}
	public void setIdParser(Integer idParser) {
		this.idParser = idParser;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getProcessingCode() {
		return processingCode;
	}
	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getAsc() {
		return asc;
	}
	public void setAsc(Boolean asc) {
		this.asc = asc;
	}
}
