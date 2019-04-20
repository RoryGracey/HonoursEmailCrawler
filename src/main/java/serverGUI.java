import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.*;

public class serverGUI {
    private JPanel panel1;
    private JLabel ConnectionLabel;
    private JButton StartServer;
    private JLabel subject;
    private JLabel body;


    public static void main(String[] args){
        JFrame frame = new JFrame("App");
        frame.setContentPane(new serverGUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    public serverGUI() {
        StartServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartServer.setText("Stop Server");
                try{
                    ServerSocket server = new ServerSocket(3000);
                    Socket s = server.accept();
                    System.out.println("Connected");
                    DataInputStream dIn = new DataInputStream(s.getInputStream());
                    String recievedSubject = dIn.readUTF();
                    String recievedBody = dIn.readUTF();
                    ConnectionLabel.setText("Connected Email Client");
                    subject.setText(recievedSubject);
                    body.setText(recievedBody);
                }catch (Exception error){
                    System.out.println("No Connection");
                }
            }
        });
    }
}
