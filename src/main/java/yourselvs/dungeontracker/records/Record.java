package yourselvs.dungeontracker.records;

import java.util.Date;
import java.util.UUID;

import yourselvs.dungeontracker.dungeons.Dungeon;

public class Record {
	private UUID player;
	private Dungeon dungeon;
	private Date finishTime;
	private Date completionTime;	
	
	/**
	 * An object that stores information on a dungeon record.
	 * @param player			The player that completed the dungeon.
	 * @param dungeon			The dungeon completed.
	 * @param finishTime		The time that the dungeon was completed.
	 * @param completionTime	The time that it took the player to complete the dungeon.
	 */
	public Record(UUID player, Dungeon dungeon, Date finishTime, Date completionTime){
		this.player = player;
		this.dungeon = dungeon;
		this.finishTime = finishTime;
		this.completionTime = completionTime;
	}
	
	public UUID getPlayer() {return player;}
	public Dungeon getDungeon() {return dungeon;}
	public Date getFinishTime() {return finishTime;}
	public Date getCompletionTime() {return completionTime;}
}
