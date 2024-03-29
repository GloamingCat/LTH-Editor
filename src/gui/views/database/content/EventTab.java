package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.TagList;
import gui.widgets.SimpleEditableList;
import lwt.editor.LObjectEditor;
import lwt.widget.LLabel;
import lwt.widget.LText;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import data.EventSheet;
import project.Project;

public class EventTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 */
	public EventTab(Composite parent) {
		super(parent);

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 0;
		contentEditor.setLayout(gridLayout);
		
		new LLabel(grpGeneral, Vocab.instance.DESCRIPTION).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtDescription = new LTextBox(grpGeneral);
		GridData gd_txtDescription = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_txtDescription.heightHint = 84;
		txtDescription.setLayoutData(gd_txtDescription);
		addControl(txtDescription, "description");
		
		Group grpTags = new Group(contentEditor, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor, "tags");

		Group grpEvents = new Group(contentEditor, SWT.NONE);
		grpEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		grpEvents.setText(Vocab.instance.EVENTS);
		grpEvents.setLayout(new GridLayout(2, false));
		
		SimpleEditableList<EventSheet.Event> lstEvents = new SimpleEditableList<>(grpEvents, SWT.NONE);
		lstEvents.getCollectionWidget().setEditEnabled(false);
		lstEvents.setIncludeID(false);
		lstEvents.type = EventSheet.Event.class;
		lstEvents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstEvents, "events");
		
		LObjectEditor eventEditor = new LObjectEditor(grpEvents, SWT.NONE);
		eventEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		eventEditor.setLayout(new GridLayout(2, false));
		lstEvents.addChild(eventEditor);
		
		new LLabel(eventEditor, Vocab.instance.COMMAND).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		LTextBox txtCommand = new LTextBox(eventEditor);
		txtCommand.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		eventEditor.addControl(txtCommand, "name");
		
		new LLabel(eventEditor, Vocab.instance.PARAM).setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		
		TagList txtArgs = new TagList(eventEditor, SWT.NONE);
		txtArgs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		eventEditor.addChild(txtArgs, "tags");
		
		new LLabel(eventEditor, Vocab.instance.CONDITION);
		
		LText txtCondition = new LText(eventEditor);
		txtCondition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		eventEditor.addControl(txtCondition, "condition");
		

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.events;
	}

}
