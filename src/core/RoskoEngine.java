package core;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import core.event.EventManager;
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
import core.gl.particles.ParticleEffectCircleShower;
import core.gl.storage.FontLibrary;
import core.math.Matrix4f;
import core.math.Time;
import core.resources.MeshLoader;
import core.resources.ResourceManager;
import core.resources.TextureLoader;

public class RoskoEngine {
	public static Shader guiShader;
	public static Shader worldShader;
	public static Shader particleShader;
	
	public static Camera camera;
	
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
		
		TransformTTF t = new TransformTTF();
		TransformTTF t2 = new TransformTTF();
		
		GUIButton button = new GUIButton(1);
		Rectangle rect = new Rectangle(new TransformRectangle().setTranslationX(100).setTranslationY(200).setWidth(50).setHeight(10));
		TrueTypeFont verdana = FontLibrary.requestFontWithSize("verdana", Font.TRUETYPE_FONT, 28);
		
		MeshTransform humanTransform = new MeshTransform();
		MeshTransform zeroTransform = new MeshTransform();
		MeshTransform PET = new MeshTransform();
		
		Mesh human = ResourceManager.getInstance().getMesh(0);
		
		ParticleEffectCircleShower pEffect = new ParticleEffectCircleShower(PET, 200, 10000);
		PET.setTranslationZ(3);
		PET.setTranslationY(-0.5f);
		LightDirectional sun = LightDirectional.getSun();
		
		t.setTranslationX(0).setTranslationY((int)Window.getHeight());
		humanTransform.setTranslationZ(3);
		
		
		button.getTransform().setTranslationX(10).setTranslationY(10).setWidth(188).setHeight(51);
		
		
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
			human.render(humanTransform);
			
			particleShader.bind();
			particleShader.setUniform("projection_matrix", Window.getProjectionMatrix());
			camera.setTranslationMatrix();
			camera.setRotationMatrix();
			pEffect.render();
			PET.setTranslationZ(PET.getTranslation().z-.001f);
			
			//PET.setTranslationX((float)(Time.getNano() / 1e10));
			
			//xyz.render(zeroTransform);
			
			guiShader.bind();
			//rect.render();
			//verdana.drawString(t, FPSManager.getFPS(), GLColor.TRANSPARENT);
			button.render();
			
			Window.endDrawing();
			FPSManager.endFrame();
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
