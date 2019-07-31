package gui.views.fieldTree;

import gui.helper.FieldHelper;
import gui.views.fieldTree.action.BucketAction;
import gui.views.fieldTree.action.CharAction;
import gui.views.fieldTree.action.EraseAction;
import gui.views.fieldTree.action.PencilAction;

import java.util.ArrayList;
import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import data.field.CharTile;

public class EditableFieldCanvas extends FieldCanvas {

	private int tool = 0;
	private int mode = 0;
	private Point selectionPoint;
	private int[][] selection;
	
	protected boolean draggingLeft = false;
	protected Point dragOrigin = null;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EditableFieldCanvas(Composite parent, int style) {
		super(parent, style);
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
		
		addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
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
		
		selection = new int[1][1];
		selection[0][0] = 0;
		selectionPoint = new Point(0, 0);
	}
	
	// -------------------------------------------------------------------------------------
	// Draw
	// -------------------------------------------------------------------------------------

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
	
	// -------------------------------------------------------------------------------------
	// Mouse Call backs
	// -------------------------------------------------------------------------------------

	public void onTileLeftDown() {
		if (mode == 0)	 // Layers
			useTool(tileX, tileY); 
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
	}
	
	// -------------------------------------------------------------------------------------
	// Editing
	// -------------------------------------------------------------------------------------
	
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
	}
	
	// -------------------------------------------------------------------------------------
	// Layers
	// -------------------------------------------------------------------------------------
	
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
						updateTileImage(p.x,  p.y);
						Point[] shift = FieldHelper.math.neighborShift;
						for(int k = 0; k < shift.length; k++) {
							stack.push(new Point(p.x + shift[k].x, p.y + shift[k].y));
						}
					}
				}
			}
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
	
	// -------------------------------------------------------------------------------------
	// Character
	// -------------------------------------------------------------------------------------
	
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
		return tile;
	}

}
