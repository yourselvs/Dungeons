package yourselvs.dungeontracker.sessions;

import java.util.List;
import java.util.UUID;

import yourselvs.dungeontracker.Dungeons;

public class SessionManager {
	private Dungeons plugin;
	
	public List<Session> sessions;

	/**
	 * Creates a manager that handles sessions of players in a dungeon.
	 * @param instance	The instance
	 */
	public SessionManager(Dungeons instance){
		this.plugin = instance;
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
		plugin.getDB().addSession(session);
	}
	
	/**
	 * Removes a session from server memory.
	 * @param player
	 */
	public Session removeSession(UUID player) {
		Session session = null;
		for(int i = 0; i < sessions.size(); i++)
			if(sessions.get(i).getPlayer() == player){
				session = sessions.remove(i);
				plugin.getDB().removeSession(player);
				break;
			}
		return session;
	}
	
	/**
	 * Loads all sessions from the database into the plugin.
	 * @return	The list of sessions
	 */
	public void loadSessions(){sessions = plugin.getDB().getSessions();}
}
