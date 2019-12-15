package com.fimet.core.impl;

import java.util.List;

import com.fimet.core.IUseCaseReportManager;
import com.fimet.core.entity.sqlite.UseCaseReport;
import com.fimet.persistence.sqlite.dao.UseCaseReportDAO;

public class UseCaseReportManager implements IUseCaseReportManager {
	
	@Override
	public UseCaseReport findByPath(String path) {
		return UseCaseReportDAO.getInstance().findByPath(path);
	}

	@Override
	public List<UseCaseReport> findByProject(String project) {
		return UseCaseReportDAO.getInstance().findByProject(project);
	}

	@Override
	public List<UseCaseReport> findByIdJob(Long idJob) {
		return UseCaseReportDAO.getInstance().findByIdJob(idJob);
	}

	@Override
	public List<String> findProjects() {
		return UseCaseReportDAO.getInstance().findProjects();
	}

	@Override
	public void save(UseCaseReport entity) {
		UseCaseReportDAO.getInstance().insertOrUpdate(entity);
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
}
