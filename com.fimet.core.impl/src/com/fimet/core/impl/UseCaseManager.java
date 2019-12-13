package com.fimet.core.impl;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IResource;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.ParserException;
import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.utils.FileUtils;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.IValidatorManager;
import com.fimet.core.Manager;
import com.fimet.core.json.adapter.JsonAdapterFactory;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;
import com.fimet.core.usecase.Validation;

public class UseCaseManager implements IUseCaseManager {
	private UseCase parse(String name, String json, boolean fail) {
		UseCase useCase = JsonAdapterFactory.GSON.fromJson(json, UseCase.class);
		useCase.setName(name);
		validate(useCase, fail);
		return useCase;
	}
	@Override
	public UseCase parseForEditor(IResource resource) {
		return parse(resource, false);
	}
	@Override
	public UseCase parseForEditor(String name, String json) {
		return parse(name, json, false);
	}
	@Override
	public UseCase parse(String name, String json) {
		return parse(name, json, true);
	}
	public UseCase parse(File file, boolean fail) {
		String json = FileUtils.readContents(file);
		UseCase useCase = parse(file.getName().substring(0, file.getName().lastIndexOf('.')),json, fail);
		return useCase;
	}
	@Override
	public UseCase parse(File file) {
		String json = FileUtils.readContents(file);
		UseCase useCase = parse(file.getName().substring(0, file.getName().lastIndexOf('.')),json);
		return useCase;
	}

	public UseCase parse(IResource resource, boolean fail) {
		UseCase useCase = parse(new File(resource.getLocation().toString()), fail);
		useCase.setResource(resource);
		return useCase;
	}
	@Override
	public UseCase parse(IResource resource) {
		UseCase useCase = parse(new File(resource.getLocation().toString()));
		useCase.setResource(resource);
		return useCase;
	}

	@Override
	public UseCase parseForExecution(IResource resource) {
		UseCase useCase = parse(resource);
		
		Manager.get(IValidatorManager.class).validatorFor(useCase);
		
		if (useCase.getAcquirer().getResponse() == null) {
			useCase.getAcquirer().setResponse(new com.fimet.core.usecase.AcquirerResponse());
		}
		if (useCase.getIssuer() != null) {
			if (useCase.getIssuer().getRequest() == null) {
				useCase.getIssuer().setRequest(new com.fimet.core.usecase.IssuerRequest());
			}
			if (useCase.getIssuer().getResponse() == null) {
				useCase.getIssuer().setResponse(new com.fimet.core.usecase.IssuerResponse());
			}
		}
		if (useCase.getAcquirer().getConnection() == null) {
			throw new UseCaseException(useCase.getName() + " Acqurier is null");
		}
		return useCase;
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
	
	
	private void validate(UseCase useCase, boolean fail) {
		if (useCase.getAcquirer() == null) {
			if (fail) {
				throw new ParserException(useCase.getName()+".acquirer is required");
			} else {
				Console.getInstance().warning(UseCaseManager.class, useCase.getName()+".acquirer is required");
			}
		}
		if (useCase.getAcquirer().getRequest() == null) {
			if (fail) {
				throw new ParserException(useCase.getName()+".acquirer.request is required");
			} else {
				Console.getInstance().warning(UseCaseManager.class, useCase.getName()+".acquirer.request is required");
			}
		}
		if (useCase.getAcquirer().getRequest().getMessage() == null) {
			if (fail) {
				throw new ParserException(useCase.getName()+".acquirer.request.message is required");
			} else {
				Console.getInstance().warning(UseCaseManager.class, useCase.getName()+".acquirer.request.message is required");
			}
		}
		validateIap(useCase.getAcquirer().getConnection(), useCase.getName()+".acquirer.connection",fail);
		if (useCase.getAcquirer().getRequest().getAuthorization() != null) {
			if (useCase.getIssuer() == null) {
				if (fail) {
					throw new ParserException(useCase.getName()+".issuer is required when useCase.acquirer.request.authorization is declared");
				} else {
					Console.getInstance().warning(UseCaseManager.class, useCase.getName()+".issuer is required when useCase.acquirer.request.authorization is declared");
				}
			}
			if (useCase.getIssuer().getConnection() == null) {
				if (fail) {
					throw new ParserException(useCase.getName()+".issuer.connection is required when useCase.acquirer.request.authorization is declared");
				} else {
					Console.getInstance().warning(UseCaseManager.class, useCase.getName()+".issuer.connection is required when useCase.acquirer.request.authorization is declared");
				}
			}
		}
		if (useCase.hasValidationsIssuerRequest()) {
			List<Validation> validations = useCase.getValidationsIssuerRequest();
			StringBuilder s = new StringBuilder();
			int i = 0;
			for (Validation v : validations) {
				if (v.getExpression() == null) {
					s.append(",expression required in validation "+i);
				}
				if (v.getExpected() == null) {
					s.append(",expected required in validation "+i);
				}
			}
			if (s.length() > 0) {
				s.deleteCharAt(0);
				if (fail) {
					throw new ParserException("Issuer request validations cannot be parsed, "+useCase.getName()+": "+s.toString());
				} else {
					Console.getInstance().warning(UseCaseManager.class, "Issuer request validations cannot be parsed, "+useCase.getName()+": "+s.toString());
				}
			}
		}
		if (useCase.getIssuer() != null) {
			validateIap(useCase.getIssuer().getConnection(), useCase.getName()+".issuer.connection",fail);
			if (useCase.hasValidationsAcquirerResponse()) {
				List<Validation> validations = useCase.getValidationsAcquirerResponse();
				StringBuilder s = new StringBuilder();
				int i = 0;
				for (Validation v : validations) {
					if (v.getExpression() == null) {
						s.append(",expression required in validation "+i);
					}
					if (v.getExpected() == null) {
						s.append(",expected required in validation "+i);
					}
				}
				if (s.length() > 0) {
					s.deleteCharAt(0);
					if (fail) {
						throw new ParserException("Acquirer response validations cannot be parsed, "+useCase.getName()+": "+s.toString());
					} else {
						Console.getInstance().warning(UseCaseManager.class, "Acquirer response validations cannot be parsed, "+useCase.getName()+": "+s.toString());
					}
				}
			}
		}
	}
	private void validateIap(ISocket connection, String path, boolean fail) {
		if (connection == null) {
			if (fail) {
				throw new ParserException(path+" is required");
			} else {
				Console.getInstance().warning(UseCaseManager.class, path+" is required");
			}
		} else {
			if (connection.getName() == null) {
				if (fail) {
					throw new ParserException(path+".name is required");
				} else {
					Console.getInstance().warning(UseCaseManager.class, path+".name is required");
				}
			}
			if (connection.getPort() == null) {
				if (fail) {
					throw new ParserException(path+".port is required");
				} else {
					Console.getInstance().warning(UseCaseManager.class, path+".port is required");
				}
			}
			if (connection.getAddress() == null) {
				if (fail) {
					throw new ParserException(path+".address is required");
				} else {
					Console.getInstance().warning(UseCaseManager.class, path+".address is required");
				}
			}
		}
	}
}
