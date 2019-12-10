package com.fimet.core.impl.preferences;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;

public class PreferenceNodeWrapper extends PreferenceNode {
	private Class<? extends PreferencePage> clazz;
	public PreferenceNodeWrapper(String id, String label, ImageDescriptor image, Class<? extends PreferencePage> clazz) {
		super(id, label, image, clazz.getName());
		this.clazz = clazz;
	}
	private IPreferencePage instantiatePage() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
    @Override
	public void createPage() {
    	IPreferencePage page = (IPreferencePage) instantiatePage();
        page.setTitle(getLabelText());
        setPage(page);
    }
}
