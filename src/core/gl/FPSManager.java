package core.gl;

import core.math.Time;
import core.utils.Defaults;

public class FPSManager {
	
	static float fps = 0;
	static float frames = 0;
	static float time = 0.0001f;
	
	public static boolean shouldDrawFrame(){
		return fps < Defaults.FPS_CAP;
	}
	
	public static void startFrame(){
		if(time >= 3f){
			time = 0;
			frames = 0;
		}
		Time.loop();
	}

	public static void endFrame(){
		frames++;
		time += Time.getDeltaSeconds();
		fps = (float) frames / time;
	}

	public static int getFPS() {
		return (int)fps;
	}
	
	@Override
	public String toString(){
		return String.format("FPS: %i", (int)fps);
	}
}
