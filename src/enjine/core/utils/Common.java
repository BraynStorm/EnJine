package enjine.core.utils;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import enjine.core.gl.GLColor;
import enjine.core.gl.TransformRectangle;
import enjine.core.math.Vertex;

public class Common {
	public static final String gameFolder = ((new File(System.getProperty("java.class.path"))).getAbsoluteFile().getParentFile().toString()).split(";")[0];
	/** gameFolder/data/ */
	public static final String dataFolder = gameFolder + "/data/";
	/** gameFolder/logs/ */
	public static final String logsFolder = gameFolder + "/logs/";
	
	/**
	 * Use this instead of makeing new {@link Random} objects.
	 */
	public static final Random random = new Random();
	
	/* Picking */
	private static float redProgress = 0;
	private static float greenProgress = 0;
	private static float blueProgress = 0;
	
	/**
	 * Aqquires a unique color for the picking phase. Every object that calls this method must save the given color as it gets counted as 'taken'.
	 * @return A unique {@link GLColor}
	 */
	public static GLColor aqquirePickingColor(){
		redProgress++;
		
		if(redProgress > 255){
			redProgress = 0;
			greenProgress++;
		}
		
		if(greenProgress > 255){
			greenProgress = 0;
			blueProgress++;
		}
		
		if(blueProgress > 255){
			throw new RuntimeException("[Error][Crit][Impossibru] Ran out of picking colors. What the actual fuck?");
		}
		
		return new GLColor(redProgress / 255, greenProgress / 255, blueProgress / 255, 1);
	}
	
	/** Simplified {@link Common#bufferData(FloatBuffer, int, int)}. Creates a new buffer and drawDynamic = {@link GL15.GL_STATIC_DRAW}. */
	public static int bufferData(FloatBuffer data, int type)					{ return bufferData(GL15.glGenBuffers(), data, type, GL15.GL_STATIC_DRAW); }
	/** Simplified {@link Common#bufferData(int, FloatBuffer, int, int)}. Creates a new buffer. */
	public static int bufferData(FloatBuffer data, int type, int drawDynamic)	{ return bufferData(GL15.glGenBuffers(), data, type, drawDynamic);	}
	/** Simplified {@link Common#bufferData(int, FloatBuffer, int, int)}. Uses the given buffer and drawDynamic = {@link GL15.GL_STATIC_DRAW}. */
	public static int bufferData(int buffer, FloatBuffer data, int type)		{ return bufferData(buffer, data, type, GL15.GL_STATIC_DRAW); }
	/**
	 * Buffers the given {@link FloatBuffer} in the give OpenGL buffer handle with the given drawDynamic.
	 * @param buffer The OpenGL buffer handle
	 * @param data FloatBuffer containing the data.
	 * @param type The buffer type ({@link GL15.GL_ARRAY_BUFFER}, {@link GL15.GL_ELEMENT_ARRAY_BUFFER}, etc.).
	 * @param drawDynamic ({@link GL15.GL_STATIC_DRAW}, {@link GL15.GL_DYNAMIC_DRAW}, etc.)
	 * @return The buffer handle.
	 */
	public static int bufferData(int buffer, FloatBuffer data, int type, int drawDynamic){
		GL15.glBindBuffer(type, buffer);
		GL15.glBufferData(type, data, drawDynamic);
		GL15.glBindBuffer(type, 0);
		return buffer;
	}
	
	/** Integer version of {@link Common#bufferData(FloatBuffer, int)}. */
	public static int bufferData(IntBuffer data, int type)					{ return bufferData(GL15.glGenBuffers(), data, type, GL15.GL_STATIC_DRAW); }
	/** Integer version of {@link Common#bufferData(FloatBuffer, int, int)}. */
	public static int bufferData(IntBuffer data, int type, int drawDynamic)	{ return bufferData(GL15.glGenBuffers(), data, type, drawDynamic);	}
	/** Integer version of {@link Common#bufferData(int, FloatBuffer, int)}. */
	public static int bufferData(int buffer, IntBuffer data, int type)		{ return bufferData(buffer, data, type, GL15.GL_STATIC_DRAW); }
	/** Integer version of {@link Common#bufferData(int, FloatBuffer, int, int)}. */
	public static int bufferData(int buffer, IntBuffer data, int type, int drawDynamic){
		GL15.glBindBuffer(type, buffer);
		GL15.glBufferData(type, data, drawDynamic);
		GL15.glBindBuffer(type, 0);
		return buffer;
	}
	
