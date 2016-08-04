package yourselvs.dungeons.dungeons;

import org.bukkit.Location;

public class Dungeon {
	public enum Difficulty{EASY, MEDIUM, HARD, INSANE;

	public static Difficulty parse(String string) {
		if(string.equalsIgnoreCase("easy"))
			return Difficulty.EASY;
		if(string.equalsIgnoreCase("medium"))
			return Difficulty.MEDIUM;
		if(string.equalsIgnoreCase("hard"))
			return Difficulty.HARD;
		return Difficulty.INSANE;
	}}
	
	private String name;
	private Location start;
	private String creator;
	private Difficulty difficulty;
	private int timesCompleted;
	
	/**
	 * An object that stores information on a dungeon.
	 * @param name				The name of the dungeon.
	 * @param start				The start location of the dungeon.
	 * @param reward2			A list of items the player gets as a reward.
	 * @param creator			The creator of the dungeon.
	 * @param difficulty		The difficulty of the dungeon.
	 * @param timesCompleted2 
	 * @param timesCompleted	The number of times the dungeon has been completed.
	 */
	public Dungeon(String name, Location start, String creator, Difficulty difficulty) {
		this.name = name;
		this.start = start;
		this.creator = creator;
		this.difficulty = difficulty;
		this.timesCompleted = 0;
	}
	
	/**
	 * An object that stores information on a dungeon.
	 * @param name				The name of the dungeon.
	 * @param start				The start location of the dungeon.
	 * @param reward2			A list of items the player gets as a reward.
	 * @param creator			The creator of the dungeon.
	 * @param difficulty		The difficulty of the dungeon.
	 * @param timesCompleted2 
	 * @param timesCompleted	The number of times the dungeon has been completed.
	 */
	public Dungeon(String name, Location start, String creator, Difficulty difficulty, int timesCompleted){
		this.name = name;
		this.start = start;
		this.creator = creator;
		this.difficulty = difficulty;
		this.timesCompleted = timesCompleted;
	}
	
	public String getName() {return name;}
	public Location getStart() {return start;}
	public String getCreator() {return creator;}
	public Difficulty getDifficulty() {return difficulty;}
	public int getTimesCompleted() {return timesCompleted;}
	
	public void setName(String name) {this.name = name;}
	public void setStart(Location start) {this.start = start;}
	public void setCreator(String creator) {this.creator = creator;}
	public void setDifficulty(Difficulty difficulty) {this.difficulty = difficulty;}
	public void setTimesCompleted(int timesCompleted) {this.timesCompleted = timesCompleted;}
}
