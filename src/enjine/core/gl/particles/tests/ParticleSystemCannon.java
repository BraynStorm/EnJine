package enjine.core.gl.particles.tests;

import enjine.core.Enjine;
import enjine.core.gl.particles.AnimatedParticle;
import enjine.core.gl.particles.ParticleSystem;
import enjine.core.gl.particles.ParticleTransform;
import enjine.core.gl.particles.PhysicsParticle;
import enjine.core.math.Vector3f;

public class ParticleSystemCannon extends ParticleSystem{
	
	double passedTime = 0;
	
	protected PhysicsParticle template;
	
	public ParticleSystemCannon(ParticleTransform transform, AnimatedParticle template) {
		super(new ParticleHolderCannon(transform));
		this.template = new AnimatedParticle(template);
		emitters.add(
				new ParticleEmitterCannon(
						holder,
						transform,
						template,
						new Vector3f(0.001f,0.02f,0)
						)
				);
		
	}
	
	@Override
	public void loop(){
		passedTime += Enjine.timer.getDeltaSeconds();
		
		if(passedTime >= 01d){
			emitters.forEach(e ->{
				e.emit();
			});
			
			passedTime = 0;
		}
		
		holder.renderParticles();
	}
}
