package enjine.core.gl;

import enjine.core.math.Matrix4f;
import enjine.core.math.Vector3f;

public abstract class AbstractTransform {
	
	protected Vector3f translation;
	protected Vector3f rotation;
	protected Vector3f scale;
	protected Matrix4f transformation;
	
	protected boolean isMatrixDirty = true;
	
	protected abstract void recalculateMatrix();

	public AbstractTransform() {
		translation = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
	}
	
	public AbstractTransform(AbstractTransform t){
		translation = new Vector3f(t.translation);
		rotation = new Vector3f(t.rotation);
		scale = new Vector3f(t.scale);
	}
	
	protected void markDirty(){ isMatrixDirty = true; }
	
	/**
	 * Best Eclipse Feature EU!
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rotation == null) ? 0 : rotation.hashCode());
		result = prime * result + ((scale == null) ? 0 : scale.hashCode());
		result = prime * result + ((translation == null) ? 0 : translation.hashCode());
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
		
		AbstractTransform other = (AbstractTransform) obj;
		if (rotation == null) {
			if (other.rotation != null)
				return false;
		} else if (!rotation.equals(other.rotation))
			return false;
		if (scale == null) {
			if (other.scale != null)
				return false;
		} else if (!scale.equals(other.scale))
			return false;
		if (transformation == null) {
			if (other.transformation != null)
				return false;
		} else if (!transformation.equals(other.transformation))
			return false;
		if (translation == null) {
			if (other.translation != null)
				return false;
		} else if (!translation.equals(other.translation))
			return false;
		return true;
	}

	/**
	 * Don't use as a 'setter'. Use the setter.
	 * @return the rotation vector
	 */
	public Vector3f getRotation(){ return rotation; }
	
	/**
	 * Don't use as a 'setter'. Use the setter.
	 * @return the rotation vector
	 */
	public Vector3f getScale(){ return scale; }
	
	/**
	 * Don't use as a 'setter'. Use the setter.
	 * @return the translation vector
	 */
	public Vector3f getTranslation() { return translation; }
	
	/**
	 * Don't modify.
	 * @return the scale vector
	 */
	public Matrix4f getTransformation(){
	    if(isMatrixDirty){
	        recalculateMatrix();
	        isMatrixDirty = false;
	    }
	    
	    return transformation;
	}
}
