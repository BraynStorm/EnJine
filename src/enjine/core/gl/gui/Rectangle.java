package enjine.core.gl.gui;

import org.lwjgl.opengl.GL15;

import enjine.core.gl.DrawLevel;
import enjine.core.gl.GLColor;
import enjine.core.gl.IRenderable;
import enjine.core.gl.Origin;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.gl.TransformRectangle;
import enjine.core.utils.Common;

public class Rectangle implements IRenderable, Transformable2D {
    public static final float drawLevel = DrawLevel.LOWMOST_LEVEL.getZValue();
    
    private static int[] VBO = new int[5];
	private static int IBO;
	private static final int DRAW_COUNT = 6;
	private static boolean I = false;
	
	public static void initialize() {
		if(I) return;
		
		
		VBO[Origin.TOPLEFT] = Common.bufferData(GL15.GL_ARRAY_BUFFER,
				0, 0,  drawLevel, 0, 0,
				2, 0,  drawLevel, 1, 0,
			 	2, -2, drawLevel, 1, 1,
				0, -2, drawLevel, 0, 1
		);
		VBO[Origin.TOPRIGHT] = Common.bufferData(GL15.GL_ARRAY_BUFFER,
				-2,  0, drawLevel, 0, 0,
				 0,  0, drawLevel, 1, 0,
			 	 0, -2, drawLevel, 1, 1,
				-2, -2, drawLevel, 0, 1
		);
		VBO[Origin.BOTTOMLEFT] = Common.bufferData(GL15.GL_ARRAY_BUFFER,
				0, 2, drawLevel, 0, 0,
				2, 2, drawLevel, 1, 0,
			 	2, 0, drawLevel, 1, 1,
				0, 0, drawLevel, 0, 1
		);
		VBO[Origin.BOTTOMRIGHT] = Common.bufferData(GL15.GL_ARRAY_BUFFER,
				-2, 2, drawLevel, 0, 0,
				 0, 2, drawLevel, 1, 0,
			 	 0, 0, drawLevel, 1, 1,
				-2, 0, drawLevel, 0, 1
		);
		VBO[Origin.CENTER] = Common.bufferData(GL15.GL_ARRAY_BUFFER,
				-1,  1, drawLevel, 0, 0,
				 1,  1, drawLevel, 1, 0,
			 	 1, -1, drawLevel, 1, 1,
				-1, -1, drawLevel, 0, 1
		);
		
		IBO = Common.bufferData(GL15.GL_ELEMENT_ARRAY_BUFFER,
    			0, 2, 3,
    			0, 1, 2
		);
		
		I = true;
	}
	
	/**
	 * Creates a VBO for a rectangle with the specified texCoords.
	 * @param origin See {@link Origin}
	 * @param texX1 Left of the rectangle
	 * @param texY1 Top of the rectangle
	 * @param texX2 Right of the rectangle
	 * @param texY2 Bottom of the rectangle
	 */
	public static int createRectangleVBO(int origin, float texX1, float texY1, float texX2, float texY2){
	    if(origin == Origin.TOPLEFT)
	        return Common.bufferData(GL15.GL_ARRAY_BUFFER,
	                0,  0, drawLevel, texX1, texY1,
	                2,  0, drawLevel, texX2, texY1,
	                2, -2, drawLevel, texX2, texY2,
	                0, -2, drawLevel, texX1, texY2
	        );
	    
	    if(origin == Origin.TOPRIGHT)
    	    return Common.bufferData(GL15.GL_ARRAY_BUFFER,
                   -2,  0, drawLevel, texX1, texY1,
                    0,  0, drawLevel, texX2, texY1,
                    0, -2, drawLevel, texX2, texY2,
                   -2, -2, drawLevel, texX1, texY2
            );
	    
	    if(origin == Origin.BOTTOMLEFT)
	        return Common.bufferData(GL15.GL_ARRAY_BUFFER,
	                0, 2, drawLevel, texX1, texY1,
	                2, 2, drawLevel, texX2, texY1,
	                2, 0, drawLevel, texX2, texY2,
	                0, 0, drawLevel, texX1, texY2 
	        );
	    
	    if(origin == Origin.BOTTOMRIGHT)
	        return Common.bufferData(GL15.GL_ARRAY_BUFFER,
	                -2, 2, drawLevel, texX1, texY1,
	                 0, 2, drawLevel, texX2, texY1,
	                 0, 0, drawLevel, texX2, texY2,
	                -2, 0, drawLevel, texX1, texY2 
	        );
	    
//      if(origin == Origin.CENTER)
	    return Common.bufferData(GL15.GL_ARRAY_BUFFER,
                -1,  1, drawLevel, texX1, texY1,
                 1,  1, drawLevel, texX2, texY1,
                 1, -1, drawLevel, texX2, texY2,
                -1, -1, drawLevel, texX1, texY2
	    );
	}
	
	private int origin = 0;
	
	
	protected TransformRectangle transform;
	protected GLColor color = Common.randomColor(false);
	protected Texture texture;
	
	public Rectangle(){
		transform = new TransformRectangle();
	}
	
	public Rectangle(TransformRectangle transform){
		this.transform = transform;
	}
	
	public Rectangle setColor(GLColor color){
		this.color = color;
		return this;
	}
	
	public Rectangle setOrigin(int origin){
		if(origin <= 4 && origin >= 0)
			this.origin = origin;
		
		return this;
	}
	
	public Rectangle setTexture(Texture texture){
		this.texture = texture;
		return this;
	}
	
	@Override
	public void render() {
		Texture.bind(texture);
		
		Shader.currentlyBound.setUniform("color", color);
		Shader.currentlyBound.setUniform("transform", transform.getTransformation());
		Common.renderBO(VBO[origin], IBO, DRAW_COUNT);
	}
	
	/**
	 * <b>Copy</b> constructor.
	 * @param rect
	 */
	public Rectangle(Rectangle rect){
		origin = rect.origin;
		transform = new TransformRectangle(rect.transform);
	}
	
	public static int getVBO(int origin){
		return VBO[origin];
	}
	
	public static int getIBO(){
		return IBO;
	}
	
	public static int getDrawCount(){
		return DRAW_COUNT;
	}

    @Override
    public TransformRectangle getTransform() {
        return transform;
    }

    @Override
    public void transformationOccured(){ /* Noting to see here*/ }
	
}
