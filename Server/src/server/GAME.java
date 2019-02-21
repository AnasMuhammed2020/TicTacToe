/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

//import com.sun.org.apache.xpath.internal.functions.FuncBoolean;

/**
 *
 * @author DOAA
 */
public class GAME {
    int id1;
    int id2;
    int id;
    String cells;

    public GAME() {
    }

    public GAME(int id, String cells) {
        this.id1 = id;
        this.cells = cells;
    }
    
    public String checkWinner(String[] cells)
    {
       
         if((cells[0]=="X"&&cells[1]=="X"&&cells[2]=="X")
          ||(cells[3]=="X"&&cells[4]=="X"&&cells[5]=="X" )   
          ||(cells[6]=="X"&&cells[7]=="X"&&cells[8]=="X" )  
          ||(cells[0]=="X"&&cells[3]=="X"&&cells[6]=="X")   
          ||(cells[1]=="X"&&cells[4]=="X"&&cells[7]=="X")     
          ||(cells[2]=="X"&&cells[5]=="X"&&cells[8]=="X")     
          ||(cells[2]=="X"&&cells[4]=="X"&&cells[6]=="X")     
          ||(cells[0]=="X"&&cells[4]=="X"&&cells[8]=="X")     
          )
        {

            System.out.println("player X win");
            return "X";
        }
        if((cells[0]=="O"&&cells[1]=="O"&&cells[2]=="O")
          ||(cells[3]=="O"&&cells[4]=="O"&&cells[5]=="O" )   
          ||(cells[6]=="O"&&cells[7]=="O"&&cells[8]=="O" )  
          ||(cells[0]=="O"&&cells[3]=="O"&&cells[6]=="O")   
          ||(cells[1]=="O"&&cells[4]=="O"&&cells[7]=="O")     
          ||(cells[2]=="O"&&cells[5]=="O"&&cells[8]=="O")     
          ||(cells[2]=="O"&&cells[4]=="O"&&cells[6]=="O")     
          ||(cells[0]=="O"&&cells[4]=="O"&&cells[8]=="O")     
          )
        {

            System.out.println("player o win");
            return "O";
        }
        return null;
    }
    
}
