package yourselvs.dungeons.database.interfaces;

import java.util.List;
import java.util.UUID;

import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

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
