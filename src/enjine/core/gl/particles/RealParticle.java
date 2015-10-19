package enjine.core.gl.particles;

import org.json.JSONObject;

import enjine.core.gl.MeshTransform;
import enjine.core.gl.Origin;
import enjine.core.gl.Rectangle;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.gl.physics.Physics;
import enjine.core.math.Matrix4f;
import enjine.core.utils.Common;

public class RealParticle {
    
    protected Physics physics = new Physics();
    protected Texture texture;
    protected TextureAnimation animation;
    
    public RealParticle(RealParticle copy) {
        physics = new Physics(copy.physics);
        animation = new TextureAnimation(copy.animation);
        texture = copy.texture;
    }
    
    public RealParticle(Texture texture, TextureAnimation animation) {
        this.texture = texture;
        this.animation = animation;
    }
    
    public RealParticle(Texture texture, int frameCount, boolean continuous) {
        this.texture = texture;
        this.animation = new TextureAnimation(frameCount, continuous);
    }
    
    public RealParticle setTransform(MeshTransform transform){
        physics.setTransfrom(transform);
        return this;
    }

    protected void update(){
        physics.update();
        animation.update();
    }
    
    public void render(){
        update();
        
        Shader.currentlyBound.setUniform("particleOriginTransform", new Matrix4f().identity());
        
        Shader.currentlyBound.setUniform("particleTransform", physics.getTransfromationMatrix());
        
        animation.set();
        texture.bind();
        Common.renderBO(Rectangle.getVBO(Origin.CENTER), Rectangle.getIBO(), Rectangle.getDrawCount());
    }
    
    public RealParticle setPhysicsProps(int props) {
        physics.setProps(props);
        return this;
    }
    
    public RealParticle setPhysicsData(JSONObject jsonObject) {
        physics.setData(jsonObject);
        return this;
    }
    
    class TextureAnimation{
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

    


    
}
