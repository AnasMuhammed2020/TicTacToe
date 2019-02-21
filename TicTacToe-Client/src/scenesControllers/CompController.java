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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import player.ClientMainHandler;
import player.GUIBuilder;

/**
 *
 * @author moham
 */
public class CompController implements Initializable {

    // Game Scene nodes
    @FXML
    public TextArea msgTA;
    @FXML
    public TextField msgTF;
    @FXML
    public Label player;
    @FXML
    public Label winnerLabel;

    @FXML
    private Button playAgainBtn;

    @FXML
    public Button b1, b2, b3, b4, b5, b6, b7, b8, b9, send;
    @FXML
    public Image emptyImg = new Image("scenesImages/null.png");

    @FXML
    static public Image imgo = new Image("scenesImages/o_1.png");
    @FXML
    static public Image imgx = new Image("scenesImages/x_1.png");
    @FXML
    private Label player1Name, player2Name;
    public static int[] flags = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    public int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;
    static int turn = 0;//0player 1comp
    public String username = GUIController.username;
    static int winner;

    public Stage initialStage;
    int XOImageFlag = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialStage = GUIBuilder.initialStage;

    }

//    public void resetScene() {
//
//        player1Name.setText(GUIBuilder.handler.player1);
//        player2Name.setText(GUIBuilder.handler.player2);
//
//        flag1 = flag2 = flag3 = flag4 = flag5 = flag6 = flag7 = flag8 = flag9 = 0;
//    }
    // handle send button in chat room 
    @FXML
    private void handleChat(ActionEvent event) {
        ClientMainHandler mainHandler = new ClientMainHandler();
        mainHandler.startConnection();

        String msg = msgTF.getText();
        msgTF.setText(" ");

        mainHandler.sendChatMsg(5, username, msg);
    }

    @FXML
    void handlechooseMode(MouseEvent event) {
//        playerInfo();
        if (ClientMainHandler.isconnected) {
            initialStage.setScene(GUIBuilder.chooseModeScene);
            initialStage.show();
        }
        initialStage.setScene(GUIBuilder.signingScene);
        initialStage.show();
    }

    @FXML
    protected void handleButton1Action(ActionEvent event) {
//        if(flag1==0 && GUIBuilder.handler.myTurn){
//            GUIBuilder.handler.makeAMove(GUIController.username, 0);
        System.out.println(event.getSource());

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b1.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag1 = 1;
            flags[0] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 200;

                flags[6] = 1;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }

            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("Player wins!");
            } else if (winner == 200) {
                winnerLabel.setText("Computer wins!");
            } else {
                winnerLabel.setText(" ");

            }

            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }

        }

    }

    @FXML
    void resetScene(ActionEvent event) {
//        player1Name.setText(GUIBuilder.handler.player1);
//        player2Name.setText(GUIBuilder.handler.player2);
        for (int i = 0; i < 9; i++) {
            flags[i] = -1;
        }
        playAgainBtn.setDisable(true);
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
        winnerLabel.setText("");

    }

    @FXML
    protected void handleButton2Action(ActionEvent event) {
//   

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b2.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag2 = 1;
            flags[1] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }

            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }
            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
            ////////////////////
//             b2.setGraphic(new ImageView(imgo));
//                  Node source1 = (Node)event.getSource() ;
//            source1.setDisable(true);

        }

    }

    @FXML
    protected void handleButton3Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b3.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag3 = 1;
            flags[2] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }
            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }
            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
        }

    }

    @FXML
    protected void handleButton4Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b4.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag4 = 1;
            flags[3] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }

            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }

            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);

                b8.setDisable(true);

            }
        }
    }

    @FXML
    protected void handleButton5Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b5.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag5 = 1;
            flags[4] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }

            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }

            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
        }
    }

    @FXML
    protected void handleButton6Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b6.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag6 = 1;
            flags[5] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }

            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }
            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
        }
    }

    @FXML
    protected void handleButton7Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b7.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag7 = 1;
            flags[6] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
