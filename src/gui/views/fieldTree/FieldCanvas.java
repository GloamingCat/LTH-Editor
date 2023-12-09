package gui.views.fieldTree;

import lwt.LFlags;
import lwt.container.LCanvas;
import lwt.container.LContainer;
import lwt.event.LMouseEvent;
import lwt.event.listener.LMouseListener;
import lwt.graphics.LColor;
import lwt.graphics.LPainter;
import lwt.graphics.LPoint;
import lwt.widget.LLabel;
import gui.helper.FieldHelper;
import gui.views.fieldTree.action.BucketAction;
import gui.views.fieldTree.action.CharAction;
import gui.views.fieldTree.action.EraseAction;
import gui.views.fieldTree.action.PencilAction;

import java.util.ArrayList;
import java.util.Stack;

import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.field.Party;

public abstract class FieldCanvas extends LCanvas {

	public Field field;
	public Layer currentLayer;
	public Party currentParty;
	public CharTile currentChar;
	public float scale = 1;
	public int x0;
	public int y0;

	public LLabel lblId = null;
	public LLabel lblCoords = null;

	protected LPoint mousePoint = new LPoint(0, 0);
	protected int tileX = 0;
	protected int tileY = 0;
	protected int height = 0;
	protected LPoint clickedTile = null;
	protected int clickedHeight = 0;
	protected boolean draggingLeft = false;
	protected LPoint dragOrigin = null;
	protected int mode = 0;
	protected int tool = 0;
	protected boolean showGrid = true;
	protected LPoint selectionPoint;
	protected int[][] selection;

	public LColor hoverColor = new LColor(0, 0, 0);
	public LColor cursorColor = new LColor(255, 0, 0);
	public LColor partyColor = new LColor(0, 0, 255);
	public LColor selectionColor = new LColor(255, 255, 0);

