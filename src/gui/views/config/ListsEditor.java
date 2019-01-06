package gui.views.config;

import gui.Vocab;
import gui.widgets.ConstantList;
import gui.widgets.NameList;
import lwt.action.LActionStack;
import lwt.editor.LObjectEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.Project;

public class ListsEditor extends LObjectEditor {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ListsEditor(Composite parent, int style) {
		super(parent, style);

		actionStack = new LActionStack(this);
		
		setLayout(new GridLayout(3, true));

		// Elements
		
		Group grpElements = new Group(this, SWT.NONE);
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		NameList lstElements = new NameList(grpElements, SWT.NONE);
		lstElements.setIncludeID(true);		
		addChild(lstElements, "elements");
		
		// Regions
		
		Group grpRegions = new Group(this, SWT.NONE);
		grpRegions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpRegions.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRegions.setText(Vocab.instance.REGIONS);
		
		RegionList lstRegions = new RegionList(grpRegions, SWT.NONE);
		addChild(lstRegions, "regions");

		// Attributes
		
		Group grpAttributes = new Group(this, SWT.NONE);
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);

		AttributeList lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		addChild(lstAttributes, "attributes");
		
		// Constants
		
		Group grpConstants = new Group(this, SWT.NONE);
		grpConstants.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpConstants.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpConstants.setText(Vocab.instance.CONSTANTS);

		ConstantList lstConstants = new ConstantList(grpConstants, SWT.NONE);
		addChild(lstConstants, "constants");
		
		Group grpEquipTypes = new Group(this, SWT.NONE);
		grpEquipTypes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpEquipTypes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpEquipTypes.setText(Vocab.instance.EQUIPTYPES);
		
		EquipTypeList lstEquipTypes = new EquipTypeList(grpEquipTypes, SWT.NONE);
		addChild(lstEquipTypes, "equipTypes");
		
		Group grpPlugins = new Group(this, SWT.NONE);
		grpPlugins.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpPlugins.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpPlugins.setText(Vocab.instance.PLUGINS);

	}
	
	public void onVisible() {
		setObject(Project.current.config.getData());
		onChildVisible();
	}
	
}
