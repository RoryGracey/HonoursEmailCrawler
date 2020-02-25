import jcsp.lang.*
import groovyJCSP.*
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
        def avCheckers = []
        println(avCheckers)
        for(i in 1 .. toChecker.size() - 2){
            avCheckers << i
        }

        while (true){
            jobRequest.write('RequestJob')
            def response = fromNetworkManager.read()
            if (response){
                def takingJob = (int)avCheckers.pop()
                toChecker[takingJob].write(response)
                avCheckers << takingJob
            }

        }
    }
}
