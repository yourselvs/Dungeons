package yourselvs.dungeons.events;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import yourselvs.dungeons.dungeons.Dungeon;

public class PlayerLeaveDungeonEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Dungeon dungeon;
	private boolean cancelled;
	
	public PlayerLeaveDungeonEvent(Player player, Dungeon dungeon) {
		this.player = player;
		this.dungeon = dungeon;		
	}

	public HandlerList getHandlers() {
		return handlers;	
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Dungeon getDungeon() {
		return dungeon;
	}

	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCanceled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
