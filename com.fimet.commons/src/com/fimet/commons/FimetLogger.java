package com.fimet.commons;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.commons.utils.FileUtils;
import com.fimet.commons.utils.RuteUtils;

public class FimetLogger {
	private static File log = new File(RuteUtils.getLogsPath()+"fimet.log");
	private static void write(String level, String message) {
		FileUtils.writeContents(log, DateUtils.hhmmss_FMT.format(new Date())+" "+level+":\n"+message+"\n");
	}
	private static void write(String level, String message, Throwable e) {
		if (e != null) {
			PrintWriter printWriter = null;
			StringWriter writer = new StringWriter();
			try {
				printWriter = new PrintWriter(writer);
				e.printStackTrace(printWriter);
			} catch (Exception ex) {
				ex.printStackTrace();
				if (printWriter != null) {
					try {
						printWriter.close();
					} catch (Exception ex2) {}
				}
			}
			FileUtils.appendContents(log, DateUtils.hhmmss_FMT.format(new Date())+" "+level+":\n"+message+"\n"+writer.toString()+"\n");
		} else {
			write(level, message);
		}
	}
	public static void debug(String message) {
		write("DEBUG", message);
	}
	public static void debug(String message, Throwable e) {
		write("DEBUG", message, e);
	}
	public static void info(String message) {
		write("INFO", message);
	}
	public static void info(String message, Throwable e) {
		write("INFO", message, e);
	}
	public static void warning(String message) {
		write("WARNING", message);
	}
	public static void warning(String message, Throwable e) {
		write("WARNING", message, e);
	}
	public static void error(String message) {
		write("ERROR", message);
	}
	public static void error(String message, Throwable e) {
		write("ERROR", message, e);
	}
}
