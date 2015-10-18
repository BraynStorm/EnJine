package enjine.core.utils;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import enjine.core.gl.Face;
import enjine.core.gl.GLColor;
import enjine.core.gl.Shader;
import enjine.core.gl.TransformRectangle;
import enjine.core.logging.Logger;
import enjine.core.math.Vector2f;
import enjine.core.math.Vector3f;
import enjine.core.math.Vertex;

public class Common {
	public static final String gameFolder = ((new File(System.getProperty("java.class.path"))).getAbsoluteFile().getParentFile().toString()).split(";")[0];
	/** gameFolder/data/ */
	public static final String dataFolder = gameFolder + "/data/";
	/** gameFolder/logs/ */
	public static final String logsFolder = gameFolder + "/logs/";
	/**Regex that matches the filename and the extension in 2 separate groups (without the .).
	 * group(1) = name; <br>
	 * group(2) = ext; <br>
	 * ex: "i.afe.geraef.aefag" + "png"
	 */
	public static final Pattern patternMatchFilename = Pattern.compile("(?:.*[\\/\\\\])?(.*)\\.(.*)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Use this instead of makeing new {@link Random} objects.
	 */
	public static final Random random = new Random();
	
	/* Picking */
	private static float redProgress = 0;
	private static float greenProgress = 0;
	private static float blueProgress = 0;
	
	
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
	
	/**
	 * Combines a {@link List<String>} to a single String separating every element with a new line (\n).
	 * @param list The {@link List}
	 * @return The combined elements of the List.
	 */
	public static String combineStringList(List<String> list){
		if(list.isEmpty())
			return "";
		
		StringBuilder sb = new StringBuilder(list.size() * 4); // Arbitrary number but its more optimized that way (or so I am told).
		
		list.forEach(e ->{
			sb.append(e).append('\n');
		});
		
		return sb.toString();
	}
	
	/**
	 * Reads all the lines in a single text file.
	 * @param absolutePath Absolute path to the file.
	 * @return A string that contains all lines of the file.
	 * @throws IOException {@link Files#readAllLines(java.nio.file.Path)}
	 */
	public static String readAllLinesToString(String absolutePath) throws IOException{
		return combineStringList(Files.readAllLines(new File(absolutePath).toPath()));
	}
	
	/**
	 * Reads all the lines in a single text file.
	 * @param absolutePath Absolute path to the file.
	 * @return A string that contains all lines of the file.
	 * @throws IOException {@link Files#readAllLines(java.nio.file.Path)}
	 */
	public static List<String> readAllLines(String absolutePath) throws IOException{
		return Files.readAllLines(new File(absolutePath).toPath());
	}
	
	/**
	 * Cycles over every line in a {@link String}.
	 * @param string The string to be cycled over.
	 * @param consumer 
	 */
	public static void forEachLine(String string, Consumer<String> consumer){
		List<String> list = Arrays.asList(string.split("\n"));
		list.forEach(consumer);
	}
	
	/**
	 * Converts a {@link JSONArray} of {@link JSONArray}s to a {@link List} of {@link Vector3f} 
	 * @param arr The 'master' JSONArray
	 * @return The List
	 */
	public static List<Vector3f> jsonArrayToVector3fList(JSONArray arr){
	    List<Vector3f> vectorList = new ArrayList<>(arr.length() + 1);
	    
	    for(int i = 0; i < arr.length(); i++){
	        JSONArray innerArray = arr.getJSONArray(i);
	        vectorList.add(jsonArrayToVector3f(innerArray));
	    }
	    
	    return vectorList;
	}
	/**
	 * Convertes a {@link JSONArray} to a {@link Vector3f}.
	 * @param arr The JSONArray
	 * @return The Vector3f
	 */
	public static Vector3f jsonArrayToVector3f(JSONArray arr){
        if(arr.length() == 3){
            float v1 = (float)arr.getDouble(0);
            float v2 = (float)arr.getDouble(1);
            float v3 = (float)arr.getDouble(2);
            return new Vector3f(v1, v2, v3);
        }else{
            return new Vector3f(0,0,0);
        }
    }
	
	/**
	 * Converts a {@link JSONArray} of {@link JSONArray} to a {@link List} of {@link Vector3f}.
	 * @param arr The 'master' JSONArray.
	 * @return The List.
	 */
	public static List<Vector2f> jsonArrayToVector2fList(JSONArray arr){
	    List<Vector2f> vectorList = new ArrayList<>(arr.length() + 1);
	    
	    for(int i = 0; i < arr.length(); i++){
	        JSONArray innerArray = arr.getJSONArray(i);
	        vectorList.add(jsonArrayToVector2f(innerArray));
	    }
	    
	    return vectorList;
	}
	/**
	 * Converts a {@link JSONArray} to a {@link Vector2f}.
	 * @param arr The JSONArray
	 * @return The Vector2f
	 */
	public static Vector2f jsonArrayToVector2f(JSONArray arr){
        if(arr.length() == 2){
            float v1 = (float)arr.getDouble(0);
            float v2 = (float)arr.getDouble(1);
            return new Vector2f(v1, v2);
        }else{
            return new Vector2f(0,0);
        }
    }
	
	public static List<Face> jsonArrayToFaceList(JSONArray arr){
        List<Face> faceList = new ArrayList<>(arr.length() + 1);
        
        for(int i = 0; i < arr.length(); i++){
            JSONArray innerArray = arr.getJSONArray(i);
            faceList.add(jsonArrayToFace(innerArray));
        }
        
        return faceList;
    }
	public static Face jsonArrayToFace(JSONArray arr){
        if(arr.length() == 3){
            int i1 = arr.getInt(0);
            int i2 = arr.getInt(1);
            int i3 = arr.getInt(2);
            return new Face(new int[] {i1, i2, i3});
        }else{
            Common.killProgram("JSONARRAY of faces has length different than 3.");
            return null;
        }
    }

	
	/** {@link Common#killProgram(String)} with no msg part. */
	public static void killProgram(){ killProgram(""); }
	/** {@link Common#killProgram(Exception)} with a String rather than Exception. */
	public static void killProgram(String msg){ killProgram(new Exception(msg)); }
	/**
	 * Kills the game/program.
	 * @param e The {@link Exception} that was thrown in order to kill the program.
	 */
	public static void killProgram(Exception e){
		Logger.getInstance().log(e);
		System.exit(1);
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
	public static String makeAbsoluteDataPath(String relativePath){ return dataFolder + relativePath; }
	
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