	/** Convenient version of {@link Common#bufferData(FloatBuffer, int)}. @return A new buffer filled with the given data. */
	public static int bufferData(int type, float... data){ return bufferData(createBuffer(data), type); }
	/** Integer version of {@link Common#bufferData(int, float...)}. */
	public static int bufferData(int type, int... data){ return bufferData(createBuffer(data), type); }
	
	/**
	 * Creates a new {@link FloatBuffer} and fills it with data.
	 * @param data The data to be filled in the new FloatBuffer.
	 * @return The newly created FloatBuffer.
	 */
	public static FloatBuffer createBuffer(float... data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Integer version of {@link Common#createBuffer(float...)}.
	 */
	public static IntBuffer createBuffer(int... data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Combines a {@link List} of {@link IntBuffer}s with the same length into a single IntBuffer (sequentially).
	 * @param list The list containing all the IntBuffers.
	 * @param bufferLength The length of each buffer. 
	 * @return
	 */
	public static IntBuffer combineBuffers(List<IntBuffer> list, int bufferLength) {
		IntBuffer finalBuffer = BufferUtils.createIntBuffer(list.size() * bufferLength);
		for(int i = 0; i < list.size(); i++){
			for(int j = 0; j < bufferLength; j++){
				finalBuffer.put((i * bufferLength) + j, list.get(i).get());
			}
		}
		
		return finalBuffer;
	}
	
	/** Renders a VBO/IBO/DrawCount pair on the screen. See {@link Common#renderBO(int, int, int, boolean)} for more information. */
	public static void renderBO(int vbo, int ibo, int drawCount){ renderBO(vbo, ibo, drawCount, false); }
	
	/**
	 * Renders a VBO/IBO/drawCount pair on the screen.
	 * @param vbo OpenGL VBO handle.
	 * @param ibo OpenGL IBO handle.
	 * @param drawCount The length of the IBO.
	 * @param hasNormals Set to true if the VBO contains normal data.
	 */
	public static void renderBO(int vbo, int ibo, int drawCount, boolean hasNormals){
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, hasNormals ? Vertex.SIZE : 20, 0); // positions
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, hasNormals ? Vertex.SIZE : 20, 12); // texCoords
		
		if(hasNormals)
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Vertex.SIZE, 0); // normals
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL11.glDrawElements(GL11.GL_TRIANGLES, drawCount, GL11.GL_UNSIGNED_INT, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	/**
	 * Concats the {@link Common#dataFolder} and given relative path.
	 * @param relativePath
	 * @return The absolute version of the given path.
	 */
	public static String makeAbsoluteDataPath(String relativePath){
		return dataFolder + relativePath;
	}
	
	
	/**
	 * Picks a pseudo-random color.
	 * @param randomAlpha If false then alpha = 1 else alpha = rand();
	 * @return The random color.
	 */
	public static GLColor randomColor(boolean randomAlpha){
		return new GLColor(
				random.nextFloat(), // R
				random.nextFloat(), // G
				random.nextFloat(), // B
				randomAlpha ? random.nextFloat() : 1);
	}
	
	/**
	 * Picks a random color with given alpha
	 * @param alpha
	 * @return The random color.
	 */
	public static GLColor randomColor(float alpha){
		return new GLColor(
				random.nextFloat(), // R
				random.nextFloat(), // G
				random.nextFloat(), // B
				alpha);
	}
	
	/**
	 * Checks if a particular X,Y coordinate pair is inside a rectangle.
	 * @param x The X coord part of the pair.
	 * @param y The Y coord part of the pair.
	 * @param transform The {@link TransformRectangle} that represents a Rectangle.
	 * @return The check result.
	 */
	public static boolean coordsInsideRectangle(int x, int y, TransformRectangle transform){
		return 
				x >= transform.getXPosition() &&
				y >= transform.getYPosition() &&
				x <= transform.getWidth() + transform.getXPosition() &&
				y <= transform.getHeight() + transform.getYPosition();
	}
	/**
	 * Checks if a given float value is clamped to the given min/max values
	 * @param src The float value that's being checked.
	 * @param min The minimum value.
	 * @param max The maximum value.
	 * @return The check result
	 */
	public static boolean isClamped(float src, float min, float max){ return src <= max && src >= min; }
	
	/**
	 * Clamps(limits) a certain float value between two extremes.
	 * @param src The value
	 * @param min The minimum value.
	 * @param max The maximum value.
	 * @return The clamped value.
	 */
	public static float clamp(float src, float min, float max){return Math.min(Math.max(src, min), max); }
}
