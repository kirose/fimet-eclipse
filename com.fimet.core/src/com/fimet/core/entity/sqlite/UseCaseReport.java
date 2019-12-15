
package com.fimet.core.entity.sqlite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.core.entity.sqlite.pojo.Notice;
import com.fimet.core.entity.sqlite.pojo.Validation;
import com.fimet.core.entity.sqlite.type.ListNoticeType;
import com.fimet.core.entity.sqlite.type.ListValidationType;
import com.fimet.core.entity.sqlite.type.MapStringString;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "UseCaseReport")
public class UseCaseReport {

	@DatabaseField(id = true)
	private String path;
	@DatabaseField(canBeNull = false)
	private String project;
	@DatabaseField(canBeNull = false)
	private String useCase;
	@DatabaseField(canBeNull = true, persisterClass=MapStringString.class)
	private Map<String, String> data;
	@DatabaseField(canBeNull = true)
	private String responseCode;
	@DatabaseField(canBeNull = true)
	private String error;
	@DatabaseField(canBeNull = false)
	private String acquirer;
	@DatabaseField(canBeNull = true)
	private String issuer;
	@DatabaseField(persisterClass=ListValidationType.class, canBeNull = true)
	private List<Validation> validations;
	@DatabaseField(persisterClass=ListNoticeType.class, canBeNull = true)
	private List<Notice> notices;
	public UseCaseReport() {
		data = new HashMap<>();
	}
	public UseCaseReport(String project, String path, String useCase) {
		super();
		this.project = project;
		this.path = path;
		this.useCase = useCase;
		data = new HashMap<>();
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUseCase() {
		return useCase;
	}
	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<Validation> getValidations() {
		return validations;
	}
	public void setValidations(List<Validation> acquirerValidations) {
		this.validations = acquirerValidations;
	}
	public List<Notice> getNotices() {
		return notices;
	}
	public void setNotices(List<Notice> notices) {
		this.notices = notices;
	}
	public boolean has(String key) {
		return data.containsKey(key);
	}
	public String get(String key) {
		return data.get(key);
	}
	public String put(String key, String value) {
		return data.put(key, value);
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getAcquirer() {
		return acquirer;
	}
	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
}
