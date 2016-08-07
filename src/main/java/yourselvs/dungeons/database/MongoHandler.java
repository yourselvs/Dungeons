 package yourselvs.dungeons.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;
import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.database.interfaces.IDatabase;
import yourselvs.dungeons.database.interfaces.IMongo;
import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.dungeons.Dungeon.Difficulty;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

public class MongoHandler implements IDatabase {
	private Dungeons plugin;
	private IMongo db;
	private MongoVars v;
	
	public MongoHandler(Dungeons instance, IMongo db){
		this.plugin = instance;
		this.db = db;
		v = new MongoVars();
	}
	
	@Override
	public List<Dungeon> getDungeons() {
		List<Dungeon> dungeons = new ArrayList<Dungeon>();
		Document docsToFind = new Document(v.type,v.dungeonType);
		List<Document> dungeonDocs = db.findDocuments(docsToFind);
		for(Document doc : dungeonDocs){
			String name = doc.getString(v.name);
			Location start = buildLocation(doc);
			String creator = doc.getString(v.creator);
			Difficulty difficulty = parseDifficulty(doc.getString(v.difficulty));
			int timesCompleted = doc.getInteger(v.timesCompleted, 0);
			Dungeon dungeon = new Dungeon(name, start, creator, difficulty, timesCompleted);
			Set<String> commandsAllowed = getCommandsAllowed(name);
			for(String command : commandsAllowed)
				dungeon.addCommandAllowed(command);
			dungeons.add(dungeon);
		}
		return dungeons;
	}
	
	@Override
	public void addDungeon(Dungeon dungeon) {
		Document doc = new Document(v.type, v.dungeonType)
				.append(v.name, dungeon.getName())
				.append(v.world, dungeon.getStart().getWorld().getName())
				.append(v.locX, dungeon.getStart().getX())
				.append(v.locY, dungeon.getStart().getY())
				.append(v.locZ, dungeon.getStart().getZ())
				.append(v.yaw, (int) dungeon.getStart().getYaw())
				.append(v.pitch, (int) dungeon.getStart().getPitch())
				.append(v.creator, dungeon.getCreator())
				.append(v.difficulty, dungeon.getDifficulty().toString())
				.append(v.timesCompleted, dungeon.getTimesCompleted());
		db.insertDocument(doc);
		
		for(String command : dungeon.getCommandsAllowed())
			addCommandAllowed(dungeon.getName(), command);
	}

	@Override
	public void removeDungeon(String dungeon) {
		db.deleteDocuments(new Document(v.type, v.dungeonType)
				.append(v.name, dungeon));
		
		db.deleteDocuments(new Document(v.type, v.command)
				.append(v.dungeon, dungeon));
	}
	
	@Override
	public Set<String> getCommandsAllowed(String dungeon) {
		Set<String> commands = new HashSet<String>();
		Document docsToFind = new Document(v.type, v.commandType)
				.append(v.dungeon, dungeon);
		List<Document> commandDocs = db.findDocuments(docsToFind);
		for(Document doc : commandDocs){
			String command = doc.getString(v.command);
			commands.add(command);
		}
		return commands;
	}
	
	@Override
	public void addCommandAllowed(String dungeon, String command){
		Document doc = new Document(v.type, v.commandType)
				.append(v.dungeon, dungeon)
				.append(v.command, command);
		db.insertDocument(doc);
	}
	
	@Override
	public void removeCommandAllowed(String dungeon, String command){
		Document doc = new Document(v.type, v.commandType)
				.append(v.dungeon, dungeon)
				.append(v.command, command);
		db.deleteDocuments(doc);
	}
	
