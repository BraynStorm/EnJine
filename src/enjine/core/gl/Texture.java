package enjine.core.gl;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import enjine.core.resources.ResourceManager;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;


public class Texture {

	public int textureID;
	public String path;
	
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
	
	/**
     * Binds the texture and sets its Wrap S/T values to the given one.
     * @param val
     */
	public void setWrap(int val){
	    bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, val);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, val);
    }
	
	/**
	 * Binds the texture and sets its Filter Min/Mag values to the given one.
	 * @param val
	 */
	public void setFilter(int val){
	    bind();
	    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, val);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, val);
    }
	
	public static void bind(Texture t){
		if(t == null)
			Texture.unbind();
		else
			t.bind();
	}
	
	public static void unbind() {
		if(defaultTexture == null)
			defaultTexture = ResourceManager.getTexture("default");
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


