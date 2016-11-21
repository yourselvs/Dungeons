package yourselvs.dungeons.records;

import java.util.Date;
import java.util.UUID;

import yourselvs.dungeons.dungeons.Dungeon;

public class Record implements Comparable<Record>{
	public static boolean useFinish = false;
	
	private UUID player;
	private Dungeon dungeon;
	private Date finishTime;
	private Date time;	
	
	/**
	 * An object that stores information on a dungeon record.
	 * @param player			The player that completed the dungeon.
	 * @param dungeon			The dungeon completed.
	 * @param finishTime		The time that the dungeon was completed.
	 * @param time				The time that it took the player to complete the dungeon.
	 */
	public Record(UUID player, Dungeon dungeon, Date finishTime, Date time){
		this.player = player;
		this.dungeon = dungeon;
		this.finishTime = finishTime;
		this.time = time;
	}
	
	/**
	 * @return	the UUID of the player.
	 */
	public UUID getPlayer() {return player;}
	
	/**
	 * @return	the Dungeon that the record was completed in.
	 */
	public Dungeon getDungeon() {return dungeon;}
	
	/**
	 * @return	the time that the dungeon was completed.
	 */
	public Date getFinishTime() {return finishTime;}
	
	/**
	 * @return	the time it took the player to complete the dungeon.
	 */
	public Date getTime() {return time;}

	@Override
	public int compareTo(Record record) {
		if(useFinish)
			return (int) (finishTime.getTime() - record.finishTime.getTime());
		else
			return (int) (time.getTime() - record.time.getTime());
	}
}
