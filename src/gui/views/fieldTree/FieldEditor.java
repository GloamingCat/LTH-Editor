package gui.views.fieldTree;

import gui.Tooltip;
import gui.helper.TilePainter;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.container.LScrollPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LActionButton;
import lui.widget.LLabel;

import project.Project;
import data.field.Field;
import data.field.FieldNode;
import data.field.Layer;

public class FieldEditor extends GDefaultObjectEditor<Field> {

	public final FieldCanvas canvas;
	public final FieldToolBar toolBar;

	private final LScrollPanel scrolledComposite;

	private final LLabel lblId;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public FieldEditor(LContainer parent) {
		super(parent, true);
		setGridLayout(2);
		
		toolBar = new FieldToolBar(this);
		toolBar.getCellData().setAlignment(LFlags.CENTER);
		toolBar.getCellData().setExpand(true, false);
		toolBar.getCellData().setMinimumSize(440, 0);
		addChild(toolBar);
		
		lblId = new LLabel(this, "ID: 9999", Tooltip.instance.ID);
		
		scrolledComposite = new LScrollPanel(this);
		scrolledComposite.getCellData().setExpand(true, true);
		scrolledComposite.getCellData().setSpread(2, 1);
		
		canvas = new FieldCanvasOpenGL(scrolledComposite);
		addChild(canvas);

		LPanel bottom = new LPanel(this);
		bottom.setGridLayout(2);
		bottom.getCellData().setSpread(2, 1);
		bottom.getCellData().setExpand(true, false);

		LPanel scale = new LPanel(bottom);
		scale.setFillLayout(true);
		scale.setSpacing(LPrefs.GRIDSPACING);
		scale.getCellData().setMinimumSize(100, 0);
		scale.getCellData().setAlignment(LFlags.LEFT | LFlags.MIDDLE);

		LActionButton btn50 = new LActionButton(scale, "1:2");
		btn50.addModifyListener(event -> {
            canvas.rescale(0.5f);
            scrolledComposite.setContentSize(canvas.getCurrentSize());
        });

		LActionButton btn100 = new LActionButton(scale, "1:1");
		btn100.addModifyListener(event -> {
            canvas.rescale(1);
            scrolledComposite.setContentSize(canvas.getCurrentSize());
        });

		LActionButton btn200 = new LActionButton(scale, "2:1");
		btn200.addModifyListener(event -> {
            canvas.rescale(2);
            scrolledComposite.setContentSize(canvas.getCurrentSize());
        });

		LLabel tileCoord = new LLabel(bottom, "(-99, -99, -99)");
		tileCoord.getCellData().setExpand(true, false);
		tileCoord.getCellData().setAlignment(LFlags.RIGHT | LFlags.MIDDLE);

		toolBar.onSelectTool = canvas::setTool;
		toolBar.onShowGrid = canvas::setShowGrid;
		canvas.lblId = lblId;
		canvas.onTileEnter = t -> {
			tileCoord.setText("(" + (t.dx + 1) + "," + (t.dy + 1) + "," + (t.height + 1) + ")");
			bottom.layout();
		};

	}

	public void selectLayer(Layer layer) {
		canvas.setCurrentLayer(layer);
	}

	public void selectField(Field field) {
		lblId.setText("ID: " + (field == null ? -1 : field.id));
		canvas.setField(field);
		toolBar.setField(field);
		scrolledComposite.setContentSize(canvas.getCurrentSize());
	}

	@Override
	public void setObject(Object obj) {
		if (obj != null) {
			FieldNode node = (FieldNode) obj;
			int id = Project.current.fieldTree.getData().getFieldID(node);
			if (canvas.field == null || canvas.field.id != id) {
				TilePainter.reload();
				Field field = Project.current.fieldTree.loadField(node);
				selectField(field);
				super.setObject(field);
			}
		} else {
			selectField(null);
			super.setObject(null);
		}
	}

	@Override
	public Class<?> getType() {
		return Field.class;
	}

}
