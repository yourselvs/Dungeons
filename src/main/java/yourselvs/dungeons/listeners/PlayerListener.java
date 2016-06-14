package yourselvs.dungeons.listeners;

import java.text.ParseException;
import java.util.Date;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.events.PlayerFinishDungeonEvent;
import yourselvs.dungeons.records.Record;
import yourselvs.dungeons.sessions.Session;


public class PlayerListener implements Listener{
	private Dungeons plugin;
	
	public PlayerListener(Dungeons instance) {
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		Session session = plugin.getSessionManager().getSession(event.getPlayer());
		if(session != null)
			plugin.getMessenger().quitDungeon(event.getPlayer(), session);
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		Session session = plugin.getSessionManager().getSession(event.getPlayer());
		if(session != null) // if the joining player is in a dungeon
			plugin.getMessenger().resumeDungeon(event.getPlayer(), session);
		
	}
	
	public void onFinishDungeon(PlayerFinishDungeonEvent event) {		
		Record record = new Record(event.getSession().getPlayer(), event.getSession().getDungeon(), event.getTime(), plugin.getFormatter().subtractTime(event.getTime(), event.getSession().getStart()));
		plugin.getSessionManager().removeSession(record.getPlayer());
		plugin.getRecordManager().addRecord(record);
		
		Record pr = plugin.getMongo().getFastestTime(event.getDungeon(), event.getPlayer());
		Record wr = plugin.getMongo().getFastestTime(event.getDungeon());
		
		Date prDate = null;
		Date wrDate = null;
		try {
			prDate = plugin.getFormatter().parse(plugin.subtractTime(pr.startTime, pr.finishTime));
			wrDate = plugin.getFormatter().parse(plugin.subtractTime(wr.startTime, wr.finishTime));
		} catch (ParseException e) {e.printStackTrace();}
		
		if(event.getTime().getTime() < prDate.getTime()) // if the player beats their personal record
			plugin.getMessenger().beatDungeonPR(event.getPlayer(), pr, record);
		
		if(event.getTime().getTime() < wrDate.getTime()) // if the player beats the world record
			plugin.getMessenger().beatDungeonWR(event.getPlayer(), wr, record);
	}
	
	public void onStartDungeon(PlayerStartDungeonEvent event) {
		plugin.getMongo().createRecord(event.getDungeon(), event.getPlayer());
		plugin.getMessenger().startDungeon(event.getPlayer(), plugin.getMongo().getCurrentRecord(event.getPlayer()));
	}
}
