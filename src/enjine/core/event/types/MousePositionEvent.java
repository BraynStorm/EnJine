package enjine.core.event.types;

public class MousePositionEvent extends Event {
	public int x;
	public int y;
	
	public MousePositionEvent(double x, double y) {
		this.x = (int) x;
		this.y = (int) y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
