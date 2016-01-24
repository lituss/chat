package com.mygdx.chat;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

//This class is a convenient place to keep things common to both the client and server.
public class Network {
     static public final int port = 54555;
     static public final String host = "192.168.2.102";

     // This registers objects that are going to be sent over the network.
     static public void register (EndPoint endPoint) {
             Kryo kryo = endPoint.getKryo();
             kryo.register(RegisterName.class);
             kryo.register(String[].class);
             kryo.register(UpdateNames.class);
             kryo.register(ChatMessage.class);
     }

     static public class RegisterName {
             public String name;
     }

     static public class UpdateNames {
             public String[] names;
     }

     static public class ChatMessage {
             public String text;
     }
}

