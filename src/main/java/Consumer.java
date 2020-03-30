import jcsp.lang.CSProcess;
import jcsp.lang.ChannelInput;

public class Consumer implements CSProcess {
    private ChannelInput inChannel;
    Consumer(ChannelInput inChannel){
        this.inChannel = inChannel;
    }
    public void run(){
        int i = 100;
        while(i > 0){
            i = (int) inChannel.read();
            System.out.println("The Value read was: " + i);
        }
        System.out.println("Done.");
    }
}