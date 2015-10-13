package enjine.core.gl;

import enjine.core.math.Time;
import enjine.core.utils.Defaults;

public class FPSManager {
	
	static float fps = 0;
	static float frames = 0;
	static float time = 0.0001f;
	
	static Time timer = new Time();
	
	public static boolean shouldDrawFrame(){
		return fps < Defaults.FPS_CAP;
	}
	
	public static void startFrame(){
		if(time >= 3f){
			time = 0;
			frames = 0;
		}
		timer.loop();
	}

	public static void endFrame(){
		frames++;
		time += timer.getDeltaSeconds();
		fps = (float) frames / time;
	}

	public static int getFPS() {
		return (int)fps;
	}
	
	@Override
	public String toString(){
		return "FPS: " + (int)fps;
	}
}
