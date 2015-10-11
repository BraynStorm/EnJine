package core.gl.particles;

import java.util.HashSet;

public abstract class ParticleSystem {
	protected HashSet<ParticleEmitter> emitters;
	protected ParticleHolder holder;
	
	public ParticleSystem(int cap, ParticleHolder holder) {
		this.holder = holder;
		emitters = new HashSet<ParticleEmitter>(cap + 1);
	}
	
	public abstract void render();
}
