/*******************************************************************************
 * Copyright (c) 2000, 2011 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.fimet.editor.usecase.wizard.newproject;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.fimet.commons.messages.Messages;

import org.eclipse.jdt.core.IJavaElement;

import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageOne;
import org.eclipse.jdt.ui.wizards.NewJavaProjectWizardPageTwo;

import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.jdt.internal.ui.util.ExceptionHandler;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FimetProjectWizard extends NewElementWizard implements IExecutableExtension {

	private NewFimetProjectWizardPage1 fFirstPage;
	private NewFimetProjectWizardPage2 fSecondPage;
	private NewFimetProjectWizardPage3 fThirdPage;
	

	private IConfigurationElement fConfigElement;

	public FimetProjectWizard() {
		this(null, null);
	}

	public FimetProjectWizard(NewFimetProjectWizardPage1 pageOne, NewFimetProjectWizardPage3 pageTwo) {
		setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWJPRJ);
		setDialogSettings(JavaPlugin.getDefault().getDialogSettings());
		setWindowTitle(Messages.FIMETProjectWizard_title);

		fFirstPage= pageOne;
		fThirdPage= pageTwo;
	}

	@Override
	public void addPages() {
		if (fFirstPage == null)
			fFirstPage= new NewFimetProjectWizardPage1();
		addPage(fFirstPage);

		if (fSecondPage == null)
			fSecondPage = new NewFimetProjectWizardPage2(fFirstPage);
		addPage(fSecondPage);
		
		if (fThirdPage == null)
			fThirdPage = new NewFimetProjectWizardPage3(fFirstPage, fSecondPage);
		addPage(fThirdPage);



		fFirstPage.init(getSelection(), getActivePart());
	}

	@Override
	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
		fThirdPage.performFinish(monitor); // use the full progress monitor
	}

	@Override
	public boolean performFinish() {
		boolean res= super.performFinish();
		if (res) {
			final IJavaElement newElement= getCreatedElement();

			IWorkingSet[] workingSets= fFirstPage.getWorkingSets();
			if (workingSets.length > 0) {
				PlatformUI.getWorkbench().getWorkingSetManager().addToWorkingSets(newElement, workingSets);
			}

			BasicNewProjectResourceWizard.updatePerspective(fConfigElement);
			selectAndReveal(fThirdPage.getJavaProject().getProject());

			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					IWorkbenchPart activePart= getActivePart();
					if (activePart instanceof IPackagesViewPart) {
						PackageExplorerPart view= PackageExplorerPart.openInActivePerspective();
						view.tryToReveal(newElement);
					}
				}
			});
		}
		return res;
	}

	private IWorkbenchPart getActivePart() {
		IWorkbenchWindow activeWindow= getWorkbench().getActiveWorkbenchWindow();
		if (activeWindow != null) {
			IWorkbenchPage activePage= activeWindow.getActivePage();
			if (activePage != null) {
				return activePage.getActivePart();
			}
		}
		return null;
	}

	@Override
	protected void handleFinishException(Shell shell, InvocationTargetException e) {
		String title= Messages.FIMETProjectWizard_op_error_title;
		String message= Messages.FIMETProjectWizard_op_error_create_message;
		ExceptionHandler.handle(e, getShell(), title, message);
	}

	/*
	 * Stores the configuration element for the wizard.  The config element will be used
	 * in <code>performFinish</code> to set the result perspective.
	 */
	@Override
	public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
		fConfigElement= cfig;
	}

	@Override
	public boolean performCancel() {
		fThirdPage.performCancel();
		return super.performCancel();
	}

	@Override
	public IJavaElement getCreatedElement() {
		return fThirdPage.getJavaProject();
	}
}
