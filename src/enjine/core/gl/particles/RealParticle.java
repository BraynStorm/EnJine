package enjine.core.gl.particles;

import org.json.JSONObject;

import braynstorm.commonlib.math.Matrix4f;
import enjine.core.gl.MeshTransform;
import enjine.core.gl.Origin;
import enjine.core.gl.Shader;
import enjine.core.gl.Texture;
import enjine.core.gl.gui.Rectangle;
import enjine.core.gl.physics.Physics;
import enjine.core.utils.Common;

public class RealParticle {
    
    protected Physics physics = new Physics();
    protected Texture texture;
    protected TextureAnimation animation;
    
    protected Matrix4f originMatrix;
    
    public RealParticle(RealParticle copy) {
        physics = new Physics(copy.physics);
        animation = new TextureAnimation(copy.animation);
        texture = copy.texture;
        originMatrix = new Matrix4f(copy.originMatrix);
    }
    
    public RealParticle(Texture texture, TextureAnimation animation) {
        this.texture = texture;
        this.animation = animation;
        this.originMatrix = new Matrix4f().identity(); 
    }
    
    public RealParticle(Texture texture, int frameCount, boolean continuous) {
        this(texture, new TextureAnimation(frameCount, continuous));
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
        Shader.currentlyBound.setUniform("originMatrix", originMatrix);
        Shader.currentlyBound.setUniform("transformMatrix", physics.getTransfromationMatrix());
        
        animation.set();
        texture.bind();
        Common.renderBO(Rectangle.getVBO(Origin.CENTER), Rectangle.getIBO(), Rectangle.getDrawCount());
    }
    
    public MeshTransform getTransform() {
        return physics.getTransfrom();
    }

    public Physics getPhysics() {
        return physics;
    }

    public RealParticle setPhysicsProps(int props) {
        physics.setProps(props);
        return this;
    }
    
    public RealParticle setPhysicsData(JSONObject jsonObject) {
        physics.setData(jsonObject);
        return this;
    }

    public boolean isAnimationFinished() {
        return animation.isCompleted();
    }
}
