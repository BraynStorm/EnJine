package enjine.core.gl.particles;

import enjine.core.gl.Shader;

public class TextureAnimation{
    int currentFrame = 0;
    int frameCount = 1;
    
    boolean continuous;
    
    public TextureAnimation(TextureAnimation copy) {
        frameCount = copy.frameCount;
        currentFrame = copy.currentFrame;
        continuous = copy.continuous;
    }
    
    public TextureAnimation(int frameCount, boolean continuous) {
        this.frameCount = frameCount;
        this.continuous = continuous;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
    
    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }
    
    public boolean isCompleted(){
        return !continuous && currentFrame == frameCount;
    }
    
    public void set(){
        Shader.currentlyBound.setUniformi("frameCount", frameCount);
        Shader.currentlyBound.setUniformi("currentFrame", currentFrame);
    }
    
    public void update(){
        if(currentFrame < frameCount)
            currentFrame++;
        else if (continuous)
            currentFrame = 0;
    }
}
