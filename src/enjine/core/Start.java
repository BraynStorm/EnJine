package enjine.core;

public class Start {
	
	//public static Thread engineThread;
	//public static Engine engine;
	
	//public static boolean f = true;
	
	public static void main(String[] args){
		/*engine = new Engine();
		engineThread = new Thread(engine);
		engineThread.start();
		
		while(f){
			System.out.println("lol");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		RoskoEngine.stop();*/
		Enjine.init();
		Enjine.start();
		Enjine.destroy();
	}
	
}
