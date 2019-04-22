import java.sql.*
import jcsp.lang.*

class Server implements CSProcess{
    ChannelOutput outputSubject
    ChannelOutput outputBody
    ChannelInput inputChannel
    void run(){
        java.sql.Connection con
        Statement st
        try{
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.33:3306/emailInbox","root","##########")
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
                String receivedUser = dIn.readUTF()
                String receivedSubject = dIn.readUTF()
                String receivedBody = dIn.readUTF()
                println("Connected Email Client Send DATA")
                outputSubject.write(receivedSubject)
                outputBody.write(receivedBody)
                def receivedInput = inputChannel.read()
                println(receivedInput)
                st = con.createStatement()
                st.executeUpdate("INSERT INTO emails(user, subject, body) VALUES('"+receivedUser+"','"+receivedSubject+"','"+receivedBody+"');")

            }catch(Exception error){
                println("No Connection")
                println(error)
            }
        }
    }
}
