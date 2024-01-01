package gui;

public class Tooltip {

	public static Tooltip instance = new Tooltip();

	// Groups
	public String GENERAL = "General settings.";
	public String GRAPHICS = "Graphics settings.";
	public String TAGS = "List of tags with their respective values. \n"
			+ "The value of a tag is interpreted as JSON.";
	
	// Common
	public String ID = "Unique identification number.";
	public String NAME = "Display name.";
	public String KEY = "Unique identification text.";
	public String ICON = "Display icon.";
	public String DESCRIPTION = "Description shown in the menu.";
	public String JSON = "A string interpreted as JSON, which can be a string (in quotes), a number,\n"
			+ "an array, an object or true/false.\n"
			+ "If it's not a valid JSON, it's left as just a raw string.";
	public String VALUE = "Value of the property.";
	public String PROPERTIES = "Elemental or immnunity bonuses.";
	public String SCRIPT = "Lua script containing the class.\n"
			+ "Leave it empty to use the base class.";
	
	//////////////////////////////////////////////////
	// {{ Config
	
	// General
	public String IDENTITY = "Game's identity and presentation.";
	public String PROJECTNAME = "Game's title.";
	public String EXECUTION = "Run Settings";
	public String PLATFORM = "Target platform. It is independent from the platform in which the game is actually running.";
	public String ANIMATIONS = "Keys of default system animations.";
	public String ICONS = "Keys of default system icons.";
	public String SOUNDS = "Keys of default system sounds.";
	
	// Lists
	public String ELEMENTS = "List of elements in the game system.";
	public String REGIONS = "List of all regions.";
	public String ATTRIBUTES = "List of all attributes in the game's system.";
	public String EQUIPTYPES = "List of all types of equipment slots.";
	public String PLUGINS = "List of installed plugins.";
	public String VARIABLES = "List of variables to used in text messages or formulas.";
	
	// Sub-content
	public String LENGTH = "Size of file.";
	public String FORMAT = "Format of file.";
	public String COLOR = "Color of the region. Only used in the editor.";
	public String BATTLEFIELDS = "Battle fields that are randomly loaded when the player steps on this region.";
	public String SLOTSIZE = "The number of slots of this type.\n"
			+ "Each individual slot with be refered to by the slot key and a number identifying it.\n"
			+ "Example: slot 'acc' with count 2 will create two accessory slots, 'acc1' and 'acc2'.";
	public String STATE = "Slot's initial state.\n"
			+ "Free: the equipment can be changed freely.\n"
			+ "Never empty: the equipment can be changed, but at least one slot of the type must be left equiped.\n"
			+ "All equiped: the equipment can be changed, but all slots of this type must be left equiped.\n"
			+ "Unchangable: cannot change the equipment in this slot.\n"
			+ "Invisible: this slot is not visible on the equipment menu.";
	public String SHORTNAME = "The name displayed in a character's status menu.";
	public String FORMULA = "The formula that computes this attribute.\n"
			+ "It has access to the table 'att', which contains each attribute function refered by its key.";
	public String VISIBILITY = "Whether or not the attribute is shown in the character's status menu, and in which column.";
	public String PLUGINON = "Uncheck this to disable the plugin without unistalling it.";
	
	// Grid
	public String GRID = "Grid settings.";
	public String TILEWIDTH = "Tile's total width in pixels.";
	public String TILEHEIGHT = "Tile's total height in pixels.";
	public String TILEBASE = "Length in pixels of the tile's horizontal base (for hexagonal tiles).";
	public String TILESIDE = "Length in pixels of the tile's vertical side (for hexagonal tiles).";
	public String PIXELHEIGHT = "Number of pixels that represent one height unit in the grid.";
	public String DEPTHHEIGHT = "The depth subtracted from a sprite each time it moves one height unit up.";
	public String DEPTHY = "The depth of a sprite according to its pixel Y.";
	public String ALLNEIGHBORS = "Considerar all 8 neighbor tiles as passable from one to the other in a battle grid.";
	public String OVERPASSALLIES = "Ally characters don't block the path.";
	public String OVERPASSDEADS = "Dead character don't block the path.";
	
