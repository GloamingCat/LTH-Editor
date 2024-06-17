package gui.views.fieldTree;

import data.field.Field;
import data.field.FieldNode;
import data.subcontent.Position;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.DirectionCombo;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.action.LCompoundAction;
import lui.base.action.LControlAction;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.base.event.LControlEvent;
import lui.container.LContainer;
import lui.container.LFlexPanel;
import lui.container.LPanel;
import lui.container.LScrollPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;
import project.Project;

import java.lang.reflect.Type;

public class PositionEditor extends GDefaultObjectEditor<Position> {

    private LNodeSelector<FieldNode> tree;
	private FieldCanvas canvas;
    private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnH;
	private LScrollPanel scrolledComposite;
	private LLabel lblPos;

	private final int fieldId;

    public PositionEditor(LContainer parent, int fieldId, boolean doubleBuffered) {
        super(parent, fieldId, doubleBuffered);
        this.fieldId = fieldId;
    }

    @Override
    protected void createContent(int style) {
        getCellData().setTargetSize(900, 600);
        setGridLayout(1);

		LFlexPanel sashForm = new LFlexPanel(this, true);
		sashForm.getCellData().setExpand(true, true);

		tree = new LNodeSelector<>(sashForm, LNodeSelector.INCLUDEID);
		tree.addModifyListener(e -> setField(e.newValue));
		addControl(tree, "fieldID");

		scrolledComposite = new LScrollPanel(sashForm);

		canvas = new PositionCanvas(scrolledComposite);
		addChild(canvas);

        sashForm.setWeights(1, 3);

		LPanel bottom = new LPanel(this);
		bottom.setGridLayout(9);
		bottom.getCellData().setExpand(true, false);

		new LLabel(bottom, Vocab.instance.POSITIONX, Tooltip.instance.POSITIONX);
		spnX = new LSpinner(bottom);
		spnX.getCellData().setTargetSize(80, -1);
		spnX.setMinimum(1);
		spnX.setValue(1);
		spnX.addModifyListener(e -> {
			int y = spnY.getValue() - 1;
			int h = spnH.getValue() - 1;
			canvas.setClickedTile(e.newValue - 1, y, h);
			canvas.repaint();
		});
		addControl(spnX, "x");

		new LLabel(bottom, Vocab.instance.POSITIONY, Tooltip.instance.POSITIONY);
		spnY = new LSpinner(bottom);
		spnY.getCellData().setTargetSize(80, -1);
		spnY.setMinimum(1);
		spnY.setValue(1);
		spnY.addModifyListener(e -> {
			int x = spnX.getValue() - 1;
			int h = spnH.getValue() - 1;
			canvas.setClickedTile(x, e.newValue - 1, h);
			canvas.repaint();
		});
		addControl(spnY, "y");

		new LLabel(bottom, Vocab.instance.POSITIONH, Tooltip.instance.POSITIONH);
		spnH = new LSpinner(bottom);
		spnH.getCellData().setTargetSize(80, -1);
		spnH.setMinimum(1);
		spnH.setValue(1);
		spnH.addModifyListener(e -> {
			int x = spnX.getValue() - 1;
			int y = spnY.getValue() - 1;
			canvas.setHeight(e.newValue - 1);
			canvas.setClickedTile(x, y, e.newValue - 1);
			canvas.repaint();
		});
		addControl(spnH, "h");

		new LLabel(bottom, Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR);
        DirectionCombo cmbDirection = new DirectionCombo(bottom, DirectionCombo.OPTIONAL | DirectionCombo.READONLY, true);
		cmbDirection.getCellData().setTargetSize(80, -1);
		addControl(cmbDirection, "direction");

		lblPos = new LLabel(bottom, "(-99, -99, -99)");
		lblPos.getCellData().setExpand(true, false);
		lblPos.getCellData().setAlignment(LFlags.RIGHT);
		lblPos.getCellData().setTargetSize(LPrefs.LABELWIDTH, LPrefs.WIDGETHEIGHT);
		lblPos.setAlignment(LFlags.RIGHT);

		canvas.onTileEnter = t -> {
			lblPos.setText("(" + (t.dx + 1) + "," + (t.dy + 1) + "," + (t.height + 1) + ")");
			bottom.refreshLayout();
		};
    }

    @Override
    public void setObject(Object value) {
		super.setObject(value);
		if (value == null) {
			setField(fieldId);
		} else {
			Position p = (Position) value;
			setField(p.fieldID);
			updateClickPoint();
		}
    }

	@Override
	public void onVisible() {
		tree.setCollection(Project.current.fieldTree.getData());
		super.onVisible();
	}

    private LPath findPath(int id) {
		LDataTree<FieldNode> node = Project.current.fieldTree.getData().findNode(id);
		return node == null ? null : node.toPath();
	}

    private void updateClickPoint() {
		int x = spnX.getValue() - 1;
		int y = spnY.getValue() - 1;
		int h = spnH.getValue() - 1;
		canvas.setClickedTile(x, y, h);
	}

	private void setField(int id) {
		if (canvas.field != null && canvas.field.id == id)
			return;
		Field field = Project.current.fieldTree.loadField(id);
		if (field == null) {
			canvas.setField(null);
			return;
		}
		spnH.setMaximum(field.prefs.maxHeight);
		spnX.setMaximum(field.sizeX);
		spnY.setMaximum(field.sizeY);
		canvas.setField(field);
		canvas.setMode(FieldCanvas.CHAR);
		if (currentObject != null)
			updateClickPoint();
		scrolledComposite.setContentSize(canvas.getTargetSize());
	}

	@Override
	public void refresh() {
		Position object = currentObject;
		currentObject = null;
		setObject(object);
	}

    @Override
    public Type getType() {
        return Position.class;
    }

	private class PositionCanvas extends FieldCanvasOpenGL {

		public PositionCanvas(LScrollPanel parent) {
			super(parent);
		}

		@Override
		public void onTileClick() {
			LControlEvent<Integer> e1 = new LControlEvent<>(spnX.getValue(), currentTile.dx + 1);
			LControlEvent<Integer> e2 = new LControlEvent<>(spnY.getValue(), currentTile.dy + 1);
			if (e1.newValue.equals(e1.oldValue) && e2.newValue.equals(e2.oldValue))
				return;
			LControlAction<Integer> a1 = new LControlAction<>(spnX, e1);
			a1.apply();
			LControlAction<Integer> a2 = new LControlAction<>(spnY, e2);
			a2.apply();
			LCompoundAction action = new LCompoundAction(a1, a2);
			if (getActionStack() != null)
				getActionStack().newAction(action);
			updateClickPoint();
			canvas.repaint();
		}

	}

}
