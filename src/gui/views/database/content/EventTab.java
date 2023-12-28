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

import java.lang.reflect.Type;

import data.EventSheet;
import project.Project;

public class EventTab extends DatabaseTab<EventSheet> {

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell(800, 600)
	 */
	public EventTab(LContainer parent) {
		super(parent);
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral, 1, 1);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");

		LFrame grpEvents = new LFrame(contentEditor, Vocab.instance.EVENTS, 2, false);
		grpEvents.setExpand(false, true);
		grpEvents.setSpread(2, 1);
		SimpleEditableList<EventSheet.Event> lstEvents = new SimpleEditableList<>(grpEvents);
		lstEvents.getCollectionWidget().setEditEnabled(false);
		lstEvents.setIncludeID(false);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.setExpand(true, true);
		lstEvents.addMenu(grpEvents);
		addChild(lstEvents, "events");
		EventEditor eventEditor = new EventEditor(grpEvents, 2, false, false);
		eventEditor.setExpand(true, false);
		lstEvents.addChild(eventEditor);
		
		LLabel lblCmd = new LLabel(eventEditor, LFlags.TOP, Vocab.instance.COMMAND);
		LTextBox txtCommand = new LTextBox(eventEditor);
		txtCommand.setExpand(true, true);
		txtCommand.addMenu(lblCmd);
		eventEditor.addControl(txtCommand, "name");
		
		LLabel lblParam = new LLabel(eventEditor, LFlags.TOP, Vocab.instance.PARAM);
		TagList txtArgs = new TagList(eventEditor);
		txtArgs.setExpand(true, true);
		txtArgs.addMenu(lblParam);
		eventEditor.addChild(txtArgs, "tags");
		
		LLabel lblCondition = new LLabel(eventEditor, Vocab.instance.CONDITION);
		LText txtCondition = new LText(eventEditor);
		txtCondition.addMenu(lblCondition);
		eventEditor.addControl(txtCondition, "condition");
		
		grpGeneral.setExpand(true, true);
		left.setExpand(true, false);
		right.setExpand(true, false);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}
	
	private static class EventEditor extends GDefaultObjectEditor<EventSheet.Event> {
		public EventEditor(LContainer parent, int columns, boolean equalCols, boolean doubleBuffered) {
			super(parent, columns, equalCols, doubleBuffered);
		}
		@Override
		public Type getType() {
			return EventSheet.Event.class;
		}
	}

}
