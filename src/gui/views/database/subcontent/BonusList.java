package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.BonusShell;
import gui.views.common.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.Bonus;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class BonusList extends SimpleEditableList<Bonus> {

	public BonusList(Composite parent, int style) {
		super(parent, style);
		setIncludeID(true);
		setShellFactory(new LShellFactory<Bonus>() {
			@Override
			public LObjectShell<Bonus> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new BonusShell(parent) {
					public ArrayList<?> getArray() {
						return comboArray();
					};
				};
			}
		});
	}
	
	protected ArrayList<?> comboArray() { return null; }

}
