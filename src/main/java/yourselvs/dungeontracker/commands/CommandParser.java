package yourselvs.dungeontracker.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import yourselvs.dungeontracker.Dungeons;

public class CommandParser implements CommandExecutor{
	private Dungeons plugin;
	
	public CommandParser(Dungeons instance){
		this.plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Cmd cmd = new Cmd(sender, command, label, args);
		return false;
	}
	
	// TODO Build command parser
}
