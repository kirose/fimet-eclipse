package com.fimet.core.entity.sqlite.pojo;

public class IapParameters {
	public static char INSERT = 'I';
	public static char UPDATE = 'U';
	public static char NONE = 'N';
	private Long idMachineSrc;
	private String iapSrc;
	private String queueSrc;
	private String processSrc;
	private String interchangeSrc;
	private String serverInterchangeSrc;
	private String logicalSrc;
	private String physicalSrc;
	private Long idDispatcherSrc;
	private Long idMachineTgt;
	private String iapTgt;
	private String queueTgt;
	private String processTgt;
	private String interchangeTgt;
	private String serverInterchangeTgt;
	private String logicalTgt;
	private String physicalTgt;
	private Long idDispatcherTgt;
	private char iapAction;
	private char queueAction;
	private char processAction;
	private char interchangeAction;
	private char serverInterchangeAction;
	private char logicalAction;
	private char physicalAction;
	private char dispatcherAction;
	public IapParameters() {
		super();
	}
	public Long getIdMachineSrc() {
		return idMachineSrc;
	}
	public void setIdMachineSrc(Long idMachineSrc) {
		this.idMachineSrc = idMachineSrc;
	}
	public String getIapSrc() {
		return iapSrc;
	}
	public void setIapSrc(String iapNameSrc) {
		this.iapSrc = iapNameSrc;
	}
	public String getQueueSrc() {
		return queueSrc;
	}
	public void setQueueSrc(String queueSrc) {
		this.queueSrc = queueSrc;
	}
	public String getProcessSrc() {
		return processSrc;
	}
	public void setProcessSrc(String processSrc) {
		this.processSrc = processSrc;
	}
	public String getInterchangeSrc() {
		return interchangeSrc;
	}
	public void setInterchangeSrc(String interchangeSrc) {
		this.interchangeSrc = interchangeSrc;
	}
	public String getLogicalSrc() {
		return logicalSrc;
	}
	public void setLogicalSrc(String logicalSrc) {
		this.logicalSrc = logicalSrc;
	}
	public String getPhysicalSrc() {
		return physicalSrc;
	}
	public void setPhysicalSrc(String physicalSrc) {
		this.physicalSrc = physicalSrc;
	}
	public Long getIdMachineTgt() {
		return idMachineTgt;
	}
	public void setIdMachineTgt(Long idMachineTgt) {
		this.idMachineTgt = idMachineTgt;
	}
	public String getIapTgt() {
		return iapTgt;
	}
	public void setIapTgt(String iapNameTgt) {
		this.iapTgt = iapNameTgt;
	}
	public String getQueueTgt() {
		return queueTgt;
	}
	public void setQueueTgt(String queueTgt) {
		this.queueTgt = queueTgt;
	}
	public String getProcessTgt() {
		return processTgt;
	}
	public void setProcessTgt(String processTgt) {
		this.processTgt = processTgt;
	}
	public String getInterchangeTgt() {
		return interchangeTgt;
	}
	public void setInterchangeTgt(String interchangeTgt) {
		this.interchangeTgt = interchangeTgt;
	}
	public String getLogicalTgt() {
		return logicalTgt;
	}
	public void setLogicalTgt(String logicalTgt) {
		this.logicalTgt = logicalTgt;
	}
	public String getPhysicalTgt() {
		return physicalTgt;
	}
	public void setPhysicalTgt(String physicalTgt) {
		this.physicalTgt = physicalTgt;
	}
	public char getIapAction() {
		return iapAction;
	}
	public void setIapAction(char iapAction) {
		this.iapAction = iapAction;
	}
	public char getQueueAction() {
		return queueAction;
	}
	public void setQueueAction(char queueAction) {
		this.queueAction = queueAction;
	}
	public char getProcessAction() {
		return processAction;
	}
	public void setProcessAction(char processAction) {
		this.processAction = processAction;
	}
	public char getInterchangeAction() {
		return interchangeAction;
	}
	public void setInterchangeAction(char interchangeAction) {
		this.interchangeAction = interchangeAction;
	}
	public char getLogicalAction() {
		return logicalAction;
	}
	public void setLogicalAction(char logicalAction) {
		this.logicalAction = logicalAction;
	}
	public char getPhysicalAction() {
		return physicalAction;
	}
	public void setPhysicalAction(char physicalAction) {
		this.physicalAction = physicalAction;
	}
	public String getServerInterchangeSrc() {
		return serverInterchangeSrc;
	}
	public void setServerInterchangeSrc(String serverInterchangeSrc) {
		this.serverInterchangeSrc = serverInterchangeSrc;
	}
	public String getServerInterchangeTgt() {
		return serverInterchangeTgt;
	}
	public void setServerInterchangeTgt(String serverInterchangeTgt) {
		this.serverInterchangeTgt = serverInterchangeTgt;
	}
	public char getServerInterchangeAction() {
		return serverInterchangeAction;
	}
	public void setServerInterchangeAction(char serverInterchangeAction) {
		this.serverInterchangeAction = serverInterchangeAction;
	}
	public Long getIdDispatcherSrc() {
		return idDispatcherSrc;
	}
	public void setIdDispatcherSrc(Long idDispatcherSrc) {
		this.idDispatcherSrc = idDispatcherSrc;
	}
	public Long getIdDispatcherTgt() {
		return idDispatcherTgt;
	}
	public void setIdDispatcherTgt(Long idDispatcherTgt) {
		this.idDispatcherTgt = idDispatcherTgt;
	}
	public char getDispatcherAction() {
		return dispatcherAction;
	}
	public void setDispatcherAction(char dispatcherAction) {
		this.dispatcherAction = dispatcherAction;
	}
}
