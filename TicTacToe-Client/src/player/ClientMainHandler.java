package player;

import signal.Player;
import signal.Signal;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scenesControllers.PlayerGameController;

public class ClientMainHandler {

    static public Socket mySocket;
    static public ObjectInputStream dis;
    static public ObjectOutputStream ps;
    public static boolean isconnected = false;

    public Button[] btns = {GUIBuilder.gameController.b1, GUIBuilder.gameController.b2, GUIBuilder.gameController.b3,
        GUIBuilder.gameController.b4, GUIBuilder.gameController.b5, GUIBuilder.gameController.b6,
        GUIBuilder.gameController.b7, GUIBuilder.gameController.b8, GUIBuilder.gameController.b9};

    public static Player player;

    public static String player1;
    public static String player2;

//    public ArrayList<Player> playersList = new ArrayList<>();
    public boolean IAmX = false;
    public static boolean myTurn = true;

    // starting connection with the server 
    public boolean startConnection() {
        try {
            System.out.println("Before cresting docket");
            mySocket = new Socket("192.168.1.19", 3008);
            System.out.println("in starting connection");
            ps = new ObjectOutputStream(mySocket.getOutputStream());
            dis = new ObjectInputStream(mySocket.getInputStream());
            isconnected = true;
            System.out.println("Connected to server");
            return true;
        } catch (IOException ex) {
//            Logger.getLogger(ClientMainHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
            System.out.println("Cant connect to server");
            isconnected = false;
            return false;
        }
    }

