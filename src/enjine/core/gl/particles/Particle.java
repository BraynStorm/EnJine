package enjine.core.gl.particles;

import enjine.core.gl.MeshTransform;
import enjine.core.gl.Origin;
import enjine.core.gl.Rectangle;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.gl.Transformable;
import enjine.core.utils.Common;

public class Particle implements Transformable {
	protected Texture texture;
	protected ParticleTransform transform;
	
	public Particle(Texture texture){
		this.texture = texture;
	}
	
	public Particle(Texture texture, ParticleTransform transform){
		this.texture = texture;
		this.transform = new ParticleTransform(transform);
	}
	
	public void render(){
		Texture.bind(texture);
		Shader.currentlyBound.setUniform("particleTransform", transform.getTransformation());
		Common.renderBO(Rectangle.getVBO(Origin.CENTER), Rectangle.getIBO(), Rectangle.getDrawCount());
	}
	
	@Override
	public MeshTransform getTransform() {
		return transform;
	}
	
	@Override
	public void transformationOccured() {}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((texture == null) ? 0 : texture.hashCode());
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
		Particle other = (Particle) obj;
		if (texture == null) {
			if (other.texture != null)
				return false;
		} else if (!texture.equals(other.texture))
			return false;
		if (transform == null) {
			if (other.transform != null)
				return false;
		} else if (!transform.equals(other.transform))
			return false;
		return true;
	}
	
	
}
