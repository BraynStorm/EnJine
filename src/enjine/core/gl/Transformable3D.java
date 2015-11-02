package enjine.core.gl;

import braynstorm.commonlib.math.Vector3f;

public interface Transformable3D{
	MeshTransform getTransform();
	
	/**
	 * Only needs to be implemented for {@link Transform} extenders.
	 */
	void transformationOccured();
	
	default public Transformable3D setTranslationX(float x){ getTransform().setTranslationX(x); transformationOccured(); return this; }
	default public Transformable3D setTranslationY(float y){ getTransform().setTranslationY(y); transformationOccured(); return this; }
	default public Transformable3D setTranslationZ(float z){ getTransform().setTranslationZ(z); transformationOccured(); return this; }
	default public Transformable3D setTranslation(Vector3f t){ getTransform().setTranslation(t); transformationOccured(); return this; }
	
	default public Transformable3D setRotationX(float x){ getTransform().setRotationX(x); transformationOccured(); return this; }
	default public Transformable3D setRotationZ(float y){ getTransform().setRotationY(y); transformationOccured(); return this; }
	default public Transformable3D setRotationY(float z){ getTransform().setRotationZ(z); transformationOccured(); return this; }
	default public Transformable3D setRotation(Vector3f r){ getTransform().setRotation(r); transformationOccured(); return this; }
	
	default public Transformable3D setScaleX(float x){ getTransform().setScaleX(x); transformationOccured(); return this; }
	default public Transformable3D setScaleY(float y){ getTransform().setScaleY(y); transformationOccured(); return this; }
	default public Transformable3D setScaleZ(float z){ getTransform().setScaleZ(z); transformationOccured(); return this; }
	default public Transformable3D setScale(Vector3f s){ getTransform().setScale(s); transformationOccured(); return this; }
	default public Transformable3D setScale(float s) { getTransform().setScale(s); transformationOccured(); return this; }
	
	
}
