/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenesControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import player.ClientMainHandler;
import player.GUIBuilder;

/**
 * FXML Controller class
 *
 * @author moham
 */
public class GameOverController implements Initializable {

    public Stage initialStage;
    ClientMainHandler mainHandler;

    @FXML public Label playerNameLabel;
    @FXML static public Image gameOverImg = new Image("scenesImages/o_1.png");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialStage = GUIBuilder.initialStage;
    }

    @FXML
    void goHome(ActionEvent event) {
        initialStage.setScene(GUIBuilder.chooseModeScene);
        initialStage.show();
    }

    @FXML
    void handleSignOut(ActionEvent event) {
        initialStage.setScene(GUIBuilder.signingScene);
        initialStage.show();
        mainHandler = new ClientMainHandler();
        mainHandler.closeConnection();
    }

}
