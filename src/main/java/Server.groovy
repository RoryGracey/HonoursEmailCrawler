import java.sql.*
import jcsp.lang.*

class Server implements CSProcess{
    void run(){
        java.sql.Connection con
        Statement st
        try{
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.33:3306/emailInbox","root","############")
        }catch(SQLException e){
            e.printStackTrace()
        }
        ServerSocket server = null
        try{
            server = new ServerSocket(3000)
        }catch(IOException e){
            e.printStackTrace()
        }
        println("Started Server")
        while (true){
            try{
                Socket s = server.accept()
                System.out.println("Connected")
                DataInputStream dIn = new DataInputStream(s.getInputStream())
                String recievedUser = dIn.readUTF()
                String recievedSubject = dIn.readUTF()
                String recievedBody = dIn.readUTF()
                println("Connected Email Client Send DATA")
                st = con.createStatement()
                st.executeUpdate("INSERT INTO emails(user, subject, body) VALUES('"+recievedUser+"','"+recievedSubject+"','"+recievedBody+"');")

            }catch(Exception error){
                println("No Connection")
                println(error)
            }
        }
    }
}
