package enjine.core.gl;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL15;

import enjine.core.math.Vertex;
import enjine.core.utils.Common;

public class Mesh {
	private GLColor pickingColor;
	
	public int ibo;
	public int vbo;
	public int drawCount;
	public Material material;
	
	public Mesh(int vbo, int ibo, int drawCount, Material material) {
		this.ibo = ibo;
		this.vbo = vbo;
		this.drawCount = drawCount;
		this.material = material;
		
		pickingColor = Common.aqquirePickingColor();
	}

	public void render(MeshTransform transform) {
		render(transform, false);
	}
	
	public void render(MeshTransform transform, boolean pickingMode){
		
		Shader.currentlyBound.setUniform("transform", transform.getTransformation());
		
		if(pickingMode)
			Shader.currentlyBound.setUniform("color", pickingColor);
		else{
			material.bind();
		}
		
		Common.renderBO(vbo, ibo, drawCount, true);
	}
	
	public Mesh(ArrayList<Vertex> vertices, ArrayList<Face> faces, Material material){
		this.material = material;
		
		IntBuffer indexData = Face.meshify(faces);
		
		this.drawCount = indexData.capacity();
		this.vbo = Common.bufferData(Vertex.bufferfy(vertices), GL15.GL_ARRAY_BUFFER);
		this.ibo = Common.bufferData(indexData, GL15.GL_ELEMENT_ARRAY_BUFFER);
		
		this.pickingColor = Common.aqquirePickingColor();
	}
	
	//public WeightedVertex[] weights;
	
	public void delete(){
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(ibo);
	}
	
}
