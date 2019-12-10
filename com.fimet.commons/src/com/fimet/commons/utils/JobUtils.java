package com.fimet.commons.utils;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;


public class JobUtils {
	public static int idJob;
	private JobUtils() {}
	public static String runJob(Runnable runnable)
	{
		new UtilJob(runnable).schedule();		
		return null;
	}
	public static class UtilJob extends Job {
		private Runnable runnable;
		public UtilJob(Runnable runnable) {
			super("Job "+(idJob++));
			this.runnable = runnable;
		}
		public IStatus run(IProgressMonitor monitor) {
			runnable.run();
			return Status.OK_STATUS;
		}
	}
}
