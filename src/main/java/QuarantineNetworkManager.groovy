import jcsp.lang.*
import groovyJCSP.*
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.util.Random

class QuarantineNetworkManager implements CSProcess {
    ChannelInput fromNetworkManager
    ChannelInputList fromChecker
    ChannelOutputList toChecker
    ChannelOutput toBuffer
    ChannelOutput jobRequest
    void run(){
        sleep(1000)
        println('started')
        def controllerIP = '146.176.75.9'
        def avCheckers = []
        println(avCheckers)
        for(i in 1 .. toChecker.size() - 2){
            avCheckers << i
        }
        def nodeAddr = new TCPIPNodeAddress(4000)
        Node.getInstance().init(nodeAddr)

        def toNM = new TCPIPNodeAddress(controllerIP, 3000)
        def toNMChan = NetChannel.any2net(toNM, 50)
        def fromNM = NetChannel.net2one()
        def fromNMLoc = fromNM.getLocation()

        //println(joinQNM.toString())
        toNMChan.write(fromNMLoc)
        println('Wrote')
        while (true){
            toNMChan.write('RequestJob')
            def response = fromNM.read()
            if (response){
                def takingJob = (int)avCheckers.pop()
                toChecker[takingJob].write(response)
                avCheckers << takingJob
            }

        }
    }
}
