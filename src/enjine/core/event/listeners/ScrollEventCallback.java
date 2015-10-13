package enjine.core.event.listeners;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWScrollCallback;

import enjine.core.event.EventManager;
import enjine.core.event.types.ScrollEvent;
import enjine.core.gl.Window;

public class ScrollEventCallback extends GLFWScrollCallback {
	
	private static ScrollEventCallback instance;
	
	public static ScrollEventCallback getInstance() {
		if(instance == null)
			instance = new ScrollEventCallback();
		
		return instance;
	}
	
	private ScrollEventCallback(){
		GLFW.glfwSetScrollCallback(Window.getID(), this);
	}
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		EventManager.invokeEvent(new ScrollEvent((int)xoffset, (int)yoffset));
	}

}
