package yourselvs.dungeons.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.dungeons.Dungeon.Difficulty;
import yourselvs.dungeons.records.Record;

public class CommandManager {
	private Dungeons plugin;
	
	/**
	 * Creates a command manager that handles all command functions.
	 * @param instance
	 */
	public CommandManager(Dungeons instance){
		this.plugin = instance;
	}
	
	//public void joinDungeon(PlayerStartDungeonEvent event){
	
	//}
	
	public void leaveDungeon(Player player){
		plugin.getSessionManager().removeSession(player.getUniqueId());
	}
	
	//public void finishDungeon(PlayerFinishDungeonEvent event){
	
	//}

	public void viewLeaderboard(Player player, Dungeon dungeon, int page) {
		
	}
	
	public void getDungeonHistory(Player player, Dungeon dungeon){
		List<Record> records = plugin.getRecordManager().getRecords(dungeon);
	}
	
	public void getPlayerHistory(Player player, Dungeon dungeon, String playerName){
		
	}
	
	public void getRecord(Player player, Dungeon dungeon){
		
	}
	
	public void getRecord(CommandSender sender, Dungeon dungeon){
		
	}
	
	public void getPlayerRecord(Player player, Dungeon dungeon, String playerName){
		
	}
	
	public void getPlayerRecord(CommandSender sender, Dungeon dungeon, String playerName){
		
	}
	
	public void createDungeon(Player sender, Dungeon dungeon, Difficulty difficulty, String creator){
		
	}
	
	public void deleteDungeon(Player sender, Dungeon dungeon){
		
	}
	
	public void setParam(Player player, Dungeon dungeon, String param, boolean bool){
		
	}
	
	public void getParam(Player player, Dungeon dungeon, String param){
		
	}
	
	public void allowCommand(Player player, Dungeon dungeon, String command){
		
	}
	
	public void blockCommand(Player player, Dungeon dungeon, String command){
		
	}
	
	public void getCommand(Player player, Dungeon dungeon, String command){
		
	}
	
	public void viewDungeon(Player player, String dungeon){
		
	}
	
	public void viewInfo(Player player){
		
	}
	
	// TODO Build command manager
}
