package yourselvs.dungeons.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.dungeons.Dungeon.Difficulty;
import yourselvs.dungeons.events.PlayerFinishDungeonEvent;
import yourselvs.dungeons.events.PlayerLeaveDungeonEvent;
import yourselvs.dungeons.events.PlayerStartDungeonEvent;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.records.RecordManager;
import yourselvs.dungeons.sessions.Session;

public class CommandManager {
	private Dungeons plugin;
	
	/**
	 * Creates a command manager that handles all command functions.
	 * @param instance
	 */
	public CommandManager(Dungeons instance){
		this.plugin = instance;
	}
	
	public void viewHelp(Player player) {
		List<String> msgs = new ArrayList<String>();
		
		msgs.add("Available commands:");
		if(player.hasPermission("dungeon.info")){
			msgs.add(ChatColor.YELLOW + "/dungeon" + ChatColor.RESET + " : View information about the plugin");
		}
		if(player.hasPermission("dungeon.join")){
			msgs.add(ChatColor.YELLOW + "/dungeon list" + ChatColor.RESET + " : List the dungeons you are able to join");
			msgs.add(ChatColor.YELLOW + "/dungeon view [dungeon]" + ChatColor.RESET + " : View information about a dungeon");
			msgs.add(ChatColor.YELLOW + "/dungeon join [dungeon]" + ChatColor.RESET + " : Joins a dungeon");
			msgs.add(ChatColor.YELLOW + "/dungeon leave" + ChatColor.RESET + " : Leaves the dungeon you are in");
		}
		if(player.hasPermission("dungeon.record")){
			msgs.add(ChatColor.YELLOW + "/dungeon record [dungeon]" + ChatColor.RESET + " : Views the fastest time of a dungeon");
			msgs.add(ChatColor.YELLOW + "/dungeon precord [player] [dungeon]" + ChatColor.RESET + " : Views a player's fastest time in a dungeon");
		}
		if(player.hasPermission("dungeon.top"))
			msgs.add(ChatColor.YELLOW + "/dungeon top [dungeon]" + ChatColor.RESET + " : Views the top records in a dungeon");
		if(player.hasPermission("dungeon.forcejoin"))
			msgs.add(ChatColor.YELLOW + "/dungeon forcejoin [player] [dungeon]" + ChatColor.RESET + " : Forces a player to join a dungeon");
		if(player.hasPermission("dungeon.forceleave"))
			msgs.add(ChatColor.YELLOW + "/dungeon forceleave [player]" + ChatColor.RESET + " : Forces a player to leave a dungeon");
		if(player.hasPermission("dungeon.create")){
			msgs.add(ChatColor.YELLOW + "/dungeon create [dungeon] [difficulty] [creator]" + ChatColor.RESET + " : Creates a dungeon with a spawn at the point you are standing");
			msgs.add(ChatColor.YELLOW + "/dungeon delete [dungeon]" + ChatColor.RESET + " : Deletes a dungeon");
		}
		if(player.hasPermission("dungeon.command"))
			msgs.add(ChatColor.YELLOW + "/dungeon command [dungeon] [add/remove/view] [command]");
		
		plugin.getMessenger().sendMessages(player, msgs);
	}
	
	public void listDungeons(Player player) {
		List<String> msgs = new ArrayList<String>();
		
		msgs.add("Dungeons available:");
		for(Dungeon dungeon : plugin.getDungeonManager().getDungeons()){
			Difficulty difficulty = dungeon.getDifficulty();
			
			String color = "";
			if(difficulty == Difficulty.EASY)
				color = "" + ChatColor.GREEN;
			else if(difficulty == Difficulty.MEDIUM)
				color = "" + ChatColor.GOLD;
			else if(difficulty == Difficulty.HARD)
				color = "" + ChatColor.RED;
			else if(difficulty == Difficulty.INSANE)
				color = "" + ChatColor.DARK_RED;
				
			msgs.add(ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " : " + color + dungeon.getDifficulty().toString() + ChatColor.RESET + " : Created by " + ChatColor.YELLOW + dungeon.getCreator());
		}
		
		msgs.add("" + ChatColor.RED + ChatColor.BOLD + "WARNING: " + ChatColor.RED + "Your inventory, armor, and potion effects are cleared upon joining a dungeon.");
		
		plugin.getMessenger().sendMessages(player, msgs);
	}
	
