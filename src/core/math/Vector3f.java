package core.math;

import java.io.DataInputStream;
import java.io.IOException;

public class Vector3f {

	public static final Vector3f ZERO_ZERO_ZERO = new Vector3f(0,0,0);
	public static final Vector3f ONE_ZERO_ZERO = new Vector3f(1,0,0);
	public float x,y,z;
	
	public Vector3f(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x).append(", ").append(y).append(", ").append(z);
		return sb.toString();
	}
	
	public float[] getData(){
		return new float[] { x, y, z };
	}
	
	/**
	 * <b>Copy</b> constructor.
	 */
	public Vector3f(Vector3f vec){
		x = vec.x;
		y = vec.y;
		z = vec.z;
	}
	
	/* MATH */

	public Vector3f getRadianized() {
		Vector3f v = new Vector3f(0,0,0);
		v.x = (float)Math.toRadians(x);
		v.y = (float)Math.toRadians(y);
		v.z = (float)Math.toRadians(z);
		return v;
	}
	
	public float length(){
		return (float)Math.sqrt(x * x + y * y + z * z);
	}
	
	public float dot(Vector3f v){
		return x * v.x + y * v.y + z * v.z;
	}
	
	public Vector3f getNormalized(){
		Vector3f v = new Vector3f(x, y, z);
		return v.normalize();
	}
	
	public Vector3f rotateAroundAxis(float angle, Vector3f axis){
		Vector3f n = axis.getNormalized();
		Vector3f v = new Vector3f(this);
		angle = (float) Math.toRadians(angle);
		
		v.mul( (float) Math.cos(angle));
		v.add( n.mul(this.dot(n)).mul(1-(float) Math.cos(angle)) );
		v.add(n.cross(this).mul((float)Math.sin(angle)));
		
		x = v.x;
		y = v.y;
		z = v.z;		
		return this;
	}
	
	public Vector3f normalize(){
		float len = length();
		
		x /= len;
		y /= len;
		z /= len;
		
		return this;
	}
	
	public Vector3f rotate(float angle, Vector3f axis){
		
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle/2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle/2));
		
		float rX = axis.x * sinHalfAngle;
		float rY = axis.y * sinHalfAngle;
		float rZ = axis.z * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rot = new Quaternion(rX, rY, rZ, rW);
		Quaternion w = rot.mul(this).mul(rot.conjugate());
		
		x = w.x;
		y = w.y;
		z = w.z;
		
		return this;
	}
	
	public Vector3f cross(Vector3f v){
		float x_ = y * v.z - z * v.y;
		float y_ = z * v.x - x * v.z;
		float z_ = x * v.y - y * v.x;
		
		return new Vector3f(x_, y_, z_);
	}
	
	public Vector3f add(Vector3f v){
		return new Vector3f(v.x + x, v.y + y, v.z + z);
	}
	
	public Vector3f add(float v){
		return new Vector3f(v + x, v + y, v + z);
	}
	
	public Vector3f sub(Vector3f v){
		return new Vector3f(v.x - x, v.y - y, v.z - z);
	}
	
	public Vector3f sub(float v){
		return new Vector3f(v - x, v - y, v - z);
	}
	
	public Vector3f mul(Vector3f v){
		return new Vector3f(v.x * x, v.y * y, v.z * z);
	}
	
	public Vector3f mul(float v){
		return new Vector3f(v * x, v * y, v * z);
	}
	
	public Vector3f div(Vector3f v){
		return new Vector3f(v.x / x, v.y / y, v.z / z);
	}
	
	public Vector3f div(float v){
		return new Vector3f(v / x, v / y, v / z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		result = prime * result + Float.floatToIntBits(z);
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
		Vector3f other = (Vector3f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}
	
	public void appendToBuilder(StringBuilder b){
		b.append("[X: ").append(x).append(", Y: ").append(y).append(", Z: ").append(z).append(']');
	}
	
	public Vector3f invert(){
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public Vector3f getInverted() {
		return new Vector3f(-x, -y, -z);
	}

	public static float getDistance(Vector3f head, Vector3f vertexPosition) {
		return (float) Math.sqrt
		(
			Math.pow(head.x - vertexPosition.x, 2) +
			Math.pow(head.y - vertexPosition.y, 2) +
			Math.pow(head.z - vertexPosition.z, 2)
		);
	}
	
	public static float getDistanceSquared(Vector3f head, Vector3f vertexPosition) {
		return (float)
		(
			Math.pow(head.x - vertexPosition.x, 2) +
			Math.pow(head.y - vertexPosition.y, 2) +
			Math.pow(head.z - vertexPosition.z, 2)
		);
	}

	public void readFrom(DataInputStream stream) throws IOException {
		x = stream.readFloat();
		y = stream.readFloat();
		z = stream.readFloat();
	}

}

