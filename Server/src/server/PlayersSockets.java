/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DOAA
 */
public class PlayersSockets{
       
    public String playername;
    public ObjectInputStream objectInputStream;
   public  InputStream inputStream;
   public OutputStream outputStream;
    public ObjectOutputStream objectOutputStream;
    public Socket cs;
    
    static Vector <PlayersSockets> myinternalSocket=new Vector<PlayersSockets>();

//    public PlayersSockets() {
//    }
//    
    
 public PlayersSockets(){
     
 }   

public PlayersSockets(Socket cs,InputStream inputStream,ObjectInputStream objectInputStream,
        OutputStream outputStream,ObjectOutputStream objectOutputStream,String playername)
{
    this.inputStream = inputStream;
    this.objectInputStream = objectInputStream;
    this.outputStream=outputStream;
    this.objectOutputStream=objectOutputStream;
    this.playername=playername;
    this.cs=cs;
    
    
}
 public static void pushPlayers(PlayersSockets newPlayer)
{
  
    myinternalSocket.add(newPlayer);
}
}