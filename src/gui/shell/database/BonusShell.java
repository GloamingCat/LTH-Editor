package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectShell;

import data.subcontent.Bonus;
import lwt.LFlags;
import lwt.container.LStack;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LWidget;
import lwt.widget.LCombo;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LSpinner;

import project.Project;

public class BonusShell extends ObjectShell<Bonus> {

	protected LLabel typeLabel;
	protected LNodeSelector<Object> typeNode;
	protected LCombo cmbType;
	
	public BonusShell(LShell parent, String title) {
		super(parent, title);
		setMinimumSize(270, 100);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		// Type of bonus
		
		new LLabel(contentEditor, Vocab.instance.TYPE, Tooltip.instance.BONUSTYPE);
		
		cmbType = new LCombo(contentEditor, true);
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
		
		new LLabel(contentEditor, Vocab.instance.VALUE, Tooltip.instance.BONUSVALUE);
		
		LSpinner spnValue = new LSpinner(contentEditor);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		addControl(spnValue, "value");
		
		// ID
		
		typeLabel = new LLabel(contentEditor, LFlags.TOP, Vocab.instance.ELEMENT, Tooltip.instance.ELEMENTBONUS);
		
		final LStack stack = new LStack(contentEditor);
		stack.setExpand(true, true);
		
		final LNodeSelector<Object> elementTree = new LNodeSelector<Object>(stack, false);
		elementTree.setCollection(Project.current.elements.getList().toTree());
		
		final LNodeSelector<Object> statusTree = new LNodeSelector<Object>(stack, false);
		statusTree.setCollection(Project.current.status.getTree());
		
		cmbType.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				if (event.newValue == 3) {
					// Status
					typeLabel.setText(Vocab.instance.STATUS);
					typeLabel.setHoverText(Tooltip.instance.STATUSBONUS);
					typeNode = statusTree;
					stack.setTop((LWidget) typeNode);
					removeControl(elementTree);
					addControl(typeNode, "id");
				} else {
					// Element
					typeLabel.setText(Vocab.instance.ELEMENT);
					typeLabel.setHoverText(Tooltip.instance.ELEMENTBONUS);
					typeNode = elementTree;
					stack.setTop((LWidget) typeNode);	
					removeControl(statusTree);
					addControl(typeNode, "id");
				}
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
