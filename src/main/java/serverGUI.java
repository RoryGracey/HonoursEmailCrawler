import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.*;
import java.sql.*;

public class serverGUI {
    private JPanel panel1;
    private JLabel ConnectionLabel;
    private JButton StartServer;
    private JLabel subject;
    private JLabel body;
    private Connection con = DriverManager.getConnection("jdbc:mysql://192.168.0.33:3306/emailInbox","root","#######");
    private Statement st;
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("App");
        frame.setContentPane(new serverGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    public serverGUI() throws SQLException {
        StartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartServer.setText("Stop Server");
                try{
                    ServerSocket server = new ServerSocket(3000);
                    Socket s = server.accept();
                    System.out.println("Connected");
                    DataInputStream dIn = new DataInputStream(s.getInputStream());
                    String recievedUser = dIn.readUTF();
                    String recievedSubject = dIn.readUTF();
                    String recievedBody = dIn.readUTF();
                    ConnectionLabel.setText("Connected Email Client");
                    subject.setText(recievedSubject);
                    body.setText(recievedBody);
                    st = con.createStatement();
                    st.executeUpdate("INSERT INTO emails(user, subject, body) VALUES('"+recievedUser+"','"+recievedSubject+"','"+recievedBody+"');");
                }catch (Exception error){
                    System.out.println("No Connection");
                    System.out.println(error);
                }
            }
        });
    }
}
