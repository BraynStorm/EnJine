package enjine.core.gl;

import java.util.ArrayList;


public class FullBlownModel {
	ArrayList<MeshWithTransform> meshes;
	//ArrayList<ParticleSystem> particleSystems;
	
	public void render(){
		meshes.forEach(m -> m.render());
		//particleSystems.forEach(m -> m.loop());
	}
	
}
