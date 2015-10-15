package enjine.core.resources;

import java.util.HashMap;

import enjine.core.gl.FullBlownModel;
import enjine.core.gl.Mesh;
import enjine.core.gl.Texture;
import enjine.core.utils.Common;

/**
 * TODO: Should change to {@link Hashtable} when we get to threading.
 * @author BraynStorm
 *
 */
public class RealResourceManager {
	
	/** Contains all textures that Enjine uses. */
	private static HashMap<String, Texture> textureMap;
	
	/** Contains all meshes that Enjine uses. */
	private static HashMap<String, Mesh> meshMap;
	
	/** Contains all models that Enjine uses. */
	private static HashMap<String, FullBlownModel> modelMap;
	
	/**
	 * Loades a new .mesh file.
	 * @param path The path to the model file counted from {@link Common#dataFolder} inwards (ex: meshes/blah.model will represent the file JARLOCATION/data/meshes/blah.model).
	 */
	private static void loadMesh(String path){
		path = Common.makeAbsoluteDataPath(path);
	}
	
	/**
	 * Loades a new .model file.
	 * @param path The path to the model file counted from {@link Common#dataFolder} inwards (ex: meshes/blah.model will represent the file JARLOCATION/data/meshes/blah.model).
	 */
	private static void loadModel(String path){
		path = Common.makeAbsoluteDataPath(path);
	}
}
