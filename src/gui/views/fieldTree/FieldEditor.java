package gui.views.fieldTree;

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

	public FieldCanvas canvas;
	public FieldToolBar toolBar;
	private LScrollPanel scroll;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public FieldEditor(LContainer parent) {
		super(parent, true);
	}

	protected void createContent(int style) {
		setGridLayout(1);
		setSpacing(0);
		
		toolBar = new FieldToolBar(this);
		toolBar.getCellData().setAlignment(LFlags.LEFT);
		toolBar.getCellData().setExpand(true, false);
		toolBar.getCellData().setRequiredSize(440, 0);
		addChild(toolBar);

		scroll = new LScrollPanel(this);
		scroll.getCellData().setExpand(true, true);

		canvas = new FieldCanvasOpenGL(scroll);
		addChild(canvas);

		LPanel bottom = new LPanel(this);
		bottom.setGridLayout(2);
		bottom.getCellData().setSpread(1, 1);
		bottom.getCellData().setExpand(true, false);

		LPanel scale = new LPanel(bottom);
		scale.setFillLayout(true);
		scale.setSpacing(LPrefs.GRIDSPACING);
		scale.getCellData().setRequiredSize(100, 0);
		scale.getCellData().setAlignment(LFlags.LEFT | LFlags.MIDDLE);

		LActionButton btn50 = new LActionButton(scale, "1:2");
		btn50.addModifyListener(event -> canvas.rescale(0.5f));

		LActionButton btn100 = new LActionButton(scale, "1:1");
		btn100.addModifyListener(event -> canvas.rescale(1));

		LActionButton btn200 = new LActionButton(scale, "2:1");
		btn200.addModifyListener(event -> canvas.rescale(2));

		LLabel tileCoord = new LLabel(bottom, "(-99, -99, -99)");
		tileCoord.getCellData().setExpand(true, false);
		tileCoord.getCellData().setAlignment(LFlags.RIGHT | LFlags.MIDDLE);

		toolBar.onSelectTool = canvas::setTool;
		toolBar.onShowGrid = canvas::setShowGrid;
		canvas.onTileEnter = t -> {
			tileCoord.setText("(" + (t.dx + 1) + "," + (t.dy + 1) + "," + (t.height + 1) + ")");
			bottom.refreshLayout();
		};

	}

	public void selectLayer(Layer layer) {
		canvas.setCurrentLayer(layer);
	}

	public void selectField(Field field) {
		canvas.setField(field);
		toolBar.setField(field);
		scroll.setContentSize(canvas.getTargetSize());
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
