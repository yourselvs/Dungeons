package yourselvs.dungeons;

import org.bukkit.plugin.java.JavaPlugin;

import yourselvs.dungeons.commands.CommandParser;
import yourselvs.dungeons.database.MongoDBStorage;
import yourselvs.dungeons.database.MongoHandler;
import yourselvs.dungeons.database.interfaces.IDatabase;
import yourselvs.dungeons.database.interfaces.IMongo;
import yourselvs.dungeons.dungeons.DungeonManager;
import yourselvs.dungeons.permissions.PermissionsManager;
import yourselvs.dungeons.records.RecordManager;
import yourselvs.dungeons.sessions.SessionManager;
import yourselvs.dungeons.utils.ConfigManager;
import yourselvs.dungeons.utils.DateFormatter;
import yourselvs.dungeons.utils.Messenger;

public class Dungeons extends JavaPlugin
{	
	public String version = "2.0";
	
	private IMongo mongo;
	private IDatabase db;
	private DateFormatter formatter;
	private DungeonManager dungeonManager;
	private SessionManager sessionManager;
	private RecordManager recordManager;
	private CommandParser commandParser;
	private ConfigManager configManager;
	private PermissionsManager permissions;
	private Messenger messenger;
    
    @Override
	public void onEnable() {
    	mongo = new MongoDBStorage(IMongo.textUri, "minecraft", "dungeon");
    	db = new MongoHandler(this, mongo);
    	
    	dungeonManager = new DungeonManager(this);
    	sessionManager = new SessionManager(this);
    	recordManager = new RecordManager(this);
    	configManager = new ConfigManager(this);
    	permissions = new PermissionsManager(this);
    	messenger = new Messenger(this);
    	
    	dungeonManager.loadDungeons();    	
    	sessionManager.loadSessions();
    	recordManager.loadRecords();
    	configManager.loadConfigs();
    	permissions.loadPermissions();
    	
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
    public ConfigManager getConfigManager() {return configManager;}
    public Messenger getMessenger() {return messenger;}
}
