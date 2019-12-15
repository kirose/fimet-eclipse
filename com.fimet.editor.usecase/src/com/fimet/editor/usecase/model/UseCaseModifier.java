package com.fimet.editor.usecase.model;

import java.util.ArrayList;
import java.util.List;

import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.Acquirer;
import com.fimet.core.usecase.AcquirerResponse;
import com.fimet.core.usecase.Authorization;
import com.fimet.core.usecase.Field;
import com.fimet.core.usecase.Issuer;
import com.fimet.core.usecase.UseCase;
import com.fimet.core.usecase.Validation;
import com.fimet.core.usecase.Validations;
import com.fimet.editor.usecase.UseCaseEditor;

public class UseCaseModifier  implements IModifier {
	private UseCase useCase;
	private UseCaseEditor editor;
	private boolean applingSourceChanges;
	private boolean areDirtyPagesGui;
	
	public UseCaseModifier(UseCaseEditor editor, UseCase useCase) {
		super();
		this.editor = editor;
		this.useCase = useCase;
	}
	public void setUseCase(UseCase useCase) {
		this.useCase = useCase;
	}
	public ISocket getOverviewAcqSrcConn(){
		return editor.getOverviewPage().getSectionAcquirer().getConnection();
	}
	public ISocket getIssSrcConn(){
		return useCase != null && useCase.getIssuer() != null ? useCase.getIssuer().getConnection() : null;
	}
	public Boolean getIssConnect(){
		return useCase != null && useCase.getIssuer() != null ? useCase.getIssuer().getConnect() : null;
	}
	public Integer getIssRespTimeout(){
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getResponse() != null ? useCase.getIssuer().getResponse().getTimeout() : null;
	}
	public List<Field> getIssRespIncFlds(){
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getResponse() != null ? useCase.getIssuer().getResponse().getIncludeFields() : null;
	}
	public List<String> getIssRespExcflds(){
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getResponse() != null ? useCase.getIssuer().getResponse().getExcludeFields() : null;
	}
	public String getValClass(){
		return useCase != null && useCase.getValidatorClass() != null ? useCase.getValidatorClass() : null;
	}
	public List<Validation> getIssReqVals(){
		return useCase != null ? useCase.getValidationsIssuerRequest() : null;
	}
	public List<Validation> getExtractVals(){
		return useCase != null ? useCase.getValidationsExtract() : null;
	}
	public ISocket getAcqSrcConn(){
		return useCase != null && useCase.getAcquirer() != null ? useCase.getAcquirer().getConnection() : null;
	}
	public Message getAcqReqMsg(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null ? useCase.getAcquirer().getRequest().getMessage() : null;
	}
	/*public Map<String, byte[]> getAcqReqMsgFlds(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getMessage() != null ? useCase.getAcquirer().getRequest().getMessage().getFields() : null;
	}*/
	public String getAcqReqMsgMti(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getMessage() != null ? useCase.getAcquirer().getRequest().getMessage().getMti() : null;
	}
	public String getAcqReqMsgHeader(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getMessage() != null ? useCase.getAcquirer().getRequest().getMessage().getHeader() : null;
	}
	public Authorization getAcqReqAut(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null ? useCase.getAcquirer().getRequest().getAuthorization() : null;
	}
	public String getAcqReqAutMti(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null ? useCase.getAcquirer().getRequest().getAuthorization().getMti() : null;
	}
	public String getAcqReqAutHeader(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null ? useCase.getAcquirer().getRequest().getAuthorization().getHeader() : null;
	}
	public Message getAcqReqAutMsg(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null ? useCase.getAcquirer().getRequest().getAuthorization().getMessage() : null;
	}
	public String getAcqReqAutMsgMti(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null && useCase.getAcquirer().getRequest().getAuthorization().getMessage() != null? useCase.getAcquirer().getRequest().getAuthorization().getMessage().getMti() : null;
	}
	public String getAcqReqAutMsgHeader(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null && useCase.getAcquirer().getRequest().getAuthorization().getMessage() != null? useCase.getAcquirer().getRequest().getAuthorization().getMessage().getHeader() : null;
	}
//	public Map<String, byte[]> getAcqReqAutMsgFlds(){
//		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null && useCase.getAcquirer().getRequest().getAuthorization().getMessage() != null ? useCase.getAcquirer().getRequest().getAuthorization().getMessage().getFields() : null;
//	}
	public List<Field> getAcqReqAutIncFlds(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null ? useCase.getAcquirer().getRequest().getAuthorization().getIncludeFields() : null;
	}
	public List<String> getAcqReqAutExcFlds(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null ? useCase.getAcquirer().getRequest().getAuthorization().getExcludeFields() : null;
	}
	public List<String> getAcqReqAutRepFlds(){
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getAuthorization() != null ? useCase.getAcquirer().getRequest().getAuthorization().getReplaceFields() : null;
	}
	public List<Validation> getAcqResVals(){
		return useCase != null ? useCase.getValidationsAcquirerResponse() : null;
	}

