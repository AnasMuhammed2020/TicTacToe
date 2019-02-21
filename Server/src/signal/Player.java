/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author aml
 */
package signal;

import java.io.Serializable;

public class Player implements Serializable{
        public int playerNumber;
        public String name;
        public String password;
        public int score;
        public String status;
 

    public Player() {
        this.playerNumber =0;
        this.name = null;
        this.password = null;
          this.score=0;
        this.status="online";
    }

    public Player(int playerId, String name, String passwd) {
        this.playerNumber =playerId;
        this.name = name;
        this.password = passwd;
        this.score=0;
        this.status="online";
    }
    public int getId() {
        return playerNumber;
    }

    public void setId(int id) {
        this.playerNumber = id;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


  

    
}
