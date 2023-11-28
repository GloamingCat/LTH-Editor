package gui.shell.field;

import gui.Vocab;
import gui.helper.FieldHelper;
import gui.views.fieldTree.*;

import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;

import lwt.LFlags;
import lwt.container.LScrollPanel;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LLabel;
import lwt.widget.LSpinner;
import project.Project;
import data.field.Field;
import data.field.Transition.Portal;
import data.subcontent.Point;

public class PortalShell extends LObjectShell<Portal> {

	protected FieldCanvas canvas;
	protected LSpinner spnH;
	protected LScrollPanel scrolledComposite;
	protected LLabel lblPos;
	protected boolean[][][] selectedTiles;
	protected int fieldID;

	/**
	 * @wbp.parser.constructor
	 */
	public PortalShell(LShell parent, int fieldID) {
		super(parent);
		this.fieldID = fieldID;
		setMinimumSize(640, 480);
		content.setGridLayout(3, false);

		scrolledComposite = new LScrollPanel(content, true);
		scrolledComposite.setExpand(true, true);
		scrolledComposite.setSpread(3, 1);

		canvas = new FieldCanvasOpenGL(scrolledComposite) {
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

		new LLabel(content, Vocab.instance.HEIGHT).setAlignment(LFlags.CENTER);
		spnH = new LSpinner(content);
		spnH.setMinimum(1);
		spnH.setValue(1);
		spnH.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				canvas.setHeight(event.newValue - 1);
				canvas.redraw();
			}
		});
		lblPos = new LLabel(content, "(-99, -99)", LFlags.EXPAND);

		pack();
	}

	public void open(Portal initial) {
		super.open(initial);
		if (initial.size() > 0) {
			spnH.setValue(initial.get(initial.size() - 1).height);
		} else {
			spnH.setValue(1);
		}
		Field field = Project.current.fieldTree.loadField(fieldID);
		int maxHeight = field.layers.maxHeight();
		selectedTiles = initial.getMask(field.sizeX, field.sizeY, maxHeight);
		spnH.setMaximum(maxHeight);
		canvas.setField(field);
		canvas.setHeight(spnH.getValue() - 1);
		content.layout();
	}

	@Override
	protected Portal createResult(Portal initial) {
		return new Portal(selectedTiles);
	}

}
