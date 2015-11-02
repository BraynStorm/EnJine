package enjine.core.gl;

import braynstorm.commonlib.math.Matrix4f;
import braynstorm.commonlib.math.Vector3f;

public class MeshTransform extends AbstractTransform {

	public MeshTransform(){
		super();
	}
	
	public MeshTransform(MeshTransform copy) {
		super(copy);
	}
	
	@Override
	protected void recalculateMatrix() {
		Matrix4f t = new Matrix4f().translate(translation);
		Matrix4f r = new Matrix4f().rotate(rotation);
		Matrix4f s = new Matrix4f().scale(scale);
		
		transformation = t.mul(r.mul(s));
	}
	
	
	public MeshTransform setTranslationX(float x){ translation.x = x; markDirty(); return this; }
	public MeshTransform setTranslationY(float y){ translation.y = y; markDirty(); return this; }
	public MeshTransform setTranslationZ(float z){ translation.z = z; markDirty(); return this; }
	public MeshTransform setTranslation(Vector3f t){ translation = new Vector3f(t); markDirty(); return this; }
	
	public MeshTransform setRotationX(float x){ rotation.x = x; markDirty(); return this; }
	public MeshTransform setRotationZ(float y){ rotation.y = y; markDirty(); return this; }
	public MeshTransform setRotationY(float z){ rotation.z = z; markDirty(); return this; }
	public MeshTransform setRotation(Vector3f r){ rotation = new Vector3f(r); markDirty(); return this; }
	
	public MeshTransform setScaleX(float x){ scale.x = x; markDirty(); return this; }
	public MeshTransform setScaleY(float y){ scale.y = y; markDirty(); return this; }
	public MeshTransform setScaleZ(float z){ scale.z = z; markDirty(); return this; }
	public MeshTransform setScale(Vector3f s){ scale = new Vector3f(s); markDirty(); return this; }
	public MeshTransform setScale(float s) { scale = new Vector3f(s, s, s); markDirty(); return this; }
	
	public MeshTransform translateBy(Vector3f t) { translation.add(t); markDirty(); return this; }

    public MeshTransform rotateBy(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
        markDirty();
        return this;
    }
}
