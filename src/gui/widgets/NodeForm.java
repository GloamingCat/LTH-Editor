package gui.widgets;

import data.subcontent.Node;
import gson.GGlobals;
import gui.Vocab;
import lui.base.LPrefs;
import lui.base.data.LDataList;
import lui.base.data.LPath;
import lui.base.gui.LControl;
import lui.collection.LForm;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LStringDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.editor.LObjectEditor;
import lui.gson.GFormEditor;
import lui.widget.LCombo;

import java.lang.reflect.Type;

public class NodeForm extends GFormEditor<Node, String, NodeForm.NodeEditor> {

    protected LDataList<?> comboList;
    protected int controlWidth = LPrefs.BUTTONWIDTH;

    public NodeForm(LContainer parent, boolean useButtons) {
        super(parent, useButtons ? LForm.BUTTONS : LForm.MENU);
        shellFactory = new LWindowFactory<>() {
            @Override
            public LObjectDialog<String> createWindow(LWindow parent) {
                return new LStringDialog(parent, Vocab.instance.NAME);
            }
        };
        getCollectionWidget().setLabelWidth(100);
		getCollectionWidget().setColumns(1);
    }

    @Override
    protected String getEditableData(LPath path) {
        return getDataCollection().get(path.index).name;
    }

    @Override
    protected void setEditableData(LPath path, String name) {
        getDataCollection().get(path.index).name = name;
    }

    @Override
    protected LDataList<Node> getDataCollection() {
        return (LDataList<Node>) currentObject;
    }

    public void setComboList(LDataList<?> list) {
        comboList = list;
        getCollectionWidget().refreshControls();
    }

    public void setControlWidth(int w) {
		controlWidth = w;
	}

    @Override
    protected String getLabelText(int i) {
        return getDataCollection().get(i).name;
    }

    @Override
    protected NodeEditor createControlWidget(LContainer parent) {
        NodeEditor editor = new NodeEditor(parent, comboList);
        editor.getCellData().setRequiredSize(controlWidth, LPrefs.WIDGETHEIGHT);
        editor.getCellData().setTargetSize(controlWidth, LPrefs.WIDGETHEIGHT);
        editor.getCellData().setExpand(true, false);
        editor.setMenuInterface(getMenuInterface()); // Hack
        return editor;
    }

    @Override
    public Type getType() {
        return Node.class;
    }

    @Override
    public void refreshControlWidget(LControl<Node> widget, final int i) {
        NodeEditor c = (NodeEditor) widget;
        c.combo.setItems(comboList);
    }

    public static class NodeEditor extends LObjectEditor<Node> {
        private LCombo combo;

        @SuppressWarnings("DataFlowIssue")
        public NodeEditor(LContainer parent, LDataList<?> comboList) {
            super(parent, false);
            combo.setItems(comboList);
        }

        @Override
        protected void createContent(int flags) {
            setFillLayout(true);
            combo = new LCombo(this);
            addControl(combo, "id");
        }

        @Override
        public String encodeData(Node value) {
            return GGlobals.gson.toJson(value);
        }

        @Override
        public Node decodeData(String str) {
            return GGlobals.gson.fromJson(str, Node.class);
        }

    }

}
