/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenesControllers;

import signal.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import player.*;

/**
 * FXML Controller class
 *
 * @author naggar
 */
public class GUIController implements Initializable {

    public Stage initialStage;
    public static String username;

    // Signing sceen nodes
    @FXML
    private PasswordField passwordTF;
    @FXML
    private TextField userNameTF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initialStage = GUIBuilder.initialStage;
    }

    // handle sign in button 
    @FXML
    protected void signIn(ActionEvent event) {
        ClientMainHandler mainHandler = new ClientMainHandler();
        if (mainHandler.startConnection()) {
            username = userNameTF.getText();
            String password = passwordTF.getText();

            Player newplayer = new Player();
            newplayer.setUsername(username);
            newplayer.setPassword(password);

            boolean response = mainHandler.requestSigning(1, newplayer);

            if (response) {
                initialStage.setScene(GUIBuilder.chooseModeScene);
                initialStage.show();
                GUIBuilder.chooseModeController.playerInfo();
            } else {
                try {
                    ClientMainHandler.ps.close();
                    ClientMainHandler.dis.close();
                    ClientMainHandler.mySocket.close();
                    System.out.println("close connection from sign in");
                } catch (IOException ex) {
                    System.out.println("Can't close connection");
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Game Error");
                alert.setHeaderText("Login failure");
                alert.setContentText("Invalid username or password!");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game Error");
            alert.setHeaderText("Connection failure");
            alert.setContentText("Can't connect to server!!");
            alert.showAndWait();
        }
    }

    // handle sign up  button
    @FXML
    private void signUp(ActionEvent event) {
        ClientMainHandler mainHandler = new ClientMainHandler();
        if (mainHandler.startConnection()) {

            username = userNameTF.getText();
            String password = passwordTF.getText();

            Player newplayer = new Player();
            newplayer.setUsername(username);
            newplayer.setPassword(password);

            boolean response = mainHandler.requestSigning(2, newplayer);

            if (response) {
                initialStage.setScene(GUIBuilder.chooseModeScene);
                initialStage.show();
                GUIBuilder.chooseModeController.playerInfo();

            } else {
                try {
                    ClientMainHandler.ps.close();
                    ClientMainHandler.dis.close();
                    ClientMainHandler.mySocket.close();
                    System.out.println("close connection from sign up ");
                } catch (IOException ex) {
                    System.out.println("Can't close connection");
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Game Error");
                alert.setHeaderText("SignUp failure");
                alert.setContentText("Username is already taken! \n \nTry another one :D");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game Error");
            alert.setHeaderText("Connection failure");
            alert.setContentText("Can't connect to server!!");
            alert.showAndWait();
        }
    }

    @FXML
    void playVsCom(ActionEvent event) {
        initialStage.setScene(GUIBuilder.compGameScene);
        initialStage.show();
    }

    // if the server closed the connection 
    public void handleLostConnection() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Connection Error");
        alert.setHeaderText("Server disconnected");
        alert.setContentText("You lost the connection with server!");
        alert.showAndWait();
    }
}
