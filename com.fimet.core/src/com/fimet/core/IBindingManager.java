package com.fimet.core;

import org.eclipse.core.commands.IHandler;

import com.fimet.core.ISO8583.parser.IParser;

public interface IBindingManager extends IManager {
	void uninstall(IParser parser);
	void uninstall(String commandId);
	void installCommands();
	void install(IParser parser);
	void install(
		IHandler handler,
		String commandId,
		String commandName,
		String schemeId,
		String contextId,
		String categoryId,
		String categoryName,
		String keySequence
	);
}
