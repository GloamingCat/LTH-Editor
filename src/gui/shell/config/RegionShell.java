package gui.shell.config;

import gui.Vocab;
import gui.views.IDList;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lwt.LVocab;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;

import project.Project;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import data.config.Region;

import org.eclipse.wb.swt.SWTResourceManager;

public class RegionShell extends LObjectShell<Region> {
	
	private Text txtName;
	private IDList lstBattlers;
	private RGB rgb;
	private Label imgColor;
	private LDataList<Integer> troops = new LDataList<>();
	
	public RegionShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(content, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
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
		
		Group grpBattler = new Group(content, SWT.NONE);
		grpBattler.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpBattler = new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1);
		gd_grpBattler.widthHint = 155;
		gd_grpBattler.heightHint = 98;
		grpBattler.setLayoutData(gd_grpBattler);
		grpBattler.setText(Vocab.instance.BATTLERS);
		
		lstBattlers = new IDList(grpBattler, SWT.NONE) {
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
			public LDataList<Integer> getDataCollection() {
				return troops;
			}
		};

		pack();
	}
	
	public void open(Region initial) {
		super.open(initial);
		txtName.setText(initial.name);
		rgb = initial.rgb;
		imgColor.setBackground(new Color(Display.getCurrent(), rgb));
		imgColor.redraw();
		
		troops = new LDataList<Integer>();
		for (Integer i : initial.troops) {
			troops.add(i);
		}
		lstBattlers.onVisible();
	}

	@Override
	protected Region createResult(Region initial) {
		if (txtName.getText().equals(initial.name) && 
				rgb.equals(initial.rgb) &&
				troops.equals(initial.troops)) {
			return null;
		} else {
			Region r = new Region();
			r.name = txtName.getText();
			r.rgb = rgb;
			r.troops = troops;
			return r;
		}
	}

}
