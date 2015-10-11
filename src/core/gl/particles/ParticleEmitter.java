package core.gl.particles;

import core.gl.MeshTransform;
import core.gl.Transformable;

public abstract class ParticleEmitter implements Transformable{
	protected ParticleTransform transform;
	protected ParticleHolder holder;
	
	public ParticleEmitter(ParticleHolder holder){
		this(holder, (ParticleTransform) holder.transform);
	}
	
	public ParticleEmitter(ParticleHolder holder, ParticleTransform transform){
		this.holder = holder;
		this.transform = new ParticleTransform(transform);
	}
	
	public ParticleHolder getHolder(){
		return holder;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((holder == null) ? 0 : holder.hashCode());
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
		ParticleEmitter other = (ParticleEmitter) obj;
		if (holder == null) {
			if (other.holder != null)
				return false;
		} else if (!holder.equals(other.holder))
			return false;
		if (transform == null) {
			if (other.transform != null)
				return false;
		} else if (!transform.equals(other.transform))
			return false;
		return true;
	}

	public abstract void emit();

	@Override public MeshTransform getTransform(){ return transform; }
	@Override public void transformationOccured(){}
	
}
