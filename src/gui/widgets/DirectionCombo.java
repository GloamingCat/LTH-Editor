package gui.widgets;

import lwt.container.LContainer;
import lwt.widget.LCombo;

public class DirectionCombo extends LCombo {
	
	public DirectionCombo(LContainer parent) {
		super(parent, true);
		setIncludeID(false);
		setOptional(false);
		String[] d = new String[] {"0�", "45�", "90�", "135�", 
				"180�", "225�", "270�", "315�"};
		setItems(d);
	}
	
	public void setSelectionIndex(int i) {
		super.setSelectionIndex(i / 45);
	}
	public int getSelectionIndex() {
		return super.getSelectionIndex() * 45;
	}
	
}
