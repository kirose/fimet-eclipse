package com.fimet.commons.console;

import java.io.PrintStream;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.MessageConsoleStream;

import com.fimet.commons.Color;

public class Console extends org.eclipse.ui.console.MessageConsole {
	public static final int NONE = 0;
	public static final int INFO = 1;
	public static final int WARNING = 2;
	public static final int ERROR = 4;
	public static final int DEBUG = 8;
	public static final int DEBUG_FINNER = 8;
	private static int level = INFO | WARNING | ERROR;

	private static Console console;
	private PrintStream printStreamClass;
	private PrintStream printStreamInfo;
	private PrintStream printStreamWarning;
	private PrintStream printStreamError;
	private PrintStream printStreamDebug;
	private PrintStream printStream;
	public static void setLevel(int level) {
		Console.level = level;
	}
	public synchronized static Console getInstance() {
		if (console == null) {
			console = new Console();
		}
		return console;
	}
	public Console() {
		super("Console FIMET", null);
		org.eclipse.ui.console.ConsolePlugin.getDefault().getConsoleManager().addConsoles(new org.eclipse.ui.console.IConsole[]{this});
		MessageConsoleStream stream;
		printStream = new PrintStream(newMessageStream());
		stream = newMessageStream();
		stream.setColor(Color.BLUE_LIGHT);
		printStreamClass = new PrintStream(stream);
		stream = newMessageStream();
		stream.setColor(Color.BLUE);
		printStreamInfo = new PrintStream(stream);
		stream = newMessageStream();
		stream.setColor(Color.ORANGE);
		printStreamWarning = new PrintStream(stream);
		stream = newMessageStream();
		stream.setColor(Color.RED);
		printStreamError = new PrintStream(stream);
		stream = newMessageStream();
		stream.setColor(Color.CYAN);
		printStreamDebug = new PrintStream(stream);
		
		//stream.setActivateOnWrite(false);
	}
	synchronized public void info(Class<?> clazz, String message) {
		if (isEnabledInfo() && PlatformUI.isWorkbenchRunning()) {
			printStreamInfo.append("INFO");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printStream.append(message+'\n');
		}
	}
	synchronized public void warning(Class<?> clazz, String message) {
		if (isEnabledWarning() && PlatformUI.isWorkbenchRunning()) {
			printStreamWarning.append("WARNING");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printStream.append(message).append('\n');
		}
	}
	synchronized public void warning(Class<?> clazz, Throwable e) {
		if (isEnabledWarning() && PlatformUI.isWorkbenchRunning()) {
			printStreamWarning.append("WARNING");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			e.printStackTrace(printStream);
			printStream.append('\n');
		}
	}
	synchronized public void warning(Class<?> clazz, String message, Throwable e) {
		if (isEnabledWarning() && PlatformUI.isWorkbenchRunning()) {
			printStreamWarning.append("WARNING");
			printStreamClass.append(" ["+clazz.getName()+"]:\n").append(message).append('\n');
			e.printStackTrace(printStream);
			printStream.append('\n');
		}
	}
	synchronized public void error(Class<?> clazz, String message) {
		if (isEnabledError() && PlatformUI.isWorkbenchRunning()) {
			printStreamError.append("ERROR");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printStream.append(message).append('\n');
			printStream.append('\n');
		}
	}
	synchronized public void error(Class<?> clazz, Throwable e) {
		if (isEnabledError() && PlatformUI.isWorkbenchRunning()) {
			printStreamError.append("ERROR");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			e.printStackTrace(printStream);
			printStream.append('\n');
		}
	}
	synchronized public void error(Class<?> clazz, String message, Throwable e) {
		if (isEnabledError() && PlatformUI.isWorkbenchRunning()) {
			printStreamError.append("ERROR");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printStream.append(message).append('\n');
			e.printStackTrace(printStream);
			printStream.append('\n');
		}
	}
	synchronized public void debug(Class<?> clazz, String message) {
		if (isEnabledDebug() && PlatformUI.isWorkbenchRunning()) {
			printStreamDebug.append("DEBUG");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printStream.append(message).append('\n');
		}
	}
	synchronized public void debug(Class<?> clazz, String message, Throwable e) {
		if (isEnabledDebug() && PlatformUI.isWorkbenchRunning()) {
			printStreamDebug.append("DEBUG");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printStream.append(message).append('\n');
			e.printStackTrace(printStream);
			printStream.append('\n');
		}
	}
	synchronized public void debug(Class<?> clazz, IPrintable printable) {
		if (isEnabledDebug() && PlatformUI.isWorkbenchRunning()) {
			printStreamDebug.append("DEBUG");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printable.print(this);
		}
	}
	synchronized public void info(Class<?> clazz, IPrintable printable) {
		if (isEnabledInfo() && PlatformUI.isWorkbenchRunning()) {
			printStreamInfo.append("INFO");
			printStreamClass.append(" ["+clazz.getName()+"]:\n");
			printable.print(this);
		}
	}
	public PrintStream getPrintStreamClass() {
		return printStreamClass;
	}
	public PrintStream getPrintStreamInfo() {
		return printStreamInfo;
	}
	public PrintStream getPrintStreamWarning() {
		return printStreamWarning;
	}
	public PrintStream getPrintStreamError() {
		return printStreamError;
	}
	public PrintStream getPrintStreamDebug() {
		return printStreamDebug;
	}
	public PrintStream getPrintStream() {
		return printStream;
	}
	public static boolean isEnabledInfo() {
		return (level & INFO) > 0;
	}
	public static boolean isEnabledDebug() {
		return (level & DEBUG) > 0;
	}
	public static boolean isEnabledWarning() {
		return (level & WARNING) > 0;
	}
	public static boolean isEnabledError() {
		return (level & ERROR) > 0;
	}
}