	// Screen
	public String SCREEN = "Screen settings.";
	public String COVER = "Background image shown when the game starts.";
	public String LOGO = "Image shown over the cover.\n"
			+ "When none is selected, a plain text with the project's title is shown instead.";
	public String FPSLIMIT = "Maximum and minimum frame rates.\n"
			+ "If the game is too slow, the physics and animations will lag instead of being adjusted by the delta time.\n"
			+ "If the game is too fast, it sleeps instead of computing more frames, for battery economy.";
	public String NATIVESIZE = "Game's native size. The game's logic will assume with screen size.\n"
			+ "If the actual size is different, some scaling will be done during the rendering.";
	public String SCALEFACTOR = "The default scaling of the game's actual screen.\n"
			+ "If 1x1, it's the same as the native size.";
	public String SCALETYPE = "What happens when the window size if different from the native size.\n"
			+ "No scaling: all empty space will be filled with black. If the native screen is bigger, it is cropped out.\n"
			+ "Only by integers: the screen will be scaled by the bigger integer number that can still fit within the actual screen.\n"
			+ "Keep ratio: the screen is scaled to fit either the width or the height, and the remaining space is filled with black.\n"
			+ "Fill screen: the screen is freely scaled to fit the actual screen.";
	public String MOBILESCALETYPE = "The scale type when the game is played on a mobile.\n";
	public String PIXELPERFECT = "When checked, the position of sprites will be rounded off.\n"
			+ "If unchecked, the sprites might be blurred with it's on a non-integer position.";
	public String VSYNC = "Vertical synchronization.\n"
			+ "When checked, logical frame will be computed only once after each rendering frame.\n"
			+ "In this case, the framerate drops if the rendering is slow.\n"
			+ "If unchecked, logical and rendering frames are computed indepentendly and the framerate is optimized,\n"
			+ "but some visual lag might occur.";
	
	// Player
	public String PLAYER = "Player settings.";
	public String STARTPOS = "The initial position of the player character.\n"
			+ "The player character is given by the first unit in the player's troop.";
	public String WALKSPEED = "The default walking speed of the player character, in pixels per second.";
	public String DASHSPEED = "The multipler of the speed (in percentage) when the player is holding the dash button.";
	public String DIAGTHRESHOLD = "The minimum value that the axis input (from 0 to 100) should have to be considered non-zero.\n"
			+ "For horizontally-liked hexagonal grid: if the horizontal input is bigger than the vertical input, \n"
			+ "the vertical input should be at least two times the threshold for it to be considered diagonal.\n"
			+ "For vertically-liked hexagonal grid: if the vertical input is bigger than the horizontal input, \n"
			+ "the horizontal input should be at least two times the threshold for it to be considered diagonal.\n"
			+ "For orthogonal grid: both conditions above are applied.";
	
	// Battle
	public String BATTLE = "Battle settings.";
	public String FINALLEVEL = "Maximum level a battler can reach.";
	public String ATTHP = "The key of the attribute that indicates the maximum HP of a character.";
	public String ATTSP = "The key of the attribute that indicates the maximum SP of a character.";
	public String ATTSTEP = "The key of the attribute that indicates the number of steps the character can take in a turn.";
	public String ATTJUMP = "The key of the attribute that indicates the maximum distance in tiles height that a character can overpass.";
	public String BATTLEENDREVIVE = "When checked, the characters that were knocked out are awaken with 1 of HP after the battle ends.";
	public String KEEPPARTIES = "If unchecked, if the player loses and choses to retry, new troops can be chosen.";
	public String CHARSPEED = "The character speed of the battle characters that are generated automatically.";
	
	// Troop
	public String TROOP = "Troop settings.";
	public String INITIALTROOP = "Player's troop.";
	public String MAXMEMBERS = "Maximum number of units in the 'current' list of the party.";
	public String TROOPSIZE = "Width and height of the troop grid in tiles.";
	
	// }}
	
	//////////////////////////////////////////////////
	// {{ Database
	
