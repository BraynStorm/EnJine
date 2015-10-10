package core.gl;

import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import core.utils.Common;

public class Face {
	
	public IntBuffer vertexIndices;
	
	public Face(IntBuffer vertexIndices) {
		this.vertexIndices = vertexIndices;
	}
	
	public Face(int[] indices) {
		vertexIndices = BufferUtils.createIntBuffer(3);
		vertexIndices.put(indices);
		vertexIndices.flip();
	}
	
	/** Create buffers for vertexIndices from a list of faces.
	 * @param faces The faces that describe the mesh/model
	 * @return IntBuffer vertexIndices;
	 */
	public static IntBuffer meshify(ArrayList<Face> faces){
		
		ArrayList<IntBuffer> buffers = new ArrayList<IntBuffer>();
		
		for(Face f : faces){
			buffers.add(f.vertexIndices);
		}
		
		return Common.combineBuffers(buffers, 3);
	}
}
