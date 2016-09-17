package yourselvs.dungeons.records;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.dungeons.Dungeon;

public class RecordManager {
	private Dungeons plugin;
	
	private List<Record> records;

	/**
	 * Creates a manager that handles dungeon records.
	 * @param instance	The instance of the dungeons plugin.
	 */
	public RecordManager(Dungeons instance){
		this.plugin = instance;
		records = new ArrayList<Record>();
	}
	
	/**
	 * @return	The list of all records in memory.
	 */
	public List<Record> getRecords() {return records;}
	
	/**
	 * Searches for records from a specific dungeon.
	 * @param dungeon	The name of the dungeon to search for.
	 * @return			The list of records found.
	 */
	public List<Record> getRecords(Dungeon dungeon) {
		List<Record> records = new ArrayList<Record>();
		
		for(Record record : this.records)
			if(record.getDungeon().getName().equalsIgnoreCase(dungeon.getName()))
				records.add(record);
		
		return records;
	}
	
	
	/**
	 * Searches for records from a specific player.
	 * @param uuid	The UUID of the player to search for.
	 * @return		The list of records found.
	 */
	public List<Record> getRecords(UUID uuid) {
		List<Record> records = new ArrayList<Record>();
		
		for(Record record : this.records)
			if(record.getPlayer().compareTo(uuid) == 0)
				records.add(record);
		return records;
	}
	
	public List<Record> getRecords(Dungeon dungeon, UUID uuid) {
		List<Record> records = new ArrayList<Record>();
		
		for(Record record : this.records){
			UUID player = record.getPlayer();
			int compare = player.compareTo(uuid);
			boolean compareBool = compare == 0;
			
			Dungeon dungeonTemp = record.getDungeon();
			String name = dungeonTemp.getName();
			String otherName = dungeon.getName();
			boolean dungeonBool = name.equalsIgnoreCase(otherName);
			if(compareBool && dungeonBool)
				records.add(record);
		}
		return records;
	}
	
	public Record getFastestRecord(Dungeon dungeon){
		List<Record> records = getRecords(dungeon);
		Collections.sort(records);
		return records.get(0);
	}
	
	public Record getFastestRecord(Dungeon dungeon, UUID player){
		List<Record> records = getRecords(dungeon, player);
		Record record = null;
		if(!records.isEmpty())
			record = records.get(0);
		for(Record rcrd : records)
			if(rcrd.getTime().getTime() < record.getTime().getTime())
				record = rcrd;
		return record;
	}
	
	/**
	 * Adds a record to server memory and database.
	 * @param record	The record to add.
	 */
	public void addRecord(Record record){
		records.add(record);
		new Thread(new Runnable() {
	        public void run(){
	        	plugin.getDB().addRecord(record);
	        }
	    }).start();
	}
	
	public void removeRecord(String dungeon){
		for(int i = 0; i < records.size(); i++){
			if(records.get(i).getDungeon().getName().equalsIgnoreCase(dungeon)){
				records.remove(i);
				
				i--;
			}
			new Thread(new Runnable() {
		        public void run(){
		        	plugin.getDB().removeRecord(dungeon);
		        }
		    }).start();
		}
	}
	
	/**
	 * Loads records into server memory.
	 */
	public void loadRecords(){
		records = plugin.getDB().getRecords();
	}
	
	public static List<Record> removeDuplicatePlayers(List<Record> records){
		Set<UUID> players = new HashSet<UUID>();
		
		for(int i = 0; i < records.size(); i++){
			Record record = records.get(i);
			
			if(players.contains(record.getPlayer())){
				records.remove(i);
				i--;
			}
			else
				players.add(record.getPlayer());
		}
		
		return records;
	}
}
