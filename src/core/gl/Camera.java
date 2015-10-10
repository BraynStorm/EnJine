package core.gl;

import org.lwjgl.glfw.GLFW;

import com.google.common.eventbus.Subscribe;

import core.event.EventManager;
import core.event.Mouse;
import core.event.types.KeyEvent;
import core.event.types.MouseButtonEvent;
import core.event.types.ScrollEvent;
import core.math.Matrix4f;
import core.math.Vector2i;
import core.math.Vector3f;
import core.utils.Common;
import core.utils.Defaults;
import core.gl.Shader;

public class Camera {
	
	public static final Vector3f Y_AXIS = new Vector3f(0,1,0);
	
	private boolean lockRotX;
	private boolean lockRotY;
	private boolean lockPos;
	
	private Vector3f pos;
	private Vector3f up;
	private Vector3f forward;
	
	private Matrix4f translationMatrix;
	private Matrix4f rotationMatrix;
	
	private float xRot;
	private float yRot;
	
	private static final float MAX_XROT = 88.5f;
	private static final float MIN_XROT = -MAX_XROT;
	
	private boolean[] keysPushed = new boolean[400];
	private boolean mouseDown;
	private Vector2i mouseEnterPoint = new Vector2i(0, 0);
	private Mouse mouse;
	
	public Camera(){
		pos = new Vector3f(0,0,0);
		forward = new Vector3f(0,0,1);
		up = Y_AXIS;
		
		
		xRot = 0;
		yRot = 0;
		
		EventManager.register(this);
		mouse = EventManager.getMouse();
		
		recalcTranslationMatrix();
		recalcRotationMatrix();
	}
	
	public void setLock(boolean v){
		lockRotX = v;
		lockRotX = v;
		lockPos = v;
	}
	
	@Subscribe
	public void mouseClicked(MouseButtonEvent event){
		if(!MouseButtonEvent.guiClicked){
			mouseEnterPoint.x = event.x;
			mouseEnterPoint.y = event.y;
			mouseDown = (event.action == 1 && event.button == 0);
		}else{
			mouseDown = false;
		}
	}
	
	@Subscribe
	public void keyPushed(KeyEvent event){
		if(event.key >= 0)
			keysPushed[event.key] = (event.action == GLFW.GLFW_REPEAT || event.action == GLFW.GLFW_PRESS);
	}
	
	//@Subscribe
	public void scrollToChangeSpeed(ScrollEvent event){
		Defaults.CAMERA_ROTATION_SPEED += event.y * 0.1f;
		System.out.println("New setting, movementSpeed : " + Defaults.CAMERA_ROTATION_SPEED);
	}
	
	public void setTranslationMatrix(){
		Shader.currentlyBound.setUniform("camera_translation", translationMatrix);
	}
	
	public void setRotationMatrix(){
		Shader.currentlyBound.setUniform("camera_rotation", rotationMatrix);
	}
	
	public void loop(){
		setRotationMatrix();
		setTranslationMatrix();
		
		/* Keyboard */
		if(!isPosLocked()){
			if(keysPushed[GLFW.GLFW_KEY_W]){
				this.moveForward(Defaults.CAMERA_MOVEMENT_SPEED);
			}
			
			if(keysPushed[GLFW.GLFW_KEY_S]){
				this.moveBackward(Defaults.CAMERA_MOVEMENT_SPEED);
			}
			
			if(keysPushed[GLFW.GLFW_KEY_A]){
				this.moveLeft(Defaults.CAMERA_MOVEMENT_SPEED);
			}
			
			if(keysPushed[GLFW.GLFW_KEY_D]){
				this.moveRight(Defaults.CAMERA_MOVEMENT_SPEED);
			}
			
			if(keysPushed[GLFW.GLFW_KEY_SPACE]){
				this.move(Y_AXIS, Defaults.CAMERA_MOVEMENT_SPEED);
			}
			
			if(keysPushed[GLFW.GLFW_KEY_X]){
				this.move(Y_AXIS, -Defaults.CAMERA_MOVEMENT_SPEED);
			}
			
			if(keysPushed[GLFW.GLFW_KEY_F]){
				lockRotY = !lockRotY;
			}
		}
		
		/* Mouse */
		if(mouseDown){
			if(!isRotXLocked())
				rotateX((float)(mouse.getY() - mouseEnterPoint.y)
						* Defaults.CAMERA_ROTATION_SPEED, true);
			
			if(!isRotYLocked())
				rotateY((float)(mouse.getX() - mouseEnterPoint.x)
						* Defaults.CAMERA_ROTATION_SPEED);
			
			mouseEnterPoint.x = mouse.getX();
			mouseEnterPoint.y = mouse.getY();
		}
	}
	
