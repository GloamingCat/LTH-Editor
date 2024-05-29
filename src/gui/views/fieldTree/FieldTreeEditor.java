package gui.views.fieldTree;

import gui.helper.FieldHelper;
import gui.helper.TilePainter;
import gui.shell.field.FieldPrefDialog;
import lui.base.event.listener.LCollectionListener;
import lui.dialog.LObjectDialog;
import project.Project;

import data.field.Field;
import data.field.Field.Prefs;
import data.field.FieldNode;
import gson.GGlobals;
import lui.container.LContainer;
import lui.container.LFlexPanel;
import lui.container.LView;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.editor.LTreeEditor;
import lui.base.event.LEditEvent;
import lui.base.event.LInsertEvent;
import lui.widget.LTree;

public class FieldTreeEditor extends LView {

	protected static class FieldTree extends LTreeEditor<FieldNode, Prefs> {
		
		private FieldTree(LContainer parent) {
			super(parent);
		}

		@Override
		public LDataTree<FieldNode> getDataCollection() {
			return Project.current.fieldTree.getData();
		}

		@Override
		public FieldNode createNewElement() {
			return Project.current.fieldTree.newNode();
		}

		@Override
		public FieldNode duplicateElement(FieldNode original) {
			return Project.current.fieldTree.duplicateNode(original);
		}
		
		@Override
		protected String encodeElement(FieldNode data) {
			Field field = Project.current.fieldTree.loadField(data);
			return GGlobals.gson.toJson(field);
		}

		@Override
		protected FieldNode decodeElement(String str) {
			Field field = GGlobals.gson.fromJson(str, Field.class);
			return Project.current.fieldTree.addField(field);
		}

		@Override
		public boolean canDecode(String str) {
			return true;
		}
		
		@Override
		protected Prefs getEditableData(LPath path) {
			return Project.current.fieldTree.loadField(path).prefs;
		}

		@Override
		protected void setEditableData(LPath path, Prefs newData) {
			Project.current.fieldTree.loadField(path).prefs = newData;
		}

		@Override
		protected void setChecked(FieldNode node, boolean checked) {}

		@Override
		public void forceFirstSelection() {
			if (getDataCollection() != null) {
				LDataTree<FieldNode> tree = getDataCollection().toTree();
				getCollectionWidget().setItems(tree);
				int lastField = Project.current.fieldTree.getData().lastField;
				LDataTree<FieldNode> lastNode = Project.current.fieldTree.getData().findNode(lastField);
				getCollectionWidget().forceSelection(lastNode == null ? null : lastNode.toPath());
			} else {
				getCollectionWidget().setItems(null);
				getCollectionWidget().forceSelection(null);
			}
		}

	}
	
