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

package com.fimet.editor.usecase.validator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.utils.ViewUtils;

import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;

import org.eclipse.jdt.internal.ui.IJavaHelpContextIds;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.actions.ActionMessages;
import org.eclipse.jdt.internal.ui.wizards.NewClassCreationWizard;

/**
 * <p>Action that opens the new class wizard. The action initialized the wizard with either the selection
 * as configured by {@link #setSelection(IStructuredSelection)} or takes a preconfigured
 * new class wizard page, see {@link #setConfiguredWizardPage(NewClassWizardPage)}.</p>
 *
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 *
 * @since 3.2
 *
 * @noextend This class is not intended to be subclassed by clients.
 */
public class OpenNewClassValidatorWizardAction extends AbstractOpenWizardAction {

	private NewValidatorClassWizardPage fPage;
	private boolean fOpenEditorOnFinish;
	private IProject project;
	private NewValidatorClassCreationWizard wizard;

	/**
	 * Creates an instance of the <code>OpenNewClassWizardAction</code>.
	 */
	public OpenNewClassValidatorWizardAction(IProject project) {
		this.project = project;
		setText(ActionMessages.OpenNewClassWizardAction_text);
		setDescription(ActionMessages.OpenNewClassWizardAction_description);
		setToolTipText(ActionMessages.OpenNewClassWizardAction_tooltip);
		setImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWCLASS);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IJavaHelpContextIds.OPEN_CLASS_WIZARD_ACTION);
		
		fPage= null;
		fOpenEditorOnFinish= true;
	}
	public OpenNewClassValidatorWizardAction() {
		this(null);
	}

	/**
	 * Sets a page to be used by the wizard or <code>null</code> to use a page initialized with values
	 * from the current selection (see {@link #getSelection()} and {@link #setSelection(IStructuredSelection)}).
	 * @param page the page to use or <code>null</code>
	 */
	public void setConfiguredWizardPage(NewValidatorClassWizardPage page) {
		fPage= page;
	}

	/**
	 * Specifies if the wizard will open the created type with the default editor. The default behaviour is to open
	 * an editor.
	 *
	 * @param openEditorOnFinish if set, the wizard will open the created type with the default editor
	 *
	 * @since 3.3
	 */
	public void setOpenEditorOnFinish(boolean openEditorOnFinish) {
		fOpenEditorOnFinish= openEditorOnFinish;
	}

	@Override
	protected final INewWizard createWizard() throws CoreException {
		wizard = new NewValidatorClassCreationWizard(fPage, fOpenEditorOnFinish);
		return wizard;
	}
	@Override
	protected IStructuredSelection getSelection() {
		if (project != null) {
			return new StructuredSelection(project);
		}
		/*ISelection selection = ViewUtils.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			return (IStructuredSelection)selection;
		}*/
		return super.getSelection();
	}
	public NewValidatorClassCreationWizard getWizard() {
		return wizard;
	}
}
