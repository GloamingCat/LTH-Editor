package gui.views.database.subcontent;

import gui.Tooltip;
import gui.Vocab;
import lui.base.LPrefs;
import lui.base.event.listener.LControlListener;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LActionButton;
import lui.widget.LLabel;
import lui.widget.LToggleButton;

public class NeighborEditor extends GDefaultObjectEditor<boolean[]> {

	private LToggleButton[] labels;
	
	public NeighborEditor(LContainer parent) {
		super(parent, false);
	}

	protected void createContent(int style) {
		setFillLayout(true);
		
		LFrame group = new LFrame(this, Vocab.instance.NEIGHBORS);
		group.setGridLayout(2);
		group.setHoverText(Tooltip.instance.NEIGHBORS);
		
		LActionButton btnNone = new LActionButton(group, Vocab.instance.NONE);
		btnNone.addModifyListener(allAction(false));
		btnNone.getCellData().setExpand(true, false);
		btnNone.getCellData().setRequiredSize(LPrefs.BUTTONWIDTH, 0);
		
		LActionButton btnAll = new LActionButton(group, Vocab.instance.ALL);
		btnAll.addModifyListener(allAction(true));
		btnAll.getCellData().setExpand(true, false);
		btnAll.getCellData().setRequiredSize(LPrefs.BUTTONWIDTH, 0);
		
		LPanel composite = new LPanel(group);
		composite.setGridLayout(3);
		composite.getCellData().setExpand(true, true);
		composite.getCellData().setSpread(2, 1);
		
		LToggleButton arrow135 = new LToggleButton(composite, "img/arrow_135.png", "img/falsearrow_135.png");
		
		LToggleButton arrow90 = new LToggleButton(composite, "img/arrow_90.png", "img/falsearrow_90.png");
		
		LToggleButton arrow45 = new LToggleButton(composite, "img/arrow_45.png", "img/falsearrow_45.png");
		
		LToggleButton arrow180 = new LToggleButton(composite, "img/arrow_180.png", "img/falsearrow_180.png");
		
		new LLabel(composite, 1, 1);
		
		LToggleButton arrow0 = new LToggleButton(composite, "img/arrow_0.png", "img/falsearrow_0.png");
		
		LToggleButton arrow225 = new LToggleButton(composite, "img/arrow_225.png", "img/falsearrow_225.png");
		
		LToggleButton arrow270 = new LToggleButton(composite, "img/arrow_270.png", "img/falsearrow_270.png");
		
		LToggleButton arrow315 = new LToggleButton(composite, "img/arrow_315.png", "img/falsearrow_315.png");
		
		labels = new LToggleButton [] {arrow0, arrow45, arrow90, arrow135, arrow180, arrow225, arrow270, arrow315};
		for (LToggleButton btn : labels) {
			btn.getCellData().setExpand(true, true);
		}
		
	}
	
	private LControlListener<Object> allAction(final boolean newValue) {
		return event -> {
            for (int i = 0; i < 8; i++) {
                labels[i].setValue(newValue);
            }
        };
	}
	
	public void setObject(Object obj) {
		super.setObject(obj);
		if (obj != null) {
			boolean[] values = (boolean[]) obj;
			for(int i = 0; i < 8; i++) {
				labels[i].setValue(values[i]);
			}
		} else {
			for(int i = 0; i < 8; i++) {
				labels[i].setValue(null);
			}
		}
	}
	
	public boolean[] getValues() {
		boolean[] values = new boolean[8];
		for (int i = 0; i < 8; i++) {
			values[i] = labels[i].getValue();
		}
		return values;
	}

	@Override
	public Type getType() {
		return new TypeToken<boolean[]>() {}.getType();
	}

}
