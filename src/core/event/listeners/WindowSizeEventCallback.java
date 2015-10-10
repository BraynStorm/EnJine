package core.event.listeners;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;

import core.event.EventManager;
import core.event.types.WindowSizeEvent;
import core.gl.Window;

public class WindowSizeEventCallback extends GLFWWindowSizeCallback {
	private static WindowSizeEventCallback instance;
	
	public static WindowSizeEventCallback getInstance() {
		if(instance == null)
			instance = new WindowSizeEventCallback();
		
		return instance ;
	}
	
	private WindowSizeEventCallback(){
		GLFW.glfwSetWindowSizeCallback(Window.getID(), this);
	}
	
	@Override
	public void invoke(long window, int width, int height) {
		EventManager.invokeEvent(new WindowSizeEvent(width, height));
	}
}
