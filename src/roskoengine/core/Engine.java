package roskoengine.core;

public class Engine implements Runnable{

	@Override
	public void run() {
		RoskoEngine.init();
		RoskoEngine.start();
		RoskoEngine.destroy();
	}

}
