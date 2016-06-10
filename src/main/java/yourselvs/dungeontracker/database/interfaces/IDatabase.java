package yourselvs.dungeontracker.database.interfaces;

import java.util.List;
import java.util.UUID;

import yourselvs.dungeontracker.dungeons.Dungeon;
import yourselvs.dungeontracker.records.Record;
import yourselvs.dungeontracker.sessions.Session;

public interface IDatabase {
	public List<Dungeon> getDungeons();
	public void removeDungeon(String dungeon);
	public void addDungeon(Dungeon dungeon);
	
	public List<Session> getSessions();
	public void removeSession(UUID uuid);
	public void addSession(Session session);
	
	public List<Record> getRecords();
	public void addRecord(Record record);
}
