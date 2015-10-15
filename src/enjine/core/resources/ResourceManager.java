package enjine.core.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import enjine.core.gl.Mesh;
import enjine.core.gl.Texture;
import enjine.core.logging.Logger;
import enjine.core.logging.Logger.LogLevel;
import enjine.core.utils.Common;
@Deprecated
public class ResourceManager {
	
	private HashMap<String, Texture> mapGuiTextures;
	private HashMap<Integer, Texture> mapTextures;
	private HashMap<Integer, Mesh> mapMeshes;
	
	private static String texturesPath = Common.dataFolder + "textures/";
	
	/**
	 * Mainly for GUI textures. 
	 * @param name Name of the texture
	 * @return
	 */
	public Texture getTexture(String name){
		Texture t = mapGuiTextures.get(name);
		if(t == null)
			try {
				t = TextureLoader.loadTexture(new FileInputStream(texturesPath + name + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return t;
	}
	public Texture getTexture(int id){ return getTexture(String.valueOf(id)); }
	public Texture getGuiTexture(String name){ return getTexture("gui/" + name); }
	
	public Mesh getMesh(int id){
		Mesh m = mapMeshes.get(id);
		if(m == null)
			mapMeshes.put(id, MeshLoader.loadMesh(id));
		return mapMeshes.get(id);
	}
	
	public void deleteMesh(int id){
		mapMeshes.remove(id);
	}
	
	
	public void unloadTexture(int id){
		Texture t = mapTextures.get(id);
		
		if(t != null){
			GL11.glDeleteTextures(t.textureID);
			mapTextures.remove(id);
		}
		else
			Logger.getInstance().log(LogLevel.WARNING, "Trying to delete unloaded textures. Skipping");
	}
	
	private ResourceManager() {
		/**
		 * Create necessary folders.
		 */
		String gameFolder = Common.dataFolder;
		
		File f = (new File(gameFolder + "meshes"));
		f.mkdirs();
		f = (new File(gameFolder + "textures"));
		f.mkdirs();
		f = (new File(gameFolder + "fonts"));
		f.mkdirs();
		f = (new File(gameFolder + "meshes"));
		f.mkdirs();
		
		f = (new File(Common.logsFolder));
		f.mkdirs();
		
		
		mapTextures = new HashMap<Integer, Texture>();
		mapGuiTextures = new HashMap<String, Texture>();
		mapMeshes = new HashMap<Integer, Mesh>();
	}

	// Singleton
	private static ResourceManager instance;

	public static ResourceManager getInstance() {
		if (instance == null)
			instance = new ResourceManager();
		return instance;
	}
}
