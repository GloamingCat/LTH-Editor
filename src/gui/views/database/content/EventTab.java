package gui.views.database.content;

import gson.editor.GDefaultObjectEditor;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import data.EventSheet;
import project.Project;

public class EventTab extends DatabaseTab<EventSheet> {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public EventTab(LContainer parent) {
		super(parent);
		
		new LLabel(grpGeneral, Vocab.instance.DESCRIPTION, LLabel.TOP);
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addControl(txtDescription, "description");

		LFrame grpEvents = new LFrame(contentEditor, Vocab.instance.EVENTS, 2, false);
		grpEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		SimpleEditableList<EventSheet.Event> lstEvents = new SimpleEditableList<>(grpEvents);
		lstEvents.getCollectionWidget().setEditEnabled(false);
		lstEvents.setIncludeID(false);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstEvents, "events");
		
		GDefaultObjectEditor<EventSheet.Event> eventEditor = new GDefaultObjectEditor<EventSheet.Event>(grpEvents, 2, false, false);
		eventEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		lstEvents.addChild(eventEditor);
		
		new LLabel(eventEditor, LLabel.TOP, Vocab.instance.COMMAND);
		
		LTextBox txtCommand = new LTextBox(eventEditor);
		txtCommand.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		eventEditor.addControl(txtCommand, "name");
		
		new LLabel(eventEditor, LLabel.TOP, Vocab.instance.PARAM);
		
		TagList txtArgs = new TagList(eventEditor);
		txtArgs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		eventEditor.addChild(txtArgs, "tags");
		
		new LLabel(eventEditor, Vocab.instance.CONDITION);
		
		LText txtCondition = new LText(eventEditor);
		eventEditor.addControl(txtCondition, "condition");
		
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}

}
