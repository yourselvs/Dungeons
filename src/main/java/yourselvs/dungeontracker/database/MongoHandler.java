 package yourselvs.dungeontracker.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import yourselvs.dungeontracker.Dungeons;
import yourselvs.dungeontracker.database.interfaces.IDatabase;
import yourselvs.dungeontracker.database.interfaces.IMongo;
import yourselvs.dungeontracker.dungeons.Dungeon;
import yourselvs.dungeontracker.dungeons.Dungeon.Difficulty;
import yourselvs.dungeontracker.records.Record;
import yourselvs.dungeontracker.sessions.Session;
import yourselvs.dungeontracker.utils.ConfigManager.ConfigFile;

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
			List<ItemStack> reward = buildItems(name);
			String creator = doc.getString(v.creator);
			Difficulty difficulty = parseDifficulty(doc.getString(v.difficulty));
			int timesCompleted = doc.getInteger(v.timesCompleted, 0);
			Dungeon dungeon = new Dungeon(name, start, reward, creator, difficulty, timesCompleted);
			dungeon.canPickupItem = doc.getBoolean(v.canPickupItem);
			dungeon.canManipulateArmorStand = doc.getBoolean(v.canManipulateArmorStand);
			dungeon.canEnterBed = doc.getBoolean(v.canEnterBed);
			dungeon.canUseBucket = doc.getBoolean(v.canUseBucket);
			dungeon.canDropItem = doc.getBoolean(v.canDropItem);
			dungeon.canChangeExperience = doc.getBoolean(v.canChangeExperience);
			dungeon.canFly = doc.getBoolean(v.canFly);
			dungeon.canSneak = doc.getBoolean(v.canSneak);
			dungeon.canSprint = doc.getBoolean(v.canSprint);
			dungeons.add(dungeon);
		}
		return dungeons;
	}

	@Override
	public void removeDungeon(String dungeon) {
		db.deleteDocuments(new Document(v.type, v.dungeonType)
				.append(v.name, dungeon));
	}

	@Override
	public void addDungeon(Dungeon dungeon) {
		Document doc = new Document(v.type, v.dungeonType)
				.append(v.name, dungeon.getName())
				.append(v.world, dungeon.getStart().getWorld())
				.append(v.locX, dungeon.getStart().getX())
				.append(v.locY, dungeon.getStart().getY())
				.append(v.locZ, dungeon.getStart().getZ())
				.append(v.yaw, dungeon.getStart().getYaw())
				.append(v.pitch, dungeon.getStart().getPitch())
				.append(v.creator, dungeon.getCreator())
				.append(v.difficulty, dungeon.getDifficulty().toString())
				.append(v.timesCompleted, dungeon.getTimesCompleted())
				.append(v.canPickupItem, dungeon.canPickupItem)
				.append(v.canManipulateArmorStand, dungeon.canManipulateArmorStand)
				.append(v.canEnterBed, dungeon.canEnterBed)
				.append(v.canUseBucket, dungeon.canUseBucket)
				.append(v.canDropItem, dungeon.canDropItem)
				.append(v.canChangeExperience, dungeon.canChangeExperience)
				.append(v.canFly, dungeon.canFly)
				.append(v.canSneak, dungeon.canSneak)
				.append(v.canSprint, dungeon.canSprint);
		db.insertDocument(doc);
		plugin.getConfigManager().getConfig(ConfigFile.CONFIG).set(v.rewards + "." + dungeon.getName(), dungeon.getReward());
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
			Inventory inventory = (Inventory) plugin.getConfig().get(v.sessions + "." + uuid);
			
			sessions.add(new Session(uuid, dungeon, start, location, inventory));
		}
		return sessions;
	}
	
	@Override
	public void addSession(Session session) {
		Document doc = new Document(v.type, v.sessionType)
				.append(v.leastSignificantBits, session.getPlayer().getLeastSignificantBits())
				.append(v.mostSignificantBits, session.getPlayer().getMostSignificantBits())
				.append(v.dungeon, session.getDungeon().getName())
				.append(v.start, session.getStart().getTime());
		db.insertDocument(doc);
		plugin.getConfig().set(v.sessions + "." + session.getPlayer(), session.getInventory());
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
				.append(v.completionTime, record.getCompletionTime().getTime());
		db.insertDocument(doc);
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
	
	private List<ItemStack> buildItems(String dungeon){
		List<ItemStack> items = new ArrayList<ItemStack>();
		/*
		Document itemsToFind = new Document(v.type, v.itemType)
				.append(v.dungeon, dungeon);
		List<Document> itemDocs = db.findDocuments(itemsToFind);
		for(Document itemDoc : itemDocs){
			Document doc = itemDoc.get(v.item, Document.class);
			String json = doc.toJson();
			Item item = gson.fromJson(json, Item.class);
			items.add(item);
		}
		*/
		List<Map<String, Object>> itemList = (List<Map<String, Object>>) plugin.getConfigManager().getConfig(ConfigFile.CONFIG).get(/* path */ v.rewards + "." + dungeon);
		for(Map<String, Object> itemMap : itemList){
			items.add(ItemStack.deserialize(itemMap));
		}
		return items;
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
