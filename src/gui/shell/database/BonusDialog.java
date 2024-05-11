package gui.shell.database;

import gui.Tooltip;
import gui.Vocab;
import gui.shell.ObjectEditorDialog;
import lui.base.LFlags;
import lui.base.event.listener.LControlListener;
import data.subcontent.Bonus;
import lui.container.LStack;
import lui.dialog.LWindow;
import lui.base.event.LControlEvent;
import lui.widget.LWidget;
import lui.widget.LCombo;
import lui.widget.LLabel;
import lui.widget.LNodeSelector;
import lui.widget.LSpinner;

import project.Project;

public class BonusDialog extends ObjectEditorDialog<Bonus> {

	protected LLabel typeLabel;
	protected LNodeSelector<Object> typeNode;
	protected LCombo cmbType;
	
	public BonusDialog(LWindow parent, String title) {
		super(parent, title);
		setRequiredSize(300, 200);
		setSize(300, 200);
	}
	
	@Override
	protected void createContent(int style) {
		super.createContent(style);
		contentEditor.setGridLayout(2);
		
		// Type of bonus
		new LLabel(contentEditor, Vocab.instance.TYPE, Tooltip.instance.BONUSTYPE);
		cmbType = new LCombo(contentEditor, true);
		cmbType.getCellData().setExpand(true, false);
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
		spnValue.getCellData().setExpand(true, false);
		spnValue.setMinimum(-10000);
		spnValue.setMaximum(10000);
		addControl(spnValue, "value");
		
		// ID
		typeLabel = new LLabel(contentEditor, LFlags.TOP, Vocab.instance.ELEMENT, Tooltip.instance.ELEMENTBONUS);
		
		final LStack stack = new LStack(contentEditor);
		stack.getCellData().setExpand(true, true);
		
		final LNodeSelector<Object> elementTree = new LNodeSelector<Object>(stack, false);
		elementTree.setCollection(Project.current.elements.getList().toTree());
		
		final LNodeSelector<Object> statusTree = new LNodeSelector<Object>(stack, false);
		statusTree.setCollection(Project.current.status.getTree());
		
		cmbType.addModifyListener(event -> {
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
        });
	}
	
	public void open(Bonus initial) {
		super.open(initial);
		cmbType.notifyEmpty();
		typeNode.setValue(initial.id);
	}

}
