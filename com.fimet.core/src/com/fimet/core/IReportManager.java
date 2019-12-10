package com.fimet.core;

import org.eclipse.core.resources.IProject;

import com.fimet.core.usecase.IReportDataMapper;

public interface IReportManager extends IManager {
	public static final String XLSX = "XLSX";
	public static final String TXT = "TXT";

	void report(String type, IProject project);
	IReportDataMapper getReportDataMapper();
}
