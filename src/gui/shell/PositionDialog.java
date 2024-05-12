package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import gui.views.fieldTree.*;
import gui.widgets.DirectionCombo;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LTree;
import data.field.Field;
import data.field.FieldNode;
import data.subcontent.Position;
import project.Project;

public class PositionDialog extends LObjectDialog<Position> {
	
	private FieldSelector tree;
	private FieldCanvas canvas;
	private LCombo cmbDirection;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnH;
	private LScrollPanel scrolledComposite;
	private LLabel lblPos;

	private final int fieldId;

	public PositionDialog(LWindow parent, int fieldId) {
		super(parent, 640, 480, fieldId, Vocab.instance.POSITIONSHELL);
		this.fieldId = fieldId;
	}
	
	@Override
	protected void createContent(int id) {
		super.createContent(0);
		content.setGridLayout(1);

		LFlexPanel sashForm = new LFlexPanel(content, true);
		sashForm.getCellData().setExpand(true, true);
		
		tree = new FieldSelector(sashForm);
		tree.setDragEnabled(false);
		tree.setDataCollection(Project.current.fieldTree.getData());
		tree.addSelectionListener(event -> setField(event.id, (FieldNode) event.data));
		
		scrolledComposite = new LScrollPanel(sashForm);

		canvas = new PositionCanvas(scrolledComposite);

		LControlListener<Integer> redraw = event -> {
            updateClickPoint();
            canvas.redraw();
        };

		LPanel bottom = new LPanel(content);
		bottom.setGridLayout(9);
		bottom.getCellData().setExpand(true, false);
		
		new LLabel(bottom, Vocab.instance.POSITIONX, Tooltip.instance.POSITIONX);
		spnX = new LSpinner(bottom);
		spnX.getCellData().setTargetSize(80, -1);
		spnX.setMinimum(1);
		spnX.setValue(1);
		spnX.addModifyListener(redraw);
		
		new LLabel(bottom, Vocab.instance.POSITIONY, Tooltip.instance.POSITIONY);
		spnY = new LSpinner(bottom);
		spnY.getCellData().setTargetSize(80, -1);
		spnY.setMinimum(1);
		spnY.setValue(1);
		spnY.addModifyListener(redraw);
		
		new LLabel(bottom, Vocab.instance.POSITIONH, Tooltip.instance.POSITIONH);
		spnH = new LSpinner(bottom);
		spnH.getCellData().setTargetSize(80, -1);
		spnH.setMinimum(1);
		spnH.setValue(1);
		spnH.addModifyListener(redraw);
		
		new LLabel(bottom, Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR);
		cmbDirection = new DirectionCombo(bottom);
		cmbDirection.getCellData().setTargetSize(80, -1);
		
		sashForm.setWeights(1, 3);

		lblPos = new LLabel(bottom, "(-99, -99, -99)");
		lblPos.getCellData().setExpand(true, false);
		lblPos.getCellData().setAlignment(LFlags.RIGHT);

		canvas.onTileEnter = t -> {
			lblPos.setText("(" + (t.dx + 1) + "," + (t.dy + 1) + "," + (t.height + 1) + ")");
			bottom.refreshLayout();
		};

	}
	
	private void setField(int id, FieldNode node) {
		if (canvas.field != null && canvas.field.id == id)
			return;
		Field field = Project.current.fieldTree.loadField(node);
		spnH.setMaximum(field.prefs.maxHeight);
		spnX.setMaximum(field.sizeX);
		spnY.setMaximum(field.sizeY);
		canvas.setField(field);
		updateClickPoint();
		scrolledComposite.setContentSize(canvas.getCurrentSize());
	}
	
	private void updateClickPoint() {
		int x = spnX.getValue() - 1;
		int y = spnY.getValue() - 1;
		int h = spnH.getValue() - 1;
		canvas.setClickedTile(x, y, h);
	}
	
	public void open(Position initial) {
		super.open(initial);
		if (initial == null) {
			if (fieldId == -1) {
				tree.forceSelection(null);
			} else {
				var node = tree.getDataCollection().findNode(fieldId);
				var path = node == null ? null : node.toPath();
				tree.forceSelection(path);
			}
		} else {
			LPath path = findPath(initial.fieldID);
			tree.forceSelection(path);
			spnX.setValue(initial.x);
			spnY.setValue(initial.y);
			spnH.setValue(initial.h);
			if (initial.direction == -1) {
				cmbDirection.setValue(-1);
			} else {
				cmbDirection.setValue(initial.direction / 45);
			}
		}
		updateClickPoint();
	}
	
	private LPath findPath(int id) {
		LDataTree<FieldNode> node = Project.current.fieldTree.getData().findNode(id);
		return node == null ? null : node.toPath();
	}

	@Override
	protected Position createResult(Position initial) {
		Position pos = new Position();
		pos.x = spnX.getValue();
		pos.y = spnY.getValue();
		pos.h = spnH.getValue();
		pos.fieldID = canvas.field.id;
		return pos;
	}
	
	private static class FieldSelector extends LTree<FieldNode, Field> {
		public FieldSelector(LContainer parent) {
			super(parent);
		}
		@Override
		protected LDataTree<FieldNode> emptyNode() {
			return null;
		}
		@Override
		protected LDataTree<FieldNode> duplicateNode(LDataTree<FieldNode> node) {
			return null;
		}
		@Override
		public LDataTree<FieldNode> toNode(LPath path) {
			return Project.current.fieldTree.getData().getNode(path);
		}
		@Override
		public FieldNode toObject(LPath path) {
			return toNode(path).data;
		}
		@Override
		protected String encodeNode(LDataTree<FieldNode> node) {
			return null;
		}
		@Override
		protected LDataTree<FieldNode> decodeNode(String node) {
			return null;
		}
		@Override
		public boolean canDecode(String str) {
			return false;
		}
	}

	private class PositionCanvas extends FieldCanvasOpenGL {

		public PositionCanvas(LScrollPanel parent) {
			super(parent);
		}

		@Override
		public void onTileClick() {
			spnX.setValue(currentTile.dx + 1);
			spnY.setValue(currentTile.dy + 1);
			updateClickPoint();
			canvas.redraw();
		}

	}
}
