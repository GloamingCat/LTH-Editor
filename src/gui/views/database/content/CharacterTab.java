package gui.views.database.content;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.TransformButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.CharTileListButton;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.ScriptList;
import gui.views.database.subcontent.TagList;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.Node;
import project.GObjectListSerializer;
import project.Project;

public class CharacterTab extends DatabaseTab {

	public CharacterTab(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = (GridLayout) contentEditor.getLayout();
		gridLayout.verticalSpacing = 0;
		
		Composite stuff = new Composite(grpGeneral, SWT.NONE);
		stuff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 3));
		GridLayout gl_stuff = new GridLayout(4, false);
		gl_stuff.marginHeight = 0;
		gl_stuff.marginWidth = 0;
		stuff.setLayout(gl_stuff);

		Label lblColliderHeight = new Label(stuff, SWT.NONE);
		lblColliderHeight.setText(Vocab.instance.COLLIDERHEIGHT);
		
		LSpinner spnHeight = new LSpinner(stuff, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnHeight, "colliderHeight");
		
		Label lblCollisionTiles = new Label(stuff, SWT.NONE);
		lblCollisionTiles.setText(Vocab.instance.COLLISIONTILES);
		
		CharTileListButton btnTiles = new CharTileListButton(stuff, SWT.NONE);
		btnTiles.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		addControl(btnTiles, "tiles");
		
		SashForm sashForm = new SashForm(contentEditor, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		SashForm center = new SashForm(sashForm, SWT.NONE);
		center.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpAnimations = new Group(center, SWT.NONE);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAnimations.setText(Vocab.instance.ANIMATIONS);
		grpAnimations.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm frmAnim = new SashForm(grpAnimations, SWT.HORIZONTAL);
		Composite anim = new Composite(frmAnim, SWT.NONE);
		anim.setLayout(new GridLayout(1, false));
		
		NodeList lstAnim = new NodeList(anim, SWT.NONE) {
			@Override
			protected ArrayList<?> comboArray() {
				return Project.current.animCharacter.getList();
			}
		};
		lstAnim.attributeName = "animations";
		lstAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstAnim);

		TransformButton btnTransform = new TransformButton(anim, SWT.NONE);
		btnTransform.setText(Vocab.instance.TRANSFORM);
		addControl(btnTransform, "transform");
		
		Label image = new Label(frmAnim, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_image.widthHint = 128;
		image.setLayoutData(gd_image);
		lstAnim.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data != null) {
					Node node = (Node) event.data;
					Animation anim = (Animation) Project.current.animCharacter.getList().get(node.id);
					image.setImage(SWTResourceManager.getImage(Project.current.imagePath() + anim.imagePath));
				} else {
					image.setImage(null);
				}
			}
		});
		
		Group grpTags = new Group(center, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		center.setWeights(new int[] {2, 1});
		
		Composite bottom = new Composite(sashForm, SWT.NONE);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		GridLayout gl_bottom = new GridLayout(3, true);
		gl_bottom.marginWidth = 0;
		gl_bottom.marginHeight = 0;
		bottom.setLayout(gl_bottom);
		
		Group grpStart = new Group(bottom, SWT.NONE);
		grpStart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpStart.setLayout(new FillLayout());
		grpStart.setText(Vocab.instance.STARTLISTENERS);
		
		ScriptList lstStart = new ScriptList(grpStart, SWT.NONE);
		lstStart.attributeName = "startListeners";
		lstStart.folderName = "character";
		addChild(lstStart);
		
		Group grpCollision = new Group(bottom, SWT.NONE);
		grpCollision.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCollision.setLayout(new FillLayout());
		grpCollision.setText(Vocab.instance.COLLISIONLISTENERS);
		
		ScriptList lstCollision = new ScriptList(grpCollision, SWT.NONE);
		lstCollision.attributeName = "collisionListeners";
		lstCollision.folderName = "character";
		addChild(lstCollision);
		
		Group grpInteract = new Group(bottom, SWT.NONE);
		grpInteract.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpInteract.setLayout(new FillLayout());
		grpInteract.setText(Vocab.instance.INTERACTLISTENERS);
		
		ScriptList lstInteract = new ScriptList(grpInteract, SWT.NONE);
		lstInteract.attributeName = "interactListeners";
		lstInteract.folderName = "character";
		addChild(lstInteract);
	}

	@Override
	protected GObjectListSerializer getSerializer() {
		return Project.current.characters;
	}


}
