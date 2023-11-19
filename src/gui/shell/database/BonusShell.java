package gui.shell.database;

import gui.Vocab;
import gui.shell.ObjectShell;

import data.subcontent.Bonus;
import lwt.container.LPanel;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;

public class BonusShell extends ObjectShell<Bonus> {

	protected LLabel typeLabel;
	protected LNodeSelector<Object> typeNode;
	protected LCombo cmbType;
	
	public BonusShell(LShell parent) {
		super(parent, 270, 100);
		contentEditor.setLayout(new GridLayout(2, false));
		
		// Type of bonus
		
		new LLabel(contentEditor, Vocab.instance.TYPE);
		
		cmbType = new LCombo(contentEditor);
		cmbType.setOptional(false);
		cmbType.setIncludeID(false);
		cmbType.setItems(new String[] {
				Vocab.instance.ELEMENTDEF,
				Vocab.instance.ELEMENTATK,
				Vocab.instance.ELEMENTBUFF,
				Vocab.instance.STATUSDEF
		});
		addControl(cmbType, "type");
		
		// Value of bonus
		
		new LLabel(contentEditor, Vocab.instance.VALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		addControl(spnValue, "value");
		
		// ID
		
		typeLabel = new LLabel(contentEditor, LLabel.TOP, Vocab.instance.ELEMENT);
		
		final LPanel idStack = new LPanel(contentEditor);
		idStack.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		final StackLayout stack = new StackLayout();
		idStack.setLayout(stack);
		
		final LNodeSelector<Object> elementTree = new LNodeSelector<Object>(idStack, false);
		elementTree.setCollection(Project.current.elements.getList().toTree());
		
		final LNodeSelector<Object> statusTree = new LNodeSelector<Object>(idStack, false);
		statusTree.setCollection(Project.current.status.getTree());
		
		cmbType.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.newValue == 3) {
					// Status
					typeLabel.setText(Vocab.instance.STATUS);
					typeNode = statusTree;
					stack.topControl = typeNode;
					removeControl(elementTree);
					addControl(typeNode, "id");
				} else {
					// Element
					typeLabel.setText(Vocab.instance.ELEMENT);
					typeNode = elementTree;
					stack.topControl = typeNode;	
					removeControl(statusTree);
					addControl(typeNode, "id");
				}
				idStack.layout();
			}
		});
		
		pack();
	}
	
	public void open(Bonus initial) {
		super.open(initial);
		cmbType.notifyEmpty();
		typeNode.setValue(initial.id);
	}

}
