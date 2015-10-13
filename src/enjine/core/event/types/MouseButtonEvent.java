package enjine.core.event.types;

import org.lwjgl.glfw.GLFW;

public class MouseButtonEvent extends Event {
	
	public int x;
	public int y;
	public int button;
	public int action;
	public int modifiers;
	
	public static boolean guiClicked = false;
	
	public MouseButtonEvent(int x, int y, int button, int action, int modifiers) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.action = action;
		this.modifiers = modifiers;
	}
	
	public boolean isActionPress(){ return action == GLFW.GLFW_PRESS; }
	public boolean isActionRelease(){ return action == GLFW.GLFW_RELEASE; }
	
}
