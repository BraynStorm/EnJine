package core.gl;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import core.resources.ResourceManager;


public class Texture {

	public int textureID;
	
	Format format;
	
	int width;
	int height;
	
	private static Texture defaultTexture;
	
	public Texture(int id, int width, int height, Format format) {
		this.format = format;
		this.width = width;
		this.height = height;
		textureID = id;
	}
	
	/**
	 * No null checks. use {@link Texture.bind(Texture)}
	 */
	public void bind(){
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public static void bind(Texture t){
		if(t == null)
			Texture.unbind();
		else
			t.bind();
	}
	
	public static void unbind() {
		if(defaultTexture == null)
			defaultTexture = ResourceManager.getInstance().getTexture(0);
		defaultTexture.bind();
	}

}
