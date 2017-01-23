package gui;

public class Vocab {

	public static Vocab instance = new Vocab();
	
	public String DATABASEEDITOR = "Database Editor";
	public String CONFIGEDITOR = "Config Editor";
	public String FIELDEDITOR = "Field Editor";
	public String GUIEDITOR = "GUI Editor";
	public String DIALOGUEEDITOR = "Dialogue Editor";
	
	// Tab names
	public String ANIMATIONS = "Animations";
	public String BATTLERS = "Battlers";
	public String CHARACTERS = "Characters";
	public String ITEMS = "Items";
	public String SKILLS = "Skills";
	public String SKILLDAGS = "Skill Dags";
	public String STATUS = "Status";
	public String OBJECTS = "Objects";
	public String OBSTACLES = "Obstacles";
	public String RAMPS = "Ramps";
	public String TERRAINS = "Terrains";
	public String TILESETS = "Tilesets";
	
	// Groups
	public String GENERAL = "General";
	public String GRAPHICS = "Graphics";
	public String TAGS = "Tags";
	public String ATTRIBUTES = "Attributes";
	public String ELEMENTS = "Elements";
	public String BATTLE = "Battle";
	public String CHARACTER = "Character";
	public String OTHER = "Other";
	
	// Common
	public String ID = "ID";
	public String NAME = "Name";
	public String ICON = "Icon";
	public String DESCRIPTION = "Description";
	public String VALUE = "Value";
	public String PATH = "Path";
	public String SECONDS = "sec";
	public String PROPERTIES = "Properties";
	public String TYPE = "Type";
	
	// Config
	public String PROJECTNAME = "Project Name";
	public String SOUNDS = "Sounds";
	public String FONTS = "Fonts";
	public String REGIONS = "Regions";
	public String ITEMTYPES = "Item Types";
	public String IMAGEATLASES = "Image Atlas";
	public String CODE = "Code";
	public String COLOR = "Color";
	
	// Attributes
	public String SHORTNAME = "Short Name";
	public String MUTABLE = "Mutable";
	public String SCRIPT = "Script";
	
	// Grid
	public String GRID = "Grid";
	public String TILEWIDTH = "Tile Width";
	public String TILEHEIGHT = "Tile Height";
	public String TILEBASE = "Tile Base";
	public String TILESIDE = "Tile Side";
	public String PIXELHEIGHT = "Pixels/Height";
	public String ALLNEIGHBORS = "8 neighbors";
	
	// Player
	public String PLAYER = "Player";
	public String STARTPOS = "Start Position";
	public String PIXELMOV = "Pixel Movement";
	public String STOPCOLL = "Stop On Collision";
	public String WALKSPEED = "Walk Speed";
	public String DASHSPEED = "Dash Speed";
	
	// Battle
	public String BATTLERTYPES = "Battler Types";
	public String INDIVIDUALTURN = "Individual Turn";
	public String GROUPESCAPE = "Group Escape";
	public String PARTYTILEESCAPE = "Escape in Party Tile";
	
	// GUI
	public String GUI = "GUI";
	public String CURSORANIMID = "Cursor Animation";
	
	// Item
	public String PRICE = "Price";
	public String WEIGHT = "Weight";
	public String CANSELL = "Can sell";
	public String CANEQUIP = "Can equip";
	public String CANUSE = "Can use";
	public String CANCONSUME = "Can consume";
	public String ITEMSKILL = "Skill ID";
	public String ITEMSTATUS = "Status ID";
	
	// Battler
	public String BATTLERTYPE = "Type";
	public String SKILLDAG = "Skill Dag";
	public String ATTACKSKILL = "Attack Skill";
	public String PERSISTENT = "Persistent";
	public String AI = "AI";
	public String MONEY = "Money";
	public String EXP = "EXP";
	public String BUILD = "Build";
	
