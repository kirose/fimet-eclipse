package com.fimet.editor.stress;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;

/**
 * Registers a position updater that tracks changes for the {@link JsonTextEditor#JSON_CATEGORY} positions.
 * This class is managed through an extension point.
 */
public class DocumentSetup implements IDocumentSetupParticipant {

    private final IPositionUpdater updater = new DefaultPositionUpdater(StressEditor.STRESS_CATEGORY);

    @Override
    public void setup(IDocument document) {
    	//System.out.println("DocumentSetup.setup(IDocument document) " + document.getClass().getName());
        document.addPositionCategory(StressEditor.STRESS_CATEGORY);
        document.addPositionUpdater(updater);
    }

}
