package gui.shell.system;

import gui.Vocab;
import gui.shell.ObjectShell;

import org.eclipse.swt.widgets.Shell;

import data.config.Config.Grid;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.graphics.Point;

public class GridShell extends ObjectShell<Grid> {
	
	public GridShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(200, 0));
		setText(Vocab.instance.GRID + " - " + Vocab.instance.PROPERTIES);
		contentEditor.setLayout(new GridLayout(2, false));
		
		Label lblTileWidth = new Label(contentEditor, SWT.NONE);
		lblTileWidth.setText(Vocab.instance.TILEWIDTH);
		
		LSpinner spnW = new LSpinner(contentEditor, SWT.NONE);
		spnW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnW, "tileW");
		
		Label lblTileHeight = new Label(contentEditor, SWT.NONE);
		lblTileHeight.setText(Vocab.instance.TILEHEIGHT);
		
		LSpinner spnH = new LSpinner(contentEditor, SWT.NONE);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnH, "tileH");
		
		Label lblTileBase = new Label(contentEditor, SWT.NONE);
		lblTileBase.setText(Vocab.instance.TILEBASE);
		
		LSpinner spnB = new LSpinner(contentEditor, SWT.NONE);
		spnB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnB, "tileB");
		
		Label lblTileSide = new Label(contentEditor, SWT.NONE);
		lblTileSide.setText(Vocab.instance.TILESIDE);
		
		LSpinner spnS = new LSpinner(contentEditor, SWT.NONE);
		spnS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnS, "tileS");
		
		Label lblPixelsheight = new Label(contentEditor, SWT.NONE);
		lblPixelsheight.setText(Vocab.instance.PIXELHEIGHT);
		
		LSpinner spnPPH = new LSpinner(contentEditor, SWT.NONE);
		spnPPH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnPPH, "pixelsPerHeight");
		
		Label lblAllNeighbors = new Label(contentEditor, SWT.NONE);
		lblAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		
		LCheckButton btnNeighbors = new LCheckButton(contentEditor, SWT.NONE);
		btnNeighbors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(btnNeighbors, "allNeighbors");
		
		pack();
	}
	
}
