package enjine.core.gl.particles;

import enjine.core.gl.MeshTransform;
import enjine.core.math.Vector3f;

public class ParticleTransform extends MeshTransform {
	
	public ParticleTransform() {
		super();
	}
	
	public ParticleTransform(ParticleTransform p){
		super(p);
	}
	
	public ParticleTransform translateBy(Vector3f t){ setTranslation(translation.add(t)); return this; }
}