	public boolean isRotXLocked() {
		return lockRotX;
	}

	public void setRotXLock(boolean lockRotX) {
		this.lockRotX = lockRotX;
	}

	public boolean isRotYLocked() {
		return lockRotY;
	}

	public void setRotYLock(boolean lockRotY) {
		this.lockRotY = lockRotY;
	}

	public boolean isPosLocked() {
		return lockPos;
	}

	public void setPosLock(boolean lockPos) {
		this.lockPos = lockPos;
	}

	public void move(Vector3f dir, float amount){
		pos = pos.add(dir.mul(amount));
		recalcTranslationMatrix();
	}
	
	public void moveForward(float amount){
		pos = pos.add(forward.mul(amount));
		recalcTranslationMatrix();
	}
	
	public void moveBackward(float amount){
		pos = pos.add(forward.getInverted().mul(amount));
		recalcTranslationMatrix();
	}
	
	public void moveLeft(float amount){
		pos = pos.add(getRight().mul(amount));
		recalcTranslationMatrix();
	}
	
	public void moveRight(float amount){
		pos = pos.add(getLeft().mul(amount));
		recalcTranslationMatrix();
	}
	
	public Vector3f getLeft(){
		return up.cross(forward).normalize(); // deabtable
	}
	
	public Vector3f getRight(){
		return getLeft().invert();
	}
	
	public Vector3f getForward(){
		return forward;
	}
	
	public Vector3f getBackward(){
		return forward.getInverted();
	}
	
	public void rotateX(float angle, boolean respectLimits){
		if(angle == 0f)
			return;
		
		if(respectLimits){
			
			if (!Common.isClamped(angle + xRot, MIN_XROT, MAX_XROT)){
				if(angle > 0){
					angle = MAX_XROT - xRot;
				}else{
					angle = MIN_XROT - xRot;
				}
			}
			
		}
		
		if(angle == 0f)
			return;
		
		xRot += angle;
		
		
		Vector3f horizontalAxis = Y_AXIS.cross(forward).normalize();
		
		forward.rotate(angle, horizontalAxis).normalize();
		up = forward.cross(horizontalAxis).normalize();
		
		recalcRotationMatrix();
	}
	
	public void rotateY(float angle){
		
		if(angle == 0f)
			return;
		
		yRot += angle;
		
		Vector3f horizontalAxis = Y_AXIS.cross(forward).normalize();
		
		forward.rotate(angle, Y_AXIS).normalize();
		up = forward.cross(horizontalAxis).normalize();
		
		recalcRotationMatrix();
	}
	
	private void recalcTranslationMatrix(){ translationMatrix = getTranslationMatrix(); }
	private void recalcRotationMatrix(){ rotationMatrix = getRotationMatrix(); }
	
	public Matrix4f getTranslationMatrix(){
		return new Matrix4f().translate(pos.getInverted());
	}
	
	public Matrix4f getRotationMatrix(){
		return new Matrix4f().camera(forward, up);
	}
	
	public Vector3f getPosition(){
		return pos;
	}
	
	public float getRotationX(){
		return xRot;
	}
	
	public float getRotationY(){
		return yRot;
	}
}
