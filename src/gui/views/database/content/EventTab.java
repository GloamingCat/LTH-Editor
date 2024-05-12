package gui.views.database.content;

import gson.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.gson.GDefaultObjectEditor;
import lui.widget.LLabel;
import lui.widget.LText;
import lui.widget.LTextBox;

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
	}

	@Override
	protected void createContent() {
		contentEditor.grpGeneral.getCellData().setExpand(true, true);
		contentEditor.left.getCellData().setExpand(true, false);
		contentEditor.left.getCellData().setAlignment(LFlags.FILL);
		contentEditor.right.getCellData().setExpand(true, false);
		contentEditor.right.getCellData().setAlignment(LFlags.FILL);

		LLabel lblDesc = new LLabel(contentEditor.grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(contentEditor.grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.getCellData().setTargetSize(0, 0);
		txtDescription.getCellData().setRequiredSize(-1, -1);
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
		eventEditor.getCellData().setExpand(true, true);
		lstEvents.addChild(eventEditor);

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
		protected void createContent(int style) {
			setGridLayout(2);

			LLabel lblCmd = new LLabel(this, LFlags.TOP, Vocab.instance.COMMAND,
					Tooltip.instance.COMMAND);
			LTextBox txtCommand = new LTextBox(this);
			txtCommand.getCellData().setExpand(true, true);
			txtCommand.addMenu(lblCmd);
			addControl(txtCommand, "name");

			LLabel lblParam = new LLabel(this, LFlags.TOP, Vocab.instance.PARAM,
					Tooltip.instance.PARAM);
			TagList txtArgs = new TagList(this);
			txtArgs.getCellData().setExpand(true, true);
			txtArgs.addMenu(lblParam);
			addChild(txtArgs, "tags");

			LLabel lblCondition = new LLabel(this, Vocab.instance.CONDITION,
					Tooltip.instance.CONDITION);
			LText txtCondition = new LText(this);
			txtCondition.getCellData().setExpand(true, false);
			txtCondition.addMenu(lblCondition);
			addControl(txtCondition, "condition");
		}

		@Override
		public Type getType() {
			return EventSheet.Event.class;
		}
	}

}
