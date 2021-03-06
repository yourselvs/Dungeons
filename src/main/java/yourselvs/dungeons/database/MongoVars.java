package yourselvs.dungeons.database;

public class MongoVars {
	public MongoVars(){
		
	}
	
	// Database commands
	public final String set = "$set";
	
	// Variable names
	public final String type = "type";
	public final String dungeon = "dungeon";
	public final String value = "value";
	public final String name = "name"; 
	public final String player = "player";
	public final String status = "status";
	public final String world = "world";
	public final String difficulty = "difficulty";
	public final String length = "length";
	public final String creator = "creator";
	public final String startTime = "startTime";
	public final String finishTime = "finishTime";
	public final String completionTime = "completionTime";
	public final String timesCompleted = "timesCompleted";
	public final String locX = "locationX";
	public final String locY = "locationY";
	public final String locZ = "locationZ";
	public final String yaw = "yaw";
	public final String pitch = "pitch";
	public final String item = "item";
	public final String rewards = "rewards";
	public final String leastSignificantBits = "leastSignificantBits";
	public final String mostSignificantBits = "mostSignificantBits";
	public final String start = "start";
	public final String sessions = "sessions";
	public final String version = "version";
	public final String command = "command";
	public final String checkpoint = "checkpoint";
	
	// Values
	public final String allowedValue = "allowed";
	
	// Types
	public final String commandType = "command";
	public final String recordType = "record";
	public final String paramType = "param";
	public final String dungeonType = "dungeon";
	public final String itemType = "item";
	public final String sessionType = "session";
	public final String versionType = "version";
	public final String checkpointType = "checkpoint";
	public final String playerCheckpointType = "playercheckpoint";
	
	// Statuses
	public final String completeStatus = "complete";
	public final String incompleteStatus = "incomplete";
	
	// Difficulties
	public final String easy = "EASY";
	public final String medium = "MEDIUM";
	public final String hard = "HARD";
	public final String insane = "INSANE";
	
	// Lengths
	public final String shortLength = "short";
	public final String average = "average";
	public final String longLength = "long";
	public final String marathon = "marathon";
	
	// Dungeon parameters
	public final String canPickupItem = "canPickupItem";
	public final String canManipulateArmorStand = "canManipulateArmorStand";
	public final String canEnterBed = "canEnterBed";
	public final String canUseBucket = "canUseBucket";
	public final String canDropItem = "canDropItem";
	public final String canChangeExperience = "canChangeExperience";
	public final String canFly = "canFly";
	public final String canSneak = "canSneak";
	public final String canSprint = "canSprint";
}
