package gui.shell.database;

import gui.helper.FieldHelper;
import gui.helper.FieldPainter;

import data.Skill.Mask;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

public class MaskShell extends LObjectShell<Mask> {

	boolean[][][] grid;
	private Spinner spnMinH;
	private Spinner spnMinX;
	private Spinner spnMinY;
	private Spinner spnMaxH;
	private Spinner spnMaxX;
	private Spinner spnMaxY;
	
	int x0, y0;
	
	public Color falseColor;
	public Color trueColor;
	public Color centerColor;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public MaskShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(6, false));
		
		Label lblMinH = new Label(content, SWT.NONE);
		lblMinH.setText("Min Height");
		
		spnMinH = new Spinner(content, SWT.BORDER);
		spnMinH.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		spnMinH.setMaximum(20);
		spnMinH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMinX = new Label(content, SWT.NONE);
		lblMinX.setText("Min X");
		
		spnMinX = new Spinner(content, SWT.BORDER);
		spnMinX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		spnMinX.setMaximum(20);
		spnMinX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMinY = new Label(content, SWT.NONE);
		lblMinY.setText("Min Y");
		
		spnMinY = new Spinner(content, SWT.BORDER);
		spnMinY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		spnMinY.setMaximum(20);
		spnMinY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMaxH = new Label(content, SWT.NONE);
		lblMaxH.setText("Max Height");
		
		spnMaxH = new Spinner(content, SWT.BORDER);
		spnMaxH.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		spnMaxH.setMaximum(20);
		spnMaxH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMaxX = new Label(content, SWT.NONE);
		lblMaxX.setText("Max X");
		
		spnMaxX = new Spinner(content, SWT.BORDER);
		spnMaxX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		spnMaxX.setMaximum(20);
		spnMaxX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMaxY = new Label(content, SWT.NONE);
		lblMaxY.setText("Max Y");
		
		spnMaxY = new Spinner(content, SWT.BORDER);
		spnMaxY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		spnMaxY.setMaximum(20);
		spnMaxY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite canvas = new Composite(content, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 6, 1));
		
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				FieldPainter painter = new FieldPainter();
				int dx = x0 + canvas.getSize().x / 2;
				int dy = y0 + canvas.getSize().y / 2;
				for (int h = 0; h < grid.length; h++)
				for (int x = 0; x < grid[h].length; x++)
				for (int y = 0; y < grid[h][x].length; y++) {
					e.gc.setBackground(grid[h][x][y] ? trueColor : falseColor);
					Point p = FieldHelper.math.tile2Pixel(x, y, h);
					painter.fillTile(e.gc, p.x + dx, p.y + dy);
					painter.drawTile(e.gc, p.x + dx, p.y + dy);
				}
				int h = spnMinH.getSelection();
				int x = spnMinX.getSelection();
				int y = spnMinY.getSelection();
				Point p = FieldHelper.math.tile2Pixel(x, y, h);
				e.gc.setForeground(centerColor);
				painter.scale = 0.8f;
				painter.drawGrid(e.gc, p.x + dx, p.y + dy);
			}
		});
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent arg0) {

			}
		});
		
	}

	public void open(Mask initial) {
		grid = initial.grid.clone();
		spnMinH.setSelection(initial.centerH);
		spnMinX.setSelection(initial.centerX);
		spnMinY.setSelection(initial.centerY);
		spnMaxH.setSelection(grid.length - initial.centerH - 1);
		spnMaxX.setSelection(grid[0].length - initial.centerX - 1);
		spnMaxY.setSelection(grid[0][0].length - initial.centerY - 1);
		Point p = FieldHelper.math.pixelSize(grid[0].length, grid[0][0].length);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(grid[0][0].length) - p.y / 2;
		super.open(initial);
	}
	
	@Override
	protected Mask createResult(Mask initial) {
		// TODO Auto-generated method stub
		return null;
	}

}