	// Item
	public String PRICE = "Item's full price.";
	public String SELLABLE = "Whether the player can sell the item or not. \n"
			+ "Uncheck this for key items.";
	public String USE = "What happens when the item is used.";
	public String CONSUME = "The item disappears when used.";
	public String NEEDSUSER = "The effect of the item depends on who is using it. \n"
			+ "When checked, a menu for user selection will open when the item is selected on the menu.";
	public String ITEMSKILL = "The skill that is executed when the item is used. \n"
			+ "Set it as none if the item is not usable.";
	public String EQUIP = "What happens when the item is equipped.";
	public String SLOT = "Key of the slot this item should be equipped on.";
	public String ALLSLOTS = "If checked, the item will occupy all slots of the given slot type.";
	public String BLOCKEDSLOTS = "Blocked Slots";
	public String ATTKEY = "Attribute's key.";
	public String ATTADD = "Constant value that will be added to the final value.";
	public String ATTMUL = "Proportional value that will be added to the final value (in percentage).";
	public String ADDONBATTLE = "When checked, the status will be added at the start of the battle if the character has this item equipped.\n"
			+ "During the battle, the status can be removed normally. \n"
			+ "If unchecked, the status is always there, and will only be removed if the item is unequipoed.";
	public String BONUSTYPE = "Elemental Immunity: add elemental defense (or vulnerability) to the battler.\n"
			+ "Attack Property: add elemental properties to the character's attack.\n"
			+ "Damage Multiplier: changes the heal/damage of the skills that has this element.\n"
			+ "Status Immnunity: makes the character resistant to receiving the specified status.s";
	public String ITEMEFFECTS = "The effects added to the item's skill's effects. \n"
			+ "It is applied to the target character when this item is used.";
	public String ITEMATTRIBUTES = "Values permanently added to a character's attributes when this item is used on that character.";
	public String EQUIPATTRIBUTES = "Values added to a character's attributes while it has this item equipped.";
	public String EQUIPPROPERTIES = "Element or status bonus applied to the character while it has this item equipped.";
	public String EQUIPSTATUSES = "The statuses added to the character while it has this item equipped.";
	
	// Battler
	public String RECRUIT = "When checked, the battler is considered a recruitable, which can be dismissed in the recruitment menu.\n"
			+ "If unchecked, the character can't be dismissed.";
	public String JOB = "Battler's job.";
	public String PERSISTENT = "When checked, the changed data is stored in the save.";
	public String RULES = "List of AI rules to be applied sequentially in every turn of this character. \n"
			+ "When this battler is in the player's party, the action menu will open if the rules are empty.\n"
			+ "If not empty, the rules will be automatically executed without player input.";
	public String MONEY = "Money rewarded to the player at the end of the battle, if this battler/troop is defeated.";
	public String EXP = "EXP rewarded to all living allies at the end of the battle, if this battler/troop is defeated.";
	public String LEVEL = "Initial level of the battler for indicated job.";
	public String BATTLERATT = "Attribute values that are added to the job's attribute build. \n"
			+ "These can be modified in-game if the character uses an item with permanent attribute bonuses. \n";
	public String INITSKILLS = "Skills that start as available for this character.";
	public String INITSTATUS = "Status that this battler starts with.";
	public String INITEQUIP = "Equipment that this battler starts with.";
	public String DROP = "Items that this battler drops when defeated. \n"
			+ "The items are rewarded at the end of the battle along with money and EXP.";
	public String EQUIPITEM = "The item currently equiped in this slot.";
	public String ELEMENTDEF = "Elemental defense. 100% for a given element means neutral. \n"
			+ "By default, the defense for all elements is 100%.";
	public String CHANCE = "Chance with which the items will be added, in percentage.";
	public String COUNT = "The number of items added.";
	
