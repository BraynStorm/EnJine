package core.utils;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import core.gl.GLColor;
import core.gl.TransformRectangle;
import core.math.Vertex;

public class Common {
	public static final String gameFolder = ((new File(System.getProperty("java.class.path"))).getAbsoluteFile().getParentFile().toString()).split(";")[0];
	public static final String dataFolder = gameFolder + "/data/";
	public static final String logsFolder = gameFolder + "/logs/";
	
	private static float redProgress = 0;
	private static float greenProgress = 0;
	private static float blueProgress = 0;
	
	/**
	 * Use this instead of makeing new {@link Random} objects.
	 */
	public static final Random random = new Random();
	
	
	public static int bufferData(FloatBuffer data, int type)					{ return bufferData(GL15.glGenBuffers(), data, type, GL15.GL_STATIC_DRAW); }
	public static int bufferData(FloatBuffer data, int type, int drawDynamic)	{ return bufferData(GL15.glGenBuffers(), data, type, drawDynamic);	}
	public static int bufferData(int buffer, FloatBuffer data, int type)		{ return bufferData(buffer, data, type, GL15.GL_STATIC_DRAW); }
	public static int bufferData(int buffer, FloatBuffer data, int type, int drawDynamic){
		GL15.glBindBuffer(type, buffer);
		GL15.glBufferData(type, data, drawDynamic);
		GL15.glBindBuffer(type, 0);
		return buffer;
	}
	
	public static int bufferData(IntBuffer data, int type)					{ return bufferData(GL15.glGenBuffers(), data, type, GL15.GL_STATIC_DRAW); }
	public static int bufferData(IntBuffer data, int type, int drawDynamic)	{ return bufferData(GL15.glGenBuffers(), data, type, drawDynamic);	}
	public static int bufferData(int buffer, IntBuffer data, int type)		{ return bufferData(buffer, data, type, GL15.GL_STATIC_DRAW); }
	public static int bufferData(int buffer, IntBuffer data, int type, int drawDynamic){
		GL15.glBindBuffer(type, buffer);
		GL15.glBufferData(type, data, drawDynamic);
		GL15.glBindBuffer(type, 0);
		return buffer;
	}
	
	public static int bufferData(int type, float... data){ return bufferData(createBuffer(data), type); }
	public static int bufferData(int type, int... data){ return bufferData(createBuffer(data), type); }
	
	public static FloatBuffer createBuffer(float... data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer createBuffer(int... data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer combineBuffers(ArrayList<IntBuffer> list, int bufferLength) {
		IntBuffer finalBuffer = BufferUtils.createIntBuffer(list.size() * bufferLength);
		for(int i = 0; i < list.size(); i++){
			for(int j = 0; j < bufferLength; j++){
				finalBuffer.put((i * bufferLength) + j, list.get(i).get());
			}
		}
		return finalBuffer;
	}
	
	public static void renderBO(int vbo, int ibo, int drawCount){ renderBO(vbo, ibo, drawCount, false); }
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
	
	public static GLColor randomColor(boolean randomAlpha){
		return new GLColor(
				random.nextFloat(), // R
				random.nextFloat(), // G
				random.nextFloat(), // B
				randomAlpha ? random.nextFloat() : 1);
	}
	public static GLColor randomColor(float alpha){
		return new GLColor(
				random.nextFloat(), // R
				random.nextFloat(), // G
				random.nextFloat(), // B
				alpha);
	}
	
	public static boolean coordsInsideRectangle(int x, int y, TransformRectangle transform){
		return 
				x >= transform.getXPosition() &&
				y >= transform.getYPosition() &&
				x <= transform.getWidth() + transform.getXPosition() &&
				y <= transform.getHeight() + transform.getYPosition();
	}
	
	public static boolean isClamped(float src, float min, float max){ return src <= max && src >= min; }
	public static float clamp(float src, float min, float max){return Math.min(Math.max(src, min), max); }
}
