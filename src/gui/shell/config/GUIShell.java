package gui.shell.config;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.Config.GUI;
import lwt.LVocab;
import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;

public class GUIShell extends LObjectShell<GUI> {

	private RGB rgb;
	private Label imgColor;
	private LCombo cmbCursor;

	public GUIShell(Shell parent) {
		super(parent);
		setSize(295, 204);
		setText(Vocab.instance.GUI + " - " + Vocab.instance.PROPERTIES);
		content.setLayout(new GridLayout(2, false));
		
		Label lblCursorAnimation = new Label(content, SWT.NONE);
		lblCursorAnimation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCursorAnimation.setText(Vocab.instance.CURSORANIMID);
		
		cmbCursor = new LCombo(content, SWT.NONE);
		cmbCursor.setOptional(false);
		cmbCursor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblColor = new Label(content, SWT.NONE);
		lblColor.setText(Vocab.instance.COLOR);
		
		Composite color = new Composite(content, SWT.NONE);
		color.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_color = new GridLayout(2, false);
		gl_color.marginHeight = 0;
		gl_color.marginWidth = 0;
		color.setLayout(gl_color);
		
		imgColor = new Label(color, SWT.NONE);
		imgColor.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		imgColor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnSelect = new Button(color, SWT.NONE);
		btnSelect.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ColorDialog d = new ColorDialog(getShell());
				RGB newRGB = d.open();
				if (newRGB != null) {
					rgb = newRGB;
					imgColor.setBackground(new Color(arg0.display, rgb));
					imgColor.redraw();
				}
			}
		});
		btnSelect.setText(LVocab.instance.SELECT);
		
		pack();
	}
	
	public void open(GUI initial) {
		super.open(initial);
		rgb = initial.disabledColor;
		imgColor.setBackground(new Color(Display.getCurrent(), rgb));
		imgColor.redraw();
		cmbCursor.setItems(Project.current.animOther.getList());
		cmbCursor.setValue(initial.cursorAnimID);
	}

	@Override
	protected GUI createResult(GUI initial) {
		if (initial.disabledColor.equals(rgb) && 
				cmbCursor.getSelectionIndex() == initial.cursorAnimID) {
			return null;
		} else {
			GUI g = new GUI();
			g.cursorAnimID = cmbCursor.getSelectionIndex();
			g.disabledColor = rgb;
			return g;
		}
	}
	
}
