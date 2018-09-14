package gui.shell;

import org.eclipse.swt.widgets.Shell;

import data.subcontent.Tag;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StyledText;

public class TagShell extends LObjectShell<Tag> {
	
	private Text txtName;
	private StyledText txtValue;

	public TagShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText("Name");
		
		txtName = new Text(content, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblValue = new Label(content, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblValue.setText("Value");
		
		txtValue = new StyledText(content, SWT.BORDER);
		GridData gd_txtValue = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtValue.widthHint = 170;
		gd_txtValue.heightHint = 75;
		txtValue.setLayoutData(gd_txtValue);
		
		pack();
	}
	
	public void open(Tag initial) {
		super.open(initial);
		txtName.setText(initial.key);
		txtValue.setText(initial.value);
	}

	@Override
	protected Tag createResult(Tag initial) {
		if (txtName.getText().equals(initial.key) && txtValue.getText().equals(initial.value)) {
			return null;
		} else {
			Tag tag = new Tag();
			tag.key = txtName.getText();
			tag.value = txtValue.getText();
			return tag;
		}
	}
	
	protected void checkSubclass() { }
	
}
