package com.fimet.core.stress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.fimet.core.Activator;

public abstract class StressJob extends Job {
	
	private boolean finish = false;
	private IProgressMonitor monitor;
	private int tasksSize;
	private StressExecutor exec;
	
	public StressJob(String name, StressExecutor exec) {
		super(name);
	}
	public synchronized void setFinish(boolean c) {
		finish = c;
	}
	public synchronized boolean isFinish() {
		return finish;
	}
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		//Console.getInstance().info("\n\nRunning " + getName()+"("+queue.size()+" UseCases)\n");
		//this.monitor = SubMonitor.convert(monitor, "Runing...", queue.size());//JobMonitor
		this.monitor = monitor;
		monitor.beginTask("Runing ("+exec+") Use Cases ...", 1);
		while(!(isFinish() || monitor.isCanceled())) {}
		if (monitor.isCanceled()) {
			onFinish();
			return Status.CANCEL_STATUS;
		}
		return new Status(IStatus.OK,Activator.PLUGIN_ID,"Finished execution of "+exec+" UseCases");
	}
	private void onFinish() {
		
	}
	public int getTasksSize() {
		return tasksSize;
	}
	public void stop() {
		if (monitor != null) {
			monitor.done();
		}
	}
}
