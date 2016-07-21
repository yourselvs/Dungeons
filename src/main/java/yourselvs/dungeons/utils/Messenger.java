package yourselvs.dungeons.utils;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mythicacraft.voteroulette.utils.InteractiveMessageAPI.FormattedText;
import com.mythicacraft.voteroulette.utils.InteractiveMessageAPI.InteractiveMessage;
import com.mythicacraft.voteroulette.utils.InteractiveMessageAPI.InteractiveMessageElement;
import com.mythicacraft.voteroulette.utils.InteractiveMessageAPI.InteractiveMessageElement.ClickEvent;
import com.mythicacraft.voteroulette.utils.InteractiveMessageAPI.InteractiveMessageElement.HoverEvent;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

public class Messenger {
	private String prefix = "[" + ChatColor.RED + ChatColor.BOLD + "DGN" + ChatColor.RESET + "]";
	private String linkPrefix = ChatColor.AQUA + "[" + ChatColor.RED + ChatColor.BOLD + "DGN" + ChatColor.RESET + ChatColor.AQUA + "]" + ChatColor.RESET;
	private String unformattedPrefix = "[DGN]";
	
	private Dungeons plugin;
	
	public Messenger(Dungeons instance){
		this.plugin = instance;
	}
	
	public void featureNotAccessiblePlayers(Player player, String message){
		sendMessage(player, "This feature of the Dungeons plugin is not accessible to players.");
	}
	
	public void commandNotFound(Player player, String command){
		sendMessage(player, "The following command was not recognized: " + ChatColor.YELLOW + command);
	}
	
	public void commandNotFound(CommandSender sender, String command){
		sendMessage(sender, "The following command was not recognized: " + command);
	}
	
	public void startDungeon(Player player, Session session){
		sendMessage(player, "Starting dungeon " + ChatColor.YELLOW + session.getDungeon().getName() + ChatColor.RESET + ".");
	}
	
	public void quitDungeon(Player player, Session session){
		sendMessage(player, "Quitting dungeon " + ChatColor.YELLOW + session.getDungeon().getName() + ChatColor.RESET + ".");
	}
	
	public void beatDungeonWR(Record prevRecord, Record newRecord){
		ArrayList<String> messages = new ArrayList<String>();
		messages.add(ChatColor.DARK_PURPLE + "*** NEW WORLD RECORD ***");
		messages.add("" + plugin.getServer().getPlayer(newRecord.getPlayer()).getName() + " beat the world record in " + ChatColor.YELLOW + newRecord.getDungeon().getName() + ChatColor.RESET + " with a time of " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(newRecord.getTime()) + ChatColor.RESET + ", beating the old time by " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(plugin.getFormatter().subtractTime(prevRecord.getTime(), newRecord.getTime())) + ChatColor.RESET + ".");
		sendServerMessage(messages);
	}
	
	public void beatDungeonPR(Record prevRecord, Record newRecord){
		sendMessage(plugin.getServer().getPlayer(newRecord.getPlayer()), "You beat your personal record in " + ChatColor.YELLOW + newRecord.getDungeon().getName() + ChatColor.RESET + " by " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(plugin.getFormatter().subtractTime(prevRecord.getTime(), newRecord.getTime())) + ChatColor.RESET + ".");
	}
	
	// Past this point are utility methods for sending formatted messsages to players
	
	public void sendPlayerLog(Player player, String message){
		plugin.getLogger().info("Player \"" + player.getName() + "\" " + message);
	}
	
	public void sendMessage(Player player, String message){
		player.sendMessage(prefix + " " + ChatColor.RESET + message + ChatColor.RESET);
	}
	
	public void sendMessages(Player player, ArrayList<String> messages){
		ArrayList<String> updateMessages = new ArrayList<String>();
		updateMessages.add("- - - - - - - - - - - - - - ");
		for(String message : messages)
			updateMessages.add(message + ChatColor.RESET);
		player.sendMessage(messages.toArray(new String[messages.size()]));
	}
	
	public void sendMessage(CommandSender player, String message){
		player.sendMessage(unformattedPrefix + " " + message);
	}
	
	public void sendServerMessage(String message){
		for(Player player : plugin.getServer().getOnlinePlayers())
			sendMessage(player, message);
	}	
	
	public void sendServerMessage(ArrayList<String> messages){
		for(Player player : plugin.getServer().getOnlinePlayers())
			sendMessages(player, messages);
	}
	
	public void sendClickMessage(Player player, String line, String hoverMessage, String command){
		InteractiveMessage message = new InteractiveMessage(new InteractiveMessageElement(new FormattedText(linkPrefix + " " + line), HoverEvent.SHOW_TEXT, new FormattedText(hoverMessage), ClickEvent.RUN_COMMAND, command));	
		message.sendTo(player);
	}
	
	public void sendClickMessage(Player player, String line, String command){
		InteractiveMessage message = new InteractiveMessage(new InteractiveMessageElement(new FormattedText(linkPrefix + " " + line), HoverEvent.NONE, new FormattedText(""), ClickEvent.RUN_COMMAND, command));	
		message.sendTo(player);
	}
	
	public void sendSuggestMessage(Player player, String line, String hoverMessage, String command){
		InteractiveMessage message = new InteractiveMessage(new InteractiveMessageElement(new FormattedText(linkPrefix + " " + line), HoverEvent.SHOW_TEXT, new FormattedText(hoverMessage), ClickEvent.SUGGEST_COMMAND, command));	
		message.sendTo(player);
	}

	public void sendSuggestMessage(Player player, String line, String command){
		InteractiveMessage message = new InteractiveMessage(new InteractiveMessageElement(new FormattedText(linkPrefix + " " + line), HoverEvent.NONE, new FormattedText(""), ClickEvent.SUGGEST_COMMAND, command));
		message.sendTo(player);
	}
}
