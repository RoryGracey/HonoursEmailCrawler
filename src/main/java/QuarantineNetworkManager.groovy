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
    def toNMC
    def fromNMLocation
    def fromNMC
    void run(){
        sleep(1000)
        println('started')
        def avCheckers = []
        println(avCheckers)
        for(i in 1 .. toChecker.size() - 2){
            avCheckers << i
        }
        toNMC.write(fromNMLocation)
        println('Wrote')
        while (true){
            toNMC.write('RequestJob')
            println "waiting on response"
            def response = fromNMC.read()
            if (response){
                println(avCheckers)
                def takingJob = (int)avCheckers.pop()
                toChecker[takingJob].write(response)
                println "wrote job"
                avCheckers << takingJob
            }

        }
    }
}
