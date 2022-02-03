package gui.shell;

import gui.Vocab;

import lwt.LVocab;
import lwt.dialog.LObjectShell;
import lwt.widget.LLabel;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.subcontent.Constant;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import com.google.gson.JsonParser;

public class ConstantShell extends LObjectShell<Constant> {
	
	private JsonParser parser = new JsonParser();
	
	private LText txtName;
	private LText txtValue;
	private Combo cmbType;

	public ConstantShell(Shell parent) {
		super(parent);
		
		String[] items = new String[] {
			"Table",
			"Animation",
			"Audio",
			"Battler",
			"Character",
			"Class",
			"Dialogue",
			"Equip",
			"Field",
			"Icon",
			"Item",
			"Obstacle",
			"Position",
			"Quad",
			"Script",
			"Status",
			"Terrain",
			"Troop"
		};
		
		setMinimumSize(new Point(300, 120));
		
		content.setLayout(new GridLayout(3, false));
		
		new LLabel(content, Vocab.instance.NAME);
		
		txtName = new LText(content);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		new LLabel(content, Vocab.instance.TYPE);
		
		cmbType = new Combo(content, SWT.READ_ONLY);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmbType.setItems(items);
		
		new LLabel(content, Vocab.instance.VALUE);
		
		txtValue = new LText(content);
		txtValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSelect = new Button(content, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.text.equals("Table")) {
					//TODO
				}
			}
		});
		btnSelect.setText(LVocab.instance.SELECT);

	}
	
	public void open(Constant initial) {
		super.open(initial);
		txtName.setValue(initial.name);
		cmbType.select(initial.type);
		txtValue.setValue(initial.value.toString());
	}

	@Override
	protected Constant createResult(Constant initial) {
		Constant c = new Constant();
		c.name = txtName.getValue();
		c.type = cmbType.getSelectionIndex();
		c.value = parser.parse(txtValue.getValue()).getAsJsonObject();
		return c;
	}

}
