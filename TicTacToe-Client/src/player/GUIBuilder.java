/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage; 
import scenesControllers.*;

/**
 *
 * @author naggar
 */
public class GUIBuilder extends Application {

    public static Stage initialStage;

    public static Scene signingScene;
    public static Scene chooseModeScene;
    public static Scene playersListScene;
    public static Scene gameoverScene;
    public static Scene compGameScene;
    public static Scene playerGameScene;

    public static GUIController controller;
    public static PlayersListController playersListController;
    public static CompController compGameController;
    public static PlayerGameController gameController;
    public static GameOverController gameoverController;
    public static ChooseModeController chooseModeController;
    
//    public static ClientMainHandler handler;

    @Override 
    public void start(Stage primaryStage) throws Exception {

        initialStage = primaryStage;
        
        // Signing scene
        FXMLLoader signingLoader = new FXMLLoader();
        signingLoader.setLocation(getClass().getResource("/scenes/SigningPage.fxml"));
        Parent signingParent = signingLoader.load();
        signingScene = new Scene(signingParent);
        controller = (GUIController) signingLoader.getController();
        
        // choose mode scene
        FXMLLoader chooseModeLoader = new FXMLLoader();  
        chooseModeLoader.setLocation(getClass().getResource("/scenes/ChooseMode.fxml"));
        Parent chooseModeParent = chooseModeLoader.load();
        chooseModeScene = new Scene(chooseModeParent);
        chooseModeController = (ChooseModeController) chooseModeLoader.getController();
        
        // Players List scene
        FXMLLoader playersListLoader = new FXMLLoader();
        playersListLoader.setLocation(getClass().getResource("/scenes/PlayersList.fxml"));
           Parent playersListParent = playersListLoader.load();
        playersListScene = new Scene(playersListParent);
        playersListController = (PlayersListController) playersListLoader.getController();

        // Comp Game board scene
        FXMLLoader CompgameSceneLoader = new FXMLLoader();
        CompgameSceneLoader.setLocation(getClass().getResource("/scenes/compgame.fxml"));
        Parent compgameSceneParent = CompgameSceneLoader.load();
        compGameScene = new Scene(compgameSceneParent);
        compGameController = (CompController) CompgameSceneLoader.getController();

        //Player Game board scene
        FXMLLoader gameSceneLoader = new FXMLLoader();
        gameSceneLoader.setLocation(getClass().getResource("/scenes/GameScene.fxml"));
        Parent playerGameSceneParent = gameSceneLoader.load();
        playerGameScene = new Scene(playerGameSceneParent);
        gameController = (PlayerGameController) gameSceneLoader.getController();
        
        // Game over scene
        FXMLLoader gameoverSceneLoader = new FXMLLoader();
        gameoverSceneLoader.setLocation(getClass().getResource("/scenes/GameOver.fxml"));
        Parent gameoverSceneParent = gameoverSceneLoader.load();
        gameoverScene = new Scene(gameoverSceneParent);
        gameoverController = (GameOverController) gameoverSceneLoader.getController();

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(signingScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop(){
        System.out.println("Before Closing ");
        ClientMainHandler mainHandler;
        mainHandler = new ClientMainHandler();
        initialStage.setScene(GUIBuilder.signingScene);
        initialStage.show();
        if (ClientMainHandler.isconnected)
            mainHandler.closeConnection();
        System.out.println("after Closing ");
    }

}
 