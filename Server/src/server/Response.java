/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author DOAA
 */
   public class Response implements Serializable{
        public int responseId;
        public String responceBody ;// it will  be changed 
        public String playerSendinvitation;
        
        HashMap<String,String> onlineplayers;
        String [] cells;
        String winner;
       public int winnerScore;
       public int loserScore;
        Response(int responseId,String responceBody){
            this.responseId=responseId;
            this.responceBody=responceBody;
        }
        
          Response(int responseId,String responceBody,String playerSendinvitation){
            this.responseId=responseId;
            this.responceBody=responceBody;
            this.playerSendinvitation=playerSendinvitation;
        }
          
               Response(int responseId,HashMap<String,String> onlineplayers){
            this.responseId=responseId;
            this.onlineplayers=onlineplayers;
           
        }
                Response(int responseId,String responceBody,String playerSendinvitation,int winnerScore,int loserScore,String[]cells,String winner){
            this.responseId=responseId;
            this.responceBody=responceBody;
            this.playerSendinvitation=playerSendinvitation;
            this.cells=cells;
            this.winner=winner;
            this.winnerScore=winnerScore;
            this.loserScore=loserScore;
        }
                
                   Response(int responseId,String[]cells){
            this.responseId=responseId;
            this.cells=cells;
           
        }
        
    }