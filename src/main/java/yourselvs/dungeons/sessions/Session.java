package yourselvs.dungeons.sessions;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Location;
import yourselvs.dungeons.dungeons.Dungeon;

public class Session {
	private UUID player;
	private Dungeon dungeon;
	private Date start;
	private Location location;
	private Location checkpoint;
	
	/**
	 * An object that stores information on a player currently in a dungeon.
	 * 
	 * @param uuid		The UUID of the player.
	 * @param dungeon	The dungeon the player is in.
	 * @param start		The time the player started.
	 * @param location	The location the player was in upon staring the dungeon
	 */
	public Session(UUID player, Dungeon dungeon, Date start, Location location, Location checkpoint){
		this.player = player;
		this.dungeon = dungeon;
		this.start = start;
		this.location = location;
		this.checkpoint = checkpoint;
	}

	public UUID getPlayer() {return player;}
	public Dungeon getDungeon() {return dungeon;}
	public Date getStart() {return start;}
	public Location getLocation() {return location;}
	public Location getCheckpoint() {return checkpoint;}
	
	public void setCheckpoint(Location location) {
		this.checkpoint = location;
	}
}
