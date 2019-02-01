package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AudioEditor;
import gui.views.database.subcontent.BonusList;
import gui.views.database.subcontent.TagList;
import gui.widgets.IDButton;
import lwt.dataestructure.LDataTree;
import lwt.widget.LCheckButton;
import lwt.widget.LImage;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import project.Project;

public class TerrainTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TerrainTab(Composite parent, int style) {
		super(parent, style);

		Composite right = new Composite(contentEditor,  SWT.NONE);
		right.setLayout(new FillLayout(SWT.VERTICAL));
		GridData gd_right = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2);
		gd_right.widthHint = 224;
		right.setLayoutData(gd_right);
		
		// General
		
		Label lblMoveCost = new Label(grpGeneral, SWT.NONE);
		lblMoveCost.setText(Vocab.instance.MOVECOST);
		
		Composite moveCost = new Composite(grpGeneral, SWT.NONE);
		moveCost.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		GridLayout gl_moveCost = new GridLayout(2, false);
		gl_moveCost.marginHeight = 0;
		gl_moveCost.marginWidth = 0;
		moveCost.setLayout(gl_moveCost);
		
		LSpinner spnCost = new LSpinner(moveCost, SWT.NONE);
		spnCost.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		spnCost.setMinimum(100);
		spnCost.setMaximum(1000);
		addControl(spnCost, "moveCost");
		
		LCheckButton btnPassable = new LCheckButton(moveCost, SWT.CHECK);
		btnPassable.setText(Vocab.instance.PASSABLE);
		addControl(btnPassable, "passable");
		
		Label lblStatus = new Label(grpGeneral, SWT.NONE);
		lblStatus.setText(Vocab.instance.STATUS);
		
		// Status
		
		Composite status = new Composite(grpGeneral, SWT.NONE);
		status.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_status = new GridLayout(2, false);
		gl_status.marginHeight = 0;
		gl_status.marginWidth = 0;
		status.setLayout(gl_status);
	
		Text txtStatus = new Text(status, SWT.BORDER | SWT.READ_ONLY);
		txtStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		IDButton btnStatus = new IDButton(status, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.status.getTree();
			}
		};
		btnStatus.setNameText(txtStatus);
		addControl(btnStatus, "statusID");
		
		new Label(grpGeneral, SWT.NONE);
		LCheckButton btnRemoveOnExit = new LCheckButton(grpGeneral, SWT.NONE);
		btnRemoveOnExit.setText(Vocab.instance.REMOVEONEXIT);
		addControl(btnRemoveOnExit, "removeOnExit");
		
		Label lblLifeTime = new Label(grpGeneral, SWT.NONE);
		lblLifeTime.setText(Vocab.instance.LIFETIME);
		
		LSpinner spnLifeTime = new LSpinner(grpGeneral, SWT.NONE);
		spnLifeTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnLifeTime, "lifeTime");

		// Step Audio
		
		Label lblAudio = new Label(grpGeneral, SWT.NONE);
		lblAudio.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblAudio.setText(Vocab.instance.SOUND);
		
		AudioEditor audioEditor = new AudioEditor(grpGeneral, SWT.NONE);
		audioEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		addChild(audioEditor);
		
		// Attributes
		
		Group grpAtt = new Group(right, SWT.NONE);
		grpAtt.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAtt.setText(Vocab.instance.ATTRIBUTES);
		
		BonusList lstAttributes = new BonusList(grpAtt, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.attributes.getList().toTree();
			}
		};
		addChild(lstAttributes, "attributes");
		
		// Graphics
		
		Group grpGraphics = new Group(contentEditor, SWT.NONE);
		grpGraphics.setLayout(new GridLayout(1, false));
		grpGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGraphics.setText(Vocab.instance.GRAPHICS);
		
		LImage imgGraphics = new LImage(grpGraphics, SWT.NONE);
		imgGraphics.setImage("/javax/swing/plaf/basic/icons/image-delayed.png");
		imgGraphics.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		imgGraphics.setHorizontalAlign(SWT.CENTER);
		imgGraphics.setVerticalAlign(SWT.CENTER);
		
		IDButton btnAnim = new IDButton(grpGraphics, 1) {
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		addControl(btnAnim, "animID");
		btnAnim.setImage(imgGraphics);
		
		// Elements
		
		Group grpElements = new Group(right, SWT.NONE);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		BonusList lstElements = new BonusList(grpElements, SWT.NONE) {
			@Override
			protected LDataTree<Object> getDataTree() {
				return Project.current.elements.getList().toTree();
			}
		};
		addChild(lstElements, "elements");
		
		// Tags
		
		Group grpTags = new Group(right, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");

	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.terrains;
	}

}
