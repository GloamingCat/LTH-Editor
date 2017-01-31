package gui.views.config;

import gui.Vocab;
import gui.helper.FieldHelper;
import gui.helper.FieldPainter;
import gui.shell.AudioShell;
import gui.shell.config.FontShell;
import gui.views.database.subcontent.TagList;
import lwt.action.LActionStack;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LHashTableEditor;
import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.Audio;
import data.FontData;

public class ConfigEditor extends LObjectEditor {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConfigEditor(Composite parent, int style) {
		super(parent, style);

		actionStack = new LActionStack(this);
		
		setLayout(new GridLayout(2, true));
		
		Group grpGeneral = new Group(this, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		grpGeneral.setLayout(new GridLayout(2, false));
		
		Label lblProjectName = new Label(grpGeneral, SWT.NONE);
		lblProjectName.setText(Vocab.instance.PROJECTNAME);
		
		LText txtName = new LText(grpGeneral, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		
		Label lblPropertes = new Label(grpGeneral, SWT.NONE);
		lblPropertes.setText(Vocab.instance.PROPERTIES);
		
		Composite composite = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_composite = new GridLayout(4, true);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		PlayerButton btnPlayer = new PlayerButton(composite, SWT.NONE);
		btnPlayer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnPlayer, "player");
		btnPlayer.setText(Vocab.instance.PLAYER);
		
		PartyButton btnParty = new PartyButton(composite, SWT.NONE);
		btnParty.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnParty, "party");
		btnParty.setText(Vocab.instance.PARTY);
		
		BattleButton btnBattle = new BattleButton(composite, SWT.NONE);
		btnBattle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnBattle, "battle");
		btnBattle.setText(Vocab.instance.BATTLE);
		
