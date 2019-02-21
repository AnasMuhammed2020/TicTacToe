package server;

import signal.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author anas
 */
public class Database {

    private static String dbURL = "jdbc:mysql://localhost:3306/xo";
    private static String tableName = "Player";
    private static int id = 0;
    String query;
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    ArrayList<Player> allPersons = new ArrayList<Player>();

   

    public static Connection connectDb() {
        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            
            String url = "jdbc:mysql://localhost:3306/xo";
            String user = "root";
            String password = "anas";
            System.out.println("Connecting to DB");
            
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB");
            
//            conn = DriverManager.getConnection(dbURL, "root", "anas");
            System.out.println("true from DB");
        } catch (Exception except) {
            System.out.println("false");
            except.printStackTrace();
        }
        return conn;
    }

    public ArrayList getAll() throws SQLException {
        String st = "select * from players ";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(st);
        while (rs.next()) {
            Player p = new Player();
            p.playerNumber = rs.getInt(1);
            p.name = rs.getString(2);
            p.password = rs.getString(3);
            p.status = rs.getString(4);
            p.score = rs.getInt(5);
            allPersons.add(p);

        }
        return allPersons;
    }

    public boolean addPerson(Player p2,Connection conn) throws SQLException {
        Player p = new Player();
        p = p2;

        String st = "insert into players(playername,password,status,score) values(?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(st);
        pst.setString(1, p.name);
        pst.setString(2, p.password);
        pst.setString(3, p.status);
        pst.setInt(4, p.score);
        //pst.setInt(5, p.playerNumber);
        int rs = pst.executeUpdate();
        System.out.println("Num of changed rows" + rs);
        if (rs == 1) {
            return true;
        } else {
            return false;
        }
    }

    
    
    public ArrayList getAll(Connection conn) throws SQLException {
        String st = "select * from players ";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(st);
        while (rs.next()) {
            Player p = new Player();
            p.playerNumber = rs.getInt(1);
            p.name = rs.getString(2);
            p.password = rs.getString(3);
            p.score = rs.getInt(4);
            p.status = rs.getString(5);
            allPersons.add(p);

        }
        return allPersons;
    }
    
    
    public boolean deleteGame(Connection conn) throws SQLException, ClassNotFoundException {

        // prepare query
        String query = "delete FROM game";
        PreparedStatement statement = conn.prepareStatement(query);
        boolean isdeleted=false;
        // execute query
        int deleted = statement.executeUpdate();
      if(deleted==1)
      {
          isdeleted=true;
      }
      else
      {
          isdeleted=false;
      }
        // close resources
      
        return isdeleted;
    }
    
    public GAME getgameid(int player1,Connection conn) throws SQLException, ClassNotFoundException {

        // prepare query
        String query = "select id,cells,player1,player2 from game where player1 =? OR player2=?";
        GAME game=new GAME();
        PreparedStatement statement;

        statement = conn.prepareStatement(query);
        statement.setInt(1, player1);
         statement.setInt(2, player1);

        // execute sql query
        ResultSet rs = statement.executeQuery();
          while(rs.next()){
               game.id = rs.getInt("id");
               game.cells=rs.getString("cells");
               game.id1=rs.getInt("player1");
               game.id2=rs.getInt("player2");
               if(game.id!=0)
               {
                   return game;
               }
               
          }

//        // close resources
//        statement.close();
//        conn.close();
        return game;
    }

    public int deleteGame(int player) throws SQLException, ClassNotFoundException {

        // prepare query
        String query = "delete FROM game where (toplayer = ?) or (fromPlayer = ?)";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, player);
        statement.setInt(2, player);

        // execute query
        int deleted = statement.executeUpdate();

        // close resources
        statement.close();
        conn.close();
        return deleted;
    }
    
    
        public boolean checkSignup(Player player,Connection conn) throws SQLException
    {
         Statement stmt= conn.createStatement();
          String myquery = new String("SELECT playername, password FROM players");
        try {
            
            
            ResultSet rs=stmt.executeQuery(myquery);
                    

            while(rs.next()){
               
                String name = rs.getString("playername");
                System.out.println(name);
                String password = rs.getString("password");
                System.out.println(password);
                System.out.println(name+"fdfgfdsf");
                if(name.equals(player.name)){
                    
                    System.out.println("player name is exist choose another name");
                   return false;
                }
                else
                {
                    System.out.println("else in add");
                    boolean isadd=addPerson(player,conn);
                    if(isadd==true)
                        return true;
                    else
                        return false;
                }
   
        } 
            
            }
       catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;    
    }
    
    
    
    public boolean checkLogin(Player player,Connection conn)
    {
         Statement stmt;
        try {
            stmt = (Statement) conn.createStatement();
             query = "SELECT playername, password FROM players";
            stmt.executeQuery(query);
            ResultSet rs = stmt.getResultSet();

            while(rs.next()){
                String name = rs.getString("playername");
                String password = rs.getString("password");

                if(name.equals(player.name)  && password.equals(player.password)){
                    System.out.println("OK");
                   return true;
                }
   
        } 
            
            }
       catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;    
    }
    
    
        public String getPlayerById(int playernumber,Connection conn)
    {
         PreparedStatement stmt;
        String name=null;
        try {
              query = "SELECT name FROM players WHERE (ID = ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, playernumber);
            stmt.executeQuery(query);
            
            ResultSet rs = stmt.getResultSet();
           
            while(rs.next()){
                name = rs.getString("name");
        } 
            
            }
       catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;    
    }
    
     public Player getPlayerBYNUMBER(int playernumber,Connection conn)
    {
         PreparedStatement stmt;
         Player player2=new Player();
        try {
              query = "SELECT * FROM players WHERE (ID = ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, playernumber);
            stmt.executeQuery(query);
            
            ResultSet rs = stmt.getResultSet();
           
            while(rs.next()){
                player2.name = rs.getString("name");
                player2.score = rs.getInt("score");
                player2.status = rs.getString("status");
                player2.playerNumber = playernumber;

   
        } 
            
            }
       catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return player2;    
    }

    public void updateScore(Player player, int gameid) {
        try {
            // prepare query
            String query = "update players set score = ? where (playername = ?) and (gameid = ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, player.playerNumber);
            statement.setInt(2, gameid);

            // execute query
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
      public int getScore(String player,Connection conn) {
           int score =0;
        try {
            // prepare query
            
            String query = "select score from players where (playername = ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, player);
           
             ResultSet rs= statement.executeQuery();
           

            while(rs.next()){
               score = rs.getInt("score");
               
          
        }

           
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
         return score;
    }
    
    
    
        public int updateScore(String winner,Connection conn) {
            int currentScore=0;
        try {
            // prepare query
            String query = "update players set score = ? where (playername = ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            currentScore=getScore(winner, conn);
            currentScore=currentScore++;
            statement.setInt(1, currentScore);
            statement.setString(2, winner);
           ;

            // execute query
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentScore;
    }
    public void close() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean updatetoOnline(String name,Connection conn) {

        String query = "UPDATE players SET STATUS = 'online' WHERE PLAYERNAME = ?";
    
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);

            // execute sql query
           int isupdate= statement.executeUpdate();
           if(isupdate==1)
           {
               return true;
           }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
return false;
    }
    public Boolean updatetoOffline(String name,Connection conn) {

        String query = "UPDATE players SET STATUS = 'offline' WHERE PLAYERNAME = ?";
    
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);

            // execute sql query
           int isupdate= statement.executeUpdate();
           if(isupdate==1)
           {
               return true;
           }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
return false;
    }
    
    public Boolean updatetoBusy(String name,Connection conn) {

        String query = "UPDATE players SET STATUS = 'busy' WHERE PLAYERNAME = ?";
    
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
        
            // execute sql query
           int isupdate= statement.executeUpdate();
           if(isupdate==1)
           {
               return true;
           }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
return false;
    }
    
    public int getIdByName(String name,Connection conn) {

        String query = "SELECT id,status FROM players WHERE playername = ?";
    
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
            statement.setString(1, name);
        
            // execute sql query
            ResultSet rs= statement.executeQuery();
           

            while(rs.next()){
               int id = rs.getInt("id");
                System.err.println(id);
                 if(id!=0)
           {
               return id;
           }
                 else
                     return 0;
        }
          
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
return 0;
    }
    
    
    
    
    public boolean StartGame(int player1,int player2,Connection conn) throws SQLException {
      
        String initCellString="0,0,0,0,0,0,0,0,0";
        String st = "insert into game(player1,player2,cells) values(?,?,?)";
        PreparedStatement pst = conn.prepareStatement(st);
        pst.setInt(1, player1);
        pst.setInt(2, player2);
        pst.setString(3, initCellString);
       
        int rs = pst.executeUpdate();
        System.out.println("Num of changed rows" + rs);
        if (rs == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    
       public Boolean updatemove(int id,String cells,Connection conn) {

        String query = "UPDATE game SET cells =? WHERE id = ?";
    
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
          
            statement.setString(1, cells);
              statement.setInt(2, id);

            // execute sql query
           int isupdate= statement.executeUpdate();
           if(isupdate==1)
           {
               return true;
           }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
return false;
    }
    
    
    
    
    
    
    
    public void updatetoOffline(int id) {
        //change status to online
        String query = "UPDATE USERS SET IS_ONLINE = 0 WHERE playername = ?";

        query = "UPDATE USERS SET STATUS = 'online' WHERE ID = ?";
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    	public static void setmove(Player player, String coordinate) throws SQLException, ClassNotFoundException {
		
		String query = "insert into game values (?,?)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setInt(1, player.playerNumber);
		statement.setString(2, coordinate);
		
		// execute query
		statement.executeUpdate();
		
		// close resources
		statement.close();
		conn.close();
	}

}
