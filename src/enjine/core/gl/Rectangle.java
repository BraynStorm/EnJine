package enjine.core.gl;

import org.lwjgl.opengl.GL15;

import enjine.core.utils.Common;

public class Rectangle implements IRenderable, Transformable2D {
	private static int[] VBO = new int[5];
	private static int IBO;
	private static final int DRAW_COUNT = 6;
	
	private static boolean I = false;
	
	public static void initialize() {
		if(I) return;
		
		float drawLevel = DrawLevel.LOWMOST_LEVEL.getZValue();
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
