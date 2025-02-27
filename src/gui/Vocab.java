package gui;

public class Vocab {

	public static Vocab instance = new Vocab();
	
	public String DATABASEEDITOR = "Database Editor";
	public String SYSTEMEDITOR = "System Editor";
	public String FIELDEDITOR = "Field Editor";
	public String GUIEDITOR = "GUI Editor";
	public String DIALOGUEEDITOR = "Dialogue Editor";
	
	// Tab names
	public String ANIMATIONS = "Animations";
	public String BATTLERS = "Battlers";
	public String CHARACTERS = "Characters";
	public String ITEMS = "Items";
	public String JOBS = "Jobs";
	public String SCRIPTS = "Scripts";
	public String SKILLS = "Skills";
	public String STATUS = "Status";
	public String OBSTACLES = "Obstacles";
	public String TERRAINS = "Terrains";
	public String TILESETS = "Tilesets";
	public String TROOPS = "Troops";
	public String EVENTSHEETS = "Event Sheets";
	
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
	public String KEY = "Key";
	public String ICON = "Icon";
	public String DESCRIPTION = "Description";
	public String VALUE = "Value";
	public String CHANCE = "Chance";
	public String COUNT = "Count";
	public String PATH = "Path";
	public String SECONDS = "sec";
	public String PROPERTIES = "Properties";
	public String TYPE = "Type";
	public String DEFAULT = "Default";
	public String OPTIONS = "Options";
	public String ELEMENT = "Element";
	
	// ========================================================================
	// Config
	// ========================================================================
	
	// Config
	public String PROJECTNAME = "Project Name";
	public String ICONS = "Icons";
	public String SOUNDS = "Sounds";
	public String LISTS = "Lists";
	public String REGIONS = "Regions";
	public String EQUIPTYPES = "Equip Types";
	public String PLUGINS = "Plugins";
	public String PLUGINON = "ON";
	public String CONSTANTS = "Constants";
	public String VARIABLES = "Variables";
	public String COLOR = "Color";
	public String SIZE = "Size";
	public String FORMAT = "Format";
	public String PLATFORM = "Platform";
	public String DESKTOP = "Desktop";
	public String MOBILE = "Mobile App";
	public String BROWSER = "Web Browser";
	public String MOBILEBROWSER = "Mobile Web Browser";
	
	// Attributes
	public String SHORTNAME = "Short Name";
	public String MUTABLE = "Mutable";
	public String SCRIPT = "Script";
	public String VISIBILITY = "Visibility";
	public String PRIMARY = "Primary";
	public String SECONDARY = "Secondary";
	public String NOTVISIBLE = "Not visible";
	
	// Grid
	public String GRID = "Grid";
	public String TILEWIDTH = "Tile Width";
	public String TILEHEIGHT = "Tile Height";
	public String TILEBASE = "Tile Base";
	public String TILESIDE = "Tile Side";
	public String PIXELHEIGHT = "Pixels/Height";
	public String DEPTHHEIGHT = "Depth/Height";
	public String DEPTHY = "Depth/Y";
	public String ALLNEIGHBORS = "8 neighbors";
	public String OVERPASSALLIES = "Overpass Allies";
	public String OVERPASSDEADS = "Overpass Dead";
	
	// Screen
	public String SCREEN = "Screen";
	public String COVER = "Cover";
	public String LOGO = "Logo";
	public String FPSLIMIT = "FPS Limits";
	public String NATIVESIZE = "Native Size";
	public String NATIVEWIDTH = "Native Width";
	public String NATIVEHEIGHT = "Native Height";
	public String SCALEFACTOR = "Scale Factor";
	public String WIDTHSCALE = "Width Scale";
	public String HEIGHTSCALE = "Height Scale";
	public String SCALETYPE = "Scale Type";
	public String MOBILESCALETYPE = "Scale Type (Mobile)";
	public String NOSCALE = "No scaling";
	public String INTEGERONLY = "Only by integers";
	public String KEEPRATIO = "Keep ratio";
	public String FREESCALE = "Fill screen";
	public String PIXELPERFECT = "Pixel Perfect";
	public String VSYNC = "V Sync";
	
	// Player
	public String PLAYER = "Player";
	public String STARTPOS = "Start Position";
	public String PIXELMOV = "Pixel Movement";
	public String STOPCOLL = "Stop On Collision";
	public String WALKSPEED = "Base Speed";
	public String DASHSPEED = "Dash Speed";
	public String DIAGTHRESHOLD = "Diag Threshold";
	
	// Battle
	public String MAXLEVEL = "Max Level";
	public String ATTHP = "HP Attribute";
	public String ATTSP = "SP Attribute";
	public String ATTSTEP = "Step Attribute";
	public String ATTJUMP = "Jump Attribute";
	public String BATTLEENDREVIVE = "Revive when battle ends";
	public String KEEPPARTIES = "Keep chosen parties on Retry";
	public String CHARSPEED = "Char Speed";
	
