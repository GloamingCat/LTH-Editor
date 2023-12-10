package gui.views.system;

import gui.Vocab;
import gui.views.database.subcontent.TagList;
import lwt.action.LActionStack;
import lwt.container.LContainer;
import lwt.container.LFrame;
import lwt.container.LPanel;
import lwt.editor.LObjectEditor;
import project.Project;

public class ListsEditor extends LObjectEditor<Object> {
	
	private ElementList lstElements;
	private EquipTypeList lstEquipTypes;
	private TagList lstVariables;
	private AttributeList lstAttributes;
	private RegionList lstRegions;
	private PluginList lstPlugins;

	/**
	 * Create the composite.
	 * @param parent
	 */
	public ListsEditor(LContainer parent) {
		super(parent, false, true);

		actionStack = new LActionStack(this);

		LPanel top = new LPanel(this, true, true);
		
		LPanel bottom = new LPanel(this, true, true);
		
		// Elements
		
		LFrame grpElements = new LFrame(top, Vocab.instance.ELEMENTS, true, true);
		lstElements = new ElementList(grpElements);
		lstElements.setIncludeID(true);
		addChild(lstElements, "elements");
		
		// Regions
		
		LFrame grpRegions = new LFrame(top, Vocab.instance.REGIONS, true, true);
		lstRegions = new RegionList(grpRegions);
		addChild(lstRegions, "regions");

		// Attributes
		
		LFrame grpAttributes = new LFrame(top, Vocab.instance.ATTRIBUTES, true, true);
		lstAttributes = new AttributeList(grpAttributes);
		addChild(lstAttributes, "attributes");
		
		// Variables
		
		LFrame grpVariables = new LFrame(bottom, Vocab.instance.VARIABLES, true, true);
		lstVariables = new TagList(grpVariables);
		lstVariables.setIncludeID(true);
		addChild(lstVariables, "variables");
		
		// Equip types
		
		LFrame grpEquipTypes = new LFrame(bottom, Vocab.instance.EQUIPTYPES, true, true);
		lstEquipTypes = new EquipTypeList(grpEquipTypes);
		addChild(lstEquipTypes, "equipTypes");
		
		// Plug-ins
		
		LFrame grpPlugins = new LFrame(bottom, Vocab.instance.PLUGINS, true, true);
		lstPlugins = new PluginList(grpPlugins);
		addChild(lstPlugins, "plugins");

	}
	
	public void onVisible() {
		onChildVisible();
		lstAttributes.setObject(Project.current.attributes.getList());
		lstElements.setObject(Project.current.elements.getList());
		lstEquipTypes.setObject(Project.current.equipTypes.getList());
		lstVariables.setObject(Project.current.variables.getList());
		lstRegions.setObject(Project.current.regions.getList());
		lstPlugins.setObject(Project.current.plugins.getList());
	}

	@Override
	public Object duplicateData(Object obj) {
		return null;
	}

	@Override
	public String encodeData(Object obj) {
		return null;
	}

	@Override
	public Object decodeData(String str) {
		return null;
	}
	
}
