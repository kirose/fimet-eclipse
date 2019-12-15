/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
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
package com.fimet.editor.usecase.validator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import org.eclipse.jdt.core.IJavaElement;


import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
import org.eclipse.jdt.internal.ui.wizards.NewWizardMessages;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.fimet.commons.utils.ViewUtils;

public class NewValidatorClassCreationWizard extends NewElementWizard {

	private NewValidatorClassWizardPage fPage;
    private boolean fOpenEditorOnFinish;

	public NewValidatorClassCreationWizard(NewValidatorClassWizardPage page, boolean openEditorOnFinish) {
		setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWCLASS);
		setDialogSettings(JavaPlugin.getDefault().getDialogSettings());
		setWindowTitle("New Class Validator");

		fPage= page;
		fOpenEditorOnFinish= openEditorOnFinish;
	}

	public NewValidatorClassCreationWizard() {
		this(null, true);
	}

	/*
	 * @see Wizard#createPages
	 */
	@Override
	public void addPages() {
		super.addPages();
		if (fPage == null) {
			fPage= new NewValidatorClassWizardPage();
			fPage.setWizard(this);
			fPage.init(getSelection());
		}
		addPage(fPage);
	}

	@Override
	protected boolean canRunForked() {
		return !fPage.isEnclosingTypeSelected();
	}

	@Override
	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
		fPage.createType(monitor); // use the full progress monitor
	}

	@Override
	public boolean performFinish() {
		warnAboutTypeCommentDeprecation();
		boolean res= super.performFinish();
		if (res) {
			IResource resource= fPage.getModifiedResource();
			if (resource != null) {
				selectAndReveal(resource);
				if (fOpenEditorOnFinish) {
					openResource((IFile) resource);
				}
			}
		}
		return res;
	}

	@Override
	public IJavaElement getCreatedElement() {
		return fPage.getCreatedType();
	}
}
