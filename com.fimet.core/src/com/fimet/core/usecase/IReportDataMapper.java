package com.fimet.core.usecase;

import com.fimet.core.entity.sqlite.UseCaseReport;

public interface IReportDataMapper {
	void map(UseCase useCase, UseCaseReport data);
}
