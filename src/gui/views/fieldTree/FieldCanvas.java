package gui.views.fieldTree;

import data.subcontent.Tile;
import lui.container.LCanvas;
import lui.container.LScrollPanel;
import lui.graphics.LColor;
import lui.graphics.LPainter;
import lui.base.data.LPoint;
import gui.helper.FieldHelper;
import gui.views.fieldTree.action.BucketAction;
import gui.views.fieldTree.action.CharAction;
import gui.views.fieldTree.action.EraseAction;
import gui.views.fieldTree.action.PencilAction;
import lui.base.LFlags;

import java.util.ArrayList;
import java.util.Stack;
import java.util.function.Consumer;

import data.field.CharTile;
import data.field.Field;
import data.field.Layer;
import data.field.Party;

public abstract class FieldCanvas extends LCanvas {

	public static final int TILE = 0, CHAR = 1, PARTY = 2;
	public static final int PENCIL = 0, BUCKET = 1, ERASER = 2;
	public Consumer<CharTile> onMoveCharacter;
	public Consumer<int[][]> onSelectArea;

	public Consumer<Tile> onTileEnter, onSelectAreaStart;

	public Field field;
	public Layer currentLayer;
	public Party currentParty;
	public CharTile currentChar;
	protected float scale = 1;
	protected int x0;
	protected int y0;

	protected LPoint mousePoint = new LPoint(0, 0);
	protected Tile currentTile = new Tile(0, 0, 0);
	protected Tile clickedTile = null;
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

