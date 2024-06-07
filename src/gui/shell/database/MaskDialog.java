package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.helper.FieldHelper;
import lui.base.LFlags;
import data.Skill.Mask;
import lui.container.LCanvas;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.base.data.LPoint;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LCheckBox;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;

public class MaskDialog extends LObjectDialog<Mask> {

	private LCanvas canvas;
	private LSpinner spnMinH;
	private LSpinner spnMinX;
	private LSpinner spnMinY;
	private LSpinner spnMaxH;
	private LSpinner spnMaxX;
	private LSpinner spnMaxY;
	
	boolean[][][] grid;
	int x0, y0, height;

	boolean showAll = true;
	
	public LColor falseColor;
	public LColor trueColor;
	public LColor centerColor;
	private LCombo cmbHeight;

	public MaskDialog(LWindow parent) {
		super(parent, 600, 400, Vocab.instance.MASKSHELL);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(6);

		// Minimum Limits

		new LLabel(content, Vocab.instance.MINHEIGHT, Tooltip.instance.MINHEIGHT);
		spnMinH = new LSpinner(content);
		spnMinH.setMaximum(20);
		spnMinH.addModifyListener(event -> {
            int n = spnMaxH.getValue() + event.newValue + 1;
            if (n < grid.length) {
                height = Math.max(height, -event.newValue);
                shrink(grid[0].length, grid[0][0].length, n, 0, 0, grid.length - n);
            } else
                expand(grid[0].length, grid[0][0].length, n, 0, 0, n - grid.length);
            updateLayerCombo(event.newValue);
        });

		new LLabel(content, Vocab.instance.MINX, Tooltip.instance.MINX);
		spnMinX = new LSpinner(content);
		spnMinX.setMaximum(20);
		spnMinX.addModifyListener(event -> {
            int n = spnMaxX.getValue() + event.newValue + 1;
            if (n < grid[0].length)
                shrink(n, grid[0][0].length, grid.length, grid[0].length - n, 0, 0);
            else
                expand(n, grid[0][0].length, grid.length, n - grid[0].length, 0, 0);
        });

		new LLabel(content, Vocab.instance.MINY, Tooltip.instance.MINY);
		spnMinY = new LSpinner(content);
		spnMinY.setMaximum(20);
		spnMinY.addModifyListener(event -> {
            int n = spnMaxY.getValue() + event.newValue + 1;
            if (n < grid[0][0].length)
                shrink(grid[0].length, n, grid.length, 0, grid[0][0].length - n, 0);
            else
                expand(grid[0].length, n, grid.length, 0, n - grid[0][0].length, 0);
        });

		// Maximum Limits

		new LLabel(content, Vocab.instance.MAXHEIGHT, Tooltip.instance.MAXHEIGHT);
		spnMaxH = new LSpinner(content);
		spnMaxH.setMaximum(20);
		spnMaxH.addModifyListener(event -> {
            int n = event.newValue + spnMinH.getValue() + 1;
            if (n < grid.length) {
                height = Math.min(height, event.newValue);
                shrink(grid[0].length, grid[0][0].length, n, 0, 0, 0);
            } else
                expand(grid[0].length, grid[0][0].length, n, 0, 0, 0);
            updateLayerCombo(spnMinH.getValue());
        });

		new LLabel(content, Vocab.instance.MAXX, Tooltip.instance.MAXX);
		spnMaxX = new LSpinner(content);
		spnMaxX.setMaximum(20);
		spnMaxX.addModifyListener(event -> {
            int n = event.newValue + spnMinX.getValue() + 1;
            if (n < grid[0].length)
                shrink(n, grid[0][0].length, grid.length, 0, 0, 0);
            else
                expand(n, grid[0][0].length, grid.length, 0, 0, 0);
        });

		new LLabel(content, Vocab.instance.MAXY, Tooltip.instance.MAXY);
		spnMaxY = new LSpinner(content);
		spnMaxY.setMaximum(20);
		spnMaxY.addModifyListener(event -> {
            int n = event.newValue + spnMinY.getValue() + 1;
            if (n < grid[0][0].length)
                shrink(grid[0].length, n, grid.length, 0, 0, 0);
            else
                expand(grid[0].length, n, grid.length, 0, 0, 0);
        });

		new LLabel(content, Vocab.instance.HEIGHT, Tooltip.instance.MASKH);
		cmbHeight = new LCombo(content, LCombo.READONLY);
		cmbHeight.setIncludeID(false);
		cmbHeight.addModifyListener(event -> {
            height = event.newValue - spnMinH.getValue();
            canvas.repaint();
        });

		LCheckBox btnShowALl = new LCheckBox(content);
		btnShowALl.setText(Vocab.instance.SHOWALL);
		btnShowALl.getCellData().setExpand(true, false);
		btnShowALl.getCellData().setSpread(4, 1);
		btnShowALl.addModifyListener(e -> {
			showAll = e.newValue;
			canvas.repaint();
		});
		btnShowALl.setValue(true);
		
		canvas = new LCanvas(content);
		canvas.getCellData().setExpand(true, true);
		canvas.getCellData().setSpread(6, 1);
		
		canvas.addPainter(new LPainter() {
			@Override
			public void paint() {
				canvas.pushBuffer();
				drawGrid(this);
				canvas.popBuffer();
				canvas.drawBuffer(0, 0);
				canvas.disposeBuffer();
			}
		});
		
		// Flip Tile
		
		canvas.addMouseListener(e -> {
            if (e.button == LFlags.LEFT && e.type == LFlags.PRESS) {
                int h = (height + spnMinH.getValue());
				if (h < 0 || h >= grid.length)
					return;
                LPoint size = canvas.getCurrentSize();
                int px = e.x - (x0 + size.x / 2);
                int py = e.y - (y0 + size.y / 2);
                LPoint tile = FieldHelper.math.pixel2Tile(px, py, h);
				if (tile.x < 0 || tile.y < 0 || tile.x >= grid[0].length || tile.y >= grid[0][0].length)
					return;
                grid[h][tile.x][tile.y] = !grid[h][tile.x][tile.y];
				canvas.repaint();
            }
        });
	}
	
