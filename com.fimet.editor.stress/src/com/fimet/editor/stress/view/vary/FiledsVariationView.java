package com.fimet.editor.stress.view.vary;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.fimet.core.IUseCaseManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.usecase.UseCase;
import com.fimet.editor.stress.Activator;
import com.fimet.parser.util.ParserUtils;
import com.fimet.commons.Color;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FiledsVariationView extends ViewPart {
	public static final String ID = "com.fimet.view.VariationParametersView";
	
	private FieldsTable table;
	private Panel panel;
	private Text txtValues;
	private Map<String, List<String>> variationParameters = new HashMap<>();
	private UseCase useCase;
    
	public FiledsVariationView() {
		super();
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(4,true);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setFont(parent.getFont());
        composite.setBackground(Color.WHITE);
        
        panel = new Panel(this, composite, SWT.BORDER);
        panel.setLayout(new GridLayout(1,true));
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
        table = new FieldsTable(this, composite, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);//SWT.SINGLE
        table.getTable().setLayout(new GridLayout(1,true));
        table.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        
		txtValues = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtValues.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		txtValues.setBackground(Color.WHITE);
        		
		hookListeners();
	}
	private void hookListeners() {
        table.getTable().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onSelectTableParameter();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        txtValues.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				onChangeTextValues();
			}
		});
	}
	public void onChangeTextValues() {
		if (table.getSelected() != null) {
			setValues(table.getSelected(), txtValues.getText());
		}
	}
	private void setValues(String idField, String values) {
		if (idField == null) {
			return;
		}
		if (values != null && !"".equals(values)) {
			values = values.replace("\r\n", "\n");
			if (values.endsWith("\n")) {
				values = values.substring(0,values.length()-1);
			}
			setValues(idField, Arrays.asList(values.split("\n")));
		} else {
			setValues(idField, new ArrayList<>());
		}
	}
	private void addValues(String idField, List<String> values) {
		if (idField == null || values == null) {
			return;
		}
		if (variationParameters.containsKey(idField)) {
			values.addAll(variationParameters.get(idField));
		} 
		setValues(idField, values);
	}
	private void setValues(String idField, List<String> values) {
		if (idField == null || values == null) {
			return;
		}
		variationParameters.put(idField, values);
		long numberOfCases = 1;
		for (Map.Entry<String, List<String>> e : variationParameters.entrySet()) {
			numberOfCases = numberOfCases*e.getValue().size();
		}
		panel.setNumberOfCases(numberOfCases);
	}
	public void onDeleteVariationParameter() {
		String idField = table.getSelected();
		if (idField != null) {
			int index = table.removeField(idField);
			variationParameters.remove(idField);
			table.getTable().deselectAll();
			if (index > 0) {
				table.getTable().select(index-1);
				onSelectTableParameter();
			} else {
				txtValues.setText("");
			}
		}
	}
	public void onAddVariationParameter(String idField) {
		if (idField != null && !table.contains(idField)) {
			table.addField(idField);
		}
		table.selectField(idField);
		onSelectTableParameter();
	}
	public void onAddVariationParameter(String idField, List<String> values) {
		if (idField != null && !table.contains(idField)) {
			table.addField(idField);
		}
		table.selectField(idField);
		addValues(idField, values);
		onSelectTableParameter();
	}
	public void onSelectTableParameter() {
		if (table.getSelected() != null) {
			List<String> list = variationParameters.get(table.getSelected());
			if (list != null && !list.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (String v : list) {
					sb.append(v).append('\n');
				}
				sb.delete(sb.length()-1, sb.length());
				txtValues.setText(sb.toString());
			} else {
				txtValues.setText("");
			}
		}
	}
	public void onSave() {
		if (useCase == null || panel.getDestination() == null || variationParameters.isEmpty()) {
			return;
		}
		FileOutputStream writer = null;
		try {
			Message message = useCase.getAcquirer().getRequest().getMessage();
			writer = new FileOutputStream(panel.getDestination(),true);
			CartesianIterator iterator = new CartesianIterator(variationParameters);
			VariationField[] next;
			StringBuilder errors = new StringBuilder();
			while (iterator.hasNext()) {
				next = iterator.next();
				try {
					for (VariationField v : next) {
						message.setValue(v.idField, v.value);
					}
					writer.write(ParserUtils.formatMessage(message));
					writer.write((byte)10);
				} catch (Exception e){
					errors.append(e.getMessage()).append('\n');
				}
			}
			if (errors.length() != 0) {
				Activator.getInstance().warning("Error formating message: "+errors.toString());	
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Error in Variation of Parameters", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch(Exception ex) {}
			}
		}
	}
	private class CartesianIterator {
		String[][] data;
		VariationField[] columns;
		int curr;
		int size;
		
		CartesianIterator(Map<String, List<String>> parameters){
			Set<Entry<String, List<String>>> set = parameters.entrySet();
			data = new String[set.size()][];
			columns = new VariationField[set.size()];
			int i = 0;
			size = 1;
			for (Entry<String, List<String>> e : set) {
				columns[i] = new VariationField();
				columns[i].idField = e.getKey();
				data[i] = e.getValue().toArray(new String[e.getValue().size()]);
				columns[i].value = data[i][0];
				size *= e.getValue().size();
				i++;
			}
			columns[columns.length-1].index = -1;
			curr = -1;
		}
		boolean hasNext() {
			return curr + 1 < size;
		}
		VariationField[] next() {
			curr++;
			for (int i = columns.length-1; i >= 0; i--) {
				if (columns[i].index+1 < data[i].length) {
					columns[i].index++;
					columns[i].value = data[i][columns[i].index];
					break;
				} else {
					columns[i].index = 0;
					columns[i].value = data[i][0];
				}
			}
			return columns;
		}
	}
	private class VariationField {
		String idField;
		int index;
		String value;
		VariationField(){}
		public String toString() {
			return idField+":"+value;
		}
	}
	public void setUseCaseResource(IResource useCaseResource) {
		try {
			this.useCase = Manager.get(IUseCaseManager.class).parse(useCaseResource);
			this.panel.setSource(useCaseResource.getLocation().toString());
		} catch (Exception e) {
			Activator.getInstance().warning("Invalid resource "+(useCaseResource != null ? useCaseResource.getName() : null));
		}
	}
	public void setUseCaseResource(File useCaseFile) {
		try {
			this.useCase = Manager.get(IUseCaseManager.class).parse(useCaseFile);
			this.panel.setSource(useCaseFile.getAbsolutePath());
		} catch (Exception e) {
			Activator.getInstance().warning("Invalid file "+(useCaseFile != null ? useCaseFile.getName() : null));
		}
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.table.getControl().setFocus();
	}
    @Override
	public void dispose() {
		super.dispose();
	}
    public UseCase getUseCase() {
    	return useCase;
    }
}