package com.fimet.core;

import com.fimet.core.usecase.UseCase;

public interface ILogManager extends IManager {
	public void manage(UseCase useCase);
	public void setReaderCycleTime(int seconds);
	public void setGarbageCycleTime(int seconds);
}
