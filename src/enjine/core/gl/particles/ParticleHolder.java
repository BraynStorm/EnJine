package enjine.core.gl.particles;

import java.util.HashSet;

import enjine.core.gl.MeshTransform;
import enjine.core.gl.Shader;
import enjine.core.gl.Transformable;

public abstract class ParticleHolder implements Transformable {
	protected int particleCap;
	protected MeshTransform transform;
	protected HashSet<Particle> particleSet;
	
	
	/**
	 * Default {@link ParticleHolder#particleCap} is 10 000.
	 */
	public ParticleHolder(ParticleTransform transform){
		this(10000, transform);
	}
	
	
	public ParticleHolder(int particleCap, ParticleTransform transform) {
		this.particleCap = particleCap;
		this.transform = new ParticleTransform(transform);
		
		particleSet = new HashSet<Particle>(particleCap);
	}
	
	public void renderParticles(){
		particleSet.removeIf(p -> shouldDespawnParticle(p));
		
		Shader.currentlyBound.setUniform("particleOriginTransform", transform.getTransformation());
		
		particleSet.forEach(p -> {
			p.render();
		});
	}
	
	public abstract boolean shouldDespawnParticle(Particle p);
	
	/**
	 * Adds a particle to the {@link ParticleHolder}. Each render pass for the holder will call {@link Particle#render).
	 * @param particle The particle to be added to the holder.
	 */
	public void addParticle(Particle particle){
		if(particleSet.size() >= particleCap)
			return;
		
		particleSet.add(particle);
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + particleCap;
		result = prime * result + ((particleSet == null) ? 0 : particleSet.hashCode());
		result = prime * result + ((transform == null) ? 0 : transform.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParticleHolder other = (ParticleHolder) obj;
		if (particleCap != other.particleCap)
			return false;
		if (particleSet == null) {
			if (other.particleSet != null)
				return false;
		} else if (!particleSet.equals(other.particleSet))
			return false;
		if (transform == null) {
			if (other.transform != null)
				return false;
		} else if (!transform.equals(other.transform))
			return false;
		return true;
	}


	@Override public MeshTransform getTransform() {return transform;}
	@Override public void transformationOccured() {}
}
