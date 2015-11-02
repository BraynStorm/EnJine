package enjine.core.gl.particles.tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import braynstorm.commonlib.math.Vector3f;
import enjine.core.Enjine;
import enjine.core.gl.Camera;
import enjine.core.gl.MeshTransform;
import enjine.core.gl.Transformable3D;
import enjine.core.gl.particles.RealParticle;
import enjine.core.resources.ResourceManager;
import enjine.core.utils.Common;

public class ParticleEffectCircleSmoker implements Transformable3D {
    protected float radius;
    protected int maxParticles;
    protected MeshTransform transform;
    protected Comparator<RealParticle> particleComparator;
    protected ArrayList<RealParticle> particles;
    
    public ParticleEffectCircleSmoker(float radius, int maxParticles, MeshTransform transform) {
        this.radius = radius;
        this.transform = transform;
        this.maxParticles = maxParticles;
        particles = new ArrayList<>(maxParticles + 2);
        particleComparator = new Comparator<RealParticle>() {
            
            @Override
            public int compare(RealParticle o1, RealParticle o2) {
                float dist1 = Vector3f.getDistanceSquared(o1.getTransform().getTranslation(), Enjine.camera.getPosition());
                float dist2 = Vector3f.getDistanceSquared(o2.getTransform().getTranslation(), Enjine.camera.getPosition());
                
                
                if(dist1 < dist2)
                    return -1;
                
                if(dist1 > dist2)
                    return 1;
                
                return 0;
            }
        };
    }
    
    public ParticleEffectCircleSmoker(float radius) {
        this(radius, 100, new MeshTransform());
    }
    
    public void render(){
        
        int spawnableCount = maxParticles - particles.size();
        
        // Spawning
        if( spawnableCount > 0 ){
            int count = (int)Common.clamp(Common.random.nextInt(spawnableCount) + 1, 0, 5);
            for(int i = 0; i < count; i++){
                RealParticle p = new RealParticle(ResourceManager.getAnimatedParticle("smokePuff"));
                double randAngle = Common.random.nextFloat() * Math.PI * 2 ;
                float speed = Common.random.nextFloat() * 0.01f + 0.2f;
                
                p.getTransform().setRotationZ(Common.random.nextFloat() * 360).setScale(6);
                
                p.getPhysics().setVelocity(new Vector3f(
                        (float) Math.cos(randAngle),
                        (float) 0.4f,
                        (float) Math.sin(randAngle)
                ), speed);
                
                particles.add(p);
            }
        }
        
        particles.sort(particleComparator);
        
        Iterator<RealParticle> it = particles.iterator();
        
        
        // Rendering and Despawning
        while (it.hasNext()){
            RealParticle p = it.next();
            
            if(shouldDespawn(p))
                it.remove();
            else
                p.render();
        }
    }
    
    protected boolean shouldDespawn(RealParticle p){
        float distanceFromOrigin = p.getTransform().getTranslation().dot(this.getTransform().getTranslation());
        return p.isAnimationFinished() || Math.abs(distanceFromOrigin) > 20f;
    }

    @Override
    public MeshTransform getTransform() {
        return transform;
    }
    @Override
    public void transformationOccured() {
        // TODO Auto-generated method stub
        
    }
    
}
