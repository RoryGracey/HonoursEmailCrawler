import jcsp.lang.*
import groovyJCSP.*
import jcsp.net2.NetChannel
import jcsp.net2.Node
import jcsp.net2.tcpip.TCPIPNodeAddress

import java.util.Random

class QuarantineNetworkManager implements CSProcess {
    ChannelInputList fromChecker
    ChannelOutputList toChecker
    def toNMC
    def fromNMLocation
    def fromNMC
    void run(){
        def avCheckers = []
        println(avCheckers)
        for(i in 1 .. toChecker.size() - 2){
            avCheckers << i
        }
        toNMC.write(fromNMLocation)
        while (true){
            toNMC.write('RequestJob')
            def response = fromNMC.read()
            if (response){
                def takingJob = (int)avCheckers.pop()
                toChecker[takingJob].write(response)
                avCheckers << takingJob
            }
        }
    }
}
