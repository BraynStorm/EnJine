package enjine.core.event.types;

public class ScrollEvent extends Event {
	public int x;
	/**
	 * The actually important one.
	 */
	public int y;
	
	public ScrollEvent(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
}
