package yourselvs.dungeons.commands;

import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.dungeons.Dungeon;
import yourselvs.dungeons.dungeons.Dungeon.Difficulty;
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
	
	public void joinDungeon(Player player, Dungeon dungeon){
		
		player.teleport(dungeon.getStart());
	}
	
	public void leaveDungeon(Player player){
		
	}
	
	public void completeDungeon(Session session){
		
	}
	
	public void viewTop(Player player, Dungeon dungeon){
		
	}
	
	public void viewHistory(Player player, Player target, Dungeon dungeon){
		
	}
	
	public void viewDungeonHistory(Player player, Dungeon dungeon){
		
	}
	
	public void viewPlayerHistory(Player player, Player target){
		
	}
	
	public void viewRecord(Player player, Dungeon dungeon){
		
	}
	
	public void viewPlayerRecord(Player player, Player target, Dungeon dungeon){
		
	}
	
	public void viewRank(Player player, Dungeon dungeon){
		
	}
	
	public void createDungeon(Player player, String dungeon, Difficulty difficulty, String creator){
		
	}
	
	public void deleteDungeon(Player player, Dungeon dungeon){
		
	}
	
	public void viewCommand(Dungeon dungeon, String command){
		
	}
	
	public void setCommand(Dungeon dungeon, String command, boolean value){
		
	}
	
	public void viewDungeon(Player player, Dungeon dungeon){
		
	}
	
	public void viewInfo(Player player){
		
	}
	
	// TODO Build command manager
}
