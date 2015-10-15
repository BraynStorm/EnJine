package enjine.core.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import enjine.core.gl.FullBlownModel;
import enjine.core.gl.Mesh;
import enjine.core.gl.Texture;
import enjine.core.logging.Logger;
import enjine.core.logging.Logger.LogLevel;
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
	 * Loades a new .obj file and converts it to a .mesh json file. 
	 * @param objPath The path to the .obj file.
	 */
	private static void convertObj(String objPath){
		objPath = Common.makeAbsoluteDataPath(objPath);
		List<String> listLines = null;
		try {
			listLines = Files.readAllLines(new File(objPath).toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(listLines == null)
			String lines = Common.combineStringList(listLines);
		
		if(lines.equals("")){
			Logger.getInstance().log(LogLevel.CRITICAL, "File " + objPath + " isn't a valid OBJ file.");
			new Exception().printStackTrace();
			System.exit(0);
		}
		
	}
	
	/**
	 * Loades a new .model file.
	 * @param objPath The path to the .obj
	 * @param mtlPath The path to the .mtl
	 */
	private static void loadModel(String modelPath){
		modelPath = Common.makeAbsoluteDataPath(modelPath);
		
		
	}
}