	// Skill
	public String SKILLTYPE = "The intended targets of the skill.\n"
			+ "General: any character or tile.\n"
			+ "Attack: enemy characters.\n"
			+ "Support: Ally characters.";
	public String ALLPARTIES = "When checked, the effects of the skill will be applied to characters of any party. \n"
			+ "If unchecked, only the target party will be affected (ally characters if support, enemy characters if attack).";
	public String TARGET = "Target selection/restriction.";
	public String EFFECTCONDITION = "Condition";
	public String TARGETSELECTION = "Limits which tiles can be chosen by the player as the target for this skill.\n"
			+ "Affected: tiles that will cause some effect. This depends on the skill effects and the skill type.\n"
			+ "Reachable: tiles within the user's range. It depends only on the skill's range mask and,\n"
			+ "if 'auto-path' is enabled, the user's movement range.";
	public String AUTOPATH = "When checked, the movement path necessary to reach the target will be calculated automatically.\n"
			+ "If unchecked, the user must the moved manually before using the skill.";
	public String FREENAVIGATION = "When checked, the tile selection uses free navigation.\n"
			+ "If unchecked, a list of affected tiles are computed, and the user must selected a target from this list.\n"
			+ "In this case, left/right arrows will select the next tile in the list instead of navigating the neighbor tiles.";
	public String RESTRICTIONS = "Restrictions";
	public String CONTEXT = "In which context this skill can be used.\n"
			+ "Battle Only: only during the battle, on player's turn.\n"
			+ "Field Only: only outside of battle, from the field menu.";
	public String COSTS = "List of costs to use the skill ('sp', 'hp' or some custom attribute).";
	public String USECONDITION = "Lua expression that determines if the character can use this skill.";
	public String SKILLELEMENTS = "Element properties of the skill damage/heal. \n";
	public String CASTMASK = "Mask determining which tiles are reachable from the user's tile.\n"
			+ "Melee skills will contain all tiles that are neighbor to the center tile.\n"
			+ "If the mask only contains the center tile, then only the user's tile can be selected.";
	public String EFFECTMASK = "Mask determining which tile will receive the skill's effect from the selected target.\n"
			+ "Single-target skill should only include the center tile.";
	public String USERELEMENTS = "When checked, the skill element list will include any elemental property that is\n"
			+ "currently applied to the user's attack.";
	public String ROTATE = "When checked, the effect mask will be rotated according the user's facing direction.\n"
			+ "The neutral facing direction is right (on orthogonal grids) or right-down (on isometric/hexagonal grids).";
	public String MIRROR = "When checked, the animations will mirror if the character is facing left.";
	public String LOAD = "Animation played over the user.";
	public String CAST = "Animation played over the selected target tile.";
	public String INDIVIDUAL = "Animation player over each affected tile (for area skills).";
	public String DAMAGEANIM = "When checked, the target character will play its damage animation if it receives this skill's effects";
	public String USERLOAD = "Animation that the user plays during the load animation.\n"
			+ "There must be an animation in this character's animation list that has this name.";
	public String USERCAST = "Animation that the user plays right before the cast animation, after loading.\n"
			+ "There must be an animation in this character's animation list that has this name.";
	public String STEPONCAST = "When checked, the user will step forward to cast the skill, after the loading animation.";
	public String INTROTIME = "Waiting time in frames after skill/target selection and the load animation.\n"
			+ "Leave blank to use default number.";
	public String CASTTIME = "Total duration of the cast, including tile and user animations.\n"
			+ "Leave blank to use default number.";
	public String CENTERTIME = "Duration of the cast animation in the center target tile.\n"
			+ "Leave blank to use default number.";
	public String TARGETTIME = "Duration of each target animation.\n"
			+ "Leave blank to use default number.";
	public String FINISHTIME = "Waiting time in frames after all animations.\n"
			+ "Leave blank to use default number.";
	
	// Animation
	public String SIZE = "The number of sprites in the animation's spritesheet.";
	public String COLUMNS = "Number of columns in the spritesheet.";
	public String ROWS = "Number of rows in the spritesheet.";
	public String INTRO = "Intro part of the animation. It will play once before the looping part.";
	public String LOOP = "Looping part of the animation. It will repeat endlessly after the intro part.";
	public String PATTERN = "The order of columns that are player (indexed from 0).\n"
			+ "The columns might be repeated, left out and switched.";
	public String DURATION = "Duration of the animation in frames. \n"
			+ "If the duration is a single number, the number of divided by the total number of columns.\n"
			+ "Else, each number in the duration text (separared by spaces) is assigned as the duration of a single frame, in order.\n"
			+ "In this case, the number of duration texts should be the same as the pattern text.";
	public String PATTERNDEFAULT = "Sets the pattern as each columns played in order.";
	public String DURATIONDEFAULT = "Takes the duration inserted in the text that divides it among the number of columns.";
	public String SOUND = "List of SFX played during the animation.";
	
