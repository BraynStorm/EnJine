package enjine.core.gl.storage;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.font.FontRenderContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import enjine.core.gl.gui.TrueTypeFont;
import enjine.core.utils.Common;

public class FontLibrary {
	
	public static FontRenderContext frc = new FontRenderContext(null, true, false);
	public static HashMap<String, TrueTypeFont> fontLibrary = new HashMap<String, TrueTypeFont>();
	
	
	public static TrueTypeFont requestFontWithSize(String fontName, int size){ return requestFontWithSize(fontName, Font.TRUETYPE_FONT, size); }
	public static TrueTypeFont requestFontWithSize(String fontName, int style, int size){
		try {
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(Common.dataFolder + "fonts/" + fontName + ".ttf"));
			awtFont = awtFont.deriveFont(style, size);
			TrueTypeFont ttf = new TrueTypeFont(awtFont, fontName, true);
			fontLibrary.put(fontName, ttf);
			return ttf;
		} catch (FontFormatException | IOException e) {
			System.out.println("[ERROR] Font " + fontName + " not found.");
			e.printStackTrace();
		}
		
		return null;
	}
	
}
