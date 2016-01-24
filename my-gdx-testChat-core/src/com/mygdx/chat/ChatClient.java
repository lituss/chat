package com.mygdx.chat;


	

	

	import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
	import com.esotericsoftware.kryonet.Connection;
	import com.esotericsoftware.kryonet.Listener;

	import com.esotericsoftware.minlog.Log;
import com.mygdx.chat.Network.ChatMessage;
import com.mygdx.chat.Network.RegisterName;
import com.mygdx.chat.Network.UpdateNames;

	

	public class ChatClient implements Runnable{
	        FrameChat frameChat;
	        Client client;
	        String name;
	        Thread myThread = Thread.currentThread();
	        public EnviaD enviaD;

	        public  ChatClient (FrameChat frameChat) {
	        	this.frameChat = frameChat;
	        	enviaD = new EnviaD();
	        	//enviaD.start();
	        }
	        public void run(){
	        	myThread = Thread.currentThread();
	        		
	                client = new Client();
	                client.start();

	                // For consistency, the classes to be sent over the network are
	                // registered by the same method for both the client and server.
	                Network.register(client);

	                client.addListener(new Listener() {
	                        public void connected (Connection connection) {
	                                RegisterName registerName = new RegisterName();
	                                registerName.name = frameChat.getName();
	                                client.sendTCP(registerName);
	                        }

	                        public void received (Connection connection, Object object) {
	                                if (object instanceof UpdateNames) {
	                                        final UpdateNames updateNames = (UpdateNames)object;
	                                        new Thread(new Runnable(){
	                                        	
	                                        	@Override
	                                        	public void run(){
	                                        	Gdx.app.postRunnable(new Runnable(){
	                                        		@Override
	                                        		public void run(){
	                                        			frameChat.setNames(updateNames.names);
	                                        		}
	                                        	});}}).start();
	                                        
	                                        return;
	                                }

	                                if (object instanceof ChatMessage) {
	                                        final ChatMessage chatMessage = (ChatMessage)object;
	                                        new Thread(new Runnable(){
	                                        	@Override
	                                        	public void run(){
	                                        		Gdx.app.postRunnable(new Runnable(){
	                                        			@Override
	                                        			public void run(){
	                                        				frameChat.addMessage(chatMessage.text);
	                                        	}});}}).start();
	                                        
	                                        return;
	                                }
	                        }

	                        public void disconnected (Connection connection) {
	                                frameChat.addMessage("Desconectat");
	                                desconecta();
	                        }
	                });

	               

	                
	               
	     

	                // We'll do the connect on a new thread so the ChatFrame can show a progress bar.
	                // Connecting to localhost is usually so fast you won't see the progress bar.
	                new Thread("Connect") {
	                        public void run () {
	                                try {
	                                        client.connect(5000, Network.host, Network.port);
	                                        // Server communication after connection can go here, or in Listener#connected().
	                                } catch (IOException ex) {
	                                        ex.printStackTrace();
	                                        System.exit(1);
	                                }
	                        }
	                }.start();
	        }
	        
	        
	        public void desconecta(){
	        	client.stop();
	        }
	        public void setFrameChat(FrameChat frameChat){
	        	this.frameChat = frameChat;
	        }
	        public Thread getThread(){return myThread;}
	        
	        public void envia (String message){
	        ChatMessage chatMessage = new ChatMessage();
			chatMessage.text = message;
			client.sendTCP(chatMessage);
	        }
	        
	        public class EnviaD extends Thread{
	        	Array<String>buffer = new Array();
	        	Thread threadEnviaD;
	        	
	        	public void envia (String text){
	        		buffer.add(text);
	        		threadEnviaD.notify();
	        		}
	        	public void run(){tasca();}
	        	
	        	public synchronized  void tasca(){
	        		threadEnviaD = Thread.currentThread();
	        		while (true){
	        			
	        			while (buffer.size > 0){
	        				ChatMessage chatMessage = new ChatMessage();
	        				chatMessage.text = buffer.pop();
	        				client.sendTCP(chatMessage);
	        			}
	        			try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        	}
	        }
	        
}
