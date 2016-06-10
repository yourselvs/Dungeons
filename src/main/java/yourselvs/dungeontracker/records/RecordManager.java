package yourselvs.dungeontracker.records;

import java.util.List;
import java.util.UUID;

import yourselvs.dungeontracker.Dungeons;

public class RecordManager {
	private Dungeons plugin;
	
	private List<Record> records;

	/**
	 * Creates a manager that handles dungeon records.
	 * @param instance	The instance of the dungeons plugin.
	 */
	public RecordManager(Dungeons instance){
		this.plugin = instance;
	}
	
	/**
	 * @return	The list of all records in memory.
	 */
	public List<Record> getRecords() {return records;}
	
	/**
	 * Adds a record to server memory and database.
	 * @param record	The record to add.
	 */
	public void addRecord(Record record){
		records.add(record);
		plugin.getDB().addRecord(record);
	}
	
	public void removeRecord(UUID player){
		for(int i = 0; i < records.size(); i++){
			if(records.get(i).getPlayer() == player){
				records.remove(i);
				i--;
			}
		}
	}
	
	public void removeRecord(String dungeon){
		for(int i = 0; i < records.size(); i++){
			if(records.get(i).getDungeon().getName().equalsIgnoreCase(dungeon)){
				records.remove(i);
				i--;
			}
		}
	}
	
	/**
	 * Loads records into server memory.
	 * @return	The list of records.
	 */
	public List<Record> loadRecords(){
		records = plugin.getDB().getRecords();
		return records;
	}
}
