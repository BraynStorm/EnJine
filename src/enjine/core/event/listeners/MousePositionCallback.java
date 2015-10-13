package enjine.core.event.listeners;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import enjine.core.event.EventManager;
import enjine.core.event.types.MousePositionEvent;
import enjine.core.gl.Window;

public class MousePositionCallback extends GLFWCursorPosCallback {

	private static MousePositionCallback instance;
	
	public static MousePositionCallback getInstance() {
		if(instance == null)
			instance = new MousePositionCallback();
		
		return instance;
	}
	
	private MousePositionCallback(){
		GLFW.glfwSetCursorPosCallback(Window.getID(), this);
	}
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		EventManager.invokeEvent(new MousePositionEvent(xpos, ypos));
	}

}