	// Character
	public String CHARBATTLER = "Default battler associated with this character.";
	public String SHADOW = "A shadow image the appear below the character sprite.\n"
			+ "It stays in place if the character is jumping/lifted from the ground.";
	public String COLLIDERTILES = "The tiles this character occupy. \n"
			+ "Each one can have a different height (in tiles). \n"
			+ "If the character does not have any collision tiles, the player character cannot interact with it.";
	public String CHARANIMS = "The list of animations of this character. \n"
			+ "The name of the animation must be unique in the list, and it can include a set name.\n"
			+ "If an animation belongs to a set, it will replace the default animation of this name if the set changes.\n"
			+ "The set can be changed when the character is in a battle (to set 'Battle') and when it receives a status (e. g. 'Sleep').\n"
			+ "Example: 'Battle:Idle' indicates that the animation's name is 'Idle', but it belongs to the set 'Battle',\n"
			+ "so it replaces the 'Idle' animation during battle.\n";
	public String CHARICONS = "The list of portrait icons of this character. \n"
			+ "The name of the animation must be unique in the list.\n";
	public String KOEFFECT = "What happens when this character is knocked out.";
	public String KOANIM = "The name of the animation that over the character when it's knocked out.";
	public String KOFADE = "The time in frames until the character fades out and is removed from the field.\n"
			+ "To instantly remove it from the field after the character's 'KO' animation, set is as 0.\n"
			+ "To keep it in the field, set it as -1.";
	public String SCRIPTS = "The scripts that run when the character is initialized, collided with, or interacted with.";
	public String TILEX = "The displacement from the character's position, in tiles (coordinate x).";
	public String TILEY = "The displacement from the character's position, in tiles (coordinate y).";
	public String TILEH = "The height of this tile of the character, in tiles.";

	// Job
	public String ATTACKSKILL = "The skill set as the attack skill of the battler.";
	public String BUILD = "The formulas for each attribute. \n"
			+ "Use 'lvl' to refer to the current level. It should typically be a constante multiplied by 'lvl'.\n"
			+ "The result of this formula is added to the attribute's formula (if any) and\n"
			+ "the battler's attribute bonus to compute the final value of the attribute.";
	public String EXPCURVE = "The formula that computes the number of EXP needed to the next level.\n"
			+ "Use 'lvl' to refer to the current level. If constant, the EXP needed to level-up is always be same.";
	public String SKILLNODES = "The skills that are learned as the job is leveled up.";
	public String STATUSNODES = "The statuses that are permanently applied to the battler as the job is leveled up.";
	public String SKILLLEVEL = "The level in which the skill is learned.";
	public String STATUSLEVEL = "The level in which the status is added.";
	
	// Obstacles
	public String NEIGHBORS = "The neighbor tiles that can access this obstacle's tile.\n"
			+ "If a direction is enabled, a character can walk into this obstacle from this direction.\n"
			+ "Disable all directions to make whole obstacle not passable.";
	public String TILES = "The list of tiles that this obstacle occupies.";
	public String NONE = "Disable all neighbors.";
	public String ALL = "Enable all neighbors";
	public String MODE = "Sets what happens when the player moves to this tile."
			+ "Block: a normal obstacle that blocks the path. It makes a passable terrain non-passable.\n"
			+ "Ramp: the player stays on the top of obstacle. The top is defined by the tile's height.\n"
			+ "Brige: frees the path. It makes a non-passable terrain passable instead of blocking it.";

