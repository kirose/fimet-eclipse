package com.fimet.editor.stress.page;

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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;

import com.fimet.editor.json.JsonTextEditor;
import com.fimet.editor.stress.Activator;
import com.fimet.editor.stress.StressEditor;

public class SourcePage extends JsonTextEditor implements IFormPage, IGotoMarker {
	private StressEditor editor;
	private Control control;
	private String id;
	private int index;
	public SourcePage(StressEditor editor, String id, String title) {
		super(title);
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
			if (editor.getModifier().areDirtyPagesGui()) {
				editor.applyChangesToSource();
				editor.getModifier().cleanDirtyPagesGui();
			}
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
		if (editor.getStress() != null) {
			replaceContents(editor.getStress().toJson());
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
		return getSourceViewer().getDocument().get();
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
}
