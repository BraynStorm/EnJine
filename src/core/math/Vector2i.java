package core.math;

import core.event.types.MouseButtonEvent;

public class Vector2i {
	public int x;
	public int y;
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2i(MouseButtonEvent evt){
		x = evt.x;
		y = evt.y;
	}
}
