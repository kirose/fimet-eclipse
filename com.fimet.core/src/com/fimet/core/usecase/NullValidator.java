package com.fimet.core.usecase;

import java.util.Map;

import com.fimet.core.IValidator;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.Message;

public class NullValidator implements IValidator {
	private UseCase useCase;
	public NullValidator(UseCase useCase) {
		this.useCase = useCase;
	}
	public UseCase getUseCase() {
		return useCase;
	}
	@Override
	public void onIssuerRequest(Message message) {}

	@Override
	public void onIssuerResponse(Message message) {}

	@Override
	public void onAcquirerRequest(Message message) {}

	@Override
	public void onAcquirerResponse(Message message) {}

	@Override
	public void onExtract(IMessage base, Map<Integer, IMessage> additionals) {}

}
