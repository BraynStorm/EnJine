package enjine.core.gl;

public class DrawLevel {
	
	/**
	 * TODO: Make it into an array list so it can be traversed.
	 */
	
	private float zValue;
	
	private DrawLevel(float zValue){ this.zValue = zValue; }
	
	public float getZValue() {
		return zValue;
	}
	
	public static final float oneStepCloser = -0.01f;
	public static final float oneStepFarther = 0.01f;
	
	public static final DrawLevel TOPMOST_LEVEL 	= new DrawLevel(0.95f);
	public static final DrawLevel TOP_LEVEL 		= new DrawLevel(0.96f);
	public static final DrawLevel MIDDLE_LEVEL 		= new DrawLevel(0.97f);
	public static final DrawLevel LOW_LEVEL 		= new DrawLevel(0.98f);
	public static final DrawLevel LOWMOST_LEVEL 	= new DrawLevel(0.99f);
	
	
}