	public LTree<FieldNode, Field.Prefs> fieldTree;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public FieldTreeEditor(LContainer parent) {
		super(parent, true);
		setFillLayout(true);
		
		createMenuInterface();
		
		LFlexPanel sashForm = new LFlexPanel(this, true);
		
		LTreeEditor<FieldNode, Field.Prefs> treeEditor = new FieldTree(sashForm);
		fieldTree = treeEditor.getCollectionWidget();
		fieldTree.setInsertNewEnabled(true);
		fieldTree.setEditEnabled(true);
		fieldTree.setDuplicateEnabled(true);
		fieldTree.setDragEnabled(true);
		fieldTree.setDeleteEnabled(true);
		fieldTree.setCopyEnabled(true);
		fieldTree.setPasteEnabled(true);
		treeEditor.setShellFactory(new LWindowFactory<>() {
			@Override
			public LObjectDialog<Prefs> createWindow(LWindow parent) {
				FieldNode n = fieldTree.getSelectedObject();
				return new FieldPrefDialog(parent, n);
			}
		});
		fieldTree.setIncludeID(true);
		addChild(treeEditor);

		LFlexPanel sashForm2 = new LFlexPanel(sashForm, true);

		FieldEditor fieldEditor = new FieldEditor(sashForm2);
		treeEditor.addChild(fieldEditor);
		
		FieldSideEditor sideEditor = new FieldSideEditor(sashForm2);
		fieldEditor.addChild(sideEditor);

		fieldTree.addSelectionListener(event -> {
            Project.current.fieldTree.getData().lastField = event.id;
            System.gc();
        });
		fieldTree.addEditListener(new LCollectionListener<>() {
			public void onEdit(LEditEvent<Prefs> e) {
				LDataTree<FieldNode> node = Project.current.fieldTree.getData().getNode(e.path);
				node.data.name = e.newData.name;
				fieldTree.refreshObject(e.path);
				fieldEditor.canvas.repaint();
			}
		});
		fieldTree.addInsertListener(new LCollectionListener<>() {
			@Override
			public void onInsert(LInsertEvent<FieldNode> event) {
				Project.current.fieldTree.getData().lastField = event.node.id;
				fieldTree.refreshAll(event.node);
			}
		});

		fieldEditor.canvas.onMoveCharacter = sideEditor::onMoveCharacter;
		fieldEditor.canvas.onSelectArea = selection -> {
			if (selection.length == 1 && selection[0].length == 1)
				sideEditor.selectTile(selection[0][0]);
			else
				sideEditor.unselectTiles();
		};

		fieldEditor.toolBar.onSelectEditor = sideEditor::selectEditor;
		fieldEditor.toolBar.onResize = newSize -> {
			fieldTree.refreshObject(fieldTree.getSelectedPath());
			sideEditor.setObject(fieldEditor.getObject());
			fieldEditor.canvas.refreshBuffer(true);
		};

		sideEditor.charEditor.onChangeX = event -> {
            fieldEditor.canvas.onTileChange(event.x(), event.y());
            fieldEditor.canvas.onTileChange(event.newValue(), event.y());
            fieldEditor.canvas.refreshBuffer(false);
        };
		sideEditor.charEditor.onChangeY = event -> {
            fieldEditor.canvas.onTileChange(event.x(), event.y());
            fieldEditor.canvas.onTileChange(event.x(), event.newValue());
            fieldEditor.canvas.refreshBuffer(false);
        };
		sideEditor.charEditor.onChangeH = event -> {
            fieldEditor.canvas.setHeight(event.newValue());
            fieldEditor.canvas.onTileChange(event.x(), event.y());
            fieldEditor.canvas.refreshBuffer(false);
        };
		sideEditor.charEditor.onChangeSprite = event -> {
			fieldEditor.canvas.onTileChange(event.x(), event.y());
            fieldEditor.canvas.refreshBuffer(false);
		};
		sideEditor.partyEditor.onChange = p -> fieldEditor.canvas.repaint();
		sideEditor.onSelectChar = fieldEditor.canvas::setCharacter;
		sideEditor.onSelectTile = fieldEditor.canvas::setSelection;
		sideEditor.onSelectParty = fieldEditor.canvas::setParty;
		sideEditor.onNewChar = sideEditor.onDeleteChar = tile -> {
			fieldEditor.canvas.onTileChange(tile.x - 1, tile.y - 1);
			fieldEditor.canvas.refreshBuffer(false);
		};
		sideEditor.onLayerEdit = () -> fieldEditor.canvas.refreshBuffer(true);
		sideEditor.onSelectLayer = fieldEditor::selectLayer;
		sideEditor.onSelectEditor = i -> {
			if (i == FieldSideEditor.CHAR) {
				fieldEditor.canvas.setMode(FieldCanvas.CHAR);
			} else if (i == FieldSideEditor.PARTY) {
				fieldEditor.canvas.setMode(FieldCanvas.PARTY);
			} else {
				fieldEditor.canvas.setMode(FieldCanvas.TILE);
			}
		};

		sashForm.setWeights(1, 5);
		sashForm2.setWeights(2, 1);
	}
	
	public void onVisible() {
		TilePainter.reload();
		FieldHelper.reloadMath();
		super.onVisible();
	}

}
