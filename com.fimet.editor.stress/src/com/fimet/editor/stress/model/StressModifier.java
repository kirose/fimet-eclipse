package com.fimet.editor.stress.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.Adapter;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocket;
import com.fimet.core.stress.Stress;
import com.fimet.core.stress.StressAcquirer;
import com.fimet.editor.stress.StressEditor;

public class StressModifier  implements IModifier {
	private Stress stress;
	private StressEditor editor;
	private boolean applingSourceChanges;
	private boolean areDirtyPagesGui;
	
	public StressModifier(StressEditor editor, Stress stress) {
		super();
		this.editor = editor;
		this.stress = stress;
	}
	public void setStress(Stress stress) {
		this.stress = stress;
	}
	public List<StressAcquirer> getAcquirers(){
		return stress.getAcquirers();
	}

	public void modifyAcquirers(List<StressAcquirer> files){
		stress.setAcquirers(files);
	}
	public void startApplingSourceChanges() {
		applingSourceChanges = true;
	}
	public void finishApplingSourceChanges() {
		applingSourceChanges = false;
	}
	public boolean isApplingSourceChanges() {
		return applingSourceChanges;
	}
	public void cleanDirtyPagesGui() {
		areDirtyPagesGui = false;
	}
	public boolean areDirtyPagesGui() {
		return areDirtyPagesGui;
	}
	public void markAsDirtyPagesGui() {
		areDirtyPagesGui = true;
		if (!editor.isDirty()) {
			editor.editorDirtyStateChanged();
		}
	}
}
