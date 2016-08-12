package yourselvs.dungeons.listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.events.DungeonPersonalRecordEvent;
import yourselvs.dungeons.events.DungeonWorldRecordEvent;
import yourselvs.dungeons.events.PlayerFinishDungeonEvent;
import yourselvs.dungeons.events.PlayerLeaveDungeonEvent;
import yourselvs.dungeons.events.PlayerStartDungeonEvent;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

public class DungeonListener implements Listener {
	private Dungeons plugin;
	
	public DungeonListener(Dungeons instance) {
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getMessenger().sendServerMessage("Dungeon listener enabled");
		
		
	}
	
	@EventHandler
	public void onWorldRecord(DungeonWorldRecordEvent event) {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add(ChatColor.DARK_PURPLE + "*** NEW WORLD RECORD ***");
		messages.add("" + plugin.getServer().getPlayer(event.getRecord().getPlayer()).getName() + " beat the world record in " + ChatColor.YELLOW + event.getRecord().getDungeon().getName() + ChatColor.RESET + " with a time of " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(event.getRecord().getTime()) + ChatColor.RESET + ", beating the old time by " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(plugin.getFormatter().subtractTime(event.getPreviousRecord().getTime(), event.getRecord().getTime())) + ChatColor.RESET + ".");
		plugin.getMessenger().sendServerMessage(messages);
	}
	
	@EventHandler
	public void onPersonalRecord(DungeonPersonalRecordEvent event) {
		plugin.getMessenger().sendMessage(plugin.getServer().getPlayer(event.getRecord().getPlayer()), "You beat your personal record in " + ChatColor.YELLOW + event.getRecord().getDungeon().getName() + ChatColor.RESET + " by " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(plugin.getFormatter().subtractTime(event.getPreviousRecord().getTime(), event.getRecord().getTime())) + ChatColor.RESET + ".");
	}
	
	@EventHandler
	public void onStartDungeon(PlayerStartDungeonEvent event) {
		Session session = new Session(event.getPlayer().getUniqueId(), event.getDungeon(), event.getTime(), event.getPlayer().getLocation());
		plugin.getSessionManager().addSession(session);
	}
	
	@EventHandler
	public void onFinishDungeon(PlayerFinishDungeonEvent event) {	
		Record record = new Record(event.getSession().getPlayer(), event.getSession().getDungeon(), event.getTime(), plugin.getFormatter().subtractTime(event.getTime(), event.getSession().getStart()));
		Record pr = plugin.getRecordManager().getFastestRecord(record.getDungeon(), record.getPlayer());
		Record wr = plugin.getRecordManager().getFastestRecord(record.getDungeon());
		
		plugin.getSessionManager().removeSession(record.getPlayer());
		plugin.getRecordManager().addRecord(record);
		Player player = plugin.getServer().getPlayer(record.getPlayer());
		plugin.getMessenger().sendMessage(player, "You finished " + ChatColor.YELLOW + record.getDungeon().getName() + ChatColor.RESET + " with a time of " + ChatColor.YELLOW + plugin.getFormatter().getShortFormatter().format(record.getTime()));
		
		String prTime = "N/A";
		String wrTime = "N/A";
		
		if(pr != null)
			prTime = plugin.getFormatter().getShortFormatter().format(pr.getTime());
		if(wr != null)
			wrTime = plugin.getFormatter().getShortFormatter().format(wr.getTime());
		
		plugin.getMessenger().sendMessage(player, "PR time: " + ChatColor.YELLOW + prTime);
		plugin.getMessenger().sendMessage(player, "WR time: " + ChatColor.YELLOW + wrTime);
		
		if(pr != null && (record.getTime().getTime() < pr.getTime().getTime())){
			DungeonPersonalRecordEvent prEvent = new DungeonPersonalRecordEvent(record, pr);
			plugin.getServer().getPluginManager().callEvent(prEvent);
		}
		if(wr != null && (record.getTime().getTime() < wr.getTime().getTime())){ // if the player beats the world record
			DungeonWorldRecordEvent wrEvent = new DungeonWorldRecordEvent(record, wr);
			plugin.getServer().getPluginManager().callEvent(wrEvent);
		}
	}
	
	@EventHandler
	public void onLeaveDungeon(PlayerLeaveDungeonEvent event) {
		plugin.getSessionManager().removeSession(event.getPlayer().getUniqueId());
	}
	
	/*
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Session session = plugin.getSessionManager().getSession(player);
		if(session == null)
			return;
		player.teleport(session.getLocation());
		plugin.getSessionManager().removeSession(player.getUniqueId());
	}
	*/
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerRespawnEvent event) {
		Session session = plugin.getSessionManager().getSession(event.getPlayer());
		if(session != null){
			event.setRespawnLocation(session.getDungeon().getStart());
		}
	}
}
