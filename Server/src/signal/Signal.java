/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author naggar
 */
public class Signal implements Serializable {
    //  1 = sign in , 2 = sign up , 3 = send requestGame to server , 4 = sending invitation response 
    // 4 = sign out , 5 = sendChat msg
    //  6 = send respond to invitation, 7 = close connection , 
    public int signalID;
    public boolean operationCondition;
    public ArrayList<Player> players = new ArrayList<Player>();
    public String userName;
    public String opponent;
    public String pwd;
    public String chatMsg;
    public int winnerscore;   // add winner score
    public int loserscore;  //add loser score
    public String returnData;
    public boolean invitationRespond;
//    public Player dataplayer;
    public int move;
   public int score;
   public String status;
   public String gameresult;
//    public ArrayList<Player> playersList =  new ArrayList<>();
    public int movesArray[] = new int[9];
    
//    public Signal(int signalID ){
//        this.signalID = signalID;
//    }
    
    public void setRequestID (int requestID){
        this.signalID = requestID;
    }
    
    public int getRequestID(){
        return signalID;
    }
    public void setUserName (String userName){
        this.userName = userName;
    }
    
    
     public  ArrayList<Player>   getplayers(){
        return players;
    }
    public  void setplayers ( ArrayList<Player> players){
        this.players = players;
    }
    
    public String getUserName (){
        return userName;
    }
    public void setPassword (String pwd){
        this.pwd = pwd;
    }
    public String getPassword (){
        return pwd;
    }
    
}