	// Troop
	public String TROOP = "Troop";
	public String INITIALTROOP = "Initial Troop";
	public String MAXMEMBERS = "Max Members";
	public String TROOPSIZE = "Grid Size";
	public String WIDTH = "Width";
	public String HEIGHT = "Height";
	
	// Animations
	public String GUI = "GUI";
	public String CURSORANIMID = "Cursor Animation";
	public String BATTLECURSORANIMID = "Battle Cursor Animation";
	public String TILEANIMID = "Tile Animation";
	public String TILEHLANIMID = "Tile Highlight Animation";
	
	// ========================================================================
	// Database
	// ========================================================================
	
	// Item
	public String PRICE = "Price";
	public String SELLABLE = "Sellable";
	public String USE = "Use";
	public String CONSUME = "Consume on use";
	public String NEEDSUSER = "Needs user";
	public String ITEMSKILL = "Item Skill ID";
	public String STATUSADD = "Add Status";
	public String STATUSREMOVE = "Cure Status";
	public String EQUIP = "Equip";
	public String SLOT = "Slot Type";
	public String ALLSLOTS = "Use all slots of type";
	public String BLOCKEDSLOTS = "Blocked Slots";
	public String ATTADD = "By constant";
	public String ATTMUL = "By percentage";
	public String ADDONBATTLE = "Add on battle";
	public String ELEMENTATK = "Attack Property";
	public String ELEMENTDEF = "Immunity";
	public String ELEMENTBUFF = "Damage Multipler";
	
	// Battler
	public String RECRUIT = "Recruitable";
	public String JOB = "Job";
	public String ATTACKSKILL = "Attack";
	public String PERSISTENT = "Persistent";
	public String AI = "AI";
	public String MONEY = "Money";
	public String EXP = "EXP";
	public String LEVEL = "Level";
	public String INITIAL = "Initial";
	public String DROP = "Drop";
	public String STATE = "State";
	public String FREE = "Free";
	public String NOTEMPTY = "Never empty";
	public String ALLEQUIPED = "All equiped";
	public String UNCHANGABLE = "Unchangable";
	public String INVISIBLE = "Invisible";
	public String EQUIPITEM = "Item ID";
	
	// Skill
	public String ATTACK = "Attack";
	public String SUPPORT = "Support";
	public String ALLPARTIES = "All parties";
	public String TARGET = "Target";
	public String EFFECTCONDITION = "Condition";
	public String TARGETSELECTION = "Selection";
	public String AUTOPATH = "Auto Path";
	public String ANYTILE = "Any tile";
	public String EFFECTONLY = "Affected only";
	public String RANGEONLY = "Reachable only";
	public String EFFECTRANGE = "Affected & Reachable";
	public String FREENAVIGATION = "Free Navigation";
	public String RESTRICTIONS = "Restrictions";
	public String CONTEXT = "Context";
	public String ALWAYS = "Always";
	public String BATTLEONLY = "Battle Only";
	public String FIELDONLY = "Field Only";
	public String COSTS = "Costs";
	public String USECONDITION = "Use Condition";
	public String CASTMASK = "Range Mask";
	public String EFFECTMASK = "Effect Mask";
	public String USERELEMENTS = "Include user's elements";
	public String PROJECTILE = "Projectile";
	public String ROTATE = "Rotate";
	public String APPLYTRANSFORM = "Apply battle animation transform";
	public String USERANIMATIONS = "User Animations";
	public String BATTLEANIMATIONS = "Battle Animations";
	public String MIRROR = "Mirror";
	public String LOAD = "Load";
	public String USER = "User";
	public String CAST = "Cast";
	public String INDIVIDUAL = "Individual";
	public String DAMAGEANIM = "Damage Animation";
	public String INTROTIME = "Intro Time";
	public String CASTTIME = "Cast Time";
	public String CENTERTIME = "Center Time";
	public String TARGETTIME = "Target Time";
	public String FINISHTIME = "Finish Time";
	public String STEPONCAST = "Step on Cast";
	
	// Animation
	public String COLUMNS = "Columns";
	public String ROWS = "Rows";
	public String PATTERN = "Pattern";
	public String INTRO = "Intro";
	public String LOOP = "Loop";
	public String DURATION = "Duration";
	
	// Character
	public String CHARBATTLER = "Battler";
	public String SHADOW = "Shadow";
	public String COLLIDERTILES = "Collision Tiles";
	public String PORTRAITS = "Portraits";
	
	// Class
	public String BUILD = "Build";
	public String EXPCURVE = "EXP Curve";;
	public String SKILLNODES = "Skill Nodes";
	public String SKILL = "Skill";
	public String REQUIREDSKILLS = "Required Skills";
	public String EXPCOST = "EXP Cost";
	public String MINLEVEL = "Min Level";
	
	// Obstacles
	public String NEIGHBORS = "Neighbors";
	public String TILES = "Tiles";
	public String NONE = "None";
	public String ALL = "All";
	public String MODE = "Mode";
	public String BLOCK = "Block";
	public String RAMP = "Ramp";
	public String BRIDGE = "Bridge";

