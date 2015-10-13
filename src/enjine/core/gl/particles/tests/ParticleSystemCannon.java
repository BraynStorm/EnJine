package enjine.core.gl.particles.tests;

import enjine.core.Enjine;
import enjine.core.gl.particles.ParticleSystem;
import enjine.core.gl.particles.ParticleTransform;
import enjine.core.math.Vector3f;

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
		passedTime += Enjine.timer.getDeltaSeconds();
		
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
