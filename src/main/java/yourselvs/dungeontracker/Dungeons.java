package yourselvs.dungeontracker;

import org.bukkit.plugin.java.JavaPlugin;

import yourselvs.dungeontracker.commands.CommandParser;
import yourselvs.dungeontracker.database.MongoDBStorage;
import yourselvs.dungeontracker.database.MongoHandler;
import yourselvs.dungeontracker.database.interfaces.IDatabase;
import yourselvs.dungeontracker.database.interfaces.IMongo;
import yourselvs.dungeontracker.dungeons.DungeonManager;
import yourselvs.dungeontracker.records.RecordManager;
import yourselvs.dungeontracker.sessions.SessionManager;
import yourselvs.dungeontracker.utils.DateFormatter;

public class Dungeons extends JavaPlugin
{	
	private IMongo mongo;
	private IDatabase db;
	private DateFormatter formatter;
	private DungeonManager dungeonManager;
	private SessionManager sessionManager;
	private RecordManager recordManager;
	private CommandParser commandParser;
    
    @Override
	public void onEnable() {
    	mongo = new MongoDBStorage(IMongo.textUri, "minecraft", "dungeon");
    	db = new MongoHandler(mongo);
    	
    	dungeonManager = new DungeonManager(this);
    	sessionManager = new SessionManager(this);
    	recordManager = new RecordManager(this);
    	
    	dungeonManager.loadDungeons();    	
    	sessionManager.loadSessions();
    	recordManager.loadRecords();
    	
    	getCommand("dungeon").setExecutor(commandParser);
    	getCommand("dgn").setExecutor(commandParser);
    	getCommand("dt").setExecutor(commandParser);
    	getCommand("dungeontracker").setExecutor(commandParser);
    }
    
    public IDatabase getDB() {return db;}
    public DateFormatter getFormatter() {return formatter;}
    public DungeonManager getDungeonManager() {return dungeonManager;}
    public SessionManager getSessionManager() {return sessionManager;}
    public RecordManager getRecordManager() {return recordManager;}
    public CommandParser getCommandParser() {return commandParser;}
}
