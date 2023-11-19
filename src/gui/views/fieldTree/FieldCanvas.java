package gui.views.fieldTree;

import lwt.container.LContainer;
import lwt.container.LView;
import lwt.dialog.LShell;
import lwt.widget.LLabel;
import gui.helper.FieldHelper;
import gui.views.fieldTree.action.BucketAction;
import gui.views.fieldTree.action.CharAction;
import gui.views.fieldTree.action.EraseAction;
import gui.views.fieldTree.action.PencilAction;

import java.util.ArrayList;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.wb.swt.SWTResourceManager;

import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.field.Party;
import data.subcontent.Point;

public abstract class FieldCanvas extends LView {

	public Field field;
	public Layer currentLayer;
	public Party currentParty;
	public CharTile currentChar;
	public float scale = 1;
	public int x0;
	public int y0;
	
	public LLabel lblId = null;
	public LLabel lblCoords = null;

	protected Point mousePoint = new Point(0, 0);
	protected int tileX = 0;
	protected int tileY = 0;
	protected int height = 0;
	protected Point clickedTile = null;
	protected int clickedHeight = 0;
	protected boolean draggingLeft = false;
	protected Point dragOrigin = null;
	protected int mode = 0;
	protected int tool = 0;
	protected boolean showGrid = true;
	protected Point selectionPoint;
	protected int[][] selection;

	protected Image buffer;

	public Color cursorColor = SWTResourceManager.getColor(SWT.COLOR_RED);

	//////////////////////////////////////////////////
	// {{ Initialization
	
	/**
	 * @wbp.parser.constructor
	 */
	public FieldCanvas() {
		this(new LShell());
	}
	
