package com.fimet.core.impl.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class KusunokiModeCmd extends AbstractHandler {
	private static IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class); 
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	int count = preferencesManager.getInt(IPreferencesManager.KUSUNOKI_COUNT, 0) + 1;
    	if (count < 5) {
    		preferencesManager.save(IPreferencesManager.KUSUNOKI_COUNT, count);
    		preferencesManager.save(IPreferencesManager.KUSUNOKI_MODE, false);
    	} else {
    		preferencesManager.save(IPreferencesManager.KUSUNOKI_COUNT, 0);
    		preferencesManager.save(IPreferencesManager.KUSUNOKI_MODE, true);
    	}
        return null;
    }

}