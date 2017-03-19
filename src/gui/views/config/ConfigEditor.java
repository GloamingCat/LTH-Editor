package gui.views.config;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.IDList;
import lwt.action.LActionStack;
import lwt.editor.LObjectEditor;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import project.Project;

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
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
		
		GridButton btnGrid = new GridButton(composite, SWT.NONE);
		btnGrid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnGrid, "grid");
		btnGrid.setText(Vocab.instance.GRID);
		
		PlayerButton btnPlayer = new PlayerButton(composite, SWT.NONE);
		btnPlayer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnPlayer, "player");
		btnPlayer.setText(Vocab.instance.PLAYER);
		
		BattleButton btnBattle = new BattleButton(composite, SWT.NONE);
		btnBattle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnBattle, "battle");
		btnBattle.setText(Vocab.instance.BATTLE);
		
		GUIButton btnGUI = new GUIButton(composite, SWT.NONE);
		btnGUI.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(btnGUI, "gui");
		btnGUI.setText(Vocab.instance.GUI);
		
		Group grpParty = new Group(this, SWT.NONE);
		grpParty.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpParty.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpParty.setText(Vocab.instance.PARTY);
		
		IDList lstMembers = new IDList(grpParty, SWT.NONE) {
			public ArrayList<?> comboArray() { return Project.current.battlers.getList(); }
		};
		lstMembers.attributeName = "initialMembers";
		addChild(lstMembers);
		
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
		
		Group grpBattlerTypes = new Group(bottom1, SWT.NONE);
		grpBattlerTypes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpBattlerTypes.setLayout(new FillLayout());
		grpBattlerTypes.setText(Vocab.instance.BATTLERTYPES);
		
		BattlerTypeList lstBattlerTypes = new BattlerTypeList(grpBattlerTypes, SWT.NONE);
		lstBattlerTypes.attributeName = "battlerTypes";
		lstBattlerTypes.setIncludeID(true);		
		addChild(lstBattlerTypes);
		
		Group grpRegions = new Group(bottom2, SWT.NONE);
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
		
	}
	
	public void onVisible() {
		setObject(Project.current.config.getData());
		onChildVisible();
	}
	
}
