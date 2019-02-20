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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;

public class MaskShell extends LObjectShell<Mask> {

	private Composite canvas;
	private Spinner spnMinH;
	private Spinner spnMinX;
	private Spinner spnMinY;
	private Spinner spnMaxH;
	private Spinner spnMaxX;
	private Spinner spnMaxY;
	
	boolean[][][] grid;
	int x0, y0, height;
	
	public Color falseColor;
	public Color trueColor;
	public Color centerColor;
	private Combo cmbHeight;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public MaskShell(Shell parent) {
		super(parent);
		content.setLayout(new GridLayout(6, false));
		
		Label lblMinH = new Label(content, SWT.NONE);
		lblMinH.setText("Min Height");
		
		// Minimum Limits
		
		spnMinH = new Spinner(content, SWT.BORDER);
		spnMinH.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int n = spnMaxH.getSelection() + spnMinH.getSelection() + 1;
				if (n < grid.length) {
					height = Math.max(height, -spnMinH.getSelection());
					shrink(grid[0].length, grid[0][0].length, n, 0, 0, grid.length - n);
				} else 
					expand(grid[0].length, grid[0][0].length, n, 0, 0, n - grid.length);
				updateLayerCombo();
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
				int n = spnMaxX.getSelection() + spnMinX.getSelection() + 1;
				if (n < grid[0].length)
					shrink(n, grid[0][0].length, grid.length, grid[0].length - n, 0, 0);
				else 
					expand(n, grid[0][0].length, grid.length, n - grid[0].length, 0, 0);
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
				int n = spnMaxY.getSelection() + spnMinY.getSelection() + 1;
				if (n < grid[0][0].length)
					shrink(grid[0].length, n, grid.length, 0, grid[0][0].length - n, 0);
				else 
					expand(grid[0].length, n, grid.length, 0, n - grid[0][0].length, 0);
			}
		});
		spnMinY.setMaximum(20);
		spnMinY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMaxH = new Label(content, SWT.NONE);
		lblMaxH.setText("Max Height");
		
		// Maximum Limits
		
		spnMaxH = new Spinner(content, SWT.BORDER);
		spnMaxH.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int n = spnMaxH.getSelection() + spnMinH.getSelection() + 1;
				if (n < grid.length) {
					height = Math.min(height, spnMaxH.getSelection());
					shrink(grid[0].length, grid[0][0].length, n, 0, 0, 0);
				} else 
					expand(grid[0].length, grid[0][0].length, n, 0, 0, 0);
				updateLayerCombo();
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
				int n = spnMaxX.getSelection() + spnMinX.getSelection() + 1;
				if (n < grid[0].length)
					shrink(n, grid[0][0].length, grid.length, 0, 0, 0);
				else 
					expand(n, grid[0][0].length, grid.length, 0, 0, 0);
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
				int n = spnMaxY.getSelection() + spnMinY.getSelection() + 1;
				if (n < grid[0][0].length)
					shrink(grid[0].length, n, grid.length, 0, 0, 0);
				else 
					expand(grid[0].length, n, grid.length, 0, 0, 0);
			}
		});
		spnMaxY.setMaximum(20);
		spnMaxY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight = new Label(content, SWT.NONE);
		lblHeight.setText("Height");
		
		cmbHeight = new Combo(content, SWT.READ_ONLY);
		cmbHeight.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				height = cmbHeight.getSelectionIndex() - spnMinH.getSelection();
				canvas.redraw();
			}
		});
		GridData gd_cmbHeight = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_cmbHeight.widthHint = 0;
		cmbHeight.setLayoutData(gd_cmbHeight);
		new Label(content, SWT.NONE);
		new Label(content, SWT.NONE);
		new Label(content, SWT.NONE);
		new Label(content, SWT.NONE);
		
		canvas = new Composite(content, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 6, 1));
		
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Image buffer = new Image(Display.getCurrent(), getBounds());
				GC gc = new GC(buffer);
				gc.setBackground(getBackground());
				gc.fillRectangle(buffer.getBounds());
				drawGrid(gc);
				gc.dispose();
				e.gc.drawImage(buffer, 0, 0);
				buffer.dispose();
			}
		});
		
		// Flip Tile
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int h = (height + spnMinH.getSelection());
				int px = e.x - (x0 + canvas.getSize().x / 2);
				int py = e.y - (y0 + canvas.getSize().y / 2);
				int pd = h * FieldHelper.config.grid.pixelsPerHeight;
				Point tile = FieldHelper.math.pixel2Tile(px, py, pd);
				try {
					grid[h][tile.x][tile.y] = !grid[h][tile.x][tile.y];
					canvas.redraw();
				} catch (IndexOutOfBoundsException ex) {}
			}
		});
		
	}
	
	private void drawGrid(GC gc) {
		FieldPainter painter = new FieldPainter();
		int x0 = this.x0 + canvas.getSize().x / 2;
		int y0 = this.y0 + canvas.getSize().y / 2;
		int currentH = spnMinH.getSelection() + height;
		for (int h = 0; h < grid.length; h++) {
			gc.setAlpha(h == currentH ? 255 : 127);
			for (int x = 0; x < grid[h].length; x++) {
				for (int y = 0; y < grid[h][x].length; y++) {
					gc.setBackground(grid[h][x][y] ? trueColor : falseColor);
					Point p = FieldHelper.math.tile2Pixel(x, y, h);
					painter.fillTile(gc, p.x + x0, p.y + y0);
					painter.drawTile(gc, p.x + x0, p.y + y0);
				}
			}
		}
		int h = spnMinH.getSelection();
		int x = spnMinX.getSelection();
		int y = spnMinY.getSelection();
		Point p = FieldHelper.math.tile2Pixel(x, y, h);
		gc.setForeground(centerColor);
		painter.scale = 0.8f;
		painter.drawGrid(gc, p.x + x0, p.y + y0);
	}
	
	private void expand(int x, int y, int h, int dx, int dy, int dh) {
		boolean[][][] newgrid = new boolean[h][x][y];
		for (int k = 0; k < grid.length; k++)
		for (int i = 0; i < grid[k].length; i++)
		for (int j = 0; j < grid[k][i].length; j++)
			newgrid[k + dh][i + dx][j + dy] = grid[k][i][j];
		grid = newgrid;
		Point p = FieldHelper.math.pixelSize(x, y);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(y) - p.y / 2;
		canvas.redraw();
	}
	
	private void shrink(int x, int y, int h, int dx, int dy, int dh) {
		boolean[][][] newgrid = new boolean[h][x][y];
		for (int k = 0; k < h; k++)
		for (int i = 0; i < x; i++)
		for (int j = 0; j < y; j++)
			newgrid[k][i][j] = grid[k + dh][i + dx][j + dy];
		grid = newgrid;
		Point p = FieldHelper.math.pixelSize(x, y);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(y) - p.y / 2;
		canvas.redraw();
	}
	
	private void updateLayerCombo() {
		String[] items = new String[grid.length];
		for (int i = 0; i < grid.length; i++)
			items[i] = "" + (i - spnMinH.getSelection());
		cmbHeight.setItems(items);
		cmbHeight.select(height + spnMinH.getSelection());
		canvas.redraw();
	}

	public void open(Mask initial) {
		grid = initial.grid.clone();
		for (int i = 0; i < grid.length; i++) {
			grid[i] = initial.grid[i].clone();
			for (int j = 0; j < grid[i].length; j++)
				grid[i][j] = initial.grid[i][j].clone();
		}
		spnMinH.setSelection(initial.centerH);
		spnMinX.setSelection(initial.centerX);
		spnMinY.setSelection(initial.centerY);
		spnMaxH.setSelection(grid.length - initial.centerH - 1);
		spnMaxX.setSelection(grid[0].length - initial.centerX - 1);
		spnMaxY.setSelection(grid[0][0].length - initial.centerY - 1);
		Point p = FieldHelper.math.pixelSize(grid[0].length, grid[0][0].length);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(grid[0][0].length) - p.y / 2;
		updateLayerCombo();
		super.open(initial);
	}
	
	@Override
	protected Mask createResult(Mask initial) {
		Mask m = new Mask();
		m.grid = grid;
		m.centerH = spnMinH.getSelection();
		m.centerX = spnMinX.getSelection();
		m.centerY = spnMinY.getSelection();
		return m;
	}
}
