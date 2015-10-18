package enjine.core.gl.particles;

import enjine.core.gl.Shader;
import enjine.core.gl.Texture;

public class AnimatedParticle extends PhysicsParticle {

    private int frameCount;
    private int currentFrame = 0;
    
    public AnimatedParticle(AnimatedParticle p) {
        super(p);
        frameCount = p.frameCount;
        currentFrame = p.currentFrame;
    }
    
    public AnimatedParticle(Texture texture, int frameCount) {
        super(texture);
        this.frameCount = frameCount;
    }

    public AnimatedParticle(Texture texture, int frameCount, ParticleTransform transform) {
        super(texture, transform);
        this.frameCount = frameCount;
    }
    
    @Override
    protected void update() {
        
        Shader.currentlyBound.setUniformi("currentFrame", currentFrame);
        Shader.currentlyBound.setUniformi("frameCount", frameCount);
        
        if(currentFrame < frameCount)
            currentFrame++;
        
        super.update();
    }
    
    public boolean hasFinishedAnimation(){
        return currentFrame == frameCount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + currentFrame;
        result = prime * result + frameCount;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnimatedParticle other = (AnimatedParticle) obj;
        if (currentFrame != other.currentFrame)
            return false;
        if (frameCount != other.frameCount)
            return false;
        return true;
    }
    
    
    
}
