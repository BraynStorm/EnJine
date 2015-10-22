package enjine.core.gl.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import enjine.core.gl.GLColor;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.utils.Common;

public class TrueTypeFont {
	
	private IntObject[] charArray = new IntObject[224];
	private int[] vboArray = new int[224];
	
	private boolean antiAlias;
	
	private Texture fontTexture;
	public Texture getFontTexture() {
		return fontTexture;
	}

	private int textureWidth = 512;
	private int textureHeight = 512;
	
	private int origin;
	
	private Font font;
	private FontMetrics fontMetrics;
	private int fontSize = 0;
	private int fontHeight = 0;
	
	private String name;
	private int style;
	
	private class IntObject {
		/** Character's width */
		public int width;

		/** Character's height */
		public int height;

		/** Character's stored x position */
		public int storedX;

		/** Character's stored y position */
		public int storedY;
		
		public float realW;
		public float realH;
	}
	
	public TrueTypeFont(Font font, String name, boolean antiAlias) {
		
		this.font = font;
		this.fontSize = font.getSize();
		this.antiAlias = antiAlias;
		this.name = name;
		
		createSet();
	}
	
	private BufferedImage getFontImage(char ch) {
		
		BufferedImage tempfontImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) tempfontImage.getGraphics();
		
		if (antiAlias == true) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		
		int charwidth = fontMetrics.charWidth(ch);

		if (charwidth <= 0) {
			charwidth = 1;
		}
		
		int charheight = fontMetrics.getHeight();
		
		if (charheight <= 0) {
			charheight = fontSize;
		}

		// Create another image holding the character we are creating
		BufferedImage fontImage;
		fontImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D) fontImage.getGraphics();
		
		if (antiAlias == true) {
			gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		gt.setFont(font);
		gt.setColor(Color.WHITE);
		gt.drawString(String.valueOf(ch), 0, fontMetrics.getAscent());
		
		return fontImage;
	}

	
	private void createSet() {
		
		BufferedImage imgTemp = new BufferedImage(textureWidth, textureHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) imgTemp.getGraphics();

		g.setColor(new Color(255,255,255,1));
		g.fillRect(0,0,textureWidth,textureHeight);
		
		
		int rowHeight = 0;
		int positionX = 0;
		int positionY = 0;
		
		for (int i = 0; i < 224; i++) {
			
			char ch = (char) (i);
			
			BufferedImage fontImage = getFontImage(ch);

			IntObject newIntObject = new IntObject();

			newIntObject.width = fontImage.getWidth();
			newIntObject.height = fontImage.getHeight();

			if (positionX + newIntObject.width >= textureWidth) {
				positionX = 0;
				positionY += rowHeight;
				rowHeight = 0;
			}

			newIntObject.storedX = positionX;
			newIntObject.storedY = positionY;

			if (newIntObject.height > fontHeight) {
				fontHeight = newIntObject.height;
			}

			if (newIntObject.height > rowHeight) {
				rowHeight = newIntObject.height;
			}
			
			// Draw it here
			g.drawImage(fontImage, positionX, positionY, null);

			positionX += newIntObject.width;
			
			charArray[i] = newIntObject;
			fontImage = null;
		}
		
		for(int i = 0; i < 224; i++)
			glyphDataProcessing(i, imgTemp);
		
		fontTexture = textureFromBufferedImage(imgTemp);
	}
	
	public static Texture textureFromBufferedImage(BufferedImage img){
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		int width = img.getWidth();
		int height = img.getHeight();
		int color = 0;
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width  * height * 4);
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				color = img.getRGB(j, i);
				buffer.put((byte) ((color >> 16)  & 0xFF));
				buffer.put((byte) ((color >>  8)  & 0xFF));
				buffer.put((byte)  (color & 0xFF) );
				buffer.put((byte) ((color >> 24) & 0xFF));
			}
		}
		
		
		buffer.flip();
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		Texture t = new Texture(id, width, height, Format.RGBA);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return t;
	}
	
	
	private void glyphDataProcessing(int i, BufferedImage finalImage){
		IntObject intObject = charArray[i];
		
		intObject.realW = (0.0f + intObject.width) / finalImage.getWidth();
		intObject.realH = (0.0f + intObject.height) / finalImage.getHeight();
		
		//In tex coords the coordinates are correct. (Trust me, I'm your future self)
		float realX1 = (float)intObject.storedX / (float) finalImage.getWidth(); // Left
		float realY1 = (float)intObject.storedY / (float) finalImage.getHeight(); // Bottom
		float realX2 = intObject.realW + realX1; // Right
		float realY2 = intObject.realH + realY1; // Top
		
		/*
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4*5);
		
		buffer.put(0).put(0).put(0) .put(realX1)        .put(realY1);
        buffer.put(2).put(0).put(0) .put(realX2)        .put(realY1);
        buffer.put(2).put(-2).put(0) .put(realX2)        .put(realY2);
        buffer.put(0).put(-2).put(0) .put(realX1)        .put(realY2);
		
		buffer.flip();
		*/
		
		vboArray[i] = Rectangle.createRectangleVBO(origin, realX1, realY1, realX2, realY2);
	}
	
	public int getWidth(String whatchars) {
		int totalwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;
		for (int i = 0; i < whatchars.length(); i++) {
			currentChar = whatchars.charAt(i);
			
			intObject = charArray[currentChar];
			
			
			if( intObject != null )
				totalwidth += intObject.width;
		}
		return totalwidth;
	}
	
	public int getHeight() {
		return fontHeight;
	}
	
	public int getHeight(String HeightString) {
		return fontHeight;
	}
	
	public int getLineHeight() {
		return fontHeight;
	}
	
	public void drawString(TransformTTF transform, int w, GLColor color) {
		drawString(transform, String.valueOf(w), color);
	}
	
	/**
	 * 
	 * @param transform The transformation matrix (zRotOnly?) that applies to that whole text
	 * @param whatchars the string to render
	 * @param color a Vector4f (RGBA).
	 */
	public void drawString(TransformTTF transform, String whatchars, GLColor color) {
		fontTexture.bind();
		
		transform.setMoved(0);
		
		for (int i = 0; i < whatchars.length(); i++) {
			int charCurrent = whatchars.charAt(i);
			
			if(charCurrent < 0){
				//Something has messed up. Horribly.
				new Exception().printStackTrace();
				return;
			}
			
			IntObject intObject = charArray[charCurrent];
			
			if( intObject != null ) {
			    transform.setWidth(intObject.width).setHeight(intObject.height);
			    
				Shader.currentlyBound.setUniform("transform", transform.getTransformation());
				Shader.currentlyBound.setUniform("color", color);
				//drawQuad(charCurrent);
				Common.renderBO(vboArray[charCurrent], Rectangle.getIBO(), Rectangle.getDrawCount());
				transform.addMovement(intObject.width * 2);
			}
		}
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public float getSize() {
		return this.font.getSize();
	}

	public String getName() {
		return name;
	}
	
	public int getStyle() {
		return style;
	}

	public Font getFont() {
		return font;
	}

	public boolean equals(String fontName, int style2, int size2) {
		return name.equals(fontName) && style2 == style && size2 == fontSize;
	}
	
}
