package com.fimet.core.usecase;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.eclipse.core.resources.IProject;

import com.fimet.commons.Activator;
import com.fimet.commons.console.Console;
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.utils.FileUtils;
import com.fimet.core.IClassLoaderManager;
import com.fimet.core.IExtract;
import com.fimet.core.IValidator;
import com.fimet.core.IValidatorManager;
import com.fimet.core.JobDeleteResource;
import com.fimet.core.JobInstallClass;
import com.fimet.core.JobRefreshProject;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.Message;

public final class ValidatorManager implements IValidatorManager {
	private static final String VALIDATORS_TMP_PACKAGE = "com.fimet.validator.tmp";
	private static final String VALIDATORS_TMP_RUTE = "com/fimet/validator/tmp/";
	private static final int prime = 31;
	private IClassLoaderManager classLoaderManager = Manager.get(IClassLoaderManager.class);
	private Map<Integer, Class<? extends IValidator>> dynamycClassValidators = new HashMap<>();

	public IValidator validatorFor(UseCase useCase) {
		IValidator validator;
		if (useCase.getValidatorClass() != null) {
			validator = getValidator(useCase, useCase.getValidatorClass());
		} else {
			validator = createValidator(useCase);
		}
		useCase.setValidator(validator);
		return validator;
	}
	public IValidator getValidator(UseCase useCase, String className) {
		if (className == null) {
			return new NullValidator(useCase);
		}
		try {
			Class<?> clazz = classLoaderManager.loadClassBin(className);
			Constructor<?> constructor = clazz.getConstructor(UseCase.class);
			return (IValidator)constructor.newInstance(useCase);
		} catch (Exception e) {
			Activator.getInstance().error("Error loading class "+className, e);
		}
		return new NullValidator(useCase);
	}
	private IValidator createValidator(UseCase useCase) {
		if (!useCase.hasValidations()) {
			return new NullValidator(useCase);
		}
		List<Validation> acqRes = useCase.getValidationsAcquirerResponse();
		List<Validation> issReq = useCase.getValidationsIssuerRequest();
		List<Validation> extractor = useCase.getValidationsExtract();
		Integer hashCode = hashCode(acqRes, issReq, extractor);
		if (!dynamycClassValidators.containsKey(hashCode)) {
			String simpleClassName = "Validator"+hashCode;
			String srcFileName = "src/"+VALIDATORS_TMP_RUTE+simpleClassName+".java";
			String binFileName = "bin/"+VALIDATORS_TMP_RUTE+simpleClassName+".class";
			try {
				Class<? extends IValidator> clazz = createClass(useCase, simpleClassName, srcFileName,binFileName);
				dynamycClassValidators.put(hashCode, clazz);
			} catch (Exception e) {
				try {
					deleteResource(useCase.getResource().getProject(), srcFileName);
				} catch(Exception ex) {}
				try {
					deleteResource(useCase.getResource().getProject(), binFileName);
				} catch(Exception ex) {}
				Console.getInstance().error(ValidatorManager.class, "Error creating validator for "+useCase.getName());
				Activator.getInstance().warning("Error creating validator for "+useCase.getName(), e);
				return new NullValidator(useCase);
			}
			
		}
		try {
			Class<? extends IValidator> clazz = dynamycClassValidators.get(hashCode);
			Constructor<?> constructor = clazz.getConstructor(UseCase.class);
			IValidator validator = (IValidator)constructor.newInstance(useCase);
			dynamycClassValidators.put(hashCode, clazz);
			return validator;
		} catch (Throwable e) {
			Console.getInstance().error(ValidatorManager.class, "Error creating validator for "+useCase.getName());
			Activator.getInstance().warning("Error creating validator for "+useCase.getName(), e);
			return new NullValidator(useCase);
		}
	}
	@SuppressWarnings("unchecked")
	private Class<? extends IValidator> createClass(UseCase useCase, String simpleClassName, String srcFileName, String binFileName) {

		List<Validation> acqRes = useCase.getValidationsAcquirerResponse();
		List<Validation> issReq = useCase.getValidationsIssuerRequest();
		List<Validation> extract = useCase.getValidationsExtract();
		IProject project = useCase.getResource().getProject();
		
		String className = VALIDATORS_TMP_PACKAGE+"."+simpleClassName; 
		String srcValidator = createSrc(project, VALIDATORS_TMP_PACKAGE, simpleClassName, acqRes, issReq, extract);
		File file = new File(project.getLocation().toString()+"/"+srcFileName);
		FileUtils.createSubdirectories(file);
		FileUtils.writeContents(file, srcValidator);

		refreshFolder(project, "src");

		installCalss(project, className);

		File fileBin = new File(project.getLocation().toString()+"/"+binFileName);
		if (!fileBin.exists()) {
			throw new FimetException("There is not exist file "+fileBin.getAbsolutePath());
		}
		byte[] contents = FileUtils.readBytesContents(fileBin); 
		classLoaderManager.installClassBin(className, contents);
		Class<? extends IValidator> clazz = null;
		try {
			clazz = (Class<? extends IValidator>)classLoaderManager.loadClassBin(className);
		} catch (ClassNotFoundException | IllegalArgumentException | SecurityException e) {
			Activator.getInstance().error("Error creating dynamic validator", e);
			throw new FimetException("Error instantianting class "+className,e);
		}
		
		deleteResource(project, srcFileName);
		deleteResource(project, binFileName);
		
		return clazz;
	}
	private void refreshFolder(IProject project, String folder) {
		JobRefreshProject jobRefresh = new JobRefreshProject(project, folder);
		jobRefresh.schedule();
		try {
			jobRefresh.join();
		} catch (InterruptedException e) {
			Activator.getInstance().error("Thread error",e);
			throw new FimetException(e);			
		}
	}
	private void installCalss(IProject project, String className) {
		JobInstallClass jobInstall = new JobInstallClass(project, className);
		jobInstall.schedule();
		try {
			jobInstall.join();
		} catch (InterruptedException e) {
			Activator.getInstance().error("Thread error",e);
			throw new FimetException(e);
		}
	}
	private void deleteResource(IProject project, String resource) {
		JobDeleteResource jobDelete = new JobDeleteResource(project, resource);
		jobDelete.schedule();
		try {
			jobDelete.join();
		} catch (InterruptedException e) {
			Activator.getInstance().error("Thread error",e);
			throw new FimetException(e);
		}
	}
	private String createSrc(IProject project, String packageName, String simpleClassName, List<Validation> acqRes, List<Validation> issReq, List<Validation> extract) {
		StringBuilder s = new StringBuilder();
		s.append("package ").append(packageName).append(";\n\n");
		s.append("import ").append(AbstractValidator.class.getName()).append(";\n");
		s.append("import ").append(UseCase.class.getName()).append(";\n");
		s.append("import ").append(IExtract.class.getName()).append(";\n");
		s.append("import ").append(Message.class.getName()).append(";\n");
		s.append("import ").append(IMessage.class.getName()).append(";\n");
		s.append("import ").append(Map.class.getName()).append(";\n\n");
		
		s.append("public class ").append(simpleClassName).append(" extends ").append(AbstractValidator.class.getSimpleName()).append("{\n");

		s.append("\t").append("public ").append(simpleClassName).append("(UseCase useCase){\n\t\tsuper(useCase);\n\t}").append("\n");
		
		s.append("\t").append("@Override").append("\n");
		s.append("\t").append("public void onAcquirerResponse(Message msg){");
		if (acqRes != null && !acqRes.isEmpty()) {
			s.append("\n");
			String expected;
			for (Validation v : acqRes) {
				if (v.getExpected() instanceof String) {
					expected = "\""+v.getExpected()+"\"";
				} else {
					expected = v.getExpected()+"";
				}
				s
				.append("\t").append("\t").append("newValidation(\""+v.getName()+"\").")
				.append(com.fimet.core.entity.sqlite.pojo.Validation.getOperatorName(v.getOperator()))
				.append("("+expected+","+v.getExpression()+");\n");
			}
		}
		s.append("\t").append("}\n");
		s.append("\t").append("@Override").append("\n");
		s.append("\t").append("public void onIssuerRequest(Message msg){");
		if (issReq != null && !issReq.isEmpty()) {
			s.append("\n");
			String expected;
			for (Validation v : issReq) {
				if (v.getExpected() instanceof String) {
					expected = "\""+v.getExpected()+"\"";
				} else {
					expected = v.getExpected()+"";
				}
				s
				.append("\t").append("\t").append("newValidation(\""+v.getName()+"\").")
				.append(com.fimet.core.entity.sqlite.pojo.Validation.getOperatorName(v.getOperator()))
				.append("("+expected+","+v.getExpression()+");\n");
			}
		}
		s.append("\t").append("}\n");
		s.append("\t").append("@Override").append("\n");
		s.append("\t").append("public void onExtract(IMessage base, Map<Integer, IMessage> add){");
		if (extract != null && !extract.isEmpty()) {
			s.append("\n");
			String expected;
			for (Validation v : extract) {
				if (v.getExpected() instanceof String) {
					expected = "\""+v.getExpected()+"\"";
				} else {
					expected = v.getExpected()+"";
				}
				s
				.append("\t").append("\t").append("newValidation(\""+v.getName()+"\").")
				.append(com.fimet.core.entity.sqlite.pojo.Validation.getOperatorName(v.getOperator()))
				.append("("+expected+","+v.getExpression()+");\n");
			}
		}
		s.append("\t").append("}\n");
		s.append("}");

		return s.toString();
	}
	private Integer hashCode(List<Validation> acqRes, List<Validation> issReq, List<Validation> extractor) {
		int hashCode = 1;
		if (acqRes != null) {
			for (Validation validation : acqRes) {
				hashCode = prime * hashCode + (validation == null ? 0 : validation.hashCode());
			}
		}
		if (issReq != null) {
			for (Validation validation : issReq) {
				hashCode = prime * hashCode + (validation == null ? 0 : validation.hashCode());
			}
		}
		if (extractor != null) {
			for (Validation validation : extractor) {
				hashCode = prime * hashCode + (validation == null ? 0 : validation.hashCode());
			}
		}
		return Math.abs(hashCode);
	}
	@Override
	public void free() {
		dynamycClassValidators.clear();
	}
	@Override
	public void saveState() {
		
	}
}
