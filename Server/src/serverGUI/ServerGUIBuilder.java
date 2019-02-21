/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverGUI;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author moham
 */
public class ServerGUIBuilder extends Application {

    public static Scene serverScene;
    public static ServerGUIController serverController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader serverLoader = new FXMLLoader();
        serverLoader.setLocation(getClass().getResource("servergui.fxml"));
        Parent servergParent = serverLoader.load();
        serverScene = new Scene(servergParent);
        serverController = (ServerGUIController) serverLoader.getController();
       
        
        primaryStage.setTitle("Server");
        primaryStage.setScene(serverScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // create a thread here to update the table
    
//    private void recieveFromDB() {
//
//        ArrayList<Player> players = new ArrayList<Player>();
//
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < players.size(); i++) {
//                    if (players.get(i).getUsername().equals(player1)) {
//                        players.remove(i);
//                    }
//                }
//                ServerGUIController.playerNameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
//                ServerGUIController.scoreCol.setCellValueFactory(new PropertyValueFactory<>(("score")));
//                ServerGUIController.statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
//                ServerGUIController.playersTable.getItems().setAll(sig.players);
//            }
//        });
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
//    }

    @Override
    public void stop() {
        // handle closing the server application .. 
    }
}
