package com.fimet.core;

import java.util.Date;

import com.fimet.core.entity.sqlite.ExtractorWindow;


public interface IExtractorWindowManager extends IManager {
	public ExtractorWindow windowFor(Date date);
	public ExtractorWindow nextWindow(ExtractorWindow window);
}
