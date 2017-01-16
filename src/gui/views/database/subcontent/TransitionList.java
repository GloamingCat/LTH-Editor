package gui.views.database.subcontent;

import gui.shell.TransitionShell;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Position;
import data.Transition;
import lwt.dataestructure.LDataCollection;
import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LDefaultListEditor;

public class TransitionList extends LDefaultListEditor<Transition> {

	protected LDataList<Transition> currentList;
	
	public TransitionList(Composite parent, int style) {
		super(parent, style);
		getCollectionWidget().setEditEnabled(true);
		getCollectionWidget().setInsertNewEnabled(true);
		getCollectionWidget().setDuplicateEnabled(true);
		getCollectionWidget().setDeleteEnabled(true);
		getCollectionWidget().setDragEnabled(true);
		setIncludeID(false);
		setShellFactory(new LShellFactory<Transition>() {
			@Override
			public LObjectShell<Transition> createShell(Shell parent) {
				return new TransitionShell(parent);
			}
		});
	}
	
	public void setObject(Object object) {
		if (object == null) {
			super.setObject(null);
		} else {
			Object value = getFieldValue(object, "transitions");
			super.setObject(value);
		}
	}

	@Override
	public void setDataCollection(LDataCollection<Transition> list) {
		currentList = (LDataList<Transition>) list;
		super.setDataCollection(list);
	}
	
	@Override
	public LDataList<Transition> getDataCollection() {
		return currentList;
	}

	@Override
	public Transition createNewData() {
		return new Transition();
	}

	@Override
	public Transition duplicateData(Transition original) {
		Transition transition = new Transition();
		transition.origin = new Position(original.origin);
		transition.destination = new Position(original.destination);
		transition.fadeOut = original.fadeOut;
		return transition;
	}

}
