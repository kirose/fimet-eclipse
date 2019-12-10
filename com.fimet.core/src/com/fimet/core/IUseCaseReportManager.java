package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.UseCaseReport;

public interface IUseCaseReportManager extends IManager {
	public UseCaseReport findByPath(String path);
	public List<UseCaseReport> findByProject(String project);
	public List<UseCaseReport> findByIdJob(Long idJob);
	public List<String> findProjects();
	public void save(UseCaseReport entity);
}
