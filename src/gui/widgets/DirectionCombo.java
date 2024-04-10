package gui.widgets;

import lui.container.LContainer;
import lui.widget.LCombo;

public class DirectionCombo extends LCombo {
	
	public DirectionCombo(LContainer parent) {
		super(parent, true);
		setIncludeID(false);
		setOptional(false);
		String[] d = new String[] {"0º", "45º", "90º", "135º",
				"180º", "225º", "270º", "315º"};
		setItems(d);
	}
	
	public void setSelectionIndex(int i) {
		super.setSelectionIndex(i / 45);
	}
	public int getSelectionIndex() {
		return super.getSelectionIndex() * 45;
	}
	
}
