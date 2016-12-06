package gui.views.fieldTree;

import gui.helper.FieldHelper;

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
				if (tileX >= 0 && tileX < field.sizeX && tileY >= 0 && tileY < field.sizeY) {
					if (e.button == 1) {
						onTileLeftClick(tileX, tileY);
						draggingLeft = true;
					} else {
						dragOrigin = new Point(tileX, tileY);
					}
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 1) {
					draggingLeft = false;
				} else {
					onTileRightClick(tileX, tileY, dragOrigin);
					dragOrigin = null;
				}
			}
		});
		
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				int h = currentLayer == null ? 0 : currentLayer.info.height;
				Point tilePos = FieldHelper.math.pixel2Tile(
						Math.round(e.x / scale - x0),
						Math.round(e.y / scale - y0), 
						h * FieldHelper.config.pixelsPerHeight);
				if (tileX != tilePos.x || tileY != tilePos.y) {
					if (tilePos.x >= 0 && tilePos.x < field.sizeX && tilePos.y >= 0 && tilePos.y < field.sizeY) {
						tileX = tilePos.x;
						tileY = tilePos.y;
						onTileEnter(tilePos.x, tilePos.y);
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
		boolean needsRedraw = false;
		tileX -= selectionPoint.x;
		tileY -= selectionPoint.y;
		for(int i = 0; i < selection.length && tileX + i < grid.length; i++) {
			for(int j = 0; j < selection[i].length && tileY + j < grid[0].length; j++) {
				if (selection[i][j] != grid[tileX + i][tileY + j]) {
					grid[tileX + i][tileY + j] = selection[i][j];
					updateTileImage(tileX + i,  tileY + j);
					needsRedraw = true;
				}
			}
		}
		if (needsRedraw) {
			redraw();
			//Project.current.fieldTree.changed = true;
		}
	}
	
	private void useBucket(int[][] grid, int tileX, int tileY) {
		int id = grid[tileX][tileY];
		if (selection[selectionPoint.x][selectionPoint.y] != id) {
			Stack<Point> stack = new Stack<Point>();
			stack.push(new Point(tileX, tileY));
			while(!stack.isEmpty()) {
				Point p = stack.pop();
				if (p.x >= 0 && p.x < grid.length && p.y >= 0 && p.y < grid[0].length) {
					if (grid[p.x][p.y] == id) {
						grid[p.x][p.y] = selection[selectionPoint.x][selectionPoint.y];
						updateTileImage(p.x,  p.y);
						Point[] shift = FieldHelper.math.neighborShift;
						for(int k = 0; k < shift.length; k++) {
							stack.push(new Point(p.x + shift[k].x, p.y + shift[k].y));
						}
					}
				}
			}
			redraw();
			//Project.current.fieldTree.changed = true;
		}
	}
	
	private void useEraser(int[][] grid, int tileX, int tileY) {
		if (grid[tileX][tileY] != -1) {
			grid[tileX][tileY] = -1;
			updateTileImage(tileX,  tileY);
			redraw();
			//Project.current.fieldTree.changed = true;
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
		
		if (currentLayer.info.type <= 2) {
			int[][] grid = currentLayer.grid;
	
			int x1 = Math.max(tileX, origin.x);
			int y1 = Math.max(tileY, origin.y);
			int x2 = Math.min(tileX, origin.x);
			int y2 = Math.min(tileY, origin.y);
			
			selectionPoint = new Point(x1 - tileX, y1 - tileY);
			selection = new int[x1 - x2 + 1][y1 - y2 + 1];
			for(int i = 0; i < selection.length; i++) {
				for(int j = 0; j < selection[i].length; j++) {
					selection[i][j] = grid[x2 + i][y2 + j];
				}
			}
		}
	}

	public void onTileEnter(int x, int y) { }
	
}
