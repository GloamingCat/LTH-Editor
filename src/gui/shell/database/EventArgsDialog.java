package gui.shell.database;

import data.subcontent.Tag;
import gson.GGlobals;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.PropertyList;
import gui.views.fieldTree.PositionEditor;
import gui.widgets.NameList;
import lui.base.LPrefs;
import lui.base.data.LDataList;
import lui.base.data.LDataTree;
import lui.container.LFrame;
import lui.container.LView;
import lui.dialog.LWindow;
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
    public static final int CLOSE = 8192;
    public static final int LIMIT = 32768;
    public static final int MENU = 16384;
    public static final int ITEM = 65536;
    public static final int INPUT = 131072;
    public static final int CHOICE = 262144;
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
            addSpinner(Vocab.instance.DURATION, Tooltip.instance.WAITTIME, "time", false);
        } else if (style == NAME) {
            addTextField(Vocab.instance.NAME, Tooltip.instance.LABEL, "name");
            LSpinner spn = addSpinner(Vocab.instance.EVENTID, Tooltip.instance.EVENTID, "index", false);
            spn.setMinimum(-1);
            spn.setValue(-1);
        } else if ((style & SKIP) > 0) {
            if ((style & NAME) > 0)
                addTextField(Vocab.instance.NAME, Tooltip.instance.LABEL, "name");
            else if ((style & LIMIT) > 0)
                addSpinner(Vocab.instance.COUNT, Tooltip.instance.EVENTSKIP, "events", false);
            else
                addSpinner(Vocab.instance.EVENTID, Tooltip.instance.EVENTID, "index", false);
        } else if ((style & KEY) > 0) {
            addTextField(Vocab.instance.KEY, Tooltip.instance.KEY, "key");
            if ((style & NAME) > 0 && (style & WINDOW) == 0)
                addTextField(Vocab.instance.NAME, Tooltip.instance.CHARANIM, "name");
        }
        if ((style & WINDOW) > 0) {
            LSpinner spn = addSpinner(Vocab.instance.ID, Tooltip.instance.ID, "id", false);
            spn.setMinimum(1);
            spn.setValue(1);
            if ((style & POS) > 0) {
                if ((style & INPUT) > 0) {
                    if ((style & NAME) > 0) {
                        addSpinner(Vocab.instance.MINLENGTH, Tooltip.instance.LENGTHLIMITS, "min", false);
                        addSpinner(Vocab.instance.MAXLENGTH, Tooltip.instance.LENGTHLIMITS, "max", true);
                        addTextField(Vocab.instance.CANCELVALUE, Tooltip.instance.CANCELVALUE, "cancel");
                    } else if ((style & LIMIT) > 0) {
                        addSpinner(Vocab.instance.DIGITS, Tooltip.instance.DIGITS, "length", false);
                        addSpinner(Vocab.instance.CANCELVALUE, Tooltip.instance.CANCELVALUE, "cancel", true);
                    } else {
                        new LLabel(contentEditor, Vocab.instance.OPTIONS, Tooltip.instance.CHOICES);
                        NameList lst = new NameList(contentEditor, Vocab.instance.TEXT);
                        lst.getCollectionWidget().setIncludeID(true);
                        lst.getCellData().setExpand(true, true);
                        lst.getCellData().setSpread(2, 1);
                        lst.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT * 2);
                        lst.setObject(new LDataList<>());
                        editors.put("choices", lst);
                        addSpinner(Vocab.instance.CANCELVALUE, Tooltip.instance.CANCELVALUE, "cancel", true);
                    }
                } else if ((style & NAME) > 0) {
                    addTextField(Vocab.instance.TEXT, Tooltip.instance.TEXT, "message");
                } else {
                    addTextField(Vocab.instance.TEXT, Tooltip.instance.TEXT, "text");
                }
                addCombo(Vocab.instance.ALIGNX, Tooltip.instance.TEXTALIGN, "alignX",
                        new String[] {Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT}, 0 );
                addCombo(Vocab.instance.ALIGNY, Tooltip.instance.TEXTALIGN, "alignY",
                        new String[] {Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM}, 0 );
                addSpinner(Vocab.instance.WIDTH, Tooltip.instance.SIZEX, "width", true);
                addSpinner(Vocab.instance.HEIGHT, Tooltip.instance.SIZEY, "height", true);
                addSpinner(Vocab.instance.POSITIONX, Tooltip.instance.CENTERX, "x", true);
                addSpinner(Vocab.instance.POSITIONY, Tooltip.instance.CENTERY, "y", true);
                if ((style & NAME) > 0) {
                    addTextField(Vocab.instance.NAME, Tooltip.instance.NAME, "name");
                    addSpinner(Vocab.instance.NAMEX, Tooltip.instance.NAMEPOS, "nameX", true);
                    addSpinner(Vocab.instance.NAMEY, Tooltip.instance.NAMEPOS, "nameY", true);
                } else {
                    addCheckBox(Vocab.instance.WAIT, Tooltip.instance.WAIT, "wait");
                }
            }
        } else if ((style & MENU) > 0) {
            if ((style & FIELD) > 0) {
                addCombo(Vocab.instance.TYPE, Tooltip.instance.KEY, "menu",
                    new String[] { Vocab.instance.FIELDMENU, Vocab.instance.SAVEMENU }, LCombo.READONLY );
            } else if((style & ITEM) > 0) {
                var list = addPropertyList(Vocab.instance.ITEMS, Tooltip.instance.INVENTORY, "items", false, Project.current.items.getTree());
                addCheckBox(Vocab.instance.SELLABLE, Tooltip.instance.SELLABLE, "sell");
            } else {
                addPropertyList(Vocab.instance.BATTLERS, Tooltip.instance.UNITS, "items", false, Project.current.battlers.getTree());
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
            addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade", false);
        } else if ((style & TILE) > 0) {
            addSpinner(Vocab.instance.OFFSETX, Tooltip.instance.POSITIONX, "x", false);
            addSpinner(Vocab.instance.OFFSETY, Tooltip.instance.POSITIONY, "y", false);
            addSpinner(Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH, "h", false);
            addTextField(Vocab.instance.TILEREF, Tooltip.instance.TILEREF, "other");
            if ((style & LIMIT) > 0) {
                addSpinner(Vocab.instance.PATHLIMIT, Tooltip.instance.PATHLIMIT, "limit", true);
            }
        } else if ((style & DIR) > 0) {
            addSpinner(Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR, "angle", false);
            if ((style & LIMIT) > 0) {
                addSpinner(Vocab.instance.DISTANCE, Tooltip.instance.DISTANCE, "distance", false);
            }
        } else if ((style & DEACTIVATE) > 0) {
            addCheckBox(Vocab.instance.VISIBLE, Tooltip.instance.CHARVISIBLE, "visible");
            addCheckBox(Vocab.instance.PASSABLE, Tooltip.instance.CHARPASSABLE, "passable");
            addCheckBox(Vocab.instance.DEACTIVATE, Tooltip.instance.CHARDEACTIVATE, "deactivate");
            addCheckBox(Vocab.instance.PERSISTENT, Tooltip.instance.CHARPERSISTENT, "permanent");
            addCheckBox(Vocab.instance.OPTIONAL, Tooltip.instance.CHAROPTIONAL, "optional");
            addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade", false);
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
                Object value = oe.decodeData(p.value);
                oe.setObject(value);
            }
        }
        if (editors.containsKey("")) {
            LObjectEditor<?> oe = editors.get("");
            for (Tag p : initial) {
                Object value = oe.getFieldValue(p.key);
                if (value != null) {
                    value =  GGlobals.gson.fromJson(p.value, value.getClass());
                    oe.setFieldValue(p.key, value);
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
            Object value = entry.getValue().getValue();
            Tag p = new Tag(entry.getKey(), GGlobals.gson.toJson(value));
            params.add(p);
        }
        for (var entry : editors.entrySet()) {
            LObjectEditor<?> oe = entry.getValue();
            oe.saveObjectValues();
            if (entry.getKey().isEmpty()) {
                for (var field : oe.getFieldValues().entrySet()) {
                    Tag p = new Tag(field.getKey(), GGlobals.gson.toJson(field.getValue()));
                    params.add(p);
                }
            } else {
                Tag p = new Tag(entry.getKey(), GGlobals.gson.toJson(oe.getObject()));
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

    private LSpinner addSpinner(String label, String tooltip, String key, boolean optional) {
        new LLabel(contentEditor, label, tooltip);
        LSpinner spn = new LSpinner(contentEditor);
        if (optional) {
            spn.setMinimum(-1);
            spn.setValue(-1);
        } else {
            spn.setValue(0);
        }
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

    private PropertyList addPropertyList(String label, String tooltip, String key, boolean includeId, LDataTree<Object> data) {
        new LLabel(contentEditor, label, tooltip);
        PropertyList lst = new PropertyList(contentEditor, label);
        lst.dataTree = data;
        lst.getCollectionWidget().setIncludeID(includeId);
        lst.getCellData().setExpand(true, true);
        lst.getCellData().setSpread(2, 1);
        lst.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT * 2);
        lst.setObject(new LDataList<>());
        editors.put(key, lst);
        return lst;
    }

}
