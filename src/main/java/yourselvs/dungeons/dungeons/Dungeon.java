package yourselvs.dungeons.dungeons;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

public class Dungeon {
	public enum Difficulty {
		EASY, MEDIUM, HARD, INSANE;
		
		public static Difficulty parse(String string) {
			if(string.equalsIgnoreCase("easy"))
				return EASY;
			if(string.equalsIgnoreCase("medium"))
				return MEDIUM;
			if(string.equalsIgnoreCase("hard"))
				return HARD;
			return INSANE;
		}
	}
	
	public enum Length {
		SHORT, AVERAGE, LONG, MARATHON;
		
		public static Length parse(String string) {
			if(string.equalsIgnoreCase("short"))
				return SHORT;
			if(string.equalsIgnoreCase("average"))
				return AVERAGE;
			if(string.equalsIgnoreCase("long"))
				return LONG;
			return MARATHON;
		}
	}
	
	private String name;
	private Location start;
	private String creator;
	private Difficulty difficulty;
	private Length length;
	private int timesCompleted;
	private Set<String> commandsAllowed;
	
	/**
	 * An object that stores information on a dungeon.
	 * @param name				The name of the dungeon.
	 * @param start				The start location of the dungeon.
	 * @param reward2			A list of items the player gets as a reward.
	 * @param creator			The creator of the dungeon.
	 * @param difficulty		The difficulty of the dungeon.
	 * @param length			The length of the dungeon.
	 * @param timesCompleted2 
	 * @param timesCompleted	The number of times the dungeon has been completed.
	 */
	public Dungeon(String name, Location start, String creator, Difficulty difficulty, Length length) {
		this.name = name;
		this.start = start;
		this.creator = creator;
		this.difficulty = difficulty;
		this.length = length;
		this.timesCompleted = 0;
		commandsAllowed = new HashSet<String>();
	}
	
	/**
	 * An object that stores information on a dungeon.
	 * @param name				The name of the dungeon.
	 * @param start				The start location of the dungeon.
	 * @param reward2			A list of items the player gets as a reward.
	 * @param creator			The creator of the dungeon.
	 * @param difficulty		The difficulty of the dungeon.
	 * @param length			The length of the dungeon
	 * @param timesCompleted2 
	 * @param timesCompleted	The number of times the dungeon has been completed.
	 */
	public Dungeon(String name, Location start, String creator, Difficulty difficulty, Length length, int timesCompleted){
		this.name = name;
		this.start = start;
		this.creator = creator;
		this.difficulty = difficulty;
		this.length = length;
		this.timesCompleted = timesCompleted;
		commandsAllowed = new HashSet<String>();
	}
	
	public String getName() {return name;}
	public Location getStart() {return start;}
	public String getCreator() {return creator;}
	public Difficulty getDifficulty() {return difficulty;}
	public Length getLength() {return length;}
	public int getTimesCompleted() {return timesCompleted;}
	public Set<String> getCommandsAllowed() {return commandsAllowed;}
	
	public void setName(String name) {this.name = name;}
	public void setStart(Location start) {this.start = start;}
	public void setCreator(String creator) {this.creator = creator;}
	public void setDifficulty(Difficulty difficulty) {this.difficulty = difficulty;}
	public void setLength(Length length) {this.length = length;}
	public void setTimesCompleted(int timesCompleted) {this.timesCompleted = timesCompleted;}
	
	public void addCommandAllowed(String command){commandsAllowed.add(command);}
	public void removeCommandAllowed(String command){commandsAllowed.remove(command);}
	
	public boolean isCommandAllowed(String command){
		if(commandsAllowed.contains(command))
			return true;
		return false;
	}
}
