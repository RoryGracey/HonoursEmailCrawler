import jcsp.lang.*
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

class NMBuffer implements CSProcess{
    ChannelOutput toReceiver
    void run() {
        String ip = '146.176.75.72'
        def fromCheckers = new TCPIPNodeAddress(ip, 3015)
        Node.getInstance().init(fromCheckers)
        def fromCheckersChan = NetChannel.numberedNet2One(52)
        while(true){
            def result = fromCheckersChan.read()
            println('Got good from: ' + result)
            toReceiver.write(result)
        }
    }
}
