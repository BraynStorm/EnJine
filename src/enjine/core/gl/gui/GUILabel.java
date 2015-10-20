package enjine.core.gl.gui;

import enjine.core.gl.GLColor;
import enjine.core.gl.Origin;
import enjine.core.gl.Rectangle;
import enjine.core.gl.Texture;
import enjine.core.gl.TransformRectangle;
import enjine.core.gl.TransformTTF;
import enjine.core.gl.Transformable2D;
import enjine.core.gl.TrueTypeFont;
import enjine.core.gl.storage.FontLibrary;

public class GUILabel implements Transformable2D {

    protected TrueTypeFont font;
    protected TransformTTF fontTransform;
    protected Rectangle rectangle;
    protected String text;
    protected GLColor textColor;
    
    public GUILabel() {
        this("");
    }
    
    public GUILabel(String text) {
        this(new TransformRectangle(), "");
    }
    
    public GUILabel(TransformRectangle transform, String text) {
        this(new Rectangle(transform).setOrigin(Origin.CENTER), text);
    }
    
    public GUILabel(Rectangle rectangle, String text) {
        this.rectangle = rectangle;
        this.text = text;
        fontTransform = new TransformTTF();
        textColor = GLColor.BLACK;
        transformationOccured();
    }
    
    
    
    public String getText() {
        return text;
    }
    
    public void render(){
        rectangle.render();
        font.drawString(fontTransform, text, textColor);
    }
    public void setTextColor(String hex) { this.textColor = new GLColor(hex); }
    public void setTextColor(GLColor textColor) { this.textColor = textColor; }
    public GUILabel setText(String text){ this.text = text; return this; }
    public GUILabel setText(Object text){ this.text = text.toString(); return this; }
    public GUILabel setFont(TrueTypeFont font){ this.font = font; return this; }
    public GUILabel setFont(String name, int size){ setFont(FontLibrary.requestFontWithSize(name, size)); return this; }
    public GUILabel setBackgroundColor(GLColor color){ rectangle.setColor(color); return this; }
    public GUILabel setTexture(Texture t){ rectangle.setTexture(t); return this; }
    
    @Override public TransformRectangle getTransform(){ return rectangle.getTransform(); }

    @Override
    public void transformationOccured() {
        //fontTransform.setTranslationX(rectangle.getTransform().getWidth() / -2);
        //fontTransform.setTranslationY(rectangle.getTransform().getHeight() / -2);
        System.out.println("occured");
    }
}
