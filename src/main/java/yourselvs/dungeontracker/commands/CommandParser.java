package yourselvs.dungeontracker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yourselvs.commands.DungeonCommand;
import yourselvs.dungeontracker.Dungeons;

public class CommandParser implements CommandExecutor{
	private Dungeons plugin;
	
	public CommandParser(Dungeons instance){
		this.plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Cmd cmd = new Cmd(sender, command, label, args);
		
		if(!(command.getName().equalsIgnoreCase("dungeon") || command.getName().equalsIgnoreCase("dgn") ||
				command.getName().equalsIgnoreCase("dt") || command.getName().equalsIgnoreCase("dungeontracker")))
			return false;
		else if(cmd.args.length == 0)
			processDungeon(cmd);
		else{
			String subcmd = args[0];

			if(subcmd.equalsIgnoreCase("join")){
				processJoin(cmd);
			}
			else if(subcmd.equalsIgnoreCase("leave")){
				processLeave(cmd);
			}
			else if(subcmd.equalsIgnoreCase("forcejoin")){
				processForceJoin(cmd);
			}
			else if(subcmd.equalsIgnoreCase("forceleave")){
				processForceLeave(cmd);
			}
			else if(subcmd.equalsIgnoreCase("complete")){
				processComplete(cmd);
			}
			else if(subcmd.equalsIgnoreCase("leaders")){
				processLeaders(cmd);
			}
			else if(subcmd.equalsIgnoreCase("history")){
				processHistory(cmd);
			}
			else if(subcmd.equalsIgnoreCase("record")){
				parseRecord(cmd);
			}
			else if(subcmd.equalsIgnoreCase("create")){
				processCreate(cmd);
			}
			else if(subcmd.equalsIgnoreCase("delete")){
				processDelete(cmd);
			}
			else if(subcmd.equalsIgnoreCase("param")){
				processParam(cmd);
			}
			else if(subcmd.equalsIgnoreCase("cmd")){
				processCommand(cmd);
			}
			else if(plugin.getDungeonManager().dungeonExists(subcmd)){
				processViewDungeon(cmd);
			}
			else
				processCommandNotFound(cmd);
		}
		return true;
	}
	
	private void processJoin(Cmd command){
		
	}
	
	private void processLeave(Cmd command){
		
	}
	
	private void processForceJoin(Cmd command){
		
	}
	
	private void processForceLeave(Cmd command){
		
	}
	
	private void processComplete(Cmd command){
		
	}
	
	private void processLeaders(Cmd command){
		
	}
	
	private void processHistory(Cmd command){
		
	}
	
	private void parseRecord(Cmd command){
		
	}
	
	private void processCreate(Cmd command){
		
	}
	
	private void processDelete(Cmd command){
		
	}
	
	private void processParam(Cmd command){
		
	}
	
	private void processCommand(Cmd command){
		
	}
	
	private void processDungeon(Cmd command){
		
	}
	
	private void processViewDungeon(Cmd command){
		
	}
	
	private void processCommandNotFound(Cmd command){
		
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
