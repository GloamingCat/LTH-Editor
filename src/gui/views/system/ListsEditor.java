package gui.views.system;

import gui.Vocab;
import gui.widgets.ConstantList;
import gui.widgets.NameList;
import lwt.action.LActionStack;
import lwt.editor.LObjectEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import project.Project;

public class ListsEditor extends LObjectEditor {
	
	private NameList lstElements;
	private EquipTypeList lstEquipTypes;
	private ConstantList lstConstants;
	private AttributeList lstAttributes;
	private RegionList lstRegions;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ListsEditor(Composite parent, int style) {
		super(parent, style);

		actionStack = new LActionStack(this);
		setLayout(new FillLayout(SWT.VERTICAL));

		Composite top = new Composite(this, SWT.NONE);
		Composite bottom = new Composite(this, SWT.NONE);
		top.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Elements
		
		Group grpElements = new Group(top, SWT.NONE);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		lstElements = new NameList(grpElements, SWT.NONE);
		lstElements.setIncludeID(true);
		addChild(lstElements, "elements");
		
		// Regions
		
		Group grpRegions = new Group(top, SWT.NONE);
		grpRegions.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRegions.setText(Vocab.instance.REGIONS);
		
		lstRegions = new RegionList(grpRegions, SWT.NONE);
		addChild(lstRegions, "regions");

		// Attributes
		
		Group grpAttributes = new Group(top, SWT.NONE);
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);

		lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		addChild(lstAttributes, "attributes");
		bottom.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Constants
		
		Group grpConstants = new Group(bottom, SWT.NONE);
		grpConstants.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpConstants.setText(Vocab.instance.CONSTANTS);

		lstConstants = new ConstantList(grpConstants, SWT.NONE);
		addChild(lstConstants, "constants");
		
		Group grpEquipTypes = new Group(bottom, SWT.NONE);
		grpEquipTypes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpEquipTypes.setText(Vocab.instance.EQUIPTYPES);
		
		lstEquipTypes = new EquipTypeList(grpEquipTypes, SWT.NONE);
		addChild(lstEquipTypes, "equipTypes");
		
		Group grpPlugins = new Group(bottom, SWT.NONE);
		grpPlugins.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpPlugins.setText(Vocab.instance.PLUGINS);

	}
	
	public void onVisible() {
		onChildVisible();
		lstAttributes.setObject(Project.current.attributes.getList());
		lstElements.setObject(Project.current.elements.getList());
		lstEquipTypes.setObject(Project.current.equipTypes.getList());
		lstConstants.setObject(Project.current.constants.getList());
		lstRegions.setObject(Project.current.regions.getList());
		
		//lstPlugins.setObject(Project.current.plugins.getList());
	}
	
}
