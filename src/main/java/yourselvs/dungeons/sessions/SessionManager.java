package yourselvs.dungeons.sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	 * @param player	The UUID of the player to search for.
	 */
	public void removeSession(UUID player) {
		for(int i = 0; i < sessions.size(); i++)
			if(sessions.get(i).getPlayer() == player){
				sessions.remove(i);
				plugin.getDB().removeSession(player);
				break;
			}
	}
	
	/**
	 * Removes all sessions pretaining to a certain dungeon from memory.
	 * @param dungeon	The name of the dungeon to search for.
	 */
	public void removeSession(String dungeon) {
		for(int i = 0; i < sessions.size(); i++)
			if(sessions.get(i).getDungeon().getName().equalsIgnoreCase(dungeon)){
				plugin.getDB().removeSession(sessions.get(i).getPlayer());
				sessions.remove(i);	
				i--;
			}
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
	public void loadSessions(){sessions = plugin.getDB().getSessions();}
}