	// Status
	public String VISIBLE = "If unchecked, the status is does not appear on the UI.";
	public String PRIORITY = "Statues with lower priority numbers appear and are processed first.";
	public String KOLIKE = "When checked, a character with this status is considered knocked-out when deciding who won the battle.";
	public String DEACTIVATE = "When checked, this character's turn is skipped.";
	public String CUMULATIVE = "When checked, a character can have multiple of this status simultaneously.\n"
			+ "If unchecked, the previous status is removed when the battler received it again and replaced by the new one.";
	public String DURABILITY = "When is the status removed.";
	public String BATTLEONLY = "When checked, the status is removed when the battle ends.";
	public String BEHAVIOR = "The AI rules that this character executes when it has this status.\n"
			+ "Leave it empty to not change the battler's AI.";
	public String CHARANIM = "The animation set of the character is changed to this one when it receives the status.\n"
			+ "Specific animation replacement can be set in the character's animation list by specifying the set of an animation.\n"
			+ "The replacements are processed in order according to the statuses' priorities.";
	public String TRANSFORMATIONS = "Changes in the character's transform when it receives the status.\n"
			+ "The changes are applied in order according to the statuses' priorities.";
	public String OVERRIDETRANSFORM = "When checked, the value of this field will be replaced.\n"
			+ "If unchecked, the new value will combined with the old value.\n"
			+ "Combined with addition: offset, rotation, hue.\n"
			+ "Combined with multiplication: scale, RGBA, saturation, brightness.";
	public String TURNS = "The number of turns until this status is removed.";
	public String REMOVEONKO = "When checked, the status is removed when the character is knocked out.";
	public String REMOVEONDAMAGE = "When checked, the status is removed when the character received damage.";
	public String DRAIN = "Draining or regeneration effect.";
	public String DRAINATT = "The attribute that is drained/regenerated ('hp', 'sp' or some custom attribute).";
	public String DRAINVALUE = "The value to be drained.";
	public String PERCENTAGE = "When checked, the value is interpreted as a percentage on the total value of the attribute.\n"
			+ "If unchecked, the value is a constant.";
	public String FREQUENCY = "The frequency in number of turns in which the drain effect will be applied.";
	public String STATUSCANCEL = "Statuses that are removed when this status is added to the character.";
	public String STATUSDEF = "Immunity in percentage to receiving a status.\n"
			+ "100% is no change in the immunity, 0% is total immunity, more than 100% makes the battler more vulnerable to the status."
			+ "This immunity is used to compute the chance in which the battler will receive the status.";
	public String STATUSATT = "Bonus or debuff applied to an attribute.";

	// Terrain
	public String MOVECOST = "The default cost in steps to move to this tile.";
	public String JOBMOVECOST = "Specific move costs according to the character's job.";
	public String PASSABLE = "If unchecked, no character can move to this terrain.";
	public String TERRAINSTATUS = "The status added to a battle character each tile it steps on this terrain.";
	public String REMOVEONEXIT = "When checked, the added status is removed when the character moves out of this tile.";
	
	// Troops
	public String AI = "Troop's AI script file.";
	public String UNITS = "List with all members of the troop.\n"
			+ "Different members can have the same battler or character, but each member must be identified by different unique keys.";
	public String GRIDX = "Position of the unit's character relative to the troop's top-left corner\n"
			+ "(or left corner if it's isometric/hexagonal).";
	public String GRIDY = "Position of the unit's character relative to the troop's top-left corner\n"
			+ "(or left corner if it's isometric/hexagonal).";
	public String UNITLIST = "The list containing this unit. \n"
			+ "Current: the unit automatically spawned in the field at the start of a battle.\n"
			+ "Backup: the unit is initially hidden, but can be manually put in the field.\n"
			+ "Hidden: the unit is not in the party, but its data are still saved.";
	public String INVENTORY = "Items in the troop's inventory. \n"
			+ "If this troop is defated by the player, the items are rewarded to the player at the end of the battle.";
	
	// Event Sheet
	public String EVENTS = "Events";
	public String COMMAND = "The command to be executed.\n"
			+ "It can be the name of an event function, is this case the function will be called with the given parameters.\n"
			+ "If no function with the given name is found, the text will be interpreted as Lua code inside a function.\n"
			+ "In this case, the 'script' argument can be used to refer to the script's data,\n"
			+ "which includes the character executing it (if any).";
	
	// }}
	
	//////////////////////////////////////////////////
	// {{ Sub-content
	
	// Audio
	public String FILE = "Path to the audio file.";
	public String VOLUME = "Volume multiplier.";
	public String PITCH = "Pitch/velocity multiplier.";
	public String TIME = "Waiting time from the start of the animation until the audio starts playing.";
	public String PLAY = "Play the audio with the specified pitch and volume.";
	public String STOP = "Stop all audio playing.";
	public String LOOPAUDIO = "Play on repeat.";
	
