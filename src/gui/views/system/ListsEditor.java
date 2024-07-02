package gui.views.system;

import gui.Tooltip;
import gui.Vocab;
import gui.views.database.subcontent.DataList;
import lui.base.LPrefs;
import lui.container.LContainer;
import lui.container.LFrame;
import lui.container.LPanel;
import lui.editor.LObjectEditor;
import project.Project;

public class ListsEditor extends LObjectEditor<Object> {
	
	private ElementList lstElements;
	private EquipTypeList lstEquipTypes;
	private DataList lstLanguages;
	private AttributeList lstAttributes;
	private RegionList lstRegions;
	private PluginList lstPlugins;

	public ListsEditor(LContainer parent) {
		super(parent, true);
	}

	@Override
	protected void createContent(int style) {
		setFillLayout(false);

		createMenuInterface();

		LPanel top = new LPanel(this);
		top.setFillLayout(true);
		top.setSpacing(LPrefs.GRIDSPACING);
		
		LPanel bottom = new LPanel(this);
		bottom.setFillLayout(true);
		bottom.setSpacing(LPrefs.GRIDSPACING);
		
		// Elements
		
		LFrame grpElements = new LFrame(top, Vocab.instance.ELEMENTS);
		grpElements.setFillLayout(true);
		grpElements.setHoverText(Tooltip.instance.ELEMENTS);
		lstElements = new ElementList(grpElements);
		lstElements.setIncludeID(true);
		addChild(lstElements, "elements");
		
		// Regions
		
		LFrame grpRegions = new LFrame(top, Vocab.instance.REGIONS);
		grpRegions.setFillLayout(true);
		grpRegions.setHoverText(Tooltip.instance.REGIONS);
		lstRegions = new RegionList(grpRegions);
		addChild(lstRegions, "regions");

		// Attributes
		
		LFrame grpAttributes = new LFrame(top, Vocab.instance.ATTRIBUTES);
		grpAttributes.setFillLayout(true);
		grpAttributes.setHoverText(Tooltip.instance.ATTRIBUTES);
		lstAttributes = new AttributeList(grpAttributes);
		addChild(lstAttributes, "attributes");
		
		// Variables
		
		LFrame grpVariables = new LFrame(bottom, Vocab.instance.LANGUAGES);
		grpVariables.setFillLayout(true);
		grpVariables.setHoverText(Tooltip.instance.LANGUAGES);
		lstLanguages = new DataList(grpVariables, Vocab.instance.LANGUAGES, false);
		lstLanguages.setIncludeID(true);
		addChild(lstLanguages, "languages");
		
		// Equip types
		
		LFrame grpEquipTypes = new LFrame(bottom, Vocab.instance.EQUIPTYPES);
		grpEquipTypes.setFillLayout(true);
		grpEquipTypes.setHoverText(Tooltip.instance.EQUIPTYPES);
		lstEquipTypes = new EquipTypeList(grpEquipTypes);
		addChild(lstEquipTypes, "equipTypes");
		
		// Plug-ins
		
		LFrame grpPlugins = new LFrame(bottom, Vocab.instance.PLUGINS);
		grpPlugins.setFillLayout(true);
		grpPlugins.setHoverText(Tooltip.instance.PLUGINS);
		lstPlugins = new PluginList(grpPlugins);
		addChild(lstPlugins, "plugins");

	}
	
	public void onVisible() {
		onChildVisible();
		lstAttributes.setObject(Project.current.attributes.getList());
		lstElements.setObject(Project.current.elements.getList());
		lstEquipTypes.setObject(Project.current.equipTypes.getList());
		lstLanguages.setObject(Project.current.languages.getList());
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

	@Override
	public boolean canDecode(String str) {
		return false;
	}
	
}
