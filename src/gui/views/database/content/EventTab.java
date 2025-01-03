package gui.views.database.content;

import gson.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.EventEditor;
import gui.widgets.CheckBoxPanel;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.data.LDataTree;
import lui.base.data.LPath;
import lui.base.event.LEditEvent;
import lui.container.*;
import lui.collection.LEditableList;
import lui.graphics.LColor;
import lui.widget.*;

import data.EventSheet;
import data.EventSheet.Command;
import project.Project;

public class EventTab extends DatabaseTab<EventSheet> {

	private EventEditor eventEditor;

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
		contentEditor.grpTags.setTitle(Vocab.instance.PARAM);
		contentEditor.grpTags.setHoverText(Tooltip.instance.PARAM);

		LLabel lblDesc = new LLabel(contentEditor.grpGeneral, LFlags.TOP, Vocab.instance.DESCRIPTION,
				Tooltip.instance.DESCRIPTION);
		LTextBox txtDescription = new LTextBox(contentEditor.grpGeneral);
		txtDescription.getCellData().setExpand(true, true);
		txtDescription.getCellData().setTargetSize(0, 0);
		txtDescription.getCellData().setRequiredSize(-1, -1);
		txtDescription.addMenu(lblDesc);
		addControl(txtDescription, "description");

		CheckBoxPanel check = new CheckBoxPanel(contentEditor.grpGeneral);
		check.getCellData().setSpread(2, 1);
		LCheckBox btnSkip = new LCheckBox(check);
		btnSkip.setText(Vocab.instance.SKIPPABLE);
		btnSkip.setHoverText(Tooltip.instance.SKIPPABLE);
        addControl(btnSkip, "skippable");

		LFrame grpEvents = new LFrame(contentEditor, Vocab.instance.EVENTS, Tooltip.instance.EVENTS);
		grpEvents.setFillLayout(true);
		grpEvents.getCellData().setExpand(true, true);
		grpEvents.getCellData().setSpread(2, 1);
		LFlexPanel events = new LFlexPanel(grpEvents);

		EventList lstEvents = new EventList(events);
		lstEvents.getCellData().setExpand(true, true);
		lstEvents.addMenu(grpEvents);
		addChild(lstEvents, "events");

		eventEditor = new EventEditor(events, false);
		eventEditor.getCellData().setExpand(true, true);
		eventEditor.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		lstEvents.addChild(eventEditor);
		eventEditor.addModifyListener(e -> lstEvents.refreshObject(lstEvents.getCollectionWidget().getSelectedPath()));

		events.setWeights(1, 3);

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}

	private class EventList extends SimpleEditableList<Command> {

		public EventList(LContainer parent) {
			super(parent);
			type = Command.class;
			setIncludeID(true);
			getCollectionWidget().setEditEnabled(false);
		}

		@Override
		protected void createContent(int style) {
			list = new LEditableList<>(this, style == 1) {
				@Override
				public LEditEvent<Command> edit(LPath path) {
					return onEditItem(path);
				}
				@Override
				public Command toObject(LPath path) {
					if (path == null || path.index == -1)
						return null;
					return EventList.this.getDataCollection().get(path.index);
				}
				@Override
				public LDataTree<Command> emptyNode() {
					return new LDataTree<>(createNewElement());
				}
				@Override
				public LDataTree<Command> duplicateNode(LDataTree<Command> node) {
					Command data = duplicateElement(node.data);
					return new LDataTree<> (data);
				}
				@Override
				public LDataTree<Command> toNode(LPath path) {
					return EventList.this.getDataCollection().toTree().getNode(path);
				}
				@Override
				protected String encodeNode(LDataTree<Command> node) {
					return EventList.this.encodeElement(node.data);
				}
				@Override
				protected LDataTree<Command> decodeNode(String str) {
					return new LDataTree<>(EventList.this.decodeElement(str));
				}
				@Override
				public boolean isDataChecked(Command data) {
					return EventList.this.isChecked(data);
				}
				@Override
				public LColor dataColor(Command data) {
					return eventEditor.getEventColor(data);
				}

			};
		}

	}

}
