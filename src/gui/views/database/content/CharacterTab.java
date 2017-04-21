package gui.views.database.content;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.TransformButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.CharTileListButton;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.views.database.subcontent.TagList;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LImage;
import lwt.widget.LSpinner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.GameCharacter.Portrait;
import data.Node;
import project.Project;

public abstract class CharacterTab extends DatabaseTab {

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
		
		SashForm middle = new SashForm(contentEditor, SWT.HORIZONTAL);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		SashForm lists = new SashForm(middle, SWT.VERTICAL);
		lists.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpAnimations = new Group(lists, SWT.NONE);
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
		lstAnim.getCollectionWidget().setIncludeID(false);
		lstAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstAnim);

		TransformButton btnTransform = new TransformButton(anim, SWT.NONE);
		btnTransform.setText(Vocab.instance.TRANSFORM);
		addControl(btnTransform, "animXform");
		
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
		
		Group grpPortraits = new Group(lists, SWT.NONE);
		grpPortraits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpPortraits.setText(Vocab.instance.PORTRAITS);
		grpPortraits.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm frmPortrait = new SashForm(grpPortraits, SWT.HORIZONTAL);
		Composite portrait = new Composite(frmPortrait, SWT.NONE);
		portrait.setLayout(new GridLayout(1, false));
		
		PortraitList lstPortrait = new PortraitList(portrait, SWT.NONE);
		lstPortrait.attributeName = "portraits";
		lstPortrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstPortrait);
		
		GridData gd_imgPortrait = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_imgPortrait.widthHint = 128;		
		TransformButton btnPortraitXform = new TransformButton(portrait, SWT.NONE);
		btnPortraitXform.setText(Vocab.instance.TRANSFORM);
		addControl(btnPortraitXform, "portraitXform");
		
		LImage imgPotrait = new LImage(frmPortrait, SWT.NONE);
		imgPotrait.setLayoutData(gd_imgPortrait);
		lists.setWeights(new int[] {204, 149});

		lstPortrait.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data != null) {
					Portrait p = (Portrait) event.data;
					imgPotrait.setImage(Project.current.imagePath() + p.quad.imagePath, p.quad.getRectangle());
				} else {
					imgPotrait.setImage((Image) null);
				}
			}
		});
		
		Group grpTags = new Group(middle, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		middle.setWeights(new int[] {322, 105});
		
	}

}
