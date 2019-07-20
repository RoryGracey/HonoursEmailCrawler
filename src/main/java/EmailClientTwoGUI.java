import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EmailClientTwoGUI{
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton sendEmailButton;
    private JPanel jp1;
    private JComboBox comboBox1;
    private Socket s;
    public static void main(String[] args) throws IOException {
        // Create GUI
        JFrame frame = new JFrame("App");
        frame.setContentPane(new EmailClientTwoGUI().jp1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public EmailClientTwoGUI() throws IOException {
        sendEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textOne = textArea1.getText();
                String textTwo = textArea2.getText();
                System.out.println(textOne);
                System.out.println(textTwo);
                try{
                    DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
                    dOut.writeUTF(comboBox1.getSelectedItem().toString());
                    dOut.writeUTF(textOne);
                    dOut.writeUTF(textTwo);
                    dOut.flush();
                }catch (Exception error){
                    System.out.println("Connection Not Made");
                }
            }
        });
        // Drop down auto-fill
        String[] rs = {"User One"};
        s = new Socket("127.0.0.1", 3000);
        System.out.println("Connected To Server");

        for(int i = 0; i < rs.length; i++){
            System.out.println(rs[i]);
            comboBox1.addItem(rs[i]);
        }
    }
}
