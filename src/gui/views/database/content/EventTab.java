package gui.views.database.content;

import data.subcontent.Tag;
import gson.GObjectTreeSerializer;
import gui.Tooltip;
import gui.Vocab;
import gui.shell.database.EventArgsDialog;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;
import lui.base.LFlags;
import lui.base.LPrefs;
import lui.base.action.LAction;
import lui.base.data.LDataList;
import lui.base.event.LControlEvent;
import lui.base.event.listener.LSelectionListener;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.container.LViewFolder;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.gson.GDefaultObjectEditor;
import lui.widget.*;

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
		LTextBox txtCommand;
		TagList lstParam;

		public EventEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}

		@Override
		protected void createContent(int style) {
			setGridLayout(2);

			LLabel lblCondition = new LLabel(this, Vocab.instance.CONDITION,
					Tooltip.instance.CONDITION);
			LText txtCondition = new LText(this);
			txtCondition.getCellData().setExpand(true, false);
			txtCondition.addMenu(lblCondition);
			addControl(txtCondition, "condition");

			LLabel lblCmd = new LLabel(this, LFlags.TOP, Vocab.instance.COMMAND,
					Tooltip.instance.COMMAND);
			txtCommand = new LTextBox(this);
			txtCommand.getCellData().setExpand(true, true);
			txtCommand.addMenu(lblCmd);
			addControl(txtCommand, "name");

			LLabel lblParam = new LLabel(this, LFlags.TOP, Vocab.instance.PARAM,
					Tooltip.instance.PARAM);
			lstParam = new TagList(this);
			lstParam.getCellData().setExpand(true, true);
			lstParam.addMenu(lblParam);
			addChild(lstParam, "tags");

			LFrame commands = new LFrame(this, Vocab.instance.DEFAULTCOMMANDS);
			commands.setFillLayout(true);
			commands.getCellData().setExpand(true, true);
			commands.getCellData().setSpread(2, 1);

			LViewFolder tabFolder = new LViewFolder(commands, false);

			LPanel fieldEvents = new LPanel(tabFolder);
			fieldEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.FIELDEVENTS, fieldEvents);
			new EventButton(fieldEvents, "Normal Field Transition", "moveToField",
					EventArgsDialog.FIELD | EventArgsDialog.TILE);
			new EventButton(fieldEvents, "Battle Field Transition", "startBattle",
					EventArgsDialog.FIELD);

			LPanel charEvents = new LPanel(tabFolder);
			charEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.CHAREVENTS, charEvents);
			new EventButton(charEvents, "Move to Tile", "moveCharTile",
					EventArgsDialog.KEY | EventArgsDialog.TILE | EventArgsDialog.LIMIT);
			new EventButton(charEvents, "Move in Direction", "moveCharDir",
					EventArgsDialog.KEY | EventArgsDialog.DIR | EventArgsDialog.LIMIT);
			new EventButton(charEvents, "Turn to Tile", "turnCharTile",
					EventArgsDialog.KEY | EventArgsDialog.TILE);
			new EventButton(charEvents, "Turn to Direction", "turnCharDir",
					EventArgsDialog.KEY | EventArgsDialog.DIR);

			//tabFolder.addTab(Vocab.instance.FLOWEVENT);
			//tabFolder.addTab(Vocab.instance.MENUEVENT);
			//tabFolder.addTab(Vocab.instance.PARTYEVENT);
			//tabFolder.addTab(Vocab.instance.SCREENEVENT);
			//tabFolder.addTab(Vocab.instance.SOUNDEVENT);


		}

		@Override
		public Type getType() {
			return EventSheet.Event.class;
		}

		private class EventButton extends LObjectButton<LDataList<Tag>> {

			String command;

			public EventButton(LContainer parent, String name, String command, int flags) {
				super(parent);
				this.command = command;
				getCellData().setTargetSize(2 * LPrefs.BUTTONWIDTH, LPrefs.WIDGETHEIGHT);
				setText(name);
				setShellFactory(new LWindowFactory<>() {
					@Override
					public LObjectDialog<LDataList<Tag>> createWindow(LWindow parent) {
						return new EventArgsDialog(parent, flags, name);
					}
				});
				addModifyListener(e -> {
					LControlEvent<String> e2 = new LControlEvent<>(txtCommand.getValue(), command);
					e.oldValue = lstParam.getDataCollection().clone();
					LAction action = new LAction() {
						@Override
						public void undo() {
							lstParam.setValue(e.oldValue);
							txtCommand.setValue(e2.oldValue);
						}
						@Override
						public void redo() {
							lstParam.setValue(e.newValue);
							txtCommand.setValue(e2.newValue);
						}
					};
					action.redo();
					EventEditor.this.getActionStack().newAction(action);
				});
				LSelectionListener onClick = button.onClick;
				button.onClick = e -> {
					currentValue = lstParam.getDataCollection();
					onClick.onSelect(e);
				};
			}

			@Override
			protected Type getType() {
				return LDataList.class;
			}

		}

	}

}
