package com.fimet.core.usecase;

import java.util.List;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.IValidatorManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.pojo.Notice;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Esta tarea se crea cuando useCase.aquirer.reques.authorization = true
 * Se genera una autorizacion (0200,0100,etc) para ejecutar un caso posterior (0420,0220)
 *
 */
public class UseCaseExecutorAuthorization extends UseCaseExecutor {
	private static final List<String> DEFAULT_REPLACE_FIELDS = new java.util.ArrayList<>();
	static {
		DEFAULT_REPLACE_FIELDS.add("38");
		DEFAULT_REPLACE_FIELDS.add("39");
	}
	private UseCaseExecutor parentTask;
	
	UseCaseExecutorAuthorization(UseCaseExecutor parent) {
		super(parent.getResource(), parent.getJob());
		parentTask = parent;
	}

	private static UseCase buildAuthorization(UseCase useCase) {
		validateAuthorization(useCase.getAcquirer().getRequest());
		UseCase auth = null;
		try {
			auth = useCase.clone();
		} catch (CloneNotSupportedException e) {
			throw new com.fimet.commons.exception.FimetException(e);
		}
		auth.setName(useCase.getName()+"_AUTH");
		Message message = auth.getAcquirer().getRequest().getAuthorization().getMessage();
		auth.getAcquirer().getRequest().setAuthorization(null);
		auth.getAcquirer().getRequest().setMessage(message);
		auth.setValidations(null);
		auth.setValidatorClass(null);
		auth.getIssuer().getResponse().setExcludeFields(null);
		auth.getIssuer().getResponse().setIncludeFields(null);
		Manager.get(IValidatorManager.class).validatorFor(auth);
		return auth;
	}


	private static void validateAuthorization(AcquirerRequest request) {
		Authorization authorization = request.getAuthorization();
		if (authorization.getMessage() == null) {
			Message msgReversal = request.getMessage();
			String mtiAuthorization = authorization.getMti() != null ? authorization.getMti() : createMtiAuthorization(msgReversal.getParser().toString(),msgReversal.getMti());
			Message msgAuth;
			try {
				msgAuth = msgReversal.clone();
			} catch (CloneNotSupportedException e) {
				throw new com.fimet.commons.exception.FimetException(e);
			}
			authorization.setMessage(msgAuth);
			msgAuth.setMti(mtiAuthorization);
			authorizaionCfgFields(authorization);
		} else {
			authorizaionCfgFields(authorization);
		}
	}
	private static String createMtiAuthorization(String parser, String mtiReversal) {
		parser = parser.toUpperCase();
		if (parser.contains("VISA") || parser.contains("MASTERCARD")) {
			if ("0400".equals(mtiReversal)) {
				return "0100";
			}
		} else if (parser.contains("TPV")) {
			if ("0400".equals(mtiReversal)) {
				return "0200";
			}
		} else {
			if ("0420".equals(mtiReversal) || "0421".equals(mtiReversal)) {
				return "0200";
			} else if ("0220".equals(mtiReversal)) {//Ceck out
				return "0100";//Check in
			}
		}
		throw new ParserException("Mti '"+mtiReversal+"' not is reversal (cannot create mti for authorization), check useCase.acquirer.request.authorization");
	}

	private static void authorizaionCfgFields(Authorization authorization) {
		if (authorization.getIncludeFields() != null) {
			for (Field f : authorization.getIncludeFields()) {
				authorization.getMessage().setValue(f.getKey(), f.getValue());
			}
		}
		if (authorization.getExcludeFields() != null) {
			for (String idField : authorization.getExcludeFields()) {
				authorization.getMessage().remove(idField);
			}
		}
		authorization.getMessage().remove("15");
		authorization.getMessage().remove("38");
		authorization.getMessage().remove("39");
		authorization.getMessage().remove("90");
	}
	@Override
	protected UseCase buildUseCase() {
		return buildAuthorization(parentTask.getUseCase());
	}
	@Override
	public void onMessengerComplete() {
		setStatus(COMPLETED);
		Console.getInstance().info(UseCaseExecutorAuthorization.class, "\n\nComplete authorization UseCase " + getUseCase().getName()+"\n");
		onFinish();
		if (getUseCase().getAcquirer().getResponse().getMessage() != null) {
			Message response = getUseCase().getAcquirer().getResponse().getMessage();
			Message messageRev = parentTask.getUseCase().getAcquirer().getRequest().getMessage();
			List<String> replaceFields = parentTask.getUseCase().getAcquirer().getRequest().getAuthorization().getReplaceFields();
			if (replaceFields == null) {
				replaceFields = DEFAULT_REPLACE_FIELDS;
			}
			byte[] field;
			for (String id : replaceFields) {
				field = response.getField(id);
				if (field != null) {
					messageRev.setField(id,field);
				}
			}
			parentTask.run();
		}
	}
	@Override
	public void stop() {
		setStatus(STOPED);
		Console.getInstance().info(UseCaseExecutorAuthorization.class, "\n\nStoping Authorization UseCase " + getUseCase().getName()+"\n");
		onFinish();
	}
	@Override
	public void onTimeout() {
		setStatus(STOPED);
		Console.getInstance().info(UseCaseExecutorAuthorization.class, "\n\nAutorization cause timeout " + getUseCase().getName()+"\n");
		parentTask.getUseCase().addNotice(new Notice(Notice.ERROR, "Autorization cause timeout"));
		onFinish();
		parentTask.stop();
	}
	@Override
	public void onFinish() {
		removeListeners();
		//Console.getInstance().info("\n\nFinish UseCase " + getUseCase().getName()+"\n");
		if (onFinishListener != null) {
			onFinishListener.onFinish(this);
		}
		createEvidences(getUseCase());
	}
}
