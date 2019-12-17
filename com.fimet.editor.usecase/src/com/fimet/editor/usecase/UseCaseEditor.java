package com.fimet.editor.usecase;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.fimet.commons.console.Console;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.Manager;
import com.fimet.core.usecase.UseCase;
import com.fimet.editor.usecase.model.UseCaseModifier;
import com.fimet.editor.usecase.page.AuthorizationPage;
import com.fimet.editor.usecase.page.OverviewPage;
import com.fimet.editor.usecase.page.SimulatorPage;
import com.fimet.editor.usecase.page.SourcePage;
import com.fimet.editor.usecase.page.ValidationsPage;

public class UseCaseEditor extends FormEditor implements ITextEditor {
	
    /**
     * The editor context ID wrt. to the key binding scope
     */
    public static final String EDITOR_CONTEXT_ID = "com.fimet.context.useCaseEditorScope";
    
	static final String USE_CASE_CATEGORY = "__usecase_elements";
	private IPath path;
	private IResource resource;
	private UseCase useCase;
	private UseCaseModifier modifier;
	private SourcePage sourcePage;
	private OverviewPage overviewPage;
	private ValidationsPage validationsPage;
	private SimulatorPage simulatorPage;
	private AuthorizationPage authorizationPage;
	
	public UseCaseEditor() {
		super();
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(StringUtils.maxLength(input.getName(),14));
		try {
			this.path = ((FileEditorInput)input).getPath();
			resource = (IResource)((FileEditorInput)input).getFile();
			useCase = Manager.get(IUseCaseManager.class).parseForEditor(resource);
		} catch (Exception e) {
			Activator.getInstance().error("Parsing use case "+path, e);
			Console.getInstance().error(UseCaseEditor.class, e.getMessage());
		}
		modifier = new UseCaseModifier(this, useCase);
	}
	@Override
	protected void addPages() {
		
		try {
			overviewPage = new OverviewPage(this, "uc-editor-overviewpage","Overview");
			validationsPage = new ValidationsPage(this, "uc-editor-validationsviewpage","Validations");
			simulatorPage = new SimulatorPage(this, "uc-editor-simulatorpage","Simulator");
			authorizationPage = new AuthorizationPage(this, "uc-editor-authorizationpage","Authorization");
			sourcePage = new SourcePage(this, "uc-editor-sourcepage","Source");
			//sourcePage.init(getEditorSite(), getEditorInput());
			
			addPage(overviewPage);
			addPage(validationsPage);
			addPage(simulatorPage);
			addPage(authorizationPage);
			//addPage(sourcePage);
			addPage(4,sourcePage, getEditorInput());
			
			IContextService contextService = (IContextService)getSite().getService(IContextService.class);
			contextService.activateContext(EDITOR_CONTEXT_ID);

		} catch (PartInitException e) {
			Activator.getInstance().error("Use Case Editor", e);
		}
	}
	public SourcePage getSourcePage() {
		return sourcePage;
	}
	public OverviewPage getOverviewPage() {
		return overviewPage;
	}
	public ValidationsPage getValidationsPage() {
		return validationsPage;
	}
	public SimulatorPage getSimulatorPage() {
		return simulatorPage;
	}
	public AuthorizationPage getAuthorizationPage() {
		return authorizationPage;
	}
	@Override
	public void doSave(IProgressMonitor monitor) {
		if (modifier.areDirtyPagesGui()) {
			overviewPage.doSave(monitor);
			validationsPage.doSave(monitor);
			simulatorPage.doSave(monitor);
			authorizationPage.doSave(monitor);
			applyChangesToSource();
			sourcePage.doSave(monitor);
			modifier.cleanDirtyPagesGui();
		} else {
			sourcePage.doSave(monitor);
		}
		this.editorDirtyStateChanged();
	}
	public void undoGui() {
		IProgressMonitor monitor = new NullProgressMonitor();
		applyChangesToPagesGui();
		overviewPage.doSave(monitor);
		validationsPage.doSave(monitor);
		simulatorPage.doSave(monitor);
		authorizationPage.doSave(monitor);
		editorDirtyStateChanged();
		modifier.cleanDirtyPagesGui();
	}
	@Override
	public void doSaveAs() {
		sourcePage.doSaveAs();
		this.editorDirtyStateChanged();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	public UseCase getUseCase() {
		return useCase;
	}
	public void createPartControlSourcePage() {
		if (sourcePage.getPartControl() == null) {
			//overviewPage.getPartControl().getParent()
			sourcePage.createPartControl(getContainer());
			setControl(4, sourcePage.getPartControl());
			sourcePage.getPartControl().setMenu(getContainer().getMenu());
		}
		sourcePage.configureListeners();
	}

	public UseCaseModifier getModifier() {
		return modifier;
	}
	public void applyChangesToSource() {
		overviewPage.commit();
		validationsPage.commit();
		simulatorPage.commit();
		authorizationPage.commit();
		if (modifier.areDirtyPagesGui()) {
			sourcePage.replaceContents(useCase.toJson());
		} else {
			applyChangesToPagesGui();
		}
	}
	public void applyChangesToPagesGui() {
		try {
			modifier.startApplingSourceChanges();
			useCase = Manager.get(IUseCaseManager.class).parseForEditor(getEditorInput().getName(),sourcePage.getContents());
			modifier.setUseCase(useCase);
			overviewPage.update();
			validationsPage.update();
			simulatorPage.update();
			authorizationPage.update();
			modifier.finishApplingSourceChanges();
		} catch (Exception e) {
			Activator.getInstance().error("Parsing Use Case", e);
		}
	}
	/*public void removeMarkDirty() {
		doSaveAs();
		commitPages(false);
		setPartName(StringUtils.maxLength(getEditorInput().getName(),14));
		firePropertyChange(IWorkbenchPartConstants.PROP_PART_NAME);
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
		//this.editorDirtyStateChanged();
	}*/
	public IDocumentProvider getDocumentProvider() {
		return sourcePage.getDocumentProvider();
	}
	@Override
	protected IEditorSite createSite(IEditorPart editor) {
		return new UseCaseEditorSite(this, editor);
	}
	public IPath getPath() {
		return path;
	}
	public IResource getResource() {
		return resource;
	}

	@Override
	public boolean isEditable() {
		return sourcePage.isEditable();
	}

	@Override
	public void doRevertToSaved() {
		sourcePage.doRevertToSaved();
	}

	@Override
	public void setAction(String actionID, IAction action) {
		sourcePage.setAction(actionID, action);		
	}

	@Override
	public IAction getAction(String actionId) {
		return sourcePage.getAction(actionId);
	}

	@Override
	public void setActionActivationCode(String actionId, char activationCharacter, int activationKeyCode,
			int activationStateMask) {
		sourcePage.setActionActivationCode(actionId, activationCharacter, activationKeyCode, activationStateMask);		
	}

	@Override
	public void removeActionActivationCode(String actionId) {
		sourcePage.removeActionActivationCode(actionId);		
	}

	@Override
	public boolean showsHighlightRangeOnly() {
		return sourcePage.showsHighlightRangeOnly();
	}

	@Override
	public void showHighlightRangeOnly(boolean showHighlightRangeOnly) {
		sourcePage.showHighlightRangeOnly(showHighlightRangeOnly);		
	}

	@Override
	public void setHighlightRange(int offset, int length, boolean moveCursor) {
		sourcePage.setHighlightRange(offset, length, moveCursor);		
	}

	@Override
	public IRegion getHighlightRange() {
		return sourcePage.getHighlightRange();
	}

	@Override
	public void resetHighlightRange() {
		sourcePage.resetHighlightRange();		
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return sourcePage.getSelectionProvider();
	}

	@Override
	public void selectAndReveal(int offset, int length) {
		sourcePage.selectAndReveal(offset, length);		
	}
}
