package gui.widgets;

import lui.container.LContainer;
import lui.widget.LCombo;

public class DirectionCombo extends LCombo {

	public static final String[] dir8 = new String[]
					{"0º", "45º", "90º", "135º",
					"180º", "225º", "270º", "315º"};
		public static final String[] dir4 = new String[]
					{"0º", "90º", "180º",  "270º"};
	private final int rotation;
	
	public DirectionCombo(LContainer parent, boolean dir8) {
		super(parent, true);
		this.rotation = dir8 ? 45 : 90;
		setIncludeID(false);
		setOptional(false);
		if (dir8) {
			setItems(DirectionCombo.dir8);
		} else {
			setItems(dir4);
		}
	}

	public DirectionCombo(LContainer parent) {
		this(parent, true);
	}
	
	public void setSelectionIndex(int i) {
		super.setSelectionIndex(i / rotation);
	}

	public int getSelectionIndex() {
		return super.getSelectionIndex() * rotation;
	}
	
}
