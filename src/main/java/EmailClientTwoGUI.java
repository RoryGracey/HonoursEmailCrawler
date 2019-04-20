import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.Socket;

public class EmailClientTwoGUI {
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton sendEmailButton;
    private JPanel jp1;
    private Socket s;
    public static void main(String[] args){
        JFrame frame = new JFrame("App");
        frame.setContentPane(new EmailClientTwoGUI().jp1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public EmailClientTwoGUI() {
        sendEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textOne = textArea1.getText();
                String textTwo = textArea2.getText();
                System.out.println(textOne);
                System.out.println(textTwo);
                try{
                    s = new Socket("127.0.0.1", 3000);
                    System.out.println("Connected To Server");
                    DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
                    dOut.writeUTF("Subject: " + textOne);
                    dOut.writeUTF("Body: " + textTwo);
                    dOut.flush();
                }catch (Exception error){
                    System.out.println("Connection Not Made");
                }
            }
        });
    }
}
