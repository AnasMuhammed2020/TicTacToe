/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import signal.Player;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import signal.Signal;
//import jdk.internal.org.objectweb.asm.tree.MultiANewArrayInsnNode;

/**
 *
 * @author DOAA
 */

  
                        
     

public class OnlinePlayers extends Thread{


 
     public ArrayList<Player> players;
    Signal signal;
    Connection con;

    public OnlinePlayers(Connection con) throws SQLException {
//        Database db =new Database();
//        
//        this.signal=new Signal();
       
this.con=con;
//        this.players=db.getAll(con);
//        System.out.println(this.players);
//        signal.signalID=3;
//        signal.players=this.players;
        //System.out.println(signal.players.get(0).name+"hellllllllo");
        
//        for (Player player : players)
//        {
//            playersMap.put(player.name, player.status);
//        }
        
        
        
        
//        this.onlineplayers.stream().forEach((internal) -> {
//            this.PlayersList.add(internal.playername);
//        });
            //System.out.println("cvcvbdd"+PlayersList.get(0));
        
    }
     
public void run()
{

        while (true) {  
            try {
                Database db =new Database();
                
                this.signal=new Signal();
                
                
                this.players=db.getAll(this.con);
                System.out.println(this.players);
                
                signal.signalID=3;
                signal.players=this.players;
                sendMessageToAll(signal);
            } catch (SQLException ex) {
                Logger.getLogger(OnlinePlayers.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
       
       

}
void sendMessageToAll(Signal signal)
{
    
     
       
             for(PlayersSockets internal : PlayersSockets.myinternalSocket)
{          
   
                 try {
                     System.out.println("hell befor signal");
                     internal.objectOutputStream.writeObject(signal);
                     System.out.println("after sif=gnal");
                 } catch (IOException ex) {
                     Logger.getLogger(OnlinePlayers.class.getName()).log(Level.SEVERE, null, ex);
                 }
           
         
}
         try {
             this.sleep(10000);
         } catch (InterruptedException ex) {
             Logger.getLogger(OnlinePlayers.class.getName()).log(Level.SEVERE, null, ex);
         }
        
}
     
}
