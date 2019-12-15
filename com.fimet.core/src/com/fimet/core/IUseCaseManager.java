package com.fimet.core;

import java.io.File;

import org.eclipse.core.resources.IResource;

import com.fimet.core.usecase.UseCase;

public interface IUseCaseManager extends IManager {
	UseCase parseForEditor(IResource resource);
	UseCase parseForEditor(String name, String json);
	UseCase parse(String name, String json);
	UseCase parse(File file);
	UseCase parse(IResource resource);
	UseCase parseForExecution(IResource resource);
}
