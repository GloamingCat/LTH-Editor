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
import lui.container.*;
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
		grpEvents.setFillLayout(true);
		grpEvents.setHoverText(Tooltip.instance.EVENTS);
		grpEvents.getCellData().setExpand(true, true);
		grpEvents.getCellData().setSpread(2, 1);
		LFlexPanel events = new LFlexPanel(grpEvents);

		SimpleEditableList<EventSheet.Event> lstEvents = new SimpleEditableList<>(events);
		lstEvents.getCellData().setExpand(true, true);
		lstEvents.getCollectionWidget().setEditEnabled(false);
		lstEvents.setIncludeID(false);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.addMenu(grpEvents);
		addChild(lstEvents, "events");

		EventEditor eventEditor = new EventEditor(events, false);
		eventEditor.getCellData().setExpand(true, true);
		lstEvents.addChild(eventEditor);

		events.setWeights(1, 2);

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

			LScrollPanel flowScroll = new LScrollPanel(tabFolder);
			LPanel flowEvents = new LPanel(flowScroll);
			flowEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.FLOWEVENTS, flowScroll);
			new EventButton(flowEvents, "Set Label", "setLabel",
					EventArgsDialog.NAME);
			new EventButton(flowEvents, "Jump To Label", "jumpTo",
					EventArgsDialog.SKIP | EventArgsDialog.NAME);
			new EventButton(flowEvents, "Skip To Event", "setEvent",
					EventArgsDialog.SKIP);
			new EventButton(flowEvents, "Skip Events", "skipEvents",
					EventArgsDialog.SKIP | EventArgsDialog.LIMIT);
			new EventButton(flowEvents, "Wait", "wait",
					EventArgsDialog.WAIT);

			LScrollPanel fieldScroll = new LScrollPanel(tabFolder);
			LPanel fieldEvents = new LPanel(fieldScroll);
			fieldEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.FIELDEVENTS, fieldScroll);
			new EventButton(fieldEvents, "Normal Field Transition", "moveToField",
					EventArgsDialog.FIELD | EventArgsDialog.TILE);
			new EventButton(fieldEvents, "Battle Field Transition", "startBattle",
					EventArgsDialog.FIELD);

			LScrollPanel charScroll = new LScrollPanel(tabFolder);
			LPanel charEvents = new LPanel(charScroll);
			charEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.CHAREVENTS, charScroll);
			new EventButton(charEvents, "Turn to Tile", "turnCharTile",
					EventArgsDialog.KEY | EventArgsDialog.TILE);
			new EventButton(charEvents, "Turn to Direction", "turnCharDir",
					EventArgsDialog.KEY | EventArgsDialog.DIR);
			new EventButton(charEvents, "Move to Tile", "moveCharTile",
					EventArgsDialog.KEY | EventArgsDialog.TILE | EventArgsDialog.LIMIT);
			new EventButton(charEvents, "Move in Direction", "moveCharDir",
					EventArgsDialog.KEY | EventArgsDialog.DIR | EventArgsDialog.LIMIT);
			new EventButton(charEvents, "Set Properties", "hideChar",
					EventArgsDialog.KEY | EventArgsDialog.DEACTIVATE);
			new EventButton(charEvents, "Delete", "deleteChar",
					EventArgsDialog.KEY | EventArgsDialog.DEACTIVATE);
			new EventButton(charEvents, "Play Animation", "playCharAnim",
					EventArgsDialog.KEY | EventArgsDialog.NAME);
			new EventButton(charEvents, "Stop Animation", "stopChar",
					EventArgsDialog.KEY);

			LScrollPanel menuScroll = new LScrollPanel(tabFolder);
			LPanel menuEvents = new LPanel(menuScroll);
			menuEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.MENUEVENTS, menuScroll);
			new EventButton(menuEvents, "Field Menu", "openFieldMenu",
					EventArgsDialog.MENU | EventArgsDialog.FIELD);
			new EventButton(menuEvents, "Shop Menu", "openShopMenu",
					EventArgsDialog.MENU | EventArgsDialog.ITEM);
			new EventButton(menuEvents, "Recruit Menu", "openRecruitMenu",
					EventArgsDialog.MENU);

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
