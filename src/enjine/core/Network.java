package enjine.core;

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

import braynstorm.commonlib.Logger;

public class Network implements Runnable {
    
    public List<ByteBuffer> dataToSend = Collections.synchronizedList(new ArrayList<ByteBuffer>());
    private Selector selector;
    
    public void sendData(ByteBuffer buffer){
        synchronized (dataToSend) {
            dataToSend.add(buffer);
        }
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

    private void read(SelectionKey key) {
        long timeStartedReading = System.currentTimeMillis();
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer header = ByteBuffer.allocate(Short.BYTES * 2);
        
        try {
            while(header.remaining() > 0){
                channel.read(header);
                if(timeStartedReading > 500L + System.currentTimeMillis()){
                    // Disconnect because of too much ping.
                    channel.socket().close();
                    channel.close();
                    return;
                }
            }
            
            header.flip();
            ByteBuffer data = ByteBuffer.allocate(header.getShort(2));
            
            while(data.remaining() > 0){
                channel.read(data);
                if(timeStartedReading > 1500L + System.currentTimeMillis()){
                    // Disconnect because of too much ping.
                    channel.socket().close();
                    channel.close();
                    return;
                }
            }
            
            header.flip();
            
            Logger.logInfo(header.array());
            Logger.logInfo(data.array());
            
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void write(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        
        synchronized (dataToSend) {
            Iterator<ByteBuffer> it = dataToSend.iterator();
            
            if(it.hasNext()){
                System.out.println("Writiing..");
                try {
                    socketChannel.write(it.next());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                it.remove();
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
