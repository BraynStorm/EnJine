package core.gl;

import core.math.Vector3f;

public class Material {
	
	public Vector3f kd = new Vector3f(1, 1, 1);
	public Vector3f ka = new Vector3f(1, 1, 1);
	public Vector3f ks = new Vector3f(0, 0, 0);
	public Vector3f ke = new Vector3f(1, 1, 1);
	public float d = 0;
	public float ns = 0; // rang
	
	public int illum;
	
	public Texture map_Kd;
	public int map_Ka = -1;
	public int map_Ks = -1;
	public int map_Ke = -1;
	
	public int disp = -1;
	
	public Material() {}
	
	public void bind(){
		Texture.bind(map_Kd);
		Shader.currentlyBound.setUniform("material.diffuseColor", kd);
	}
	
}
