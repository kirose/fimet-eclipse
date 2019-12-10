package com.fimet.core.usecase;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.FimetException;
import com.fimet.core.IExtract;
import com.fimet.core.IUseCase;
import com.fimet.core.IValidator;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.pojo.Notice;
import com.fimet.core.entity.sqlite.pojo.Validation;
import com.fimet.core.json.adapter.JsonAdapterFactory;
//import com.fimet.core.json.adapter.UseCaseAdapter;
import com.fimet.core.net.ISocket;
//import com.google.gson.annotations.JsonAdapter;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 * <br/>
 * This class modelate a useCase
 * UseCase<br/>
 * 	Aquirer<br/>
 * 		IssuerRequest<br/>
 * 			Message<br/>
 * 		IssuerResponse<br/>
 * 			Message<br/>
 * 	Issuer<br/>
 * 		IssuerRequest<br/>
 * 			Message<br/>
 * 		IssuerResponse<br/>
 * 			Message<br/>
 *	
 */
//@JsonAdapter(UseCaseAdapter.class)
public final class UseCase implements Cloneable, Serializable, IUseCase {
	
	public static final String TIMESTAMP = "Timestamp";
	public static final String ID_EXTRACT = "IdExtract";
	public static final String ERROR_ON2 = "ErrorOn2";
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String timestamp;
	private String name;
	private Acquirer acquirer;
	private Issuer issuer;
	private IResource resource;
	private IFolder folder;
	private Integer executionTime;
	private String validatorClass;
	private IValidator validator;
	private Validations validations;
	private List<Validation> validationResults;
	private List<Notice> notices;
	private IExtract extract;
	
