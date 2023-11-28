package gui.views.database.content;

import gson.editor.GDefaultObjectEditor;
import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import data.EventSheet;
import project.Project;

public class EventTab extends DatabaseTab<EventSheet> {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public EventTab(LContainer parent) {
		super(parent);
		
		new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		addControl(txtDescription, "description");

		LFrame grpEvents = new LFrame(contentEditor, Vocab.instance.EVENTS, 2, false);
		grpEvents.setExpand(false, true);
		grpEvents.setSpread(2, 1);
		
		SimpleEditableList<EventSheet.Event> lstEvents = new SimpleEditableList<>(grpEvents);
		lstEvents.getCollectionWidget().setEditEnabled(false);
		lstEvents.setIncludeID(false);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.setExpand(true, true);
		addChild(lstEvents, "events");
		
		GDefaultObjectEditor<EventSheet.Event> eventEditor = new GDefaultObjectEditor<EventSheet.Event>(grpEvents, 2, false, false);
		eventEditor.setExpand(true, false);
		lstEvents.addChild(eventEditor);
		
		new LLabel(eventEditor, LFlags.TOP, Vocab.instance.COMMAND);
		
		LTextBox txtCommand = new LTextBox(eventEditor);
		txtCommand.setExpand(true, true);
		eventEditor.addControl(txtCommand, "name");
		
		new LLabel(eventEditor, LFlags.TOP, Vocab.instance.PARAM);
		
		TagList txtArgs = new TagList(eventEditor);
		txtArgs.setExpand(true, true);
		eventEditor.addChild(txtArgs, "tags");
		
		new LLabel(eventEditor, Vocab.instance.CONDITION);
		
		LText txtCondition = new LText(eventEditor);
		eventEditor.addControl(txtCondition, "condition");
		
		grpGeneral.setExpand(true, true);
		left.setExpand(true, false);
		right.setExpand(true, false);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}

}
