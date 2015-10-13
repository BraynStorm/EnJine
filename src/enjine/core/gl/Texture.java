package enjine.core.gl;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import enjine.core.resources.ResourceManager;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;


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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + height;
		result = prime * result + textureID;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Texture other = (Texture) obj;
		if (format != other.format)
			return false;
		if (height != other.height)
			return false;
		if (textureID != other.textureID)
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	
}


