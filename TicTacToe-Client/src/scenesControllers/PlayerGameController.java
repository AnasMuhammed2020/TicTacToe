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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import player.ClientMainHandler;
import player.GUIBuilder;

/**
 *
 * @author moham
 */
public class PlayerGameController implements Initializable {

    // Game Scene nodes
    @FXML public TextArea msgTA;
    @FXML public TextField msgTF;

    @FXML public Button b1,b2,b3,b4,b5,b6,b7,b8,b9,send;
//    @FXML static public Image imgo = new Image("scenesImages/o_1.png");
//    @FXML static public Image imgx = new Image("scenesImages/x_1.png");
    @FXML public static Image img = new Image("scenesImages/o_1.png");
    @FXML public Image emptyImg = new Image("scenesImages/null.png");
    @FXML private Label player1Name,player2Name;

    public int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;

    public String username = ClientMainHandler.player1;

    public Stage initialStage;
    int XOImageFlag = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialStage = GUIBuilder.initialStage;
    }
   
    public void resetScene(){
        player1Name.setText(ClientMainHandler.player1);
        player2Name.setText(ClientMainHandler.player2);
        flag1=flag2=flag3=flag4=flag5=flag6=flag7=flag8=flag9=0;
        
        b1.setDisable(false);
        b2.setDisable(false);
        b3.setDisable(false);
        b4.setDisable(false);
        b5.setDisable(false);
        b6.setDisable(false);
        b7.setDisable(false);
        b8.setDisable(false);
        b9.setDisable(false);
        b1.setGraphic(new ImageView(emptyImg));
        b2.setGraphic(new ImageView(emptyImg));
        b3.setGraphic(new ImageView(emptyImg));
        b4.setGraphic(new ImageView(emptyImg));
        b5.setGraphic(new ImageView(emptyImg));
        b6.setGraphic(new ImageView(emptyImg));
        b7.setGraphic(new ImageView(emptyImg));
        b8.setGraphic(new ImageView(emptyImg));
        b9.setGraphic(new ImageView(emptyImg));   
    }
    
    public void setDisabled (Node source){
        source.setDisable(true);
    }
    
    // handle send button in chat room 
    @FXML
    private void handleChat(ActionEvent event) {
        ClientMainHandler mainHandler = new ClientMainHandler();
        mainHandler.startConnection();
        String msg = msgTF.getText();
        msgTF.setText(" ");
        mainHandler.sendChatMsg(5, username, msg);
    }

    @FXML protected void handleButton1Action(ActionEvent event) {
        System.out.println(ClientMainHandler.myTurn);
        if(flag1==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(0);
            System.out.println(event.getSource());
            b1.setGraphic(new ImageView(img));
            b1.setDisable(true);
            flag1=1;
        }
    }
    @FXML protected void handleButton2Action(ActionEvent event) {
        if(flag2==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(1);
            b2.setGraphic(new ImageView(img));
            b2.setDisable(true);
            flag2=1;
        }
    }
    @FXML protected void handleButton3Action(ActionEvent event) {
        if(flag3==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(2);
            b3.setGraphic(new ImageView(img));
            b3.setDisable(true);
            flag3=1;
        }
    }
    @FXML protected void handleButton4Action(ActionEvent event) {
        if(flag4==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(3);
            b4.setGraphic(new ImageView(img));
            b4.setDisable(true);
            flag4=1;
        }
    }
    @FXML protected void handleButton5Action(ActionEvent event) {
        if(flag5==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(4);
            b5.setGraphic(new ImageView(img));
            b5.setDisable(true);
            flag5=1;
        }
    }
    @FXML protected void handleButton6Action(ActionEvent event) {
        if(flag6==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(5);
            b6.setGraphic(new ImageView(img));
            b6.setDisable(true);
            flag6=1;
        }
    }
    @FXML protected void handleButton7Action(ActionEvent event) {
        if(flag7==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(6);
            b7.setGraphic(new ImageView(img));
            b7.setDisable(true);
            flag7=1;
        }
    }
    @FXML protected void handleButton8Action(ActionEvent event) {
        if(flag8==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(7);
            b8.setGraphic(new ImageView(img));
            b8.setDisable(true);
            flag8=1;
        }
    }
    @FXML protected void handleButton9Action(ActionEvent event) {
        if(flag9==0 && ClientMainHandler.myTurn){
            ClientMainHandler mainHandler = new ClientMainHandler();
            mainHandler.makeAMove(8);
            b9.setGraphic(new ImageView(img));
            b9.setDisable(true);
            flag9=1;
        }
    }
    
    //game board
//    @FXML
//    private void showxo(MouseEvent e) {
//        System.out.println("image pressed");
//        
//        Node source = (Node)e.getSource() ;
//        ImageView imagename = (ImageView) source;
//        if (XOImageFlag == 0 )
//        {
//            imagename.setImage(new Image ("scenesImages/x.png"));
//            XOImageFlag = 1;
//        }
//        else
//        {
//            imagename.setImage(new Image ("scenesImages/o.png"));
//            XOImageFlag = 0;
//        }
//        imagename.setDisable(true);
//    }

}
