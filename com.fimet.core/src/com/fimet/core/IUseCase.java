package com.fimet.core;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;

import com.fimet.core.IValidator;
import com.fimet.core.entity.sqlite.pojo.Notice;
import com.fimet.core.usecase.Acquirer;
import com.fimet.core.usecase.Issuer;

public interface IUseCase {
	
	public IResource getResource();
	public void setResource(IResource resource);
	public String getName();
	public void setName(String name);
	public IFolder getFolder();
	public void setFolder(IFolder folder);
	public String toJson();
	public Acquirer getAcquirer();
	public void setAcquirer(Acquirer acquirer);
	public Issuer getIssuer();
	public void setIssuer(Issuer issuer);
	public Integer getExecutionTime();
	public void setExecutionTime(Integer timeExecution);
	public IUseCase clone() throws CloneNotSupportedException;
	public String getValidatorClass();
	public void setValidatorClass(String validatorClass);
	public IValidator getValidator();
	public void setValidator(IValidator validator);
	public boolean hasValidationsAcquirerResponse();
	public boolean hasValidationsIssuerRequest();
	public List<com.fimet.core.usecase.Validation> getValidationsAcquirerResponse();
	public List<com.fimet.core.usecase.Validation> getValidationsIssuerRequest();
	public boolean hasFieldIssReq(String idField);
	public boolean hasFieldIssReq(int idField);
	public byte[] getFieldIssReq(int idField);
	public String getValueIssReq(int idField);
	public byte[] getFieldIssReq(String idField);
	public String getValueIssReq(String idField);
	public boolean hasFieldIssRes(String idField);
	public boolean hasFieldIssRes(int idField);
	public byte[] getFieldIssRes(int idField);
	public byte[] getFieldIssRes(String idField);
	public String getValueIssRes(int idField);
	public String getValueIssRes(String idField);
	public boolean hasFieldAcqReq(String idField);
	public boolean hasFieldAcqReq(int idField);
	public byte[] getFieldAcqReq(int idField);
	public String getValueAcqReq(int idField);
	public String getValueAcqReq(String idField);
	public byte[] getFieldAcqReq(String idField);
	public boolean hasFieldAcqRes(String idField);
	public boolean hasFieldAcqRes(int idField);
	public String getValueAcqRes(int idField);
	public byte[] getFieldAcqRes(String idField);
	public byte[] getFieldAcqRes(int idField);
	public String getValueAcqRes(String idField);
	public List<Notice> getNotices();
	public void setNotices(List<Notice> notices);
	public void addNotice(Notice notice);
}
