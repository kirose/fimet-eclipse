package com.fimet.core.usecase;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.fimet.core.Activator;
import com.fimet.core.IUseCaseJob;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Clase abstracta que representa una Job de ejecucion 
 * (ver view Progress, se puede visualizar el progreso o detenerlo)
 */
public abstract class UseCaseJobAbstract extends Job implements UseCaseJobListener, IUseCaseJob {
	
	private boolean finish = false;
	private UseCaseExecutorManager manager;
	private IProgressMonitor monitor;
	private int tasksSize;
	private ConcurrentLinkedQueue<UseCaseExecutor> queue = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<UseCaseExecutor> running = new ConcurrentLinkedQueue<>();
	private UseCaseExecutor runningTask;
	
	public UseCaseJobAbstract(String name, UseCaseExecutorManager manager, List<IResource> resources) {
		super(name);
		this.manager = manager;
		if (resources != null && !resources.isEmpty()) {
			tasksSize = resources.size();
			IResource lastResource = resources.remove(resources.size()-1);
			for (IResource r : resources) {
				queue.add(new UseCaseExecutor(r,this));
			}
			UseCaseExecutor last = new UseCaseExecutor(lastResource, this, (UseCaseExecutor executor)->{
				if (!monitor.isCanceled()) {
					UseCaseJobAbstract.this.onComplete();
				}
			});
			queue.add(last);
		} else {
			onComplete();
		}		
	}
	void runTask(UseCaseExecutor executor) {
		queue.add(executor);
		checkForNextTask();
	}
	public void forceRunTask(UseCaseExecutor executor) {
		running.add(executor);
		executor.execute();
	}
	private void checkForNextTask() {
		if (monitor.isCanceled()) {
			onFinish();
		} else {
			if (running.isEmpty() && !queue.isEmpty()) {
				runningTask = queue.remove();
				running.add(runningTask);
				try {
					runningTask.execute();
				} catch (Exception e) {
					Activator.getInstance().error("Executing UseCaseReport "+runningTask.getName(), e);
					if (!(runningTask.isStoped() || runningTask.isCompleted())) {
						runningTask.stop();
					}
				}
			}
		}
	}
	@Override
	public void onStart(UseCaseExecutor executor) {}
	@Override
	public void onStop(UseCaseExecutor executor) {}
	@Override
	public void onTimeout(UseCaseExecutor executor) {}
	@Override
	public void onComplete(UseCaseExecutor executor) {}
	@Override
	public void onFinish(UseCaseExecutor executor) {
		running.remove(executor);
		monitor.worked(1);
		checkForNextTask();
	}
	protected void onComplete() {
		onFinish();
	}
	protected void onFinish() {
		manager.onFinish(this);
		if (monitor != null) {
			monitor.done();
		}
		setFinish(true);
	}
	public UseCaseExecutorManager getManager() {
		return manager;
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
		monitor.beginTask("Runing ("+queue.size()+") Use Cases ...", queue.size());
		checkForNextTask();
		while(!(isFinish() || monitor.isCanceled())) {}
		if (monitor.isCanceled()) {
			onFinish();
			return Status.CANCEL_STATUS;
		}
		return new Status(IStatus.OK,Activator.PLUGIN_ID,"Finished execution of "+queue.size()+" UseCases");
	}
	public int getTasksSize() {
		return tasksSize;
	}
	public void stop() {
		if (!running.isEmpty()) {
			for (UseCaseExecutor executor : queue) {
				executor.stop();
			}
		}
		if (monitor != null) {
			monitor.done();
		}
	}
}
