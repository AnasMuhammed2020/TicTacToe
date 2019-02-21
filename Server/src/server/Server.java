
package server;
 

import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Server  {
public static void main(String args[]) {
int port = 3008;
int MAXIMUM_NUM_REQ=10;
int i=1;
  String dbURL = "jdbc:mysql://localhost:3306/xo";
  String tableName = "player";
    Connection conn = null;
 Statement stmt = null;
try {
    String url = "jdbc:mysql://localhost:3306/xo";
            String user = "root";
            String password = "anas";
            System.out.println("Connecting to DB");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to DB");
    
//     Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            //conn = DriverManager.getConnection(dbURL, "root", "anas");
    
ServerSocket serverSocketConnection = new ServerSocket(port);

/* create Database Connection*/
OnlinePlayers onlineplayers=new OnlinePlayers(conn);
onlineplayers.start();
while(i<10){
    System.out.println("Waiting clients:");
    Socket newSocket = serverSocketConnection.accept();
    System.out.println("Connected to client:");
    
InputStream inputStream = newSocket.getInputStream();
OutputStream outputStream = newSocket.getOutputStream();

ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

RequestHandler request=  new RequestHandler(newSocket,inputStream,objectInputStream,outputStream,objectOutputStream,conn);
request.start();

i++;

}
serverSocketConnection.close();

/*close database Connection*/

}catch(Exception e){System.out.println(e);}
}
}
