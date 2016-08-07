package yourselvs.dungeons.dungeons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.dungeons.Dungeon.Difficulty;

public class DungeonManager {
	private Dungeons plugin;
	
	private List<Dungeon> dungeons;
	
	/**
	 * Creates a manager that handles data for the dungeons.
	 * @param instance	The instance of the Dungeons plugin.
	 */
	public DungeonManager(Dungeons instance){
		this.plugin = instance;
		dungeons = new ArrayList<Dungeon>();
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
	
	public void addCommandAllowed(String dungeon, String command){
		getDungeon(dungeon).addCommandAllowed(command);
		plugin.getDB().addCommandAllowed(dungeon, command);
	}
	
	public void removeCommandAllowed(String dungeon, String command){
		getDungeon(dungeon).removeCommandAllowed(command);
		plugin.getDB().removeCommandAllowed(dungeon, command);
	}
	
	/**
	 * Removes a dungeon from the plugin.
	 * @param dungeon	The name of the dungeon that should be removed.
	 */
	public boolean removeDungeon(String dungeon){
		boolean found = false;
		for(int i = 0; i < dungeons.size(); i++)
			if(dungeons.get(i).getName().equalsIgnoreCase(dungeon)){
				plugin.getSessionManager().removeSession(dungeon);
				dungeons.remove(i);
				plugin.getDB().removeDungeon(dungeon);
				found = true;
				break;
			}
		return found;
	}
	
	/**
	 * Loads the dungeons from the plugin database.
	 * @return	The list of dungeons found.
	 */
	public void loadDungeons(){
		dungeons = plugin.getDB().getDungeons();
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
	public Dungeon buildDungeon(String name, Location start, String creator, Difficulty difficulty){
		Dungeon dungeon = new Dungeon(name, start, creator, difficulty);
		
		return dungeon;
	}
	
	public Dungeon getDungeon(String name){
		Dungeon dungeon = null;
		for(Dungeon dgn : dungeons)
			if(dgn.getName().equalsIgnoreCase(name))
				dungeon = dgn;
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
