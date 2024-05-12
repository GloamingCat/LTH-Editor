package gui.views.fieldTree.subcontent;

import data.field.Transition;
import gui.Tooltip;
import gui.Vocab;
import gui.widgets.PortalButton;
import gui.widgets.PositionButton;
import lui.container.LContainer;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LSpinner;
import lui.widget.LText;

import java.lang.reflect.Type;

public class TransitionEditor extends GDefaultObjectEditor<Transition> {

    public TransitionEditor(LContainer parent, int id) {
        super(parent, id, false);
	}

    @Override
	protected void createContent(int id) {
        setGridLayout(3);

        // Destination

        new LLabel(this, Vocab.instance.DESTINATION, Tooltip.instance.DESTINATION);
        LText txtDest = new LText(this, true);
        txtDest.getCellData().setExpand(true, false);
        PositionButton btnDest = new PositionButton(this, id);
        btnDest.setTextWidget(txtDest);
        this.addControl(btnDest, "destination");

        // Origin Tiles

        new LLabel(this, Vocab.instance.ORIGTILES, Tooltip.instance.ORIGTILES);
        LText txtOrigin = new LText(this, true);
        txtOrigin.getCellData().setExpand(true, false);
        PortalButton btnOrigin = new PortalButton(this, id);
        btnOrigin.setTextWidget(txtOrigin);
        this.addControl(btnOrigin, "origin");

        // Fade

        new LLabel(this, Vocab.instance.FADEOUT, Tooltip.instance.FADEOUT);
        LSpinner spnFade = new LSpinner(this);
        spnFade.getCellData().setExpand(true, false);
        spnFade.setMinimum(-1);
        spnFade.setMaximum(99999);
        this.addControl(spnFade, "fade");
        new LLabel(this, 1, 1);

    }

    @Override
    public Type getType() {
        return Transition.class;
    }

}
