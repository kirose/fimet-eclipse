package com.fimet.core.usecase;

import com.fimet.commons.console.Console;
import com.fimet.core.IExtract;
import com.fimet.core.IValidator;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.pojo.Validation;

public abstract class AbstractValidator implements IValidator {

	protected Console console = Console.getInstance();
	protected UseCase useCase;
	public AbstractValidator(UseCase useCase) {
		this.useCase = useCase;
	}
	/**
	 * Write a message to the console
	 * @return The console
	 */
	protected Console getConsole() {
		return console;
	}
	/**
	 * @return UseCase the use case
	 */
	public UseCase getUseCase() {
		return useCase;
	}
	public IExtract getExtract() {
		return useCase != null ? useCase.getExtract() : null;
	}
	public IMessage getBase() {
		return useCase != null && useCase.getExtract() != null ? useCase.getExtract().getBase() : null;
	}
	public boolean hasAdditional(int idAdditional) {
		return useCase != null && useCase.getExtract() != null && useCase.getExtract().hasAdditional(idAdditional);
	}
	public IMessage getAdditional(int idAdditional) {
		return useCase != null && useCase.getExtract() != null ? useCase.getExtract().getAdditional(idAdditional) : null;
	}
	public boolean hasAquirerRequest() {
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getMessage() != null;
	}
	public boolean hasAquirerResponse() {
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getResponse() != null && useCase.getAcquirer().getResponse().getMessage() != null;
	}
	public boolean hasIssuerRequest() {
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getRequest() != null && useCase.getIssuer().getRequest().getMessage() != null;
	}
	public boolean hasIssuerResponse() {
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getResponse() != null && useCase.getIssuer().getResponse().getMessage() != null;
	}
	public Message getAquirerRequest() {
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getRequest() != null ? useCase.getAcquirer().getRequest().getMessage() : null;
	}
	public Message getAquirerResponse() {
		return useCase != null && useCase.getAcquirer() != null && useCase.getAcquirer().getResponse() != null ? useCase.getAcquirer().getResponse().getMessage() : null;
	}
	public Message getIssuerRequest() {
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getRequest() != null ? useCase.getIssuer().getRequest().getMessage() : null;
	}
	public Message getIssuerResponse() {
		return useCase != null && useCase.getIssuer() != null && useCase.getIssuer().getResponse() != null ? useCase.getIssuer().getResponse().getMessage() : null;
	}
	/**
	 * This method will be invoked on the issuer response 
	 * @param request message
	 */
	public void onIssuerResponse(Message msg) {}
	/**
	 * This method will be invoked on the acquirer request 
	 * @param response message
	 */
	public void onAcquirerRequest(Message msg) {}
	/**
	 * This method will report the validation and result in Console and will be include on the FimetReport<br/>
	 *   
	 * @param useCase the use case 
	 * @param name the name of the validation
	 * @param expected the value expected
	 * @param value the actual value
	 * <br/>
	 * Example:<br/><br/>
	 * 
	 * 		validate(useCase, "IssuerResponse Code Approval",message.getField("39").getValue(), "00");<br/>
	 * 
	 * that validation will print in console:<br/>
	 * 		IssuerResponse Code Approval<br/>
	 * 		Expected: "00"<br/>
	 * 		Value:    "00"<br/>
	 * 		Result:	  SUCCESSFUL<br/>
	 * 
	 */
	protected void validate(String name, Object expected, Object value) {
		Validation validation = new Validation(name, expected, value);
		useCase.addValidationResult(validation);
	}
	protected Validation newValidation(String name) {
		Validation validation = new Validation(name);
		useCase.addValidationResult(validation);
		return validation;
	}

}