	// Status
	public String VISIBLE = "Visible";
	public String PRIORITY = "Priority";
	public String KO = "KO";
	public String DEACTIVATE = "Deactivate";
	public String CUMULATIVE = "Cumulative";
	public String DURABILITY = "Durability";
	public String BEHAVIOR = "Behavior";
	public String CHARANIM = "Character Animation Set";
	public String OVERRIDETRANSFORM = "Override Value";
	public String TRANSFORMATIONS = "Transformations";
	public String TURNS = "Turns";
	public String REMOVEONKO = "Remove on KO";
	public String REMOVEONDAMAGE = "Remove on Damage";
	public String DRAIN = "Drain";
	public String DRAINATT = "Attribute";
	public String DRAINVALUE = "Value";
	public String PERCENTAGE = "Percent";
	public String FREQUENCY = "Frequency";
	public String STATUSCANCEL = "Cancel";
	public String STATUSDEF = "Status Immunity";

	// Terrain
	public String MOVECOST = "Move Cost";
	public String PASSABLE = "Passable";
	public String REMOVEONEXIT = "Remove on Exit";
	public String ALLMEMBERS = "Affect All Members";
	
	// Troops
	public String UNITS = "Units";
	public String POSITIONX = "Grid X";
	public String POSITIONY = "Grid Y";
	public String LIST = "List";
	public String CURRENT = "Current";
	public String BACKUP = "Backup";
	public String HIDDEN = "Hidden";
	
	// Event Sheet
	public String EVENTS = "Events";
	public String COMMAND = "Command";
	
	// ========================================================================
	// Sub-content
	// ========================================================================
	
	// Audio
	public String FILE = "File";
	public String SOUND = "Sound";
	public String VOLUME = "Volume";
	public String PITCH = "Pitch";
	public String TIME = "Time";
	public String PLAY = "Play";
	public String STOP = "Stop";
	
	// Transform
	public String TRANSFORM = "Transform";
	public String OFFSETX = "Offset X";
	public String OFFSETY = "Offset Y";
	public String OFFSETDEPTH = "Offset Depth";
	public String ROTATION = "Rotation";
	public String SCALEX = "Scale X";
	public String SCALEY = "Scale Y";
	public String RED = "Red";
	public String BLUE = "Blue";
	public String GREEN = "Green";
	public String ALPHA = "Alpha";
	public String HUE = "Hue";
	public String SATURATION = "Saturation";
	public String BRIGHTNESS = "Brightness";
	
	// Quad
	public String QUADX = "Quad X";
	public String QUADY = "Quad Y";
	public String QUADW = "Quad Width";
	public String QUADH = "Quad Height";
	public String FULLIMAGE = "Full Image";
	
	// Icon
	public String COLUMN = "Column";
	public String ROW = "Row";
	
	// Script / Rule
	public String PARAM = "Parameters";
	public String GLOBAL = "Global";
	public String WAIT = "Wait";
	public String BLOCKPLAYER = "Block player";
	public String CONDITION = "Condition";
	public String REPEATCOLLISIONS = "Repeat collisions";
	
	// Effect
	public String EFFECTS = "Effects";
	public String BASICRESULT = "Basic Result";
	public String SUCCESSRATE = "Success Rate";
	public String HEAL = "Heal";
	public String ABSORB = "Absorb";

	// ========================================================================
	// Field
	// ========================================================================
	
	// Field
	public String DEFAULTREGION = "Default Region";
	public String MAXHEIGHT = "Max Height";
	public String BGM = "BGM";
	public String IMAGES = "Images";
	public String FOREGROUND = "Foreground";
	public String GLUED = "Glued to Camera";
	
	// Layers
	public String LAYERS = "Layers";
	public String TERRAIN = "Terrain";
	public String OBSTACLE = "Obstacle";
	public String REGION = "Region";
	public String PARTY = "Party";
	public String SHOWGRID = "Show Grid";
	public String NOAUTO = "No autotiles";
	
	// Resize
	public String RESIZE = "Resize";
	public String SIZEX = "Width";
	public String SIZEY = "Height";
	public String ALIGNX = "Horizontal Anchor";
	public String ALIGNY = "Vertical Anchor";
	public String CENTER = "Center";
	public String BOTTOM = "Bottom";
	public String TOP = "Top";
	public String LEFT = "Left";
	public String RIGHT = "Right";
	
	// Character
	public String ANIMATION = "Animation";
	public String FRAME = "Frame";
	public String ONLOAD = "On Load";
	public String ONCOLLIDE = "On Collide";
	public String ONINTERACT = "On Interact";
	public String SPEED = "Speed";
	
	// Party
	public String PLAYERPARTY = "Player Party";
	public String PARTYGEN = "Unit Generation";
	public String TROOPUNITS = "Troop Units";
	public String FIELDCHARS = "Field Characters";
	
	// Position
	public String POSITION = "Position";
	public String LAYER = "Layer";
	public String DIRECTION = "Direction";
	
	// Transition
	public String TRANSITIONS = "Transitions";
	public String DESTINATION = "Destination";
	public String ORIGTILES = "Origin Tiles";
	public String FADEOUT = "Fade out";

}
