package yourselvs.dungeons.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.events.DungeonPersonalRecordEvent;
import yourselvs.dungeons.events.DungeonWorldRecordEvent;
import yourselvs.dungeons.events.PlayerFinishDungeonEvent;
import yourselvs.dungeons.events.PlayerStartDungeonEvent;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;

public class DungeonListener implements Listener {
	private Dungeons plugin;
	
	public DungeonListener(Dungeons instance) {
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void onWorldRecord(DungeonWorldRecordEvent event) {
		plugin.getMessenger().beatDungeonWR(event.getPreviousRecord(), event.getRecord());
	}
	
	public void onPersonalRecord(DungeonPersonalRecordEvent event) {
		plugin.getMessenger().beatDungeonPR(event.getPreviousRecord(), event.getRecord());
	}
	
	public void onFinishDungeon(PlayerFinishDungeonEvent event) {		
		Record record = new Record(event.getSession().getPlayer(), event.getSession().getDungeon(), event.getTime(), plugin.getFormatter().subtractTime(event.getTime(), event.getSession().getStart()));
		plugin.getSessionManager().removeSession(record.getPlayer());
		plugin.getRecordManager().addRecord(record);
		
		Record pr = plugin.getRecordManager().getFastestRecord(record.getDungeon(), record.getPlayer());
		Record wr = plugin.getRecordManager().getFastestRecord(record.getDungeon());
		
		if(event.getTime().getTime() < pr.getTime().getTime()){
			DungeonPersonalRecordEvent prEvent = new DungeonPersonalRecordEvent(record, pr);
			plugin.getServer().getPluginManager().callEvent(prEvent);
		}
		if(event.getTime().getTime() < wr.getTime().getTime()){ // if the player beats the world record
			DungeonWorldRecordEvent wrEvent = new DungeonWorldRecordEvent(record, wr);
			plugin.getServer().getPluginManager().callEvent(wrEvent);
		}
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		Session session = plugin.getSessionManager().getSession(event.getPlayer());
		if(session != null){
			plugin.getSessionManager().removeSession(session.getPlayer());
			plugin.getMessenger().quitDungeon(event.getPlayer(), session);
		}
	}
	
	public void onStartDungeon(PlayerStartDungeonEvent event) {
		Session session = new Session(event.getPlayer().getUniqueId(), event.getDungeon(), event.getTime(), event.getPlayer().getLocation(), event.getPlayer().getInventory());
		plugin.getSessionManager().addSession(session);
		plugin.getMessenger().startDungeon(event.getPlayer(), plugin.getSessionManager().getSession(event.getPlayer()));
	}
}
