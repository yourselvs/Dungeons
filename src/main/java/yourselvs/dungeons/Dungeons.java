package yourselvs.dungeons;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import yourselvs.dungeons.commands.Cmd;
import yourselvs.dungeons.commands.CommandManager;
import yourselvs.dungeons.commands.CommandParser;
import yourselvs.dungeons.database.MongoDBStorage;
import yourselvs.dungeons.database.MongoHandler;
import yourselvs.dungeons.database.interfaces.IDatabase;
import yourselvs.dungeons.database.interfaces.IMongo;
import yourselvs.dungeons.dungeons.DungeonManager;
import yourselvs.dungeons.listeners.CommandListener;
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
	
	private boolean dbConfigured = false;
	
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
	private PermissionsManager permissionsManager;
	private Messenger messenger;
	private DungeonListener dungeonListener;
	private CommandListener commandListener;
	
	private boolean useDungeons;
	
	// TODO Add dungeons that can only be completed while in a vehicle
	// TODO Add documentation to methods
	// TODO Add maximum number of people in dungeon
	// TODO Add permissions to dungeons
	// TODO Add dungeon create event
	// TODO Remember if players were flying when starting a dungeon
	// TODO Add reset command to dungeons to remove records
	
    @Override
	public void onEnable() {
    	saveDefaultConfig();
    	FileConfiguration config = getConfig();
    	
    	useDungeons = config.getBoolean("dungeons");
    	
    	String textUri = config.getString("textUri");
    	String dbName = config.getString("dbName");
    	String collectionName = config.getString("collectionName");
    	
    	if(textUri.equalsIgnoreCase("not configured"))
    		return;
    	if(dbName.equalsIgnoreCase("not configured"))
    		return;
    	if(collectionName.equalsIgnoreCase("not configured"))
    		return;
    	
    	commandManager = new CommandManager(this);
    	commandParser = new CommandParser(this);
    	messenger = new Messenger(this, prefix, linkPrefix, unformattedPrefix);
    	
    	try {
	    	mongo = new MongoDBStorage(textUri, dbName, collectionName);
	    	db = new MongoHandler(this, mongo);
	    } 
    	catch (Exception e) {
    		e.printStackTrace();
    		return;
    	}
    	
    	dbConfigured = true;
    	
    	formatter = new DateFormatter();
    	permissionsManager = new PermissionsManager(this);
    	dungeonManager = new DungeonManager(this);
    	sessionManager = new SessionManager(this);
    	recordManager = new RecordManager(this);
    	dungeonListener = new DungeonListener(this);
    	commandListener = new CommandListener(this);
    	
	    dungeonManager.loadDungeons();    	
	    sessionManager.loadSessions();
	    recordManager.loadRecords();
    }
    
    public IDatabase getDB() {return db;}
    public DateFormatter getFormatter() {return formatter;}
    public DungeonManager getDungeonManager() {return dungeonManager;}
    public SessionManager getSessionManager() {return sessionManager;}
    public RecordManager getRecordManager() {return recordManager;}
    public CommandParser getCommandParser() {return commandParser;}
    public CommandManager getCommandManager() {return commandManager;}
    public PermissionsManager getPermissionsManager() {return permissionsManager;}
    public Messenger getMessenger() {return messenger;}
    public DungeonListener getDungeonListener() {return dungeonListener;}
    public CommandListener getCommandListener() {return commandListener;}
    public boolean useDungeons() {return useDungeons;}
    
    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Cmd cmd = new Cmd(sender, command, label, args);
		
		if(!dbConfigured)
			sender.sendMessage(prefix + "Your plugin database is not properly configured. Ask your server administrators to fix this issue.");
		else{
			if(cmd.args.length == 0){
				new Thread(new Runnable() {
			        public void run(){
			        	commandParser.parseDungeon(cmd);
			        }
			    }).start();
			}
			else{
				String subcmd = args[0];
				
				if(subcmd.equalsIgnoreCase("join")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseJoin(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("leave")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseLeave(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("list")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseList(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("help")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseHelp(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("forcejoin")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseForceJoin(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("forceleave")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseForceLeave(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("complete")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseComplete(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("top")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseTop(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("history")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseHistory(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("dhistory") || subcmd.equalsIgnoreCase("dungeonhistory")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseDungeonHistory(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("phistory") || subcmd.equalsIgnoreCase("playerhistory")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parsePlayerHistory(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("record")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseRecord(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("precord") || subcmd.equalsIgnoreCase("playerrecord")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parsePlayerRecord(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("rank")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseRank(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("create")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseCreate(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("delete")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseDelete(cmd);
					}
				}
				else if(subcmd.equalsIgnoreCase("cmd") || subcmd.equalsIgnoreCase("command")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseCommand(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("view")){
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseViewDungeon(cmd);
				        }
				    }).start();
				}
				else if(subcmd.equalsIgnoreCase("checkpoint")){
					if(!useDungeons) {
						commandParser.parseNotInUse(cmd);
					}
					else {
						commandParser.parseCheckpoint(cmd);
					}
				}
				else{
					new Thread(new Runnable() {
				        public void run(){
				        	commandParser.parseCommandNotFound(cmd);
				        }
				    }).start();
				}
			}	
		}
		return true;
	}
}
