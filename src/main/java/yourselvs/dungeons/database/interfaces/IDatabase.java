package yourselvs.dungeons.database.interfaces;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

public interface IDatabase {
	public List<Dungeon> getDungeons();
	public void removeDungeon(String dungeon);
	public void addDungeon(Dungeon dungeon);
	
	public Set<String> getCommandsAllowed(String dungeon);
	public void addCommandAllowed(String dungeon, String command);
	public void removeCommandAllowed(String dungeon, String command);

	public void setPlayerCheckpoint(UUID uuid, Location checkpoint);
	public void removePlayerCheckpoint(UUID uuid);
	
	public List<Session> getSessions();
	public void removeSession(UUID uuid);
	public void addSession(Session session);
	
	public List<Record> getRecords();
	public void removeRecord(String dungeon);
	public void addRecord(Record record);
	
	public String getVersion();
}
