import jcsp.lang.*
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

class NMBuffer implements CSProcess{
    ChannelInput fromCheckerProcesses
    ChannelOutput toReceiver
    void run() {


        def nodeAddr = new TCPIPNodeAddress(3007)
        Node.getInstance().init(nodeAddr)
        def fromCheckers = NetChannel.net2one()
        while(true){
            println "in loop"
            def result = fromCheckers.read()
            println('Got good from: ' + result)
            toReceiver.write(result)
        }
    }
}
