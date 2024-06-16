package gui.shell.database;

import data.subcontent.Property;
import data.subcontent.Tag;
import gson.GGlobals;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.PropertyDialog;
import gui.views.fieldTree.PositionEditor;
import gui.widgets.SimpleEditableList;
import lui.base.LPrefs;
import lui.base.data.LDataList;
import lui.base.data.LDataTree;
import lui.container.LFrame;
import lui.container.LView;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.editor.LObjectEditor;
import lui.gson.GObjectDialog;
import lui.widget.*;
import project.Project;

import java.util.TreeMap;

public class EventArgsDialog extends GObjectDialog<LDataList<Tag>> {

    public static final int SKIP = 4096;
    public static final int KEY = 1;
    public static final int FIELD = 2;
    public static final int WINDOW = 4;
    public static final int DIR = 8;
    public static final int TILE = 16;
    public static final int NAME = 32;
    public static final int FADE = 64;
    public static final int POS = 128;
    public static final int NAMEPOS = 256;
    public static final int SIZE = 512;
    public static final int WAIT = 1024;
    public static final int DEACTIVATE = 2048;
    public static final int OPTIONAL = 8192;
    public static final int LIMIT = 32768;
    public static final int MENU = 16384;
    public static final int ITEM = 65536;
    public static final int INPUT = 131072;
    public static final int ALIGN = 262144;
    public static final int EXP = 524288;
    public static final int EQUIP = 524288;
    public static final int FORMATION = 1048576;

    TreeMap<String, LControlWidget<?>> controls;
    TreeMap<String, LObjectEditor<?>> editors;

    public EventArgsDialog(LWindow parent, int style, String title) {
        super(parent, style, title);
    }

