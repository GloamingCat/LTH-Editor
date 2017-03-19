package gui.shell.config;

import gui.Vocab;

import org.eclipse.swt.widgets.Shell;

import data.Config.Grid;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;

public class GridShell extends LObjectShell<Grid> {
	
	private Spinner spnW;
	private Spinner spnH;
	private Spinner spnB;
	private Spinner spnS;
	private Spinner spnPPH;
	private Button btnNeighbors;
	
	public GridShell(Shell parent) {
		super(parent);
		setMinimumSize(new Point(200, 0));
		setText(Vocab.instance.GRID + " - " + Vocab.instance.PROPERTIES);
		content.setLayout(new GridLayout(2, false));
		
		Label lblTileWidth = new Label(content, SWT.NONE);
		lblTileWidth.setText(Vocab.instance.TILEWIDTH);
		
		spnW = new Spinner(content, SWT.BORDER);
		spnW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTileHeight = new Label(content, SWT.NONE);
		lblTileHeight.setText(Vocab.instance.TILEHEIGHT);
		
		spnH = new Spinner(content, SWT.BORDER);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTileBase = new Label(content, SWT.NONE);
		lblTileBase.setText(Vocab.instance.TILEBASE);
		
		spnB = new Spinner(content, SWT.BORDER);
		spnB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTileSide = new Label(content, SWT.NONE);
		lblTileSide.setText(Vocab.instance.TILESIDE);
		
		spnS = new Spinner(content, SWT.BORDER);
		spnS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPixelsheight = new Label(content, SWT.NONE);
		lblPixelsheight.setText(Vocab.instance.PIXELHEIGHT);
		
		spnPPH = new Spinner(content, SWT.BORDER);
		spnPPH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAllNeighbors = new Label(content, SWT.NONE);
		lblAllNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		
		btnNeighbors = new Button(content, SWT.CHECK);
		btnNeighbors.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		pack();
	}
	
	public void open(Grid initial) {
		super.open(initial);
		spnW.setSelection(initial.tileW);
		spnH.setSelection(initial.tileH);
		spnB.setSelection(initial.tileB);
		spnS.setSelection(initial.tileS);
		spnPPH.setSelection(initial.pixelsPerHeight);
		btnNeighbors.setSelection(initial.allNeighbors);
	}

	@Override
	protected Grid createResult(Grid initial) {
		if (btnNeighbors.getSelection() == initial.allNeighbors &&
				spnW.getSelection() == initial.tileW &&
				spnH.getSelection() == initial.tileH &&
				spnB.getSelection() == initial.tileB &&
				spnS.getSelection() == initial.tileS &&
				spnPPH.getSelection() == initial.pixelsPerHeight) {
			return null;
		} else {
			Grid g = new Grid();
			g.tileW = spnW.getSelection();
			g.tileH = spnH.getSelection();
			g.tileB = spnB.getSelection();
			g.tileS = spnS.getSelection();
			g.pixelsPerHeight = spnPPH.getSelection();
			g.allNeighbors = btnNeighbors.getSelection();
			return g;
		}
	}
	
}