	private void drawGrid(LPainter painter) {
		LPoint canvasSize = canvas.getCurrentSize();
		int x0 = this.x0 + canvasSize.x / 2;
		int y0 = this.y0 + canvasSize.y / 2;
		int currentH = spnMinH.getValue() + height;
		for (int h = 0; h < grid.length; h++) {
			if (!showAll && h != currentH)
				continue;
			painter.setTransparency(h == currentH ? 255 : 127);
			for (int x = 0; x < grid[h].length; x++) {
				for (int y = 0; y < grid[h][x].length; y++) {
					painter.setFillColor(grid[h][x][y] ? trueColor : falseColor);
					LPoint center = FieldHelper.math.tile2Pixel(x, y, h);
					int[] p = FieldHelper.getTilePolygon(center.x + x0, center.y + y0, 1);
					painter.fillPolygon(p);
					painter.drawPolygon(p, true);
					if (x == spnMinX.getValue() && y == spnMinY.getValue() && (!showAll || h == currentH)) {
						p = FieldHelper.getTilePolygon(center.x + x0, center.y + y0, 0.8f);
						painter.setPaintColor(centerColor);
						painter.fillPolygon(p);
						painter.drawPolygon(p, true);
					}
				}
			}
		}
	}
	
	private void expand(int x, int y, int h, int dx, int dy, int dh) {
		boolean[][][] newGrid = new boolean[h][x][y];
		for (int k = 0; k < grid.length; k++)
			for (int i = 0; i < grid[k].length; i++)
                System.arraycopy(grid[k][i], 0, newGrid[k + dh][i + dx], dy, grid[k][i].length);
		grid = newGrid;
		LPoint p = FieldHelper.math.pixelSize(x, y);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(y) - p.y / 2;
		canvas.repaint();
	}
	
	private void shrink(int x, int y, int h, int dx, int dy, int dh) {
		boolean[][][] newGrid = new boolean[h][x][y];
		for (int k = 0; k < h; k++)
			for (int i = 0; i < x; i++)
                System.arraycopy(grid[k + dh][i + dx], dy, newGrid[k][i], 0, y);
		grid = newGrid;
		LPoint p = FieldHelper.math.pixelSize(x, y);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(y) - p.y / 2;
		canvas.repaint();
	}
	
	private void updateLayerCombo(int minH) {
		String[] items = new String[grid.length];
		for (int i = 0; i < grid.length; i++)
			items[i] = "" + (i - minH);
		cmbHeight.setItems(items);
		cmbHeight.setValue(height + minH);
		canvas.repaint();
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
		LPoint p = FieldHelper.math.pixelSize(grid[0].length, grid[0][0].length);
		x0 = FieldHelper.config.grid.tileW / 2 - p.x / 2;
		y0 = FieldHelper.math.pixelDisplacement(grid[0][0].length) - p.y / 2;
		updateLayerCombo(spnMinH.getValue());
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