	protected LScrollPanel scrollPanel;
	//////////////////////////////////////////////////
	// {{ Initialization

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public FieldCanvas(LScrollPanel parent) {
		super(parent);
		scrollPanel = parent;
		addPainter(new LPainter() {
			public void paint() {
				try {
					draw(this);
					setTransparency(100);
					if (currentParty != null && mode == PARTY) {
						setFillColor(partyColor);
						drawParty(this);
					} else if (mode == TILE) {
						setFillColor(selectionColor);
						drawSelection(this);
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		addMouseListener(e -> {
            if (e.button == 0) {
                onMouseMove(e.x, e.y);
            } else if (e.type == LFlags.PRESS) {
                if (currentTile.dx < 0 || currentTile.dx >= field.sizeX || currentTile.dy < 0 || currentTile.dy >= field.sizeY)
                    return;
                dragOrigin = new LPoint(currentTile.dx, currentTile.dy);
                if (e.button == LFlags.LEFT) {
                    draggingLeft = true;
                    onTileClick();
                } else if (e.button == LFlags.RIGHT && !draggingLeft) {
					if (onSelectAreaStart != null)
						onSelectAreaStart.accept(currentTile);
                }
            } else if (e.type == LFlags.RELEASE) {
                if (e.button == LFlags.LEFT) {
                    if (draggingLeft) {
						if (mode == CHAR)
							dragCharacter(currentTile.dx, currentTile.dy, dragOrigin);
					}
                    draggingLeft = false;
                } else if (e.button == LFlags.RIGHT && !draggingLeft && dragOrigin != null) {
					selectArea(dragOrigin, currentTile.dx, currentTile.dy);
					if (onSelectArea != null)
						onSelectArea.accept(selection);
					repaint();
                }
                dragOrigin = null;
            }
        });
		selection = new int[0][0];
		selectionPoint = null;
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Draw

	protected void draw(LPainter painter) {
		if (field != null) {
			drawBuffer(0, 0, scale, scale);
			painter.setPaintColor(hoverColor);
			drawCursor(painter, mousePoint);
			if (clickedTile != null && mode != TILE) {
				LPoint clickPoint = FieldHelper.math.tile2Pixel(clickedTile.dx, clickedTile.dy, currentTile.height);
				painter.setPaintColor(cursorColor);
				drawCursor(painter, clickPoint);
			}
		}
	}

	protected abstract void redrawBuffer();

	protected void drawSelection(LPainter painter) {
		if (dragOrigin == null || draggingLeft || currentLayer == null)
			return;
		int h = currentLayer.info.height - 1;
		LPoint p1 = FieldHelper.math.tile2Pixel(currentTile.dx, currentTile.dy, h);
		LPoint p2 = FieldHelper.math.tile2Pixel(currentTile.dx, dragOrigin.y, h);
		LPoint p3 = FieldHelper.math.tile2Pixel(dragOrigin.x, dragOrigin.y, h);
		LPoint p4 = FieldHelper.math.tile2Pixel(dragOrigin.x, currentTile.dy, h);
		int[] poly = new int[] { (int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale), (int) ((p2.x + x0) * scale),
				(int) ((p2.y + y0) * scale), (int) ((p3.x + x0) * scale), (int) ((p3.y + y0) * scale),
				(int) ((p4.x + x0) * scale), (int) ((p4.y + y0) * scale), (int) ((p1.x + x0) * scale),
				(int) ((p1.y + y0) * scale) };
		painter.fillPolygon(poly);
	}

	protected void drawParty(LPainter painter) {
		int maxX = currentParty.maxX() - 1;
		int maxY = currentParty.maxY() - 1;
		int minX = currentParty.x - 1;
		int minY = currentParty.y - 1;
		LPoint p1 = FieldHelper.math.tile2Pixel(maxX, maxY, currentParty.h - 1);
		LPoint p2 = FieldHelper.math.tile2Pixel(maxX, minY, currentParty.h - 1);
		LPoint p3 = FieldHelper.math.tile2Pixel(minX, minY, currentParty.h - 1);
		LPoint p4 = FieldHelper.math.tile2Pixel(minX, maxY, currentParty.h - 1);
		int[] poly = new int[] { (int) ((p1.x + x0) * scale), (int) ((p1.y + y0) * scale), (int) ((p2.x + x0) * scale),
				(int) ((p2.y + y0) * scale), (int) ((p3.x + x0) * scale), (int) ((p3.y + y0) * scale),
				(int) ((p4.x + x0) * scale), (int) ((p4.y + y0) * scale), (int) ((p1.x + x0) * scale),
				(int) ((p1.y + y0) * scale) };
		painter.fillPolygon(poly);
		int direction = (currentParty.rotation * 90 + FieldHelper.math.initialDirection) % 360;
		String dirName =  "img/falsearrow_" + direction;
		painter.setTransparency(255);
		painter.drawImageCenter(dirName + ".png",
				(int) ((x0 + (p1.x + p3.x) / 2) * scale),
				(int) ((y0 + (p1.y + p3.y) / 2) * scale),
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
				currentTile.height);
		if ((currentTile.dx != tilePos.x || currentTile.dy != tilePos.y) && field != null) {
			if (tilePos.x >= 0 && tilePos.x < field.sizeX && tilePos.y >= 0 && tilePos.y < field.sizeY) {
				currentTile.dx = tilePos.x;
				currentTile.dy = tilePos.y;
				mousePoint = FieldHelper.math.tile2Pixel(currentTile.dx, currentTile.dy, currentTile.height);
				if (draggingLeft)
					onTileClick();
				if (onTileEnter != null)
					onTileEnter.accept(currentTile);
				repaint();
			}
		}
	}

	public void onTileClick() {
		if (mode == TILE)
			useTool(currentTile.dx, currentTile.dy);
		else { // Characters and Parties
			clickedTile = currentTile.clone();
			onTileChange(currentTile.dx, currentTile.dy);
			refreshBuffer(false);
		}
	}

	public void onTileChange(int x, int y) {}

	public void onTileChange(ArrayList<LPoint> tiles) {}

	// }}

	//////////////////////////////////////////////////
	// {{ Visualization

	public void refreshBuffer(boolean all) {
		redrawBuffer();
		repaint();
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
		int w = Math.round((pixelSize.x + x0 * 2 - FieldHelper.config.grid.tileB) * scale);
		int h = Math.round((pixelSize.y + y0) * scale);
		scrollPanel.setContentSize(w, h);
	}

	public void setCurrentLayer(Layer layer) {
		if (layer != null)
			setHeight(layer.info.height - 1);
		if (currentLayer != layer) {
			currentLayer = layer;
			if (showGrid)
				refreshBuffer(true);
		}
	}

	public void setHeight(int h) {
		currentTile.height = h;
	}

	public void setParty(Party party) {
		if (party != currentParty) {
			currentParty = party;
			if (party != null)
				currentTile.height = party.h - 1;
			repaint();
		}
	}

	public void setCharacter(CharTile tile) {
		if (tile != currentChar) {
			currentChar = tile;
			if (currentChar != null) {
				currentTile.dx = tile.x - 1;
				currentTile.dy = tile.y - 1;
				currentTile.height = tile.h - 1;
				clickedTile = currentTile.clone();
			}
			refreshBuffer(false);
		}
	}

	public void setShowGrid(boolean value) {
		if (value != showGrid) {
			showGrid = value;
			refreshBuffer(true);
		}
	}

	public void setField(Field field) {
		if (field != this.field) {
			currentLayer = null;
			currentChar = null;
			currentParty = null;
			this.field = field;
			if (field == null) {
				rescale(1);
			} else {
				rescale(scale);
			}
		}
		try {
			refreshBuffer(true);
		} catch(Exception e) {
			System.out.println("Thread: " + Thread.currentThread().threadId());
		}
	}

	public void setClickedTile(int x, int y, int h) {
		clickedTile = new Tile(x, y, h);
		repaint();
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Edit

	public void setSelection(int[][] s, LPoint p) {
		selection = s;
		selectionPoint = p;
	}

	public void setSelection(int index) {
		setSelection(new int[][] { { index } },  new LPoint(0, 0));
	}

	public void selectArea(LPoint dragOrigin, int tileX, int tileY) {
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
            System.arraycopy(grid[x1 + i], y1, selection[i], 0, selection[i].length);
		}
	}

	public void setTool(int t) {
		tool = t;
	}

	public void setMode(int m) {
		mode = m;
		if (m == 0)
			clickedTile = null;
	}

	public void dragCharacter(int x, int y, LPoint origin) {
		if (origin == null)
			return;
		if (origin.x == currentTile.dx && origin.y == currentTile.dy)
			return;
		CharTile tile = field.findCharacter(origin.x + 1, origin.y + 1, currentTile.height + 1);
		if (tile == null)
			return;
		CharAction action = new CharAction(this, tile, x + 1, y + 1, currentTile.height + 1);
		setCharacterTile(tile, x + 1, y + 1, currentTile.height + 1);
		action.redo();
		getActionStack().newAction(action);
	}

	public void setCharacterTile(CharTile tile, int x, int y, int h) {
		int x0 = tile.x;
		int y0 = tile.y;
		tile.x = x;
		tile.y = y;
		tile.h = h;
		onTileChange(x0 - 1, y0 - 1);
		onTileChange(x - 1, y - 1);
		if (onMoveCharacter != null)
			onMoveCharacter.accept(tile);
		refreshBuffer(false);
	}

	// }}

	//////////////////////////////////////////////////
	// {{ Tools

	private void useTool(int x, int y) {
		if (currentLayer == null)
			return;
		int[][] grid = currentLayer.grid;
		switch (tool) {
			case PENCIL: // pencil
				usePencil(grid, x, y);
				break;
			case BUCKET: // bucket
				useBucket(grid, x, y);
				break;
			case ERASER: // eraser
				useEraser(grid, x, y);
				break;
		}
	}

	private void usePencil(int[][] grid, int tileX, int tileY) {
		if (selectionPoint == null)
			return;
		tileX -= selectionPoint.x;
		tileY -= selectionPoint.y;
		boolean changed = false;
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
		PencilAction action = new PencilAction(this, grid, tileX, tileY, values, selection);
		if (action.apply(selection))
			getActionStack().newAction(action);
	}

	private void useBucket(int[][] grid, int tileX, int tileY) {
		int id = grid[tileX][tileY];
		int newID = selection[selectionPoint.x][selectionPoint.y];
		if (newID == id)
			return;
		ArrayList<LPoint> modified = new ArrayList<>();
		Stack<LPoint> stack = new Stack<>();
		stack.push(new LPoint(tileX, tileY));
		while (!stack.isEmpty()) {
			LPoint p = stack.pop();
			if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
				if (grid[p.x][p.y] == id) {
					modified.add(p);
					grid[p.x][p.y] = newID;
					LPoint[] shift = FieldHelper.math.neighborShift;
					for (LPoint lPoint : shift) {
						stack.push(new LPoint(p.x + lPoint.x, p.y + lPoint.y));
					}
				}
			}
		}
		onTileChange(modified);
		BucketAction action = new BucketAction(grid, newID, id, modified, this);
		getActionStack().newAction(action);
		refreshBuffer(false);
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