	public FieldCanvas(LContainer parent) {
		super(parent, false);
		setBackground(SWTResourceManager.getColor(new RGB(250, 250, 250)));
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				draw(e.gc);
				if (currentParty != null) {
					e.gc.setAlpha(100);
					e.gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
					drawParty(e.gc);
				} else if (currentLayer != null && dragOrigin != null && !draggingLeft) {
					e.gc.setAlpha(100);
					e.gc.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
					drawSelection(e.gc);
				}
			}
		});
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				onMouseMove(e.x, e.y);
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (tileX < 0 || tileX >= field.sizeX || tileY < 0 || tileY >= field.sizeY)
					return;
				dragOrigin = new Point(tileX, tileY);
				if (e.button == 1) {	// Left button.
					draggingLeft = true;
					onTileLeftDown();
				} else if (!draggingLeft) {
					onTileRightDown();
				}
			}
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1) { // Left button.
					if (draggingLeft)
						onTileLeftUp();
					draggingLeft = false;
				} else if (!draggingLeft) {	// Right button.
					onTileRightUp();
				}
				dragOrigin = null;
			}
		});
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				onDispose();
			}
		});
		selection = new int[1][1];
		selection[0][0] = 0;
		selectionPoint = new Point(0, 0);
	}

	public void onDispose() {
		if (buffer != null)
			buffer.dispose();
		buffer = null;
	}
	// }}

	//////////////////////////////////////////////////
	// {{ Draw

	public void draw(GC gc) {
		if (field != null) {
			gc.drawImage(buffer, 
					0, 0, 
					buffer.getBounds().width, 
					buffer.getBounds().height, 
					0, 0, 
					Math.round(buffer.getBounds().width * scale), 
					Math.round(buffer.getBounds().height * scale));
			drawCursor(gc);
			if (clickedTile != null) {
				Point clickPoint = FieldHelper.math.tile2Pixel(clickedTile.x, 
						clickedTile.y, clickedHeight);
				drawCursor(gc, cursorColor, clickPoint);
			}
		}
	}
	public abstract void redrawBuffer();
	public abstract void drawCursor(GC gc, Color color, Point point);
	public void drawCursor(GC gc) {
		drawCursor(gc, getForeground(), mousePoint);
	}
	
	protected void drawSelection(GC gc) {
		int h = currentLayer.info.height - 1;
		Point p1 = FieldHelper.math.tile2Pixel(tileX, tileY, h);
		Point p2 = FieldHelper.math.tile2Pixel(tileX, dragOrigin.y, h);
		Point p3 = FieldHelper.math.tile2Pixel(dragOrigin.x, dragOrigin.y, h);
		Point p4 = FieldHelper.math.tile2Pixel(dragOrigin.x, tileY, h);
		int[] poly = new int[] {(int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale),
				(int) ((p2.x + x0) * scale), (int) ((p2.y + y0) * scale),
				(int) ((p3.x + x0) * scale), (int) ((p3.y + y0) * scale),
				(int) ((p4.x + x0) * scale), (int) ((p4.y + y0) * scale),
				(int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale) };
		gc.fillPolygon(poly);
	}
	
	protected void drawParty(GC gc) {
		int maxx = currentParty.maxX() - 1;
		int maxy = currentParty.maxY() - 1;
		int minx = currentParty.x - 1;
		int miny = currentParty.y - 1;
		Point p1 = FieldHelper.math.tile2Pixel(maxx, maxy, currentParty.h - 1);
		Point p2 = FieldHelper.math.tile2Pixel(maxx, miny, currentParty.h - 1);
		Point p3 = FieldHelper.math.tile2Pixel(minx, miny, currentParty.h - 1);
		Point p4 = FieldHelper.math.tile2Pixel(minx, maxy, currentParty.h - 1);
		int[] poly = new int[] {(int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale),
				(int) ((p2.x + x0) * scale), (int) ((p2.y + y0) * scale),
				(int) ((p3.x + x0) * scale), (int) ((p3.y + y0) * scale),
				(int) ((p4.x + x0) * scale), (int) ((p4.y + y0) * scale),
				(int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale) };
		gc.fillPolygon(poly);
		int direction = (currentParty.rotation * 90 + FieldHelper.math.initialDirection) % 360;
		String dirName = "/img/falsearrow_" + direction;
		Image arrow = SWTResourceManager.getImage(this.getClass(), dirName + ".png");
		gc.setAlpha(255);
		gc.drawImage(arrow, (int) ((x0 + (p1.x + p3.x - arrow.getBounds().width) / 2) * scale), 
				(int) ((y0 + (p1.y + p3.y - arrow.getBounds().height) / 2) * scale));
	}
	// }}
	
	//////////////////////////////////////////////////
	// {{ Callbacks
	
	public void onMouseMove(int x, int y) {
		Point tilePos = FieldHelper.math.pixel2Tile(
				x * 1.0f / scale - x0,
				y * 1.0f / scale - y0, 
				height * FieldHelper.config.grid.pixelsPerHeight);
		if ((tileX != tilePos.x || tileY != tilePos.y) && field != null) {
			if (tilePos.x >= 0 && tilePos.x < field.sizeX && tilePos.y >= 0 && tilePos.y < field.sizeY) {
				tileX = tilePos.x;
				tileY = tilePos.y;
				mousePoint = FieldHelper.math.tile2Pixel(tileX, tileY, height);
				onTileEnter(tileX, tileY);
				redraw();
			}
		}
	}

	public void onTileLeftDown() {
		if (mode == 0)	// Layers
			useTool(tileX, tileY);
		else { 			// Characters and Parties
			clickedTile = new Point(tileX, tileY);
			clickedHeight = height;
			onTileChange(tileX, tileY);
			redrawBuffer();
		}
		redraw();
	}

	public void onTileLeftUp() {
		if (mode == 1)	// Characters
			moveCharacter(tileX, tileY, dragOrigin);
	}

	public void onTileRightDown() {}

	public void onTileRightUp() {
		if (dragOrigin == null)
			return;
		onSelect();
		redraw();
	}

	public void onTileEnter(int x, int y) {
		if (draggingLeft) {
			onTileLeftDown();
		}
		if (lblCoords != null)
			lblCoords.setText("(" + (x + 1) + ", " + (y + 1) + ", " + (height + 1) + ")");
	}

	public void onTileChange(int x, int y) {}
	
	public void onTileChange(ArrayList<Point> tiles) {}

	// }}
	
	//////////////////////////////////////////////////
	// {{ Visualization
	
	public void refresh() {
		redrawBuffer();
		redraw();
	}
	
	public void rescale(float scale) {
		this.scale = scale;
		Point pixelSize;
		x0 = 100;
		if (field == null) {
			pixelSize = new Point(0, 0);
			y0 = 0;
		} else {
			pixelSize = FieldHelper.math.pixelSize(field.sizeX, field.sizeY);
			y0 = (FieldHelper.math.pixelDisplacement(field.sizeY) + x0 +
					FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		}
		setSize(Math.round((pixelSize.x + x0*2 - FieldHelper.config.grid.tileB) * scale), 
				Math.round((pixelSize.y + y0) * scale));
	}

	public void setCurrentLayer(Layer layer) {
		if (layer != null)
			setHeight(layer.info.height - 1);
		if (currentLayer != layer) {
			currentLayer = layer;
			if (showGrid)
				refresh();
		}
	}

	public void setHeight(int h) {
		height = h;
	}

	public void setParty(Party party) {
		if (party != currentParty) {
			currentParty = party;
			if (party != null)
				height = party.h - 1;
			redraw();
		}
	}

	public void setCharacter(CharTile tile) {
		if (tile != currentChar) {
			currentChar = tile;
			redrawBuffer();
			redraw();
		}
	}

	public void setShowGrid(boolean value) {
		if (value != showGrid) {
			showGrid = value;
			refresh();
		}
	}

	public void setField(Field field) {
		if (field == null) {
			this.field = null;
			this.currentLayer = null;
			rescale(1);
			if (lblId != null)
				lblId.setText("ID: -1");
		} else {
			this.field = field;
			this.currentLayer = null;
			rescale(scale);
			if (lblId != null)
				lblId.setText("ID: " + field.id);
		}
		refresh();
	}	

	public void setClickedTile(int x, int y, int h) {
		clickedTile = new Point(x, y);
		clickedHeight = h;
		redraw();
	}
	
	// }}

	//////////////////////////////////////////////////
	// {{ Edit
	
	public void setSelection(int[][] s, Point p) {
		selection = s;
		selectionPoint = p;
	}

	public void setSelection(int index) {
		selection = new int[][] {{index}};
		selectionPoint = new Point(0, 0);
	}

	public void onSelect() {
		if (currentLayer == null)
			return;
		Point origin = dragOrigin;
		if (origin == null)
			origin = new Point(tileX, tileY);
		int[][] grid = currentLayer.grid;

		int x1 = Math.min(tileX, origin.x);
		int y1 = Math.min(tileY, origin.y);
		int x2 = Math.max(tileX, origin.x); 
		int y2 = Math.max(tileY, origin.y);

		selectionPoint = new Point(tileX - x1, tileY - y1);
		selection = new int[x2 - x1 + 1][y2 - y1 + 1];
		for(int i = 0; i < selection.length; i++) {
			for(int j = 0; j < selection[i].length; j++) {
				selection[i][j] = grid[x1 + i][y1 + j];
			}
		}
		if (selection.length == 1 && selection[0].length == 1)
			FieldSideEditor.instance.selectTile(selection[0][0]);
		else
			FieldSideEditor.instance.unselectTiles();
	}

	public void setTool(int t) {
		tool = t;
	}

	public void setMode(int m) {
		mode = m;
		if (m == 0)
			clickedTile = null;
	}

	public CharTile moveCharacter(int x, int y, Point origin) {
		if (origin == null)
			return null;
		if (origin.x == tileX && origin.y == tileY)
			return null;
		CharTile tile = field.findCharacter(origin.x + 1, origin.y + 1, height + 1);
		if (tile == null)
			return null;
		CharAction action = new CharAction(tile, x + 1, y + 1, height + 1);
		action.redo();
		getActionStack().newAction(action);
		FieldSideEditor.instance.onMoveCharacter(tile);
		return tile;
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Tools
	
	private void useTool(int x, int y) {
		if (currentLayer == null)
			return;
		int[][] grid = currentLayer.grid;
		switch(tool) {
		case 0: // pencil
			usePencil(grid, x, y);
			break;
		case 1: // bucket
			useBucket(grid, x, y);
			break;
		case 2: // eraser
			useEraser(grid, x, y);
			break;
		}
	}

	private void usePencil(int[][] grid, int tileX, int tileY) {
		if (selectionPoint == null)
			return;
		tileX -= selectionPoint.x;
		tileY -= selectionPoint.y;
		int[][] values = new int[selection.length][selection[0].length];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[0].length; j++) {
				if (tileX + i >= grid.length)
					continue;
				if (tileY + j >= grid[0].length)
					continue;
				if (tileX + i < 0)
					continue;
				if (tileY + j < 0)
					continue;
				values[i][j] = grid[tileX + i][tileY + j];
			}
		}
		PencilAction action = new PencilAction(grid, tileX, tileY, values, selection);
		if (action.apply(selection)) {
			getActionStack().newAction(action);
		}
	}

	private void useBucket(int[][] grid, int tileX, int tileY) {
		int id = grid[tileX][tileY];
		int newID = selection[selectionPoint.x][selectionPoint.y];
		if (newID != id) {
			ArrayList<Point> modified = new ArrayList<>();
			Stack<Point> stack = new Stack<Point>();
			stack.push(new Point(tileX, tileY));
			while(!stack.isEmpty()) {
				Point p = stack.pop();
				if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
					if (grid[p.x][p.y] == id) {
						modified.add(p);
						grid[p.x][p.y] = newID;
						Point[] shift = FieldHelper.math.neighborShift;
						for(int k = 0; k < shift.length; k++) {
							stack.push(new Point(p.x + shift[k].x, p.y + shift[k].y));
						}
					}
				}
			}
			onTileChange(modified);
			redrawBuffer();
			redraw();
			BucketAction action = new BucketAction(grid, newID, id, modified, this);
			getActionStack().newAction(action);
		}
	}

	private void useEraser(int[][] grid, int tileX, int tileY) {
		if (grid[tileX][tileY] != -1) {
			EraseAction action = new EraseAction(grid, tileX, tileY, grid[tileX][tileY], this);
			action.redo();
			getActionStack().newAction(action);
		}
	}
	// }}

}
