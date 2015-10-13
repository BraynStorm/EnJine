package enjine.core.gl;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

import com.google.common.eventbus.Subscribe;

import enjine.core.event.EventManager;
import enjine.core.event.types.WindowSizeEvent;
import enjine.core.math.Matrix4f;
import enjine.core.math.Vector2f;
import enjine.core.utils.Defaults;

public class Window{
	private static int 	width = Defaults.WINDOW_WIDTH,
						height = Defaults.WINDOW_HEIGHT;
	private static boolean I = false;
	private static long windowID;
	private static Matrix4f projectionMatrix;
	
	
	public static long getID(){return windowID;}
	
	public static float getAspectRatio(){
		return (float)width / (float)height;
	}
	
	public Window create(){
		if(GLFW.glfwInit() != 1 || I) // a.k.a NotInitialized
			throw new RuntimeException("[Error][Crit]");
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		
		windowID = GLFW.glfwCreateWindow(width, height, "Game", 0, 0);
		
		GLFW.glfwMakeContextCurrent(windowID);
		GLContext.createFromCurrent();
		EventManager.initialize();
		
		EventManager.register(this);
		projectionMatrix = new Matrix4f();
		projectionMatrix.updateProjection(width, height);
		
		I = true;
		return this;
	}
	
	public static void show(){
		GLFW.glfwShowWindow(windowID);
		GLFW.glfwSwapInterval(1);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glFrontFace(GL11.GL_CW);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// @Deprecated GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		
		GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
		
	}
	
	public static void hide(){
		GLFW.glfwHideWindow(windowID);
	}
	
	public static boolean shouldClose(){
		return GLFW.glfwWindowShouldClose(windowID) == 1;
	}
	
	public static void beginDrawing(){
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void endDrawing(){
		GLFW.glfwPollEvents();
		//GLFW.glfwWaitEvents();
		GLFW.glfwSwapBuffers(windowID);
	}
	
	public static void close() {
		GLFW.glfwDestroyWindow(windowID);
		GLFW.glfwTerminate();
	}

	public static float getWidth() {
		return width;
	}
	
	public static float getHeight() {
		return height;
	}

	@Subscribe
	public void trigger(WindowSizeEvent event) {
		width = event.width;
		height = event.height;
		
		GL11.glViewport(0, 0, width, height);
		projectionMatrix.updateProjection(width, height);
		//Start.f = width < 1000;
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}
