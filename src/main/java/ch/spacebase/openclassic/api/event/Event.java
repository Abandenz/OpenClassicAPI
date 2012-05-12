package ch.spacebase.openclassic.api.event;

public abstract class Event {

	private final EventType type;
	
	public Event(EventType type) {
		this.type = type;
	}
	
	public final EventType getType() {
		return this.type;
	}
	
	public enum EventType {
		PLUGIN_ENABLE,
		PLUGIN_DISABLE;
	}
	
}
