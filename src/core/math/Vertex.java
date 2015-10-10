package core.math;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class Vertex {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result
				+ ((texCoord == null) ? 0 : texCoord.hashCode());
		result = prime * result
				+ ((normal == null) ? 0: normal.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		
		/* Position */
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		
		/* TexCoord */
		if (texCoord == null) {
			if (other.texCoord != null)
				return false;
		} else if (!texCoord.equals(other.texCoord))
			return false;
		
		/* Normal */
		if (normal == null) {
			if (other.normal != null)
				return false;
		} else if (!normal.equals(other.normal))
			return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "[P: " + position.toString() + " | T: " + texCoord.toString() + " | N: " + normal.toString() + "]";
	}
	
	public void appendToBuilder(StringBuilder b){
		b.append('[');
		position.appendToBuilder(b);
		b.append(',');
		texCoord.appendToBuilder(b);
		b.append(',');
		normal.appendToBuilder(b);
		b.append("]\n");
	}

	public static final int SIZE = (3 + 2 + 3) * 4;
	
	private Vector3f position;
	private Vector2f texCoord;
	private Vector3f normal;
	
	public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
		this.position = position;
		this.texCoord = texCoord;
		this.normal = normal;
	}
	
	public Vertex(float x, float y, float z, float s, float t, float nx, float ny, float nz){
		this(new Vector3f(x, y, z), new Vector2f(s, t), new Vector3f(nx, ny, nz));
	}
	
	public Vertex(float[] data){
		if(data.length == 6){ // NoTexCoords
			position = new Vector3f(data[0], data[1], data[2]);
			texCoord = new Vector2f(0, 0);
			normal = new Vector3f(data[3], data[4], data[5]);
		}
		else{
			position = new Vector3f(data[0], data[1], data[2]);
			texCoord = new Vector2f(data[3], data[4]);
			normal = new Vector3f(data[5], data[6], data[7]);
		}
	}
	
	public static FloatBuffer bufferfy(ArrayList<Vertex> vertices){
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * SIZE);
		
		for(Vertex v : vertices){
			buffer.put(v.position.getData());
			buffer.put(v.texCoord.getData());
			buffer.put(v.normal.getData());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
}
