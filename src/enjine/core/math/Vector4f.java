package enjine.core.math;

public class Vector4f {
	public float x,y,z,w;

	public Vector4f(){
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector4f copy){
		x = copy.x;
		y = copy.y;
		z = copy.z;
		w = copy.w;
	}
	
}
