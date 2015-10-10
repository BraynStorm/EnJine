package core.event.listeners;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import core.event.EventManager;
import core.event.types.MouseButtonEvent;
import core.gl.Window;

public class MouseButtonCallback extends GLFWMouseButtonCallback{

	private static MouseButtonCallback instance;
	
	public static MouseButtonCallback getInstance() {
		if(instance == null)
			instance = new MouseButtonCallback();
		
		return instance;
	}
	
	private MouseButtonCallback(){
		GLFW.glfwSetMouseButtonCallback(Window.getID(), this);
	}
	
	@Override
	public void invoke(long window, int button, int action, int mods) {
		EventManager.invokeEvent(
				new MouseButtonEvent(
						EventManager.getMouse().getX(),
						EventManager.getMouse().getY(),
						button,
						action,
						mods
					)
			);
	}

}
