package gui.views.database;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LText;

import java.lang.reflect.Type;

public class DatabaseContentEditor<T> extends GDefaultObjectEditor<T> {
    public LFrame grpGeneral;
    protected LFrame grpTags;
    protected LLabel lblID;
    protected LLabel lblKey;
    protected LText txtKey;
    protected LLabel lblName;
    protected LText txtName;
    public LPanel left;
    public LPanel right;

    private final DatabaseTab<T> parent;

    protected static final int TAGSHEIGHT = 140;

    public DatabaseContentEditor(LContainer parent, DatabaseTab<T> tab, boolean doubleBuffered) {
        super(parent, doubleBuffered);
        this.parent = tab;
    }

    @Override
    public Type getType() {
        return parent.getSerializer().getDataType();
    }

    @Override
    protected void createContent(int style) {
        left = new LPanel(this);
        left.setGridLayout(1);
        left.getCellData().setExpand(true, true);
        right = new LPanel(this);
        right.setGridLayout(1);
        right.getCellData().setExpand(true, true);

        LFrame grpGeneral = new LFrame(left, Vocab.instance.GENERAL);
        grpGeneral.setGridLayout(2);
        grpGeneral.setHoverText(Tooltip.instance.GENERAL);
        grpGeneral.getCellData().setExpand(true, false);
        this.grpGeneral = grpGeneral;

        LPanel compID = new LPanel(grpGeneral);
        compID.setGridLayout(3);
        compID.getCellData().setExpand(true, false);
        compID.getCellData().setSpread(2, 1);
        lblID = new LLabel(compID, LFlags.EXPAND, "", Tooltip.instance.ID);
        lblKey = new LLabel(compID, Vocab.instance.KEY, Tooltip.instance.KEY);
        txtKey = new LText(compID);
        txtKey.getCellData().setExpand(true, false);
        addControl(txtKey, "key");

        lblName = new LLabel(grpGeneral, Vocab.instance.NAME, Tooltip.instance.NAME);
        lblName.getCellData().setRequiredSize(LPrefs.LABELWIDTH, 0);

        txtName = new LText(grpGeneral);
        txtName.getCellData().setExpand(true, false);
        addControl(txtName, "name");

        // Tags

        grpTags = new LFrame(right, Vocab.instance.TAGS);
        grpTags.setFillLayout(true);
        grpTags.getCellData().setExpand(true, false);
        grpTags.getCellData().setRequiredSize(0, TAGSHEIGHT);
        grpTags.getCellData().setTargetSize(-1, TAGSHEIGHT);
        TagList lstTags = new TagList(grpTags);
        addChild(lstTags, "tags");

    }
}
