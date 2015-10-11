package core.gl;

import java.util.ArrayList;

import core.gl.particles.ParticleSystem;

public class FullBlownModel {
	ArrayList<MeshWithTransform> meshes;
	ArrayList<ParticleSystem> particleSystems;
	
	public void render(){
		meshes.forEach(m -> m.render());
		particleSystems.forEach(m -> m.render());
	}
	
}
