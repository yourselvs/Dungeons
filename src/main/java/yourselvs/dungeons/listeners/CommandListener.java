package yourselvs.dungeons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;
import yourselvs.dungeons.Dungeons;
import yourselvs.dungeons.sessions.Session;

public class CommandListener implements Listener{
	Dungeons plugin;
	
	public CommandListener(Dungeons instance){
		this.plugin = instance;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		plugin.getMessenger().sendServerMessage("Command listener enabled");
	}
	
	@EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		Session session = plugin.getSessionManager().getSession(event.getPlayer());
		if(session == null)
			return;
		if(event.getMessage().charAt(0) != '/')
			return;
		if(event.getMessage().equalsIgnoreCase("/dungeon leave") || event.getMessage().equalsIgnoreCase("/suicide"))
			return;
		//String command = event.getMessage().substring(1, event.getMessage().indexOf(' '));
		plugin.getMessenger().sendMessage(event.getPlayer(), "You cannot use this command while you are in a dungeon. Use " + ChatColor.YELLOW + "/dungeon leave" + ChatColor.RESET + " to leave the dungeon.");
		event.setCancelled(true);
	}
}
