package server;

import signal.Player;
import java.io.FileOutputStream;
import signal.Signal;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.derby.iapi.error.StandardException;
//import org.apache.derby.iapi.sql.ResultSet;

class RequestHandler extends Thread {

    Socket newSocket;
    ObjectInputStream objectInputStream;
    InputStream inputStream;
    RequestProtocol protocol;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    Connection conn;
    Database db = new Database();
    Response res;
    PlayersSockets ps;
    PlayersSockets player1;
    PlayersSockets player2;
    static String turn = "X";
    static int playerX = 0;
    static int playerY = 0;
    Vector<Player> players = new Vector<Player>();
    ArrayList<Player> allplayers = new ArrayList<Player>();

    RequestHandler(Socket newSocket, InputStream inputStream, ObjectInputStream objectInputStream, OutputStream outputStream, ObjectOutputStream objectOutputStream, Connection conn) {
        this.newSocket = newSocket;
        this.protocol = protocol;
        this.conn = conn;
        this.ps=new PlayersSockets();
        this.inputStream = inputStream;
        this.objectInputStream = objectInputStream;
        this.outputStream = outputStream;
        this.objectOutputStream = objectOutputStream;
        this.ps= new PlayersSockets(newSocket, inputStream, objectInputStream, outputStream, objectOutputStream, " ");

        System.out.println("Request Handler constructor");
    }

    public void terminateConnection() throws IOException {
        objectInputStream.close();
        inputStream.close();
        objectOutputStream.close();
        outputStream.close();
        newSocket.close();
    }

    public void terminateConnection(PlayersSockets player) throws IOException {
        player.objectInputStream.close();
        player.inputStream.close();
        player.objectOutputStream.close();
        player.outputStream.close();
        player.cs.close();
    }

