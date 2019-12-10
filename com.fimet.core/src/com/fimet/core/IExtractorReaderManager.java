package com.fimet.core;

import com.fimet.core.entity.sqlite.ExtractorWindow;

public interface IExtractorReaderManager extends IManager {
	public boolean hasNext();
	public IExtract next();
	public void startCycle();
	public void endCycle();
	public ExtractorWindow getActiveWindow();
}
