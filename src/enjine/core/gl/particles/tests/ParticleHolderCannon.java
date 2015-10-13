package enjine.core.gl.particles.tests;

import enjine.core.gl.particles.Particle;
import enjine.core.gl.particles.ParticleHolder;
import enjine.core.gl.particles.ParticleTransform;

public class ParticleHolderCannon extends ParticleHolder{

	public ParticleHolderCannon(ParticleTransform transform) {
		super(20, transform);
	}
	
	@Override
	public boolean shouldDespawnParticle(Particle p) {
		return p.getTransform().getTranslation().y < -2;
	}

}
