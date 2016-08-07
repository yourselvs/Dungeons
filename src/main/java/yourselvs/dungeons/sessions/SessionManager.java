package yourselvs.dungeons.sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import yourselvs.dungeons.Dungeons;

public class SessionManager {
	private Dungeons plugin;
	
	public List<Session> sessions;

	/**
	 * Creates a manager that handles sessions of players in a dungeon.
	 * @param instance	The instance
	 */
	public SessionManager(Dungeons instance){
		this.plugin = instance;
		sessions = new ArrayList<Session>();
	}
	
	/**
	 * @return	The list of all player sessions in memory.
	 */
	public List<Session> getSessions() {return sessions;}
	
	/**
	 * Adds a session to the plugin.
	 * @param session	The session that is added.
	 */
	public void addSession(Session session) {
		sessions.add(session);
		new Thread(new Runnable() {
	        public void run(){
	        	plugin.getDB().addSession(session);
	        }
	    }).start();
	}
	
	/**
	 * Removes a session from server memory.
	 * @param player	The UUID of the player to search for.
	 */
	public void removeSession(UUID player) {
		for(int i = 0; i < sessions.size(); i++)
			if(sessions.get(i).getPlayer() == player){
				sessions.remove(i);				
				break;
			}
		new Thread(new Runnable() {
	        public void run(){
	        	plugin.getDB().removeSession(player);
	        }
	    }).start();
		
	}
	
	/**
	 * Removes all sessions pretaining to a certain dungeon from memory.
	 * @param dungeon	The name of the dungeon to search for.
	 */
	public void removeSession(String dungeon) {
		for(int i = 0; i < sessions.size(); i++)
			if(sessions.get(i).getDungeon().getName().equalsIgnoreCase(dungeon)){
				Session session = sessions.get(i);
				Player player = plugin.getServer().getPlayer(session.getPlayer());
				player.teleport(session.getLocation());
				sessions.remove(i);	
				new Thread(new Runnable() {
			        public void run(){
			        	plugin.getDB().removeSession(session.getPlayer());
			        }
			    }).start();
				i--;
			}
	}
	
	/**
	 * Finds the session of a specific player.
	 * @param player	The player to search for
	 * @return			the session found. If no session is found, returns null.
	 */
	public Session getSession(Player player){
		for(Session session : sessions)
			if(session.getPlayer().compareTo(player.getUniqueId()) == 0)
				return session;
		return null;
	}
	
	/**
	 * Finds all sessions in a dungeon.
	 * @param dungeon	The name of the dungeon to search for.
	 * @return			the sessions found.
	 */
	public List<Session> getSessions(String dungeon){
		List<Session> foundSessions = new ArrayList<Session>();
		for(Session session : sessions)
			if(session.getDungeon().getName().equalsIgnoreCase(dungeon))
				foundSessions.add(session);
		return foundSessions;
	}

	
	/**
	 * Loads all sessions from the database into the plugin.
	 * @return	The list of sessions
	 */
	public void loadSessions(){
		sessions = plugin.getDB().getSessions();
	}
}
