package yourselvs.dungeons.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.dungeons.Dungeon.Difficulty;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

public class CommandParser{
	private Dungeons plugin;
	
	public CommandParser(Dungeons instance){
		this.plugin = instance;
	}
	
	public void parseDungeon(Cmd command){
		// *
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.info")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		
		plugin.getCommandManager().viewInfo(player);
	}
	
	public void parseHelp(Cmd command){
		// help
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.info")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		
		plugin.getCommandManager().viewHelp(player);
	}
	
	public void parseList(Cmd command){
		// list
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.join")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		
		plugin.getCommandManager().listDungeons(player);
	}
	
	public void parseJoin(Cmd command){
		// join [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.join")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon
			plugin.getMessenger().sendMessage(player, "You must include a dungeon.");
			return;
		}
		String dungeon = command.args[1]; // Get the dungeon from the command
		if(!plugin.getDungeonManager().dungeonExists(dungeon)){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + dungeon);
			return;
		}
		
		plugin.getCommandManager().joinDungeon(player, plugin.getDungeonManager().getDungeon(dungeon));
	}
	
	public void parseLeave(Cmd command){
		// leave
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		Session session = plugin.getSessionManager().getSession(player); // Get the session of the player
		if(session == null){ // Check if the player is not in a session
			plugin.getMessenger().sendMessage(player, "You are not currently in a dungeon.");
			return;
		}
		
		plugin.getCommandManager().leaveDungeon(player, session);
	}
	
	@SuppressWarnings("deprecation")
	public void parseForceJoin(Cmd command){
		// forcejoin [player] [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.forcejoin")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a player name
			plugin.getMessenger().sendMessage(player, "You must include a player.");
			return;
		}
		Player target = plugin.getServer().getPlayer(command.args[1]); // Get the player from the server
		if(target == null){ // Check if the player exists
			plugin.getMessenger().sendMessage(player, "Player not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		if(plugin.getSessionManager().getSession(target) != null){ // Check if the player already has an active session
			plugin.getMessenger().sendMessage(player, "This player is already in a dungeon.");
			return;
		}
		if(command.args.length < 3){ // Check if the user included a dungeon
			plugin.getMessenger().sendMessage(player, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[2])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[2]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[2]); // Get the dungeon
		
		plugin.getCommandManager().joinDungeon(target, dungeon);
	}
	
	@SuppressWarnings("deprecation")
	public void parseForceLeave(Cmd command){
		// forceleave [player]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.forceleave")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a player name
			plugin.getMessenger().sendMessage(player, "You must include a player.");
			return;
		}
		Player target = plugin.getServer().getPlayer(command.args[1]); // Get the player from the server
		if(target == null){ // Check if the player exists
			plugin.getMessenger().sendMessage(player, "Player not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		Session session = plugin.getSessionManager().getSession(target);
		if(session == null){ // Check if the player already has an active session
			plugin.getMessenger().sendMessage(player, "This player is not in a dungeon.");
			return;
		}
		
		plugin.getCommandManager().leaveDungeon(target, session);
	}
	
	@SuppressWarnings("deprecation")
	public void parseComplete(Cmd command){
		// complete [player]
		if(command.sender instanceof Player){ // Make sure the user is NOT a player
			Player player = (Player) command.sender;
			plugin.getMessenger().sendMessage(player, "This feature of the Dungeons plugin is not accessible to players.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a player name
			plugin.getMessenger().sendMessage(command.sender, "You must include a player.");
			return;
		}
		Player target = plugin.getServer().getPlayer(command.args[1]);
		if(target == null){ // Check if the player exists
			plugin.getMessenger().sendMessage(command.sender, "Player not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		Session session = plugin.getSessionManager().getSession(target);
		if(session == null){ // Check if the player already has an active session
			plugin.getMessenger().sendMessage(command.sender, "This player is not in a dungeon.");
			return;
		}
		
		plugin.getCommandManager().completeDungeon(target, session);
	}
	
	public void parseTop(Cmd command){
		// top [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.top")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		
		plugin.getCommandManager().viewTop(player, plugin.getDungeonManager().getDungeon(command.args[1]));
	}
	
	@SuppressWarnings("deprecation")
	public void parseHistory(Cmd command){
		// history [player] [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.history")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a player name
			plugin.getMessenger().sendMessage(player, "You must include a player.");
			return;
		}
		Player target = plugin.getServer().getPlayer(command.args[1]); // Get the player from the server
		if(target == null){ // Check if the player exists
			plugin.getMessenger().sendMessage(player, "Player not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		if(command.args.length < 3){ // Check if the user included a dungeon
			plugin.getMessenger().sendMessage(player, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[2])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[2]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[2]); // Get the dungeon
		if(plugin.getRecordManager().getRecords(dungeon, target.getUniqueId()).isEmpty()){ // If the player hasn't finished the dungeon
			plugin.getMessenger().sendMessage(player, ChatColor.YELLOW + target.getName() + ChatColor.RESET + " has not completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " yet.");
			return;
		}
		
		plugin.getCommandManager().viewHistory(player, target, dungeon);
	}
	
	public void parseDungeonHistory(Cmd command){
		// dhistory/dungeonhistory [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.history")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[1]);
		if(plugin.getRecordManager().getRecords(dungeon).isEmpty()){ // Check if anyone has completed the dungeon yet
			plugin.getMessenger().sendMessage(player, "Nobody has completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " yet.");
			return;
		}
		
		plugin.getCommandManager().viewDungeonHistory(player, dungeon);
	}
	
	@SuppressWarnings("deprecation")
	public void parsePlayerHistory(Cmd command){
		// phistory/playerhistory [player]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.history")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a player name
			plugin.getMessenger().sendMessage(player, "You must include a player.");
			return;
		}
		Player target = plugin.getServer().getPlayer(command.args[1]); // Get the player from the server
		if(target == null){ // Check if the player exists
			plugin.getMessenger().sendMessage(player, "Player not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		if(plugin.getRecordManager().getRecords(target.getUniqueId()).isEmpty()){ // Check if the player has completed any dungeons
			plugin.getMessenger().sendMessage(player, ChatColor.YELLOW + target.getName() + ChatColor.RESET + " has not completed any dungeons yet.");
			return;
		}
		
		plugin.getCommandManager().viewPlayerHistory(player, target);
	}
		
	public void parseRecord(Cmd command){
		// record [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.record")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[1]); // Get the dungeon
		if(plugin.getRecordManager().getRecords(dungeon).isEmpty()){ // Check if anyone has completed the dungeon yet
			plugin.getMessenger().sendMessage(player, "Nobody has completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " yet.");
			return;
		}
		
		plugin.getCommandManager().viewRecord(player, dungeon);
	}
	
	@SuppressWarnings("deprecation")
	public void parsePlayerRecord(Cmd command){
		// precord/playerrecord [player] [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.record")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a player name
			plugin.getMessenger().sendMessage(player, "You must include a player.");
			return;
		}
		Player target = plugin.getServer().getPlayer(command.args[1]); // Get the player from the server
		if(target == null){ // Check if the player exists
			plugin.getMessenger().sendMessage(player, "Player not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		if(command.args.length < 3){ // Check if the user included a dungeon
			plugin.getMessenger().sendMessage(player, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[2])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[2]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[2]); // Get the dungeon
		List<Record> records = plugin.getRecordManager().getRecords(dungeon, target.getUniqueId()); // Get the records
		if(records.isEmpty()){ // Check that the player has completed the dungeon
			plugin.getMessenger().sendMessage(player, ChatColor.YELLOW + target.getName() + ChatColor.RESET + " has not completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " yet.");
			return;
		}
		
		plugin.getCommandManager().viewPlayerRecord(player, target, dungeon);
	}
	
	public void parseRank(Cmd command){
		// rank [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.rank")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[1]); // Get the dungeon
		if(plugin.getRecordManager().getRecords(dungeon, player.getUniqueId()).isEmpty()){
			plugin.getMessenger().sendMessage(player, "You have not completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " yet.");
			return;
		}
		
		plugin.getCommandManager().viewRank(player, dungeon);
	}
	
	public void parseCreate(Cmd command){
		// create [dungeon] [difficulty] [creator]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.create")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon name.");
			return;
		}
		if(plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "That dungeon already exists.");
			return;
		}
		String dungeon = command.args[1];
		if(command.args.length < 3){ // Check if the user included a difficulty
			plugin.getMessenger().sendMessage(command.sender, "You must include a difficulty.");
			return;
		}
		if(!isParsableDifficulty(command.args[2])){ // Check if the the difficulty is parsable
			plugin.getMessenger().sendMessage(player, "Difficulty not recognized: " + ChatColor.YELLOW + command.args[2]);
			return;
		}
		Difficulty difficulty = Difficulty.parse(command.args[2]);
		if(command.args.length < 4){ // Check if the user included a creator
			plugin.getMessenger().sendMessage(command.sender, "You must include a creator.");
			return;
		}
		String creator = command.args[3];
		for(int i = 4; i < command.args.length; i++)
			creator = creator + " " + command.args[i];
		
		plugin.getCommandManager().createDungeon(player, dungeon, difficulty, creator);
	}
	
	public void parseDelete(Cmd command){
		// delete [dungeon]
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.create")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		
		plugin.getCommandManager().deleteDungeon(player, plugin.getDungeonManager().getDungeon(command.args[1]));
	}
	
	public void parseCommand(Cmd command){
		// cmd/command [dungeon] [command] (add/remove)
		if(!(command.sender instanceof Player)){ // Make sure the user is a player 
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.command")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		Dungeon dungeon = plugin.getDungeonManager().getDungeon(command.args[1]);
		if(command.args.length < 3){ // Check if the user included a command name
			plugin.getMessenger().sendMessage(command.sender, "You must include a command.");
			return;
		}
		if(!isParsableCommand(command.args[2])){ // Check if the boolean value entered is parsable
			plugin.getMessenger().sendMessage(player, "Value must be either " + ChatColor.YELLOW + "add" + ChatColor.RESET + "/" + ChatColor.YELLOW + "remove" + ChatColor.RESET + "/" + ChatColor.YELLOW + "view" + ChatColor.RESET + ".");
			return;
		}
		String action = command.args[2];
		if(command.args.length < 4){ // Check if the user didn't include a command
			plugin.getCommandManager().viewCommand(player, dungeon, command.args[2]);
			return;
		}
		String commandString = command.args[3];
		for(int i = 4; i < command.args.length; i++)
			commandString = commandString + " " + command.args[i];
		
		
		if(action.equalsIgnoreCase("remove")){
			if(!dungeon.getCommandsAllowed().contains(commandString))
				plugin.getMessenger().sendMessage(player, "This command is already not allowed.");
			else
				plugin.getCommandManager().removeCommand(player, dungeon, commandString);
		}
		else if(action.equalsIgnoreCase("add")){
			if(dungeon.getCommandsAllowed().contains(commandString))
				plugin.getMessenger().sendMessage(player, "This command is already allowed.");
			else
				plugin.getCommandManager().addCommand(player, dungeon, commandString);
		}
		else{
			plugin.getCommandManager().viewCommand(player, dungeon, commandString);
		}
	}
	
	public void parseViewDungeon(Cmd command){
		// view [dungeon]
		
		if(!(command.sender instanceof Player)){ // Make sure the user is a player
			plugin.getMessenger().sendMessage(command.sender, "This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender; // Create a player by casting the sender
		if(!player.hasPermission("dungeon.info")){ // Check if the user has permission
			plugin.getMessenger().sendMessage(player, "You don't have permission to do this.");
			return;
		}
		if(command.args.length < 2){ // Check if the user included a dungeon name
			plugin.getMessenger().sendMessage(command.sender, "You must include a dungeon.");
			return;
		}
		if(!plugin.getDungeonManager().dungeonExists(command.args[1])){ // Check if the dungeon exists
			plugin.getMessenger().sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + command.args[1]);
			return;
		}
		
		plugin.getCommandManager().viewDungeon(player, plugin.getDungeonManager().getDungeon(command.args[1]));
	}
	
	public void parseCommandNotFound(Cmd command){
		String subcmd = command.args[0];
		if(command.sender instanceof Player){ // If the user is a player, send a colored message
			Player player = (Player) command.sender; // Create a player by casting the sender
			plugin.getMessenger().sendMessage(player, "The following command was not recognized: " + ChatColor.YELLOW + subcmd);
		}
		else // If the user is not a player, send an uncolored message
			plugin.getMessenger().sendMessage(command.sender, "The following command was not recognized: " + subcmd);
	}
	
	// Utility methods
	
	public boolean isParsableInt(String input){
	    boolean parsable = true;
	    try{
	        Integer.parseInt(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}
	
	public boolean isParsableBool(String input){
		if(input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false"))
			return true;
		return false;
	}
	
	public boolean parseBool(String input){
	    if(input.equalsIgnoreCase("true"))
	    	return true;
	    return false;
	}
	
	public boolean isParsableCommand(String input){
		if(input.equalsIgnoreCase("add") || input.equalsIgnoreCase("remove") || input.equalsIgnoreCase("view"))
			return true;
		return false;
	}
	
	public boolean isParsableDifficulty(String input){
		if(input.equalsIgnoreCase("easy") || input.equalsIgnoreCase("medium") ||
				input.equalsIgnoreCase("hard") || input.equalsIgnoreCase("insane"))
			return true;
		return false;
	}
}
