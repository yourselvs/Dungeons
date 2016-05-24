package yourselvs.dungeontracker.dungeons;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;

import yourselvs.dungeontracker.Dungeons;
import yourselvs.dungeontracker.dungeons.Dungeon.Difficulty;

public class DungeonManager {
	private Dungeons plugin;
	
	public boolean canPickupItemDefault;
	public boolean canManipulateArmorStandDefault;
	public boolean canEnterBedDefault;
	public boolean canUseBucketDefault;
	public boolean canDropItemDefault;
	public boolean canChangeExperienceDefault;
	public boolean canFlyDefault;
	public boolean canSneakDefault;
	public boolean canSprintDefault;	
	
	private List<Dungeon> dungeons;
	
	/**
	 * Creates a manager that handles data for the dungeons.
	 * @param instance	The instance of the Dungeons plugin.
	 */
	public DungeonManager(Dungeons instance){
		this.plugin = instance;
		
		FileConfiguration cfg = plugin.getConfig();
		
		canPickupItemDefault = cfg.getBoolean("parameters.canPickupItem.default");
		canManipulateArmorStandDefault = cfg.getBoolean("parameters.canManipulateArmorStand.default");
		canEnterBedDefault = cfg.getBoolean("parameters.canEnterBed.default");
		canUseBucketDefault = cfg.getBoolean("parameters.canUseBucket.default");
		canDropItemDefault = cfg.getBoolean("parameters.canDropItem.default");
		canChangeExperienceDefault = cfg.getBoolean("parameters.canChangeExperience.default");
		canFlyDefault = cfg.getBoolean("parameters.canFly.default");
		canSneakDefault = cfg.getBoolean("parameters.canSneak.default");
		canSprintDefault = cfg.getBoolean("parameters.canSprint.default");
	}
	
	/**
	 * @return	The list of dungeons.
	 */
	public List<Dungeon> getDungeons() {return dungeons;}
	
	/**
	 * Adds a dungeon to the plugin.
	 * @param dungeon	The dungeon that is added.
	 */
	public void addDungeon(Dungeon dungeon){
		dungeons.add(dungeon);
		plugin.getDB().addDungeon(dungeon);
	}
	
	/**
	 * Removes a dungeon from the plugin.
	 * @param dungeon	The name of the dungeon that should be removed.
	 */
	public boolean removeDungeon(String dungeon){
		boolean found = false;
		for(int i = 0; i < dungeons.size(); i++)
			if(dungeons.get(i).getName().equalsIgnoreCase(dungeon)){
				dungeons.remove(i);
				plugin.getDB().removeDungeon(dungeon);
				found = true;
			}
		return found;
	}
	
	/**
	 * Loads the dungeons from the plugin database.
	 * @return	The list of dungeons found.
	 */
	public List<Dungeon> loadDungeons(){
    	return plugin.getDB().getDungeons();
    }
	
	/**
	 * Builds a dungeon object. The parameters are automatically set to the default that is in the config.
	 * @param name				The name of the dungeon.
	 * @param start				The start location of the dungeon.
	 * @param reward			A list of items the player gets as a reward.
	 * @param creator			The creator of the dungeon.
	 * @param difficulty		The difficulty of the dungeon.
	 * @param timesCompleted	The number of times the dungeon has been completed.
	 */
	public Dungeon buildDungeon(String name, Location start, List<Item> reward, String creator, Difficulty difficulty, int timesCompleted){
		Dungeon dungeon = new Dungeon(name, start, reward, creator, difficulty, timesCompleted);
		
		dungeon.canPickupItem = canPickupItemDefault;
		dungeon.canManipulateArmorStand = canManipulateArmorStandDefault;
		dungeon.canEnterBed = canEnterBedDefault;
		dungeon.canUseBucket = canUseBucketDefault;
		dungeon.canDropItem = canDropItemDefault;
		dungeon.canChangeExperience = canChangeExperienceDefault;
		dungeon.canFly = canFlyDefault;
		dungeon.canSneak = canSneakDefault;
		dungeon.canSprint = canSprintDefault;
		
		return dungeon;
	}
	
	/**
	 * Determines whether or not a dungeon exists in memory.
	 * @param name	The name of the dungeon to search for.
	 * @return		True if the dungeon is found, false if no dungeon is found.
	 */
	public boolean dungeonExists(String name){
		for(Dungeon dungeon : dungeons)
			if(dungeon.getName().equalsIgnoreCase(name))
				return true;
		return false;
	}
	
}