	// Transform
	public String TRANSFORM = "Spatial and colors transformations.";
	public String OFFSETX = "Horizontal offset of the sprite's origin point.\n"
			+ "A higher offset will move the sprite to the left.";
	public String OFFSETY = "Vertical offset y of the sprite's origin point.\n"
			+ "A higher offset will move the sprite up.";
	public String OFFSETDEPTH = "Number added to the sprite's depth.\n"
			+ "Sprites with higher depth are rendered first.";
	public String ROTATION = "Rotation of the sprite, in angles.";
	public String SCALEX = "Horizontal scale of the sprite, in percentage.";
	public String SCALEY = "Vertical scale of the sprite, in percentage.";
	public String RED = "Red multiplier. Neutral/white is 255.";
	public String BLUE = "Blue multiplier. Neutral/white is 255.";
	public String GREEN = "Green multipler. Neutral/white is 255.";
	public String ALPHA = "Alpha multiplier. Neutral/opaque is 255.";
	public String HUE = "Hue shift, from 0 to 360.";
	public String SATURATION = "Saturation multiplier. Neutral is 100.";
	public String BRIGHTNESS = "Brightness multiplier. Neutral is 100.";
	public String TRANSFORMTYPE = "The transform field to be altered.";
	public String TRANSFORMVALUE = "The new value to the applied to the specified field.";
	
	// Quad
	public String QUADX = "Left corner of the quad's rectangle.";
	public String QUADY = "Top corner of the quad's rectangle.";
	public String QUADW = "Width of the quad's rectangle.";
	public String QUADH = "Height of the quad's rectangle.";
	public String FULLIMAGE = "Set the quad as the entire texture.";
	
	// Script / Rule
	public String PARAM = "Parameters to the script's execution. \n"
			+ "Like tags, the value of each parameters is interpreted as a JSON value.\n"
			+ "If the JSON code is not valid, it is interpreted as a raw string.";
	public String GLOBAL = "When checked, the script is executed globally.\n"
			+ "If unchecked, it stops running if the character is deleted or the field is unloaded.";
	public String WAIT = "When checked, the thread executing will only execute the next script when this script ends its execution.\n"
			+ "If unchecked, this script will run in parallel to the following ones.";
	public String BLOCKPLAYER = "When checked, the player's input will be blocked until this script ends its execution.";
	public String CONDITION = "Condition to execute the script. \n"
			+ "It is treated as a Lua boolean expression.";
	public String REPEATCOLLISIONS = "When checked, the collision scripts will be executed in multiple times in parallel if\n"
			+ "the character has another collision before the script ends.\n"
			+ "If unchecked, the script won't be executed again if it's already running when the new collision occurs.";
	
	// Effect
	public String EFFECTS = "Effects of the skill (damage, healing and status).";
	public String BASICRESULT = "The base value of the damage or healing.";
	public String SUCCESSRATE = "The chance with which this effect will be applied.\n"
			+ "It should be a Lua expression returning a number value from 0 to 100.";
	public String HEAL = "When checked, the value is going to be added instead of subtrated.";
	public String ABSORB = "When checked, the value subtracted from the target is added to the user (and vice-versa).";
	public String EFFECTSTATUS = "Status added (or removed, if 'Heal' is checked) when effect is successfull.";

	// Bonus
	public String BONUSVALUE = "Value of the bonus.";
	public String STATUSBONUS = "The status the bonus refers to.";
	public String ELEMENTBONUS = "The element the bonus refers to.";
	
	// Mask
	public String MINHEIGHT = "Maximum tile height distance below the user.";
	public String MAXHEIGHT = "Maximum tile height distance above the user.";
	public String MINX = "Maximum tile distance to the user's left.";
	public String MAXX = "Maximum tile distance to the user's right.";
	public String MINY = "Maximum tile distance above the user.";
	public String MAXY = "Maximum tile distance below the user.";
	public String MASKH = "Current height to be edited (by the difference from the target to the user).";
	
	// }}
	
	//////////////////////////////////////////////////
	// {{ Field
	
	// Prefs
	public String DEFAULTREGION = "The region that is automatically added to all tiles in the field.";
	public String FIELDMAXHEIGHT = "The maximum height of a tile.\n"
			+ "This limits the number of tile layers.";
	public String LOADSCRIPT = "The script that is automacilly executed when the field is loaded.";
	public String BGM = "The background music that is automatically played when the field is loaded.";
	public String IMAGES = "Foreground and background images.\n"
			+ "Their placement and visibility can be controlled by script.";
	public String IMGVISIBLE = "If unchecked, the image starts off invisible.";
	public String FOREGROUND = "When checked, the image if placed over the field.\n"
			+ "If unchecked, it's placed below the field.";
	public String GLUED = "When checked, the image with follow the camera.\n"
			+ "If unchecked, the image is fixed to the center of the field.";
	
