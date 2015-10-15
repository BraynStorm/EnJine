package enjine.core.resources;

import java.util.HashMap;

import enjine.core.gl.FullBlownModel;
import enjine.core.gl.Mesh;
import enjine.core.gl.Texture;
import enjine.core.utils.Common;

/**
 * Note: Should change to {@link Hashtable} when we get to threading.
 * @author BraynStorm
 *
 */
public class RealResourceManager {
	private static HashMap<String, Texture> textureMap;
	private static HashMap<String, Mesh> meshMap;
	private static HashMap<String, FullBlownModel> modelMap;
	
	private static void loadModel(String path){
		path = Common.dataFolder + path;
	}
}
