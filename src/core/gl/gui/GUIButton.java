package core.gl.gui;

import com.google.common.eventbus.Subscribe;

import core.event.EventManager;
import core.event.types.MouseButtonEvent;
import core.event.types.MousePositionEvent;
import core.gl.Rectangle;
import core.gl.Texture;
import core.gl.TransformRectangle;
import core.resources.ResourceManager;
import core.utils.Common;

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
		
		normal = ResourceManager.getInstance().getTexture(textureID);
		clicked = ResourceManager.getInstance().getTexture(textureID+1);
		hovered = ResourceManager.getInstance().getTexture(textureID+2);
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