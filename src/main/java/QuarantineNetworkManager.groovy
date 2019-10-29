import jcsp.lang.*
import java.util.Random
class QuarantineNetworkManager implements CSProcess {
    ChannelInput fromNetworkManager
    ChannelOutput toNetworkManager
    void run(){
        fromNetworkManager.read()
        Random rand = new Random()
        def num = rand.nextInt(1)
        if(num == 0){
            toNetworkManager.write('OK')
        }else{
            toNetworkManager.write('BAD')
        }
    }
}
