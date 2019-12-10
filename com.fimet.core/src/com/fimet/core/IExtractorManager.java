package com.fimet.core;

import com.fimet.core.usecase.UseCase;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public interface IExtractorManager extends IManager {
	public void manage(UseCase useCase);
	public void dispatch(IExtract next);
	public void start();
	public void stop();
	public void setReaderCycleTime(int seconds);
	public void setGarbageCycleTime(int seconds);
}
