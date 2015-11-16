package game.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import braynstorm.commonlib.Logger;
import braynstorm.commonlib.network.PacketSize;
import braynstorm.commonlib.network.PacketType;

public class Network implements Runnable {
    
    public ConcurrentLinkedQueue<ByteBuffer> dataToSend = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedDeque<Byte> incomingData = new ConcurrentLinkedDeque<>();
    private ByteBuffer tempReadBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer tempProcessBuffer = ByteBuffer.allocate(1024);
    
    private Selector selector;
    
    public void sendData(ByteBuffer buffer){
        synchronized (dataToSend) {
            dataToSend.add(buffer);
        }
    }
    
    private boolean tryProcessPacket(){
    	
    	if(!incomingData.isEmpty()){
    		tempProcessBuffer.put(incomingData.poll()); // Type
    		tempProcessBuffer.put(incomingData.poll()); // Type
    		tempProcessBuffer.put(incomingData.poll()); // Len
    		tempProcessBuffer.put(incomingData.poll()); // Len
    		tempProcessBuffer.flip();
    		
    		short type = tempProcessBuffer.getShort();
    		short len = tempProcessBuffer.getShort();
    		
    		if(len >= incomingData.size()){
    			tempProcessBuffer.clear();
    			
    		}
    	}
    		
    	
    	return false;
    }
    
    @Override
    public void run() {
        SocketChannel channel;
        
        try{
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.configureBlocking(false);
            
            channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE);
            channel.connect(new InetSocketAddress("localhost", 33055));
        } catch (IOException e){
            e.printStackTrace();
        }
        
        while (!Thread.interrupted()){
            try { selector.select(1000); } catch (IOException e) { e.printStackTrace(); continue; }
             
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while (keys.hasNext()){
                SelectionKey key = keys.next();
                keys.remove();

                if (!key.isValid()){
                    System.out.println("Invalid");
                    return;
                }

                if (key.isConnectable()){
                    connect(key);
                }
                
                if (key.isWritable()){
                    write(key);
                }
                
                if (key.isReadable()){
                    read(key);
                }
            }
        }
        
    }
    
    void read(SelectionKey key){
    	SocketChannel channel = (SocketChannel) key.channel();
    	
        try {
            if(channel.read(tempReadBuffer) == 0)
                return;
            
            tempReadBuffer.flip();
            
            while(tempReadBuffer.hasRemaining())
            	incomingData.add(tempReadBuffer.get());
            
            while(tryProcessPacket());
            
            tempReadBuffer.clear();
        } catch (IOException e) {
            Logger.logExceptionInfo(e);
            // TODO close the connection
        }
        
        // Check for amount of data read.
        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        
        
    }

    private void write(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        
        //FIXME Potential need of synchronization.
    	while(!dataToSend.isEmpty()){
    		try {
                socketChannel.write(dataToSend.poll());
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
        
        key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }

    private void connect(SelectionKey key) {
        System.out.println("connecting");
        SocketChannel channel = (SocketChannel) key.channel();
        
        try {
            if(channel.isConnectionPending()){
                channel.finishConnect();
            }
            
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(byte[] bytes) {
        sendData(ByteBuffer.wrap(bytes));
    }
    
}
