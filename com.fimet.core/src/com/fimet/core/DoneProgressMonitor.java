package com.fimet.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

public class DoneProgressMonitor implements IProgressMonitor {
	private IDoneListener listener;
	private IProgressMonitor parent;
	private boolean done = false;
	public DoneProgressMonitor(IDoneListener listener) {
		this.parent = new NullProgressMonitor();
		this.listener = listener;
	}
	public DoneProgressMonitor(IProgressMonitor parent, IDoneListener listener) {
		this.parent = parent;
		this.listener = listener;
	}
	@Override
	public void beginTask(String name, int totalWork) {
		parent.beginTask(name, totalWork);
	}
	@Override
	public void done() {
		listener.done();
		parent.done();
		done = true;
	}
	@Override
	public void internalWorked(double work) {
		parent.internalWorked(work);
	}
	@Override
	public boolean isCanceled() {
		return parent.isCanceled();
	}
	@Override
	public void setCanceled(boolean value) {
		parent.setCanceled(value);
	}
	@Override
	public void setTaskName(String name) {
		parent.setTaskName(name);			
	}
	@Override
	public void subTask(String name) {
		parent.subTask(name);			
	}
	@Override
	public void worked(int work) {
		parent.worked(work);
	}
	public boolean isDone() {
		return done;
	}
	
}
