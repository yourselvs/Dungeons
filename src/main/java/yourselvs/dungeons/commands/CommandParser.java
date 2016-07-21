package yourselvs.dungeons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.utils.Messenger;

public class CommandParser implements CommandExecutor{
	private Dungeons plugin;
	private Messenger msg; // TODO rename to messenger after everything is finished
	
	public CommandParser(Dungeons instance){
		this.plugin = instance;
		this.msg = instance.getMessenger();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Cmd cmd = new Cmd(sender, command, label, args);
		
		if(!(command.getName().equalsIgnoreCase("dungeon") || command.getName().equalsIgnoreCase("dgn") ||
				command.getName().equalsIgnoreCase("dt") || command.getName().equalsIgnoreCase("dungeontracker")))
			return false;
		else if(cmd.args.length == 0)
			parseDungeon(cmd);
		else{
			String subcmd = args[0];

			if(subcmd.equalsIgnoreCase("join")){
				parseJoin(cmd);
			}
			else if(subcmd.equalsIgnoreCase("leave")){
				parseLeave(cmd);
			}
			else if(subcmd.equalsIgnoreCase("forcejoin")){
				parseForceJoin(cmd);
			}
			else if(subcmd.equalsIgnoreCase("forceleave")){
				parseForceLeave(cmd);
			}
			else if(subcmd.equalsIgnoreCase("complete")){
				parseComplete(cmd);
			}
			else if(subcmd.equalsIgnoreCase("top")){
				parseTop(cmd);
			}
			else if(subcmd.equalsIgnoreCase("history")){
				parseHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("dhistory") || subcmd.equalsIgnoreCase("dungeonhistory")){
				parseDungeonHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("phistory") || subcmd.equalsIgnoreCase("playerhistory")){
				parsePlayerHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("record")){
				parseRecord(cmd);
			}
			else if(subcmd.equalsIgnoreCase("precord") || subcmd.equalsIgnoreCase("playerrecord")){
				parsePlayerRecord(cmd);
			}
			else if(subcmd.equalsIgnoreCase("rank")){
				parseRank(cmd);
			}
			else if(subcmd.equalsIgnoreCase("create")){
				parseCreate(cmd);
			}
			else if(subcmd.equalsIgnoreCase("delete")){
				parseDelete(cmd);
			}
			else if(subcmd.equalsIgnoreCase("param") || subcmd.equalsIgnoreCase("parameter")){
				parseParameter(cmd);
			}
			else if(subcmd.equalsIgnoreCase("cmd") || subcmd.equalsIgnoreCase("command")){
				parseCommand(cmd);
			}
			else if(plugin.getDungeonManager().dungeonExists(subcmd)){
				parseViewDungeon(cmd);
			}
			else
				parseCommandNotFound(cmd);
		}
		return true;
	}
	
	private void parseJoin(Cmd command){
		// join [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseLeave(Cmd command){
		// leave
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseForceJoin(Cmd command){
		// forcejoin [player] [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseForceLeave(Cmd command){
		// forceleave [player]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseComplete(Cmd command){
		// complete [player]
		if(command.sender instanceof Player){
			Player player = (Player) command.sender;
			return;
		}
	}
	
	private void parseTop(Cmd command){
		// top [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseHistory(Cmd command){
		// history [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseDungeonHistory(Cmd command){
		// dhistory/dungeonhistory [player] [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parsePlayerHistory(Cmd command){
		// phistory/playerhistory [player]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
		
	private void parseRecord(Cmd command){
		// record [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parsePlayerRecord(Cmd command){
		// precord/playerrecord [player] [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseRank(Cmd command){
		// rank [dungeon]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseCreate(Cmd command){
		// create [dungeon] [difficulty] [creator]
		if(!(command.sender instanceof Player)){
			command.sender.sendMessage("This feature of the Dungeons plugin is only accessible if you are a player.");
			return;
		}
		Player player = (Player) command.sender;
	}
	
	private void parseDelete(Cmd command){
		// delete [dungeon]
	}
	
	private void parseParameter(Cmd command){
		// param/parameter [dungeon] [parameter] (true/false)
		
	}
	
	private void parseCommand(Cmd command){
		// cmd/command [dungeon] [command] (true/false)
	}
	
	private void parseDungeon(Cmd command){
		
	}
	
	private void parseViewDungeon(Cmd command){
		
	}
	
	private void parseCommandNotFound(Cmd command){
		String subcmd = command.args[0];
		if(command.sender instanceof Player){
			Player player = (Player) command.sender;
			msg.commandNotFound(player, subcmd);
		}
		else
			msg.commandNotFound(command.sender, subcmd);
			
		
	}
	
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
		if(input.equalsIgnoreCase("allow") || input.equalsIgnoreCase("block"))
			return true;
		return false;
	}
	
	public boolean parseCommand(String input){
	    if(input.equalsIgnoreCase("allow"))
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
