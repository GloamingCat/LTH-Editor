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
	
	public DirectionCombo(LContainer parent, int flags, boolean has8dir) {
		super(parent, flags);
		this.rotation = has8dir ? 45 : 90;
		setIncludeID(false);
		if (has8dir) {
			setItems(dir8);
		} else {
			setItems(dir4);
		}
	}

	public DirectionCombo(LContainer parent, int flags) {
		this(parent, flags, true);
	}

	public DirectionCombo(LContainer parent) {
		this(parent, LCombo.READONLY, true);
	}

	@Override
	protected void setSelectionIndex(int i) {
		if (i == -1)
			super.setSelectionIndex(-1);
		else
			super.setSelectionIndex(i / rotation);
	}

	@Override
	protected int getSelectionIndex() {
		int i = super.getSelectionIndex();
		if (i == -1)
			return -1;
		return i * rotation;
	}
	
}
