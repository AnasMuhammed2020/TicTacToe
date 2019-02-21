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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import player.ClientMainHandler;
import player.GUIBuilder;
import signal.Player;

/**
 *
 * @author moham
 */
public class PlayersListController implements Initializable {

    public ClientMainHandler mainHandler;
    public static String player2;
    public String player2Status;
    public Stage initialStage;
    public String username;

    //playersList nodes
    @FXML
    public TableView<Player> playersTable;
    @FXML
    public TableColumn playerNameCol;
    @FXML
    public TableColumn scoreCol;
    @FXML
    public TableColumn statusCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialStage = GUIBuilder.initialStage;
    }

    public void getSelectedPlayer() {
        mainHandler = new ClientMainHandler();
        // check the table's selected item and get selected item
        if (playersTable.getSelectionModel().getSelectedItem() != null) {
            Player selectedPerson = playersTable.getSelectionModel().getSelectedItem();
            player2 = selectedPerson.getUsername();
            player2Status = selectedPerson.getStatus();
            System.out.println(selectedPerson.getUsername());
            System.out.println(selectedPerson.getStatus());
        }
    }

    @FXML
    private void handleSendInvitation(ActionEvent event) {
        mainHandler = new ClientMainHandler();
        System.out.println(ClientMainHandler.player1 + "inside handle send invitatio");
        System.out.println(player2 + "inside handle send invitatio");
        System.out.println(player2Status + "inside handle send invitatio");
        if (playersTable.getSelectionModel().getSelectedItem() != null) {
            if (!player2Status.equals("online")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Player not available now!");
                alert.setHeaderText("Player is not available");
                alert.setContentText(player2 + " is not available.");
                alert.showAndWait();
            } else {
                mainHandler.requestGame(4, ClientMainHandler.player1, player2);
            }
        }
    }

    // handle sign out button
    @FXML
    private void signout(ActionEvent event) {
        mainHandler = new ClientMainHandler();
        initialStage.setScene(GUIBuilder.signingScene);
        initialStage.show();

        mainHandler.closeConnection();
    }

    public void showAlert(String playerName) {
        mainHandler = new ClientMainHandler();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, playerName + " wants to play with you", ButtonType.NO, ButtonType.YES);
        if (alert.showAndWait().get() == ButtonType.YES) {
            mainHandler.sendResponse(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    GUIBuilder.initialStage.setScene(GUIBuilder.playerGameScene);
                    GUIBuilder.gameController.resetScene();
                    PlayerGameController.img = new Image("scenesImages/o_1.png");

                }
            });

        } else {
            mainHandler.sendResponse(false);
        }
    }

    // blue button >> go back
    @FXML
    void handlechooseMode(MouseEvent event) {
        initialStage.setScene(GUIBuilder.chooseModeScene);
        initialStage.show();
//        playerInfo();
    }
}
