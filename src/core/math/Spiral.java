package core.math;

import core.gl.Transform;

public class Spiral {
	private float height;
	private float angle;
	private float radius;
	
	public Transform transform;

	public Spiral(float height, float angle, float radius, Transform transform) {
		this.height = height;
		this.angle = angle;
		this.radius = radius;
		this.transform = transform;
	}
	
	public SpiralPoint getPointAt(float at){
		double angle = Math.toRadians(this.angle * at);
		float radiusAt = radius * at;
		
		return new SpiralPoint(
			new Vector3f(
				(float) Math.cos(angle) * radiusAt,
				at * height,
				(float) Math.sin(angle) * radiusAt
			),
			transform.getTranslation()
		);
	}
	
	public class SpiralPoint{
		Vector3f position;
		Vector3f normal;
		
		public SpiralPoint(Vector3f position, Vector3f radiusVector) {
			this.position = position;
			this.normal = position.cross(radiusVector);
		}
	}
	
}
