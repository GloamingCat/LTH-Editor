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
import lui.base.action.LCompoundAction;
import lui.base.action.LControlAction;
import lui.base.data.LDataCollection;
import lui.base.data.LDataList;
import lui.base.event.LControlEvent;
import lui.container.*;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;
import lui.gson.GDefaultObjectEditor;
import lui.widget.*;

import java.lang.reflect.Type;
import java.util.HashMap;

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
		lstEvents.setIncludeID(true);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.addMenu(grpEvents);
		addChild(lstEvents, "events");

		EventEditor eventEditor = new EventEditor(events, false);
		eventEditor.getCellData().setExpand(true, true);
		eventEditor.setMargins(LPrefs.FRAMEMARGIN, LPrefs.FRAMEMARGIN);
		lstEvents.addChild(eventEditor);

		events.setWeights(1, 3);

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}
	
	private static class EventEditor extends GDefaultObjectEditor<EventSheet.Event> {
		LTextBox txtCommand;
		TagList lstParam;
		EventButton currentEventButton;
		HashMap<String, EventButton> eventButtons;

		public EventEditor(LContainer parent, boolean doubleBuffered) {
			super(parent, doubleBuffered);
		}

		@Override
		protected void createContent(int style) {
			setGridLayout(2);
			eventButtons = new HashMap<>();

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
			txtCommand.addModifyListener(e -> refreshCurrentEventButton());
			addControl(txtCommand, "name");

			LLabel lblParam = new LLabel(this, LFlags.TOP, Vocab.instance.PARAM,
					Tooltip.instance.PARAM);
			lstParam = new TagList(this);
			lstParam.getCellData().setExpand(true, true);
			lstParam.addMenu(lblParam);
			addChild(lstParam, "tags");

			LFrame grpCommands = new LFrame(this, Vocab.instance.DEFAULTCOMMANDS);
			grpCommands.setGridLayout(1);
			grpCommands.getCellData().setExpand(true, true);
			grpCommands.getCellData().setSpread(2, 1);

			LViewFolder tabFolder = new LViewFolder(grpCommands, false);
			tabFolder.getCellData().setExpand(true, true);

			LScrollPanel flowScroll = new LScrollPanel(tabFolder);
			LPanel flowEvents = new LPanel(flowScroll);
			flowEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.FLOWEVENTS, flowScroll);
			new EventButton(flowEvents, "Set Local Variable", "setLocalVar",
					EventArgsDialog.VAR | EventArgsDialog.VALUE);
			new EventButton(flowEvents, "Set Global Variable", "setGlobalVar",
					EventArgsDialog.VAR | EventArgsDialog.VALUE);
			new EventButton(flowEvents, "Set Label", "setLabel",
					EventArgsDialog.NAME);
			new EventButton(flowEvents, "Jump To Label", "jumpTo",
					EventArgsDialog.SKIP | EventArgsDialog.NAME);
			new EventButton(flowEvents, "Skip To Event", "setEvent",
					EventArgsDialog.SKIP);
			new EventButton(flowEvents, "Skip Events", "skipEvents",
					EventArgsDialog.SKIP | EventArgsDialog.VALUE);
			new EventButton(flowEvents, "Wait", "wait",
					EventArgsDialog.DURATION);

			LScrollPanel fieldScroll = new LScrollPanel(tabFolder);
			LPanel fieldEvents = new LPanel(fieldScroll);
			fieldEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.FIELDEVENTS, fieldScroll);
			new EventButton(fieldEvents, "Set Field Variable", "setFieldVar",
					EventArgsDialog.VAR | EventArgsDialog.VALUE);
			new EventButton(fieldEvents, "Move to Field", "moveToField",
					EventArgsDialog.FIELD | EventArgsDialog.POS);
			new EventButton(fieldEvents, "Run Battle", "runBattle",
					EventArgsDialog.FIELD);
			new EventButton(fieldEvents, "Field Image", "setupImage",
					EventArgsDialog.NAME | EventArgsDialog.VISIBLE);

			LScrollPanel charScroll = new LScrollPanel(tabFolder);
			LPanel charEvents = new LPanel(charScroll);
			charEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.CHAREVENTS, charScroll);
			new EventButton(charEvents, "Set Character Variable", "setCharVar",
					EventArgsDialog.VAR | EventArgsDialog.VALUE);
			new EventButton(charEvents, "Set Property", "setCharProperty",
					EventArgsDialog.KEY | EventArgsDialog.PROP | EventArgsDialog.VALUE);
			new EventButton(charEvents, "Log Properties", "logProperties",
					EventArgsDialog.KEY | EventArgsDialog.PROP | EventArgsDialog.ALL);
			new EventButton(charEvents, "Character Visibility", "setCharVisibility",
					EventArgsDialog.KEY | EventArgsDialog.PROP | EventArgsDialog.VISIBLE | EventArgsDialog.WAIT);
			new EventButton(charEvents, "Shadow Visibility", "setShadowVisibility",
					EventArgsDialog.KEY | EventArgsDialog.PROP | EventArgsDialog.VISIBLE | EventArgsDialog.WAIT);
			new EventButton(charEvents, "Delete Character", "deleteChar",
					EventArgsDialog.KEY | EventArgsDialog.PROP);
			new EventButton(charEvents, "Reset Character", "resetChar",
					EventArgsDialog.KEY | EventArgsDialog.PROP | EventArgsDialog.ALL);
			new EventButton(charEvents, "Turn to Tile", "turnCharTile",
					EventArgsDialog.KEY | EventArgsDialog.POS);
			new EventButton(charEvents, "Turn to Direction", "turnCharDir",
					EventArgsDialog.KEY | EventArgsDialog.DIR);
			new EventButton(charEvents, "Move to Tile", "moveCharTile",
					EventArgsDialog.KEY | EventArgsDialog.POS | EventArgsDialog.HEIGHT | EventArgsDialog.VALUE | EventArgsDialog.SPEED | EventArgsDialog.WAIT);
			new EventButton(charEvents, "Move in Direction", "moveCharDir",
					EventArgsDialog.KEY | EventArgsDialog.DIR | EventArgsDialog.VALUE | EventArgsDialog.SPEED | EventArgsDialog.WAIT);
			new EventButton(charEvents, "Character Jump", "jumpChar",
					EventArgsDialog.KEY | EventArgsDialog.HEIGHT | EventArgsDialog.SPEED | EventArgsDialog.DURATION | EventArgsDialog.WAIT);
			new EventButton(charEvents, "Play Animation", "playCharAnim",
					EventArgsDialog.KEY | EventArgsDialog.NAME);
			new EventButton(charEvents, "Stop Animation", "stopChar",
					EventArgsDialog.KEY);

			LScrollPanel menuScroll = new LScrollPanel(tabFolder);
			LPanel menuEvents = new LPanel(menuScroll);
			menuEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.MENUEVENTS, menuScroll);
			new EventButton(menuEvents, "Show Dialogue", "showDialogue",
					EventArgsDialog.WINDOW | EventArgsDialog.POS | EventArgsDialog.NAME);
			new EventButton(menuEvents, "Close Dialogue", "closeDialogueWindow",
					EventArgsDialog.WINDOW);
			new EventButton(menuEvents, "Show Title", "openTitleWindow",
					EventArgsDialog.WINDOW | EventArgsDialog.POS);
			new EventButton(menuEvents, "Close Title", "closeTitleWindow",
					EventArgsDialog.WINDOW);
			new EventButton(menuEvents, "Show Message", "showMessage",
					EventArgsDialog.WINDOW | EventArgsDialog.POS);
			new EventButton(menuEvents, "Close Message", "closeMessageWindow",
					EventArgsDialog.WINDOW);
			new EventButton(menuEvents, "Choice Window", "openChoiceWindow",
					EventArgsDialog.WINDOW | EventArgsDialog.INPUT | EventArgsDialog.POS);
			new EventButton(menuEvents, "Input Window", "openStringWindow",
					EventArgsDialog.WINDOW | EventArgsDialog.INPUT | EventArgsDialog.POS | EventArgsDialog.NAME);
			new EventButton(menuEvents, "Password Window", "openNumberWindow",
					EventArgsDialog.WINDOW | EventArgsDialog.INPUT | EventArgsDialog.POS | EventArgsDialog.VALUE);
			new EventButton(menuEvents, "Field Menu", "openFieldMenu",
					EventArgsDialog.MENU);
			new EventButton(menuEvents, "Shop Menu", "openShopMenu",
					EventArgsDialog.MENU | EventArgsDialog.ITEM);
			new EventButton(menuEvents, "Recruit Menu", "openRecruitMenu",
					EventArgsDialog.MENU | EventArgsDialog.FORMATION);
			new EventButton(menuEvents, "HUD Visibility", "setHudVisibility",
					EventArgsDialog.VISIBLE);

			LScrollPanel partyScroll = new LScrollPanel(tabFolder);
			LPanel partyEvents = new LPanel(partyScroll);
			partyEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.PARTYEVENTS, partyScroll);
			new EventButton(partyEvents, "Add Member", "addMember",
					EventArgsDialog.FORMATION | EventArgsDialog.KEY | EventArgsDialog.POS);
			new EventButton(partyEvents, "Remove Member", "hideMember",
					EventArgsDialog.FORMATION | EventArgsDialog.KEY);
			new EventButton(partyEvents, "Use Skill", "useSkill",
					EventArgsDialog.FORMATION | EventArgsDialog.SKILL | EventArgsDialog.ALL);
			new EventButton(partyEvents, "Add Exp", "increaseExp",
					EventArgsDialog.FORMATION | EventArgsDialog.VALUE | EventArgsDialog.ALL);
			new EventButton(partyEvents, "Add Money", "increaseMoney",
					EventArgsDialog.FORMATION | EventArgsDialog.VALUE);
			new EventButton(partyEvents, "Add Item", "increaseItem",
					EventArgsDialog.FORMATION | EventArgsDialog.VALUE | EventArgsDialog.ITEM);
			new EventButton(partyEvents, "Set Equip", "setEquip",
					EventArgsDialog.FORMATION | EventArgsDialog.KEY | EventArgsDialog.ITEM);
			new EventButton(partyEvents, "Set Level", "setLevel",
					EventArgsDialog.FORMATION | EventArgsDialog.KEY | EventArgsDialog.VALUE);
			new EventButton(partyEvents, "Add Skill", "learnSKill",
					EventArgsDialog.FORMATION | EventArgsDialog.KEY | EventArgsDialog.SKILL | EventArgsDialog.ALL);
			new EventButton(partyEvents, "Add Status", "addStatus",
					EventArgsDialog.FORMATION | EventArgsDialog.KEY | EventArgsDialog.ALL);

			LScrollPanel soundScroll = new LScrollPanel(tabFolder);
			LPanel soundEvents = new LPanel(soundScroll);
			soundEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.SOUNDEVENTS, soundScroll);
			new EventButton(soundEvents, "Play SFX", "playSFX",
					EventArgsDialog.AUDIO | EventArgsDialog.WAIT | EventArgsDialog.VALUE | EventArgsDialog.SKILL);
			new EventButton(soundEvents, "Play BGM", "playBGM",
					EventArgsDialog.AUDIO | EventArgsDialog.WAIT | EventArgsDialog.VALUE);
			new EventButton(soundEvents, "Pause BGM", "pauseBGM",
					EventArgsDialog.WAIT | EventArgsDialog.VALUE);
			new EventButton(soundEvents, "Resume BGM", "resumeBGM",
					EventArgsDialog.WAIT | EventArgsDialog.VALUE);
			new EventButton(soundEvents, "Play Field BGM", "playFieldBGM",
					EventArgsDialog.WAIT | EventArgsDialog.VALUE);

			LScrollPanel screenScroll = new LScrollPanel(tabFolder);
			LPanel screenEvents = new LPanel(screenScroll);
			screenEvents.setSequentialLayout(true);
			tabFolder.addTab(Vocab.instance.SCREENEVENTS, screenScroll);
			new EventButton(screenEvents, "Focus On Character", "focusCharacter",
					EventArgsDialog.SPEED | EventArgsDialog.KEY | EventArgsDialog.WAIT | EventArgsDialog.SPEED);
			new EventButton(screenEvents, "Focus On Tile", "focusTile",
					EventArgsDialog.SPEED | EventArgsDialog.POS | EventArgsDialog.HEIGHT | EventArgsDialog.WAIT | EventArgsDialog.SPEED);
			new EventButton(screenEvents, "Fade in", "fadein",
					EventArgsDialog.DURATION | EventArgsDialog.WAIT);
			new EventButton(screenEvents, "Fade out", "fadeout",
					EventArgsDialog.DURATION | EventArgsDialog.WAIT);
			new EventButton(screenEvents, "Apply Shader", "shaderin",
					EventArgsDialog.NAME | EventArgsDialog.DURATION | EventArgsDialog.WAIT);
			new EventButton(screenEvents, "Unapply Shader", "shaderout",
					EventArgsDialog.DURATION | EventArgsDialog.WAIT);
			new EventButton(screenEvents, "Change Color", "colorin",
					EventArgsDialog.COLOR | EventArgsDialog.DURATION | EventArgsDialog.WAIT);

			currentEventButton = new EventButton(grpCommands, "Edit Current", null,0);
			currentEventButton.getCellData().setExpand(true, false);

		}

		@Override
		public void setObject(Object obj) {
			super.setObject(obj);
			refreshCurrentEventButton();
		}

		private void refreshCurrentEventButton() {
			String command = txtCommand.getValue();
			if (command == null) {
				currentEventButton.setValue(null);
				currentEventButton.setEnabled(false);
			} else {
				EventButton eventButton = eventButtons.getOrDefault(command, null);
				if (eventButton == null) {
					currentEventButton.setValue(null);
					currentEventButton.setEnabled(false);
				} else {
					currentEventButton.command = eventButton.command;
					currentEventButton.setShellFactory(eventButton.getShellFactory());
					currentEventButton.setValue(lstParam.getObject());
					currentEventButton.setEnabled(true);
				}
			}
			for (EventButton button : eventButtons.values()) {
				button.setEnabled(command != null);
			}
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
				addModifyListener(e -> {
					LAction action = null;
					if (!lstParam.getDataCollection().equals(e.newValue)) {
						LControlEvent<LDataCollection<Tag>> e1 = new LControlEvent<>(lstParam.getDataCollection().clone(), e.newValue);
						LControlAction<LDataCollection<Tag>> a1 = new LControlAction<>(lstParam, e1);
						a1.apply();
						action = a1;
					}
					if (!txtCommand.getValue().equals(EventButton.this.command)) {
						LControlEvent<String> e2 = new LControlEvent<>(txtCommand.getValue(), EventButton.this.command);
						LControlAction<String> a2 = new LControlAction<>(txtCommand, e2);
						a2.apply();
						if (action != null)
							action = new LCompoundAction(action, a2);
						else
							action = a2;
					}
					if (action != null)
						EventEditor.this.getActionStack().newAction(action);
				});
				if (command == null) {
					setEnabled(false);
					return;
				}
				setShellFactory(new LWindowFactory<>() {
					@Override
					public LObjectDialog<LDataList<Tag>> createWindow(LWindow parent) {
						return new EventArgsDialog(parent, flags, name);
					}
				});
				eventButtons.put(command, this);
			}

			@Override
			protected void onClick() {
				currentValue = lstParam.getDataCollection();
				super.onClick();
			}

			@Override
			protected Type getType() {
				return LDataList.class;
			}

		}

	}

}
