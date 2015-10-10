package core.gl.particles;

import core.math.Vector3f;

public class ParticlePoint {
	Vector3f forward;
	Vector3f position;
	public ParticlePoint(Vector3f forward, Vector3f position) {
		this.forward = forward;
		this.position = position;
	}
	
	@Override
	public String toString() {
		return position.toString();
	}
}
