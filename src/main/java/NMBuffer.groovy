import jcsp.lang.*
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

class NMBuffer implements CSProcess{
    ChannelInput fromCheckerProcesses
    ChannelOutput toReceiver
    void run() {
    while(true){
        def nodeAddr = new TCPIPNodeAddress(3003)
        Node.getInstance().init(nodeAddr)
        def fromCheckers = NetChannel.net2one()
        def result = fromCheckers.read()
        //println('Got good from: ' + result)
        toReceiver.write(result)
    }
    }
}
