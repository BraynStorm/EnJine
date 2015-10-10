package core.gl.particles;

import core.math.Vector3f;
import core.utils.Common;

public class ParticleShapeSprinkler extends ParticleShape{
	
	float radius;
	float maxHeight;
	float startHeight;
	
	
	public ParticleShapeSprinkler(float radius, float maxHeight, float startHeight) {
		this.radius = radius;
		this.maxHeight = maxHeight;
		this.startHeight = startHeight;
	}


	@Override
	public ParticlePoint getPointAt(float at) {
		return getPointAt(at, (float)Common.random.nextDouble()*360);
	}
	
	public ParticlePoint getPointAt(float at, float angle){
		angle = (float)Math.toRadians(2*360*at);
		float posX  = (float)Math.cos(angle) * radius;
		float posZ  = (float)Math.sin(angle) * radius;
		
		Vector3f p = new Vector3f(posX, startHeight + (maxHeight * at), posZ);
		Vector3f f = new Vector3f(0, 0, 0); //?? NYI
		
		
		return new ParticlePoint(f, p);
	}
	
}
