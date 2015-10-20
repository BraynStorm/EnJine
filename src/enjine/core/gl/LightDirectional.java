package enjine.core.gl;

import com.google.common.eventbus.Subscribe;

import enjine.core.event.EventManager;
import enjine.core.event.types.ScrollEvent;
import enjine.core.math.Vector3f;

/**
 * A.K.A Sun.
 * @author BraynStorm
 *
 */
public class LightDirectional{
	private GLColor color;
	private Vector3f direction;
	float angle = (float)Math.toRadians(90);
	
	public static LightDirectional getSun(){
		return new LightDirectional(new GLColor(0.968f,0.956f,0.784f, 1f), new Vector3f(1, 1, 0f));
	}
	
	public LightDirectional(GLColor color, Vector3f direction) {
		EventManager.register(this);
		this.color = color;
		this.direction = direction;
	}
	
	@Subscribe
	public void changeAngle(ScrollEvent event){
		angle += (float)Math.toRadians(event.y);
		System.out.println((float)Math.sin(angle));
	}
	
	public void use(){
		//Shader.currentlyBound.setUniform("sunlight_color", color);
		Shader.currentlyBound.setUniform("sunlight_direction", new Vector3f(-1, (float)Math.cos(angle), (float)Math.sin(angle))); // TODO: Remove this, make the sun act like a real sun
		
	}
	
}
