/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverGUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author moham
 */
public class ServerGUIController implements Initializable {

    @FXML
    private Pane rootpane;
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;
    @FXML
    public TableView<?> playersTable;
    @FXML
    public TableColumn<?, ?> playerNameCol;
    @FXML
    public TableColumn<?, ?> scoreCol;
    @FXML
    public TableColumn<?, ?> statusCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void handleStartBtn(ActionEvent event) {
        startBtn.setDisable(true);
        stopBtn.setDisable(false);
        // write your functionality here
    }

    @FXML
    private void handleStopBtn(ActionEvent event) {
        stopBtn.setDisable(true);
        startBtn.setDisable(false);
    }
    
}
