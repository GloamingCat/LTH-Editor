package gui.views.database.subcontent;

import gui.Vocab;

import gson.editor.GDefaultObjectEditor;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LActionButton;
import lwt.widget.LLabel;
import lwt.widget.LToggleButton;

public class NeighborEditor extends GDefaultObjectEditor<boolean[]> {

	private LToggleButton[] labels;
	
	public NeighborEditor(LContainer parent) {
		super(parent, true, false);
		LFrame group = new LFrame(this, Vocab.instance.NEIGHBORS, 2, true);
		
		LActionButton btnNone = new LActionButton(group, Vocab.instance.NONE);
		btnNone.addModifyListener(allAction(false));
		
		LActionButton btnAll = new LActionButton(group, Vocab.instance.ALL);
		btnAll.addModifyListener(allAction(true));
		
		LPanel composite = new LPanel(group, 3, true);
		composite.setExpand(true, true);
		composite.setSpread(2, 1);
		
		LToggleButton arrow135 = new LToggleButton(composite, "/img/arrow_135.png", "/img/falsearrow_135.png");
		
		LToggleButton arrow90 = new LToggleButton(composite, "/img/arrow_90.png", "/img/falsearrow_90.png");
		
		LToggleButton arrow45 = new LToggleButton(composite, "/img/arrow_45.png", "/img/falsearrow_45.png");
		
		LToggleButton arrow180 = new LToggleButton(composite, "/img/arrow_180.png", "/img/falsearrow_180.png");
		
		new LLabel(composite, 1, 1);
		
		LToggleButton arrow0 = new LToggleButton(composite, "/img/arrow_0.png", "/img/falsearrow_0.png");
		
		LToggleButton arrow225 = new LToggleButton(composite, "/img/arrow_225.png", "/img/falsearrow_225.png");
		
		LToggleButton arrow270 = new LToggleButton(composite, "/img/arrow_270.png", "/img/falsearrow_270.png");
		
		LToggleButton arrow315 = new LToggleButton(composite, "/img/arrow_315.png", "/img/falsearrow_315.png");
		
		labels = new LToggleButton [] {arrow0, arrow45, arrow90, arrow135, arrow180, arrow225, arrow270, arrow315};

	}
	
	private LControlListener<Object> allAction(final boolean newValue) {
		return new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> event) {
				for(int i = 0; i < 8; i++) {
					labels[i].setValue(newValue);
				}
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

}
