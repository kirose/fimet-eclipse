package com.fimet.core.json.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.Acquirer;
import com.fimet.core.usecase.Issuer;
import com.fimet.core.usecase.UseCase;
import com.fimet.core.usecase.Validations;

public class UseCaseAdapter extends TypeAdapter<UseCase>{

	protected final TypeAdapter<UseCase> delegate;
	private TypeAdapter<Issuer> issuerAdapter;
	private TypeAdapter<Acquirer> acquirerAdapter;
	private TypeAdapter<Validations> validationsAdapter;
	public UseCaseAdapter(TypeAdapter<UseCase> delegate) {
		if (delegate == null) {
			throw new NullPointerException("TypeAdapter delegate is null");
		}
		this.delegate = delegate;
		issuerAdapter = JsonAdapterFactory.GSON.getAdapter(Issuer.class);
		acquirerAdapter = JsonAdapterFactory.GSON.getAdapter(Acquirer.class);
		validationsAdapter = JsonAdapterFactory.GSON.getAdapter(Validations.class);
	}
	public UseCase read(JsonReader in) throws IOException {
		UseCase useCase = (UseCase)delegate.read(in);
		validateSourceConnections(useCase);
		return useCase;
	}
	protected void validateSourceConnections(UseCase useCase) {
		ISocket acquirer = useCase.getAcquirer().getConnection();
//		if (acquirer == null) {
//			Console.getInstance().warning(UseCaseAdapter.class, "Cannot find Acquirer is null in use case "+useCase.getName());
//		}
//		if (useCase.getIssuer() != null && useCase.getIssuer().getConnection() != null) {
//			ISourceConnection issuer = useCase.getIssuer().getConnection();
//			if (issuer == null) {
//				Console.getInstance().warning(UseCaseAdapter.class, "Cannot find Issuer is null in use case "+useCase.getName());
//			}
//		}
		if (useCase.getAcquirer().getRequest() != null && useCase.getAcquirer().getRequest().getMessage() != null && acquirer != null) {
			useCase.getAcquirer().getRequest().getMessage().setAdapter((IAdapter)acquirer.getAdapter());
			useCase.getAcquirer().getRequest().getMessage().setParser((IParser)acquirer.getParser());
		}

	}
	public void write(JsonWriter out, UseCase useCase) throws IOException {
		out.beginObject();
		if (useCase.getValidatorClass() != null) {
			out.name("validatorClass").value(useCase.getValidatorClass());
		}
		if (useCase.getExecutionTime() != null) out.name("executionTime").value(useCase.getExecutionTime());
		if (useCase.getIssuer() != null) {
			out.name("issuer");
			issuerAdapter.write(out, useCase.getIssuer());
		}
		if (useCase.getAcquirer() != null) {
			out.name("acquirer");
			acquirerAdapter.write(out, useCase.getAcquirer());
		}
		if (useCase.getValidations() != null && (useCase.getValidations().getExtract() != null || useCase.getValidations().getRequest() != null || useCase.getValidations().getResponse() != null)) {
			out.name("validations");
			validationsAdapter.write(out, useCase.getValidations());
		}
		out.endObject();
	}
}
