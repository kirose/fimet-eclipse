package com.fimet.core;

import java.io.File;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.fimet.core.entity.sqlite.UseCaseReport;

public interface IReport {
	void report(IProject project, File output, List<UseCaseReport> useCases);
}
