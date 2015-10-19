package enjine.core;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import enjine.core.gl.Camera;
import enjine.core.gl.FPSManager;
import enjine.core.gl.LightDirectional;
import enjine.core.gl.Mesh;
import enjine.core.gl.MeshTransform;
import enjine.core.gl.Rectangle;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.gl.Window;
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
		worldShader.addUniform("transform");
		worldShader.addUniform("camera_translation");
		worldShader.addUniform("camera_rotation");
		worldShader.addUniform("projection_matrix");
		
		worldShader.addUniform("material.diffuseColor");
		
		worldShader.addUniform("sunlight_direction");
		worldShader.addUniform("sunlight_color");
		
		
		
		particleShader = new Shader();
		particleShader.loadShader("particle");
		
		particleShader.addUniform("frameCount");
		particleShader.addUniform("currentFrame");
		particleShader.addUniform("particleOriginTransform");
		particleShader.addUniform("particleTransform");
		particleShader.addUniform("particleColor");
		particleShader.addUniform("camera_rotation");
		particleShader.addUniform("camera_translation");
		particleShader.addUniform("projection_matrix");
		
		
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
		
		/**
		 * Tesing Stuff
		 */
		LightDirectional sun = LightDirectional.getSun();
		MeshTransform cannonTransfrom = new MeshTransform();
		cannonTransfrom.setTranslationZ(2);
		//cannonTransfrom.setScale(0.1f);
		//ParticleSystemCannon cannon = new ParticleSystemCannon(cannonTransfrom, ResourceManager.getAnimatedParticle("smokePuff"));
		
		Mesh naDakataChoveka = ResourceManager.getMesh("cannon");
		MeshTransform chovekaTransform = new MeshTransform();
		chovekaTransform.setTranslationZ(3.1f).setScale(0.1f);
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
			
			worldShader.bind();
			worldShader.setUniform("projection_matrix", Window.getProjectionMatrix());
			sun.use();
			camera.loop();
			naDakataChoveka.render(chovekaTransform);
			
			particleShader.bind();
			particleShader.setUniform("projection_matrix", Window.getProjectionMatrix());
			particleShader.setUniformi("currentFrame", 0);
			particleShader.setUniformi("frameCount", 1);
			camera.setTranslationMatrix();
			camera.setRotationMatrix();
			//cannon.loop();
			smoke.render();
			
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
