package enjine.core;

import java.io.IOException;
import java.net.UnknownHostException;

import braynstorm.commonlib.Logger;
import enjine.core.utils.Common;
import game.network.Network;

public class Start {
	
	//public static Thread engineThread;
	//public static Engine engine;
	
	//public static boolean f = true;
	
	public static void main(String[] args) throws UnknownHostException, IOException{
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
		Logger.init(Common.gameFolder);
	    
	    Network network = new Network();
	    Thread networkThread = new Thread(network);
	    networkThread.start();
	    
	    try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	    synchronized (networkThread) {
	        for(byte i=0;i < 2; i++)
	            network.sendData(new byte[]{i});
        }
	    
	    
	    //Enjine.init();
		//Enjine.start();
		//Enjine.destroy();
	}
	
}
