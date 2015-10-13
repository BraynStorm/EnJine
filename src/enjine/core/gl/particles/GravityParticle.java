package enjine.core.gl.particles;

import enjine.core.gl.Texture;
import enjine.core.math.Vector3f;

/**
 * Speed = Units/second
 * @author BraynStorm
 *
 */
public class GravityParticle extends Particle {
	
	public static final float ACCELERATION_GRAVITY = 0.0098f;
	public static final Vector3f GRAVITY = new Vector3f(0,-1,0);
	
	protected Vector3f velocity;

	public GravityParticle(Texture texture) {
		super(texture);
	}
	
	public GravityParticle(Texture texture, ParticleTransform transform) {
		super(texture, transform);
	}
	
	public GravityParticle setVelocity(Vector3f v){
		velocity = v;
		return this;
	}
	
	protected void updateVelocity() {
		velocity = velocity.add(GRAVITY.mul(ACCELERATION_GRAVITY)); // WHY U NO WURK?!
	}
	
	@Override
	public void render() {
		updateVelocity();
		transform.translateBy(velocity);
		
		System.out.println(velocity.toString());
		super.render();
	}
	

}
