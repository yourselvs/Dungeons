package yourselvs.dungeons;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import yourselvs.dungeons.commands.CommandManager;
import yourselvs.dungeons.commands.CommandParser;
import yourselvs.dungeons.database.MongoDBStorage;
import yourselvs.dungeons.database.MongoHandler;
import yourselvs.dungeons.database.interfaces.IDatabase;
import yourselvs.dungeons.database.interfaces.IMongo;
import yourselvs.dungeons.dungeons.DungeonManager;
import yourselvs.dungeons.listeners.DungeonListener;
import yourselvs.dungeons.permissions.PermissionsManager;
import yourselvs.dungeons.records.RecordManager;
import yourselvs.dungeons.sessions.SessionManager;
import yourselvs.dungeons.utils.DateFormatter;
import yourselvs.dungeons.utils.Messenger;

public class Dungeons extends JavaPlugin
{	
	public final String version = "2.0";
	public final String creator = "yourselvs";
	
	private String prefix = "[" + ChatColor.RED + ChatColor.BOLD + "DGN" + ChatColor.RESET + "] ";
	private String linkPrefix = ChatColor.AQUA + "[" + ChatColor.RED + ChatColor.BOLD + "DGN" + ChatColor.RESET + ChatColor.AQUA + "]" + ChatColor.RESET + " ";
	private String unformattedPrefix = "[DGN] ";
	
	private IMongo mongo;
	private IDatabase db;
	private DateFormatter formatter;
	private DungeonManager dungeonManager;
	private SessionManager sessionManager;
	private RecordManager recordManager;
	private CommandParser commandParser;
	private CommandManager commandManager;
	private PermissionsManager permissions;
	private Messenger messenger;
	private DungeonListener listener;
    
	// TODO Make sure all UUID comparisons are fixed and not using "=="
	// TODO Remove parameter functionality
	// TODO Set parsers to run in threads
	// TODO Add documentation to methods
	// TODO Add maximum number of people in dungeon
	// TODO Add permissions to dungeons
	// TODO Set up database version doc
	// TODO Add dungeon create event
	// TODO Add commandsAllowed to Dungeon class
	// TODO Set mongo accessors in threads
	// TODO Remember if players were flying when starting a dungeon
	// TODO Add reset command to dungeons to remove records
	
    @Override
	public void onEnable() {
    	mongo = new MongoDBStorage(IMongo.textUri, "minecraft", "dungeon");
    	db = new MongoHandler(this, mongo);
    	
    	dungeonManager = new DungeonManager(this);
    	sessionManager = new SessionManager(this);
    	recordManager = new RecordManager(this);
    	commandManager = new CommandManager(this);
    	permissions = new PermissionsManager(this);
    	messenger = new Messenger(this, prefix, linkPrefix, unformattedPrefix);
    	listener = new DungeonListener(this);
    	
    	messenger.setPrefix(prefix);
    	
    	dungeonManager.loadDungeons();    	
    	sessionManager.loadSessions();
    	recordManager.loadRecords();
    	permissions.loadPermissions();
    	
    	getCommand("dungeon").setExecutor(commandParser);
    	getCommand("dgn").setExecutor(commandParser);
    	getCommand("dt").setExecutor(commandParser);
    	getCommand("dungeontracker").setExecutor(commandParser);
    	
    	checkVersion();
    }
    
    public IDatabase getDB() {return db;}
    public DateFormatter getFormatter() {return formatter;}
    public DungeonManager getDungeonManager() {return dungeonManager;}
    public SessionManager getSessionManager() {return sessionManager;}
    public RecordManager getRecordManager() {return recordManager;}
    public CommandParser getCommandParser() {return commandParser;}
    public CommandManager getCommandManager() {return commandManager;}
    public Messenger getMessenger() {return messenger;}
    public DungeonListener getDungeonListener() {return listener;}
    
    private void checkVersion(){
    	if(!version.equalsIgnoreCase(db.getVersion())){
    		getServer().getLogger().log(Level.WARNING, "There is a newer version of the dungeons plugin available. Contact yourselvs for the new version.");;
    	}
    }
    
    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Cmd cmd = new Cmd(sender, command, label, args);
		if(cmd.args.length == 0)
			commandParser.parseDungeon(cmd);
		else{
			String subcmd = args[0];

			if(subcmd.equalsIgnoreCase("join")){
				commandParser.parseJoin(cmd);
			}
			else if(subcmd.equalsIgnoreCase("leave")){
				commandParser.parseLeave(cmd);
			}
			else if(subcmd.equalsIgnoreCase("list")){
				commandParser.parseList(cmd);
			}
			else if(subcmd.equalsIgnoreCase("help")){
				commandParser.parseHelp(cmd);
			}
			else if(subcmd.equalsIgnoreCase("forcejoin")){
				commandParser.parseForceJoin(cmd);
			}
			else if(subcmd.equalsIgnoreCase("forceleave")){
				commandParser.parseForceLeave(cmd);
			}
			else if(subcmd.equalsIgnoreCase("complete")){
				commandParser.parseComplete(cmd);
			}
			else if(subcmd.equalsIgnoreCase("top")){
				commandParser.parseTop(cmd);
			}
			else if(subcmd.equalsIgnoreCase("history")){
				commandParser.parseHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("dhistory") || subcmd.equalsIgnoreCase("dungeonhistory")){
				commandParser.parseDungeonHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("phistory") || subcmd.equalsIgnoreCase("playerhistory")){
				commandParser.parsePlayerHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("record")){
				commandParser.parseRecord(cmd);
			}
			else if(subcmd.equalsIgnoreCase("precord") || subcmd.equalsIgnoreCase("playerrecord")){
				commandParser.parsePlayerRecord(cmd);
			}
			else if(subcmd.equalsIgnoreCase("rank")){
				commandParser.parseRank(cmd);
			}
			else if(subcmd.equalsIgnoreCase("create")){
				commandParser.parseCreate(cmd);
			}
			else if(subcmd.equalsIgnoreCase("delete")){
				commandParser.parseDelete(cmd);
			}
			else if(subcmd.equalsIgnoreCase("cmd") || subcmd.equalsIgnoreCase("command")){
				commandParser.parseCommand(cmd);
			}
			else if(subcmd.equalsIgnoreCase("view")){
				commandParser.parseViewDungeon(cmd);
			}
			else
				commandParser.parseCommandNotFound(cmd);
		}
		return true;
	}
}