	@Override
	protected void createContent(int style) {
        super.createContent(style);
        contentEditor.setGridLayout(3);
        controls = new TreeMap<>();
        editors = new TreeMap<>();

        if (style == WAIT) {
            addSpinner(Vocab.instance.DURATION, Tooltip.instance.WAITTIME, "time");
        } else if (style == NAME) {
            addTextField(Vocab.instance.NAME, Tooltip.instance.LABEL, "name");
            LSpinner spn = addSpinner(Vocab.instance.EVENTID, Tooltip.instance.EVENTID, "index");
            spn.setMinimum(-1);
            spn.setValue(-1);
        } else if ((style & SKIP) > 0) {
            if ((style & NAME) > 0)
                addTextField(Vocab.instance.NAME, Tooltip.instance.LABEL, "name");
            else if ((style & LIMIT) > 0)
                addSpinner(Vocab.instance.COUNT, Tooltip.instance.EVENTSKIP, "events");
            else
                addSpinner(Vocab.instance.EVENTID, Tooltip.instance.EVENTID, "index");
        } else if ((style & KEY) > 0) {
            addTextField(Vocab.instance.KEY, Tooltip.instance.KEY, "key");
            if ((style & NAME) > 0 && (style & WINDOW) == 0)
                addTextField(Vocab.instance.NAME, Tooltip.instance.CHARANIM, "name");
        }
        if ((style & MENU) > 0) {
            if ((style & FIELD) > 0) {
                addCombo(Vocab.instance.TYPE, Tooltip.instance.KEY, "menu",
                    new String[] { Vocab.instance.FIELDMENU, Vocab.instance.SAVEMENU }, LCombo.READONLY );
            } else if((style & ITEM) > 0) {
                var list = addList(Vocab.instance.ITEMS, Tooltip.instance.INVENTORY, "items", false, new LWindowFactory<>() {
                    @Override
                    public LObjectDialog<Property> createWindow(LWindow parent) {
                        return new PropertyDialog(parent, Vocab.instance.ITEMSHELL) {
                            public LDataTree<Object> getTree() {
                                return Project.current.items.getTree();
                            }
                        };
                    }
                }, Property.class);
                addCheckBox(Vocab.instance.SELLABLE, Tooltip.instance.SELLABLE, "sell");
            } else {
                addList(Vocab.instance.BATTLERS, Tooltip.instance.UNITS, "items", false, new LWindowFactory<>() {
                    @Override
                    public LObjectDialog<Property> createWindow(LWindow parent) {
                        return new PropertyDialog(parent, Vocab.instance.BATTLERSHELL) {
                            public LDataTree<Object> getTree() {
                                return Project.current.battlers.getTree();
                            }
                        };
                    }
                }, Property.class);
                addCheckBox(Vocab.instance.RECRUIT, Tooltip.instance.RECRUIT, "sell");
            }
        } else if ((style & FIELD) > 0) {
            if ((style & TILE) > 0) {
                // Transition
                LFrame grpDestination = new LFrame(contentEditor, Vocab.instance.DESTINATION);
                grpDestination.setHoverText(Tooltip.instance.DESTINATION);
                grpDestination.setFillLayout(true);
                grpDestination.getCellData().setSpread(3, 1);
                grpDestination.getCellData().setExpand(true, true);
                PositionEditor position = new PositionEditor(grpDestination, -1, false);
                position.onVisible();
                position.setObject(Project.current.config.getData().player.startPos.clone());
                editors.put("", position);
                contentEditor.addChild((LView) position);
            } else {
                // Battle
                addNodeSelector(Vocab.instance.BATTLEFIELD, Tooltip.instance.BATTLEFIELD, "fieldID",
                        Project.current.fieldTree.getData().toTree(), LNodeSelector.OPTIONAL);
                addCheckBox(Vocab.instance.SKIPINTRO, Tooltip.instance.SKIPINTRO, "skipIntro");
                addCheckBox(Vocab.instance.DISABLEESCAPE, Tooltip.instance.DISABLEESCAPE, "skipIntro");
                addCombo(Vocab.instance.GAMEOVERCONDITION, Tooltip.instance.GAMEOVERCONDITION, "gameOverCondition",
                       new String[] { Vocab.instance.NONE, Vocab.instance.LOSE, Vocab.instance.NOWIN }, LCombo.READONLY );
            }
            addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade");
        } else if ((style & TILE) > 0) {
            addSpinner(Vocab.instance.OFFSETX, Tooltip.instance.POSITIONX, "x");
            addSpinner(Vocab.instance.OFFSETY, Tooltip.instance.POSITIONY, "y");
            addSpinner(Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH, "h");
            addTextField(Vocab.instance.TILEREF, Tooltip.instance.TILEREF, "other");
            if ((style & LIMIT) > 0) {
                LSpinner limit = addSpinner(Vocab.instance.PATHLIMIT, Tooltip.instance.PATHLIMIT, "limit");
                limit.setMinimum(-1);
                limit.setValue(-1);
            }
        } else if ((style & DIR) > 0) {
            addSpinner(Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR, "angle");
            if ((style & LIMIT) > 0) {
                addSpinner(Vocab.instance.DISTANCE, Tooltip.instance.DISTANCE, "distance");
            }
        } else if ((style & DEACTIVATE) > 0) {
            addCheckBox(Vocab.instance.VISIBLE, Tooltip.instance.CHARVISIBLE, "visible");
            addCheckBox(Vocab.instance.PASSABLE, Tooltip.instance.CHARPASSABLE, "passable");
            addCheckBox(Vocab.instance.DEACTIVATE, Tooltip.instance.CHARDEACTIVATE, "deactivate");
            addCheckBox(Vocab.instance.PERSISTENT, Tooltip.instance.CHARPERSISTENT, "permanent");
            addCheckBox(Vocab.instance.OPTIONAL, Tooltip.instance.CHAROPTIONAL, "optional");
            addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade");
        }
    }

    @Override
    public void open(LDataList<Tag> initial) {
        for (Tag p : initial) {
            if (controls.containsKey(p.key)) {
                LControlWidget<?> cw = controls.get(p.key);
                if (cw instanceof LText) {
                    cw.setValue(p.value);
                } else if (cw instanceof LCheckBox) {
                    cw.setValue(Boolean.parseBoolean(p.value));
                } else {
                    cw.setValue(Integer.parseInt(p.value));
                }
            } else if (editors.containsKey(p.key)) {
                LObjectEditor<?> oe = editors.get(p.key);
                Object value = oe.getFieldValue(p.key);
                oe.setFieldValue(p.key, GGlobals.gson.fromJson(p.value, value.getClass()));
            }
        }
        if (editors.containsKey("")) {
            LObjectEditor<?> oe = editors.get("");
            for (Tag p : initial) {
                Object value = oe.getFieldValue(p.key);
                if (value != null) {
                    value =  GGlobals.gson.fromJson(p.value, value.getClass());
                    oe.setFieldValue(p.key,value);
                }
            }
        }
        for (var editor : editors.values()) {
            Object obj = editor.getObject();
            editor.setObject(null);
            editor.setObject(obj);
        }
        super.open(initial);
    }