//                Node source0 = (Node) event.getSource();
//                source0.setDisable(true);
                b1.setDisable(true);
                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }
            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }

            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
        }
    }

    @FXML
    protected void handleButton8Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b8.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag8 = 1;
            flags[7] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }
            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }

            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
        }

    }

    @FXML
    protected void handleButton9Action(ActionEvent event) {

        if (turn == 0) {
            // System.out.println(celltoplay+"gfffffffffffffff");
            b9.setGraphic(new ImageView(imgx));
            Node source = (Node) event.getSource();
            source.setDisable(true);
            flag9 = 1;
            flags[8] = 100;
            //checkFlags();
            int cell = checkFlags();
            System.out.println(cell);
            ////////////////////////////

            if (cell == 0) {
                b1.setGraphic(new ImageView(imgo));
                b1.setDisable(true);

                flag1 = 1;
                flags[0] = 200;
                //changeturn();
            } else if (cell == 1) {
                System.out.println("hjjjjjjjjjjjjjj");
                b2.setGraphic(new ImageView(imgo));
                b2.setDisable(true);

                flag2 = 1;
                flags[1] = 200;
                // changeturn();
            } else if (cell == 2) {
                b3.setGraphic(new ImageView(imgo));
                b3.setDisable(true);

                flag3 = 1;
                flags[2] = 200;
                //changeturn();
            } else if (cell == 3) {
                b4.setGraphic(new ImageView(imgo));
                b4.setDisable(true);

                flag4 = 1;
                flags[3] = 200;
                //changeturn();
            } else if (cell == 4) {
                b5.setGraphic(new ImageView(imgo));
                b5.setDisable(true);

                flag5 = 1;
                flags[4] = 200;
                //changeturn();
            } else if (cell == 5) {
                b6.setGraphic(new ImageView(imgo));
                b6.setDisable(true);

                flag6 = 1;
                flags[5] = 200;
                //changeturn();
            } else if (cell == 6) {
                b7.setGraphic(new ImageView(imgo));
                b7.setDisable(true);

                flag7 = 1;
                flags[6] = 200;
                //changeturn();
            } else if (cell == 7) {
                b8.setGraphic(new ImageView(imgo));
                b8.setDisable(true);

                flag8 = 1;
                flags[7] = 200;
                //  changeturn();
            } else if (cell == 8) {
                b9.setGraphic(new ImageView(imgo));
                b9.setDisable(true);

                flag9 = 1;
                flags[8] = 200;
                //changeturn();
            }

            winner = checkWinner(flags);
            if (winner == 100) {
                winnerLabel.setText("player is winner");
            } else if (winner == 200) {
                winnerLabel.setText("computer is winner");
            } else {
                winnerLabel.setText(" ");
            }

            if (winner == 100 || winner == 200) {
                b1.setDisable(true);
                b2.setDisable(true);
                b3.setDisable(true);
                b4.setDisable(true);
                b5.setDisable(true);
                b6.setDisable(true);
                b7.setDisable(true);
                b8.setDisable(true);
                b9.setDisable(true);

            }
        }
    }

    public int checkWinner(int[] flags) {

        if ((flags[0] == 100 && flags[1] == 100 && flags[2] == 100)
                || (flags[3] == 100 && flags[4] == 100 && flags[5] == 100)
                || (flags[6] == 100 && flags[7] == 100 && flags[8] == 100)
                || (flags[0] == 100 && flags[3] == 100 && flags[6] == 100)
                || (flags[1] == 100 && flags[4] == 100 && flags[7] == 100)
                || (flags[2] == 100 && flags[5] == 100 && flags[8] == 100)
                || (flags[2] == 100 && flags[4] == 100 && flags[6] == 100)
                || (flags[0] == 100 && flags[4] == 100 && flags[8] == 100)) {

            System.out.println("player X win");
            playAgainBtn.setDisable(false);
            return 100;
        }
        if ((flags[0] == 200 && flags[1] == 200 && flags[2] == 200)
                || (flags[3] == 200 && flags[4] == 200 && flags[5] == 200)
                || (flags[6] == 200 && flags[7] == 200 && flags[8] == 200)
                || (flags[0] == 200 && flags[3] == 200 && flags[6] == 200)
                || (flags[1] == 200 && flags[4] == 200 && flags[7] == 200)
                || (flags[2] == 200 && flags[5] == 200 && flags[8] == 200)
                || (flags[2] == 200 && flags[4] == 200 && flags[6] == 200)
                || (flags[0] == 200 && flags[4] == 200 && flags[8] == 200)) {

            System.out.println("player o win");
            playAgainBtn.setDisable(false);
            return 200;
        }
        return 300;

    }

    static public void compturn() {

    }

    static public int changeturn() {
        if (turn == 1) {
            turn = 0;
        } else {
            turn = 1;
        }
        return turn;
    }

    public static int checkFlags() {

        for (int i = 0; i < 9; i++) {
            System.out.println(flags[i]);
            if (flags[i] == -1) {
                return i;
            }

        }
        return 10;
    }
}
