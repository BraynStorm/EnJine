package core.gl.particles.tests;

import core.RoskoEngine;
import core.gl.particles.ParticleSystem;
import core.gl.particles.ParticleTransform;
import core.math.Vector3f;

public class ParticleSystemCannon extends ParticleSystem{
	
	double passedTime = 0;
	
	public ParticleSystemCannon(ParticleTransform transform) {
		super(1, new ParticleHolderCannon(transform));
		
		emitters.add(
				new ParticleEmitterCannon(
						holder,
						transform,
						new Vector3f(1,1,0)
						)
				);
		
		/*emitters.add(
				new ParticleEmitterCannon(
						holder,
						transform,
						new Vector3f(-1,1,0)
						)
				);*/
		
	}
	
	@Override
	public void render(){
		passedTime += RoskoEngine.timer.getDeltaSeconds();
		
		if(passedTime >= 1d){
			emitters.forEach(e ->{
				e.emit();
			});
			
			System.out.println("Spawning");
			passedTime = 0;
		}
		
		holder.renderParticles();
	}
}
