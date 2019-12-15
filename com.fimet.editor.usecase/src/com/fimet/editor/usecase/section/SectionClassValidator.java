package com.fimet.editor.usecase.section;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.core.CompilationUnit;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.impl.swt.VText;
import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.page.ValidationsPage;
import com.fimet.editor.usecase.validator.OpenNewClassValidatorWizardAction;

public class SectionClassValidator extends SectionPart implements ISectionEditor {

	private ValidationsPage page;
	private UseCaseEditor editor;
	private Button btnUseClass;
	private VText txtClassValidator;
	private Button btnNewValidator;
	
	public SectionClassValidator(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | /*ExpandableComposite.TWISTIE |*/ Section.DESCRIPTION);
		this.editor = editor;
		page = editor.getValidationsPage();
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
	}

	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Class Validator");
		section.setDescription("Configuration for Validator Java Class");
		
		
		Composite composite = new Composite(section, SWT.NULL);
		GridLayout layout = new GridLayout(8,true);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.WRAP, true, false, 1, 1));
		composite.setBackground(section.getBackground());
		
		Label lbl;
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Use a class:");
		lbl.setToolTipText("Use a Validator Java class");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		btnUseClass = new Button(composite, SWT.CHECK);
		btnUseClass.setSelection(true);
		btnUseClass.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		btnUseClass.setBackground(section.getParent().getBackground());
		btnUseClass.setSelection(true);
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Class Name:");
		lbl.setToolTipText("The name of the class, use full class name, example: com.validators.Validator");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		txtClassValidator = new VText(composite, SWT.BORDER);
		txtClassValidator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));
		txtClassValidator.setEnabled(false);
		txtClassValidator.valid();
		
		btnNewValidator = new Button(composite, SWT.NONE);
		btnNewValidator.setText("New");
		btnNewValidator.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		btnNewValidator.setEnabled(false);
		
		section.setClient(composite);
	}
	private void hookComponentsListeners() {
		txtClassValidator.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionClassValidator.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
		});
		btnUseClass.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionClassValidator.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
				onChangeSelection(btnUseClass.getSelection());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnNewValidator.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onNewValidator();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	@Override
	public UseCaseEditor getEditor() {
		return editor;
	}

	@Override
	public FormToolkit getToolkit() {
		return editor.getToolkit();
	}
	private void onChangeSelection(boolean selected) {
		if (selected) {
			txtClassValidator.setEnabled(true);
			btnNewValidator.setEnabled(true);
			page.getSectionAcquirerResponse().setEnabled(false);
			page.getSectionIssuerRequest().setEnabled(false);
			page.getSectionExtract().setEnabled(false);
			page.getSectionAcquirerResponse().clean();
			page.getSectionIssuerRequest().clean();
			page.getSectionExtract().clean();
			editor.getModifier().modifyValidations(null);
		} else {
			txtClassValidator.setEnabled(false);
			btnNewValidator.setEnabled(false);
			txtClassValidator.setText("");
			page.getSectionAcquirerResponse().setEnabled(true);
			page.getSectionIssuerRequest().setEnabled(true);
			page.getSectionExtract().setEnabled(true);
		}
		if (!editor.getModifier().isApplingSourceChanges()) {
			page.getSectionAcquirerResponse().markDirty();
			page.getSectionIssuerRequest().markDirty();
			page.getSectionExtract().markDirty();
			this.markDirty();
		}
	}
	public void update() {
		if (editor.getModifier().getValClass() != null) {
			btnUseClass.setSelection(true);
			onChangeSelection(true);
			txtClassValidator.setText(StringUtils.escapeNull(editor.getModifier().getValClass()));
		} else {
			onChangeSelection(false);
			btnUseClass.setSelection(false);
			txtClassValidator.setText("");
		}
	}
	public void onNewValidator() {
		OpenNewClassValidatorWizardAction action = new OpenNewClassValidatorWizardAction(editor.getResource().getProject());
		action.addPropertyChangeListener((PropertyChangeEvent event)->{
			if (OpenNewClassValidatorWizardAction.RESULT.equals(event.getProperty())) {
				IJavaElement element = action.getWizard().getCreatedElement();
				if (element != null &&element.getParent() != null && element.getParent() instanceof CompilationUnit) {
					String className = "";
					CompilationUnit cu = (CompilationUnit)element.getParent();
					if (cu.getPackageName() != null && cu.getPackageName().length > 0) {
						char[][] parts = cu.getPackageName();
						for (char[] cs : parts) {
							className += new String(cs)+".";	
						}
						className += cu.readableName();
						className = className.substring(0,className.lastIndexOf('.'));
						txtClassValidator.setText(className);
					}
				}
			}
		});
		action.run();
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		editor.getModifier().modifyValClass(txtClassValidator.getText());
	}	
}
