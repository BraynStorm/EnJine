package enjine.core.gl;

import enjine.core.math.Vector3f;

public interface Transformable{
	MeshTransform getTransform();
	
	/**
	 * Only needs to be implemented for {@link Transform} extenders.
	 */
	void transformationOccured();
	
	default public Transformable setTranslationX(float x){ getTransform().setTranslationX(x); transformationOccured(); return this; }
	default public Transformable setTranslationY(float y){ getTransform().setTranslationY(y); transformationOccured(); return this; }
	default public Transformable setTranslationZ(float z){ getTransform().setTranslationZ(z); transformationOccured(); return this; }
	default public Transformable setTranslation(Vector3f t){ getTransform().setTranslation(t); transformationOccured(); return this; }
	
	default public Transformable setRotationX(float x){ getTransform().setRotationX(x); transformationOccured(); return this; }
	default public Transformable setRotationZ(float y){ getTransform().setRotationY(y); transformationOccured(); return this; }
	default public Transformable setRotationY(float z){ getTransform().setRotationZ(z); transformationOccured(); return this; }
	default public Transformable setRotation(Vector3f r){ getTransform().setRotation(r); transformationOccured(); return this; }
	
	default public Transformable setScaleX(float x){ getTransform().setScaleX(x); transformationOccured(); return this; }
	default public Transformable setScaleY(float y){ getTransform().setScaleY(y); transformationOccured(); return this; }
	default public Transformable setScaleZ(float z){ getTransform().setScaleZ(z); transformationOccured(); return this; }
	default public Transformable setScale(Vector3f s){ getTransform().setScale(s); transformationOccured(); return this; }
	default public Transformable setScale(float s) { getTransform().setScale(s); transformationOccured(); return this; }
	
	
}
