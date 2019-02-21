/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenesControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import player.ClientMainHandler;
import player.GUIBuilder;

/**
 *
 * @author moham
 */
public class ChooseModeController implements Initializable {

    public ClientMainHandler mainHandler;
    public Stage initialStage;
    //choose mode nodes
    @FXML public Label playerNameLabel;
    @FXML public Label playerScoreLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialStage = GUIBuilder.initialStage;
    }

    // handle single player button
    @FXML
    private void playVScom(ActionEvent event) {
        initialStage.setScene(GUIBuilder.compGameScene);
        initialStage.show();
    }

    // handle two players button
    @FXML
    private void getPlayersList(ActionEvent event) {
        System.out.println("In getting playerslist choose mode Controller");
        if (ClientMainHandler.isconnected) {
            initialStage.setScene(GUIBuilder.playersListScene);
            initialStage.show();
        }
    }
    // handle sign out button

    @FXML
    public void signout(ActionEvent event) {
        mainHandler = new ClientMainHandler();
        initialStage.setScene(GUIBuilder.signingScene);
        initialStage.show();
        mainHandler.closeConnection();
    }

    // setting player name and score in choose mode scene & player list
    @FXML
    public void playerInfo() {
        playerNameLabel.setText(ClientMainHandler.player1);
        playerScoreLabel.setText(Integer.toString(ClientMainHandler.player.getScore()));
    }
   

}