    @Override
    public LDataList<Tag> createResult(LDataList<Tag> initial) {
        LDataList<Tag> params = new LDataList<>();
        for (var entry : controls.entrySet()) {
            Tag p = new Tag(entry.getKey(), entry.getValue().getValue().toString());
            params.add(p);
        }
        for (var entry : editors.entrySet()) {
            LObjectEditor<?> oe = entry.getValue();
            oe.saveObjectValues();
            if (entry.getKey().isEmpty()) {
                for (var field : oe.getFieldValues().entrySet()) {
                    Tag p = new Tag(field.getKey(), field.getValue().toString());
                    params.add(p);
                }
            } else {
                Tag p = new Tag(entry.getKey(), oe.getObject().toString());
                params.add(p);
            }
        }
        return params;
    }

    private LText addTextField(String label, String tooltip, String key) {
        new LLabel(contentEditor, label, tooltip);
        LText txt = new LText(contentEditor);
        txt.setValue("");
        txt.getCellData().setExpand(true, false);
        txt.getCellData().setSpread(2, 1);
        txt.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.WIDGETHEIGHT);
        controls.put(key, txt);
        return txt;
    }

    private LSpinner addSpinner(String label, String tooltip, String key) {
        new LLabel(contentEditor, label, tooltip);
        LSpinner spn = new LSpinner(contentEditor);
        spn.setValue(0);
        spn.getCellData().setExpand(true, false);
        spn.getCellData().setSpread(2, 1);
        spn.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.WIDGETHEIGHT);
        controls.put(key, spn);
        return spn;
    }

    private LCheckBox addCheckBox(String label, String tooltip, String key) {
        new LLabel(contentEditor, label, tooltip);
        LCheckBox btn = new LCheckBox(contentEditor);
        btn.setValue(false);
        btn.getCellData().setExpand(true, false);
        btn.getCellData().setSpread(2, 1);
        controls.put(key, btn);
        return btn;
    }

    private LCombo addCombo(String label, String tooltip, String key, String[] items, int opt) {
        new LLabel(contentEditor, label, tooltip);
        LCombo cmb = new LCombo(contentEditor, opt);
        cmb.setItems(items);
        cmb.setValue(0);
        cmb.getCellData().setExpand(true, false);
        cmb.getCellData().setSpread(2, 1);
        cmb.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.WIDGETHEIGHT);
        controls.put(key, cmb);
        return cmb;
    }

    private <T> LNodeSelector<T> addNodeSelector(String label, String tooltip, String key, LDataTree<T> tree, int opt) {
        new LLabel(contentEditor, label, tooltip);
        LNodeSelector<T> ns = new LNodeSelector<>(contentEditor, opt);
        ns.setCollection(tree);
        ns.setValue(0);
        ns.getCellData().setExpand(true, true);
        ns.getCellData().setSpread(2, 1);
        ns.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT * 2);
        controls.put(key, ns);
        return ns;
    }

    private <T> SimpleEditableList<T> addList(String label, String tooltip, String key, boolean includeId, LWindowFactory<T> factory, Class<T> type) {
        new LLabel(contentEditor, label, tooltip);
        SimpleEditableList<T> lst = new SimpleEditableList<>(contentEditor);
        lst.getCollectionWidget().setIncludeID(includeId);
        lst.getCellData().setExpand(true, true);
        lst.getCellData().setSpread(2, 1);
        lst.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT * 2);
        lst.setShellFactory(factory);
        lst.type = type;
        lst.setObject(new LDataList<T>());
        editors.put(key, lst);
        return lst;
    }

}
