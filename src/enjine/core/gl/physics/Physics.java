package enjine.core.gl.physics;

import org.json.JSONObject;

import enjine.core.Enjine;
import enjine.core.gl.MeshTransform;
import enjine.core.math.Matrix4f;
import enjine.core.math.Vector3f;
import enjine.core.utils.Common;

/**
 * Note: densities are calculated at 0 C.
 * @author BraynStorm
 *
 */
public class Physics {
    public static final float AIR_DENSITY = 1.2922f;
    
    public static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);
    
    public static final Vector3f GRAVITY_VECTOR = UP_VECTOR.getMul(-9.8f);
    public static final Vector3f FLOAT_VECTOR = UP_VECTOR;
    
    
    protected MeshTransform transfrom;
    protected int properties;
    
    protected float density;
    protected Vector3f velocity;
    
    public Physics() {
        transfrom = new MeshTransform();
        properties = AFFECTED_BY_GRAVITY | AFFECTED_BY_DENSITY;
        velocity = new Vector3f(Vector3f.ZERO_ZERO_ZERO);
    }
    
    public Physics(Physics copy){
        transfrom = new MeshTransform(copy.transfrom);
        properties = copy.properties;
        velocity = new Vector3f(copy.velocity);
    }
    
    
    /**
     * Updates the current physics of the object. Should be called once per frame.
     */
    public void update(){
        float deltaTime = (float) Enjine.timer.getDeltaSeconds();
        float randomFactor = 0.0005f;
        
        if(getProp(AFFECTED_BY_GRAVITY))
            velocity.add(GRAVITY_VECTOR.getMul(deltaTime));
        
        if(getProp(AFFECTED_BY_DENSITY))
            velocity.add(FLOAT_VECTOR.getMul(density * deltaTime / AIR_DENSITY));
        
        if(getProp(FLIGHT_JITTER))
            velocity.add(new Vector3f ( (Common.random.nextFloat() * 2 - 1) * randomFactor, (Common.random.nextFloat() * 2 - 1) * randomFactor, (Common.random.nextFloat() * 2 - 1) * randomFactor) );
        
        transfrom.translateBy(velocity);
        

        //System.out.println(String.format("X: %.4f, Y: %.4f, Z: %.4f,", velocity.x, velocity.y, velocity.z));
    }
    
    
    public void setDensity(float density) {
        this.density = density;
    }
    
    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }
    
    public void setVelocity(Vector3f direction, float speed) {
        setVelocity(direction.getMul(speed));
    }
    
    public void setTransfrom(MeshTransform transform) {
        this.transfrom = new MeshTransform(transform);
    }
    
    public Matrix4f getTransfromationMatrix() {
        return transfrom.getTransformation();
    }
    
    /**
     * Sets a current property of this physics object.
     */
    public void setProp(int prop, boolean val){
        properties = val
                ? properties | prop // Ez Pz
                : properties & (~prop); // Not so easy. Flip all the bits in 'prop' and AND it with the 'props'.
    }
    
    /**
     * Sets all properties
     */
    public void setProps(int props){
        properties = props;
    }
    
    public boolean getProp(int prop){
        return (properties & prop) == prop;
    }
    
    public void setData(JSONObject data) {
        if(data.has("density"))
            density = (float) data.getDouble("density");
    }
    
    
    public static final int AFFECTED_BY_GRAVITY = 1;
    public static final int AFFECTED_BY_DENSITY = 2;
    public static final int FLIGHT_JITTER       = 4;

    
    
}
