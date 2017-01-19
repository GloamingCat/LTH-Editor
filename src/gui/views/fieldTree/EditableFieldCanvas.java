package gui.views.fieldTree;

import gui.helper.FieldHelper;
import gui.views.fieldTree.action.BucketAction;
import gui.views.fieldTree.action.EraseAction;
import gui.views.fieldTree.action.PencilAction;

import java.util.ArrayList;
import java.util.Stack;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class EditableFieldCanvas extends FieldCanvas {

	private int tool;
	private Point selectionPoint;
	private int[][] selection;
	
	protected int tileX = 0;
	protected int tileY = 0;
	
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
				if (e.button == 1) {	// Left button.
					onTileLeftClick(tileX, tileY);
					draggingLeft = true;
				} else {				// Right button. 
					dragOrigin = new Point(tileX, tileY);
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1) { 	// Left button.
					draggingLeft = false;
				} else {				// Right button.
					onTileRightClick(tileX, tileY, dragOrigin);
					dragOrigin = null;
				}
			}
		});
		
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				int h = currentLayer == null ? 0 : currentLayer.info.height;
				Point tilePos = FieldHelper.math.pixel2Tile(
						e.x * 1.0f / scale - x0,
						e.y * 1.0f / scale - y0, 
						h * FieldHelper.config.pixelsPerHeight);
				if (tileX != tilePos.x || tileY != tilePos.y) {
					if (tilePos.x >= 0 && tilePos.x < field.sizeX && tilePos.y >= 0 && tilePos.y < field.sizeY) {
						tileX = tilePos.x;
						tileY = tilePos.y;
						onTileEnter(tilePos.x, tilePos.y);
						if (draggingLeft) {
							onTileLeftClick(tileX, tileY);
						}
					}
				}
			}
		});
		
		addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	        	if (currentLayer != null && dragOrigin != null) {
            		e.gc.setAlpha(150);
            		e.gc.setBackground(new Color(Display.getCurrent(), new RGB(255, 255, 80)));
            		drawSelection(e.gc);
	        	}
	        }
	    });
		
		selection = new int[1][1];
		selection[0][0] = 0;
		selectionPoint = new Point(0, 0);
	}

	private void drawSelection(GC gc) {
		Point p1 = FieldHelper.math.tile2Pixel(tileX, tileY, currentLayer.info.height);
		Point p2 = FieldHelper.math.tile2Pixel(tileX, dragOrigin.y, currentLayer.info.height);
		Point p3 = FieldHelper.math.tile2Pixel(dragOrigin.x, dragOrigin.y, currentLayer.info.height);
		Point p4 = FieldHelper.math.tile2Pixel(dragOrigin.x, tileY, currentLayer.info.height);
		
		int[] poly = new int[] {p1.x + x0, p1.y + y0,
								p2.x + x0, p2.y + y0,
								p3.x + x0, p3.y + y0,
								p4.x + x0, p4.y + y0 };
		gc.fillPolygon(poly);
	}
	
	// -------------------------------------------------------------------------------------
	// Tools
	// -------------------------------------------------------------------------------------
	
	public void setTool(int t) {
		tool = t;
	}
	
	public void setSelection(int[][] s, Point p) {
		selection = s;
		selectionPoint = p;
	}
	
	private void useTool(int x, int y) {
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
		PencilAction action = new PencilAction(grid, tileX, tileY, values, selection, this);
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
	// Mouse Call backs
	// -------------------------------------------------------------------------------------

	public void onTileLeftClick(int x, int y) {
		useTool(x, y);
	}
	
	public void onTileRightClick(int x, int y, Point origin) {
		if (origin == null)
			origin = new Point(tileX, tileY);
		System.out.println(origin); // 10, 11
		System.out.println(x + " " + y); // 3, 13
		int[][] grid = currentLayer.grid;
		
		int x1 = Math.min(tileX, origin.x); // 3
		int y1 = Math.min(tileY, origin.y); // 11
		int x2 = Math.max(tileX, origin.x); // 10 
		int y2 = Math.max(tileY, origin.y); // 13
		
		selectionPoint = new Point(tileX - x1, tileY - y1); // 0, 2
		selection = new int[x2 - x1 + 1][y2 - y1 + 1];
		for(int i = 0; i < selection.length; i++) {
			for(int j = 0; j < selection[i].length; j++) {
				selection[i][j] = grid[x1 + i][y1 + j];
			}
		}
	}

	public void onTileEnter(int x, int y) { }
	
}
