package yourselvs.dungeontracker.database;

import java.util.List;
import java.util.UUID;

import yourselvs.dungeontracker.database.interfaces.IDatabase;
import yourselvs.dungeontracker.database.interfaces.IMongo;
import yourselvs.dungeontracker.dungeons.Dungeon;
import yourselvs.dungeontracker.records.Record;
import yourselvs.dungeontracker.sessions.Session;

public class MongoHandler implements IDatabase {
	private IMongo db;
	
	public MongoHandler(IMongo db){
		this.db = db;
	}
	
	// TODO Build mongo handler
	
	@Override
	public List<Dungeon> getDungeons() {
		return null;
	}

	@Override
	public Dungeon removeDungeon(String dungeon) {
		return null;
	}

	@Override
	public void addDungeon(Dungeon dungeon) {
	}
	
	@Override
	public List<Session> getSessions() {
		return null;
	}
	
	@Override
	public void addSession(Session session) {
	}

	@Override
	public Session removeSession(UUID uuid) {
		return null;
	}

	@Override
	public List<Record> getRecords() {
		return null;
	}

	@Override
	public void addRecord(Record record) {
	}
}