    @Override
    public void run() {

        System.out.println("Request Handler");
        while (true) {
            try {
                Signal response = new Signal();
                System.out.println("Before recieving");
                response = (Signal) objectInputStream.readObject();
                System.out.println(response.signalID);
                System.out.println(response.userName);
                System.out.println(response.pwd);
                System.out.println("After recieving");
                ps.playername=response.userName;

                switch (response.signalID) {
                    case 2: {
                        
                        System.out.println("signing up");
                        Player newPlayer = new Player();
                        newPlayer.name = response.userName;
                        newPlayer.password = response.pwd;
                        newPlayer.score = db.getScore(newPlayer.name, conn);
//                                response.dataplayer;
                        boolean isSignup = db.checkSignup(newPlayer, conn);
                        if (isSignup == true) {
                            System.out.println("is not exist and save in db");
                            res = new Response(1, "true");
               
                            Signal res = new Signal();
                            res.signalID = 2;
                            res.operationCondition = true;
                        
                               
                                objectOutputStream.writeObject(res);
                                this.ps.playername=response.userName;
                                PlayersSockets.pushPlayers(this.ps);
                                  
                               
                   
                                System.out.println(res.signalID);
                                System.out.println(res.operationCondition);
                              
                              
                           

                              
                        } else {
                            System.out.println("is  exist");
//                            res = new Response(1, "false");
                            Signal res = new Signal();
                            res.signalID = 2;
                            res.operationCondition = false;
                            try {
                                //
//
//                        }
                                System.out.println(res.signalID);
                                System.out.println(res.operationCondition);
                                objectOutputStream.writeObject(res);
                            } catch (IOException ex) {
                                System.out.println("Cant write");
                            }
//                                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
//
                        break;
                    }

                    case 1: {
                        System.out.println("signing in");
                        Player newPlayer = new Player();
                        newPlayer.name = response.userName;
                        newPlayer.password = response.pwd;
                        newPlayer.score = db.getScore(newPlayer.name, conn);
                       
                        if (db.checkLogin(newPlayer, conn) == true) {
                            System.err.println(db.updatetoOnline(newPlayer.name, conn));
                            //db.updatetoOnline(newPlayer.name, conn);
//                            ps.playername=response.userName;
                                //this.ps= new PlayersSockets(newSocket, inputStream, objectInputStream, outputStream, objectOutputStream,response.userName);
                               this.ps.playername=response.userName;
                                PlayersSockets.pushPlayers(this.ps);
                          

// ps = new PlayersSockets(this.newSocket, this.inputStream, this.objectInputStream, this.outputStream, this.objectOutputStream, protocol.dataPlayer.name);
//                            try {
//                                players=db.getAll(conn);
//                                // new OnlinePlayers(PlayersSockets.myinternalSocket,players);
//                            } catch (SQLException ex) {
//                                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                            Signal res = new Signal();
                            res.signalID = 1;
                            res.operationCondition = true;
                            res.userName=response.userName;
                            objectOutputStream.writeObject(res);
                            

                        } else {
//                            res = new Response(2, "false");
                            Signal res = new Signal();
                            res.signalID = 1;
                            res.operationCondition = false;
                            objectOutputStream.writeObject(res);
//                                objectOutputStream.writeObject(res);
                        }

                        break;
                    }

                    case 4: {
                        System.out.println("sending invitation");
                        String destinationPlaer = response.opponent;
                         String sourcePlayer = response.userName;
                        
                        for (PlayersSockets internal : PlayersSockets.myinternalSocket) {
                            Signal signal=new Signal();
                            signal.userName=response.userName;
                            signal.opponent=response.opponent;
                            System.out.println(response.opponent);
                            System.out.println(response.userName);
                            signal.signalID=4;
                            internal.objectOutputStream.writeObject(response);
                            System.out.println("after send res 4");

                        }
                        
                        break;
                    }
                    case 5: {
                        System.out.println("true");
                        String destinationPlaer = response.opponent;
                        String sourcePlayer = response.userName;
                        boolean isaccept = response.invitationRespond;
                        String[] initCells = null;
                        for (PlayersSockets internal : PlayersSockets.myinternalSocket) {

                            if (isaccept == true) {
                                //set state busy
                                Signal signal = new Signal();
                                signal.signalID = 5;
                                signal.invitationRespond = true;
                                signal.userName = sourcePlayer;
                                signal.opponent = destinationPlaer;

                                db.updatetoBusy(destinationPlaer, conn);
                                db.updatetoBusy(sourcePlayer, conn);
                                int player1ID = db.getIdByName(destinationPlaer, conn);
                                int player2ID = db.getIdByName(sourcePlayer, conn);
                                try { //set game in db
                                    boolean isstart = db.StartGame(player1ID, player2ID, conn);
                                    playerX = player2ID; //opponent
                                    playerY = player1ID; //sender

                                    turn = "X";
                                    //set game in db

                                } catch (SQLException ex) {
                                    Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                try {
                                    internal.objectOutputStream.writeObject(response);
                                } catch (IOException ex) {
                                    Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } else {
                                Signal signal = new Signal();
                                signal.signalID = 5;
                                signal.invitationRespond = false;
                                signal.userName = sourcePlayer;
                                signal.opponent = destinationPlaer;

                                try {
                                    internal.objectOutputStream.writeObject(response);
                                } catch (IOException ex) {
                                    Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                        //send res
                        //edit this

                        break;

                    }
                        
                       
                    
                    
                
                    case 6: {

                        String[] cellsArray;
                        System.out.println("game select");
                        // String player1Name = protocol.dataPlayer.name;
                        String player1Name = response.userName;
                        String player2Name = response.opponent;
                        System.out.println(response.userName+" username in case 6");
                        System.out.println(response.opponent+" opponent in case 6");
                        int winnerScore;
                        int loserScore;
                        //ps = new PlayersSockets(newSocket, this.inputStream, this.objectInputStream, this.outputStream, this.objectOutputStream, protocol.dataPlayer.name);
                        int id1 = db.getIdByName(player1Name, conn);
                        try {
                            GAME game = db.getgameid(id1, conn);
                            int id2;
                            int gameid = game.id;
                            if (game.id1 == id1) {
                                id2 = game.id2;
                            } else {
                                id2 = game.id1;
                            }
                            //  String player2Name = db.getPlayerById(id2, conn);
                            for (PlayersSockets internal : PlayersSockets.myinternalSocket) {
                                
                            
                            cellsArray = game.cells.split(",");
                            cellsArray[response.move] = turn;
                            String cells = String.join(",", cellsArray);
                            //save to db

                            db.updatemove(gameid, cells, conn);

                            //checkwin
                            String winner = game.checkWinner(cellsArray);
//                            int draw = 0;
//                            for(int i =0; i<winner.length();i++){
//                            System.out.println(winner.charAt(i)+"  winner");
//
//                                if(winner.charAt(i)==('X')){
//                                    draw+=1;
//                                }
//                            }
                            if (winner == "X") {
//                                String gamersult="win"
                                Signal signal2 = new Signal();
                                winnerScore = db.updateScore(player1Name, conn);
                                loserScore = db.getScore(player2Name, conn);
                                signal2.signalID = 7;
                                signal2.userName = player1Name;
                                signal2.opponent = player2Name;
                                signal2.winnerscore = winnerScore;
                                signal2.loserscore = loserScore;
                                signal2.gameresult = "win";
                                internal.objectOutputStream.writeObject(signal2);
                               // internal.objectOutputStream.writeObject(signal2);
                                
                               
                            } else if (winner == "O") {

                                winnerScore = db.updateScore(player2Name, conn);
                                loserScore = db.getScore(player1Name, conn);

                                Signal signal2 = new Signal();
                                winnerScore = db.updateScore(player2Name, conn);
                                loserScore = db.getScore(player1Name, conn);
                                signal2.signalID = 7;
                                signal2.userName = player1Name;
                                signal2.opponent = player2Name;
                                signal2.winnerscore = winnerScore;
                                signal2.loserscore = loserScore;
                                signal2.gameresult = "win";
                                internal.objectOutputStream.writeObject(signal2);
                                //internal.objectOutputStream.writeObject(signal2);
                                
                                
                                
                            } else{
                                Signal signal2 = new Signal();
                                winnerScore = db.updateScore(player1Name, conn);
                                loserScore = db.getScore(player2Name, conn);
                                signal2.signalID = 7;
                                signal2.userName = player1Name;
                                signal2.opponent = player2Name;
                                signal2.winnerscore = winnerScore;
                                signal2.loserscore = loserScore;
                                signal2.gameresult = "draw";
                                //internal.objectOutputStream.writeObject(signal2);
                                //internal.objectOutputStream.writeObject(signal2);
                                
                            }
//                            else{
//                                System.out.println("else in server");
//                            }

                            if (turn == "X") {
                                Signal signal2= new Signal();
                                signal2.signalID = 6;
                                signal2.userName = player1Name;
                                signal2.opponent = player2Name;
                                signal2.move=response.move;
                                internal.objectOutputStream.writeObject(response);
//                                res = new Response(6, "updateBoard", player1Name,winnerScore,loserScore, cellsArray, winner);
//                                player2.objectOutputStream.writeObject(res);
                                turn = "O";
                            }
                            if (turn == "O") {
//                                res = new Response(6, "updateBoard", player2Name,winnerScore,loserScore, cellsArray, winner);
//                                player1.objectOutputStream.writeObject(res);
                                Signal signal2= new Signal();
                                signal2.signalID = 6;
                                signal2.userName = player1Name;
                                signal2.opponent = player2Name;
                                signal2.move=response.move;
                                internal.objectOutputStream.writeObject(response);
                                turn = "X";
                            }
                            turn = "O";
                            }
                            // System.out.println(cellsArray[0]);
                        } catch (SQLException ex) {
                            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    }
                
//                    case 7//check gameover: {
//
//                    }
//                    case 8://sendChat name,opp,mess {
//
//                    }
//                    case 9: {
//                        System.out.println("pause");
//                        String[] cellsArray;
//                        GAME game;
//                        int gameid;
//                        int player2id;
//                        PlayersSockets player1 = null;
//                        PlayersSockets player2=null;
//                        String player2Name;
//                        boolean isGameSaved;
//                         Response res;
//                        String player1Name=protocol.playerName;
//                        cellsArray=protocol.cellsArray;
//                        int player1Id=db.getIdByName(player1Name, conn);
//                    try {
//                        game=db.getgameid(player1Id, conn);
//                        gameid=game.id;
//                        String cells = String.join(",", cellsArray);
//                       isGameSaved= db.updatemove(gameid, cells, conn);
//                     
//                        if(game.id1!=player1Id)
//                        {
//                            player2id=game.id1;
//                            player2Name=db.getPlayerById(player2id, conn);
//                        }
//                        else
//                        {
//                            player2id=game.id2;
//                             player2Name=db.getPlayerById(player2id, conn);
//                        }
//                        
//                         for (PlayersSockets internal : PlayersSockets.myinternalSocket) {
//                                if (internal.playername == player1Name) {
//                                    player1 = internal;
//                                }
//                                if (internal.playername == player2Name) {
//                                    player2 = internal;
//                                } else {
//                                    player1 = null;
//                                    player2 = null;
//                                }
//                            }
//                        if (isGameSaved == true) {
//                            res = new Response(9, "true");
//                        } else {
//                            res = new Response(9, "false");
//
//                        }
//                        player1.objectOutputStream.writeObject(res);
//                        player2.objectOutputStream.writeObject(res);
//
//
//                        
//                    } catch (SQLException ex) {
//                        Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        
//                    }
//                    case 10: {  //resume
//                        System.out.println("resume");
//                        String player1Name;
//                        String player2Name;
//                        Player player2obj;
//                        int player1Id;
//                        int player2Id;
//                        PlayersSockets player1=null;
//                        PlayersSockets player2=null;
//                        GAME game;
//                        Response res;
//                        String cells;
//                        String [] cellsArray = null;
//                        player1Name=protocol.playerName;
//                        player2Name=protocol.player2;
//                        player1Id= db.getIdByName(player1Name, conn);
//                        player2Id= db.getIdByName(player2Name, conn);
//                        player2obj= db.getPlayerBYNUMBER(player2Id, conn);
//                        if(player2obj.status=="online")
//                        {
//                            try {
//                                game=db.getgameid(player1Id, conn);
//                                cells=game.cells;
//                                cellsArray=cells.split(",");
//                                
//                                
//                                  for (PlayersSockets internal : PlayersSockets.myinternalSocket) {
//                                if (internal.playername == player1Name) {
//                                    player1 = internal;
//                                }
//                                if (internal.playername == player2Name) {
//                                    player2 = internal;
//                                } else {
//                                    player1 = null;
//                                    player2 = null;
//                                }
//                            }
//                                
//                                
//                            } catch (SQLException ex) {
//                                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                            res=new Response(10,cellsArray);
//                            player1.objectOutputStream.writeObject(res);
//                            player2.objectOutputStream.writeObject(res);
//                        }
//                        //if offline
//                        else{
//                            res=new Response(10,"false");
//                            player1.objectOutputStream.writeObject(res);
//                            player2.objectOutputStream.writeObject(res);
//                        }
//                        
//                        
//                        
//                    }
                    case 11: { //signout
                        System.out.println("signing out");
                         for (PlayersSockets internal : PlayersSockets.myinternalSocket) {
                              if(internal.playername==response.userName)
                              {
                                  System.out.println("iside if");
//                            res = new Response(11, "true");
                                  Signal signal=new Signal();
                                  signal.signalID=11;
                                  signal.operationCondition=true;
                            internal.objectOutputStream.writeObject(signal);
                            internal.objectInputStream.close();
                            internal.inputStream.close();
                            internal.objectOutputStream.close();
                            internal.outputStream.close();
                            db.updatetoOffline(response.userName, conn);
                            
                            PlayersSockets.myinternalSocket.remove(internal);
                                  System.out.println("signout");
                            //players=db.getAll(conn);
                            // new OnlinePlayers(PlayersSockets.myinternalSocket,players);
                              }
                        }
                    }
                
                    case 12: {
                        System.out.println("exit from server ");
                       ArrayList <Player> allplayers=new ArrayList<Player>();
                        boolean isGamesDeleted;
                    try {
                        allplayers=db.getAll(conn);
                          for (Player player : allplayers) {
                              db.updatetoOffline(player.name, conn);
                              }
                          isGamesDeleted=db.deleteGame(conn);
                           for (PlayersSockets player : PlayersSockets.myinternalSocket) {
                               terminateConnection(player);
                               
                           }
                        
                          
                          
                    } catch (SQLException ex) {
                        Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                    }
                }
//                    default: {
//                        try {
//                            terminateConnection();
//                        } catch (IOException ex) {
//                            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        break;
//                    }
//
//                }
      
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("closing request handler");
                break;
            } catch (SQLException ex) {
                Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        }

    }       
}