	public UseCase(ISocket acquirer, ISocket issuer) {
		this();
		this.acquirer = new Acquirer(acquirer, new AcquirerRequest(new Message()));
		this.issuer = new Issuer(issuer);
	}
	public UseCase() {
		super();
		this.acquirer = new Acquirer();
	}
	public IResource getResource() {
		return resource;
	}
	public void setResource(IResource resource) {
		this.resource = resource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IFolder getFolder() {
		return folder;
	}
	public void setFolder(IFolder folder) {
		this.folder = folder;
	}
	public String toJson() {
		return JsonAdapterFactory.GSON.toJson(this);
	}
	public Acquirer getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(Acquirer acquirer) {
		this.acquirer = acquirer;
	}
	public Issuer getIssuer() {
		return issuer;
	}
	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}
	public Integer getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Integer executionTime) {
		this.executionTime = executionTime;
	}
	public UseCase clone() throws CloneNotSupportedException {
		try {
			UseCase uc = (UseCase) super.clone();
			if (acquirer != null)
				uc.setAcquirer(acquirer.clone());
			if (issuer != null)
				uc.setIssuer(issuer.clone());
			return uc;
		} catch (CloneNotSupportedException e) {
			throw new FimetException(e);
		}
	}
	public String getValidatorClass() {
		return validatorClass;
	}
	public void setValidatorClass(String validatorClass) {
		this.validatorClass = validatorClass;
	}
	public IValidator getValidator() {
		return validator;
	}
	public void setValidator(IValidator validator) {
		this.validator = validator;
	}
	public List<Validation> getValidationResults() {
		return validationResults;
	}
	public void setValidationResults(List<Validation> validations) {
		this.validationResults = validations;
	}
	public void addValidationResult(Validation validation) {
		if (validationResults == null) {
			validationResults = new ArrayList<>();
		}
		validationResults.add(validation);
	}
	public Validations getValidations() {
		return validations;
	}
	public void setValidations(Validations validations) {
		this.validations = validations;
	}
	public boolean hasValidations() {
		return validations != null;
	}
	public boolean hasValidationsAcquirerResponse() {
		return validations != null && validations.getResponse() != null && !validations.getResponse().isEmpty();
	}
	public boolean hasValidationsIssuerRequest() {
		return validations != null && validations.getRequest() != null && !validations.getRequest().isEmpty();
	}
	public boolean hasValidationsExtract() {
		return validations != null && validations.getExtract() != null && !validations.getExtract().isEmpty();
	}
	public List<com.fimet.core.usecase.Validation> getValidationsAcquirerResponse() {
		return hasValidationsAcquirerResponse() ? validations.getResponse() : null;
	}
	public List<com.fimet.core.usecase.Validation> getValidationsIssuerRequest() {
		return hasValidationsIssuerRequest() ? validations.getRequest() : null;
	}
	public List<com.fimet.core.usecase.Validation> getValidationsExtract() {
		return hasValidationsExtract() ? validations.getExtract() : null;
	}
	public boolean hasFieldIssReq(String idField) {
		return issuer != null && issuer.getRequest() != null && issuer.getRequest().getMessage() != null && issuer.getRequest().getMessage().hasField(idField);
	}
	public boolean hasFieldIssReq(int idField) {
		return issuer != null && issuer.getRequest() != null && issuer.getRequest().getMessage() != null && issuer.getRequest().getMessage().hasField(idField);
	}
	public byte[] getFieldIssReq(int idField) {
		return hasFieldIssReq(idField) ? issuer.getRequest().getMessage().getField(idField) : null;
	}
	public String getValueIssReq(int idField) {
		return hasFieldIssReq(idField) ? issuer.getRequest().getMessage().getValue(idField) : null;
	}
	public byte[] getFieldIssReq(String idField) {
		return hasFieldIssReq(idField) ? issuer.getRequest().getMessage().getField(idField) : null;
	}
	public String getValueIssReq(String idField) {
		return hasFieldIssReq(idField) ? issuer.getRequest().getMessage().getValue(idField) : null;
	}
	
	public boolean hasFieldIssRes(String idField) {
		return issuer != null && issuer.getResponse() != null && issuer.getResponse().getMessage() != null && issuer.getResponse().getMessage().hasField(idField);
	}
	public boolean hasFieldIssRes(int idField) {
		return issuer != null && issuer.getResponse() != null && issuer.getResponse().getMessage() != null && issuer.getResponse().getMessage().hasField(idField);
	}
	public byte[] getFieldIssRes(int idField) {
		return hasFieldIssRes(idField) ? issuer.getResponse().getMessage().getField(idField) : null;
	}
	public byte[] getFieldIssRes(String idField) {
		return hasFieldIssRes(idField) ? issuer.getResponse().getMessage().getField(idField) : null;
	}
	public String getValueIssRes(int idField) {
		return hasFieldIssRes(idField) ? issuer.getResponse().getMessage().getValue(idField) : null;
	}
	public String getValueIssRes(String idField) {
		return hasFieldIssRes(idField) ? issuer.getResponse().getMessage().getValue(idField) : null;
	}
	
	public boolean hasFieldAcqReq(String idField) {
		return acquirer != null && acquirer.getRequest() != null && acquirer.getRequest().getMessage() != null && acquirer.getRequest().getMessage().hasField(idField); 
	}
	public boolean hasFieldAcqReq(int idField) {
		return acquirer != null && acquirer.getRequest() != null && acquirer.getRequest().getMessage() != null && acquirer.getRequest().getMessage().hasField(idField); 
	}
	public byte[] getFieldAcqReq(int idField) {
		return hasFieldAcqReq(idField) ? acquirer.getRequest().getMessage().getField(idField) : null; 
	}
	public String getValueAcqReq(int idField) {
		return hasFieldAcqReq(idField) ? acquirer.getRequest().getMessage().getValue(idField) : null; 
	}
	public String getValueAcqReq(String idField) {
		return hasFieldAcqReq(idField) ? acquirer.getRequest().getMessage().getValue(idField) : null; 
	}
	public byte[] getFieldAcqReq(String idField) {
		return hasFieldAcqReq(idField) ? acquirer.getRequest().getMessage().getField(idField) : null; 
	}
	
	public boolean hasFieldAcqRes(String idField) {
		return acquirer != null && acquirer.getResponse() != null && acquirer.getResponse().getMessage() != null && acquirer.getResponse().getMessage().hasField(idField);
	}
	public boolean hasFieldAcqRes(int idField) {
		return acquirer != null && acquirer.getResponse() != null && acquirer.getResponse().getMessage() != null && acquirer.getResponse().getMessage().hasField(idField);
	}
	public String getValueAcqRes(int idField) {
		return hasFieldAcqRes(idField) ? acquirer.getResponse().getMessage().getValue(idField) : null;
	}
	public byte[] getFieldAcqRes(String idField) {
		return hasFieldAcqRes(idField) ? acquirer.getResponse().getMessage().getField(idField) : null;
	}
	public byte[] getFieldAcqRes(int idField) {
		return hasFieldAcqRes(idField) ? acquirer.getResponse().getMessage().getField(idField) : null;
	}
	public String getValueAcqRes(String idField) {
		return hasFieldAcqRes(idField) ? acquirer.getResponse().getMessage().getValue(idField) : null;
	}
	public int hashCode() {
		Message m = acquirer != null && acquirer.getRequest() != null ? acquirer.getRequest().getMessage() : null;
		String authCode = null;
		if (acquirer.getResponse() != null && acquirer.getResponse().getMessage() != null && acquirer.getResponse().getMessage().getMti().startsWith("9")) {
			authCode = "000000";
		} else {
			authCode = hasFieldAcqRes(38) ? getValueAcqRes(38) : "000000";
		}
		final int prime = 31;
		int result = 1;
		if (m != null) {
			result = prime * result + (m.getMti() != null ? (m.getMti().endsWith("0") ? m.getMti().hashCode() : (m.getMti().substring(0,m.getMti().length()-1)+"0").hashCode()) : 0);//Mti
			result = prime * result + (m.hasField(3) && m.getValue(3) != null ? m.getValue(3).hashCode() : 0);//Processing Code
			result = prime * result + (m.hasField(4) && m.getValue(4) != null ? m.getValue(4).hashCode() : 0);//Amount
			result = prime * result + (m.hasField(11) && m.getValue(11) != null? m.getValue(11).hashCode() : 0);//Stan
			//result = prime * result + (m.hasField(12) ? m.value(12).hashCode() : 0);//LocalTransactionTime 
			result = prime * result  +(m.getPan() != null ? m.getPan().hashCode() : 0);// PAN
			result = prime * result + (m.hasField(37) && m.getValue(37) != null ? m.getValue(37).hashCode() : 0);// RRN
			result = prime * result +  (authCode != null ? authCode.hashCode() : 0);
		} else {
			result = prime * result;
		}
		if (Console.isEnabledDebug()) {
			Console.getInstance().debug(UseCase.class, "HASH_USECASE("+result+"):\n"+
				"MTI:["+(m.getMti() != null ? m.getMti() : "" )+"]\n"+
				"PCODE:["+(m.getValue(3) != null ? m.getValue(3) : "" )+"]\n"+
				"AMT:["+(m.getValue(4) != null ? m.getValue(4) : "" )+"]\n"+
				"STAN:["+(m.getValue(11) != null ? m.getValue(11) : "" )+"]\n"+
				//(m.value(12) != null ? m.value(12) : "" )+"\n"+// TPVs extractan otro time :C
				"PAN:["+(m.getPan() != null ? m.getPan() : "")+"]\n"+
				"RRN:["+(m.getValue(37) != null ? m.getValue(37) : "")+"]\n"+
				"ACODE:["+authCode+"]"// Declinaciones IntBr envia 000000 y tpv no envia DE37
			);
		}
		return result;
	}
	public List<Notice> getNotices() {
		return notices;
	}
	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}
	public void addNotice(Notice notice) {
		if (notices == null) {
			notices = new ArrayList<>();
		}
		notices.add(notice);
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public void setExtract(IExtract extract) {
		this.extract = extract;
	}
	public IExtract getExtract() {
		return this.extract;
	}
}
