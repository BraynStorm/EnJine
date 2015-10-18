package enjine.core.gl.particles;

import enjine.core.gl.Texture;
import enjine.core.math.Vector3f;

/**
 * Speed = Units/second
 * @author BraynStorm
 *
 */
public class PhysicsParticle extends Particle {
	public static final float    ACCELERATION_GRAVITY = 0.0098f;
	public static final Vector3f GRAVITY = new Vector3f(0,-1,0);
	
	protected Vector3f velocity = new Vector3f(0, 0, 0);
	protected boolean isAffectedByGravity = true; 
	
	public PhysicsParticle(PhysicsParticle p){
	    super(p);
	    velocity = new Vector3f(p.velocity);
	    isAffectedByGravity = p.isAffectedByGravity;
	}
	
	public PhysicsParticle(Texture texture) {
		super(texture);
	}
	
	public PhysicsParticle(Texture texture, ParticleTransform transform) {
		super(texture, transform);
	}
	
	public void setIsAffectedByGravity(boolean v){
	    isAffectedByGravity = v;
	}
	
	public PhysicsParticle setVelocity(Vector3f v){
		velocity = v;
		return this;
	}
	
	protected void update() {
	    if(isAffectedByGravity)
	        velocity = velocity.add(GRAVITY.mul(ACCELERATION_GRAVITY));
	}
	
	@Override
	public void render() {
		this.update();
		transform.translateBy(velocity);
		super.render();
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (isAffectedByGravity ? 1231 : 1237);
        result = prime * result + ((velocity == null) ? 0 : velocity.hashCode());
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
        PhysicsParticle other = (PhysicsParticle) obj;
        if (isAffectedByGravity != other.isAffectedByGravity)
            return false;
        if (velocity == null) {
            if (other.velocity != null)
                return false;
        } else if (!velocity.equals(other.velocity))
            return false;
        return true;
    }
	
	

}
