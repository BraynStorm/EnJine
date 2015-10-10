package core.gl.particles;

import core.gl.MeshTransform;
import core.gl.Origin;
import core.gl.Rectangle;
import core.gl.Shader;
import core.gl.Texture;
import core.math.Time;
import core.math.Vector3f;
import core.resources.ResourceManager;
import core.utils.Common;

/**
 * Default origin - {@link Origin.CENTER}
 * Default color - #FFFFFF
 * @author BraynStorm
 *
 */
public class Particle{
	private long timeCreated;
	
	public Vector3f color;
	public Texture texture;
	public MeshTransform transform;
	public MeshTransform origin;
	
	public Particle(int textureID){ this(textureID, new Vector3f(1, 1, 1)); }
	public Particle(int textureID, Vector3f color){
		this.texture = ResourceManager.getInstance().getTexture(textureID);
		this.color = color;
		
		timeCreated = Time.getMilli();
	}
	
	public Particle(Texture texture){ this(texture, new Vector3f(1, 1, 1)); }
	public Particle(Texture texture, Vector3f color){
		this.texture = texture;
		this.color = color;
		
		timeCreated = Time.getMilli();
	}
	
	public Particle(Particle copy){
		color = new Vector3f(copy.color);
		texture = copy.texture;
		transform = copy.transform;
		timeCreated = copy.timeCreated;
	}
	
	public Particle setTransform(MeshTransform transform){
		this.transform = transform;
		return this;
	}
	
	public void render(){
		texture.bind();
		Shader.currentlyBound.setUniform("particleOriginTransform", origin.getTransformation());
		Shader.currentlyBound.setUniform("particleTransform", transform.getTransformation());
		Shader.currentlyBound.setUniform("particleColor", color);
		Common.renderBO(Rectangle.getVBO(Origin.CENTER), Rectangle.getIBO(), Rectangle.getDrawCount());
	}
	
	public int getAge(){
		return (int)(Time.getMilli() - timeCreated);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(!obj.getClass().equals(this.getClass()))
			return false;
		
		Particle p = (Particle) obj;
		
		return transform.equals(p.transform)
				&& texture.equals(p.texture)
				&& color.equals(p.color)
				&& timeCreated == p.timeCreated; // Almost forgot the most important one;
	}
}
