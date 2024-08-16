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
	private SoundList lstSounds;
	private PluginList lstPlugins;

	public ListsEditor(LContainer parent) {
		super(parent, true);
	}

	@Override
	protected void createContent(int style) {
		setFillLayout(true);
		setSpacing(LPrefs.GRIDSPACING);

		createMenuInterface();

		LPanel left = new LPanel(this);
		left.setFillLayout(false);

		LPanel middle = new LPanel(this);
		middle.setFillLayout(false);

		LPanel middle2 = new LPanel(this);
		middle2.setFillLayout(false);

		LPanel right = new LPanel(this);
		right.setFillLayout(false);

		// Elements
		
		LFrame grpElements = new LFrame(left, Vocab.instance.ELEMENTS);
		grpElements.setFillLayout(true);
		grpElements.setHoverText(Tooltip.instance.ELEMENTS);
		lstElements = new ElementList(grpElements);
		lstElements.setIncludeID(true);
		addChild(lstElements, "elements");

		// Attributes

		LFrame grpAttributes = new LFrame(left, Vocab.instance.ATTRIBUTES);
		grpAttributes.setFillLayout(true);
		grpAttributes.setHoverText(Tooltip.instance.ATTRIBUTES);
		lstAttributes = new AttributeList(grpAttributes);
		addChild(lstAttributes, "attributes");

		// Regions
		
		LFrame grpRegions = new LFrame(middle, Vocab.instance.REGIONS);
		grpRegions.setFillLayout(true);
		grpRegions.setHoverText(Tooltip.instance.REGIONS);
		lstRegions = new RegionList(grpRegions);
		addChild(lstRegions, "regions");

		// Equip types
		
		LFrame grpEquipTypes = new LFrame(middle, Vocab.instance.EQUIPTYPES);
		grpEquipTypes.setFillLayout(true);
		grpEquipTypes.setHoverText(Tooltip.instance.EQUIPTYPES);
		lstEquipTypes = new EquipTypeList(grpEquipTypes);
		addChild(lstEquipTypes, "equipTypes");

		// Languages

		LFrame grpLangs = new LFrame(middle, Vocab.instance.LANGUAGES);
		grpLangs.setFillLayout(true);
		grpLangs.setHoverText(Tooltip.instance.LANGUAGES);
		lstLanguages = new DataList(grpLangs, Vocab.instance.LANGUAGES, null,true);
		lstLanguages.setIncludeID(true);
		addChild(lstLanguages, "languages");

		// Sounds

		LFrame grpSounds = new LFrame(middle2, Vocab.instance.SOUNDS);
		grpSounds.setFillLayout(true);
		grpSounds.setHoverText(Tooltip.instance.SOUNDS);
		lstSounds = new SoundList(grpSounds);
		addChild(lstSounds, "sounds");

		// Plug-ins
		
		LFrame grpPlugins = new LFrame(right, Vocab.instance.PLUGINS);
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
		lstSounds.setObject(Project.current.sounds.getList());
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
