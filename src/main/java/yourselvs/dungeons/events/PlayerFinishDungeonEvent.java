package yourselvs.dungeons.events;

import java.util.Date;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import yourselvs.dungeons.sessions.Session;

public class PlayerFinishDungeonEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Session session;
	private Date time;
	private boolean cancelled;

	public PlayerFinishDungeonEvent(Session session, Date time) {
		this.session = session;
		this.time = time;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCanceled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
