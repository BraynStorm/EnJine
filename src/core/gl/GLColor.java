package core.gl;

import core.math.Vector4f;

public class GLColor extends Vector4f {
	
	public static final GLColor BLACK 		= new GLColor(0, 0, 0, 1);
	public static final GLColor RED 		= new GLColor(1, 0, 0, 1);
	public static final GLColor GREEN 		= new GLColor(0, 1, 0, 1);
	public static final GLColor BLUE 		= new GLColor(0, 0, 1, 1);
	public static final GLColor CYAN 		= new GLColor(0, 1, 1, 1);
	public static final GLColor YELLOW 		= new GLColor(1, 1, 0, 1);
	public static final GLColor MAGENTA		= new GLColor(1, 0, 1, 1);
	public static final GLColor WHITE 		= new GLColor(1, 1, 1, 1);
	public static final GLColor TRANSPARENT = new GLColor(0, 0, 0, 0);
	
	/**
	 * Values range [0-1].
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public GLColor(float x, float y, float z, float w) {
		super(x, y, z, w);
	}
	
	public GLColor(String hex) {
		super();
		setColor(hex);
	}
	
	public GLColor(GLColor copy){
		super(copy);
	}
	
	/**
	 * @param hex #RRGGBBAA
	 */
	public void setColor(String hex){
		setColor(Integer.parseInt(hex.substring(1), 16));
	}
	
	/**
	 * !! UNTESTED !!
	 * @param hex 0xRRGGBBAA
	 */
	public void setColor(int hex){
		//  R  G  B  A
		//  FF 00 FF 00
		x = (float)((hex & 0xFF0000) >> 16) / (float)0xFF;
		y = (float)((hex & 0x00FF00) >> 8) / (float)0xFF;
		z = (float) (hex & 0x0000FF) / (float)0xFF;
		w = 1;
	}
	
	/**
	 * Sets the color in range [0-255]
	 * @param r Red
	 * @param g Green
	 * @param b BLue
	 * @param a Alpha
	 */
	public void setColor(int r, int g, int b, int a){
		x = (float) r / 255.f;
		y = (float) g / 255.f;
		z = (float) b / 255.f;
		w = (float) a / 255.f;
	}
	
}
