package com.fimet.editor.stress;

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
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.stress.Stress;
import com.fimet.editor.stress.model.StressModifier;
import com.fimet.editor.stress.page.OverviewPage;
import com.fimet.editor.stress.page.SourcePage;
import com.fimet.parser.util.ParserUtils;

public class StressEditor extends FormEditor implements ITextEditor {
	static final String STRESS_CATEGORY = "__stress_elements";
	private IPath path;
	private IResource resource;
	private Stress stress;
	private StressModifier modifier;
	private SourcePage sourcePage;
	private OverviewPage overviewPage;
	
	public StressEditor() {
		super();
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName(StringUtils.maxLength(input.getName(),14));
		try {
			this.path = ((FileEditorInput)input).getPath();
			resource = (IResource)((FileEditorInput)input).getFile();
			stress = ParserUtils.parseStress(resource);
		} catch (Exception e) {
			Activator.getInstance().error("Parsing Json use case", e);
		}
		modifier = new StressModifier(this, stress);
	}
	@Override
	protected void addPages() {
		try {
			overviewPage = new OverviewPage(this, "stress-editor-overviewpage","Overview");
			sourcePage = new SourcePage(this, "stress-editor-sourcepage","Source");
			addPage(overviewPage);
			addPage(1,sourcePage, getEditorInput());

		} catch (PartInitException e) {
			Activator.getInstance().error("Stress Editor", e);
		}
	}
	public SourcePage getSourcePage() {
		return sourcePage;
	}
	public OverviewPage getOverviewPage() {
		return overviewPage;
	}
	@Override
	public void doSave(IProgressMonitor monitor) {
		if (modifier.areDirtyPagesGui()) {
			overviewPage.doSave(monitor);
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

	public Stress getStress() {
		return stress;
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

	public StressModifier getModifier() {
		return modifier;
	}
	public void applyChangesToSource() {
		overviewPage.commit();
		if (modifier.areDirtyPagesGui()) {
			sourcePage.replaceContents(stress.toJson());
		} else {
			applyChangesToPagesGui();
		}
	}
	public void applyChangesToPagesGui() {
		try {
			modifier.startApplingSourceChanges();
			stress = ParserUtils.parseStress(getEditorInput().getName(),sourcePage.getContents());
			modifier.setStress(stress);
			overviewPage.update();
			modifier.finishApplingSourceChanges();
		} catch (Exception e) {
			Activator.getInstance().error("Parsing Stress file", e);
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
		return new StressEditorSite(this, editor);
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
