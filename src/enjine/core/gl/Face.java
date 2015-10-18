package enjine.core.gl;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import enjine.core.utils.Common;

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
	public static IntBuffer meshify(List<Face> faces){
		
	    List<IntBuffer> buffers = new ArrayList<IntBuffer>();
		
		for(Face f : faces){
			buffers.add(f.vertexIndices);
		}
		
		return Common.combineBuffers(buffers, 3);
	}
	
	public int[] getData(){
        return vertexIndices.array();
    }
}
