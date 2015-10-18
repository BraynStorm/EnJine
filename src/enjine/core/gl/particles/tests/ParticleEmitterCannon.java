package enjine.core.gl.particles.tests;

import enjine.core.gl.particles.AnimatedParticle;
import enjine.core.gl.particles.ParticleEmitter;
import enjine.core.gl.particles.ParticleHolder;
import enjine.core.gl.particles.ParticleTransform;
import enjine.core.math.Vector3f;

public class ParticleEmitterCannon extends ParticleEmitter{

	protected Vector3f direction;
	protected AnimatedParticle template;
	
	public ParticleEmitterCannon(ParticleHolder holder, ParticleTransform transform, AnimatedParticle template, Vector3f velocity) {
		super(holder, transform);
		this.direction = velocity;
		this.template = template;
	}
	
	@Override
	public void emit() {
	    AnimatedParticle p = new AnimatedParticle(template);
		p.setVelocity(direction);
		holder.addParticle(p);
	}

}
