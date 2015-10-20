package enjine.core.gl;

import enjine.core.math.Matrix4f;

public class TransformTTF extends TransformRectangle{

	protected float moved;
	
	@Override
	public void recalculateMatrix() {
		super.recalculateMatrix();
		
		transformation = transformation.mul(new Matrix4f().translate(moved,0,0));
	}
	
	public TransformTTF(){
		super();
	}
	
	public TransformTTF(TransformTTF t){
		super(t);
	}
	
	public void addMovement(float amount){
		moved += amount;
		markDirty();
	}
}
