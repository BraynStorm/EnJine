package core.math;

public class Time {
	private static long lastTime = 0;
	
	
	public static void loop(){
		lastTime = System.nanoTime();
	}
	
	public static double getDelta(){
		return System.nanoTime() - lastTime;
	}
	
	public static double getDeltaSeconds(){
		return getDelta() / 1000000000;
	}
	
	public static long getNano(){
		return System.nanoTime();
	}
	
	public static long getMilli(){
		return (long) (getNano() / 1e6);
	}
}