	@Override
	public List<Session> getSessions() {
		List<Session> sessions = new ArrayList<Session>();
		Document docsToFind = new Document(v.type, v.sessionType);
		List<Document> sessionDocs = db.findDocuments(docsToFind);
		for(Document doc : sessionDocs){
			long leastSigBits = doc.getLong(v.leastSignificantBits);
			long mostSigBits = doc.getLong(v.mostSignificantBits);
			UUID uuid = new UUID(mostSigBits, leastSigBits);
			Dungeon dungeon = plugin.getDungeonManager().getDungeon(doc.getString(v.dungeon));
			Date start = new Date(doc.getLong(v.start));
			World world = plugin.getServer().getWorld(doc.getString(v.world));
			double x = doc.getDouble(v.locX);
			double y = doc.getDouble(v.locY);
			double z = doc.getDouble(v.locZ);
			float yaw = doc.getInteger(v.yaw);
			float pitch = doc.getInteger(v.pitch);
			Location location = new Location(world, x, y, z, yaw, pitch);
			
			sessions.add(new Session(uuid, dungeon, start, location));
		}
		return sessions;
	}
	
	@Override
	public void addSession(Session session) {
		Document doc = new Document(v.type, v.sessionType)
				.append(v.leastSignificantBits, session.getPlayer().getLeastSignificantBits())
				.append(v.mostSignificantBits, session.getPlayer().getMostSignificantBits())
				.append(v.dungeon, session.getDungeon().getName())
				.append(v.start, session.getStart().getTime())
				.append(v.world, session.getLocation().getWorld().getName())
				.append(v.locX, session.getLocation().getX())
				.append(v.locY, session.getLocation().getY())
				.append(v.locZ, session.getLocation().getZ())
				.append(v.yaw, (int) session.getLocation().getYaw())
				.append(v.pitch, (int) session.getLocation().getPitch());
		db.insertDocument(doc);
	}

	@Override
	public void removeSession(UUID uuid) {
		Document docToRemove = new Document(v.type, v.sessionType)
				.append(v.leastSignificantBits, uuid.getLeastSignificantBits())
				.append(v.mostSignificantBits, uuid.getMostSignificantBits());
		db.deleteDocuments(docToRemove);
	}

	@Override
	public List<Record> getRecords() {
		List<Record> records = new ArrayList<Record>();
		Document docsToFind = new Document(v.type, v.recordType);
		List<Document> docs = db.findDocuments(docsToFind);
		for(Document doc : docs){
			long leastSigBits = doc.getLong(v.leastSignificantBits);
			long mostSigBits = doc.getLong(v.mostSignificantBits);
			UUID uuid = new UUID(mostSigBits, leastSigBits);
			Dungeon dungeon = plugin.getDungeonManager().getDungeon(doc.getString(v.dungeon));
			Date finishTime = new Date(doc.getLong(v.finishTime));
			Date completionTime = new Date(doc.getLong(v.completionTime));
			
			records.add(new Record(uuid, dungeon, finishTime, completionTime));
		}
		return records;
	}

	@Override
	public void addRecord(Record record) {
		Document doc = new Document(v.type, v.recordType)
				.append(v.leastSignificantBits, record.getPlayer().getLeastSignificantBits())
				.append(v.mostSignificantBits, record.getPlayer().getMostSignificantBits())
				.append(v.dungeon, record.getDungeon().getName())
				.append(v.finishTime, record.getFinishTime().getTime())
				.append(v.completionTime, record.getTime().getTime());
		db.insertDocument(doc);
	}
	
	@Override
	public void removeRecord(String dungeon) {
		Document doc = new Document(v.type, v.recordType)
				.append(v.dungeon, dungeon);
		db.deleteDocuments(doc);
	}
	
	@Override
	public String getVersion(){
		Document doc = db.findDocument(new Document(v.type, v.versionType));
		return doc.getString(v.version);
	}
	
	private Location buildLocation(Document doc){
		String worldString = doc.getString(v.world);
		World world = plugin.getServer().getWorld(worldString);
		double x = doc.getDouble(v.locX);
		double y = doc.getDouble(v.locY);
		double z = doc.getDouble(v.locZ);
		float yaw = doc.getInteger(v.yaw);
		float pitch = doc.getInteger(v.pitch);
		Location loc = new Location(world, x, y, z, yaw, pitch);
		return loc;
	}
	
	private Difficulty parseDifficulty(String input){
		if(input.equalsIgnoreCase(v.easy))
			return Difficulty.EASY;
		if(input.equalsIgnoreCase(v.medium))
			return Difficulty.MEDIUM;
		if(input.equalsIgnoreCase(v.hard))
			return Difficulty.HARD;
		return Difficulty.INSANE;	
	}
}
