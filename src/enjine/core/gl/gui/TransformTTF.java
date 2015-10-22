package enjine.core.gl.gui;

import enjine.core.gl.TransformRectangle;
import enjine.core.gl.Window;
import enjine.core.math.Matrix4f;

public class TransformTTF extends TransformRectangle{

	protected float moved = 0;
	
	@Override
	public void recalculateMatrix() {
	    recalculateTranslation();
        Matrix4f t = new Matrix4f().translate(translation.x + (moved / Window.getWidth()), translation.y, translation.z);
        Matrix4f r = new Matrix4f().rotate(rotation);
        Matrix4f s = new Matrix4f().scale(
                scale.x * ((float) width / (float)Window.getWidth()),
                scale.y * ((float) height / (float)Window.getHeight()),
                scale.z
        );
        
        
        
        transformation = t.mul(s.mul(r));
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
	public void setMoved(float moved) {
        this.moved = moved;
        markDirty();
    }
}
