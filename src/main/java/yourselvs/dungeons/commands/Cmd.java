package yourselvs.dungeons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Cmd {
	public CommandSender sender;
	public Command cmd;
	public String label;
	public String[] args;
	
	public Cmd(CommandSender sender, Command cmd, String label, String[] args){
		this.sender = sender;
		this.cmd = cmd;
		this.label = label;
		this.args = args;
	}
}
