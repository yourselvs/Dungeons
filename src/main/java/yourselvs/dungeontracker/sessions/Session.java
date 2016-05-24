package yourselvs.dungeontracker.sessions;

import java.util.Date;
import java.util.UUID;

import org.bukkit.inventory.Inventory;

import yourselvs.dungeontracker.dungeons.Dungeon;

public class Session {
	private UUID player;
	private Dungeon dungeon;
	private Date start;
	private Inventory inventory;
	
	/**
	 * An object that stores information on a player currently in a dungeon.
	 * 
	 * @param uuid		The UUID of the player.
	 * @param dungeon	The dungeon the player is in.
	 * @param start		The time the player started.
	 * @param inventory	The inventory the player had upon starting the dungeon
	 */
	public Session(UUID player, Dungeon dungeon, Date start, Inventory inventory){
		this.player = player;
		this.dungeon = dungeon;
		this.start = start;
		this.inventory = inventory;
	}
	
	public UUID getPlayer() {return player;}
	public Dungeon getDungeon() {return dungeon;}
	public Date getStart() {return start;}
	public Inventory getInventory() {return inventory;}
}
