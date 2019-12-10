package com.fimet.core.impl;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class FimetPerspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {

		/*MessageConsole myConsole = new MessageConsole("Console Prespective", null); // declare console
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{myConsole});

		PrintStream myS = new PrintStream(myConsole.newMessageStream());
		System.setOut(myS); // link standard output stream to the console
		System.setErr(myS); // link error output stream to the console*/
		
	}
}
