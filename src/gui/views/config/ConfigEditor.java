package gui.views.config;

import gui.Vocab;
import gui.shell.AudioShell;
import gui.shell.FontShell;
import gui.views.common.PositionButton;
import gui.views.common.ScriptButton;
import lwt.action.LActionStack;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LHashTableEditor;
import lwt.editor.LObjectEditor;
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
import org.eclipse.swt.widgets.Text;

import project.Project;
import data.Audio;
import data.Config;

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
		
		Label lblVar = new Label(grpGeneral, SWT.NONE);
		lblVar.setText(Vocab.instance.VARIABLES);
		
		Composite varScript = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_varScript = new GridLayout(2, false);
		gl_varScript.marginWidth = 0;
		gl_varScript.marginHeight = 0;
		varScript.setLayout(gl_varScript);
		varScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Text txtVariables = new Text(varScript, SWT.BORDER | SWT.READ_ONLY);
		txtVariables.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ScriptButton btnVariables = new ScriptButton(varScript, SWT.NONE);
		btnVariables.setFolder("other");
		addControl(btnVariables, "textVariables");
		
		Label lblStartPos = new Label(grpGeneral, SWT.NONE);
		lblStartPos.setText(Vocab.instance.STARTPOS);
		
		Composite startPos = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_startPos = new GridLayout(2, false);
		gl_startPos.marginHeight = 0;
		gl_startPos.marginWidth = 0;
		startPos.setLayout(gl_startPos);
		startPos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Text txtStartpos = new Text(startPos, SWT.BORDER | SWT.READ_ONLY);
		txtStartpos.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		PositionButton btnStartPos = new PositionButton(startPos, SWT.NONE);
		btnStartPos.setText(txtStartpos);
		addControl(btnStartPos, "startPos");
		
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
				return new AudioShell(parent, "sfx");
			}
		});
		addChild(lstSounds);
		
		Group grpFonts = new Group(right, SWT.NONE); 
		grpFonts.setText(Vocab.instance.FONTS);
		grpFonts.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		LHashTableEditor<Config.Font> lstFonts = new LHashTableEditor<Config.Font>(grpFonts, SWT.NONE) {
			public Config.Font createNewValue() {
				return new Config.Font();
			}
			public String getFieldName() {
				return "fonts";
			}
		};
		lstFonts.getTable().setShellFactory(new LShellFactory<Config.Font>() {
			@Override
			public LObjectShell<Config.Font> createShell(Shell parent) {
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
		
		Label lblPixelsheight = new Label(grpGrid, SWT.NONE);
		lblPixelsheight.setText(Vocab.instance.PIXELHEIGHT);
		
		LSpinner spnPPH = new LSpinner(grpGrid, SWT.NONE);
		spnPPH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnPPH, "pixelsPerHeight");
		
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
		
		Composite checkButtons = new Composite(grpGrid, SWT.NONE);
		GridLayout gl_checkButtons = new GridLayout(2, false);
		gl_checkButtons.marginHeight = 0;
		gl_checkButtons.marginWidth = 0;
		checkButtons.setLayout(gl_checkButtons);
		checkButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		LCheckButton btnPixelMovement = new LCheckButton(checkButtons, SWT.CHECK);
		btnPixelMovement.setText(Vocab.instance.PIXELMOV);
		addControl(btnPixelMovement, "pixelMovement");
		
		LCheckButton btnNeighbors = new LCheckButton(checkButtons, SWT.CHECK);
		btnNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		addControl(btnNeighbors, "allNeighbors");
		
		Composite bottom = new Composite(this, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(3, false);
		gl_bottom.marginWidth = 0;
		gl_bottom.marginHeight = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Group grpElements = new Group(bottom, SWT.NONE);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setSize(220, 58);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		NameList lstElements = new NameList(grpElements, SWT.NONE) {
			public String attributeName() {
				return "elements";
			}
		};
		lstElements.setIncludeID(true);		
		addChild(lstElements);
		
		Group grpRegions = new Group(bottom, SWT.NONE);
		grpRegions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRegions.setSize(220, 15);
		grpRegions.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRegions.setText(Vocab.instance.REGIONS);
		
		NameList lstRegions = new NameList(grpRegions, SWT.NONE) {
			public String attributeName() {
				return "regions";
			}
		};
		lstRegions.setIncludeID(true);
		addChild(lstRegions);
		
		Group grpAttributes = new Group(bottom, SWT.NONE);
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAttributes.setSize(220, 15);
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);

		AttributeList lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		lstAttributes.setIncludeID(true);
		addChild(lstAttributes);
	}
	
	public void onVisible() {
		setObject(Project.current.config.getData());
		onChildVisible();
	}
	
}
