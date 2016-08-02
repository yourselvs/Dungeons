package yourselvs.dungeons.dungeons;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

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
	private List<ItemStack> reward;
	private String creator;
	private Difficulty difficulty;
	private int timesCompleted;
	
	public boolean canPickupItem;
	public boolean canManipulateArmorStand;
	public boolean canEnterBed;
	public boolean canUseBucket;
	public boolean canDropItem;
	public boolean canChangeExperience;
	public boolean canFly;
	public boolean canSneak;
	public boolean canSprint;
	
	/**
	 * An object that stores information on a dungeon.
	 * @param name				The name of the dungeon.
	 * @param start				The start location of the dungeon.
	 * @param reward2			A list of items the player gets as a reward.
	 * @param creator			The creator of the dungeon.
	 * @param difficulty		The difficulty of the dungeon.
	 * @param timesCompleted	The number of times the dungeon has been completed.
	 */
	public Dungeon(String name, Location start, List<ItemStack> reward, String creator, Difficulty difficulty, int timesCompleted){
		this.name = name;
		this.start = start;
		this.reward = reward;
		this.creator = creator;
		this.difficulty = difficulty;
		this.timesCompleted = timesCompleted;
	}
	
	public String getName() {return name;}
	public Location getStart() {return start;}
	public List<ItemStack> getReward() {return reward;}
	public String getCreator() {return creator;}
	public Difficulty getDifficulty() {return difficulty;}
	public int getTimesCompleted() {return timesCompleted;}
	
	public void setName(String name) {this.name = name;}
	public void setStart(Location start) {this.start = start;}
	public void setReward(List<ItemStack> reward) {this.reward = reward;}
	public void setCreator(String creator) {this.creator = creator;}
	public void setDifficulty(Difficulty difficulty) {this.difficulty = difficulty;}
	public void setTimesCompleted(int timesCompleted) {this.timesCompleted = timesCompleted;}
}
