package core.gl;

import core.math.Matrix4f;
import core.math.Vector3f;

public abstract class Transform {
	
	protected Vector3f translation;
	protected Vector3f rotation;
	protected Vector3f scale;
	protected Matrix4f transformation;
	
	protected abstract void recalculateMatrix();

	public Transform() {
		translation = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		
		recalculateMatrix();
	}
	
	public Transform(Transform t){
		translation = new Vector3f(t.translation);
		rotation = new Vector3f(t.rotation);
		scale = new Vector3f(t.scale);
		
		recalculateMatrix();
	}
	
	/**
	 * Don't use as a 'setter'. Use the setter.
	 * @return the rotation vector
	 */
	public Vector3f getRotation(){return rotation;}
	
	/**
	 * Don't use as a 'setter'. Use the setter.
	 * @return the rotation vector
	 */
	public Vector3f getScale(){return scale;}
	
	/**
	 * Don't use as a 'setter'. Use the setter.
	 * @return the rotation vector
	 */
	public Vector3f getTranslation() {return translation;}
	
	/**
	 * Don't modify.
	 * @return the rotation vector
	 */
	public Matrix4f getTransformation(){return transformation;}
}
