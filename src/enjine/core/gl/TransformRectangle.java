package enjine.core.gl;

import com.google.common.eventbus.Subscribe;

import braynstorm.commonlib.math.Matrix4f;
import braynstorm.commonlib.math.Vector3f;
import enjine.core.event.EventManager;
import enjine.core.event.types.WindowSizeEvent;

public class TransformRectangle extends AbstractTransform {
	
	/**
	 * In Pixels.
	 */
	protected int width = 0;
	
	/**
	 * In Pixels.
	 */
	protected int height = 0;
	
	/**
	 *  Position on screen SHOULD and WILL be defined as offset form the TopLeft corner of the screen.
	 */
	protected int xPosition = 0;
	
	/**
	 *  Position on screen SHOULD and WILL be defined as offset form the TopLeft corner of the screen.
	 */
	protected int yPosition = 0;
	
	
	
	public TransformRectangle() {
		super();
		EventManager.register(this);
	}
	
	/**
	 * Copy contructor.
	 */
	public TransformRectangle(TransformRectangle transform) {
		super(transform);
		width = transform.width;
		height = transform.height;
		xPosition = transform.xPosition;
		yPosition = transform.yPosition;
		
		EventManager.register(this);
	}
	
	/**
	 * @return Recalculates the whole {@link Matrix4f}.
	 */
	@Override
	protected void recalculateMatrix() {
		recalculateTranslation();
		Matrix4f t = new Matrix4f().translate(translation);
		Matrix4f r = new Matrix4f().rotate(rotation);
		Matrix4f s = new Matrix4f().scale(
				scale.x * ((float) width / Window.getWidth()),
				scale.y * ((float) height / Window.getHeight()),
				scale.z
				);
		
		transformation = t.mul(s.mul(r));
	}
	
	/**
	 * Fixes the fact that whenever the window gets resized, {@link setXPosition} and {@link setYPosition} aren't necessarily called.
	 * @param event The window size event.
	 */
	@Subscribe
	public void windowResized(WindowSizeEvent event){
	    markDirty();
	}
	
	/**
	 * Converts pixels to screen coords [0 to 1].
	 */
	protected void recalculateTranslation(){
		translation.x = (2 * (float)xPosition / (float) Window.getWidth()) - 1;
		translation.y = (-2 * (float)yPosition / (float) Window.getHeight()) + 1f;
	}

	public void setDrawLevel(DrawLevel dl) {
        translation.z = dl.getZValue();
    }
	
	public TransformRectangle setWidth(int w){
		width = w;
		markDirty();
		return this;
	}
	
	public TransformRectangle setHeight(int h){
		height = h;
		markDirty();
		return this;
	}
	
	public TransformRectangle translateBy(int x, int y){
		xPosition += x;
		yPosition += y;
		markDirty();
		return this;
	}
	
	public TransformRectangle setScale(float scale){
		this.scale.x = scale;
		this.scale.y = scale;
		this.scale.z = scale;
		markDirty();
		return this;
	}
	
	public TransformRectangle setScale(Vector3f scale){
		this.scale = new Vector3f(scale);
		markDirty();
		return this;
	}
	
	public TransformRectangle setTranslationX(int x) {
		xPosition = x;
		markDirty();
		return this;
	}
	
	public TransformRectangle setTranslationY(int y) {
		yPosition = y;
		markDirty();
		return this;
	}

	public TransformRectangle setRotation(float deg){
	    rotation.z = deg;
	    markDirty();
	    return this;
	}
	
	public TransformRectangle rotateBy(float deg){
	    rotation.z += deg;
	    markDirty();
	    return this;
	}
	
	public float getRealWidth(){
        return (float)getWidth() / Window.getWidth();
    }
	
	public float getRealHeight(){
        return (float)getHeight() / Window.getHeight();
    }
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}

	public int getXPosition(){return xPosition;}
	public int getYPosition(){return yPosition;}

    
	
}
