package enjine.core.event;

import com.google.common.eventbus.Subscribe;

import enjine.core.event.types.MousePositionEvent;

public class Mouse{
	private int x;
	private int y;
	
	public Mouse(){
		EventManager.register(this);
	}
	
	@Subscribe
	public void mouseMoved(MousePositionEvent event){
		x = event.getX();
		y = event.getY();
	}
	
	public int getX(){ return x; }
	public int getY(){ return y; }
}
