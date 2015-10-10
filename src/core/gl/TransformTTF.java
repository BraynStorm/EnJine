package core.gl;

import core.math.Matrix4f;

public class TransformTTF extends TransformRectangle{

	protected float moved;
	
	@Override
	public void recalculateMatrix() {
		Matrix4f t = new Matrix4f().translate(translation);
		Matrix4f r = new Matrix4f().rotate(rotation);
		Matrix4f s = new Matrix4f().scale(scale.x, scale.y * Window.getAspectRatio(), scale.z);
		
		Matrix4f offset_mat = new Matrix4f().translate(moved, 0, 0);
		
		transformation = t.mul(s.mul(r.mul(offset_mat)));
	}
	
	public TransformTTF(){
		super();
	}
	
	public TransformTTF(TransformTTF t){
		super(t);
	}
	
	public void addMovement(float amount){
		moved += amount;
		recalculateMatrix();
	}
}