	//////////////////////////////////////////////////
	// {{ Initialization

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public FieldCanvas(LContainer parent) {
		super(parent);
		addPainter(new LPainter() {
			public void paint() {
				draw(this);
				setTransparency(100);
				if (currentParty != null) {
					setFillColor(partyColor);
					drawParty(this);
				} else if (currentLayer != null) {
					setFillColor(selectionColor);
					drawSelection(this);
				}
			}
		});
		addMouseListener(new LMouseListener() {
			public void onMouseChange(LMouseEvent e) {
				if (e.button == 0) {
					onMouseMove(e.x, e.y);
				} else if (e.type == LFlags.PRESS) {
					if (tileX < 0 || tileX >= field.sizeX || tileY < 0 || tileY >= field.sizeY)
						return;
					dragOrigin = new LPoint(tileX, tileY);
					if (e.button == LFlags.LEFT) {
						draggingLeft = true;
						onTileLeftDown();
					} else if (e.button == LFlags.RIGHT && !draggingLeft) {
						onTileRightDown();
					}
				} else if (e.type == LFlags.RELEASE) {
					if (e.button == LFlags.LEFT) {
						if (draggingLeft)
							onTileLeftUp();
						draggingLeft = false;
					} else if (e.button == LFlags.RIGHT && !draggingLeft) {
						onTileRightUp();
					}
					dragOrigin = null;
				}
			}
		});
		selection = new int[1][1];
		selection[0][0] = 0;
		selectionPoint = new LPoint(0, 0);
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Draw

	public void draw(LPainter painter) {
		if (field != null) {
			drawBuffer(0, 0, scale, scale);
			painter.setPaintColor(hoverColor);
			drawCursor(painter, mousePoint);
			if (clickedTile != null) {
				LPoint clickPoint = FieldHelper.math.tile2Pixel(clickedTile.x, clickedTile.y, clickedHeight);
				painter.setPaintColor(cursorColor);
				drawCursor(painter, clickPoint);
			}
		}
	}

	public abstract void redrawBuffer();

	protected void drawSelection(LPainter painter) {
		if (dragOrigin == null || draggingLeft)
			return;
		int h = currentLayer.info.height - 1;
		LPoint p1 = FieldHelper.math.tile2Pixel(tileX, tileY, h);
		LPoint p2 = FieldHelper.math.tile2Pixel(tileX, dragOrigin.y, h);
		LPoint p3 = FieldHelper.math.tile2Pixel(dragOrigin.x, dragOrigin.y, h);
		LPoint p4 = FieldHelper.math.tile2Pixel(dragOrigin.x, tileY, h);
		int[] poly = new int[] { (int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale), (int) ((p2.x + x0) * scale),
				(int) ((p2.y + y0) * scale), (int) ((p3.x + x0) * scale), (int) ((p3.y + y0) * scale),
				(int) ((p4.x + x0) * scale), (int) ((p4.y + y0) * scale), (int) ((p1.x + x0) * scale),
				(int) ((p1.y + y0) * scale) };
		painter.fillPolygon(poly);
	}

	protected void drawParty(LPainter painter) {
		int maxx = currentParty.maxX() - 1;
		int maxy = currentParty.maxY() - 1;
		int minx = currentParty.x - 1;
		int miny = currentParty.y - 1;
		LPoint p1 = FieldHelper.math.tile2Pixel(maxx, maxy, currentParty.h - 1);
		LPoint p2 = FieldHelper.math.tile2Pixel(maxx, miny, currentParty.h - 1);
		LPoint p3 = FieldHelper.math.tile2Pixel(minx, miny, currentParty.h - 1);
		LPoint p4 = FieldHelper.math.tile2Pixel(minx, maxy, currentParty.h - 1);
		int[] poly = new int[] { (int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale), (int) ((p2.x + x0) * scale),
				(int) ((p2.y + y0) * scale), (int) ((p3.x + x0) * scale), (int) ((p3.y + y0) * scale),
				(int) ((p4.x + x0) * scale), (int) ((p4.y + y0) * scale), (int) ((p1.x + x0) * scale),
				(int) ((p1.y + y0) * scale) };
		painter.fillPolygon(poly);
		int direction = (currentParty.rotation * 90 + FieldHelper.math.initialDirection) % 360;
		String dirName =  "/img/falsearrow_" + direction;
		painter.setTransparency(255);
		painter.drawImageCenter(dirName + ".png",
				(int) (x0 + (p1.x + p3.x) * scale / 2),
				(int) (y0 + (p1.y + p3.y) * scale / 2),
				scale, scale);
	}
	
	public void drawCursor(LPainter painter, LPoint point) {
		int[] p = FieldHelper.getTilePolygon(
				Math.round(scale * (point.x + x0)),
				Math.round(scale * (point.y + y0)),
				scale * 0.75f);
		painter.drawPolygon(p, true);
	}
	
	// }}

	//////////////////////////////////////////////////
	// {{ Callbacks

	public void onMouseMove(int x, int y) {
		LPoint tilePos = FieldHelper.math.pixel2Tile(x * 1.0f / scale - x0, y * 1.0f / scale - y0,
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
		if (mode == 0) // Layers
			useTool(tileX, tileY);
		else { // Characters and Parties
			clickedTile = new LPoint(tileX, tileY);
			clickedHeight = height;
			onTileChange(tileX, tileY);
			redrawBuffer();
		}
		redraw();
	}

	public void onTileLeftUp() {
		if (mode == 1) // Characters
			moveCharacter(tileX, tileY, dragOrigin);
	}

	public void onTileRightDown() {
	}

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

	public void onTileChange(int x, int y) {
	}

	public void onTileChange(ArrayList<LPoint> tiles) {
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Visualization

	public void refresh() {
		redrawBuffer();
		redraw();
	}

	public void rescale(float scale) {
		this.scale = scale;
		LPoint pixelSize;
		x0 = 100;
		if (field == null) {
			pixelSize = new LPoint(0, 0);
			y0 = 0;
		} else {
			pixelSize = FieldHelper.math.pixelSize(field.sizeX, field.sizeY);
			y0 = (FieldHelper.math.pixelDisplacement(field.sizeY) + x0
					+ FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		}
		setCurrentSize(Math.round((pixelSize.x + x0 * 2 - FieldHelper.config.grid.tileB) * scale),
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
		clickedTile = new LPoint(x, y);
		clickedHeight = h;
		redraw();
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Edit

	public void setSelection(int[][] s, LPoint p) {
		selection = s;
		selectionPoint = p;
	}

	public void setSelection(int index) {
		selection = new int[][] { { index } };
		selectionPoint = new LPoint(0, 0);
	}

	public void onSelect() {
		if (currentLayer == null)
			return;
		LPoint origin = dragOrigin;
		if (origin == null)
			origin = new LPoint(tileX, tileY);
		int[][] grid = currentLayer.grid;

		int x1 = Math.min(tileX, origin.x);
		int y1 = Math.min(tileY, origin.y);
		int x2 = Math.max(tileX, origin.x);
		int y2 = Math.max(tileY, origin.y);

		selectionPoint = new LPoint(tileX - x1, tileY - y1);
		selection = new int[x2 - x1 + 1][y2 - y1 + 1];
		for (int i = 0; i < selection.length; i++) {
			for (int j = 0; j < selection[i].length; j++) {
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

	public CharTile moveCharacter(int x, int y, LPoint origin) {
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
		switch (tool) {
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
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
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
			ArrayList<LPoint> modified = new ArrayList<>();
			Stack<LPoint> stack = new Stack<LPoint>();
			stack.push(new LPoint(tileX, tileY));
			while (!stack.isEmpty()) {
				LPoint p = stack.pop();
				if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
					if (grid[p.x][p.y] == id) {
						modified.add(p);
						grid[p.x][p.y] = newID;
						LPoint[] shift = FieldHelper.math.neighborShift;
						for (int k = 0; k < shift.length; k++) {
							stack.push(new LPoint(p.x + shift[k].x, p.y + shift[k].y));
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
