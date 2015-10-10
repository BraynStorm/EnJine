package core.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import core.gl.Texture;
import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureLoader {
	
	static Texture loadTexture(InputStream stream) throws IOException{
		return loadTexture(stream, false, true, GL11.GL_LINEAR);
	}
	
	static Texture loadTexture(InputStream stream, boolean flipped, int filter) throws IOException{
		return loadTexture(stream, flipped, true, filter);
	}
	
	static Texture loadTexture(InputStream stream, boolean flipped, boolean repeat, int filter) throws IOException{
		PNGDecoder decoder = new PNGDecoder(stream);
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		
		decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
		buffer.flip();
		
		int textureID = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP);
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		Texture t = new Texture(textureID,  decoder.getWidth(), decoder.getHeight(), Format.RGBA);
		
		return t;
	}
}
