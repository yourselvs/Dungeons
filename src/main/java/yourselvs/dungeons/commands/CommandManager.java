package yourselvs.dungeons.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;
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

	public void viewLeaderboard(Player player, String dungeon, int page) {
		
	}
	
	public void getDungeonHistory(Player player, String dungeon){
		List<Record> records = plugin.getRecordManager().getRecords(dungeon);
	}
	
	public void getPlayerHistory(Player player, String dungeon, String playerName){
		
	}
	
	public void getRecord(Player player, String dungeon){
		
	}
	
	public void getRecord(CommandSender sender, String dungeon){
		
	}
	
	public void getPlayerRecord(Player player, String dungeon, String playerName){
		
	}
	
	public void getPlayerRecord(CommandSender sender, String dungeon, String playerName){
		
	}
	
	public void createDungeon(Player sender, String dungeon, String difficulty, String creator){
		
	}
	
	public void deleteDungeon(Player sender, String dungeon){
		
	}
	
	public void setParam(Player player, String dungeon, String param, boolean bool){
		
	}
	
	public void getParam(Player player, String dungeon, String param){
		
	}
	
	public void allowCommand(Player player, String dungeon, String command){
		
	}
	
	public void blockCommand(Player player, String dungeon, String command){
		
	}
	
	public void getCommand(Player player, String dungeon, String command){
		
	}
	
	public void viewDungeon(Player player, String dungeon){
		
	}
	
	public void viewInfo(Player player){
		
	}
	
	// TODO Build command manager
}