	public void modifyIssSrcConn(ISocket iap){
		validateIssuer().setConnection(iap);
	}
	public void modifyIssConnect(Boolean connect){
		if (connect == null) {
			validateIssuer().setConnect(true);	
		} else {
			validateIssuer().setConnect(connect);
		}
	}	
	public void modifyIssRespTimeout(Integer timeout){
		if (timeout == null) {
			if (validateIssuer().getResponse() != null) {
				validateIssuerResponse().setTimeout(null);
				validateIssuerResponseForNull();	
			}
		} else {
			validateIssuerResponse().setTimeout(timeout);
		}
	}
	public void modifyIssRespIncFlds(List<Field> includeFields){
		if (includeFields == null || includeFields.isEmpty()) {
			if (validateIssuer().getResponse() != null) {
				validateIssuerResponse().setIncludeFields(null);
				validateIssuerResponseForNull();
			}
		} else {
			validateIssuerResponse().setIncludeFields(includeFields);
		}
	}
	public void modifyIssRespExcFlds(List<String> fields){
		if (fields == null || fields.isEmpty()) {
			if (validateIssuer().getResponse() != null) {
				validateIssuerResponse().setExcludeFields(null);
				validateIssuerResponseForNull();
			}
		} else {
			validateIssuerResponse().setExcludeFields(fields);
		}
	}
	public void modifyValidations(Validations validations){
		if (useCase != null) {
			useCase.setValidations(validations);
		}
	}
	public void modifyValClass(String className){
		if (useCase != null) {
			if (className == null || "".equals(className.trim())) {
				useCase.setValidatorClass(null);
			} else {
				useCase.setValidatorClass(className);
			}
		}
	}
	public void modifyExtractVals(List<Validation> validations){
		if (useCase != null) {
			if (validations == null || validations.isEmpty()) {
				if (useCase.getValidations() != null) {
					validateValidations().setExtract(null);
				}
			} else {
				validateValidations().setExtract(validations);
			}
		}
	}
	public void modifyIssReqVals(List<Validation> validations){
		if (useCase != null) {
			if (validations == null || validations.isEmpty()) {
				if (useCase.getValidations() != null) {
					validateValidations().setRequest(null);
				}
			} else {
				validateValidations().setRequest(validations);
			}
		}
	}
	public void modifyAcqSrcConn(ISocket iap){
		if (useCase != null) {
			validateAcquirer().setConnection(iap);
		}
	}
	public void modifyAcqRes(AcquirerResponse res){
		if (useCase != null) {
			if (res == null) {
				validateAcquirer().setResponse(null);
			} else {
				validateAcquirer().setResponse(res);
			}
		}
	}
	public void modifyAcqResVals(List<Validation> vals){
		if (useCase != null) {
			if (vals == null || vals.isEmpty()) {
				if (useCase.getValidations() != null) {
					validateValidations().setResponse(null);
				}
			} else {
				validateValidations().setResponse(vals);
			}
		}
	}
	public void modifyAcqReqMsg(Message msg){
		if (useCase != null) {
			if (msg == null) {
				validateAcquirerRequest().setMessage(null);
			} else {
				validateAcquirerRequest().setMessage(msg);
			}
		}
	}
//	public void modifyAcqReqMsgFlds(Map<String,byte[]> fields){
//		if (useCase != null) {
//			validateAcquirerRequestMsg().setFields(fields);
//		}
//	}
	public void modifyAcqReqMsgMti(String mti){
		if (useCase != null) {
			validateAcquirerRequestMsg().setMti(mti);
		}
	}
	public void modifyAcqReqMsgHeader(String header){
		if (useCase != null) {
			validateAcquirerRequestMsg().setHeader(header);
		}
	}
	public void modifyAcqReqAut(Authorization auth){
		if (useCase != null) {
			validateAcquirerRequest().setAuthorization(auth);
		}
	}
	public void modifyAcqReqAutMti(String mti){
		if (useCase != null) {
			validateAcquirerRequestAuth().setMti(mti);
		}
	}
	public void modifyAcqReqAutHeader(String header){
		if (useCase != null) {
			validateAcquirerRequestAuth().setHeader(header);
		}
	}
	public void modifyAcqReqAutMsg(Message msg){
		if (useCase != null) {
			validateAcquirerRequestAuth().setMessage(msg);
		}
	}
	public void modifyAcqReqAutMsgHeader(String header){
		if (useCase != null) {
			validateAcquirerRequestAuthMsg().setHeader(header);
		}
	}
	public void modifyAcqReqAutMsgMti(String mti){
		if (useCase != null) {
			validateAcquirerRequestAuthMsg().setMti(mti);
		}
	}
	public void modifyAcqReqAutMsgParser(String parser){
		if (useCase != null) {
			validateAcquirerRequestAuthMsg().setParser(Manager.get(IParserManager.class).getParser(parser));
		}
	}
	public void modifyAcqReqAutMsgAdapter(String adapter){
		if (useCase != null) {
			validateAcquirerRequestAuthMsg().setAdapter(Manager.get(IAdapterManager.class).getAdapter(adapter));
		}
	}
//	public void modifyAcqReqAutMsgFlds(Map<String,byte[]> fields){
//		if (useCase != null) {
//			validateAcquirerRequestAuthMsg().setFields(fields);
//		}		
//	}
	public void modifyAcqReqAutIncFlds(List<Field> fields){
		if (useCase != null) {
			validateAcquirerRequestAuth().setIncludeFields(fields);
		}
	}
	public void modifyAcqReqAutRepFlds(List<String> fields){
		if (useCase != null) {
			validateAcquirerRequestAuth().setReplaceFields(fields);
		}
	}
	public void modifyAcqReqAutExcFlds(List<String> fields){
		if (useCase != null) {
			validateAcquirerRequestAuth().setExcludeFields(fields);
		}
	}
	public Issuer validateIssuer() {
		if (useCase != null) {
			if (useCase.getIssuer() == null) {
				useCase.setIssuer(new Issuer());
			}
			return useCase.getIssuer();
		}
		return null;
	}
	public Validations validateValidations() {
		if (useCase != null) {
			Validations v;
			if (useCase.getValidations()  == null) {
				useCase.setValidations(v = new Validations());
			} else {
				v = useCase.getValidations();
			}
			return v;
		}
		return null;
	}
	public com.fimet.core.usecase.IssuerRequest validateIssuerRequest() {
		if (useCase != null) {
			Issuer issuer = validateIssuer();
			com.fimet.core.usecase.IssuerRequest req;
			if (issuer.getRequest() == null) {
				issuer.setRequest(req = new com.fimet.core.usecase.IssuerRequest());
			} else {
				req = issuer.getRequest();
			}
			return req;
		}
		return null;
	}
	public com.fimet.core.usecase.IssuerResponse validateIssuerResponse() {
		if (useCase != null) {
			Issuer issuer = validateIssuer();
			com.fimet.core.usecase.IssuerResponse res;
			if (issuer.getResponse() == null) {
				issuer.setResponse(res = new com.fimet.core.usecase.IssuerResponse());
			} else {
				res = issuer.getResponse();
			}
			return res;
		}
		return null;
	}
	public void validateIssuerResponseForNull() {
		if (useCase != null) {
			com.fimet.core.usecase.IssuerResponse res = validateIssuer().getResponse();
			if (res != null) {
				if (
					res.getTimeout() == null &&
					res.getExcludeFields() == null &&
					res.getIncludeFields() == null
				) {
					validateIssuer().setResponse(null);
				}
			}
		}
	}
	public List<Field> validateIssuerResponseIncfields() {
		if (useCase != null) {
			com.fimet.core.usecase.IssuerResponse resp = validateIssuerResponse();
			if (resp.getIncludeFields() == null) {
				List<Field> flds;
				resp.setIncludeFields(flds = new ArrayList<>());
				return flds;
			} else {
				return resp.getIncludeFields();
			}
		}
		return null;
	}
	public List<String> validateIssuerResponseExcfields() {
		if (useCase != null) {
			com.fimet.core.usecase.IssuerResponse resp = validateIssuerResponse();
			if (resp.getExcludeFields() == null) {
				List<String> flds;
				resp.setExcludeFields(flds = new ArrayList<>());
				return flds;
			} else {
				return resp.getExcludeFields();
			}
		}
		return null;
	}
	public Acquirer validateAcquirer() {
		if (useCase != null) {
			if (useCase.getAcquirer() == null) {
				useCase.setAcquirer(new Acquirer());
			}
			return useCase.getAcquirer();
		}
		return null;
	}
	public com.fimet.core.usecase.AcquirerRequest validateAcquirerRequest() {
		if (useCase != null) {
			Acquirer acquirer = validateAcquirer();
			if (acquirer.getRequest() == null) {
				com.fimet.core.usecase.AcquirerRequest req;
				acquirer.setRequest(req = new com.fimet.core.usecase.AcquirerRequest());
				return req;
			} else {
				return acquirer.getRequest();
			}
		}
		return null;
	}
	public com.fimet.core.usecase.AcquirerResponse validateAcquirerResponse() {
		if (useCase != null) {
			Acquirer acquirer = validateAcquirer();
			if (acquirer.getResponse() == null) {
				com.fimet.core.usecase.AcquirerResponse res;
				acquirer.setResponse(res = new com.fimet.core.usecase.AcquirerResponse());
				return res;
			} else {
				return acquirer.getResponse();
			}
		}
		return null;
	}
	public Message validateAcquirerRequestMsg() {
		if (useCase != null) {
			com.fimet.core.usecase.AcquirerRequest req = validateAcquirerRequest();
			if (req.getMessage() == null) {
				Message m;
				req.setMessage(m = new Message());
				return m;
			} else {
				return req.getMessage();
			}
		}
		return null;
	}
	public Authorization validateAcquirerRequestAuth() {
		if (useCase != null) {
			com.fimet.core.usecase.AcquirerRequest req = validateAcquirerRequest();
			if (req.getAuthorization() == null) {
				Authorization auth;
				req.setAuthorization(auth = new Authorization());
				return auth;
			} else {
				return req.getAuthorization();
			}
		}
		return null;
	}
	public Message validateAcquirerRequestAuthMsg() {
		if (useCase != null) {
			Authorization auth = validateAcquirerRequestAuth();
			if (auth.getMessage() == null) {
				Message msg;
				auth.setMessage(msg = new Message());
				return msg;
			} else {
				return auth.getMessage();
			}
		}
		return null;
	}
	public List<Field> validateAcquirerRequestAuthIncfields() {
		if (useCase != null) {
			Authorization auth = validateAcquirerRequestAuth();
			if (auth.getIncludeFields() == null) {
				List<Field> flds;
				auth.setIncludeFields(flds = new ArrayList<>());
				return flds;
			} else {
				return auth.getIncludeFields();
			}
		}
		return null;
	}
	public List<String> validateAcquirerRequestAuthExcfields() {
		if (useCase != null) {
			Authorization auth = validateAcquirerRequestAuth();
			if (auth.getExcludeFields() == null) {
				List<String> flds;
				auth.setExcludeFields(flds = new ArrayList<>());
				return flds;
			} else {
				return auth.getExcludeFields();
			}
		}
		return null;
	}
	public void startApplingSourceChanges() {
		applingSourceChanges = true;
	}
	public void finishApplingSourceChanges() {
		applingSourceChanges = false;
	}
	public boolean isApplingSourceChanges() {
		return applingSourceChanges;
	}
	public void cleanDirtyPagesGui() {
		areDirtyPagesGui = false;
	}
	public boolean areDirtyPagesGui() {
		return areDirtyPagesGui;
	}
	public void markAsDirtyPagesGui() {
		areDirtyPagesGui = true;
		if (!editor.isDirty()) {
			editor.editorDirtyStateChanged();
		}
	}
}
