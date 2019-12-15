package com.fimet.editor.usecase.utils;

import com.boothen.jsonedit.antlr.JSONLexer;
import com.boothen.jsonedit.model.AntlrTokenScanner;
import com.boothen.jsonedit.preferences.JsonTokenStyler;
import com.boothen.jsonedit.preferences.OverlayPreferenceStore;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;


public class JsonUtils {
//	public static void reconcilerJson(TextViewer viewer, String text) {
//        viewer.setDocument(new Document(text));
//        IPresentationReconciler reconciler = createPresentationReconciler();
//        reconciler.install(viewer);
//	}
    private static IPresentationReconciler createPresentationReconciler() {
        PresentationReconciler reconciler = new PresentationReconciler();

        JSONLexer lexer = new JSONLexer(null);
        IPreferenceStore orgStore = com.boothen.jsonedit.core.JsonCorePlugin.getDefault().getPreferenceStore();
        IPreferenceStore preferenceStore = new OverlayPreferenceStore(orgStore);
        JsonTokenStyler mapping = new JsonTokenStyler(preferenceStore);
        AntlrTokenScanner scanner = new AntlrTokenScanner(lexer, mapping);
        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        return reconciler;
    }
}
