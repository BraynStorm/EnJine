package core.math;

public class Quaternion {
	public float x, y, z, w;

	public Quaternion(float x, float y, float z, float w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public float length(){
		return (float)Math.sqrt(x*x + y*y + z*z + w*w);
	}
	
	public Quaternion normalize(){
		float len = length();
		
		x /= len;
		y /= len;
		z /= len;
		w /= len;
		
		return this;
	}
	
	public Quaternion conjugate(){
		return new Quaternion(-x, -y, -z, w);
	}
	
	public Quaternion mul(Quaternion v){
		float w_ = w * v.w   -   x * v.x   -   y * v.y   -   z * v.z;
		float x_ = x * v.w   +   w * v.x   +   y * v.z   -   z * v.y;
		float y_ = y * v.w   +   w * v.y   +   z * v.x   -   x * v.z;
		float z_ = z * v.w   +   w * v.y   +   x * v.y   -   y * v.x;
		
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
	public Quaternion mul(Vector3f v){
		float w_ = -x * v.x - y * v.y - z * v.z;
		float x_ =  w * v.x + y * v.z - z * v.y;
		float y_ =  w * v.y + z * v.x - x * v.z;
		float z_ =  w * v.z + x * v.y - y * v.x;
		
		return new Quaternion(x_, y_, z_, w_);
	}
	
}
