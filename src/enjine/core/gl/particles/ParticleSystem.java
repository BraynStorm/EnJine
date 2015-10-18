package enjine.core.gl.particles;

import java.util.HashSet;

public abstract class ParticleSystem {
	protected HashSet<ParticleEmitter> emitters;
	protected ParticleHolder holder;
	
	public ParticleSystem(ParticleHolder holder) {
		this.holder = holder;
		emitters = new HashSet<ParticleEmitter>();
	}
	
	public abstract void loop();
}
