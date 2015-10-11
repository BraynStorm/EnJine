package core.math;

public class Time {
	private long lastTime = 0;
	
	
	public void loop(){
		lastTime = System.nanoTime();
	}
	
	public double getDelta(){
		return System.nanoTime() - lastTime;
	}
	
	public double getDeltaSeconds(){
		return getDelta() / 1e9;
	}
	
	public long getNano(){
		return System.nanoTime();
	}
	
	public long getMilli(){
		return (long) (getNano() / 1e6);
	}
}
