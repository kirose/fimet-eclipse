package com.fimet.editor.usecase.page;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.text.undo.DocumentUndoEvent;
import org.eclipse.text.undo.DocumentUndoManagerRegistry;
import org.eclipse.text.undo.IDocumentUndoListener;
import org.eclipse.text.undo.IDocumentUndoManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import com.boothen.jsonedit.editor.*;
import com.fimet.editor.usecase.Activator;
import com.fimet.editor.usecase.UseCaseEditor;

public class SourcePage extends JsonTextEditor implements IFormPage, IGotoMarker {
    
	private UseCaseEditor editor;
	private Control control;
	private String id;
	private int index;
	private IContextActivation editorContext; 
	public SourcePage(UseCaseEditor editor, String id, String title) {
		super();
		this.editor = editor;
		this.id = id;
	}
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		setPartName("Source");
	}
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		Control[] children = parent.getChildren();
		this.control = children[children.length - 1];
		configureListeners();
	}

	@Override
	public void initialize(FormEditor editor) {
	}

	@Override
	public FormEditor getEditor() {
		return editor;
	}

	@Override
	public IManagedForm getManagedForm() {
		return null;
	}

	@Override
	public void setActive(boolean active) {
		if (active) {
			this.getEditor().setFocus();
			if (editor.getModifier().areDirtyPagesGui()) {
				editor.applyChangesToSource();
				editor.getModifier().cleanDirtyPagesGui();
			}
			activateEditorContext();
		}
	}

	@Override
	public boolean isActive() {
		return this.equals(editor.getActivePageInstance());
	}

	@Override
	public boolean canLeaveThePage() {
		return true;
	}

	@Override
	public Control getPartControl() {
		return control;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;		
	}

	@Override
	public boolean isEditor() {
		return true;
	}

	@Override
	public boolean selectReveal(Object object) {
		if (object instanceof IMarker) {
			IDE.gotoMarker(this, (IMarker) object);
			return true;
		}
		return false;
	}
	public void update() {
		if (editor.getUseCase() != null) {
			replaceContents(editor.getUseCase().toJson());
		}
	}
    @Override
    public void doSave(IProgressMonitor monitor) {
        super.doSave(monitor);
		editor.applyChangesToPagesGui();
		editor.getModifier().cleanDirtyPagesGui();
    }

    @Override
    public void doSaveAs() {
        super.doSaveAs();
		editor.applyChangesToPagesGui();
		editor.getModifier().cleanDirtyPagesGui();
    }
	public void replaceContents(String json) {
		try {
			this.getSourceViewer().getDocument().replace(0, this.getSourceViewer().getDocument().getLength(), json);
		} catch (BadLocationException e) {
			Activator.getInstance().error("Replacing Json", e);
		}		
	}
	public String getContents() {
		return getSourceViewer().getDocument() != null ? getSourceViewer().getDocument().get() : null;
	}
	public void configureListeners() {
		configureListeners(getDocumentProvider().getDocument(getEditorInput()));
	}
	public IDocument getDocument() {
		return getDocumentProvider().getDocument(getEditorInput());
	}
	private IDocumentUndoManager documentUndoManager;
    public void configureListeners(IDocument document) {
    	documentUndoManager = DocumentUndoManagerRegistry.getDocumentUndoManager(document);
        documentUndoManager.addDocumentUndoListener(new IDocumentUndoListener() {
			@Override
			public void documentUndoNotification(DocumentUndoEvent event) {
				if (event.getEventType() == org.eclipse.core.commands.operations.OperationHistoryEvent.DONE) {
					if (event.getSource() == null || event.getSource() instanceof org.eclipse.ui.operations.UndoActionHandler) {
						editor.applyChangesToPagesGui();
						editor.getModifier().cleanDirtyPagesGui();
					} else if (event.getSource() instanceof org.eclipse.ui.operations.RedoActionHandler) {
						editor.applyChangesToPagesGui();
						editor.getModifier().cleanDirtyPagesGui();
					}
				}
			}
		});
    }
    public IDocumentUndoManager getDocumentUndoManager() {
		return documentUndoManager;
	}
    
	public void activateEditorContext() {
		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
		editorContext = contextService.activateContext("com.boothen.jsonedit.jsonEditorScope");
		//editorContext = contextService.activateContext(EDITOR_CONTEXT_ID);
	}
    public void deactivateEditorContext() {
    	if (editorContext != null) {
    		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
    		contextService.deactivateContext(editorContext);    		
    	}
    }
}
