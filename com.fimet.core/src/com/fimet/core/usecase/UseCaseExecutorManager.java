package com.fimet.core.usecase;


import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import com.fimet.commons.console.Console;
import com.fimet.core.Activator;
import com.fimet.core.IFimetReport;
import com.fimet.core.IUseCaseJob;
import com.fimet.core.IUseCaseExecutorManager;
import com.fimet.core.JobBuildProject;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class UseCaseExecutorManager implements IUseCaseExecutorManager {
	
	private ConcurrentLinkedQueue<IUseCaseJob> queue = new ConcurrentLinkedQueue<>();
	private ConcurrentLinkedQueue<IUseCaseJob> running = new ConcurrentLinkedQueue<>();
	public UseCaseExecutorManager() {
		super();
	}
	public void run(List<IResource> resources) {
		IProject project = resources.get(0).getProject();
		if (requireBuild(project)) {
			buildProject(project);
		}
		runJob(new UseCaseJob("Runing ("+(resources != null ? resources.size() : 0)+") Uses Case(s)", this,resources));
	}
	public void runF8(IFimetReport f8Report) {									
		IProject project = f8Report.getResources().get(0).getProject();
		if (requireBuild(project)) {
			buildProject(project);
		}
		runJob(new UseCaseJobF8("Runing ("+(f8Report.getResources() != null ? f8Report.getResources().size() : 0)+") Uses Case(s)", this,f8Report));
	}
	private boolean requireBuild(IProject project) {
		return true;
	}
	public void buildProject(IProject project) {
		JobBuildProject job = new JobBuildProject(project);
		job.schedule();
		try {
			job.join();
		} catch (Exception e) {
			Activator.getInstance().error("Thread error",e);
		}
	}
	void runJob(UseCaseJobAbstract job) {
		queue.add(job);
		checkForNextJob();
		
	}
	/*void forceRunJob(UseCaseJobAbstract job) {
		running.add(job);
		job.schedule();
	}*/
	void checkForNextJob() {
		if (running.isEmpty() && !queue.isEmpty()) {
			IUseCaseJob next = queue.remove();
			running.add(next);
			next.schedule();
		}
	}
	public void onFinish(IUseCaseJob job) {
		Console.getInstance().info(UseCaseExecutorManager.class,"\nFinish execution of " + job.getTasksSize()+" Use Case(s)\n");
		running.remove(job);
		checkForNextJob();
	}
	public void stop() {
		if (!running.isEmpty()) {
			for (IUseCaseJob job : queue) {
				job.stop();
			}
		}
		if (!queue.isEmpty()) {
			queue.clear();
		}
	}
	@Override
	public void free() {
		stop();
	}
	@Override
	public void saveState() {
		
	}
}
