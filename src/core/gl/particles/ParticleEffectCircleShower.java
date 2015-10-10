package core.gl.particles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import core.RoskoEngine;
import core.gl.MeshTransform;
import core.gl.Origin;
import core.gl.Shader;
import core.gl.Texture;
import core.math.Vector3f;
import core.resources.ResourceManager;
import core.utils.Common;

public class ParticleEffectCircleShower extends ParticleEffect {
	
	private ParticleShapeSprinkler shape;
	private ArrayList<Particle> particles;
	private MeshTransform particleTransform;
	private MeshTransform origin;
	private float maxLifespan;
	private int maxParticles;
	
	private static Comparator<Particle> particleComparator = new Comparator<Particle>(){
		@Override
		public int compare(Particle a, Particle b) {
			float distA = Vector3f.getDistanceSquared(a.particleTransform.getTranslation(), RoskoEngine.camera.getPosition());
			float distB = Vector3f.getDistanceSquared(b.particleTransform.getTranslation(), RoskoEngine.camera.getPosition());
			
			if(distA < distB)
				return 1;
			
			return distA == distB ? 0 : -1;
		}
	};
	
	private Texture texture;
	
	public ParticleEffectCircleShower(MeshTransform origin, int maxParticles, float maxLifespan) {
		this.maxParticles = maxParticles;
		this.maxLifespan = maxLifespan;
		this.origin = origin;
		
		this.shape = new ParticleShapeSprinkler(1f, 2f, 0f);
		this.particles = new ArrayList<Particle>(maxParticles + 1);
		this.particleTransform = new MeshTransform();
		particleTransform.setScale(0.07f);
		
		texture = ResourceManager.getInstance().getTexture(5);
	}

	@Override
	public void render() {
		Shader.currentlyBound.setUniform("particleOriginTransform", origin.getTransformation());
		
		if(particles.size() < maxParticles)
			spawnParticles();
		else
			angle = Common.random.nextFloat() * 360;
		
		
		Iterator<Particle> it = particles.iterator();
		
		while(it.hasNext()){
			Particle p = it.next();
			int age = p.getAge();
			
			if(age >= maxLifespan){
				it.remove();
			}else{
				ParticlePoint pp = shape.getPointAt(age / maxLifespan, angle);
				particleTransform.setTranslation(pp.position);
				particleTransform.setRotation(new Vector3f(RoskoEngine.camera.getRotationX(),-RoskoEngine.camera.getRotationY(), 0));
				p.originTransform = origin;
				p.render();
			}
		}
	}
	
	private float angle = Common.random.nextFloat() * 360;
	
	public void spawnParticles(){
		System.out.println("Currently Spawned " + particles.size());
		
		int count = (int)(1);
		
		if(particles.size() + count > maxParticles)
			count = maxParticles - particles.size();
		
		for(int i = 0; i < count; i++)
			particles.add(
					new Particle(
							texture,
							new Vector3f(Common.random.nextFloat(), Common.random.nextFloat(), Common.random.nextFloat())
					).setTransform(particleTransform)
			);
		
	}

	@Override
	public ParticleEffect setEffectOrigin() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
