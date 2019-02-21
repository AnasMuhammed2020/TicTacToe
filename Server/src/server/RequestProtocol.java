
package server;
import signal.Player;
import java.io.Serializable;



class RequestProtocol implements Serializable{
     int requestType;
     Player dataPlayer;
     //Player player2;
     String player2;
     String playerName;
     boolean isaccept;
     String []cellsArray;
     int move;

  public RequestProtocol( int requestType,Player dataPlayer) {
        this.dataPlayer=dataPlayer;
       this.requestType=requestType;
    }
  public RequestProtocol( int requestType,String playerName) {
        this.playerName=playerName;
       this.requestType=requestType;
    }
  public RequestProtocol( int requestType,Player dataPlayer,String playerName) {
        this.dataPlayer=dataPlayer;
       this.requestType=requestType;
       this.player2=playerName;
     
    }
  
   public RequestProtocol( int requestType,String player2Name,String player1Name) {
        this.playerName=player1Name;
       this.requestType=requestType;
       this.player2=player2Name;
     
    }
  public RequestProtocol( int requestType,Player dataPlayer,String playerName,boolean isaccept) {
        this.dataPlayer=dataPlayer;
       this.requestType=requestType;
       this.player2=playerName;
       this.isaccept=isaccept;
     
    }
  
   public RequestProtocol( int requestType,String playerName,String []cellsArray) {
        this.playerName=playerName;
       this.requestType=requestType;
       this.cellsArray=cellsArray;
    }
    public RequestProtocol( int requestType,Player dataPlayer,String playerName,int move) {
        this.dataPlayer=dataPlayer;
       this.requestType=requestType;
       this.player2=playerName;
       this.move=move;
     
    }
}