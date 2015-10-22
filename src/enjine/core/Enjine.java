package enjine.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import enjine.core.gl.Camera;
import enjine.core.gl.FPSManager;
import enjine.core.gl.GLColor;
import enjine.core.gl.LightDirectional;
import enjine.core.gl.Mesh;
import enjine.core.gl.MeshTransform;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.gl.Window;
import enjine.core.gl.gui.GUILabel;
import enjine.core.gl.gui.Rectangle;
import enjine.core.gl.particles.RealParticle;
import enjine.core.math.Time;
import enjine.core.resources.ResourceManager;

public class Enjine {
	public static Shader guiShader;
	public static Shader worldShader;
	public static Shader particleShader;
	
	public static Camera camera;
	public static Time timer = new Time();
	
	private static boolean running = false;
	
	/**
	 * Initializes {@link Enjine}. 'Singleton'.
	 */
	public static void init() {
		new Window().create();

		guiShader = new Shader();
		guiShader.loadShader("gui");
		
		guiShader.addUniform("transform");
		guiShader.addUniform("color");
		
		
		
		worldShader = new Shader();
		worldShader.loadShader("world");
		// TODO: picking Color for selecting 3D objects:  worldShader.addUniform("color");
		
		worldShader.addUniform("viewMatrixT");
		worldShader.addUniform("viewMatrixR");
		worldShader.addUniform("projectionMatrix");
		worldShader.addUniform("transformMatrix");
		worldShader.addUniform("material.diffuseColor");
		worldShader.addUniform("sunlight_direction");
		worldShader.addUniform("sunlight_color");
		
		
		
		particleShader = new Shader();
		particleShader.loadShader("particle");
		
		particleShader.addUniform("viewMatrixT");
        particleShader.addUniform("viewMatrixR");
        particleShader.addUniform("projectionMatrix");
        particleShader.addUniform("originMatrix");
		particleShader.addUniform("transformMatrix");
		
		particleShader.addUniform("frameCount");
		particleShader.addUniform("currentFrame");
		particleShader.addUniform("particleColor");
		
		
		
		/* General Stuff */
		
		ResourceManager.initializeParticles();
		
		/* Shapes */
		Rectangle.initialize();
		
		Window.show();
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}
	
	public static void destroy(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		guiShader.delete();
		error();
		Window.close();
		
	}
	
	/**
	 * Start the engine. {@link Enjine.stop} to stop it.
	 */
	public static void start() {
		running = true;
		Texture.unbind();
		
		camera = new Camera();
		
		/* FONTS */
		GUILabel fpsLabel = new GUILabel();
		fpsLabel.setFont("consolas", 50).setWidth(100).setHeight(50).setTranslationX(500).setTranslationY(25);
		fpsLabel.setBackgroundColor(GLColor.CYAN);
		
		/**
		 * Tesing Stuff
		 */
		LightDirectional sun = LightDirectional.getSun();
		MeshTransform cannonTransfrom = new MeshTransform();
		cannonTransfrom.setTranslationZ(2);
		cannonTransfrom.setScale(1f);
		Mesh naDakataChoveka = ResourceManager.getMesh("cannon");
		MeshTransform chovekaTransform = new MeshTransform();
		chovekaTransform.setTranslationZ(2f).setScale(1f);
		RealParticle smoke = ResourceManager.getAnimatedParticle("smokePuff");
		
		/*
		 * End of Testing Stuff
		 */
		timer.loop();
		while(!Window.shouldClose() && running){
			
			/* FPS Limit */
			if (!FPSManager.shouldDrawFrame()){
				FPSManager.endFrame();
				continue;
			}
			
			FPSManager.startFrame();
			Window.beginDrawing();
			
			camera.loop();
			
			worldShader.bind();
			worldShader.setUniform("projectionMatrix", Window.getProjectionMatrix());
			camera.setViewMatrix();
			sun.use();
			naDakataChoveka.render(chovekaTransform);
			
			particleShader.bind();
			particleShader.setUniform("projectionMatrix", Window.getProjectionMatrix());
			particleShader.setUniformi("currentFrame", 0);
			particleShader.setUniformi("frameCount", 1);
			camera.setViewMatrix();
			smoke.render();
			
			guiShader.bind();
			fpsLabel.setText(FPSManager.getFPS()).render();
			
			Window.endDrawing();
			FPSManager.endFrame();
			timer.loop();
			
		}
	}
	
	public static void stop(){
		running = false;
	}
	
	/**
	 * Dumps any OPENGL-related errors.
	 */
	private static void error(){
		int k = GL11.glGetError();
		System.out.println(k);
		while (k != 0){
			k = GL11.glGetError();
			System.out.println(k);
		}
	}
}
