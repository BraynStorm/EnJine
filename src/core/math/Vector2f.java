package core.math;

public class Vector2f {

	public static final Vector2f ZERO_ZERO = new Vector2f(0,0);
	public static final Vector2f HALF_HALF = new Vector2f(.5f,.5f);
	public float x,y;
	
	public Vector2f(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(double x, double y) {
		this((float)x, (float)y);
	}

	public float[] getData(){
		return new float[]{ x, y };
	}
	
	
	/* MATH */

	public float length(){
		return (float)Math.sqrt(x * x + y * y);
	}
	
	public float dot(Vector2f v){
		return x * v.x + y * v.y;
	}
	
	public Vector2f getNormalized(){
		Vector2f v = new Vector2f(x, y);
		return v.normalize();
	}
	
	public Vector2f normalize(){
		float len = length();
		
		x /= len;
		y /= len;
		
		return this;
	}
	
	public Vector2f rotate(float angle){
		double rad = Math.toRadians(angle);
		double sin = Math.sin(rad);
		double cos = Math.cos(rad);
		
		return new Vector2f(x * cos - y * sin, x * sin + y * cos);
	}
	
	public Vector2f add(Vector2f v){
		return new Vector2f(v.x + x, v.y + y);
	}
	
	public Vector2f add(float v){
		return new Vector2f(v + x, v + y);
	}
	
	public Vector2f sub(Vector2f v){
		return new Vector2f(v.x - x, v.y - y);
	}
	
	public Vector2f sub(float v){
		return new Vector2f(v - x, v - y);
	}
	
	public Vector2f mul(Vector2f v){
		return new Vector2f(v.x * x, v.y * y);
	}
	
	public Vector2f mul(float v){
		return new Vector2f(v * x, v * y);
	}
	
	public Vector2f div(Vector2f v){
		return new Vector2f(v.x / x, v.y / y);
	}
	
	public Vector2f div(float v){
		return new Vector2f(v / x, v / y);
	}
	
	/* END MATH */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
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
		Vector2f other = (Vector2f) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "U: " + x + ", V: " + y;
	}
	
	public void appendToBuilder(StringBuilder b){
		b.append("[U: ").append(x).append(", V: ").append(y).append(']');
	}
	
	public Vector2f clone(){
		return new Vector2f(x,y);
	}
}
