package enjine.core.gl.particles.tests;

import enjine.core.gl.Texture;
import enjine.core.gl.particles.GravityParticle;
import enjine.core.gl.particles.ParticleEmitter;
import enjine.core.gl.particles.ParticleHolder;
import enjine.core.gl.particles.ParticleTransform;
import enjine.core.math.Vector3f;
import enjine.core.resources.ResourceManager;

public class ParticleEmitterCannon extends ParticleEmitter{

	protected Vector3f direction;
	protected Texture particleTexture;
	
	public ParticleEmitterCannon(ParticleHolder holder, ParticleTransform transform, Vector3f velocity) {
		super(holder, transform);
		this.direction = velocity;
		particleTexture = ResourceManager.getInstance().getTexture(6);
	}
	
	@Override
	public void emit() {
		GravityParticle p = new GravityParticle(particleTexture, transform);
		p.setVelocity(direction);
		holder.addParticle(p);
	}

}