	// Skill
	public String RADIUS = "Radius";
	public String RANGE = "Range";
	public String EPCOST = "EP Cost";
	public String TIMECOST = "Time Cost";
	public String RESTRICTIONS = "Restrictions";
	public String ALWAYS = "Always";
	public String BATTLEONLY = "Battle Only";
	public String FIELDONLY = "Field Only";
	public String USER = "User";
	public String CAST = "Cast";
	public String CENTER = "Center";
	public String INDIVIDUAL = "Individual";
	public String EFFECT = "Effect";
	public String PARAM = "Param";
	
	// Animation
	public String COLUMNS = "Columns";
	public String ROWS = "Rows";
	public String FRAMEDURATION = "Frame Duration";
	
	// Audio
	public String SOUND = "Sound";
	public String VOLUME = "Volume";
	public String PITCH = "Pitch";
	public String SPEED = "Speed";
	
	// Transform
	public String TRANSFORM = "Transform";
	public String OFFSETX = "Offset X";
	public String OFFSETY = "Offset Y";
	public String OFFSETDEPTH = "Offset Depth";
	public String ROTATION = "Rotation";
	public String SCALEX = "Scale X";
	public String SCALEY = "Scale Y";
	public String RED = "Red %";
	public String BLUE = "Blue %";
	public String GREEN = "Green %";
	public String ALPHA = "Alpha %";
	
	// Quad
	public String QUADX = "Quad X";
	public String QUADY = "Quad Y";
	public String QUADW = "Quad Width";
	public String QUADH = "Quad Height";
	public String FULLIMAGE = "Full Image";
	
	// Character
	public String COLLISIONTILES = "Collision Tiles";
	public String COLLIDERHEIGHT = "Collider Height";
	public String STARTLISTENERS = "Start Scripts";
	public String COLLISIONLISTENERS = "Collision Scripts";
	public String INTERACTLISTENERS = "Interact Scripts";
	
	// Ramp
	public String HEIGHT = "Height";
	public String BOTTOM = "Bottom";
	public String TOP = "Top";
	public String LINES = "Lines";
	public String CHOOSEDEFAULT = "Choose Default";
	
	// Obstacle
	public String RAMP = "Ramp";
	public String NEIGHBORS = "Neighbors";
	public String TILES = "Tiles";

	// Status
	public String REMOVEONDAMAGE = "Remove on Damage";
	public String DURATION = "Duration";
	public String DRAIN = "Drain";
	public String HPDRAIN = "HP Drain";
	public String MPDRAIN = "MP Drain";
	public String PERCENTAGE = "Percentage";
	public String FREQUENCE = "Frequence";

	// Terrain
	public String FRAMECOUNT = "N\u00BA of frames";
	public String PASSABLE = "Passable";
	public String LIFETIME = "Life time";
	public String REMOVEONEXIT = "Remove on Exit";
	public String RESETTIME = "Reset time";

	// Skill Dag
	public String SKILLNODES = "Skill Nodes";
	public String REQUIREDSKILLS = "Required Skills";
	public String EXPCOST = "EXP Cost";
	public String SKILL = "Skill";
	
	// Tileset
	public String ANIMATION = "Animation";
	public String DIRECTION = "Direction";
	public String STARTLISTENER = "Start";
	public String COLLISIONLISTENER = "Collision";
	public String INTERACTLISTENER = "Interact";

	// Field
	public String BACKGROUND = "Background";
	public String TILESET = "Tileset";
	public String DEFAULTREGION = "Default Region";
	public String PARTYCOUNT = "Party Count";
	public String TERRAIN = "Terrain";
	public String OBSTACLE = "Obstacle";
	public String REGION = "Region";
	public String SIZEX = "Width";
	public String SIZEY = "Height";
	public String PARTY = "Party";
	
	// Transition
	public String TRANSITIONS = "Transitions";
	public String ORIGIN = "Origin";
	public String DESTINATION = "Destination";
	public String FADEOUT = "Fade out";

	// Dialogue
	public String SPEECHES = "Speeches";
	public String PORTRAIT = "Portrait";
	public String MESSAGE = "Message";
	public String CHARNAME = "Character Name";
	
}
