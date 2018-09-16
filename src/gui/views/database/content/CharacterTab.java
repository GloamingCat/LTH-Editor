package gui.views.database.content;

import gui.Vocab;
import gui.views.IDButton;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AnimationList;
import gui.views.database.subcontent.CharTileList;
import gui.views.database.subcontent.PortraitList;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransformEditor;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.GameCharacter.Portrait;
import data.config.Config;
import data.subcontent.Node;
import data.subcontent.Quad;
import project.GObjectTreeSerializer;
import project.Project;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CharacterTab extends DatabaseTab {
	
	private Composite folders;

	public CharacterTab(Composite parent, int style) {
		super(parent, style);
		GridLayout gl_content = new GridLayout(2, false);
		gl_content.verticalSpacing = 0;
		contentEditor.setLayout(gl_content);
		
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
		
		IDButton btnBattler = new IDButton(battler, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.battlers.getTree();
			}
		};
		btnBattler.optional = true;
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
		
		IDButton btnShadow = new IDButton(shadow, SWT.NONE) {
			@Override
			public LDataTree<Object> getDataTree() {
				return Project.current.animations.getTree();
			}
		};
		btnShadow.optional = true;
		btnShadow.setNameText(txtShadow);
		addControl(btnShadow, "shadowID");
		
		Group grpTiles = new Group(contentEditor, SWT.NONE);
		grpTiles.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTiles.setText(Vocab.instance.COLLIDERTILES);
		grpTiles.setLayout(new FillLayout());
		
		CharTileList lstTiles = new CharTileList(grpTiles, SWT.NONE);
		lstTiles.attributeName = "tiles";
		addChild(lstTiles);
		
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
		
		AnimationList lstAnim = new AnimationList(grpAnimations, SWT.NONE);
		GridData gd_anims = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_anims.widthHint = 128;
		lstAnim.setLayoutData(gd_anims);
		lstAnim.attributeName = "animations";
		lstAnim.getCollectionWidget().setIncludeID(false);
		addChild(lstAnim);
		
		LImage imgAnim = new LImage(grpAnimations, SWT.NONE);
		imgAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lstAnim.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				if (event.data != null) {
					Node node = (Node) event.data;
					Animation anim = (Animation) Project.current.animations.getTree().get(node.id);
					if (anim != null) {
						Rectangle rect = anim.quad.getRectangle();
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
		
		folders = new Composite(grpAnimations, SWT.NONE);
		RowLayout rl_folders = new RowLayout(SWT.HORIZONTAL);
		rl_folders.marginRight = 0;
		rl_folders.marginBottom = 0;
		rl_folders.marginLeft = 0;
		rl_folders.marginTop = 0;
		folders.setLayout(rl_folders);
		folders.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		Button btnDefault = new Button(folders, SWT.RADIO);
		btnDefault.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				lstAnim.folderName = "Default";
				lstAnim.setObject(contentEditor.getObject());
			}
		});
		btnDefault.setText(Vocab.instance.DEFAULT);
		btnDefault.setSelection(true);
		
		Button btnBattle = new Button(folders, SWT.RADIO);
		btnBattle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				lstAnim.folderName = "Battle";
				lstAnim.setObject(contentEditor.getObject());
			}
		});
		
		btnBattle.setText(Vocab.instance.BATTLE);
		
		Group grpPortraits = new Group(lists, SWT.NONE);
		grpPortraits.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpPortraits.setText(Vocab.instance.PORTRAITS);
		grpPortraits.setLayout(new GridLayout(2, false));
		
		PortraitList lstPortraits = new PortraitList(grpPortraits, SWT.NONE);
		GridData gd_portraits = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_portraits.widthHint = 128;
		lstPortraits.setLayoutData(gd_portraits);
		lstPortraits.attributeName = "portraits";
		addChild(lstPortraits);
		
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
					Quad quad = ((Animation) obj).quad;
					imgPotrait.setImage(Project.current.imagePath() + quad.path, quad.getRectangle());
				} else {
					imgPotrait.setImage((Image) null);
				}
			}
		});
		
		TransformEditor transformTab = new TransformEditor(middle, SWT.NONE);
		transformTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		addChild(transformTab);
		
		Group grpTags = new Group(middle, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
	}
	
	public void onVisible() {
		Control[] buttons = folders.getChildren();
		for (int i = 2; i < buttons.length; i++) {
			buttons[i].dispose();
		}
		//Config conf = Project.current.config.getData();
		//for (String folder : conf.animations.sets) {
			// create button
		//}
		super.onVisible();
	}

	@Override
	protected GObjectTreeSerializer getSerializer() {
		return Project.current.characters;
	}

}
