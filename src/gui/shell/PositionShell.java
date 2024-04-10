package gui.shell;

import gui.Tooltip;
import gui.Vocab;
import gui.views.fieldTree.*;
import gui.widgets.DirectionCombo;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;
import lui.base.event.listener.LSelectionListener;
import lui.container.LContainer;
import lui.container.LPanel;
import lui.container.LFlexPanel;
import lui.container.LScrollPanel;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.dialog.LObjectWindow;
import lui.dialog.LWindow;
import lui.base.event.LControlEvent;
import lui.base.event.LSelectionEvent;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LTree;
import data.field.Field;
import data.field.FieldNode;
import data.subcontent.Position;
import project.Project;

public class PositionShell extends LObjectWindow<Position> {
	
	private FieldSelector tree;
	private FieldCanvas canvas;
	private LCombo cmbDirection;
	private LSpinner spnX;
	private LSpinner spnY;
	private LSpinner spnH;
	private LScrollPanel scrolledComposite;
	private LLabel lblPos;
	
	private LPath path = null;

	public PositionShell(LWindow parent) {
		super(parent, Vocab.instance.POSITIONSHELL);
		setMinimumSize(640, 480);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		content.setGridLayout(1);

		LFlexPanel sashForm = new LFlexPanel(content, true);
		sashForm.getCellData().setExpand(true, true);
		
		tree = new FieldSelector(sashForm);
		tree.setDragEnabled(false);
		tree.setDataCollection(Project.current.fieldTree.getData());
		tree.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				setField(event.id, (FieldNode) event.data);
			}
		});
		
		scrolledComposite = new LScrollPanel(sashForm);

		canvas = new FieldCanvasOpenGL(scrolledComposite) {
			public void onTileLeftDown() {
				spnX.setValue(tileX + 1);
				spnY.setValue(tileY + 1);
				updateClickPoint();
				canvas.redraw();
			}
			public void onTileRightDown() {}
			public void onTileEnter(int x, int y) {
				lblPos.setText("(" + (x + 1) + "," + (y + 1) + ")");
			}
		};

		LPanel bottom = new LPanel(content);
		bottom.setGridLayout(4);
		bottom.getCellData().setAlignment(LFlags.CENTER);
		
		LPanel coordinates = new LPanel(bottom);
		coordinates.setGridLayout(6);
		coordinates.getCellData().setAlignment(LFlags.CENTER);
		coordinates.getCellData().setExpand(true, false);
		
		LControlListener<Integer> redraw = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				updateClickPoint();
				canvas.redraw();
			}
		};
		
		new LLabel(coordinates, Vocab.instance.POSITIONX, Tooltip.instance.POSITIONX);
		spnX = new LSpinner(coordinates);
		spnX.setMinimum(1);
		spnX.setValue(1);
		spnX.addModifyListener(redraw);
		
		new LLabel(coordinates, Vocab.instance.POSITIONY, Tooltip.instance.POSITIONY);
		spnY = new LSpinner(coordinates);
		spnY.setMinimum(1);
		spnY.setValue(1);
		spnY.addModifyListener(redraw);
		
		new LLabel(coordinates, Vocab.instance.POSITIONH, Tooltip.instance.POSITIONH);
		spnH = new LSpinner(coordinates);
		spnH.setMinimum(1);
		spnH.setValue(1);
		spnH.addModifyListener(redraw);
		
		new LLabel(bottom, LFlags.RIGHT, Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR);
		cmbDirection = new DirectionCombo(bottom);
		
		lblPos = new LLabel(bottom, "(-99, -99)");
		
		sashForm.setWeights(1, 3);
		
		pack();
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
		if (path == null) {
			LPath path = findPath(initial.fieldID);
			tree.forceSelection(path);
		} else {
			tree.forceSelection(path);
		}
		spnX.setValue(initial.x);
		spnY.setValue(initial.y);
		spnH.setValue(initial.h);
		if (initial.direction == -1) {
			cmbDirection.setValue(-1);
		} else {
			cmbDirection.setValue(initial.direction / 45);
		}
		updateClickPoint();
		pack();
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
	
}
