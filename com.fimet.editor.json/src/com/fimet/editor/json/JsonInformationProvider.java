package com.fimet.editor.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;

import com.fimet.editor.json.core.JsonLog;
import com.fimet.editor.json.core.model.AntlrAdapter;
import com.fimet.editor.json.core.model.ParseResult;
import com.fimet.editor.json.core.model.antlr.JSONParser.JsonContext;

/**
 * Computes a JSON syntax tree (e.g. for the quick outline view)
 */
class JsonInformationProvider implements IInformationProvider, IInformationProviderExtension {

    @Override
    public IRegion getSubject(ITextViewer textViewer, int offset) {
        return new Region(offset, 0);
    }

    @Override
    public String getInformation(ITextViewer textViewer, IRegion subject) {
        // not used, since this class also implements IInformationProviderExtension
        return String.valueOf(getInformation2(textViewer, subject));
    }

    @Override
    public Object getInformation2(ITextViewer textViewer, IRegion subject) {
        // subject is currently ignored
        try {
            String content = textViewer.getDocument().get();
            System.out.println("JsonInformationProvider.getInformation2(ITextViewer textViewer, IRegion subject) "+textViewer.getDocument().getClass().getName()+"-"+content);
            Reader reader = new StringReader(content);
            ParseResult result = AntlrAdapter.convert(reader);
            JsonContext syntaxTree = result.getTree();

            return syntaxTree;
        } catch (IOException e) {
            JsonLog.error("Could not compute information", e);
            return null;
        }
    }
}