	// Layers
	public String LAYERS = "List of layers. The layers on the list are renderers in order.";
	public String TILESET = "List of tilesets. Select one and click on a tile to paste it there.";
	public String TERRAIN = "Layers with terrain tiles.";
	public String OBSTACLE = "Layers with obstacle tiles.";
	public String REGION = "Layers with region tiles.";
	public String CHARACTERS = "List of character instances.";
	public String PARTY = "List of party slots.";
	public String SHOWGRID = "Show tile divisions.";
	public String NOAUTO = "For terrain layers. When checked, it uses the second-to-last row of the\n"
			+ "terrain's tileset for all tiles in this layer.";
	
	// Resize
	public String RESIZE = "Resize the field.";
	public String SIZEX = "New width.";
	public String SIZEY = "New height.";
	public String ALIGNX = "The tiles on horizontal axis are removed from/added to the opposite side.\n"
			+ "If 'Center' is selected, the tiles are equally distributed between left and right.";
	public String ALIGNY = "The tiles on vertical axis are removed from/added to the opposite side.\n"
			+ "If 'Center' is selected, the tiles are equally distributed between top and bottom.";
	
	// Character
	public String CHARPOS = "Character's initial position.";
	public String CHARDIR = "Character's initial direction. \n"
			+ "0 is facing right, 180 is facing left.";
	public String CHARACTER = "Character graphics.";
	public String ANIMATION = "The name of the initial animation of this character. \n"
			+ "Usually 'Idle'.";
	public String FRAME = "The initial frame of the animation. \n"
			+ "It's an index in the animation's pattern (not a column number).";
	public String ONLOAD = "Run this script on initialization.";
	public String ONCOLLIDE = "Run this script on collision with player or another character.";
	public String ONINTERACT = "Run this script when player interacts with this character.";
	public String SPEED = "Speed";
	public String CHARPERSISTENT = "When checked, the changed data will be saved in the field's save if the field is also persistent.";
	public String CHARPASSABLE = "When checked, the player and other characters can overpass it.";
	public String CHARVISIBLE = "If unchecked, the character starts off as invisible.";
	public String CHARPARTY = "The party slot associated with this character, in case it's a battle character.\n"
			+ "This will tell which troop this character belongs to, when the 'unit generation'\n"
			+ "setting of the party is set as 'field characters'.\n"
			+ "If left blank, it will be treated as a scenery object or non-battle NPC.";
	
	// Party
	public String PARTYPOS = "Top-left (when orthogonal) or left (when isometric/hexagonal) corner of the party's grid.\n"
			+ "The size of the grid is set in the config editor.";
	public String PARTYDIR = "The direction in which the characters in this party will be facing at the start.\n"
			+ "0 is facing right when the grid is orthogonal, or right-down when the grid is isometric/hexagonal.";
	public String PLAYERPARTY = "The party slot that will contain the player's troop.\n"
			+ "When none is selected, the player's party will be a random slot selected at the start of the battle.";
	public String PARTYGEN = "How the battle characters for each troop unit will be created on the field.\n"
			+ "Troop Units: a new battle character will be created for each unit in the troop's 'current' list.\n"
			+ "Field Characters: no new battle characters are created. Instead, the characters that are already on the field\n"
			+ "are attributed to each unit in the troop, according to their keys.\n"
			+ "The key of a characters must be the same as the key of the unit set in the 'Troops' tab for them to be associated to each other.";
	public String PARTYTROOPS = "Defines which troops can be spawned in this party slot.";
	public String MINLEVEL = "The minimum level that the lowest-level character must have for this troop to be available.";
	public String MAXLEVEL = "The maximum level that the lowest-level character must have for this troop to be available.";
	
	// Position
	public String POSITIONX = "X of the tile.";
	public String POSITIONY = "Y of the tile.";
	public String POSITIONH = "Height of the layer.";
	
	// Transition
	public String TRANSITIONS = "List of transitions to other fields.";
	public String DESTINATION = "The tile the player with be teleported to.";
	public String ORIGTILES = "The tile the player need to step on to be teleported.";
	public String FADEOUT = "Time in frames of the fading animation when the player steps on the origin tiles.\n"
			+ "Set as 0 to teleport instantly.";
	
	// }}

}
