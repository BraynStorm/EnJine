package enjine.core.event;

import com.google.common.eventbus.EventBus;

import enjine.core.event.listeners.KeyEventCallback;
import enjine.core.event.listeners.MouseButtonCallback;
import enjine.core.event.listeners.MousePositionCallback;
import enjine.core.event.listeners.ScrollEventCallback;
import enjine.core.event.listeners.WindowSizeEventCallback;
import enjine.core.event.types.Event;

public class EventManager {
	
	private static EventBus eventBus;
	
	private static boolean I = false;
	public static void initialize(){
		if (I) return;
		
		eventBus = new EventBus();
		mouse = new Mouse();
		
		WindowSizeEventCallback.getInstance();
		MousePositionCallback.getInstance();
		MouseButtonCallback.getInstance();
		KeyEventCallback.getInstance();
		ScrollEventCallback.getInstance();
		
		I = true;
	}
	
	public static void register(Object o){
		eventBus.register(o);
	}

	public static <T extends Event> void invokeEvent(T event) {
		eventBus.post(event);
	}
	
	
	private static Mouse mouse;
	
	public static Mouse getMouse(){
		return mouse;
	}
}