		GUIButton btnGUI = new GUIButton(composite, SWT.NONE);
		btnGUI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnGUI, "gui");
		btnGUI.setText(Vocab.instance.GUI);

		SashForm right = new SashForm(this, SWT.VERTICAL);
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		
		Group grpSounds = new Group(right, SWT.NONE);
		grpSounds.setText(Vocab.instance.SOUNDS);
		grpSounds.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		LHashTableEditor<Audio> lstSounds = new LHashTableEditor<Audio>(grpSounds, SWT.NONE) {
			public Audio createNewValue() {
				return new Audio();
			}
			public String getFieldName() {
				return "sounds";
			}
		};
		lstSounds.getTable().setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(Shell parent) {
				return new AudioShell(parent, "sfx", false);
			}
		});
		addChild(lstSounds);
		
		Group grpFonts = new Group(right, SWT.NONE); 
		grpFonts.setText(Vocab.instance.FONTS);
		grpFonts.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		LHashTableEditor<FontData> lstFonts = new LHashTableEditor<FontData>(grpFonts, SWT.NONE) {
			public FontData createNewValue() {
				return new FontData();
			}
			public String getFieldName() {
				return "fonts";
			}
		};
		lstFonts.getTable().setShellFactory(new LShellFactory<FontData>() {
			@Override
			public LObjectShell<FontData> createShell(Shell parent) {
				return new FontShell(parent);
			}
		});
		addChild(lstFonts);
		
		Group grpGrid = new Group(this, SWT.NONE);
		grpGrid.setLayout(new GridLayout(2, false));
		grpGrid.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpGrid.setText(Vocab.instance.GRID);
		
		Label lblTileWidth = new Label(grpGrid, SWT.NONE);
		lblTileWidth.setText(Vocab.instance.TILEWIDTH);
		
		LSpinner spnTileW = new LSpinner(grpGrid, SWT.NONE);
		spnTileW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileW, "tileW");
		
		Label lblTileHeight = new Label(grpGrid, SWT.NONE);
		lblTileHeight.setText(Vocab.instance.TILEHEIGHT);
		
		LSpinner spnTileH = new LSpinner(grpGrid, SWT.NONE);
		spnTileH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileH, "tileH");
		
		Label lblTileBase = new Label(grpGrid, SWT.NONE);
		lblTileBase.setText(Vocab.instance.TILEBASE);
		
		LSpinner spnTileB = new LSpinner(grpGrid, SWT.NONE);
		spnTileB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileB, "tileB");
		
		Label lblTileSide = new Label(grpGrid, SWT.NONE);
		lblTileSide.setText(Vocab.instance.TILESIDE);
		
		LSpinner spnTileS = new LSpinner(grpGrid, SWT.NONE);
		spnTileS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileS, "tileS");
		
		Label lblPixelsheight = new Label(grpGrid, SWT.NONE);
		lblPixelsheight.setText(Vocab.instance.PIXELHEIGHT);
		
		LSpinner spnPPH = new LSpinner(grpGrid, SWT.NONE);
		spnPPH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnPPH, "pixelsPerHeight");
		
		LControlListener<Integer> tileListener = new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				FieldHelper.reloadMath();
				FieldPainter.reload();
			}
		};
		
		spnTileW.addModifyListener(tileListener);
		spnTileH.addModifyListener(tileListener);
		spnTileB.addModifyListener(tileListener);
		spnTileS.addModifyListener(tileListener);
		
		Composite checkButtons = new Composite(grpGrid, SWT.NONE);
		GridLayout gl_checkButtons = new GridLayout(2, false);
		gl_checkButtons.marginHeight = 0;
		gl_checkButtons.marginWidth = 0;
		checkButtons.setLayout(gl_checkButtons);
		checkButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));

		LCheckButton btnNeighbors = new LCheckButton(checkButtons, SWT.CHECK);
		btnNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		addControl(btnNeighbors, "allNeighbors");
		
		SashForm bottom = new SashForm(this, SWT.VERTICAL);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		Composite bottom1 = new Composite(bottom, SWT.NONE);
		GridLayout gl_bottom1 = new GridLayout(3, false);
		gl_bottom1.marginWidth = 0;
		gl_bottom1.marginHeight = 0;
		bottom1.setLayout(gl_bottom1);
		bottom1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Composite bottom2 = new Composite(bottom, SWT.NONE);
		GridLayout gl_bottom2 = new GridLayout(3, false);
		gl_bottom2.marginWidth = 0;
		gl_bottom2.marginHeight = 0;
		bottom2.setLayout(gl_bottom2);
		bottom2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpElements = new Group(bottom1, SWT.NONE);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		NameList lstElements = new NameList(grpElements, SWT.NONE);
		lstElements.attributeName = "elements";
		lstElements.setIncludeID(true);		
		addChild(lstElements);
		
		Group grpItemTypes = new Group(bottom1, SWT.NONE);
		grpItemTypes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpItemTypes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpItemTypes.setText(Vocab.instance.ITEMTYPES);
		
		NameList lstItemTypes = new NameList(grpItemTypes, SWT.NONE);
		lstItemTypes.attributeName = "itemTypes";
		lstItemTypes.setIncludeID(true);		
		addChild(lstItemTypes);
		
		Group grpRegions = new Group(bottom1, SWT.NONE);
		grpRegions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRegions.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRegions.setText(Vocab.instance.REGIONS);
		
		RegionList lstRegions = new RegionList(grpRegions, SWT.NONE);
		addChild(lstRegions);

		Group grpAttributes = new Group(bottom2, SWT.NONE);
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);

		AttributeList lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		addChild(lstAttributes);
		
		Group grpAtlas = new Group(bottom2, SWT.NONE);
		grpAtlas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAtlas.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAtlas.setText(Vocab.instance.IMAGEATLASES);

		ImageAtlasList lstAtlas = new ImageAtlasList(grpAtlas, SWT.NONE);
		addChild(lstAtlas);
		
		Group grpTags = new Group(bottom2, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
	}
	
	public void onVisible() {
		setObject(Project.current.config.getData());
		onChildVisible();
	}
	
}
