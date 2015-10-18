package enjine.core.gl.gui;

import com.google.common.eventbus.Subscribe;

import enjine.core.event.EventManager;
import enjine.core.event.types.MouseButtonEvent;
import enjine.core.event.types.MousePositionEvent;
import enjine.core.gl.Rectangle;
import enjine.core.gl.Texture;
import enjine.core.gl.TransformRectangle;
import enjine.core.resources.ResourceManager;
import enjine.core.utils.Common;

public class GUIButton {
	private Rectangle shape;
	
	private Texture normal;
	private Texture clicked;
	private Texture hovered;
	
	/**
	 * 0 = Normal,
	 * 1 = Pressed
	 * everythingElse = Hovered
	 */
	private int currentAction = 0;
	
	public GUIButton(int textureID){
		EventManager.register(this);
		shape = new Rectangle();
		
		normal = ResourceManager.getTexture(textureID);
		clicked = ResourceManager.getTexture(textureID+1);
		hovered = ResourceManager.getTexture(textureID+2);
	}
	
	@Subscribe
	public void clicked(MouseButtonEvent event){
		if(event.isActionRelease()){
			MouseButtonEvent.guiClicked = false;
			currentAction = 0;
		}
		
		if(!Common.coordsInsideRectangle(event.x, event.y, shape.transform))
			return;
		
		if(event.isActionPress())
			MouseButtonEvent.guiClicked = true;
		
		currentAction = event.action;
		
		System.out.println("Button Click");
		
		//TODO: Do Button Stuff (on release).
	}
	
	@Subscribe
	public void hovered(MousePositionEvent event){
		if(!Common.coordsInsideRectangle(event.x, event.y, shape.transform)){
			if(currentAction == -1)
				currentAction = 0;
			
			return;
		}
		
		if(currentAction != 1)
			currentAction = -1;
	}

	public TransformRectangle getTransform() {
		return shape.transform;
	}

	public void render() {
		if(currentAction == 0)
			shape.texture = normal;
		else if(currentAction == 1)
			shape.texture = clicked;
		else{
			shape.texture = hovered;
		}
		
		shape.render();
	}
}
