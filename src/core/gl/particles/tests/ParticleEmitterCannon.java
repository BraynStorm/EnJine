package core.gl.particles.tests;

import core.gl.Texture;
import core.gl.particles.GravityParticle;
import core.gl.particles.ParticleEmitter;
import core.gl.particles.ParticleHolder;
import core.gl.particles.ParticleTransform;
import core.math.Vector3f;
import core.resources.ResourceManager;

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
