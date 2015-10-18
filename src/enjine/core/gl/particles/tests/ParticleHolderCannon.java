package enjine.core.gl.particles.tests;

import enjine.core.gl.particles.AnimatedParticle;
import enjine.core.gl.particles.Particle;
import enjine.core.gl.particles.ParticleHolder;
import enjine.core.gl.particles.ParticleTransform;

public class ParticleHolderCannon extends ParticleHolder{

	public ParticleHolderCannon(ParticleTransform transform) {
		super(5, transform);
	}
	
	@Override
	public boolean shouldDespawnParticle(Particle p) {
	    AnimatedParticle pp = (AnimatedParticle) p;
		return Math.abs(pp.getTransform().getTranslation().y) >= 0.2f || pp.hasFinishedAnimation();
	}

}
