
package server;

import signal.Player;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client{

public static void main(String args[]){
try{
Socket socketConnection = new Socket("localhost",3002);
OutputStream outputStream = socketConnection.getOutputStream();
ObjectOutputStream clientOutputStream = new ObjectOutputStream(outputStream);
InputStream inputStream = socketConnection.getInputStream();
ObjectInputStream clientinpInputStream = new ObjectInputStream(inputStream);
Player playerData=new  Player(2, "yasser", "123");
   
   // RequestProtocol protocol = new RequestProtocol(3, playerData, "doaa");
//    RequestProtocol protocol = new RequestProtocol(4, playerData, "yasser", true);
 RequestProtocol protocol = new RequestProtocol(6, playerData, "yasser", 1);
    System.out.println(protocol.requestType * 200);
    System.out.println("before writing from client");
    clientOutputStream.writeObject(protocol);
        System.out.println("after writing from client");


    Response response = (Response) clientinpInputStream.readObject();
//    for(int i=0;i<response.onlineplayers.size();i++){
//     System.out.println(response.onlineplayers.get(i));
//    }
      // ArrayList<String> PlayersList = new ArrayList<String>();
//    System.err.println(response.responceBody);
//    System.err.println(response.playerSendinvitation);
//protocol.player2.name="yasser";




clientOutputStream.close();
outputStream.close();
socketConnection.close();
}
catch(Exception e){
    System.out.println(e);
}
}
}