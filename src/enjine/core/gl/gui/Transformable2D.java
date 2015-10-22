package enjine.core.gl.gui;

import enjine.core.gl.DrawLevel;
import enjine.core.gl.TransformRectangle;
import enjine.core.math.Vector3f;

public interface Transformable2D {
    
    public TransformRectangle getTransform();
    public void transformationOccured();
    
    default public Transformable2D rotateBy(float deg){ getTransform().rotateBy(deg); transformationOccured(); return this; }
    default public Transformable2D setRotation(float deg){ getTransform().setRotation(deg); transformationOccured(); return this; }
    
    default public Transformable2D setScale(float scale){ getTransform().setScale(scale); transformationOccured(); return this; }
    default public Transformable2D setScale(Vector3f scale){ getTransform().setScale(scale); transformationOccured(); return this; }
    
    default public Transformable2D setTranslationX(int x){ getTransform().setTranslationX(x); transformationOccured(); return this; }
    default public Transformable2D setTranslationY(int y){ getTransform().setTranslationY(y); transformationOccured(); return this; }
    default public Transformable2D setDrawLevel(DrawLevel dl){ getTransform().setDrawLevel(dl); transformationOccured(); return this; }
    
    default public Transformable2D setWidth(int w){ getTransform().setWidth(w); transformationOccured(); return this; }
    default public Transformable2D setHeight(int h){ getTransform().setHeight(h); transformationOccured(); return this; }
}
