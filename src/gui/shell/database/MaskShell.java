package gui.shell.database;

import gui.Vocab;
import gui.helper.FieldHelper;
import data.Skill.Mask;
import data.subcontent.Point;
import lwt.LColor;
import lwt.container.LCanvas;
import lwt.container.LCanvas.LPainter;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MaskShell extends LObjectShell<Mask> {

	private LCanvas canvas;
	private LSpinner spnMinH;
	private LSpinner spnMinX;
	private LSpinner spnMinY;
	private LSpinner spnMaxH;
	private LSpinner spnMaxX;
	private LSpinner spnMaxY;
	
	boolean[][][] grid;
	int x0, y0, height;
	
	public LColor falseColor;
	public LColor trueColor;
	public LColor centerColor;
	private LCombo cmbHeight;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public MaskShell(LShell parent) {
		super(parent);
		content.setGridLayout(6, false);
		
		new LLabel(content, "Min Height");
		
		// Minimum Limits
		
		spnMinH = new LSpinner(content);
		spnMinH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int n = spnMaxH.getValue() + spnMinH.getValue() + 1;
				if (n < grid.length) {
					height = Math.max(height, -spnMinH.getValue());
					shrink(grid[0].length, grid[0][0].length, n, 0, 0, grid.length - n);
				} else 
					expand(grid[0].length, grid[0][0].length, n, 0, 0, n - grid.length);
				updateLayerCombo();
			}
		});
		spnMinH.setMaximum(20);
		
		new LLabel(content, "Min X");
		
		spnMinX = new LSpinner(content);
		spnMinX.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int n = spnMaxX.getValue() + spnMinX.getValue() + 1;
				if (n < grid[0].length)
					shrink(n, grid[0][0].length, grid.length, grid[0].length - n, 0, 0);
				else 
					expand(n, grid[0][0].length, grid.length, n - grid[0].length, 0, 0);
			}
		});
		spnMinX.setMaximum(20);
		
		new LLabel(content, "Min Y");
		
		spnMinY = new LSpinner(content);
		spnMinY.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int n = spnMaxY.getValue() + spnMinY.getValue() + 1;
				if (n < grid[0][0].length)
					shrink(grid[0].length, n, grid.length, 0, grid[0][0].length - n, 0);
				else 
					expand(grid[0].length, n, grid.length, 0, n - grid[0][0].length, 0);
			}
		});
		spnMinY.setMaximum(20);
		
		new LLabel(content, "Max Height");
		
		// Maximum Limits
		
		spnMaxH = new LSpinner(content);
		spnMaxH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int n = spnMaxH.getValue() + spnMinH.getValue() + 1;
				if (n < grid.length) {
					height = Math.min(height, spnMaxH.getValue());
					shrink(grid[0].length, grid[0][0].length, n, 0, 0, 0);
				} else 
					expand(grid[0].length, grid[0][0].length, n, 0, 0, 0);
				updateLayerCombo();
			}
		});
		spnMaxH.setMaximum(20);
		
		new LLabel(content, "Max X");
		
		spnMaxX = new LSpinner(content);
		spnMaxX.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int n = spnMaxX.getValue() + spnMinX.getValue() + 1;
				if (n < grid[0].length)
					shrink(n, grid[0][0].length, grid.length, 0, 0, 0);
				else 
					expand(n, grid[0][0].length, grid.length, 0, 0, 0);
			}
		});
		spnMaxX.setMaximum(20);
		
		new LLabel(content, "Max Y");
		
		spnMaxY = new LSpinner(content);
		spnMaxY.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int n = spnMaxY.getValue() + spnMinY.getValue() + 1;
				if (n < grid[0][0].length)
					shrink(grid[0].length, n, grid.length, 0, 0, 0);
				else 
					expand(grid[0].length, n, grid.length, 0, 0, 0);
			}
		});
		spnMaxY.setMaximum(20);
		
		new LLabel(content, Vocab.instance.HEIGHT);
		
		cmbHeight = new LCombo(content, true);
		cmbHeight.setOptional(false);
		cmbHeight.setIncludeID(false);
		cmbHeight.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				height = cmbHeight.getValue() - spnMinH.getValue();
				canvas.redraw();
			}
		});
		new LLabel(content, 4, 1);
		
		canvas = new LCanvas(content);
		canvas.setExpand(false, true);
		canvas.setSpread(6, 1);
		
		canvas.addPainter(new LPainter() {
			@Override
			public void paint() {
				canvas.pushBuffer();
				drawGrid();
				canvas.popBuffer();
				canvas.drawBuffer(0, 0);
				canvas.disposeBuffer();
			}
		});
		
		// Flip Tile
		
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int h = (height + spnMinH.getValue());
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
	
	private void drawGrid() {
		int x0 = this.x0 + canvas.getSize().x / 2;
		int y0 = this.y0 + canvas.getSize().y / 2;
		int currentH = spnMinH.getValue() + height;
		for (int h = 0; h < grid.length; h++) {
			canvas.setTransparency(h == currentH ? 255 : 127);
			for (int x = 0; x < grid[h].length; x++) {
				for (int y = 0; y < grid[h][x].length; y++) {
					canvas.setFillColor(grid[h][x][y] ? trueColor : falseColor);
					Point center = FieldHelper.math.tile2Pixel(x, y, h);
					int[] p = FieldHelper.getTilePolygon(center.x + x0, center.y + y0, 1);
					canvas.fillPolygon(p);
					canvas.drawPolygon(p, true);
				}
			}
		}
		int h = spnMinH.getValue();
		int x = spnMinX.getValue();
		int y = spnMinY.getValue();
		Point center = FieldHelper.math.tile2Pixel(x, y, h);
		int[] p = FieldHelper.getTilePolygon(center.x + x0, center.y + y0, 0.8f);
		canvas.setPaintColor(centerColor);
		canvas.fillPolygon(p);
		canvas.drawPolygon(p, true);
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
			items[i] = "" + (i - spnMinH.getValue());
		cmbHeight.setItems(items);
		cmbHeight.setValue(height + spnMinH.getValue());
		canvas.redraw();
	}

	public void open(Mask initial) {
		grid = initial.grid.clone();
		for (int i = 0; i < grid.length; i++) {
			grid[i] = initial.grid[i].clone();
			for (int j = 0; j < grid[i].length; j++)
				grid[i][j] = initial.grid[i][j].clone();
		}
		spnMinH.setValue(initial.centerH - 1);
		spnMinX.setValue(initial.centerX - 1);
		spnMinY.setValue(initial.centerY - 1);
		spnMaxH.setValue(grid.length - initial.centerH);
		spnMaxX.setValue(grid[0].length - initial.centerX);
		spnMaxY.setValue(grid[0][0].length - initial.centerY);
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
		m.centerH = spnMinH.getValue() + 1;
		m.centerX = spnMinX.getValue() + 1;
		m.centerY = spnMinY.getValue() + 1;
		return m;
	}
}