	public void joinDungeon(Player player, Dungeon dungeon){
		PlayerStartDungeonEvent event = new PlayerStartDungeonEvent(player, new Date(), dungeon);
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled())
			player.teleport(dungeon.getStart());
		player.setFlying(false);
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		plugin.getMessenger().sendMessage(player, "Joining dungeon: " + ChatColor.YELLOW + dungeon.getName());
	}
	
	public void leaveDungeon(Player player, Session session){
		PlayerLeaveDungeonEvent event = new PlayerLeaveDungeonEvent(player, session.getDungeon());
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled())
			player.teleport(session.getLocation());
		player.getInventory().clear();
		plugin.getMessenger().sendMessage(player, "Leaving dungeon: " + ChatColor.YELLOW + session.getDungeon().getName());
	}
	
	public void completeDungeon(Player player, Session session){
		PlayerFinishDungeonEvent event = new PlayerFinishDungeonEvent(session, new Date());
		Bukkit.getServer().getPluginManager().callEvent(event);
		if(!event.isCancelled())
			player.teleport(session.getLocation());
	}
	
	public void viewTop(Player player, Dungeon dungeon){
		List<Record> records = plugin.getRecordManager().getRecords(dungeon);
		Collections.sort(records);
		records = RecordManager.removeDuplicatePlayers(records);

		if(records.size() == 0){
			player.sendMessage("Nobody has completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " yet.");
			return;
		}
		
		int maxRecord = 10;
		
		if(records.size() < maxRecord)
			maxRecord = records.size();
		
		List<String> messages = new ArrayList<String>();
		messages.add("Top " + ChatColor.YELLOW + maxRecord + ChatColor.RESET + " records for " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + ":");
		
		for(int i = 0; i < maxRecord; i++){
			
			
			String time = plugin.getFormatter().getShortFormatter().format(records.get(i).getTime());
			String targetName = "";
			
			Player target = Bukkit.getPlayer(records.get(i).getPlayer());
			if(target == null)
				targetName = Bukkit.getOfflinePlayer(records.get(i).getPlayer()).getName();
			else
				targetName = target.getName();
			
			messages.add((i + 1) + ") " + ChatColor.YELLOW + targetName + ChatColor.RESET + " : " + time);
		}
		
		plugin.getMessenger().sendMessages(player, messages);
	}
	
	public void viewHistory(Player player, Player target, Dungeon dungeon){
		// TODO Implement feature
		plugin.getMessenger().sendMessage(player, "This feature is not available yet.");
	}
	
	public void viewDungeonHistory(Player player, Dungeon dungeon){
		// TODO Implement feature
		plugin.getMessenger().sendMessage(player, "This feature is not available yet.");
	}
	
	public void viewPlayerHistory(Player player, Player target){
		// TODO Implement feature
		plugin.getMessenger().sendMessage(player, "This feature is not available yet.");
	}
	
	public void viewRecord(Player player, Dungeon dungeon){
		Record record = plugin.getRecordManager().getFastestRecord(dungeon);
		
		if(record == null)
			plugin.getMessenger().sendMessage(player, ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + " hasn't been completed yet.");
		else{
			String name;
			Player target = plugin.getServer().getPlayer(record.getPlayer());
			
			if(target == null)
				name = plugin.getServer().getOfflinePlayer(record.getPlayer()).getName();
			else
				name = target.getName();
			
			plugin.getMessenger().sendMessage(player, "The world record in " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + 
					" is " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(record.getTime()) + ChatColor.RESET + 
					", held by " + ChatColor.YELLOW + name);
		}
	}
	
	public void viewPlayerRecord(Player player, Player target, Dungeon dungeon){
		Record record = plugin.getRecordManager().getFastestRecord(dungeon, target.getUniqueId());
		
		if(record == null)
			plugin.getMessenger().sendMessage(player, "This player hasn't completed " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + "yet.");
		else						
			plugin.getMessenger().sendMessage(player, "" +  ChatColor.YELLOW + Bukkit.getPlayer(record.getPlayer()).getName() + ChatColor.RESET + " has a record of " + 
					ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(record.getTime()) + ChatColor.RESET + 
					" in " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET);
	}
	
	public void viewRank(Player player, Dungeon dungeon){
		// TODO Implement feature
		plugin.getMessenger().sendMessage(player, "This feature is not available yet.");
	}
	
	public void createDungeon(Player player, String name, Difficulty difficulty, String creator){
		Dungeon dungeon = new Dungeon(name, player.getLocation(), creator, difficulty);
		dungeon.addCommandAllowed("dungeon leave");
		plugin.getDungeonManager().addDungeon(dungeon);
		plugin.getMessenger().sendMessage(player, "Dungeon created: " + ChatColor.YELLOW + name);
	}
	
	public void deleteDungeon(Player player, Dungeon dungeon){
		plugin.getDungeonManager().removeDungeon(dungeon.getName());
		plugin.getSessionManager().removeSession(dungeon.getName());
		plugin.getRecordManager().removeRecord(dungeon.getName());
	}
	
	public void viewCommand(Player player, Dungeon dungeon, String command){
		if(dungeon.getCommandsAllowed().contains(command))
			plugin.getMessenger().sendMessage(player, "The command " + ChatColor.GREEN + command + ChatColor.RESET + " is allowed in " + ChatColor.YELLOW + dungeon.getName());
		else
			plugin.getMessenger().sendMessage(player, "The command " + ChatColor.RED + command + ChatColor.RESET + " is not allowed in " + ChatColor.YELLOW + dungeon.getName());
	}
	
	public void addCommand(Player player, Dungeon dungeon, String command){
		plugin.getDungeonManager().addCommandAllowed(dungeon.getName(), command);
		plugin.getMessenger().sendMessage(player, "Command now allowed in " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + ": " + ChatColor.GREEN + command);
	}
	
	public void removeCommand(Player player, Dungeon dungeon, String command){
		plugin.getDungeonManager().removeCommandAllowed(dungeon.getName(), command);
		plugin.getMessenger().sendMessage(player, "Command no longer allowed in " + ChatColor.YELLOW + dungeon.getName() + ChatColor.RESET + ": " + ChatColor.RED + command);
	}
	
	public void viewDungeon(Player player, Dungeon dungeon){
		List<String> messages = new ArrayList<String>();
		
		messages.add("Dungeon: " + ChatColor.YELLOW + dungeon.getName());
		
		String difficulty = dungeon.getDifficulty().toString();
		if(difficulty.equalsIgnoreCase("easy"))
			difficulty = ChatColor.GREEN + difficulty;
		if(difficulty.equalsIgnoreCase("medium"))
			difficulty = ChatColor.YELLOW + difficulty;
		if(difficulty.equalsIgnoreCase("hard"))
			difficulty = ChatColor.RED + difficulty;
		if(difficulty.equalsIgnoreCase("insne"))
			difficulty = ChatColor.DARK_RED + difficulty;
		messages.add("Difficulty: " + difficulty);
		
		messages.add("Creator: " + dungeon.getCreator());
		
		Record wr = plugin.getRecordManager().getFastestRecord(dungeon);
		String record;
		if(wr == null)
			record = "N/A";
		else
			record = plugin.getFormatter().getShortFormatter().format(wr.getTime());	
		messages.add("WR: " + ChatColor.YELLOW + record);
		
		plugin.getMessenger().sendMessages(player, messages);
	}
	
	public void viewInfo(Player player){
		plugin.getMessenger().sendMessage(player, "Dungeons plugin v" + ChatColor.YELLOW + plugin.version);
		plugin.getMessenger().sendMessage(player, "Created by " + ChatColor.YELLOW + plugin.creator);
		plugin.getMessenger().sendMessage(player, "Type " + ChatColor.YELLOW + "/dungeon help" + ChatColor.RESET + " to view commands.");
	}
}
