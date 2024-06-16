package gui.shell.database;

import data.subcontent.Position;
import data.subcontent.Tag;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.PositionButton;
import lui.base.LPrefs;
import lui.base.data.LDataList;
import lui.base.data.LDataTree;
import lui.dialog.LWindow;
import lui.gson.GObjectDialog;
import lui.widget.*;
import project.Project;

import java.util.TreeMap;

public class EventArgsDialog extends GObjectDialog<LDataList<Tag>> {

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
    public static final int PASSABLE = 4096;
    public static final int OPTIONAL = 8192;
    public static final int PERMANENT = 16384;
    public static final int LIMIT = 32768;
    public static final int SELL = 65536;
    public static final int INPUT = 131072;
    public static final int ALIGN = 262144;
    public static final int EXP = 524288;
    public static final int EQUIP = 524288;
    public static final int FORMATION = 1048576;

    TreeMap<String, LControlWidget<?>> controls;

    PositionButton btnPosition;


    public EventArgsDialog(LWindow parent, int style, String title) {
        super(parent, style, title);
    }

	@Override
	protected void createContent(int style) {
        super.createContent(style);
        contentEditor.setGridLayout(3);
        controls = new TreeMap<>();

        if ((style & KEY) > 0)
            addTextField(Vocab.instance.KEY, Tooltip.instance.KEY, "key");
        if ((style & FIELD) > 0) {
            if ((style & TILE) > 0) {
                // Transition
                new LLabel(contentEditor, Vocab.instance.DESTINATION, Tooltip.instance.DESTINATION);
                LText txtPos = new LText(contentEditor, true);
                txtPos.getCellData().setExpand(true, false);
                btnPosition = new PositionButton(contentEditor, -1);
                btnPosition.setValue(Project.current.config.getData().player.startPos);
                btnPosition.setTextWidget(txtPos);
            } else {
                // Battle
                addNodeSelector(Vocab.instance.BATTLEFIELD, Tooltip.instance.BATTLEFIELD, "fieldID",
                        Project.current.fieldTree.getData().toTree(), true);
                addCheckBox(Vocab.instance.SKIPINTRO, Tooltip.instance.SKIPINTRO, "skipIntro");
                addCheckBox(Vocab.instance.DISABLEESCAPE, Tooltip.instance.DISABLEESCAPE, "skipIntro");
                addCombo(Vocab.instance.GAMEOVERCONDITION, Tooltip.instance.GAMEOVERCONDITION, "gameOverCondition",
                       new String[] { Vocab.instance.NONE, Vocab.instance.LOSE, Vocab.instance.NOWIN }, 0 );
            }
            addSpinner(Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT, "fade");
            return;
        } else if ((style & TILE) > 0) {
            addSpinner(Vocab.instance.POSITIONX, Tooltip.instance.POSITIONX, "x");
            addSpinner(Vocab.instance.POSITIONY, Tooltip.instance.POSITIONY, "y");
            addSpinner(Vocab.instance.POSITIONH, Tooltip.instance.POSITIONH, "h");
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
        }

        if ((style & NAME) > 0)
            addTextField(Vocab.instance.NAME, Tooltip.instance.NAME, "name");
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
            }
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
        if (btnPosition != null) {
            Position p = btnPosition.getValue();
            params.add(new Tag("fieldID", "" + p.fieldID));
            params.add(new Tag("x", "" + p.x));
            params.add(new Tag("y", "" + p.y));
            params.add(new Tag("h", "" + p.h));
            params.add(new Tag("direction", "" + p.direction));
        }
        if (params.equals(initial))
            return null;
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
        LCombo cmb = new LCombo(contentEditor, opt);;
        cmb.setItems(items);
        cmb.setValue(0);
        cmb.getCellData().setExpand(true, false);
        cmb.getCellData().setSpread(2, 1);
        cmb.getCellData().setRequiredSize(LPrefs.LISTWIDTH, LPrefs.WIDGETHEIGHT);
        controls.put(key, cmb);
        return cmb;
    }

    private <T> LNodeSelector<T> addNodeSelector(String label, String tooltip, String key, LDataTree<T> tree, boolean opt) {
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

}
