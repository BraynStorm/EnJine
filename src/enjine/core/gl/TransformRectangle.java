package enjine.core.gl;

import com.google.common.eventbus.Subscribe;

import enjine.core.event.EventManager;
import enjine.core.event.types.WindowSizeEvent;
import enjine.core.math.Matrix4f;
import enjine.core.math.Vector3f;

public class TransformRectangle extends AbstractTransform {
	
	/**
	 * In Pixels.
	 */
	private int width = 0;
	
	/**
	 * In Pixels.
	 */
	private int height = 0;
	
	/**
	 *  Position on screen SHOULD and WILL be defined as offset form the TopLeft corner of the screen.
	 */
	private int xPosition = 0;
	
	/**
	 *  Position on screen SHOULD and WILL be defined as offset form the TopLeft corner of the screen.
	 */
	private int yPosition = 0;
	
	
	public TransformRectangle() {
		super();
		EventManager.register(this);
		recalculateTranslation();
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
		
		recalculateMatrix();
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
				scale.x * ((float) width / (float)Window.getWidth()),
				scale.y * ((float) height / (float)Window.getHeight()),
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
		recalculateMatrix();
	}
	
	/**
	 * Converts pixels to screen coords [0 to 1].
	 */
	protected void recalculateTranslation(){
		translation.x = (2 * (float)xPosition / (float) Window.getWidth()) - 1;
		translation.y = (-2 * (float)yPosition / (float) Window.getHeight()) + 1f;
	}

	public TransformRectangle setWidth(int w){
		width = w;
		recalculateMatrix();
		return this;
	}
	
	public TransformRectangle setHeight(int h){
		height = h;
		recalculateMatrix();
		return this;
	}
	
	public TransformRectangle translateBy(int x, int y){
		xPosition += x;
		yPosition += y;
		recalculateMatrix();
		return this;
	}
	
	public TransformRectangle setScale(float scale){
		this.scale.x = scale;
		this.scale.y = scale;
		this.scale.z = scale;
		recalculateMatrix();
		return this;
	}
	
	public TransformRectangle setScale(Vector3f scale){
		this.scale = new Vector3f(scale);
		recalculateMatrix();
		return this;
	}
	
	public TransformRectangle setTranslationX(int x) {
		xPosition = x;
		recalculateMatrix();
		return this;
	}
	
	public TransformRectangle setTranslationY(int y) {
		yPosition = y;
		recalculateMatrix();
		return this;
	}

	public int getWidth(){return width;}
	public int getHeight(){return height;}

	public int getXPosition(){return xPosition;}
	public int getYPosition(){return yPosition;}
	
}
