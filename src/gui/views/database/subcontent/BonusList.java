package gui.views.database.subcontent;

import java.util.ArrayList;

import gui.shell.BonusShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import data.Bonus;
import lwt.dataestructure.LDataTree;
import lwt.dataestructure.LPath;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LEditEvent;
import lwt.widget.LList;

public class BonusList extends SimpleEditableList<Bonus> {

	public BonusList(Composite parent, int style) {
		super(parent, style);
		type = Bonus.class;
		setIncludeID(false);
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
	
	protected LList<Bonus, Bonus> createList() {
		return new LList<Bonus, Bonus>(this, SWT.NONE) {
			@Override
			public LEditEvent<Bonus> edit(LPath path) {
				return onEditItem(path);
			}
			@Override
			public Bonus toObject(LPath path) {
				if (path == null)
					return null;
				return getDataCollection().get(path.index);
			}
			@Override
			public LDataTree<Bonus> toNode(LPath path) {
				Bonus i = toObject(path);
				return new LDataTree<Bonus> (i);
			}
			@Override
			public LDataTree<Bonus> emptyNode() {
				return new LDataTree<Bonus>(new Bonus());
			}
			@Override
			public LDataTree<Bonus> duplicateNode(LPath path) {
				Bonus copy = new Bonus(toObject(path));
				return new LDataTree<Bonus> (copy);
			}
			@Override
			protected String dataToString(Bonus item) {
				Object obj = comboArray().get(item.id);
				return stringID(item.id) + obj.toString() + ": " + item.value;
			}
		};
	}
	
	protected ArrayList<?> comboArray() { return null; }

}
