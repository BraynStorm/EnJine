package core.resources;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import core.gl.Face;
import core.gl.Material;
import core.gl.Mesh;
import core.math.Vertex;
import core.utils.Common;


public class MeshLoader {
	static Mesh loadMesh(int id){
		String path = Common.dataFolder + "meshes/" + String.valueOf(id);
		
		try{
			DataInputStream stream = new DataInputStream(new FileInputStream(path));
			
			// If its BSMESH, preceed reading.
			if(!stream.readUTF().equals("BSMESH")){
				stream.close();
				new Exception().printStackTrace();
				return null;
			}
			
			
			int numVertices = stream.readInt();
			int numFaces= stream.readInt();
			
			boolean hasTexCoords = stream.readBoolean();
			
			int vSize = 3 + (hasTexCoords ? 2 : 0) + 3;
			
			
			PreMesh preMesh = new PreMesh(numVertices, numFaces);
			Material material = new Material();
			
			// Vertices
			for(int i = 0; i < numVertices; i++){
				float[] data = new float[vSize];
			
				for(int j = 0; j < vSize; j++)
					data[j] = stream.readFloat();
				
				preMesh.vertices.add(new Vertex(data));
			}
				
			// Faces
			for(int i = 0; i < numFaces; i++){
				int[] data = new int[3];
				
				for(int j = 0; j < 3; j++)
					data[j] = stream.readInt();
				
				preMesh.faces.add(new Face(data));
			}
			
			material.kd.readFrom(stream);
			material.ka.readFrom(stream);
			material.ks.readFrom(stream);
			material.ke.readFrom(stream);
			material.d = stream.readFloat();
			material.ns = stream.readFloat();
			material.illum = stream.readInt();
			
			material.map_Kd = ResourceManager.getInstance().getTexture(stream.readInt());
			material.map_Ka = stream.readInt();
			material.map_Ks = stream.readInt();
			material.map_Ke = stream.readInt();
			material.disp = stream.readInt();
			
			stream.close();
			
			Mesh t = new Mesh(preMesh.vertices, preMesh.faces, material);
			return t;
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	static class PreMesh{
		public ArrayList<Vertex>vertices;
		public ArrayList<Face>faces;
		
		public PreMesh(int numV, int numF) {
			vertices = new ArrayList<Vertex>(numV);
			faces = new ArrayList<Face>(numF);
		}
	}
}
