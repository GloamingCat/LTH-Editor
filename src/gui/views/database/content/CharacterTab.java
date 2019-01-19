package gui.views.database.content;

import gson.project.GObjectTreeSerializer;
import gui.Vocab;
import gui.helper.ImageHelper;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.CharTileList;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.PortraitList;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransformEditor;
import gui.widgets.IDButton;
import lwt.dataestructure.LDataTree;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.GameCharacter;
import data.GameCharacter.Portrait;
import data.subcontent.Node;
import project.Project;

public class CharacterTab extends DatabaseTab {

	public CharacterTab(Composite parent, int style) {
		super(parent, style);
		
		Label lblBattler = new Label(grpGeneral, SWT.NONE);
		lblBattler.setText(Vocab.instance.CHARBATTLER);
		
		Composite battler = new Composite(grpGeneral, SWT.NONE);
		battler.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_battler = new GridLayout(2, false);
		gl_battler.marginHeight = 0;
		gl_battler.marginWidth = 0;
		battler.setLayout(gl_battler);
		
		Text txtBattler = new Text(battler, SWT.BORDER | SWT.READ_ONLY);
		txtBattler.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnBattler = new IDButton(battler, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.setNameText(txtBattler);
		addControl(btnBattler, "battlerID");
		
		Label lblShadow = new Label(grpGeneral, SWT.NONE);
		lblShadow.setText(Vocab.instance.SHADOW);
		
		Composite shadow = new Composite(grpGeneral, SWT.NONE);
		shadow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		GridLayout gl_shadow = new GridLayout(2, false);
		gl_shadow.marginHeight = 0;
		gl_shadow.marginWidth = 0;
		shadow.setLayout(gl_shadow);
		
		Text txtShadow = new Text(shadow, SWT.BORDER | SWT.READ_ONLY);
		txtShadow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		IDButton btnShadow = new IDButton(shadow, 1) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnShadow.setNameText(txtShadow);
		addControl(btnShadow, "shadowID");
		
		Group grpTiles = new Group(contentEditor, SWT.NONE);
		grpTiles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTiles.setText(Vocab.instance.COLLIDERTILES);
		grpTiles.setLayout(new FillLayout());
		
		CharTileList lstTiles = new CharTileList(grpTiles, SWT.NONE);
		addChild(lstTiles, "tiles");
		
		Composite middle = new Composite(contentEditor, SWT.NONE);
		middle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		GridLayout gl_middle = new GridLayout(2, false);
		gl_middle.marginHeight = 0;
		gl_middle.marginWidth = 0;
		gl_middle.verticalSpacing = 0;
		middle.setLayout(gl_middle);
		
		Composite lists = new Composite(middle, SWT.NONE);
		GridLayout gl_lists = new GridLayout(1, false);
		gl_lists.marginWidth = 0;
		gl_lists.horizontalSpacing = 0;
		gl_lists.verticalSpacing = 0;
		gl_lists.marginHeight = 0;
		lists.setLayout(gl_lists);
		lists.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		
		Group grpAnimations = new Group(lists, SWT.NONE);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAnimations.setText(Vocab.instance.ANIMATIONS);
		grpAnimations.setLayout(new GridLayout(2, false));
		
		NodeList lstAnim = new NodeList(grpAnimations, SWT.NONE) {
			protected LDataTree<Object> getDataTree() { 
				return Project.current.animations.getTree(); 
			}
		};
		GridData gd_anims = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_anims.widthHint = 128;
		lstAnim.setLayoutData(gd_anims);
		lstAnim.getCollectionWidget().setIncludeID(false);
		addChild(lstAnim, "animations");
		
		LImage imgAnim = new LImage(grpAnimations, SWT.NONE);
		imgAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstAnim.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data != null) {
					Node node = (Node) event.data;
					Animation anim = (Animation) Project.current.animations.getTree().get(node.id);
					if (anim != null) {
						GameCharacter c = (GameCharacter) contentEditor.getObject();
						Rectangle rect = anim.quad.getRectangle();
						ImageHelper.setColorTransform(imgAnim, anim.transform, c.transform);
						imgAnim.setImage(SWTResourceManager.getImage(
								Project.current.imagePath() + anim.quad.path), rect);
					} else {
						imgAnim.setImage((Image) null); 
					}
				} else {
					imgAnim.setImage((Image) null); 
				}
			}
		});
		
		Group grpPortraits = new Group(lists, SWT.NONE);
		grpPortraits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpPortraits.setText(Vocab.instance.PORTRAITS);
		grpPortraits.setLayout(new GridLayout(2, false));
		
		PortraitList lstPortraits = new PortraitList(grpPortraits, SWT.NONE);
		GridData gd_portraits = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_portraits.widthHint = 128;
		lstPortraits.setLayoutData(gd_portraits);
		addChild(lstPortraits, "portraits");
		
		LImage imgPotrait = new LImage(grpPortraits, SWT.NONE);
		imgPotrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		lstPortraits.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data != null) {
					Portrait p = (Portrait) event.data;
					Object obj = Project.current.animations.getTree().get(p.id);
					if (obj == null) {
						imgPotrait.setImage((Image) null);
						return;
					}
					GameCharacter c = (GameCharacter) contentEditor.getObject();
					Animation anim = (Animation) obj;
					ImageHelper.setColorTransform(imgPotrait, anim.transform, c.transform);
					imgPotrait.setImage(Project.current.imagePath() + anim.quad.path, anim.quad.getRectangle());
				} else {
					imgPotrait.setImage((Image) null);
				}
			}
		});
		
		Group grpTransform = new Group(middle, SWT.NONE);
		grpTransform.setText(Vocab.instance.TRANSFORM);
		grpTransform.setLayout(new FillLayout());
		grpTransform.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		TransformEditor transformTab = new TransformEditor(grpTransform, SWT.NONE);
		addChild(transformTab, "transform");
		
		Group grpTags = new Group(middle, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList lstTags = new TagList(grpTags, SWT.NONE);
		addChild(lstTags, "tags");
		
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.characters;
	}

}