    //reading from the server  (start communication)
    private void recieveFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isconnected) {
                    try {
//                        System.out.println("Inside the thread");
                        Signal msgFromServer = (Signal) dis.readObject();
                        System.out.println(msgFromServer.signalID + "from recievefrom server  < sigID");
//                        System.out.println(msgFromServer.players.get(0).name + " from thread recieve");
                        parseSignal(msgFromServer);
                    } catch (IOException ex) {
                        System.out.println(ex);
                        System.out.println("Cant read");
                        isconnected = false;
                        break;
                    } catch (ClassNotFoundException cnfex) {
                        System.out.println(cnfex);
                        System.out.println("Cant read");
                    }
                }
                try {
                    mySocket.close();
                    ps.close();
                    dis.close();
                } catch (IOException ex) {
                }
            }
        }).start();
    }

    // handle msg from 
    public void parseSignal(Signal msg) {
        switch (msg.signalID) {
            // case 3 >> recieve all players from server
            case 3:
                System.out.println("Inside parser case 3");
                reciveAllPlayers(msg);
                break;
            case 4:
                System.out.println("Inside parser case 4 .. respond to request");
                respondToRequest(msg);
                break;
            case 5:
                handleResponse(msg);
                break;
            case 6:
                System.out.println("parser ID 6 handle move");
                handleMove(msg);
                break;
            case 7:
                System.out.println("parser 7 game over");
                handleGameOver(msg);
                break;
            case 8:
                recieveChatMsg(msg);
                break;
            case 12:
                terminateConnection();
                break;
            default:
                System.out.println("Default in switch");
                break;
        }
    }

    public boolean requestSigning(int id, Player newplayer) {
        Signal signal = new Signal();
        signal.signalID = id;
//        signal.dataplayer = newplayer;
        signal.userName = newplayer.getUsername();
        signal.pwd = newplayer.getPassword();
        boolean signingResult = false;
        System.out.println(signal.userName + "from client side");
        if (isconnected) {
            sendSignal(signal); // write in stream
            while (isconnected) {
                try {
                    System.out.println("trying to read object from server");
                    Signal response = (Signal) dis.readObject();
                    System.out.println(response.signalID);
                    // waiting to know the object to be send from server ( response.signalID == 1 && condition == true)
                    if (response.signalID == 1 || response.signalID == 2) {
                        if (response.operationCondition == true) {
                            System.out.println("Signed successfully");
                            signingResult = true;
                            player1 = newplayer.name;
                            player = new Player();
                            player.setUsername(response.userName);
                            player.setScore(response.score);
                            recieveFromServer();
                        }
                        break;
                    } else {
                        System.out.println("Different msg");
                        parseSignal(response);
                    }
                } catch (IOException ex) {
                    System.out.println("Error in reading response 1");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error in reading response 2");
                }
            }
        }
        return signingResult;
    }

    // Getting all players list from server 
    public void reciveAllPlayers(Signal sig) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < sig.players.size(); i++) {
                    if (sig.players.get(i).getUsername().equals(player1)) {
                        sig.players.remove(i);
                    }
                }
                GUIBuilder.playersListController.playerNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
                GUIBuilder.playersListController.scoreCol.setCellValueFactory(new PropertyValueFactory<>(("score")));
                GUIBuilder.playersListController.statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

                GUIBuilder.playersListController.playersTable.getItems().setAll(sig.players);
            }
        });

    }

    // player1 send request to server to play with player2
    public void requestGame(int id, String player1, String player2) {
        Signal signal = new Signal();
        signal.signalID = id;
        signal.userName = player1;
        signal.opponent = player2;
        ClientMainHandler.player2 = player2;
        System.out.println(player1 + "in request game fun");
        System.out.println(player2 + "in request game fun");
        if (isconnected) {
            sendSignal(signal);
        }
    }

    // 
    public void respondToRequest(Signal incoming) {
        // opponent gets a game request
        System.out.println("in Respond");
//        System.out.println(incoming.playersList.get(0).getUsername());
        if (incoming.opponent.equals(ClientMainHandler.player1)) {
            ClientMainHandler.player2 = incoming.userName;
            System.out.println(player2 + "from respond to request");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("inside run from respond to request");
                    GUIBuilder.playersListController.showAlert(player2);
                }
            });
        }
    }

    public void sendResponse(boolean response) {
        IAmX = false;
        Signal outgoing = new Signal();
        outgoing.userName = ClientMainHandler.player2;
        outgoing.opponent = ClientMainHandler.player1;
        outgoing.signalID = 5;
        outgoing.invitationRespond = response;
        System.out.println(outgoing.opponent + "opponent in sends response");
        System.out.println(outgoing.userName + " username in send response");
        System.out.println(response);
        if (isconnected) {
            sendSignal(outgoing);
        }
    }

    public void handleResponse(Signal incoming) {
        if (incoming.invitationRespond == true && incoming.userName.equals(ClientMainHandler.player1) && incoming.opponent.equals(ClientMainHandler.player2)) {
            IAmX = true;
            myTurn = true;
            ClientMainHandler.player2 = incoming.opponent;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    GUIBuilder.initialStage.setScene(GUIBuilder.playerGameScene);
                    GUIBuilder.gameController.resetScene();
                    PlayerGameController.img = new Image("scenesImages/x_1.png");
                }
            });
        } else if (incoming.invitationRespond == false && incoming.userName.equals(ClientMainHandler.player1)) {
            //other player rejected request >> Show alert for sende (later )
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Rejection Message");
                    alert.setHeaderText("Game Rejected!");
                    alert.setContentText(player2 + " didn't accept invitation!");
                    alert.showAndWait();
                }
            });
        } else {
            System.out.println("else");
        }
    }

    public void makeAMove(int position) {
        if (ClientMainHandler.myTurn){
            
        System.out.println(ClientMainHandler.player1 + " from make a move  .. " + position);
        System.out.println(ClientMainHandler.player2 + " opponent from make a move");
        myTurn = false;
        Signal moveSignal = new Signal();
        moveSignal.signalID = 6;
        moveSignal.userName = ClientMainHandler.player1;
        moveSignal.opponent = ClientMainHandler.player2;
        moveSignal.move = position;
        sendSignal(moveSignal);
        }
    }

    public void handleMove(Signal signal) {

        System.out.println(signal.userName + " user name from handle move");
        System.out.println(signal.opponent + " opponent from handle move");
        
        if (signal.opponent.equals(ClientMainHandler.player1) && signal.userName.equals(ClientMainHandler.player2)) {
                myTurn = true;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Inside run of handle move");
                    btns[signal.move].setGraphic(new ImageView(IAmX ? "scenesImages/o_1.png" : "scenesImages/x_1.png"));
                    switch (signal.move) {
                        case 0:
                            GUIBuilder.gameController.flag1 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b1);
                            break;
                        case 1:
                            GUIBuilder.gameController.flag2 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b2);
                            break;
                        case 2:
                            GUIBuilder.gameController.flag3 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b3);
                            break;
                        case 3:
                            GUIBuilder.gameController.flag4 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b4);
                            break;
                        case 4:
                            GUIBuilder.gameController.flag5 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b5);
                            break;
                        case 5:
                            GUIBuilder.gameController.flag6 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b6);
                            break;
                        case 6:
                            GUIBuilder.gameController.flag7 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b7);
                            break;
                        case 7:
                            GUIBuilder.gameController.flag8 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b8);
                            break;
                        case 8:
                            GUIBuilder.gameController.flag9 = 1;
                            GUIBuilder.gameController.setDisabled(GUIBuilder.gameController.b9);
                            break;
                    }
                }
            });
        }
    }

    public void handleGameOver(Signal msg) {
        GUIBuilder.initialStage.setScene(GUIBuilder.gameoverScene);
        if (msg.gameresult.equals("win")) {
            if (msg.userName.equals(player1)) {
                GUIBuilder.gameoverController.playerNameLabel.setText("You are the winner :D");
//                    GUIBuilder.gameoverController.gameOverImg;

            } else {
                GUIBuilder.gameoverController.playerNameLabel.setText("ya loser ya faaaaashel :P");
                //          GUIBuilder.gameoverController.gameOverImg;

            }
        } else {
            GUIBuilder.gameoverController.playerNameLabel.setText("Draw game");
//          GUIBuilder.gameoverController.gameOverImg;
        }
    }

    public void sendChatMsg(int id, String senderName, String msg) {
        Signal signal = new Signal();
        signal.signalID = id;
        signal.userName = senderName;
        signal.chatMsg = msg;
        if (isconnected) {
            sendSignal(signal);
            System.out.println("SIGNAL Sent from client");
        }
    }

    public void recieveChatMsg(Signal msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String recievedMsg = msg.chatMsg;
                GUIBuilder.gameController.msgTA.appendText(recievedMsg);
            }
        });
    }

    public void sendSignal(Signal signal) {
        try {
            System.out.println(signal.signalID);
            ps.writeObject(signal);
            System.out.println(signal.signalID);
        } catch (IOException ioex) {
            System.out.println("error in sending signal to server");
        }
    }

    // closing socekt and streams from player
    public void closeConnection() {
        Signal logOutSignal = new Signal();
        logOutSignal.signalID = 11;
        logOutSignal.userName = player1;
        sendSignal(logOutSignal);
        isconnected = false;
        try {
            ps.close();
            dis.close();
            mySocket.close();
            System.out.println("Sign out success from close connection");
        } catch (IOException ex) {
            System.out.println("Can't close connection");
        }
    }

    // handle terminating connection from the server
    public void terminateConnection() {
        closeConnection();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GUIBuilder.initialStage.setScene(GUIBuilder.signingScene);
                GUIBuilder.controller.handleLostConnection();
            }
        });
    }

    public static void main(String[] args) {
    }
}
