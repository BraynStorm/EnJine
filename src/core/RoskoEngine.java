package core;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.gl.Camera;
import core.gl.FPSManager;
import core.gl.LightDirectional;
import core.gl.Mesh;
import core.gl.MeshTransform;
import core.gl.Rectangle;
import core.gl.TransformRectangle;
import core.gl.TransformTTF;
import core.gl.TrueTypeFont;
import core.gl.Shader;
import core.gl.Texture;
import core.gl.Window;
import core.gl.gui.GUIButton;
import core.gl.particles.ParticleEmitter;
import core.gl.particles.ParticleHolder;
import core.gl.particles.ParticleTransform;
import core.gl.particles.tests.ParticleEmitterCannon;
import core.gl.particles.tests.ParticleSystemCannon;
import core.gl.storage.FontLibrary;
import core.math.Time;
import core.math.Vector3f;
import core.resources.ResourceManager;

public class RoskoEngine {
	public static Shader guiShader;
	public static Shader worldShader;
	public static Shader particleShader;
	
	public static Camera camera;
	public static Time timer = new Time();
	
	private static boolean running = false;
	
	/**
	 * Initializes {@link RoskoEngine}. 'Singleton'.
	 */
	public static void init() {
		new Window().create();

		guiShader = new Shader();
		guiShader.loadShader("gui");
		
		guiShader.addUniform("transform");
		guiShader.addUniform("color");
		
		
		
		worldShader = new Shader();
		worldShader.loadShader("world");
		// TODO: picking Color worldShader.addUniform("color");
		worldShader.addUniform("transform");
		worldShader.addUniform("camera_translation");
		worldShader.addUniform("camera_rotation");
		worldShader.addUniform("projection_matrix");
		
		worldShader.addUniform("material.diffuseColor");
		
		worldShader.addUniform("sunlight_direction");
		worldShader.addUniform("sunlight_color");
		
		
		
		particleShader = new Shader();
		particleShader.loadShader("particle");
		
		particleShader.addUniform("particleOriginTransform");
		particleShader.addUniform("particleTransform");
		particleShader.addUniform("particleColor");
		particleShader.addUniform("camera_rotation");
		particleShader.addUniform("camera_translation");
		particleShader.addUniform("projection_matrix");
		
		/* General Stuff */
		ResourceManager.getInstance();
		
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
	 * Start the engine. {@link RoskoEngine.stop} to stop it.
	 */
	public static void start() {
		running = true;
		Texture.unbind();
		
		camera = new Camera();
		LightDirectional sun = LightDirectional.getSun();
		
		timer.loop();
		while(!Window.shouldClose() && running){
			
			/* FPS Limit */
			if (!FPSManager.shouldDrawFrame()){
				FPSManager.endFrame();
				continue;
			}
			
			FPSManager.startFrame();
			Window.beginDrawing();
			
			worldShader.bind();
			worldShader.setUniform("projection_matrix", Window.getProjectionMatrix());
			sun.use();
			camera.loop();
			
			particleShader.bind();
			particleShader.setUniform("projection_matrix", Window.getProjectionMatrix());
			camera.setTranslationMatrix();
			camera.setRotationMatrix();
			
			
			guiShader.bind();
			
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
