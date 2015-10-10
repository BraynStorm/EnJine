package core.gl;

import core.math.Matrix4f;
import core.math.Vector3f;

public class MeshTransform extends Transform {

	public MeshTransform(){
		super();
	}
	
	public MeshTransform(MeshTransform copy) {
		super(copy);
	}
	
	@Override
	public void recalculateMatrix() {
		Matrix4f t = new Matrix4f().translate(translation);
		Matrix4f r = new Matrix4f().rotate(rotation);
		Matrix4f s = new Matrix4f().scale(scale);
		
		transformation = t.mul(r.mul(s));
	}
	
	public Transform setTranslation(Vector3f translation) { this.translation = translation; recalculateMatrix(); return this; }
	public Transform setRotation(Vector3f rotation) { this.rotation = rotation; recalculateMatrix(); return this; }
	public Transform setScale(Vector3f scale) { this.scale = scale; recalculateMatrix(); return this; }
	public Transform setScale(float s) { this.scale = new Vector3f(s, s, s); recalculateMatrix(); return this; }
	
	public Transform setTranslationX(float x) { this.translation.x = x; recalculateMatrix(); return this; }
	public Transform setTranslationY(float y) { this.translation.y = y; recalculateMatrix(); return this; }
	public Transform setTranslationZ(float z) { this.translation.z = z; recalculateMatrix(); return this; }
	
	public Transform setRotationX(float x) { this.rotation.x = x; recalculateMatrix(); return this; }
	public Transform setRotationY(float y) { this.rotation.y = y; recalculateMatrix(); return this; }
	public Transform setRotationZ(float z) { this.rotation.z = z; recalculateMatrix(); return this; }
	
	public Transform setScaleX(float x) { this.scale.x = x; recalculateMatrix(); return this; }
	public Transform setScaleY(float y) { this.scale.y = y; recalculateMatrix(); return this; }
	public Transform setScaleZ(float z) { this.scale.z = z; recalculateMatrix(); return this; }
}
