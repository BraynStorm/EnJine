package core.event.listeners;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import core.event.EventManager;
import core.event.types.KeyEvent;
import core.gl.Window;

public class KeyEventCallback extends GLFWKeyCallback {
	
	private static KeyEventCallback instance;
	
	public static KeyEventCallback getInstance() {
		if(instance == null)
			instance = new KeyEventCallback();
		
		return instance;
	}
	
	private KeyEventCallback(){
		GLFW.glfwSetKeyCallback(Window.getID(), this);
	}
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		EventManager.invokeEvent(new KeyEvent(key, scancode, action, mods));
	}

}
