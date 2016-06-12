package yourselvs.dungeontracker.commands;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yourselvs.dungeontracker.Dungeons;
import yourselvs.dungeontracker.records.Record;
import yourselvs.dungeontracker.sessions.Session;

public class Messenger {
	private String prefix = "[" + ChatColor.RED + ChatColor.BOLD + "DGN" + ChatColor.RESET + "]";
	private String unformattedPrefix = "[DGN]";
	private Dungeons plugin;
	
	public Messenger(Dungeons instance){
		this.plugin = instance;
	}
	
	public void commandNotAllowed(String dungeon, String command, Player player){
		sendMessage(player, "You are not allowed to use the command \"" + ChatColor.YELLOW + command + ChatColor.RESET + "\" because you are in dungeon \"" + ChatColor.YELLOW + dungeon + ChatColor.RESET + "\"." );
		sendPlayerLog(player, "tried to use command \"" + command + "\" while in dungeon \"" + dungeon + "\" and was stopped.");
	}
	
	public void commandNotAllowed(Player player){
		sendMessage(player, "You are not allowed to use that commmand." );
	}
	
	public void commandNotAllowed(CommandSender player){
		sendMessage(player, "You are not allowed to use that commmand." );
	}
	
	public void resumeDungeon(Player player, Session record){
		Date time = plugin.getFormatter().subtractTime(record.getStart(), new Date());
		sendMessage(player,  "Resuming dungeon \"" + ChatColor.YELLOW + record.getDungeon().getName() + ChatColor.RESET + "\" with a time of " + ChatColor.YELLOW + time + ChatColor.RESET + "." );
		sendPlayerLog(player, "rejoined and resumed dungeon \"" + record.getDungeon().getName() + "\" with a time of " + time + ".");
	}
	
	public void quitDungeon(Player player, Session record){
		Date time = plugin.getFormatter().subtractTime(record.getStart(), new Date());
		sendPlayerLog(player, "quit the server and left dungeon \"" + record.getDungeon().getName() + "\" with a time of " + plugin.getFormatter().getShortFormatter().format(time) + ".");
	}
	
	public void startDungeon(Player player, Session record){
		sendMessage(player, "Starting dungeon \"" + ChatColor.YELLOW + record.getDungeon().getName() + ChatColor.RESET + "\" at time " + ChatColor.YELLOW + plugin.getFormatter().getLongFormatter().format(record.getStart()) + ChatColor.RESET + ".");
		sendPlayerLog(player, "started the dungeon \"" + record.getDungeon().getName() + "\" at the time " + plugin.getFormatter().getLongFormatter().format(record.getStart()) + ".");
	}
	
	public void finishDungeon(Player player, Record record){
		sendMessage(player, "Dungeon \"" + ChatColor.YELLOW + record.getDungeon().getName() + ChatColor.RESET + "\" finished with a time of " + ChatColor.YELLOW + plugin.getFormatter().getLongFormatter().format(record.getTime()) + ChatColor.RESET + ".");
		sendPlayerLog(player, "finished the dungeon \"" + record.getDungeon().getName() + "\" with a time of " + plugin.getFormatter().getShortFormatter().format(record.getTime()) + ".");
	}
	
	public void beatDungeonPR(Player player, Record oldPR, Record newPR){
		sendMessage(player, "You beat your old time by " + ChatColor.YELLOW + plugin.getFormatter().subtractTime(oldPR.getTime(), newPR.getTime()) + ChatColor.RESET + ".");
	}
	
	public void beatDungeonWR(Player player, Record oldWR, Record newWR){		
		sendServerMessage(ChatColor.DARK_PURPLE + "NEW WORLD RECORD");
		sendServerMessage(ChatColor.YELLOW + player.getName() + " beat the world record time in \"" + ChatColor.YELLOW + newWR.getDungeon().getName() + ChatColor.RESET + " with a time of " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(newWR.getTime()) + ChatColor.RESET + ", beating the world record by " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(plugin.getFormatter().subtractTime(oldWR.getTime(), newWR.getTime())) + ChatColor.RESET + ".");
	}
	
	public void commandNotFound(Player player, String command){
		sendMessage(player, "Command not recognized: " + ChatColor.YELLOW + command);
	}
	
	public void commandNotFound(CommandSender player, String command){
		sendMessage(player, "Command not recognized: " + command);
	}
	
	public void dungeonNotFound(Player player, String dungeon){
		sendMessage(player, "Dungeon not found: " + ChatColor.YELLOW + dungeon);
	}
	
	public void dungeonNotFound(CommandSender player, String dungeon){
		sendMessage(player, "Dungeon not found: " + dungeon);
	}
	
	public void playerNotFound(Player sender, String player) {
		sendMessage(sender, "Player not found: " + ChatColor.YELLOW + player);
	}
	
	public void playerNotFound(CommandSender sender, String player) {
		sendMessage(sender, "Player not found: " + player);
	}
	
	public void mustBePlayer(CommandSender player){
		sendMessage(player, "You must be a player to do this.");
	}
	
	public void mustIncludeDungeon(Player player){
		sendMessage(player, "You must include a dungeon.");
	}
	
	public void mustIncludeDungeon(CommandSender player){
		sendMessage(player, "You must include a dungeon.");
	}
	
	public void mustIncludePlayer(Player player) {
		sendMessage(player, "You must include a player.");
	}
	
	public void mustIncludePlayer(CommandSender player) {
		sendMessage(player, "You must include a player.");
	}	
	
	public void mustBeInDungeon(Player player){
		sendMessage(player, "You must be in a dungeon to do this.");
	}
	
	public void mustBeInDungeon(CommandSender player){
		sendMessage(player, "The player must be in a dungeon to do this.");
	}
	
	public void invalidPageNum(Player player) {
		sendMessage(player, "That is an invalid page.");
	}
	
	public void mustIncludeCreator(Player player){
		sendMessage(player, "You must include a creator.");
	}
	
	public void mustIncludeParam(Player player){
		sendMessage(player, "You must include a parameter.");
	}
	
	public void mustIncludeCommand(Player player){
		sendMessage(player, "You must include a command.");
	}
	
	public void mustIncludeDifficulty(Player player){
		sendMessage(player, "You must include a difficulty.");
	}
	
	public void argumentNotFound(Player player, String arg){
		sendMessage(player, "Argument not found: " + ChatColor.YELLOW + arg);
	}
	
	public void paramNotFound(Player player, String string) {
		sendMessage(player, "Parameter not found: " + ChatColor.YELLOW + string);
	}
	
	public void difficultyNotFound(Player player, String string) {
		sendMessage(player, "Difficulty not found: " + ChatColor.YELLOW + string);
	}
	
	private void sendPlayerLog(Player player, String message){
		plugin.getLogger().info("Player \"" + player.getName() + "\" " + message);
	}
	
	private void sendMessage(Player player, String message){
		player.sendMessage(prefix + " " + ChatColor.RESET + message + ChatColor.RESET);
	}
	
	private void sendMessage(CommandSender player, String message){
		player.sendMessage(unformattedPrefix + " " + message);
	}
	
	private void sendServerMessage(String message){
		for(Player player : plugin.getServer().getOnlinePlayers())
			sendMessage(player, message);
	}	
}
