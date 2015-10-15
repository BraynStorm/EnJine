package enjine.core.resources.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import enjine.core.gl.Face;
import enjine.core.math.Vector2f;
import enjine.core.math.Vector3f;
import enjine.core.math.Vertex;

public class OBJ {
	
	private static ArrayList<Vector3f> positions;
	private static ArrayList<Vector2f> texCoords;
	private static ArrayList<Vector3f> normals;
	
	private static ArrayList<Face> faces;
	private static ArrayList<Vertex> vertices;
	
	private static boolean hasTexCoords;
	
	public static void loadOBJ(String path) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(path));
		
		hasTexCoords = false;
		
		positions = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		
		vertices = new ArrayList<Vertex>();
		faces = new ArrayList<Face>();
		
		
		while(r.ready()){
				String line = r.readLine();
				String[] parts = line.split("  *");
				switch(parts[0]){
					case "#":
						continue;
					case "o":
						continue;
					case "mtllib":
						continue;
					case "v":
						positions.add(
							new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
							)
						);
						break;
					case "vt":
						texCoords.add(
							new Vector2f(
								Float.parseFloat(parts[1]),
								1-Float.parseFloat(parts[2])
							)
						);
						hasTexCoords = true;
						break;
					case "vn":
						normals.add(
							new Vector3f(
								Float.parseFloat(parts[1]),
								Float.parseFloat(parts[2]),
								Float.parseFloat(parts[3])
							)
						);
						break;
					case "f":
						int[] indices = new int[3];
						int I = 0;
						for(int i = 1; i < parts.length; i++){
							String[] p = parts[i].split("/");
							
							int pos = Integer.parseInt(p[0]) - 1;
							int tex = -1;
							int norm = Integer.parseInt(p[1]) - 1;
							
							//Texture
							if(hasTexCoords){
								tex = Integer.parseInt(p[1]) - 1;
								norm = Integer.parseInt(p[2]) - 1;
							}
							
							Vector3f posVector = positions.get(pos);
							Vector2f texVector = hasTexCoords ? texCoords.get(tex) : null;
							Vector3f normVector = normals.get(norm);
							
							Vertex potentialVertex = new Vertex(posVector, texVector, normVector);
							
							boolean found = false;
							
							for(int j = 0; j < vertices.size(); j++){
								if(vertices.get(j).equals(potentialVertex)){
									indices[I] = j;
									I++;
									found = true;
									break;
								}
							}
							
							if(!found){
								indices[I] = vertices.size();
								I++;
								vertices.add(potentialVertex);
							}
						}
						
						faces.add(new Face(indices));
						
						break;
				}
				
			}
		r.close();
	}
		//m = MaterialLoader.loadMaterial(path.replace(".obj", ".mtl"));
		
		
	
}
