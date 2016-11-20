package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import data.Attribute;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class AttributeShell extends LObjectShell<Attribute> {
	
	private Text txtName;
	private Text txtShortName;
	private Button btnMutable;
	private StyledText txtScript;

	public AttributeShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(content, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		
		Label lblShortName = new Label(content, SWT.NONE);
		lblShortName.setText(Vocab.instance.SHORTNAME);
		
		txtShortName = new Text(content, SWT.BORDER);
		txtShortName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblMutable = new Label(content, SWT.NONE);
		lblMutable.setText(Vocab.instance.MUTABLE);
		
		btnMutable = new Button(content, SWT.CHECK);
		btnMutable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Label lblScript = new Label(content, SWT.NONE);
		lblScript.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblScript.setText(Vocab.instance.SCRIPT);
		
		txtScript = new StyledText(content, SWT.BORDER);
		GridData gd_txtScript = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtScript.minimumWidth = 192;
		gd_txtScript.minimumHeight = 48;
		txtScript.setLayoutData(gd_txtScript);
		
		pack();
	}
	
	public void open(Attribute initial) {
		super.open(initial);
		txtName.setText(initial.name);
		txtShortName.setText(initial.shortName);
		txtScript.setText(initial.script);
		btnMutable.setSelection(initial.mutable);
	}

	@Override
	protected Attribute createResult(Attribute initial) {
		System.out.println("lllllllllllllllllllllllllllllllllllllllllllllll");
		if (txtName.getText().equals(initial.name) && txtShortName.getText().equals(initial.shortName)
				&& txtScript.getText().equals(initial.script) && btnMutable.getSelection() == initial.mutable) {
			return null;
		} else {
			Attribute att = new Attribute();
			att.name = txtName.getText();
			att.shortName = txtShortName.getText();
			att.script = txtScript.getText();
			att.mutable = btnMutable.getSelection();
			return att;
		}
	}
	
	protected void checkSubclass() { }
	
}
