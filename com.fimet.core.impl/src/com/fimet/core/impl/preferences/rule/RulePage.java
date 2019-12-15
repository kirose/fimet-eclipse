package com.fimet.core.impl.preferences.rule;


import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.Color;
import com.fimet.commons.DefaultStyler;
import com.fimet.commons.Images;
import com.fimet.commons.exception.FieldFormatException;
import com.fimet.commons.history.History;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.IRuleManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.IRuleValue;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.core.impl.Activator;
import com.fimet.core.impl.swt.EnviromentTypeCombo;
import com.fimet.core.impl.swt.FieldMapperCombo;

public class RulePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "com.fimet.preferences.RulesPage";
	
	static Styler STYLER_RESERVED_WORDS;
	static Styler STYLER_TERMINAL;
	static Styler STYLER_TEXT;
	static Styler STYLER_VALUE;
    
	private static void init() {
		if (STYLER_RESERVED_WORDS == null) {
			STYLER_RESERVED_WORDS = new DefaultStyler(Color.WINE, null);
			STYLER_TERMINAL = new DefaultStyler(Color.BLACK, null);
			STYLER_TEXT = new DefaultStyler(Color.BLUE, null);
			STYLER_VALUE = new DefaultStyler(Color.CYAN, null);
		}
	}
	
	private IRuleManager ruleManager = Manager.get(IRuleManager.class);
	private RuleTree tree;
	private EnviromentTypeCombo cboEnviromentType;
	private FieldMapperCombo cboFieldMapper;
	private History<Rule> historyRules;
	private List<IRuleValue> values;
	Button btnUp;
	Button btnDown;
	Button btnApply;
    public RulePage() {
        noDefaultAndApplyButton();
        init();
        historyRules = new History<Rule>();
    }
    @Override
    protected final Control createContents(Composite parent) {
        org.eclipse.swt.graphics.Color widgetBackground = parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
        parent.setBackground(widgetBackground);
		GridLayout layout = new GridLayout(1,true);
        layout.horizontalSpacing = 1;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.heightHint = 300;
        gd.widthHint = 600;
        parent.setLayout(layout);
		parent.setLayoutData(gd);
		
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        layout = new GridLayout(1,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		
        gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
        layout = new GridLayout(16,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite header = new Composite(composite, SWT.NONE);
        header.setLayout(layout);
        header.setLayoutData(gd);
		
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        layout = new GridLayout(1,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite body = new Composite(composite, SWT.NONE);
		body.setLayout(layout);
		body.setLayoutData(gd);
		
		Label label;

		label = new Label(header, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		label.setText("Enviroment Type:");
		//label.setBackground(Color.WHITE);
		label.setToolTipText("The enviroment type");
		
		cboEnviromentType = new EnviromentTypeCombo(header);
		cboEnviromentType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		label = new Label(header, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Field:");
		//label.setBackground(Color.WHITE);
		label.setToolTipText("The field to be configured");
		
		cboFieldMapper = new FieldMapperCombo(header);
		cboFieldMapper.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnUp = new Button(header, SWT.NONE);
		btnUp.setImage(Images.ARROW_UP_ICON.createImage());
		btnUp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		btnDown = new Button(header, SWT.NONE);
		btnDown.setImage(Images.ARROW_DOWN_ICON.createImage());
		btnDown.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		label = new Label(header, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gridData.widthHint = 600;
		tree = new RuleTree(this, body, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		tree.getControl().setLayoutData(gridData);


    	Composite cmpButtons = new Composite(parent, SWT.NONE);
    	layout = new GridLayout(2,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	cmpButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1,1));
    	cmpButtons.setLayout(layout);
    	new Label(cmpButtons, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnApply = new Button(cmpButtons, SWT.NONE);
		btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnApply.setText("    Apply    ");
		btnApply.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		cboEnviromentType.getCombo().addModifyListener((ModifyEvent e)->{
			if (cboEnviromentType.getSelected() != null && cboFieldMapper.getSelected() != null) {
				tree.setRules(ruleManager.getRules(cboEnviromentType.getSelected().getId(), cboFieldMapper.getSelected().getId()));
				tree.expandAll();
			}
		});
		cboFieldMapper.getCombo().addModifyListener((ModifyEvent e)->{
			if (cboFieldMapper.getSelected() != null) {
				values = ruleManager.getValues(cboFieldMapper.getSelected());
			}
			if (cboEnviromentType.getSelected() != null && cboFieldMapper.getSelected() != null) {
				tree.setRules(ruleManager.getRules(cboEnviromentType.getSelected().getId(), cboFieldMapper.getSelected().getId()));
				tree.expandAll();
			}
		});
		tree.addDoubleClickListener((DoubleClickEvent event)->{
			onEditRule();
		});
		btnUp.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onUpRule();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDown.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onDownRule();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		return composite;
    }
	public void onNewRule() {
		RuleDialog dialog = new RuleDialog(
			null,
			cboEnviromentType.getSelected(),
			cboFieldMapper.getSelected(),
			String.format("%02d",tree.getRoots().size()),
			getShell(),
			values,
			SWT.NONE
		);
		dialog.open();
		Rule rule = dialog.getRule();
		if (rule != null) {
			tree.add(new RuleNode(rule));
			historyRules.insert(rule);
		}
	}
	public void onNewChildRule() {
		if (tree.getSelectedNode() != null) {
			RuleNode parent = tree.getSelectedNode();
			String order;
			int idOrder;
			if (parent.hasChildren()) {
				order = parent.children.get(parent.children.size()-1).rule.getOrder();
				if (order.indexOf('.') != -1) order = order.substring(order.lastIndexOf('.')+1);
				idOrder = Integer.parseInt(order)+1;
			} else {
				idOrder = 0;
			}
			order = parent.rule.getOrder()+"."+String.format("%02d", idOrder);
			RuleDialog dialog = new RuleDialog(
				null,
				cboEnviromentType.getSelected(),
				cboFieldMapper.getSelected(),
				order,
				getShell(),
				values,
				SWT.NONE
			);
			dialog.open();
			Rule rule = dialog.getRule();
			if (rule != null) {
				RuleNode node = new RuleNode(rule);
				parent.add(node);
				tree.add(node);
				historyRules.insert(rule);
			}
		}
	}
	public void onEditRule() {
		if (tree.getSelected() != null) {
			RuleDialog dialog = new RuleDialog(
				tree.getSelected(),
				cboEnviromentType.getSelected(),
				cboFieldMapper.getSelected(),
				tree.getSelected().getOrder(),
				getShell(),
				values,
				SWT.NONE
			);
			dialog.open();
			Rule sm = dialog.getRule();
			if (sm != null) {
				tree.update(tree.getSelectedNode());
				historyRules.update(sm);
			}
		}
	}
	public void onDeleteRule() {
		if (tree.getSelected() != null) {
			Rule p = tree.getSelected();
			if (askDeleteRule(p)) {
				delete(tree.getSelectedNode());
				tree.delete(tree.getSelectedParentNode(), tree.getSelectedNode());
			}
		}
	}
	public void onUpRule() {
		if (tree.getSelected() != null) {
			Rule[] rules = tree.up(tree.getSelectedNode());
			if (rules != null) {
				historyRules.update(rules[0]);
				historyRules.update(rules[1]);
			}
		}
	}
	public void onDownRule() {
		if (tree.getSelected() != null) {
			Rule[] rules = tree.down(tree.getSelectedNode());
			if (rules != null) {
				historyRules.update(rules[0]);
				historyRules.update(rules[1]);
			}
		}
	}
	private void delete(RuleNode node) {
		historyRules.delete(node.rule);
		if (node.hasChildren()) {
			for (RuleNode child : node.getChildren()) {
				delete(child);
			}
		}
	}
	@Override
    public void init(IWorkbench workbench) {
    }
	@Override
    public boolean performOk() {
		try {
			commit();
			return super.performOk();
		} catch (FieldFormatException e) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Field Format", e.getMessage());
			Activator.getInstance().warning(e.getMessage(), e);
			return false;
		}
    }
	public void commit() {
		ruleManager.commit(historyRules);
		historyRules = new History<Rule>();
	}
	private boolean askDeleteRule(Rule node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_RULE);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			getShell(), 
			"Delete Rule",
			"Do you want to delete the rule \""+node.toString()+"\"?",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_RULE
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_RULE, delete);
		}
		return delete;
	}

	public String getResult(Integer idResult) {
		for (IRuleValue v : values) {
			if (v.getId() == idResult) {
				return v.getName();
			}
		}
		return null;
	}
}