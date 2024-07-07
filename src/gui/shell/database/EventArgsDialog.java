package gui.shell.database;

import data.subcontent.Tag;
import gson.GGlobals;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.PropertyList;
import gui.views.fieldTree.PositionEditor;
import gui.widgets.AudioButton;
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

    public static final int KEY = 1;
    public static final int FIELD = 2;
    public static final int WINDOW = 4;
    public static final int MENU = 8;
    public static final int AUDIO = 16;
    public static final int NAME = 32;
    public static final int VISIBLE = 64;
    public static final int POS = 128;
    public static final int DIR = 256;
    public static final int WAIT = 512;
    public static final int PROP = 1024;
    public static final int SKIP = 2048;
    public static final int SPEED = 4096;
    public static final int DURATION = 8192;
    public static final int VAR = 16384;
    public static final int ITEM = 32768;
    public static final int INPUT = 65536;
    public static final int COLOR = 131072;
    public static final int ALL = 262144;
    public static final int SKILL = 524288;
    public static final int FORMATION = 1048576;
    public static final int VALUE = 2097152;
    public static final int HEIGHT = 4194304;
    public static final int RANDOM = 8388608;

    //public static final int FLAG = 16777216;

    //public static final int FLAG = 1073741824;

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

        if (style == NAME) {
            // Set label
            addTextField(Vocab.instance.NAME, Tooltip.instance.LABEL, "name");
            //LSpinner spn = addSpinner(Vocab.instance.EVENTID, Tooltip.instance.EVENTID, "index", false);
            //spn.setMinimum(-1);
            //spn.setValue(-1);
        } else if (style == (VAR | VALUE)) {
            // Set Variable
            addTextField(Vocab.instance.KEY, Tooltip.instance.VARIABLE, "key");
            addTextField(Vocab.instance.VALUE, Tooltip.instance.VARVALUE, "value");
        } else if ((style & SKIP) > 0) {
            if ((style & NAME) > 0)
                // Go to label
                addTextField(Vocab.instance.NAME, Tooltip.instance.LABEL, "name");
            else if ((style & VALUE) > 0)
                // Skip events
                addSpinner(Vocab.instance.COUNT, Tooltip.instance.EVENTSKIP, "events", false);
            else
                // Go to event
                addSpinner(Vocab.instance.EVENTID, Tooltip.instance.EVENTID, "index", true);
        } else if ((style & KEY) > 0) {
            addTextField(Vocab.instance.KEY, Tooltip.instance.CHARKEY, "key");
            if ((style & NAME) > 0 && (style & WINDOW) == 0)
                addTextField(Vocab.instance.NAME, Tooltip.instance.CHARKEY, "name");
        } else if ((style & NAME) > 0 && (style & WINDOW) == 0) {
            addTextField(Vocab.instance.NAME, Tooltip.instance.SCRIPT, "name");
        }

        if ((style & WINDOW) > 0) {
            if ((style & INPUT) == 0) {
                LSpinner spn = addSpinner(Vocab.instance.ID, Tooltip.instance.ID, "id", false);
                spn.setMinimum(1);
                spn.setValue(1);
            }
            if ((style & POS) > 0) {
                if ((style & INPUT) > 0) {
                    String varName;
                    if ((style & NAME) > 0) {
                        // String Input
                        addSpinner(Vocab.instance.MINLENGTH, Tooltip.instance.LENGTHLIMITS, "min", false);
                        addSpinner(Vocab.instance.MAXLENGTH, Tooltip.instance.LENGTHLIMITS, "max", true);
                        addTextField(Vocab.instance.CANCELVALUE, Tooltip.instance.CANCELVALUE, "cancel");
                    } else if ((style & VALUE) > 0) {
                        // Password
                        addSpinner(Vocab.instance.DIGITS, Tooltip.instance.DIGITS, "length", false);
                        addSpinner(Vocab.instance.CANCELVALUE, Tooltip.instance.CANCELVALUE, "cancel", true);
                    } else {
                        // Choices
                        addNameList(Vocab.instance.OPTIONS, Tooltip.instance.CHOICES, "choices", true);
                        addSpinner(Vocab.instance.CANCELVALUE, Tooltip.instance.CANCELVALUE, "cancel", true);
                    }
                    LText txt = addTextField(Vocab.instance.VARIABLE, Tooltip.instance.VARIABLE, "variable");
                    txt.setValue("inputResult");
                } else if ((style & NAME) > 0) {
                    // Dialogue
                    addTextField(Vocab.instance.TEXT, Tooltip.instance.TEXT, "message");
                } else {
                    // Title/message
                    addTextField(Vocab.instance.TEXT, Tooltip.instance.TEXT, "text");
                }
                addCombo(Vocab.instance.ALIGNX, Tooltip.instance.TEXTALIGN, "alignX",
                        new String[] {Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT}, 0 );
                addCombo(Vocab.instance.ALIGNY, Tooltip.instance.TEXTALIGN, "alignY",
                        new String[] {Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM}, 0 );
                addSpinner(Vocab.instance.WIDTH, Tooltip.instance.SIZEX, "width", true);
                addSpinner(Vocab.instance.HEIGHT, Tooltip.instance.SIZEY, "height", true);
                LSpinner x = addSpinner(Vocab.instance.POSITIONX, Tooltip.instance.CENTERX, "x", false);
                LSpinner y = addSpinner(Vocab.instance.POSITIONY, Tooltip.instance.CENTERY, "y", false);
                x.setMinimum(Integer.MIN_VALUE);
                y.setMinimum(Integer.MIN_VALUE);
                if ((style & INPUT) == 0) {
                    if ((style & NAME) > 0) {
                        // Dialogue
                        addTextField(Vocab.instance.NAME, Tooltip.instance.DISPLAYNAME, "name");
                        x = addSpinner(Vocab.instance.NAMEX, Tooltip.instance.NAMEPOS, "nameX", false);
                        y = addSpinner(Vocab.instance.NAMEY, Tooltip.instance.NAMEPOS, "nameY", false);
                        x.setMinimum(Integer.MIN_VALUE);
                        y.setMinimum(Integer.MIN_VALUE);
                    } else {
                        // Title/message
                        addCheckBox(Vocab.instance.WAIT, Tooltip.instance.WAIT, "wait");
                    }
                }
            }
        } else if ((style & MENU) > 0) {
            if ((style & ITEM) > 0) {
                // Shop
                var list = addPropertyList(Vocab.instance.ITEMS, Tooltip.instance.INVENTORY, "items", false, true, Project.current.items.getTree());
                addCheckBox(Vocab.instance.SELLABLE, Tooltip.instance.SELLABLE, "sell");
            } else if ((style & FORMATION) > 0) {
                // Recruit
                addPropertyList(Vocab.instance.BATTLERS, Tooltip.instance.UNITS, "items", false, true, Project.current.battlers.getTree());
                addCheckBox(Vocab.instance.RECRUIT, Tooltip.instance.RECRUIT, "sell");
            } else {
                // Field/Save
                addCombo(Vocab.instance.TYPE, Tooltip.instance.KEY, "menu",
                    new String[] { Vocab.instance.FIELDMENU, Vocab.instance.SAVEMENU }, 0 );
            }
        } else if ((style & FORMATION) > 0) {
            if ((style & KEY) > 0) {
                // Character
                if ((style & ITEM) > 0) {
                    // Equip
                    addTextField(Vocab.instance.SLOT, Tooltip.instance.SLOT, "slot");
                    addNodeSelector(Vocab.instance.EQUIPITEM, Tooltip.instance.EQUIPITEM, "id",
                            Project.current.items.getTree(), LNodeSelector.OPTIONAL);
                    addCheckBox(Vocab.instance.STORE, Tooltip.instance.STORE, "store");
                } else if ((style & VALUE) > 0) {
                    // Level
                    addSpinner(Vocab.instance.LEVEL, Tooltip.instance.LEVEL, "level", false);
                } else if ((style & ALL) > 0) {
                    if ((style & SKILL) > 0) {
                        // Learn Skill
                        addNodeSelector(Vocab.instance.SKILL, Tooltip.instance.SKILL, "id",
                            Project.current.skills.getTree(), 0);
                    } else {
                        // Add Status
                        addNodeSelector(Vocab.instance.STATUS, Tooltip.instance.EFFECTSTATUS, "id",
                            Project.current.skills.getTree(), 0);
                    }
                    addCheckBox(Vocab.instance.REMOVE, Tooltip.instance.REMOVE, "remove");
                } else if ((style & POS) > 0) {
                    // Add unit
                    addSpinner(Vocab.instance.GRIDX, Tooltip.instance.GRIDX, "x", false);
                    addSpinner(Vocab.instance.GRIDY, Tooltip.instance.GRIDY, "y", false);
                    addCheckBox(Vocab.instance.BACKUP, Tooltip.instance.UNITLIST, "backup");
                }
            } else if ((style & VALUE) > 0) {
                // Money/EXP/Item
                if ((style & ITEM) > 0) {
                    // Item
                    addNodeSelector(Vocab.instance.EQUIPITEM, Tooltip.instance.EQUIPITEM, "id",
                        Project.current.items.getTree(), 0);
                }
                LSpinner spn = addSpinner(Vocab.instance.VALUE, Tooltip.instance.VALUE, "fade", false);
                spn.setMinimum(Integer.MIN_VALUE);
            } else if ((style & SKILL) > 0) {
                // Use Skill
                addNodeSelector(Vocab.instance.SKILL, Tooltip.instance.SKILL, "id",
                        Project.current.skills.getTree(), 0);
                addSpinner(Vocab.instance.PARTY, Tooltip.instance.PLAYERPARTY, "party", true);
                addTextField(Vocab.instance.USER, Tooltip.instance.SKILLUSER, "user");
                addTextField(Vocab.instance.TARGET, Tooltip.instance.SKILLTARGET, "target");
            }
            if ((style & ALL) > 0)
                addCheckBox(Vocab.instance.BACKUP, Tooltip.instance.BACKUP, "backup");
        } else if ((style & AUDIO) > 0) {
            if ((style & EventArgsDialog.SKILL) > 0)
                new LLabel(contentEditor, Vocab.instance.BGM, Tooltip.instance.BGM);
            else
                new LLabel(contentEditor, Vocab.instance.SOUND, Tooltip.instance.SOUND);
            LText txtAudio = new LText(contentEditor, true);
            txtAudio.getCellData().setExpand(true, false);
            AudioButton btnAudio = new AudioButton(contentEditor, false);
            btnAudio.setTextWidget(txtAudio);
            controls.put("", btnAudio);
        } else if ((style & FIELD) > 0) {
            if ((style & POS) > 0) {
                // Transition
                contentEditor.setGridLayout(6);
                LFrame grpDestination = new LFrame(contentEditor, Vocab.instance.DESTINATION);
                grpDestination.setHoverText(Tooltip.instance.DESTINATION);
                grpDestination.setFillLayout(true);
                grpDestination.getCellData().setSpread(6, 1);
                grpDestination.getCellData().setExpand(true, true);
                PositionEditor position = new PositionEditor(grpDestination, -1, false);
                position.onVisible();
                position.setObject(Project.current.config.getData().player.startPos.clone());
                editors.put("", position);
                contentEditor.addChild((LView) position);
                new LLabel(contentEditor, 1, 1).getCellData().setExpand(true, false);
                LCheckBox btn = addCheckBox(Vocab.instance.MOVEPLAYER, Tooltip.instance.MOVEPLAYER, "movePlayer");
                btn.getCellData().setSpread(1, 1);
            } else {
                // Battle
                addNodeSelector(Vocab.instance.BATTLEFIELD, Tooltip.instance.BATTLEFIELD, "fieldID",
                        Project.current.fieldTree.getData().toTree(), LNodeSelector.OPTIONAL);
                addCheckBox(Vocab.instance.SKIPINTRO, Tooltip.instance.SKIPINTRO, "skipIntro");
                addCheckBox(Vocab.instance.DISABLEESCAPE, Tooltip.instance.DISABLEESCAPE, "skipIntro");
                addCombo(Vocab.instance.GAMEOVERCONDITION, Tooltip.instance.GAMEOVERCONDITION, "gameOverCondition",
                       new String[] { Vocab.instance.NONE, Vocab.instance.LOSE, Vocab.instance.NOWIN }, 0 );
            }
            addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade", false);
        } else if ((style & POS) > 0) {
            // Tile Destination
            addSpinner(Vocab.instance.OFFSETX, Tooltip.instance.POSITIONX, "x", false);
            addSpinner(Vocab.instance.OFFSETY, Tooltip.instance.POSITIONY, "y", false);
            if ((style & HEIGHT) > 0)
                addSpinner(Vocab.instance.HEIGHT, Tooltip.instance.POSITIONH, "h", false);
            if ((style & KEY) > 0)
                addTextField(Vocab.instance.OBJREF, Tooltip.instance.TILEREF, "other");
            if ((style & VALUE) > 0) {
                addSpinner(Vocab.instance.VISION, Tooltip.instance.PATHLIMIT, "vision", true);
                addSpinner(Vocab.instance.DISTANCE, Tooltip.instance.DISTANCE, "distance", true);
            }
            if ((style & SPEED) > 0)
                addSpinner(Vocab.instance.SPEED, Tooltip.instance.SPEED, "speed", true);
        } else if ((style & DIR) > 0) {
            // Angle
            addSpinner(Vocab.instance.DIRECTION, Tooltip.instance.CHARDIR, "angle", false);
            if ((style & KEY) > 0)
                addTextField(Vocab.instance.OBJREF, Tooltip.instance.DIRREF, "other");
            if ((style & VALUE) > 0)
                addSpinner(Vocab.instance.DISTANCE, Tooltip.instance.DISTANCE, "distance", false);
            if ((style & SPEED) > 0)
                addSpinner(Vocab.instance.SPEED, Tooltip.instance.SPEED, "speed", true);
        } else if ((style & RANDOM) > 0) {
             if ((style & KEY) > 0)
                addCheckBox(Vocab.instance.OBSTACLES, Tooltip.instance.INCLUDEOBSTACLES, "blocked");
            if ((style & SPEED) > 0)
                addSpinner(Vocab.instance.SPEED, Tooltip.instance.SPEED, "speed", true);
        } else if ((style & HEIGHT) > 0) {
            addSpinner(Vocab.instance.HEIGHT, Tooltip.instance.PIXELHEIGHT, "height", true);
            if ((style & SPEED) > 0)
                addSpinner(Vocab.instance.SPEED, Tooltip.instance.SPEED, "speed", true);
        } else if ((style & COLOR) > 0) {
            addSpinner(Vocab.instance.RED, Tooltip.instance.RED, "red", false);
            addSpinner(Vocab.instance.GREEN, Tooltip.instance.GREEN, "green", false);
            addSpinner(Vocab.instance.BLUE, Tooltip.instance.BLUE, "blue", false);
        } else if ((style & SPEED) > 0) {
            addSpinner(Vocab.instance.SPEED, Tooltip.instance.SPEED, "speed", true);
        }

        if ((style & VISIBLE) > 0)
            addCheckBox(Vocab.instance.VISIBLE, Tooltip.instance.VISIBLE, "visible");

        if ((style & PROP) > 0) {
            if ((style & ALL) > 0) {
                // Delete Char
                addCheckBox(Vocab.instance.PERSISTENT, Tooltip.instance.CHARPERSISTENT, "permanent");
                addCheckBox(Vocab.instance.OPTIONAL, Tooltip.instance.CHAROPTIONAL, "optional");
                addSpinner(Vocab.instance.TIME, Tooltip.instance.WAITTIME, "time", false);
                addCheckBox(Vocab.instance.WAIT, Tooltip.instance.WAIT, "wait");
                new LLabel(contentEditor, 1, 1);
                LLabel lblReset = new LLabel(contentEditor, Vocab.instance.RESET, Tooltip.instance.RESET);
                lblReset.getCellData().setSpread(2, 1);
            }
            if ((style & VALUE) > 0) {
                // Props
                addCombo(Vocab.instance.TYPE, Tooltip.instance.KEY, "prop",
                    new String[] { Vocab.instance.PASSABLE, Vocab.instance.ACTIVE, Vocab.instance.SPEED,
                    Vocab.instance.FIXANIM, Vocab.instance.FIXDIR }, 0 );
                addTextField(Vocab.instance.VALUE, Tooltip.instance.VALUE, "value");
            } else if ((style & (VISIBLE)) > 0) {
                // Setup Shadow/Char/Image
                addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade", false);
            } else {
                // Log/Reset/Delete
                addCheckBox(Vocab.instance.TILE, Tooltip.instance.TILERESET, "tile");
                addCheckBox(Vocab.instance.GRAPHICS, Tooltip.instance.GRAPHICSRESET, "graphics");
                addCheckBox(Vocab.instance.PROPERTIES, Tooltip.instance.PROPRESET, "props");
                addCheckBox(Vocab.instance.SCRIPTS, Tooltip.instance.SCRIPTRESET, "scripts");
                addCheckBox(Vocab.instance.VARIABLES, Tooltip.instance.VARRESET, "vars");
            }
        }

        if ((style & DURATION) > 0)
            addSpinner(Vocab.instance.DURATION, Tooltip.instance.WAITTIME, "time", false);
        if ((style & WAIT) > 0)
            addCheckBox(Vocab.instance.WAIT, Tooltip.instance.WAIT, "wait");

    }

    @Override
    public void open(LDataList<Tag> initial) {
        if (editors.containsKey("")) {
            LObjectEditor<?> oe = editors.get("");
            String json = GGlobals.encodeJsonList(initial, Tag::toString);
            json = "{" + json.substring(1, json.length() - 1) + "}";
            Object obj = oe.decodeData(json);
            if (obj != null)
                oe.setObject(obj);
        }
        if (controls.containsKey("")) {
            LControlWidget<?> oe = controls.get("");
            String json = GGlobals.encodeJsonList(initial, Tag::toString);
            json = "{" + json.substring(1, json.length() - 1) + "}";
            Object obj = oe.decodeData(json);
            if (obj != null)
                oe.setValue(obj);
        }
        for (Tag p : initial) {
            if (controls.containsKey(p.key)) {
                LControlWidget<?> cw = controls.get(p.key);
                try {
                    if (cw instanceof LText) {
                        cw.setValue(p.value);
                    } else if (cw instanceof LCheckBox) {
                        cw.setValue(Boolean.parseBoolean(p.value));
                    } else {
                        cw.setValue(Integer.parseInt(p.value));
                    }
                } catch (IllegalArgumentException ignored) {
                    cw.setEnabled(false);
                }
            } else if (editors.containsKey(p.key)) {
                LObjectEditor<?> oe = editors.get(p.key);
                Object value = oe.decodeData(p.value);
                oe.setObject(value);
                System.out.println(p.key + ": " + value.getClass());
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
            LControlWidget<?> cw = entry.getValue();
            if (!cw.isEnabled())
                continue;
            Object value = entry.getValue().getValue();
            if (value == null)
                continue;
            if (entry.getKey().isEmpty()) {
                for (var field : LObjectEditor.getFieldValues(value).entrySet()) {
                    Tag p = new Tag(field.getKey(), GGlobals.gson.toJson(field.getValue()));
                    params.add(p);
                }
            } else {
                Tag p = new Tag(entry.getKey(), "");
                if (value instanceof String str)
                    p.value = str;
                else
                    p.value = value.toString();
                params.add(p);
            }
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
                String str = oe.encodeObject();
                if (str != null) {
                    Tag p = new Tag(entry.getKey(), oe.encodeObject());
                    params.add(p);
                }
            }
        }
        return params;
    }

    //////////////////////////////////////////////////
	//region Controls

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
        LCombo cmb = new LCombo(contentEditor, LCombo.READONLY | opt);
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

    private PropertyList addPropertyList(String label, String tooltip, String key, boolean includeId, boolean optional, LDataTree<Object> data) {
        new LLabel(contentEditor, label, tooltip);
        PropertyList lst = new PropertyList(contentEditor, label, optional);
        lst.dataTree = data;
        lst.getCollectionWidget().setIncludeID(includeId);
        lst.getCellData().setExpand(true, true);
        lst.getCellData().setSpread(2, 1);
        lst.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT * 2);
        lst.setObject(new LDataList<>());
        editors.put(key, lst);
        return lst;
    }

    private NameList addNameList(String label, String tooltip, String key, boolean includeId) {
        new LLabel(contentEditor, label, tooltip);
        NameList lst = new NameList(contentEditor, Vocab.instance.TEXT);
        lst.getCollectionWidget().setIncludeID(true);
        lst.getCellData().setExpand(true, true);
        lst.getCellData().setSpread(2, 1);
        lst.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.LISTHEIGHT * 2);
        lst.setObject(new LDataList<>());
        editors.put(key, lst);
        return lst;
    }

    //endregion

}
