package core.gl.particles;

import core.gl.MeshTransform;
import core.math.Vector3f;

public class ParticleTransform extends MeshTransform {
	
	public ParticleTransform() {
		super();
	}
	
	public ParticleTransform(ParticleTransform p){
		super(p);
	}
	
	public ParticleTransform translateBy(Vector3f t){ setTranslation(translation.add(t)); return this; }
}
