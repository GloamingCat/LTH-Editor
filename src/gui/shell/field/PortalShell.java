package gui.shell.field;

import gui.Vocab;
import gui.helper.FieldHelper;
import gui.views.fieldTree.FieldCanvasGC;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

import lwt.dialog.LObjectShell;
import lwt.widget.LLabel;
import project.Project;
import data.field.Field;
import data.field.Transition.Portal;
import data.subcontent.Point;

public class PortalShell extends LObjectShell<Portal> {
	
	protected FieldCanvasGC canvas;
	protected Spinner spnH;
	protected ScrolledComposite scrolledComposite;
	protected LLabel lblPos;
	protected boolean[][][] selectedTiles;
	protected int fieldID;
	
	/**
	 * @wbp.parser.constructor
	 */
	public PortalShell(Shell parent, int fieldID) {
		super(parent);
		this.fieldID = fieldID;
		setMinimumSize(640, 480);
		content.setLayout(new GridLayout(3, false));
		
		scrolledComposite = new ScrolledComposite(content, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
		canvas = new FieldCanvasGC(scrolledComposite, SWT.NONE) {
			public void onTileLeftDown() {
				selectedTiles[height][tileX][tileY] = !selectedTiles[height][tileX][tileY];
				canvas.redraw();
			}
			public void onTileRightDown() {}
			public void onTileEnter(int x, int y) {
				lblPos.setText("(" + (x + 1) + "," + (y + 1) + ")");
			}
		};
		canvas.addPaintListener(new PaintListener() {
	        public void paintControl(PaintEvent e) {
	    		for (int h = 0; h < selectedTiles.length; h++)
	    			for (int x = 0; x < selectedTiles[h].length; x++)
	    				for (int y = 0; y < selectedTiles[h][x].length; y++) 
	    					if (selectedTiles[h][x][y]) {
	    						Point point = FieldHelper.math.tile2Pixel(x, y, h);
	    						canvas.drawCursor(e.gc, SWTResourceManager.getColor(SWT.COLOR_YELLOW), point);
	    		}
	        }
	    });
		
		scrolledComposite.setContent(canvas);
		
		new LLabel(content, Vocab.instance.HEIGHT).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		spnH = new Spinner(content, SWT.BORDER);
		spnH.setMinimum(1);
		spnH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnH.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				canvas.setHeight(spnH.getSelection() - 1);
				canvas.redraw();
			}
		});
		
		lblPos = new LLabel(content, "(-99, -99)");
		lblPos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	public void open(Portal initial) {
		super.open(initial);
		if (initial.size() > 0) {
			spnH.setSelection(initial.get(initial.size() - 1).height);
		} else {
			spnH.setSelection(1);
		}
		Field field = Project.current.fieldTree.loadField(fieldID);
		int maxHeight = field.layers.maxHeight();
		selectedTiles = initial.getMask(field.sizeX, field.sizeY, maxHeight);
		spnH.setMaximum(maxHeight);
		canvas.setField(field);
		canvas.setHeight(spnH.getSelection() - 1);
		content.layout();
	}

	@Override
	protected Portal createResult(Portal initial) {
		return new Portal(selectedTiles);
	}
	
}
