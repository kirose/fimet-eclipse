package com.fimet.core.impl.nature;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class BaseProject extends PlatformObject implements IProjectNature {
	private IProject project;

	public BaseProject() {
		super();
	}
	protected void addNature(String natureID) throws CoreException {
		addNature(getProject(), natureID, null);
	}
	protected void removeNature(String natureID) throws CoreException {
		removeNature(getProject(), natureID, null);
	}
	public static void addNature(IProject project, String natureID, IProgressMonitor monitor) throws CoreException {

		IProjectDescription description = project.getDescription();
		
		String[] natures = description.getNatureIds();
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = natureID;

		// validate the natures
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus status = workspace.validateNatureSet(newNatures);

		// only apply new nature, if the status is ok
		if (status.getCode() == IStatus.OK) {
		    description.setNatureIds(newNatures);
		    project.setDescription(description, monitor);
		}
	}
	public static void removeNature(IProject project, String natureID, IProgressMonitor monitor) throws CoreException {

		IProjectDescription description = project.getDescription();
		
		List<String> natures = Arrays.asList(description.getNatureIds());
		if (natures.contains(natureID)) {
			natures.remove(natureID);
			String[] newNatures = natures.toArray(new String[natures.size()]);
			// validate the natures
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateNatureSet(newNatures);

			// only apply new nature, if the status is ok
			if (status.getCode() == IStatus.OK) {
			    description.setNatureIds(newNatures);
			    project.setDescription(description, monitor);
			}
		}

	}
	protected void addToBuildSpec(String builderID) throws CoreException {

		IProjectDescription description = getProject().getDescription();
		ICommand builderCommand = getBuilderCommand(description, builderID);

		if (builderCommand == null) {
			// Add a new build spec
			ICommand command = description.newCommand();
			command.setBuilderName(builderID);
			setBuilderCommand(description, command);
		}
	}

	private ICommand getBuilderCommand(IProjectDescription description, String builderId) {
		ICommand[] commands = description.getBuildSpec();
		for (ICommand command : commands) {
			if (command.getBuilderName().equals(builderId)) {
				return command;
			}
		}
		return null;
	}

	@Override
	public IProject getProject() {
		return project;
	}

	protected IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	protected void removeFromBuildSpec(String builderID) throws CoreException {
		IProjectDescription description = getProject().getDescription();
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderID)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
				description.setBuildSpec(newCommands);
				getProject().setDescription(description, null);
				return;
			}
		}
	}

	private void setBuilderCommand(IProjectDescription description, ICommand newCommand) throws CoreException {

		ICommand[] oldCommands = description.getBuildSpec();
		ICommand oldBuilderCommand = getBuilderCommand(description, newCommand.getBuilderName());

		ICommand[] newCommands;

		if (oldBuilderCommand == null) {
			// Add a build spec after other builders
			newCommands = new ICommand[oldCommands.length + 1];
			System.arraycopy(oldCommands, 0, newCommands, 0, oldCommands.length);
			newCommands[oldCommands.length] = newCommand;
		} else {
			for (int i = 0, max = oldCommands.length; i < max; i++) {
				if (oldCommands[i] == oldBuilderCommand) {
					oldCommands[i] = newCommand;
					break;
				}
			}
			newCommands = oldCommands;
		}

		// Commit the spec change into the project
		description.setBuildSpec(newCommands);
		getProject().setDescription(description, null);
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}
}
