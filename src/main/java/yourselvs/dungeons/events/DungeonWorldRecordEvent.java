package yourselvs.dungeons.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import yourselvs.dungeons.records.Record;

public class DungeonWorldRecordEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	private Record record;
	private Record previousRecord;
	private boolean cancelled;

	public DungeonWorldRecordEvent(Record record, Record previousRecord) {
		this.record = record;
		this.previousRecord = previousRecord;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Record getRecord() {
		return record;
	}
	
	public void setRecord(Record record) {
		this.record = record;
	}
	
	public Record getPreviousRecord() {
		return previousRecord;
	}
	
	public void setPreviousRecord(Record previousRecord) {
		this.previousRecord = previousRecord;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCanceled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
