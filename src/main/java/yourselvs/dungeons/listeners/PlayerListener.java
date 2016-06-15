package yourselvs.dungeons.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import yourselvs.dungeons.Dungeons;
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
	
	
}
