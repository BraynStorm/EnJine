package enjine.core.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enjine.core.gl.Face;
import enjine.core.gl.FullBlownModel;
import enjine.core.gl.Mesh;
import enjine.core.gl.Texture;
import enjine.core.math.Vector2f;
import enjine.core.math.Vector3f;
import enjine.core.math.Vertex;
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
	 * Loades a new .obj file and converts it to a .mesh (json) file. 
	 * @param objPath The path to the .obj file.
	 */
	private static void convertObj(String objPath){
		objPath = Common.makeAbsoluteDataPath(objPath);
		
		List<String> listLines = null;
		
		try {
			listLines = Common.readAllLines(objPath);
		} catch (IOException e) {
			Common.killProgram(e);
		}
		
		List<PreMesh> preMeshes = createPreMeshList(listLines);
		
		
	}
	
	private static List<PreMesh> createPreMeshList(List<String> data){
		if(data == null)
			Common.killProgram("OBJ file is empty.");
		
		HashMap<String, PreMesh> meshes = new HashMap<String, PreMesh>();
		
		PreMesh currentMesh = null;
		String currentMeshName = "";
		
		data.forEach(line -> {
			String[] parts = line.split("  *");
			switch(parts[0]){
				case "#":
					break;
				case "o":
					if(currentMesh != null)
						meshes.put(currentMeshName, currentMesh);
					
					currentMesh = new PreMesh(); // FIXME: WTF? Why not?
					currentMeshName = parts[1];
					break;
				case "mtllib":
					break;
				case "v":
					currentMesh.positions.add(
						new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
						)
					);
					break;
				case "vt":
					currentMesh.texCoords.add(
						new Vector2f(
							Float.parseFloat(parts[1]),
							1-Float.parseFloat(parts[2])
						)
					);
					break;
				case "vn":
					currentMesh.normals.add(
						new Vector3f(
							Float.parseFloat(parts[1]),
							Float.parseFloat(parts[2]),
							Float.parseFloat(parts[3])
						)
					);
					break;
			}
		});
		
	}
	
	private static void loadMaterial(String path){
		
	}
	
	/**
	 * Loades a new .model file.
	 * @param objPath The path to the .obj
	 * @param mtlPath The path to the .mtl
	 */
	private static void loadModel(String modelPath){
		modelPath = Common.makeAbsoluteDataPath(modelPath);
		
		
	}
	
	class PreMesh{
		ArrayList<Vector3f> positions;
		ArrayList<Vector2f> texCoords;
		ArrayList<Vector3f> normals;
		
		ArrayList<Face> faces;
		ArrayList<Vertex> vertices;
		
		public PreMesh() {
			this.positions = new ArrayList<Vector3f>();
			this.texCoords = new ArrayList<Vector2f>();
			this.normals = new ArrayList<Vector3f>();
			this.faces = new ArrayList<Face>();
			this.vertices =  new ArrayList<Vertex>();
		}
		
	}
}
