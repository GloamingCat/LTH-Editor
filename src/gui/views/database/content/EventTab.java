package gui.views.database.content;

import gson.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;
import lbase.LFlags;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.gson.GDefaultObjectEditor;
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
		
		LLabel lblDesc = new LLabel(grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");

		LFrame grpEvents = new LFrame(contentEditor, Vocab.instance.EVENTS);
		grpEvents.setGridLayout(2);
		grpEvents.setHoverText(Tooltip.instance.EVENTS);
		grpEvents.getCellData().setExpand(true, true);
		grpEvents.getCellData().setSpread(2, 1);
		SimpleEditableList<EventSheet.Event> lstEvents = new SimpleEditableList<>(grpEvents);
		lstEvents.getCellData().setExpand(true, true);
		lstEvents.getCollectionWidget().setEditEnabled(false);
		lstEvents.setIncludeID(false);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.addMenu(grpEvents);
		addChild(lstEvents, "events");
		EventEditor eventEditor = new EventEditor(grpEvents, false);
		eventEditor.setGridLayout(2);
		eventEditor.getCellData().setExpand(true, true);
		lstEvents.addChild(eventEditor);
		
		LLabel lblCmd = new LLabel(eventEditor, LFlags.TOP, Vocab.instance.COMMAND,
				Tooltip.instance.COMMAND);
		LTextBox txtCommand = new LTextBox(eventEditor);
		txtCommand.getCellData().setExpand(true, true);
		txtCommand.addMenu(lblCmd);
		eventEditor.addControl(txtCommand, "name");
		
		LLabel lblParam = new LLabel(eventEditor, LFlags.TOP, Vocab.instance.PARAM,
				Tooltip.instance.PARAM);
		TagList txtArgs = new TagList(eventEditor);
		txtArgs.getCellData().setExpand(true, true);
		txtArgs.addMenu(lblParam);
		eventEditor.addChild(txtArgs, "tags");
		
		LLabel lblCondition = new LLabel(eventEditor, Vocab.instance.CONDITION,
				Tooltip.instance.CONDITION);
		LText txtCondition = new LText(eventEditor);
		txtCondition.getCellData().setExpand(true, false);
		txtCondition.addMenu(lblCondition);
		eventEditor.addControl(txtCondition, "condition");
		
		grpGeneral.getCellData().setExpand(true, true);
		left.getCellData().setExpand(true, false);
		right.getCellData().setExpand(true, false);
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}
	
	private static class EventEditor extends GDefaultObjectEditor<EventSheet.Event> {
		public EventEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}
		@Override
		public Type getType() {
			return EventSheet.Event.class;
		}
	}

}
